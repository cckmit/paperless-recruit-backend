package com.xiaohuashifu.recruit.external.api.service;

import com.xiaohuashifu.recruit.common.constant.AppEnum;
import com.xiaohuashifu.recruit.common.exception.ServiceException;
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
     * @param code code
     * @param app 具体的微信小程序
     * @return openId
     */
    String getOpenId(
            @NotBlank @Size(max = WeChatMpServiceConstants.WECHAT_MP_CODE_LENGTH,
                    min = WeChatMpServiceConstants.WECHAT_MP_CODE_LENGTH) String code,
            @NotNull AppEnum app) throws ServiceException;

    void getUserInfo(String encryptedData, String iv, String code);

    /**
     * 发送订阅消息
     *
     * @param request SendWeChatMpSubscribeMessageRequest
     */
    void sendSubscribeMessage(@NotNull SendWeChatMpSubscribeMessageRequest request) throws ServiceException;


}
