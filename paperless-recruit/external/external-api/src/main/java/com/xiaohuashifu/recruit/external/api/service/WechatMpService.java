package com.xiaohuashifu.recruit.external.api.service;

import com.xiaohuashifu.recruit.common.constant.App;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.external.api.dto.SubscribeMessageDTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

/**
 * 描述：微信小程序相关服务
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/20 13:57
 */
public interface WechatMpService {

    /**
     * 通过code获得openid
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *
     *
     * @param code code
     * @param app 具体的微信小程序
     * @return openid
     */
    default Result<String> getOpenid(@NotBlank @Size(max = 32, min = 32) String code, @NotNull App app) {
        throw new UnsupportedOperationException();
    }

    @interface SendSubscribeMessage{}
    /**
     * 发送订阅消息
     *
     * @param app 微信小程序类型
     * @param userId 用于获取openid
     * @param subscribeMessageDTO 订阅消息
     * @return 发送结果
     */
    default Result<Void> sendSubscribeMessage(@NotNull App app, @NotNull @Positive Long userId,
                                              @NotNull SubscribeMessageDTO subscribeMessageDTO) {
        throw new UnsupportedOperationException();
    }

}
