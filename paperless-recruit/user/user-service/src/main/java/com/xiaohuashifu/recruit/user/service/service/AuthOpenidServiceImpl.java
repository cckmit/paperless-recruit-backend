package com.xiaohuashifu.recruit.user.service.service;

import com.github.dozermapper.core.Mapper;
import com.xiaohuashifu.recruit.common.constant.Platform;
import com.xiaohuashifu.recruit.user.api.dto.AuthOpenidDTO;
import com.xiaohuashifu.recruit.user.api.service.AuthOpenidService;
import com.xiaohuashifu.recruit.common.constant.App;
import com.xiaohuashifu.recruit.common.result.ErrorCode;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.common.util.DesUtils;
import com.xiaohuashifu.recruit.external.api.service.WechatMpService;
import com.xiaohuashifu.recruit.user.api.service.RoleService;
import com.xiaohuashifu.recruit.user.api.service.UserService;
import com.xiaohuashifu.recruit.user.service.dao.AuthOpenidMapper;
import com.xiaohuashifu.recruit.user.service.do0.AuthOpenidDO;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

/**
 * 描述：AuthOpenid相关服务，用于接入第三方平台的身份认证
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/20 19:29
 */
@Service
public class AuthOpenidServiceImpl implements AuthOpenidService {

    private static final Logger logger = LoggerFactory.getLogger(AuthOpenidService.class);

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
     * openid加密时使用的密钥
     */
    @Value("${service.auth-openid.secret}")
    private String secretKey;

    public AuthOpenidServiceImpl(AuthOpenidMapper authOpenidMapper, Mapper mapper) {
        this.authOpenidMapper = authOpenidMapper;
        this.mapper = mapper;
    }

    /**
     * 用于微信小程序用户绑定AuthOpenid
     * 会通过code获取openid
     * 保存时会对openid进行加密
     * 只支持App.SCAU_RECRUIT_INTERVIEWEE_MP和App.SCAU_RECRUIT_INTERVIEWER_MP两种类型的绑定
     *
     * @param userId 用户编号
     * @param app 具体的微信小程序
     * @param code 微信小程序wx.login()接口的返回结果
     * @return AuthOpenidDTO
     */
    @Override
    public Result<AuthOpenidDTO> bindAuthOpenidForWechatMp(Long userId, App app, String code) {
        // 如果App类型不是微信小程序，则不给绑定
        if (app.getPlatform() != Platform.WECHAT_MINI_PROGRAM) {
            return Result.fail(ErrorCode.INVALID_PARAMETER, "Unsupported app.");
        }

        // 检查用户是否存在
        Result<Void> userExistsResult = userService.userExists(userId);
        if (!userExistsResult.isSuccess()) {
            return Result.fail(userExistsResult);
        }

        // 检查用户是否已经绑定在这个app上
        int count = authOpenidMapper.countByUserIdAndAppName(userId, app);
        if (count > 0) {
            return Result.fail(ErrorCode.INVALID_PARAMETER, "Has been bind.");
        }

        // 获取openid
        Result<String> getOpenidResult = wechatMpService.getOpenid(code, app);
        if (!getOpenidResult.isSuccess()) {
            return Result.fail(getOpenidResult);
        }
        String openid = getOpenidResult.getData();

        // 加密openid
        try {
            openid = DesUtils.encrypt(openid, secretKey);
        } catch (Exception e) {
            logger.warn("Encode openid error.", e);
            return Result.fail(ErrorCode.INTERNAL_ERROR);
        }

        // 添加到数据库
        AuthOpenidDO authOpenidDO = new AuthOpenidDO
                .Builder()
                .userId(userId)
                .appName(app)
                .openid(openid)
                .build();
        authOpenidMapper.saveAuthOpenid(authOpenidDO);

        // TODO: 2020/11/21 这里如果数量大于2，可以改造成通过APP类型判断roleId
        // 给用户添加微信小程序基本权限
        if (app == App.SCAU_RECRUIT_INTERVIEWEE_MP) {
            roleService.saveUserRole(userId, scauRecruitIntervieweeMpDefaultRoleId);
        }
        if (app == App.SCAU_RECRUIT_INTERVIEWER_MP) {
            roleService.saveUserRole(userId, scauRecruitInterviewerMpDefaultRoleId);
        }
        return getAuthOpenid(authOpenidDO.getId());
    }


    /**
     * 用于微信小程序用户检查AuthOpenid
     * 会通过code获取openid
     * 可以用于快捷登录时使用
     * 该接口调用成功即可证明用户身份
     *
     * @param app 具体的微信小程序
     * @param code 微信小程序wx.login()接口的返回结果
     * @return AuthOpenidDTO
     */
    @Override
    public Result<AuthOpenidDTO> checkAuthOpenidForWechatMp(App app, String code) {
        // 如果App类型不是微信小程序，则不需要继续下去
        if (app.getPlatform() != Platform.WECHAT_MINI_PROGRAM) {
            return Result.fail(ErrorCode.INVALID_PARAMETER, "Unsupported app.");
        }

        // 获取openid
        Result<String> getOpenidResult = wechatMpService.getOpenid(code, app);
        if (!getOpenidResult.isSuccess()) {
            return Result.fail(getOpenidResult);
        }
        String openid = getOpenidResult.getData();

        // 加密openid
        try {
            openid = DesUtils.encrypt(openid, secretKey);
        } catch (Exception e) {
            logger.warn("Encode openid error.", e);
            return Result.fail(ErrorCode.INTERNAL_ERROR);
        }

        // 检查是否存在数据库，结合app_name+openid（加密后）
        Long id = authOpenidMapper.getIdByAppNameAndOpenid(app, openid);
        if (id == null) {
            return Result.fail(ErrorCode.INVALID_PARAMETER, "Not exists.");
        }

        return getAuthOpenid(id);
    }

    /**
     * 获取openid
     *
     * @param userId 用户编号
     * @param app 具体的微信小程序
     * @return openid
     */
    @Override
    public Result<String> getOpenid(App app, Long userId) {
        // 获取openid
        String openid = authOpenidMapper.getOpenidByAppNameAndUserId(app, userId);
        if (openid == null) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND);
        }

        // 解码openid
        try {
            openid = DesUtils.decrypt(openid, secretKey);
        } catch (Exception e) {
            logger.warn("Decode openid error.", e);
            return Result.fail(ErrorCode.INTERNAL_ERROR);
        }
        return Result.success(openid);
    }

    /**
     * 获取AuthOpenidDTO
     * @param id AuthOpenid编号
     * @return AuthOpenidDTO
     */
    private Result<AuthOpenidDTO> getAuthOpenid(Long id) {
        AuthOpenidDO authOpenidDO = authOpenidMapper.getAuthOpenid(id);
        if (authOpenidDO == null) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND);
        }
        AuthOpenidDTO authOpenidDTO = mapper.map(authOpenidDO, AuthOpenidDTO.class);
        authOpenidDTO.setApp(authOpenidDO.getAppName());
        return Result.success(authOpenidDTO);
    }
}
