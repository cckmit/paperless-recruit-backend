package com.xiaohuashifu.recruit.external.service.service;

import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.external.api.dto.SmsDTO;
import com.xiaohuashifu.recruit.external.api.service.SmsService;
import org.apache.dubbo.config.annotation.Service;

/**
 * 描述：发送短信服务
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/11 15:22
 */
@Service
public class SmsServiceImpl implements SmsService {
    @Override
    public Result<Object> sendSms(SmsDTO smsDTO) {
        return Result.success("Send sms success.");
    }
}
