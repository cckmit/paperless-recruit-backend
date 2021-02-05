package com.xiaohuashifu.recruit.external.service.service;

import com.xiaohuashifu.recruit.common.constant.AppEnum;
import com.xiaohuashifu.recruit.common.constant.PlatformEnum;
import com.xiaohuashifu.recruit.common.result.ErrorCodeEnum;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.external.api.request.SendWeChatMpSubscribeMessageRequest;
import com.xiaohuashifu.recruit.external.api.service.WeChatMpService;
import com.xiaohuashifu.recruit.external.service.manager.WeChatMpManager;
import com.xiaohuashifu.recruit.external.service.pojo.dto.WeChatMpSessionDTO;
import com.xiaohuashifu.recruit.user.api.service.AuthOpenIdService;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;

import java.util.Optional;

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

    /**
     * 通过 code 获得 openId
     *
     * @errorCode InvalidParameter: 请求参数格式错误 | App 是不支持的类型 | 非法 code
     *
     * @param code code
     * @param app 具体的微信小程序
     * @return openId
     */
    @Override
    public Result<String> getOpenId(String code, AppEnum app) {
        // 平台必须是微信小程序
        if (app.getPlatform() != PlatformEnum.WECHAT_MINI_PROGRAM) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER, "The platform must be wechat mp.");
        }

        // 获取 openId
        Optional<WeChatMpSessionDTO> code2SessionDTOOptional = weChatMpManager.getSessionByCode(code, app);
        if (code2SessionDTOOptional.isEmpty()) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER, "The code is invalid.");
        }

        return Result.success(code2SessionDTOOptional.get().getOpenId());
    }

    /**
     * 发送订阅消息
     *
     * @errorCode InvalidParameter: 请求参数格式错误 | App 是不支持的类型 | 用户还未绑定此 app
     *              InternalError: 服务器错误 | access-token 获取失败 | 发送订阅消息出错
     *              UnknownError: 未知错误 | 发送订阅消息时微信小程序报的错误，具体查看错误消息
     *
     * @param sendWeChatMpSubscribeMessagePO 发送订阅消息的参数对象
     * @return 发送结果
     */
    @Override
    public Result<Void> sendSubscribeMessage(SendWeChatMpSubscribeMessageRequest sendWeChatMpSubscribeMessagePO) {
        AppEnum app = sendWeChatMpSubscribeMessagePO.getApp();
        // 平台必须是微信小程序
        if (app.getPlatform() != PlatformEnum.WECHAT_MINI_PROGRAM) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER, "The platform must be wechat mp.");
        }

        // 获取 openId
        Result<String> getOpenIdResult = authOpenIdService.getOpenId(app, sendWeChatMpSubscribeMessagePO.getUserId());
        if (!getOpenIdResult.isSuccess()) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER, "The user has not been bound this app.");
        }

        // 发送订阅消息
        boolean sendResult = weChatMpManager.sendSubscribeMessage(
                app, getOpenIdResult.getData(), sendWeChatMpSubscribeMessagePO);
        if (!sendResult) {
            return Result.fail(ErrorCodeEnum.INTERNAL_ERROR, "Send subscribe message failed.");
        }

        return Result.success();
    }

    public void getUserInfo(String encryptedData, String iv, String code) {
        System.out.println(weChatMpManager.getUserInfo(encryptedData, iv, code, AppEnum.SCAU_RECRUIT_INTERVIEWEE_MP));
    }


}
