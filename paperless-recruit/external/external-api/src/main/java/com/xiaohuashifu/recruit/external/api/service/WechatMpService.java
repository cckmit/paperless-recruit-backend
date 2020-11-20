package com.xiaohuashifu.recruit.external.api.service;

import com.xiaohuashifu.recruit.common.constant.App;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.external.api.dto.MessageTemplateDTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
     * @param code code
     * @param wechatMp 具体的微信小程序
     * @return openid
     */
    default Result<String> getOpenid(@NotBlank @Size(max = 32, min = 32) String code, @NotNull App wechatMp) {
        throw new UnsupportedOperationException();
    }

    default Result<Void> sendTemplateMessage(MessageTemplateDTO messageTemplate) {
        throw new UnsupportedOperationException();
    }

}
