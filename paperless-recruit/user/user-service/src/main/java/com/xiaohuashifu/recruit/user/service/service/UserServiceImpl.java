package com.xiaohuashifu.recruit.user.service.service;

import com.github.dozermapper.core.Mapper;
import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.recruit.authentication.api.service.PasswordService;
import com.xiaohuashifu.recruit.common.result.ErrorCodeEnum;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.external.api.dto.EmailAuthCodeDTO;
import com.xiaohuashifu.recruit.external.api.dto.SmsAuthCodeDTO;
import com.xiaohuashifu.recruit.external.api.service.EmailService;
import com.xiaohuashifu.recruit.external.api.service.SmsService;
import com.xiaohuashifu.recruit.user.api.dto.UserDTO;
import com.xiaohuashifu.recruit.user.api.query.UserQuery;
import com.xiaohuashifu.recruit.user.api.service.RoleService;
import com.xiaohuashifu.recruit.user.api.service.UserProfileService;
import com.xiaohuashifu.recruit.user.api.service.UserService;
import com.xiaohuashifu.recruit.user.service.dao.UserMapper;
import com.xiaohuashifu.recruit.user.service.do0.UserDO;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 描述：用户服务的rpc实现
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/10/30 15:05
 */
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

    private final UserMapper userMapper;

    private final Mapper mapper;

    private final StringRedisTemplate redisTemplate;

    private final RedisScript<Long> incrementIdRedisScript;

    @Value("${service.user.default-role-id}")
    private Long defaultUserRoleId;

    /**
     * 短信验证码过期时间5分钟
     */
    private static final int SMS_AUTH_CODE_EXPIRED_TIME = 5;

    /**
     * 邮箱验证码过期时间5分钟
     */
    private static final int EMAIL_AUTH_CODE_EXPIRED_TIME = 5;

    /**
     * 验证码注册的主题，用于调用短信验证码服务
     */
    private static final String SMS_AUTH_CODE_SIGN_UP_SUBJECT = "user:sign-up";

    /**
     * 更新手机号码的主题，用于调用短信验证码服务
     */
    private static final String SMS_AUTH_CODE_UPDATE_PHONE_SUBJECT = "user:update-phone";

    /**
     * 短信验证码更新密码的主题，用于调用短信验证码服务
     */
    private static final String SMS_AUTH_CODE_UPDATE_PASSWORD_SUBJECT = "user:update-password";

    /**
     * 更新邮件的主题，用于调用邮箱验证码服务
     */
    private static final String EMAIL_AUTH_CODE_UPDATE_EMAIL_SUBJECT = "user:update-email";

    /**
     * 更新邮箱的的邮件标题
     */
    private static final String EMAIL_AUTH_CODE_UPDATE_EMAIL_TITLE = "更新邮箱";

    /**
     * 邮箱验证码更新密码的主题，用于调用邮箱验证码服务
     */
    private static final String EMAIL_AUTH_CODE_UPDATE_PASSWORD_SUBJECT = "user:update-password";

    /**
     * 邮箱验证码更新密码的邮件标题
     */
    private static final String EMAIL_AUTH_CODE_UPDATE_PASSWORD_TITLE = "更新密码";

    /**
     * 注册时用户名的自增编号Redis Key，用于避免用户名重复
     */
    private static final String SIGN_UP_USERNAME_INCREMENT_ID_REDIS_KEY = "user:sign-up:username:increment-id";

    /**
     * 注册时用户名的自增编号Redis里的起始值
     */
    private static final String SIGN_UP_USERNAME_INCREMENT_ID_REDIS_START_VALUE = "0";

    /**
     * 注册时用户名的前缀
     */
    private static final String SIGN_UP_USERNAME_PREFIX = "scau_recruit";

    public UserServiceImpl(UserMapper userMapper, Mapper mapper, StringRedisTemplate redisTemplate,
                           RedisScript<Long> incrementIdRedisScript) {
        this.userMapper = userMapper;
        this.mapper = mapper;
        this.redisTemplate = redisTemplate;
        this.incrementIdRedisScript = incrementIdRedisScript;
    }

    /**
     * 创建用户，对外不使用该方式进行注册
     *
     * @errorCode InvalidParameter: 用户名或密码格式错误
     *              OperationConflict: 用户名已经存在
     *
     * @param username 用户名
     * @param password 密码
     * @return 新创建的用户
     */
    @Override
    public Result<UserDTO> signUpUser(String username, String password) {
        // 判断用户名是否存在
        int count = userMapper.countByUsername(username);
        if (count > 0) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT, "The username already exist.");
        }

        // 添加到数据库
        UserDO userDO = new UserDO.Builder()
                .username(username)
                .password(passwordService.encodePassword(password))
                .build();
        return saveUser(userDO);
    }

    /**
     * 通过短信验证码注册账号
     * 该方式会随机生成用户名和密码
     *
     * @errorCode InvalidParameter: 手机号码或验证码或密码格式错误
     *              OperationConflict: 手机号码已经存在
     *              InvalidParameter.Incorrect: 短信验证码错误
     *
     * @param phone 手机号码
     * @param authCode 短信验证码
     * @return 新创建的用户
     */
    @Override
    public Result<UserDTO> signUpBySmsAuthCode(String phone, String authCode) {
        return signUpBySmsAuthCode(phone, authCode, null);
    }

    /**
     * 通过短信验证码注册账号
     * 该方式会随机生成用户名
     * 若密码为 null 会随机生成密码
     * 推荐使用该方式进行注册，且密码不允许为 null
     *
     * @errorCode InvalidParameter: 手机号码或验证码或密码格式错误
     *              OperationConflict: 手机号码已经存在
     *              InvalidParameter.AuthCode.Incorrect: 短信验证码错误
     *
     * @param phone 手机号码
     * @param authCode 短信验证码
     * @param password 密码
     * @return 新创建的用户
     */
    @Override
    public Result<UserDTO> signUpBySmsAuthCode(String phone, String authCode, String password) {
        // 判断手机号码是否存在
        int count = userMapper.countByPhone(phone);
        if (count > 0) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT, "Phone does already exist.");
        }

        // 随机生成用户名
        // 这里的解决方案是scau_recruit_{7随机数}_{10自增id}
        Long incrementId = redisTemplate.execute(incrementIdRedisScript,
                Collections.singletonList(SIGN_UP_USERNAME_INCREMENT_ID_REDIS_KEY),
                SIGN_UP_USERNAME_INCREMENT_ID_REDIS_START_VALUE);
        String username = SIGN_UP_USERNAME_PREFIX + "_"
                + RandomStringUtils.randomNumeric(7) + "_"
                + String.format("%010d", incrementId);

        // 判断验证码是否正确
        Result<Void> checkEmailAuthCodeResult = smsService.checkSmsAuthCode(new SmsAuthCodeDTO.Builder()
                .phone(phone)
                .subject(SMS_AUTH_CODE_SIGN_UP_SUBJECT)
                .authCode(authCode)
                .delete(true)
                .build());
        if (!checkEmailAuthCodeResult.isSuccess()) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_AUTH_CODE_INCORRECT, "Invalid auth code.");
        }

        // 如果密码为null，则随机生成密码
        if (password == null) {
            password = RandomStringUtils.randomNumeric(20);
        }

        // 添加到数据库
        UserDO userDO = new UserDO.Builder()
                .username(username)
                .password(passwordService.encodePassword(password))
                .phone(phone)
                .build();
        return saveUser(userDO);
    }

    /**
     * 通过id获取用户信息
     *
     * @errorCode InvalidParameter: 编号格式错误
     *              InvalidParameter.NotFound: 该编号的用户不存在
     *
     * @param id 用户编号
     * @return 获取到的用户
     */
    @Override
    public Result<UserDTO> getUser(Long id) {
        final UserDO user = userMapper.getUser(id);
        if (user == null) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_FOUND);
        }
        return Result.success(mapper.map(user, UserDTO.class));
    }

    /**
     * 通过用户名获取用户对象
     *
     * @errorCode InvalidParameter: 用户名格式错误
     *              InvalidParameter.NotFound: 该用户名的用户不存在
     *
     * @param username 用户名
     * @return 获取到的用户
     */
    @Override
    public Result<UserDTO> getUserByUsername(String username) {
        UserDO user = userMapper.getUserByUsername(username);
        if (user == null) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_FOUND);
        }
        return Result.success(mapper.map(user, UserDTO.class));
    }

    /**
     * 通过用户名或者手机或者邮箱获取用户对象
     * 该接口用于通过[用户名|手机号码|邮箱]+密码进行登录的服务
     *
     * @errorCode InvalidParameter: [用户名或者手机或者邮箱]格式错误
     *              InvalidParameter.NotFound: 该[用户名或者手机或者邮箱]对应的用户不存在
     *
     * @param usernameOrPhoneOrEmail 用户名或者手机或者邮箱
     * @return 获取到的用户
     */
    @Override
    public Result<UserDTO> getUserByUsernameOrPhoneOrEmail(String usernameOrPhoneOrEmail) {
        UserDO user = userMapper.getUserByUsernameOrPhoneOrEmail(usernameOrPhoneOrEmail);
        if (user == null) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_FOUND);
        }
        return Result.success(mapper.map(user, UserDTO.class));
    }

    /**
     * 通过手机号码获取用户对象
     *
     * @errorCode InvalidParameter: 手机号码格式错误
     *              InvalidParameter.NotFound: 该手机号码的用户不存在
     *
     * @param phone 手机号
     * @return 获取到的用户
     */
    @Override
    public Result<UserDTO> getUserByPhone(String phone) {
        final UserDO user = userMapper.getUserByPhone(phone);
        if (user == null) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_FOUND);
        }
        return Result.success(mapper.map(user, UserDTO.class));
    }

    /**
     * 通过邮箱获取用户对象
     *
     * @errorCode InvalidParameter: 邮箱格式错误
     *              InvalidParameter.NotFound: 该邮箱的用户不存在
     *
     * @param email 邮箱
     * @return 获取到的用户
     */
    @Override
    public Result<UserDTO> getUserByEmail(String email) {
        final UserDO user = userMapper.getUserByEmail(email);
        if (user == null) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_FOUND);
        }
        return Result.success(mapper.map(user, UserDTO.class));
    }

    /**
     * 多参数查询用户信息
     *
     * @errorCode InvalidParameter: 查询参数出错
     *
     * @param query 查询参数
     * @return Result<PageInfo<UserDTO>> 带分页信息的查询结果用户列表，可能返回空列表
     */
    @Override
    public Result<PageInfo<UserDTO>> listUsers(UserQuery query) {
        List<UserDO> userDOList = userMapper.listUsers(query);
        List<UserDTO> userDTOList = userDOList
                .stream()
                .map(userDO -> mapper.map(userDO, UserDTO.class))
                .collect(Collectors.toList());
        PageInfo<UserDTO> pageInfo = new PageInfo<>(userDTOList);
        return Result.success(pageInfo);
    }

    /**
     * 更新用户名
     *
     * @errorCode InvalidParameter: 用户编号或新用户名格式错误 | 用户不存在
     *              OperationConflict: 新用户名已经存在
     *
     * @param id 用户编号
     * @param newUsername 新用户名
     * @return 更新后的用户
     */
    @Override
    public Result<UserDTO> updateUsername(Long id, String newUsername) {
        // 判断用户是否存在
        int count = userMapper.count(id);
        if (count < 1) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER, "The user does not exist.");
        }

        // 判断用户名是否存在
        count = userMapper.countByUsername(newUsername);
        if (count > 0) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT, "The new username already exist.");
        }

        // 更新用户名
        userMapper.updateUsername(id, newUsername);
        return getUser(id);
    }

    /**
     * 更新手机号码
     *
     * @errorCode InvalidParameter: 用户编号或新手机号码或短信验证码格式错误 | 用户不存在
     *              OperationConflict: 新手机号码已经存在
     *              InvalidParameter.AuthCode.Incorrect: 短信验证码错误
     *
     * @param id 用户编号
     * @param newPhone 新手机号码
     * @param authCode 短信验证码
     * @return 更新后的用户
     */
    @Override
    public Result<UserDTO> updatePhone(Long id, String newPhone, String authCode) {
        // 判断用户是否存在
        int count = userMapper.count(id);
        if (count < 1) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER, "The user does not exist.");
        }

        // 判断手机号码是否存在
        count = userMapper.countByPhone(newPhone);
        if (count > 0) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT, "The new phone already exist.");
        }

        // 判断验证码是否正确
        Result<Void> checkSmsAuthCodeResult = smsService.checkSmsAuthCode(new SmsAuthCodeDTO.Builder()
                .phone(newPhone)
                .subject(SMS_AUTH_CODE_UPDATE_PHONE_SUBJECT)
                .authCode(authCode)
                .delete(true)
                .build());
        if (!checkSmsAuthCodeResult.isSuccess()) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_AUTH_CODE_INCORRECT, "Invalid auth code.");
        }

        // 更新手机号码
        userMapper.updatePhone(id, newPhone);
        return getUser(id);
    }

    /**
     * 更新邮箱
     *
     * @errorCode InvalidParameter: 用户编号或新邮箱或邮箱验证码格式错误 | 用户不存在
     *              OperationConflict: 新邮箱已经存在
     *              InvalidParameter.AuthCode.Incorrect: 邮箱验证码错误
     *
     * @param id 用户编号
     * @param newEmail 新邮箱
     * @param authCode 邮箱认证码
     * @return 更新后的用户
     */
    @Override
    public Result<UserDTO> updateEmail(Long id, String newEmail, String authCode) {
        // 判断用户是否存在
        int count = userMapper.count(id);
        if (count < 1) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER, "The user does not exist.");
        }

        // 判断邮箱是否存在
        count = userMapper.countByEmail(newEmail);
        if (count > 0) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT, "The new email already exist.");
        }

        // 判断验证码是否正确
        Result<Void> checkEmailAuthCodeResult = emailService.checkEmailAuthCode(new EmailAuthCodeDTO.Builder()
                .email(newEmail)
                .subject(EMAIL_AUTH_CODE_UPDATE_EMAIL_SUBJECT)
                .authCode(authCode)
                .delete(true)
                .build());
        if (!checkEmailAuthCodeResult.isSuccess()) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_AUTH_CODE_INCORRECT, "Invalid auth code.");
        }

        // 更新邮箱
        userMapper.updateEmail(id, newEmail);
        return getUser(id);
    }

    /**
     * 更新密码
     *
     * @errorCode InvalidParameter: 用户编号或新密码格式错误 | 该用户不存在
     *
     * @param id 用户编号
     * @param newPassword 新密码
     * @return 更新后的用户
     */
    @Override
    public Result<UserDTO> updatePassword(Long id, String newPassword) {
        // 判断用户是否存在
        int count = userMapper.count(id);
        if (count < 1) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER, "The user does not exist.");
        }

        // 更新密码
        userMapper.updatePassword(id, passwordService.encodePassword(newPassword));
        return getUser(id);
    }

    /**
     * 更新密码，通过邮箱验证码
     *
     * @errorCode InvalidParameter: 请求参数格式错误 | 该邮箱的用户不存在
     *              InvalidParameter.AuthCode.Incorrect: 邮箱验证码错误
     *
     * @param email 邮箱
     * @param newPassword 新密码
     * @param authCode 邮箱验证码
     * @return 更新后的用户
     */
    @Override
    public Result<UserDTO> updatePasswordByEmailAuthCode(String email, String newPassword, String authCode) {
        // 判断用户是否存在
        int count = userMapper.countByEmail(email);
        if (count < 1) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER, "The user does not exist.");
        }

        // 判断验证码是否正确
        Result<Void> checkEmailAuthCodeResult = emailService.checkEmailAuthCode(new EmailAuthCodeDTO.Builder()
                .email(email)
                .subject(EMAIL_AUTH_CODE_UPDATE_PASSWORD_SUBJECT)
                .authCode(authCode)
                .delete(true)
                .build());
        if (!checkEmailAuthCodeResult.isSuccess()) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_AUTH_CODE_INCORRECT, "Invalid auth code.");
        }

        // 更新密码
        userMapper.updatePasswordByEmail(email, passwordService.encodePassword(newPassword));
        return getUserByEmail(email);
    }

    /**
     * 更新密码，通过短信验证码
     *
     * @errorCode InvalidParameter: 手机号码或验证码或新密码格式错误
     *              InvalidParameter.NotFound: 对应手机号码的用户不存在
     *              InvalidParameter.AuthCode.Incorrect: 短信验证码错误
     *
     * @param phone 手机号码
     * @param newPassword 新密码
     * @param authCode 短信验证码
     * @return 更新后的用户
     */
    @Override
    public Result<UserDTO> updatePasswordBySmsAuthCode(String phone, String newPassword, String authCode) {
        // 判断用户是否存在
        int count = userMapper.countByPhone(phone);
        if (count < 1) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_FOUND, "The user does not exist.");
        }

        // 判断验证码是否正确
        Result<Void> checkSmsAuthCodeResult = smsService.checkSmsAuthCode(new SmsAuthCodeDTO.Builder()
                .phone(phone)
                .subject(SMS_AUTH_CODE_UPDATE_PASSWORD_SUBJECT)
                .authCode(authCode)
                .delete(true)
                .build());
        if (!checkSmsAuthCodeResult.isSuccess()) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_AUTH_CODE_INCORRECT, "Invalid auth code.");
        }

        // 更新密码
        userMapper.updatePasswordByPhone(phone, passwordService.encodePassword(newPassword));
        return getUserByPhone(phone);
    }

    /**
     * 禁用用户
     *
     * @errorCode InvalidParameter: 用户编号格式错误 | 用户不存在
     *              OperationConflict: 用户已经被禁用，无需再次禁用
     *
     * @param id 用户编号
     * @return 禁用后的用户
     */
    @Override
    public Result<UserDTO> disableUser(Long id) {
        // 判断用户是否存在
        UserDO userDO = userMapper.getUser(id);
        if (userDO == null) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER, "The user does not exist.");
        }

        // 判断用户当前的状态是不是已经是禁用
        if (!userDO.getAvailable()) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT, "This user already disable.");
        }

        // 禁用用户
        userMapper.updateAvailable(id, false);
        return getUser(id);
    }

    /**
     * 解禁用户
     *
     * @errorCode InvalidParameter: 用户编号格式错误 | 用户不存在
     *              OperationConflict: 用户没有被禁用，无需解禁
     *
     * @param id 用户编号
     * @return 解禁后的用户
     */
    @Override
    public Result<UserDTO> enableUser(Long id) {
        // 判断用户是否存在
        UserDO userDO = userMapper.getUser(id);
        if (userDO == null) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER, "The user does not exist.");
        }

        // 判断用户当前的状态是不是已经是可用
        if (userDO.getAvailable()) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT, "This user already enable.");
        }

        // 解禁用户
        userMapper.updateAvailable(id, true);
        return getUser(id);
    }

    /**
     * 判断用户是否存在
     *
     * @errorCode InvalidParameter: 用户编号格式错误
     *              InvalidParameter.User.NotExist: 对应编号的用户不存在
     *
     * @param id 用户编号
     * @return 是否存在
     */
    @Override
    public Result<Void> userExists(Long id) {
        int count = userMapper.count(id);
        if (count < 1) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_USER_NOT_EXIST);
        }
        return Result.success();
    }

    /**
     * 发送注册账号时使用的短信验证码
     *
     * @errorCode InvalidParameter: 手机号码格式错误
     *              OperationConflict: 该手机号码已经被注册，无法发送验证码
     *              InternalError: 发送短信验证码错误，需要重试
     *
     * @param phone 手机号码
     * @return 发送结果
     */
    @Override
    public Result<Void> sendSmsAuthCodeForSignUp(String phone) {
        // 判断该手机号码是否存在，如果存在就不发送短信验证码
        int count = userMapper.countByPhone(phone);
        if (count > 0) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT, "This phone already exist.");
        }

        return sendSmsAuthCode(phone, SMS_AUTH_CODE_SIGN_UP_SUBJECT);
    }

    /**
     * 发送更新手机号码时使用的短信验证码
     *
     * @errorCode InvalidParameter: 手机号码格式错误
     *              OperationConflict: 该手机号码已经被使用，无法发送验证码
     *              InternalError: 发送短信验证码错误，需要重试
     *
     * @param phone 手机号码
     * @return 发送结果
     */
    @Override
    public Result<Void> sendSmsAuthCodeForUpdatePhone(String phone) {
        // 判断该手机号码是否存在，如果存在就不发送短信验证码
        int count = userMapper.countByPhone(phone);
        if (count > 0) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT, "This phone already exist.");
        }

        return sendSmsAuthCode(phone, SMS_AUTH_CODE_UPDATE_PHONE_SUBJECT);
    }

    /**
     * 发送更新密码时使用的短信验证码
     *
     * @errorCode InvalidParameter: 手机号码格式错误
     *              InvalidParameter.NotFound: 手机号码不存在，不给发送验证码
     *              InternalError: 发送短信验证码错误，需要重试
     *
     * @param phone 手机号码
     * @return 发送结果
     */
    @Override
    public Result<Void> sendSmsAuthCodeForUpdatePassword(String phone) {
        // 判断该手机号码是否存在，如果不存在就不发送短信验证码
        int count = userMapper.countByPhone(phone);
        if (count < 1) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_FOUND, "This phone does not exist.");
        }

        return sendSmsAuthCode(phone, SMS_AUTH_CODE_UPDATE_PASSWORD_SUBJECT);
    }

    /**
     * 发送更新邮箱时使用的邮箱验证码
     *
     * @errorCode InvalidParameter: 邮箱格式错误
     *              OperationConflict: 该邮箱已经被使用，无法发送验证码
     *              InternalError: 发送邮件验证码失败，可能是邮箱地址错误，或者网络延迟
     *
     * @param email 邮箱
     * @return 发送结果
     */
    @Override
    public Result<Void> sendEmailAuthCodeForUpdateEmail(String email) {
        // 判断邮箱是否存在，邮箱如果存在就不给发送验证码
        int count = userMapper.countByEmail(email);
        if (count > 0) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT, "This email already exist.");
        }

        return sendEmailAuthCode(email, EMAIL_AUTH_CODE_UPDATE_EMAIL_SUBJECT, EMAIL_AUTH_CODE_UPDATE_EMAIL_TITLE);
    }

    /**
     * 发送更新密码时使用的邮箱验证码
     *
     * @errorCode InvalidParameter: 邮箱格式错误
     *              InvalidParameter.NotFound: 邮箱地址不存在，不给发送验证码
     *              InternalError: 发送邮件验证码失败，可能是邮箱地址错误，或者网络延迟
     *
     * @param email 邮箱
     * @return 发送结果
     */
    @Override
    public Result<Void> sendEmailAuthCodeForUpdatePassword(String email) {
        // 判断邮箱是否存在，邮箱如果不存在就不给发送验证码
        int count = userMapper.countByEmail(email);
        if (count < 1) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT, "This email does not exist.");
        }

        return sendEmailAuthCode(email, EMAIL_AUTH_CODE_UPDATE_PASSWORD_SUBJECT, EMAIL_AUTH_CODE_UPDATE_PASSWORD_TITLE);
    }

    /**
     * 发送邮件验证码
     *
     * @param email 邮件
     * @param subject 主题
     * @param title 标题
     * @return 发送结果
     */
    private Result<Void> sendEmailAuthCode(String email, String subject, String title) {
        // 创建发送邮件验证码
        EmailAuthCodeDTO emailAuthCodeDTO = new EmailAuthCodeDTO.Builder()
                .email(email)
                .subject(subject)
                .title(title)
                .expiredTime(EMAIL_AUTH_CODE_EXPIRED_TIME)
                .build();
        return emailService.createAndSendEmailAuthCode(emailAuthCodeDTO);
    }

    /**
     * 发送短信验证码
     *
     * @param phone 手机号码
     * @param subject 主题
     * @return 发送结果
     */
    private Result<Void> sendSmsAuthCode(String phone, String subject) {
        // 创建发送短信验证码
        SmsAuthCodeDTO smsAuthCodeDTO = new SmsAuthCodeDTO.Builder()
                .phone(phone)
                .subject(subject)
                .expiredTime(SMS_AUTH_CODE_EXPIRED_TIME)
                .build();
        return smsService.createAndSendSmsAuthCode(smsAuthCodeDTO);
    }

    /**
     * 保存用户
     *
     * @param userDO UserDO
     * @return Result<UserDTO>
     */
    private Result<UserDTO> saveUser(UserDO userDO) {
        userMapper.insertUser(userDO);

        // 为新账号赋予最基本的权限
        roleService.saveUserRole(userDO.getId(), defaultUserRoleId);

        // 为账号初始化个人信息
        userProfileService.createUserProfile(userDO.getId());

        return getUser(userDO.getId());
    }

}
