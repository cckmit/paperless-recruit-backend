package com.xiaohuashifu.recruit.external.api.service;

import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.external.api.dto.SmsDTO;

/**
 * 描述：手机短信服务
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/11 15:20
 */
public interface SmsService {
    @interface SendSms{}
    /**
     * 发送手机短信
     * @param smsDTO 手机短信
     * @return 发送结果
     */
    Result<Object> sendSms(SmsDTO smsDTO);
}
