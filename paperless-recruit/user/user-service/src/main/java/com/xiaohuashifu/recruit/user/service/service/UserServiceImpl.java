package com.xiaohuashifu.recruit.user.service.service;

import com.github.dozermapper.core.Mapper;
import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.recruit.authentication.api.service.PasswordService;
import com.xiaohuashifu.recruit.common.result.ErrorCode;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.common.util.UsernameUtils;
import com.xiaohuashifu.recruit.external.api.dto.EmailAuthCodeDTO;
import com.xiaohuashifu.recruit.external.api.dto.SmsAuthCodeDTO;
import com.xiaohuashifu.recruit.external.api.service.EmailService;
import com.xiaohuashifu.recruit.external.api.service.SmsService;
import com.xiaohuashifu.recruit.user.api.dto.UserDTO;
import com.xiaohuashifu.recruit.user.api.query.UserQuery;
import com.xiaohuashifu.recruit.user.api.service.RoleService;
import com.xiaohuashifu.recruit.user.api.service.UserService;
import com.xiaohuashifu.recruit.user.service.dao.UserMapper;
import com.xiaohuashifu.recruit.user.service.pojo.do0.UserDO;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Value;

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

    private final UserMapper userMapper;

    private final Mapper mapper;

    @Value("${service.user.default-role-id}")
    private Long defaultUserRoleId;

    public UserServiceImpl(UserMapper userMapper, Mapper mapper) {
        this.userMapper = userMapper;
        this.mapper = mapper;
    }

    /**
     * 注册用户
     *
     * @param username 用户名
     * @param password 密码
     * @return 新注册的用户
     */
    @Override
    public Result<UserDTO> signUpUser(String username, String password) {
        // 判断用户名是否存在
        int count = userMapper.countByUsername(username.trim());
        if (count > 0) {
            return Result.fail(ErrorCode.INVALID_PARAMETER, "Username exists.");
        }

        // 添加到数据库
        UserDO userDO = new UserDO.Builder()
                .username(username.trim())
                .password(passwordService.encodePassword(password))
                .build();
        userMapper.saveUser(userDO);

        // 为新账号赋予最基本的权限
        roleService.saveUserRole(userDO.getId(), defaultUserRoleId);

        return getUser(userDO.getId());
    }

    /**
     * 通过短信验证码注册账号
     * 该方式会随机生成用户名和密码
     *
     * @param phone 手机号码
     * @param authCode 短信验证码
     * @return 新注册的用户
     */
    @Override
    public Result<UserDTO> signUpBySmsAuthCode(String phone, String authCode) {
        // 判断手机号码是否存在
        int count = userMapper.countByPhone(phone);
        if (count > 0) {
            return Result.fail(ErrorCode.INVALID_PARAMETER, "Phone exists.");
        }

        // 随机生成用户名，这里会尝试生成3次
        String username = null;
        for (int i = 0; i < 3; i++) {
            String randomUsername = UsernameUtils.randomUsername();
            count = userMapper.countByUsername(randomUsername);
            if (count == 0) {
                username = randomUsername;
                break;
            }
        }
        if (username == null) {
            return Result.fail(ErrorCode.INTERNAL_ERROR, "Internal error, please repeat.");
        }

        // 判断验证码是否正确
        Result<Void> checkEmailAuthCodeResult = smsService.checkSmsAuthCode(new SmsAuthCodeDTO.Builder()
                .phone(phone)
                .subject(SIGN_UP_SUBJECT)
                .authCode(authCode)
                .delete(true)
                .build());
        if (!checkEmailAuthCodeResult.isSuccess()) {
            return Result.fail(checkEmailAuthCodeResult);
        }

        // 添加到数据库
        UserDO userDO = new UserDO.Builder()
                .username(username)
                .password(passwordService.encodePassword(RandomStringUtils.randomNumeric(20)))
                .phone(phone)
                .build();
        userMapper.saveUser(userDO);

        // 为新账号赋予最基本的权限
        roleService.saveUserRole(userDO.getId(), defaultUserRoleId);

        return getUser(userDO.getId());
    }

    /**
     * 通过用户编号获取用户对象
     *
     * @param id 用户编号
     * @return 获取到的用户
     */
    @Override
    public Result<UserDTO> getUser(Long id) {
        final UserDO user = userMapper.getUser(id);
        if (user == null) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND);
        }
        return Result.success(mapper.map(user, UserDTO.class));
    }

    /**
     * 通过用户名获取用户对象
     *
     * @param username 用户名
     * @return 获取到的用户
     */
    @Override
    public Result<UserDTO> getUserByUsername(String username) {
        UserDO user = userMapper.getUserByUsername(username.trim());
        if (user == null) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND);
        }
        return Result.success(mapper.map(user, UserDTO.class));
    }

    /**
     * 通过用户名或者手机或者邮箱获取用户对象
     * 该接口用于通过[用户名|手机号码|邮箱]+密码进行登录的服务
     *
     * @param usernameOrPhoneOrEmail 用户名或者手机或者邮箱
     * @return 获取到的用户
     */
    @Override
    public Result<UserDTO> getUserByUsernameOrPhoneOrEmail(String usernameOrPhoneOrEmail) {
        UserDO user = userMapper.getUserByUsernameOrPhoneOrEmail(usernameOrPhoneOrEmail.trim());
        if (user == null) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND);
        }
        return Result.success(mapper.map(user, UserDTO.class));
    }

    /**
     * 通过手机号码获取用户对象
     *
     * @param phone 手机号码
     * @return 获取到的用户
     */
    @Override
    public Result<UserDTO> getUserByPhone(String phone) {
        final UserDO user = userMapper.getUserByPhone(phone.trim());
        if (user == null) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND);
        }
        return Result.success(mapper.map(user, UserDTO.class));
    }

    /**
     * 通过邮箱获取用户对象
     *
     * @param email 邮箱
     * @return 获取到的用户
     */
    @Override
    public Result<UserDTO> getUserByEmail(String email) {
        final UserDO user = userMapper.getUserByEmail(email.trim());
        if (user == null) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND);
        }
        return Result.success(mapper.map(user, UserDTO.class));
    }

    /**
     * 多参数查询用户信息
     *
     * @param query 查询参数
     * @return Result<PageInfo<UserDTO>> 带分页信息的查询结果用户列表
     */
    @Override
    public Result<PageInfo<UserDTO>> getUser(UserQuery query) {
        List<UserDO> userDOList = userMapper.getUserByQuery(query);
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
     * @param id 用户编号
     * @param newUsername 新用户名
     * @return 更新后的用户
     */
    @Override
    public Result<UserDTO> updateUsername(Long id, String newUsername) {
        // 判断用户是否存在
        int count = userMapper.count(id);
        if (count < 1) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "User not exists.");
        }

        // 去掉用户名两边的空白符
        newUsername = newUsername.trim();

        // 判断用户名是否存在
        count = userMapper.countByUsername(newUsername);
        if (count > 0) {
            return Result.fail(ErrorCode.INVALID_PARAMETER, "New username exists.");
        }

        // 更新用户名
        userMapper.updateUsername(id, newUsername);
        return getUser(id);
    }

    /**
     * 更新手机号码
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
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "User not exists.");
        }

        // 判断手机号码是否存在
        count = userMapper.countByPhone(newPhone);
        if (count > 0) {
            return Result.fail(ErrorCode.INVALID_PARAMETER, "New phone exists.");
        }

        // 判断验证码是否正确
        Result<Void> checkSmsAuthCodeResult = smsService.checkSmsAuthCode(new SmsAuthCodeDTO.Builder()
                .phone(newPhone)
                .subject(UPDATE_PHONE_SUBJECT)
                .authCode(authCode)
                .delete(true)
                .build());
        if (!checkSmsAuthCodeResult.isSuccess()) {
            return Result.fail(checkSmsAuthCodeResult);
        }

        // 更新手机号码
        userMapper.updatePhone(id, newPhone);
        return getUser(id);
    }

    /**
     * 更新邮箱
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
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "User not exists.");
        }

        // 去掉邮箱两边的空白符
        newEmail = newEmail.trim();

        // 判断邮箱是否存在
        count = userMapper.countByEmail(newEmail);
        if (count > 0) {
            return Result.fail(ErrorCode.INVALID_PARAMETER, "New email exists.");
        }

        // 判断验证码是否正确
        Result<Void> checkEmailAuthCodeResult = emailService.checkEmailAuthCode(new EmailAuthCodeDTO.Builder()
                .email(newEmail)
                .subject(UPDATE_EMAIL_SUBJECT)
                .authCode(authCode)
                .delete(true)
                .build());
        if (!checkEmailAuthCodeResult.isSuccess()) {
            return Result.fail(checkEmailAuthCodeResult);
        }

        // 更新邮箱
        userMapper.updateEmail(id, newEmail);
        return getUser(id);
    }

    /**
     * 更新密码
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
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "User not exists.");
        }

        // 更新密码
        userMapper.updatePassword(id, passwordService.encodePassword(newPassword));
        return getUser(id);
    }

    /**
     * 更新密码，通过邮箱验证码
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
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "User not exists.");
        }

        // 判断验证码是否正确
        Result<Void> checkEmailAuthCodeResult = emailService.checkEmailAuthCode(new EmailAuthCodeDTO.Builder()
                .email(email)
                .subject(UPDATE_PASSWORD_BY_EMAIL_AUTH_CODE_SUBJECT)
                .authCode(authCode)
                .delete(true)
                .build());
        if (!checkEmailAuthCodeResult.isSuccess()) {
            return Result.fail(checkEmailAuthCodeResult);
        }

        // 更新密码
        userMapper.updatePasswordByEmail(email, passwordService.encodePassword(newPassword));
        return getUserByEmail(email);
    }

    /**
     * 更新密码，通过短信验证码
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
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "User not exists.");
        }

        // 判断验证码是否正确
        Result<Void> checkSmsAuthCodeResult = smsService.checkSmsAuthCode(new SmsAuthCodeDTO.Builder()
                .phone(phone)
                .subject(UPDATE_PASSWORD_BY_SMS_AUTH_CODE_SUBJECT)
                .authCode(authCode)
                .delete(true)
                .build());
        if (!checkSmsAuthCodeResult.isSuccess()) {
            return Result.fail(checkSmsAuthCodeResult);
        }

        // 更新密码
        userMapper.updatePasswordByPhone(phone, passwordService.encodePassword(newPassword));
        return getUserByPhone(phone);
    }


    /**
     * 禁用用户
     *
     * @param id 用户编号
     * @return 禁用后的用户
     */
    @Override
    public Result<UserDTO> disableUser(Long id) {
        // 判断用户是否存在
        UserDO userDO = userMapper.getUser(id);
        if (userDO == null) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "User not exists.");
        }

        // 判断用户当前的状态是不是已经是禁用
        if (!userDO.getAvailable()) {
            return Result.fail(ErrorCode.INVALID_PARAMETER, "This user has been disable.");
        }

        // 禁用用户
        userMapper.updateAvailable(id, false);
        return getUser(id);
    }

    /**
     * 解禁用户
     *
     * @param id 用户编号
     * @return 解禁后的用户
     */
    @Override
    public Result<UserDTO> enableUser(Long id) {
        // 判断用户是否存在
        UserDO userDO = userMapper.getUser(id);
        if (userDO == null) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND, "User not exists.");
        }

        // 判断用户当前的状态是不是已经是可用
        if (userDO.getAvailable()) {
            return Result.fail(ErrorCode.INVALID_PARAMETER, "This user has been enable.");
        }

        // 解禁用户
        userMapper.updateAvailable(id, true);
        return getUser(id);
    }

    /**
     * 判断用户是否存在
     *
     * @param id 用户编号
     * @return 是否存在
     */
    @Override
    public Result<Void> userExists(Long id) {
        int count = userMapper.count(id);
        if (count < 1) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND);
        }
        return Result.success();
    }
}
