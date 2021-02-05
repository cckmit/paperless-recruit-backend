package com.xiaohuashifu.recruit.external.api.service;

import com.xiaohuashifu.recruit.common.constant.AppEnum;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.external.api.constant.WeChatMpServiceConstants;
import com.xiaohuashifu.recruit.external.api.request.SendWeChatMpSubscribeMessageRequest;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 描述：微信小程序相关服务
 *
 * @private 内部服务
 *
 * @author: xhsf
 * @create: 2020/11/20 13:57
 */
public interface WeChatMpService {

    /**
     * 通过 code 获得 openId
     *
     * @errorCode InvalidParameter: 请求参数格式错误 | App 是不支持的类型 | 非法 code
     *
     * @param code code
     * @param app 具体的微信小程序
     * @return openId
     */
    String getOpenId(
            @NotBlank @Size(max = WeChatMpServiceConstants.WECHAT_MP_CODE_LENGTH,
                    min = WeChatMpServiceConstants.WECHAT_MP_CODE_LENGTH) String code,
            @NotNull AppEnum app);

    void getUserInfo(String encryptedData, String iv, String code);

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
    Result<Void> sendSubscribeMessage(@NotNull(message = "The sendWeChatMpSubscribeMessagePO can't be null.")
                                              SendWeChatMpSubscribeMessageRequest sendWeChatMpSubscribeMessagePO);


}
