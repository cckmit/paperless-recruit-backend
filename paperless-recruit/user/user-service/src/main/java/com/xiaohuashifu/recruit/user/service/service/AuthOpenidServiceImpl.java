package com.xiaohuashifu.recruit.user.service.service;

import com.github.dozermapper.core.Mapper;
import com.xiaohuashifu.recruit.common.constant.AppEnum;
import com.xiaohuashifu.recruit.common.constant.PlatformEnum;
import com.xiaohuashifu.recruit.common.result.ErrorCodeEnum;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.common.util.DesUtils;
import com.xiaohuashifu.recruit.external.api.service.WechatMpService;
import com.xiaohuashifu.recruit.user.api.dto.AuthOpenidDTO;
import com.xiaohuashifu.recruit.user.api.service.AuthOpenidService;
import com.xiaohuashifu.recruit.user.api.service.RoleService;
import com.xiaohuashifu.recruit.user.api.service.UserService;
import com.xiaohuashifu.recruit.user.service.dao.AuthOpenidMapper;
import com.xiaohuashifu.recruit.user.service.do0.AuthOpenidDO;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Value;

/**
 * 描述：AuthOpenid 相关服务，用于接入第三方平台的身份认证
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/20 19:29
 */
@Service
public class AuthOpenidServiceImpl implements AuthOpenidService {

    @Reference
    private WechatMpService wechatMpService;

    @Reference
    private UserService userService;

    @Reference
    private RoleService roleService;

    private final AuthOpenidMapper authOpenidMapper;

    private final Mapper mapper;

    /**
     * 华农招新面试者端微信小程序绑定后默认添加的角色
     */
    @Value("${service.auth-openid.scau-recruit-interviewee-mp.default-role-id}")
    private Long scauRecruitIntervieweeMpDefaultRoleId;

    /**
     * 华农招新面试官端微信小程序绑定后默认添加的角色
     */
    @Value("${service.auth-openid.scau-recruit-interviewer-mp.default-role-id}")
    private Long scauRecruitInterviewerMpDefaultRoleId;

    /**
     * openid 加密时使用的密钥
     */
    @Value("${service.auth-openid.secret}")
    private String secretKey;

    public AuthOpenidServiceImpl(AuthOpenidMapper authOpenidMapper, Mapper mapper) {
        this.authOpenidMapper = authOpenidMapper;
        this.mapper = mapper;
    }

    /**
     * 用于微信小程序用户绑定 AuthOpenid
     * 会通过 code 获取 openid
     * 保存时会对 openid 进行加密
     *
     * @errorCode InvalidParameter: 请求参数格式错误 | 不支持的 App 类型 | 非法 code | 对应编号的用户不存在
     *              OperationConflict: 用户已经绑定在此 App 上
     *
     * @param userId 用户编号
     * @param app 具体的微信小程序，只支持 SCAU_RECRUIT_INTERVIEWEE_MP 和 SCAU_RECRUIT_INTERVIEWER_MP 两种类型的绑定
     * @param code 微信小程序 wx.login() 接口的返回结果
     * @return AuthOpenidDTO
     */
    @Override
    public Result<AuthOpenidDTO> bindAuthOpenidForWechatMp(Long userId, AppEnum app, String code) {
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
        int count = authOpenidMapper.countByUserIdAndAppName(userId, app);
        if (count > 0) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT, "This user Has been bind.");
        }

        // 获取 openid
        Result<String> getOpenidResult = wechatMpService.getOpenid(code, app);
        if (!getOpenidResult.isSuccess()) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER, "Invalid code.");
        }
        String openid = getOpenidResult.getData();

        // 加密 openid
        try {
            openid = DesUtils.encrypt(openid, secretKey);
        } catch (Exception ignored) {
            // 本地操作，不会报错
        }

        // 添加到数据库
        AuthOpenidDO authOpenidDO = new AuthOpenidDO
                .Builder()
                .userId(userId)
                .appName(app)
                .openid(openid)
                .build();
        authOpenidMapper.insertAuthOpenid(authOpenidDO);

        // 给用户添加微信小程序基本权限
        if (app == AppEnum.SCAU_RECRUIT_INTERVIEWEE_MP) {
            roleService.saveUserRole(userId, scauRecruitIntervieweeMpDefaultRoleId);
        }
        if (app == AppEnum.SCAU_RECRUIT_INTERVIEWER_MP) {
            roleService.saveUserRole(userId, scauRecruitInterviewerMpDefaultRoleId);
        }
        return getAuthOpenid(authOpenidDO.getId());
    }


    /**
     * 用于微信小程序用户检查 AuthOpenid
     * 会通过 code 获取 openid
     * 可以用于快捷登录时使用
     * 该接口调用成功即可证明用户身份
     *
     * @errorCode InvalidParameter: 请求参数格式错误 | 不支持的 App 类型 | 非法 code
     *              InvalidParameter.NotExist: 该用户还未绑定到此 App
     *
     * @param app 具体的微信小程序
     * @param code 微信小程序 wx.login() 接口的返回结果
     * @return AuthOpenidDTO
     */
    @Override
    public Result<AuthOpenidDTO> checkAuthOpenidForWechatMp(AppEnum app, String code) {
        // 如果 App 类型不是微信小程序，则不需要继续下去
        if (app.getPlatform() != PlatformEnum.WECHAT_MINI_PROGRAM) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER, "Unsupported app.");
        }

        // 获取 openid
        Result<String> getOpenidResult = wechatMpService.getOpenid(code, app);
        if (!getOpenidResult.isSuccess()) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER, "Invalid code.");
        }
        String openid = getOpenidResult.getData();

        // 加密 openid
        try {
            openid = DesUtils.encrypt(openid, secretKey);
        } catch (Exception ignored) {
            // 本地操作，不会报错
        }

        // 检查是否存在数据库，结合 app_name + openid （加密后）
        Long id = authOpenidMapper.getIdByAppNameAndOpenid(app, openid);
        if (id == null) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_EXIST, "The user has not been bound this app.");
        }

        return getAuthOpenid(id);
    }

    /**
     * 获取 openid
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *              InvalidParameter.NotFound: 找不到对应的 openid
     *
     * @param userId 用户编号
     * @param app 具体的微信小程序
     * @return openid 若参数错误的情况下，返回 null
     */
    @Override
    public Result<String> getOpenid(AppEnum app, Long userId) {
        // 获取 openid
        String openid = authOpenidMapper.getOpenidByAppNameAndUserId(app, userId);
        if (openid == null) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_FOUND);
        }

        // 解码 openid
        try {
            openid = DesUtils.decrypt(openid, secretKey);
        } catch (Exception ignored) {
            // 内部操作，不会抛出异常
        }
        return Result.success(openid);
    }

    /**
     * 获取 AuthOpenidDTO
     *
     * @errorCode InvalidParameter.NotFound: 该编号对应的 AuthOpenid 不存在
     *
     * @param id AuthOpenid 的编号
     * @return AuthOpenidDTO
     */
    private Result<AuthOpenidDTO> getAuthOpenid(Long id) {
        AuthOpenidDO authOpenidDO = authOpenidMapper.getAuthOpenid(id);
        if (authOpenidDO == null) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_FOUND);
        }
        AuthOpenidDTO authOpenidDTO = mapper.map(authOpenidDO, AuthOpenidDTO.class);
        authOpenidDTO.setApp(authOpenidDO.getAppName());
        return Result.success(authOpenidDTO);
    }
}
