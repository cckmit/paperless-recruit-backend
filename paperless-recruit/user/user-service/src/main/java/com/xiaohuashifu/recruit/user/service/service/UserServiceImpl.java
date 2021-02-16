package com.xiaohuashifu.recruit.user.service.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaohuashifu.recruit.authentication.api.service.PasswordService;
import com.xiaohuashifu.recruit.common.aspect.annotation.DistributedLock;
import com.xiaohuashifu.recruit.common.exception.MqServiceException;
import com.xiaohuashifu.recruit.common.exception.NotFoundServiceException;
import com.xiaohuashifu.recruit.common.exception.ServiceException;
import com.xiaohuashifu.recruit.common.exception.unprocessable.DuplicateServiceException;
import com.xiaohuashifu.recruit.common.exception.unprocessable.UnmodifiedServiceException;
import com.xiaohuashifu.recruit.common.limiter.frequency.RangeRefreshFrequencyLimit;
import com.xiaohuashifu.recruit.common.query.QueryResult;
import com.xiaohuashifu.recruit.external.api.request.CheckEmailAuthCodeRequest;
import com.xiaohuashifu.recruit.external.api.request.CheckSmsAuthCodeRequest;
import com.xiaohuashifu.recruit.external.api.request.CreateAndSendEmailAuthCodeRequest;
import com.xiaohuashifu.recruit.external.api.request.CreateAndSendSmsAuthCodeRequest;
import com.xiaohuashifu.recruit.external.api.service.EmailService;
import com.xiaohuashifu.recruit.external.api.service.SmsService;
import com.xiaohuashifu.recruit.user.api.dto.UserDTO;
import com.xiaohuashifu.recruit.user.api.query.UserQuery;
import com.xiaohuashifu.recruit.user.api.request.*;
import com.xiaohuashifu.recruit.user.api.service.RoleService;
import com.xiaohuashifu.recruit.user.api.service.UserProfileService;
import com.xiaohuashifu.recruit.user.api.service.UserService;
import com.xiaohuashifu.recruit.user.service.assembler.UserAssembler;
import com.xiaohuashifu.recruit.user.service.dao.UserMapper;
import com.xiaohuashifu.recruit.user.service.do0.UserDO;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 描述：用户服务的rpc实现
 *
 * @author: xhsf
 * @create: 2020/10/30 15:05
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Reference
    private PasswordService passwordService;

    @Reference
    private EmailService emailService;

    @Reference
    private SmsService smsService;

    @Reference
    private RoleService roleService;

    @Reference
    private UserProfileService userProfileService;

    @Value("${rocketmq.topics.sms}")
    private String smsTopic;

    @Value("${rocketmq.tags.create-and-send-sms-auth-code}")
    private String createAndSendSmsAuthCodeTag;

    @Value("${rocketmq.topics.email}")
    private String emailTopic;

    @Value("${rocketmq.tags.create-and-send-email-auth-code}")
    private String createAndSendEmailAuthCodeTag;

    private final DefaultMQProducer defaultMQProducer;

    private final UserMapper userMapper;

    private final UserAssembler userAssembler;

    private final StringRedisTemplate redisTemplate;

    /**
     * 用户默认角色编号，即所有用户都拥有的角色
     */
    private static final long USER_DEFAULT_ROLE_ID = 1;

    /**
     * 短信验证码过期时间5分钟
     */
    private static final int SMS_AUTH_CODE_EXPIRED_TIME = 5;

    /**
     * 邮箱验证码过期时间5分钟
     */
    private static final int EMAIL_AUTH_CODE_EXPIRED_TIME = 5;

    /**
     * 验证码注册的主题，用于调用验证码服务
     */
    private static final String AUTH_CODE_SIGN_UP_SUBJECT = "user:sign-up";

    /**
     * 更新手机号码的主题，用于调用短信验证码服务
     */
    private static final String SMS_AUTH_CODE_UPDATE_PHONE_SUBJECT = "user:update-phone";

    /**
     * 更新邮件的主题，用于调用邮箱验证码服务
     */
    private static final String EMAIL_AUTH_CODE_UPDATE_EMAIL_SUBJECT = "user:update-email";

    /**
     * 验证码更新密码的主题，用于调用验证码服务
     */
    private static final String AUTH_CODE_UPDATE_PASSWORD_SUBJECT = "user:update-password";

    /**
     * 更新邮箱的的邮件标题
     */
    private static final String EMAIL_AUTH_CODE_UPDATE_EMAIL_TITLE = "更新邮箱";

    /**
     * 邮箱验证码更新密码的邮件标题
     */
    private static final String EMAIL_AUTH_CODE_UPDATE_PASSWORD_TITLE = "更新密码";

    /**
     * 注册时用户名的自增编号 Redis Key，用于避免用户名重复
     */
    private static final String SIGN_UP_USERNAME_INCREMENT_ID_REDIS_KEY = "user:sign-up:username:increment-id";

    /**
     * 注册时用户名的前缀
     */
    private static final String SIGN_UP_USERNAME_PREFIX = "scau_recruit";

    /**
     * 手机号码的分布式锁的 key 的模式，{0}是手机号码
     */
    private static final String PHONE_DISTRIBUTED_LOCK_KEY_PATTERN = "phone:{0}";

    /**
     * 邮箱的分布式锁的 key 的模式，{0}是邮箱
     */
    private static final String EMAIL_DISTRIBUTED_LOCK_KEY_PATTERN = "email:{0}";

    /**
     * 短信验证码每分钟频率限制
     */
    private static final long SMS_AUTH_CODE_FREQUENCY_PER_MINUTE = 1;

    /**
     * 邮箱验证码每分钟频率限制
     */
    private static final long EMAIL_AUTH_CODE_FREQUENCY_PER_MINUTE = 1;

    /**
     * 短信验证码每天频率限制
     */
    private static final long SMS_AUTH_CODE_FREQUENCY_PER_DAY = 10;

    /**
     * 注册时短信验证码限频模式，{0}为手机号码
     */
    private static final String SIGN_UP_SMS_AUTH_CODE_FREQUENCY_LIMIT_PATTERN = "user:sign-up:sms-auth-code:{0}";

    /**
     * 更新手机时短信验证码限频模式，{0}为手机号码
     */
    private static final String UPDATE_PHONE_SMS_AUTH_CODE_FREQUENCY_LIMIT_PATTERN =
            "user:update-phone:sms-auth-code:{0}";

    /**
     * 更新密码时短信验证码限频模式，{0}为手机号码
     */
    private static final String UPDATE_PASSWORD_SMS_AUTH_CODE_FREQUENCY_LIMIT_PATTERN =
            "user:update-password:sms-auth-code:{0}";

    /**
     * 注册时邮件验证码限频模式，{0}为邮箱
     */
    private static final String SIGN_UP_EMAIL_AUTH_CODE_FREQUENCY_LIMIT_PATTERN = "user:sign-up:email-auth-code:{0}";

    /**
     * 更新邮箱时邮件验证码限频模式，{0}为邮箱
     */
    private static final String UPDATE_EMAIL_EMAIL_AUTH_CODE_FREQUENCY_LIMIT_PATTERN =
            "user:update-email:email-auth-code:{0}";

    /**
     * 更新密码时邮件验证码限频模式，{0}为邮箱
     */
    private static final String UPDATE_PASSWORD_EMAIL_AUTH_CODE_FREQUENCY_LIMIT_PATTERN =
            "user:update-password:email-auth-code:{0}";

    public UserServiceImpl(DefaultMQProducer defaultMQProducer, UserMapper userMapper, UserAssembler userAssembler,
                           StringRedisTemplate redisTemplate) {
        this.defaultMQProducer = defaultMQProducer;
        this.userMapper = userMapper;
        this.userAssembler = userAssembler;
        this.redisTemplate = redisTemplate;
    }

    @Override
    @GlobalTransactional
    public UserDTO register(CreateUserRequest request) {
        // 判断用户名是否存在
        UserDO userDO = userMapper.selectByUsername(request.getUsername());
        if (userDO != null) {
            throw new DuplicateServiceException("The username already exist.");
        }

        // 添加到数据库
//        String encodedPassword = passwordService.encodePassword(request.getPassword());
        UserDO userDOForInsert = UserDO.builder().username(request.getUsername())
                .password(request.getPassword()).build();
        return ((UserServiceImpl)AopContext.currentProxy()).saveUser(userDOForInsert);
    }

    @Override
    @Transactional
    @DistributedLock(value = PHONE_DISTRIBUTED_LOCK_KEY_PATTERN, parameters = "#{#request.phone}")
    public UserDTO registerBySmsAuthCode(CreateUserBySmsAuthCodeRequest request) {
        // 判断手机号码是否存在
        UserDO userDO = userMapper.selectByPhone(request.getPhone());
        if (userDO != null) {
            throw new DuplicateServiceException("The phone does already exist.");
        }

        // 判断验证码是否正确
        smsService.checkSmsAuthCode(CheckSmsAuthCodeRequest.builder().phone(request.getPhone())
                .subject(AUTH_CODE_SIGN_UP_SUBJECT).authCode(request.getAuthCode()).delete(true).build());

        // 生成用户名
        String username = generateRandomUsername();
        String password = passwordService.encodePassword(request.getPassword());

        // 添加到数据库
        UserDO userDOForInsert =
                UserDO.builder().username(username).password(password).phone(request.getPhone()).build();
        return ((UserServiceImpl)AopContext.currentProxy()).saveUser(userDOForInsert);
    }

    @Override
    @Transactional
    @DistributedLock(value = EMAIL_DISTRIBUTED_LOCK_KEY_PATTERN, parameters = "#{#request.email}")
    public UserDTO registerByEmailAuthCode(CreateUserByEmailAuthCodeRequest request) {
        // 判断邮箱是否存在
        UserDO userDO = userMapper.selectByEmail(request.getEmail());
        if (userDO != null) {
            throw new DuplicateServiceException("The email does already exist.");
        }

        // 判断验证码是否正确
        emailService.checkEmailAuthCode(CheckEmailAuthCodeRequest.builder().email(request.getEmail())
                .subject(AUTH_CODE_SIGN_UP_SUBJECT).authCode(request.getAuthCode()).delete(true).build());

        // 生成用户名
        String username = generateRandomUsername();
        String password = passwordService.encodePassword(request.getPassword());

        // 添加到数据库
        UserDO userDOForInsert =
                UserDO.builder().username(username).password(password).email(request.getEmail()).build();
        return ((UserServiceImpl)AopContext.currentProxy()).saveUser(userDOForInsert);
    }

    @Override
    public UserDTO getUser(Long id) {
        UserDO userDO = userMapper.selectById(id);
        if (userDO == null) {
            throw new NotFoundServiceException("user", "id", id);
        }
        return userAssembler.userDOToUserDTO(userDO);
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        UserDO userDO = userMapper.selectByUsername(username);
        if (userDO == null) {
            throw new NotFoundServiceException("user", "username", username);
        }
        return userAssembler.userDOToUserDTO(userDO);
    }

    @Override
    public UserDTO getUserByUsernameOrPhoneOrEmail(String usernameOrPhoneOrEmail) {
        LambdaQueryWrapper<UserDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserDO::getUsername, usernameOrPhoneOrEmail)
                .or().eq(UserDO::getPhone, usernameOrPhoneOrEmail)
                .or().eq(UserDO::getEmail, usernameOrPhoneOrEmail);
        UserDO userDO = userMapper.selectOne(wrapper);
        if (userDO == null) {
            throw new NotFoundServiceException("user", "usernameOrPhoneOrEmail", usernameOrPhoneOrEmail);
        }
        return userAssembler.userDOToUserDTO(userDO);
    }

    @Override
    public UserDTO getUserByPhone(String phone) {
        UserDO userDO = userMapper.selectByPhone(phone);
        if (userDO == null) {
            throw new NotFoundServiceException("user", "phone", phone);
        }
        return userAssembler.userDOToUserDTO(userDO);
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        UserDO userDO = userMapper.selectByEmail(email);
        if (userDO == null) {
            throw new NotFoundServiceException("user", "email", email);
        }
        return userAssembler.userDOToUserDTO(userDO);
    }

    @Override
    public QueryResult<UserDTO> listUsers(UserQuery query) {
        LambdaQueryWrapper<UserDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.likeRight(query.getUsername() != null, UserDO::getUsername, query.getUsername())
                .likeRight(query.getPhone() != null, UserDO::getPhone, query.getPhone())
                .likeRight(query.getEmail() != null, UserDO::getEmail, query.getEmail())
                .eq(query.getAvailable() != null, UserDO::getAvailable, query.getAvailable());

        Page<UserDO> page = new Page<>(query.getPageNum(), query.getPageSize(), true);
        userMapper.selectPage(page, wrapper);
        List<UserDTO> userDTOS = page.getRecords()
                .stream().map(userAssembler::userDOToUserDTO).collect(Collectors.toList());
        return new QueryResult<>(page.getTotal(), userDTOS);
    }

    @Override
    public UserDTO updateUsername(Long id, String username) {
        // 判断用户名是否存在
        UserDO userDO = userMapper.selectByUsername(username);
        if (userDO != null) {
            throw new DuplicateServiceException("The new username already exist.");
        }

        // 更新用户名
        UserDO userDOForUpdate = UserDO.builder().id(id).username(username).build();
        userMapper.updateById(userDOForUpdate);
        return getUser(id);
    }

    @Override
    @DistributedLock(value = PHONE_DISTRIBUTED_LOCK_KEY_PATTERN, parameters = "#{#phone}")
    public UserDTO updatePhone(Long id, String phone, String authCode) {
        // 判断手机号码是否存在
        UserDO userDO = userMapper.selectByPhone(phone);
        if (userDO != null) {
            throw new DuplicateServiceException("The new phone already exist.");
        }

        // 判断验证码是否正确
        smsService.checkSmsAuthCode(CheckSmsAuthCodeRequest.builder().phone(phone)
                .subject(SMS_AUTH_CODE_UPDATE_PHONE_SUBJECT).authCode(authCode).delete(true).build());

        // 更新手机号码
        UserDO userDOForUpdate = UserDO.builder().id(id).phone(phone).build();
        userMapper.updateById(userDOForUpdate);
        return getUser(id);
    }

    @Override
    @DistributedLock(value = EMAIL_DISTRIBUTED_LOCK_KEY_PATTERN, parameters = "#{#email}")
    public UserDTO updateEmail(Long id, String email, String authCode) {
        // 判断邮箱是否存在
        UserDO userDO = userMapper.selectByEmail(email);
        if (userDO != null) {
            throw new DuplicateServiceException("The new email already exist.");
        }

        // 判断验证码是否正确
        emailService.checkEmailAuthCode(CheckEmailAuthCodeRequest.builder().email(email)
                .subject(EMAIL_AUTH_CODE_UPDATE_EMAIL_SUBJECT).authCode(authCode).delete(true).build());

        // 更新邮箱
        UserDO userDOForUpdate = UserDO.builder().id(id).email(email).build();
        userMapper.updateById(userDOForUpdate);
        return getUser(id);
    }

    @Override
    public UserDTO updatePassword(Long id, String password) {
        // 更新密码
        String encodedPassword = passwordService.encodePassword(password);
        UserDO userDOForUpdate = UserDO.builder().id(id).password(encodedPassword).build();
        userMapper.updateById(userDOForUpdate);
        return getUser(id);
    }

    @Override
    public UserDTO updatePasswordByEmailAuthCode(UpdatePasswordByEmailAuthCodeRequest request) {
        // 判断验证码是否正确
        emailService.checkEmailAuthCode(CheckEmailAuthCodeRequest.builder().email(request.getEmail())
                .subject(AUTH_CODE_UPDATE_PASSWORD_SUBJECT).authCode(request.getAuthCode()).delete(true).build());

        // 更新密码
        LambdaUpdateWrapper<UserDO> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(UserDO::getEmail, request.getEmail());
        String password = passwordService.encodePassword(request.getPassword());
        UserDO userDOForUpdate = UserDO.builder().password(password).build();
        userMapper.update(userDOForUpdate, wrapper);
        return getUserByEmail(request.getEmail());
    }

    @Override
    public UserDTO updatePasswordBySmsAuthCode(UpdatePasswordBySmsAuthCodeRequest request) {
        // 判断验证码是否正确
        smsService.checkSmsAuthCode(CheckSmsAuthCodeRequest.builder().phone(request.getPhone())
                .subject(AUTH_CODE_UPDATE_PASSWORD_SUBJECT).authCode(request.getAuthCode()).delete(true).build());

        // 更新密码
        LambdaUpdateWrapper<UserDO> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(UserDO::getPhone, request.getPhone());
        String password = passwordService.encodePassword(request.getPassword());
        UserDO userDOForUpdate = UserDO.builder().password(password).build();
        userMapper.update(userDOForUpdate, wrapper);
        return getUserByPhone(request.getPhone());
    }

    @Override
    public UserDTO disableUser(Long id) {
        // 判断用户当前的状态是不是已经是禁用
        UserDTO userDTO = getUser(id);
        if (!userDTO.getAvailable()) {
            throw new UnmodifiedServiceException("This user already disable.");
        }

        // 禁用用户
        UserDO userDOForUpdate = UserDO.builder().id(id).available(false).build();
        userMapper.updateById(userDOForUpdate);
        return getUser(id);
    }

    @Override
    public UserDTO enableUser(Long id) {
        // 判断用户当前的状态是不是已经是可用
        UserDTO userDTO = getUser(id);
        if (userDTO.getAvailable()) {
            throw new UnmodifiedServiceException("This user already enable.");
        }

        // 解禁用户
        UserDO userDOForUpdate = UserDO.builder().id(id).available(true).build();
        userMapper.updateById(userDOForUpdate);
        return getUser(id);
    }

    @RangeRefreshFrequencyLimit(key = SIGN_UP_SMS_AUTH_CODE_FREQUENCY_LIMIT_PATTERN, parameters = "#{#phone}",
            frequency = SMS_AUTH_CODE_FREQUENCY_PER_MINUTE, refreshTime = 1, timeUnit = TimeUnit.MINUTES)
    @RangeRefreshFrequencyLimit(key = SIGN_UP_SMS_AUTH_CODE_FREQUENCY_LIMIT_PATTERN, parameters = "#{#phone}",
            frequency = SMS_AUTH_CODE_FREQUENCY_PER_DAY, refreshTime = 1, timeUnit = TimeUnit.DAYS)
    @Override
    public void sendSmsAuthCodeForSignUp(String phone) {
        // 判断该手机号码是否存在，如果存在就不发送短信验证码
        UserDO userDO = userMapper.selectByPhone(phone);
        if (userDO != null) {
            throw new DuplicateServiceException("This phone already exist.");
        }

        sendSmsAuthCode(phone, AUTH_CODE_SIGN_UP_SUBJECT);
    }

    @RangeRefreshFrequencyLimit(key = SIGN_UP_EMAIL_AUTH_CODE_FREQUENCY_LIMIT_PATTERN, parameters = "#{#email}",
            frequency = EMAIL_AUTH_CODE_FREQUENCY_PER_MINUTE, refreshTime = 1, timeUnit = TimeUnit.MINUTES)
    @Override
    public void sendEmailAuthCodeForSignUp(String email, String title) {
        // 判断该邮箱是否存在，如果存在就不发送邮箱验证码
        UserDO userDO = userMapper.selectByEmail(email);
        if (userDO != null) {
            throw new DuplicateServiceException("This email already exist.");
        }

        sendEmailAuthCode(email, AUTH_CODE_SIGN_UP_SUBJECT, title);
    }

    @RangeRefreshFrequencyLimit(key = UPDATE_PHONE_SMS_AUTH_CODE_FREQUENCY_LIMIT_PATTERN, parameters = "#{#phone}",
            frequency = SMS_AUTH_CODE_FREQUENCY_PER_MINUTE, refreshTime = 1, timeUnit = TimeUnit.MINUTES)
    @RangeRefreshFrequencyLimit(key = UPDATE_PHONE_SMS_AUTH_CODE_FREQUENCY_LIMIT_PATTERN, parameters = "#{#phone}",
            frequency = SMS_AUTH_CODE_FREQUENCY_PER_DAY, refreshTime = 1, timeUnit = TimeUnit.DAYS)
    @Override
    public void sendSmsAuthCodeForUpdatePhone(String phone) {
        // 判断该手机号码是否存在，如果存在就不发送短信验证码
        UserDO userDO = userMapper.selectByPhone(phone);
        if (userDO != null) {
            throw new DuplicateServiceException("This phone already exist.");
        }

        sendSmsAuthCode(phone, SMS_AUTH_CODE_UPDATE_PHONE_SUBJECT);
    }

    @RangeRefreshFrequencyLimit(key = UPDATE_PASSWORD_SMS_AUTH_CODE_FREQUENCY_LIMIT_PATTERN, parameters = "#{#phone}",
            frequency = SMS_AUTH_CODE_FREQUENCY_PER_MINUTE, refreshTime = 1, timeUnit = TimeUnit.MINUTES)
    @RangeRefreshFrequencyLimit(key = UPDATE_PASSWORD_SMS_AUTH_CODE_FREQUENCY_LIMIT_PATTERN, parameters = "#{#phone}",
            frequency = SMS_AUTH_CODE_FREQUENCY_PER_DAY, refreshTime = 1, timeUnit = TimeUnit.DAYS)
    @Override
    public void sendSmsAuthCodeForUpdatePassword(String phone) {
        // 判断该手机号码是否存在，如果不存在就不发送短信验证码
        getUserByPhone(phone);

        // 发送短信验证码
        sendSmsAuthCode(phone, AUTH_CODE_UPDATE_PASSWORD_SUBJECT);
    }

    @RangeRefreshFrequencyLimit(key = UPDATE_EMAIL_EMAIL_AUTH_CODE_FREQUENCY_LIMIT_PATTERN, parameters = "#{#email}",
            frequency = EMAIL_AUTH_CODE_FREQUENCY_PER_MINUTE, refreshTime = 1, timeUnit = TimeUnit.MINUTES)
    @Override
    public void sendEmailAuthCodeForUpdateEmail(String email) {
        // 判断该邮箱是否存在，如果存在就不发送邮箱验证码
        UserDO userDO = userMapper.selectByEmail(email);
        if (userDO != null) {
            throw new DuplicateServiceException("This email already exist.");
        }

        sendEmailAuthCode(email, EMAIL_AUTH_CODE_UPDATE_EMAIL_SUBJECT, EMAIL_AUTH_CODE_UPDATE_EMAIL_TITLE);
    }

    @RangeRefreshFrequencyLimit(key = UPDATE_PASSWORD_EMAIL_AUTH_CODE_FREQUENCY_LIMIT_PATTERN, parameters = "#{#email}",
            frequency = EMAIL_AUTH_CODE_FREQUENCY_PER_MINUTE, refreshTime = 1, timeUnit = TimeUnit.MINUTES)
    @Override
    public void sendEmailAuthCodeForUpdatePassword(String email) {
        // 判断邮箱是否存在，邮箱如果不存在就不给发送验证码
        getUserByEmail(email);

        sendEmailAuthCode(email, AUTH_CODE_UPDATE_PASSWORD_SUBJECT, EMAIL_AUTH_CODE_UPDATE_PASSWORD_TITLE);
    }

    /**
     * 发送邮件验证码
     *
     * @param email 邮件
     * @param subject 主题
     * @param title 标题
     */
    private void sendEmailAuthCode(String email, String subject, String title) {
        // 创建发送邮件验证码
        CreateAndSendEmailAuthCodeRequest request = CreateAndSendEmailAuthCodeRequest.builder().email(email)
                .subject(subject).title(title).expirationTime(EMAIL_AUTH_CODE_EXPIRED_TIME).build();
        Message message = new Message(emailTopic, createAndSendEmailAuthCodeTag, JSON.toJSONBytes(request));
        try {
            defaultMQProducer.send(message);
        } catch (MQClientException | RemotingException | MQBrokerException | InterruptedException e) {
            log.error("Send message error." + message, e);
            throw new MqServiceException("Send message error.");
        }
    }

    /**
     * 发送短信验证码
     *
     * @param phone 手机号码
     * @param subject 主题
     */
    private void sendSmsAuthCode(String phone, String subject) throws MqServiceException {
        // 创建发送短信验证码
        CreateAndSendSmsAuthCodeRequest request = CreateAndSendSmsAuthCodeRequest.builder().phone(phone)
                .subject(subject).expirationTime(SMS_AUTH_CODE_EXPIRED_TIME).build();
        Message message = new Message(smsTopic, createAndSendSmsAuthCodeTag, JSON.toJSONBytes(request));
        try {
            defaultMQProducer.send(message);
        } catch (MQClientException | RemotingException | MQBrokerException | InterruptedException e) {
            log.error("Send message error." + message, e);
            throw new MqServiceException("Send message error.");
        }
    }

    /**
     * 保存用户
     *
     * @param userDO UserDO
     * @return UserDTO
     */
    protected UserDTO saveUser(UserDO userDO) throws ServiceException {
        // 保存用户
        userMapper.insert(userDO);

        // 为新账号赋予最基本的权限
        roleService.createUserRole(userDO.getId(), USER_DEFAULT_ROLE_ID);

        // 为账号初始化个人信息
        userProfileService.createUserProfile(userDO.getId());

        return getUser(userDO.getId());
    }

    /**
     * 随机生成用户名
     * 这里的解决方案是scau_recruit_{7随机数}_{10自增id}
     *
     * @return 随机产生的用户名
     */
    private String generateRandomUsername() {
        Long incrementId = redisTemplate.opsForValue().increment(SIGN_UP_USERNAME_INCREMENT_ID_REDIS_KEY);
        return SIGN_UP_USERNAME_PREFIX + "_"
                + RandomStringUtils.randomNumeric(7) + "_"
                + String.format("%010d", incrementId);
    }

}
