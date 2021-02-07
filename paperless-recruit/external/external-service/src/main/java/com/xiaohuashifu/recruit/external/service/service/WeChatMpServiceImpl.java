package com.xiaohuashifu.recruit.external.service.service;

import com.xiaohuashifu.recruit.common.constant.AppEnum;
import com.xiaohuashifu.recruit.common.constant.PlatformEnum;
import com.xiaohuashifu.recruit.common.exception.unprocessable.UnsupportedServiceException;
import com.xiaohuashifu.recruit.external.api.request.SendWeChatMpSubscribeMessageRequest;
import com.xiaohuashifu.recruit.external.api.service.WeChatMpService;
import com.xiaohuashifu.recruit.external.service.manager.WeChatMpManager;
import com.xiaohuashifu.recruit.external.service.pojo.dto.WeChatMpSessionDTO;
import com.xiaohuashifu.recruit.user.api.dto.AuthOpenIdDTO;
import com.xiaohuashifu.recruit.user.api.service.AuthOpenIdService;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;

/**
 * 描述：微信小程序相关服务 RPC 实现
 *
 * @private 内部服务
 *
 * @author: xhsf
 * @create: 2020/11/20 14:14
 */
@Service
public class WeChatMpServiceImpl implements WeChatMpService {

    @Reference
    private AuthOpenIdService authOpenIdService;

    private final WeChatMpManager weChatMpManager;

    public WeChatMpServiceImpl(WeChatMpManager weChatMpManager) {
        this.weChatMpManager = weChatMpManager;
    }

    @Override
    public String getOpenId(String code, AppEnum app) {
        // 平台必须是微信小程序
        if (app.getPlatform() != PlatformEnum.WECHAT_MINI_PROGRAM) {
            throw new UnsupportedServiceException("The platform must be wechat mp.");
        }

        // 获取 openId
        WeChatMpSessionDTO code2SessionDTOOptional = weChatMpManager.getSessionByCode(code, app);
        return code2SessionDTOOptional.getOpenId();
    }

    @Override
    public void sendSubscribeMessage(SendWeChatMpSubscribeMessageRequest request) {
        AppEnum app = request.getApp();
        // 平台必须是微信小程序
        if (app.getPlatform() != PlatformEnum.WECHAT_MINI_PROGRAM) {
            throw new UnsupportedServiceException("The platform must be wechat mp.");
        }

        // 获取 openId
        AuthOpenIdDTO authOpenIdDTO = authOpenIdService.getAuthOpenIdByAppAndUserId(app, request.getUserId());

        // 发送订阅消息
        weChatMpManager.sendSubscribeMessage(app, authOpenIdDTO.getOpenId(), request);
    }

    public void getUserInfo(String encryptedData, String iv, String code) {
        System.out.println(weChatMpManager.getUserInfo(encryptedData, iv, code, AppEnum.SCAU_RECRUIT_INTERVIEWEE_MP));
    }

}
