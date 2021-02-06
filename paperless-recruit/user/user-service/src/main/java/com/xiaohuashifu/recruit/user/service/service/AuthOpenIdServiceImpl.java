package com.xiaohuashifu.recruit.user.service.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaohuashifu.recruit.common.constant.AppEnum;
import com.xiaohuashifu.recruit.common.constant.PlatformEnum;
import com.xiaohuashifu.recruit.common.exception.NotFoundServiceException;
import com.xiaohuashifu.recruit.common.exception.UnknownServiceException;
import com.xiaohuashifu.recruit.common.exception.unprocessable.DuplicateServiceException;
import com.xiaohuashifu.recruit.common.exception.unprocessable.UnsupportedServiceException;
import com.xiaohuashifu.recruit.common.util.DesUtils;
import com.xiaohuashifu.recruit.external.api.service.WeChatMpService;
import com.xiaohuashifu.recruit.user.api.dto.AuthOpenIdDTO;
import com.xiaohuashifu.recruit.user.api.service.AuthOpenIdService;
import com.xiaohuashifu.recruit.user.api.service.RoleService;
import com.xiaohuashifu.recruit.user.api.service.UserService;
import com.xiaohuashifu.recruit.user.service.assembler.AuthOpenIdAssembler;
import com.xiaohuashifu.recruit.user.service.dao.AuthOpenIdMapper;
import com.xiaohuashifu.recruit.user.service.do0.AuthOpenIdDO;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

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

    private final AuthOpenIdAssembler authOpenIdAssembler;

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

    public AuthOpenIdServiceImpl(AuthOpenIdMapper authOpenIdMapper, AuthOpenIdAssembler authOpenIdAssembler) {
        this.authOpenIdMapper = authOpenIdMapper;
        this.authOpenIdAssembler = authOpenIdAssembler;
    }

    @Override
    @Transactional
    public AuthOpenIdDTO bindAuthOpenIdForWeChatMp(Long userId, AppEnum app, String code) {
        // 如果 App 类型不是微信小程序，则不需要继续下去
        if (app.getPlatform() != PlatformEnum.WECHAT_MINI_PROGRAM) {
            throw new UnsupportedServiceException("Unsupported app.");
        }

        // 检查用户是否存在
        userService.getUser(userId);

        // 检查用户是否已经绑定在这个 app 上
        LambdaQueryWrapper<AuthOpenIdDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AuthOpenIdDO::getUserId, userId)
                .eq(AuthOpenIdDO::getAppName, app);
        int count = authOpenIdMapper.selectCount(wrapper);
        if (count > 0) {
            throw new DuplicateServiceException("This user has been bind.");
        }

        // 获取并加密 openId
        String openId = weChatMpService.getOpenId(code, app);
        openId = encryptOpenId(openId);

        // 添加到数据库
        AuthOpenIdDO authOpenIdDOForInsert = AuthOpenIdDO.builder().userId(userId).appName(app).openId(openId).build();
        authOpenIdMapper.insert(authOpenIdDOForInsert);

        // 给用户添加微信小程序基本权限
        if (app == AppEnum.SCAU_RECRUIT_INTERVIEWEE_MP) {
            roleService.createUserRole(userId, INTERVIEWEE_DEFAULT_ROLE_ID);
        }
        if (app == AppEnum.SCAU_RECRUIT_INTERVIEWER_MP) {
            roleService.createUserRole(userId, INTERVIEWER_DEFAULT_ROLE_ID);
        }

        return getAuthOpenId(authOpenIdDOForInsert.getId());
    }

    @Override
    public AuthOpenIdDTO getAuthOpenId(Long id) {
        AuthOpenIdDO authOpenIdDO = authOpenIdMapper.selectById(id);
        if (authOpenIdDO == null) {
            throw new NotFoundServiceException("authOpenId", "id", id);
        }
        AuthOpenIdDTO authOpenIdDTO = authOpenIdAssembler.authOpenIdDOToAuthOpenIdDTO(authOpenIdDO);
        authOpenIdDTO.setOpenId(decryptOpenId(authOpenIdDTO.getOpenId()));
        return authOpenIdDTO;
    }

    @Override
    public AuthOpenIdDTO getAuthOpenIdByAppAndUserId(AppEnum app, Long userId) {
        LambdaQueryWrapper<AuthOpenIdDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AuthOpenIdDO::getAppName, app).eq(AuthOpenIdDO::getUserId, userId);
        AuthOpenIdDO authOpenIdDO = authOpenIdMapper.selectOne(wrapper);
        if (authOpenIdDO == null) {
            throw new NotFoundServiceException("authOpenId and userId", "app and userId", app.name() + userId);
        }
        AuthOpenIdDTO authOpenIdDTO = authOpenIdAssembler.authOpenIdDOToAuthOpenIdDTO(authOpenIdDO);
        authOpenIdDTO.setOpenId(decryptOpenId(authOpenIdDTO.getOpenId()));
        return authOpenIdDTO;
    }

    @Override
    public AuthOpenIdDTO checkAuthOpenIdForWeChatMp(AppEnum app, String code) {
        // 如果 App 类型不是微信小程序，则不需要继续下去
        if (app.getPlatform() != PlatformEnum.WECHAT_MINI_PROGRAM) {
            throw new UnsupportedServiceException("Unsupported app.");
        }

        // 获取并加密 openId
        String openId = weChatMpService.getOpenId(code, app);
        openId = encryptOpenId(openId);

        // 检查是否存在数据库，结合 app_name + openId （加密后）
        LambdaQueryWrapper<AuthOpenIdDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AuthOpenIdDO::getAppName, app).eq(AuthOpenIdDO::getOpenId, openId);
        AuthOpenIdDO authOpenIdDO = authOpenIdMapper.selectOne(wrapper);
        if (authOpenIdDO == null) {
            throw new NotFoundServiceException("The user has not been bound this app.");
        }

        return getAuthOpenId(authOpenIdDO.getId());
    }

    /**
     * 加密 openId
     * @param openId openId
     * @return 加密后的 openId
     */
    private String encryptOpenId(String openId) {
        try {
            return DesUtils.encrypt(openId, secretKey);
        } catch (Exception ignored) {
            // 本地操作，不会报错
            throw new UnknownServiceException("Encrypt openId error.");
        }
    }

    /**
     * 解密 openId
     * @param openId openId
     * @return 解密后的 openId
     */
    private String decryptOpenId(String openId) {
        try {
            return DesUtils.decrypt(openId, secretKey);
        } catch (Exception ignored) {
            // 本地操作，不会报错
            throw new UnknownServiceException("Decrypt openId error.");
        }
    }

}
