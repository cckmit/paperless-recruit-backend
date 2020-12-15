package com.xiaohuashifu.recruit.user.service.service;

import com.github.dozermapper.core.Mapper;
import com.xiaohuashifu.recruit.common.constant.AppEnum;
import com.xiaohuashifu.recruit.common.constant.PlatformEnum;
import com.xiaohuashifu.recruit.common.result.ErrorCodeEnum;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.common.util.DesUtils;
import com.xiaohuashifu.recruit.external.api.service.WeChatMpService;
import com.xiaohuashifu.recruit.user.api.dto.AuthOpenIdDTO;
import com.xiaohuashifu.recruit.user.api.service.AuthOpenIdService;
import com.xiaohuashifu.recruit.user.api.service.RoleService;
import com.xiaohuashifu.recruit.user.api.service.UserService;
import com.xiaohuashifu.recruit.user.service.dao.AuthOpenIdMapper;
import com.xiaohuashifu.recruit.user.service.do0.AuthOpenIdDO;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Value;

/**
 * 描述：AuthOpenId 相关服务，用于接入第三方平台的身份认证
 *
 * @author: xhsf
 * @create: 2020/11/20 19:29
 */
@Service
public class AuthOpenIdServiceImpl implements AuthOpenIdService {

    @Reference
    private WeChatMpService weChatMpService;

    @Reference
    private UserService userService;

    @Reference
    private RoleService roleService;

    private final AuthOpenIdMapper authOpenIdMapper;

    private final Mapper mapper;

    /**
     * 华农招新面试者默认角色编号
     */
    private static final long INTERVIEWEE_DEFAULT_ROLE_ID = 2;

    /**
     * 华农招新面试官默认角色编号
     */
    private static final long INTERVIEWER_DEFAULT_ROLE_ID = 3L;

    /**
     * openId 加密时使用的密钥
     */
    @Value("${service.auth-open-id.secret}")
    private String secretKey;

    public AuthOpenIdServiceImpl(AuthOpenIdMapper authOpenIdMapper, Mapper mapper) {
        this.authOpenIdMapper = authOpenIdMapper;
        this.mapper = mapper;
    }

    /**
     * 用于微信小程序用户绑定 AuthOpenId
     * 会通过 code 获取 openId
     * 保存时会对 openId 进行加密
     *
     * @errorCode InvalidParameter: 请求参数格式错误 | 不支持的 App 类型 | 非法 code | 对应编号的用户不存在
     *              OperationConflict: 用户已经绑定在此 App 上
     *
     * @param userId 用户编号
     * @param app 具体的微信小程序，只支持 SCAU_RECRUIT_INTERVIEWEE_MP 和 SCAU_RECRUIT_INTERVIEWER_MP 两种类型的绑定
     * @param code 微信小程序 wx.login() 接口的返回结果
     * @return AuthOpenIdDTO
     */
    @Override
    public Result<AuthOpenIdDTO> bindAuthOpenIdForWeChatMp(Long userId, AppEnum app, String code) {
        // 如果 App 类型不是微信小程序，则不给绑定
        if (app.getPlatform() != PlatformEnum.WECHAT_MINI_PROGRAM) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER, "Unsupported app.");
        }

        // 检查用户是否存在
        Result<Void> userExistsResult = userService.userExists(userId);
        if (!userExistsResult.isSuccess()) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER, "This user does not exist.");
        }

        // 检查用户是否已经绑定在这个 app 上
        int count = authOpenIdMapper.countByUserIdAndAppName(userId, app);
        if (count > 0) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT, "This user has been bind.");
        }

        // 获取 openId
        Result<String> getOpenIdResult = weChatMpService.getOpenId(code, app);
        if (!getOpenIdResult.isSuccess()) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER, "Invalid code.");
        }
        String openId = getOpenIdResult.getData();

        // 加密 openId
        try {
            openId = DesUtils.encrypt(openId, secretKey);
        } catch (Exception ignored) {
            // 本地操作，不会报错
        }

        // 添加到数据库
        AuthOpenIdDO authOpenIdDO = new AuthOpenIdDO
                .Builder()
                .userId(userId)
                .appName(app)
                .openId(openId)
                .build();
        authOpenIdMapper.insertAuthOpenId(authOpenIdDO);

        // 给用户添加微信小程序基本权限
        if (app == AppEnum.SCAU_RECRUIT_INTERVIEWEE_MP) {
            roleService.saveUserRole(userId, INTERVIEWEE_DEFAULT_ROLE_ID);
        }
        if (app == AppEnum.SCAU_RECRUIT_INTERVIEWER_MP) {
            roleService.saveUserRole(userId, INTERVIEWER_DEFAULT_ROLE_ID);
        }
        return getAuthOpenId(authOpenIdDO.getId());
    }

    /**
     * 用于微信小程序用户检查 AuthOpenId
     * 会通过 code 获取 openId
     * 可以用于快捷登录时使用
     * 该接口调用成功即可证明用户身份
     *
     * @errorCode InvalidParameter: 请求参数格式错误 | 不支持的 App 类型 | 非法 code
     *              InvalidParameter.NotExist: 该用户还未绑定到此 App
     *
     * @param app 具体的微信小程序
     * @param code 微信小程序 wx.login() 接口的返回结果
     * @return AuthOpenIdDTO
     */
    @Override
    public Result<AuthOpenIdDTO> checkAuthOpenIdForWeChatMp(AppEnum app, String code) {
        // 如果 App 类型不是微信小程序，则不需要继续下去
        if (app.getPlatform() != PlatformEnum.WECHAT_MINI_PROGRAM) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER, "Unsupported app.");
        }

        // 获取 openId
        Result<String> getOpenIdResult = weChatMpService.getOpenId(code, app);
        if (!getOpenIdResult.isSuccess()) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER, "Invalid code.");
        }
        String openId = getOpenIdResult.getData();

        // 加密 openId
        try {
            openId = DesUtils.encrypt(openId, secretKey);
        } catch (Exception ignored) {
            // 本地操作，不会报错
        }

        // 检查是否存在数据库，结合 app_name + openId （加密后）
        Long id = authOpenIdMapper.getIdByAppNameAndOpenId(app, openId);
        if (id == null) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_EXIST, "The user has not been bound this app.");
        }

        return getAuthOpenId(id);
    }

    /**
     * 获取 openId
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *              InvalidParameter.NotFound: 找不到对应的 openId
     *
     * @param userId 用户编号
     * @param app 具体的微信小程序
     * @return openId 若参数错误的情况下，返回 null
     */
    @Override
    public Result<String> getOpenId(AppEnum app, Long userId) {
        // 获取 openId
        String openId = authOpenIdMapper.getOpenIdByAppNameAndUserId(app, userId);
        if (openId == null) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_FOUND);
        }

        // 解码 openId
        try {
            openId = DesUtils.decrypt(openId, secretKey);
        } catch (Exception ignored) {
            // 内部操作，不会抛出异常
        }
        return Result.success(openId);
    }

    /**
     * 获取 AuthOpenIdDTO
     *
     * @errorCode InvalidParameter.NotFound: 该编号对应的 AuthOpenId 不存在
     *
     * @param id AuthOpenId 的编号
     * @return AuthOpenIdDTO
     */
    private Result<AuthOpenIdDTO> getAuthOpenId(Long id) {
        AuthOpenIdDO authOpenIdDO = authOpenIdMapper.getAuthOpenId(id);
        if (authOpenIdDO == null) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_FOUND);
        }
        AuthOpenIdDTO authOpenIdDTO = mapper.map(authOpenIdDO, AuthOpenIdDTO.class);
        authOpenIdDTO.setApp(authOpenIdDO.getAppName());
        return Result.success(authOpenIdDTO);
    }
}
