package com.xiaohuashifu.recruit.external.service.controller;

import com.xiaohuashifu.recruit.external.api.dto.SmsAuthCodeDTO;
import com.xiaohuashifu.recruit.external.api.service.SmsService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述：
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/19 17:04
 */
@RestController("/sms")
public class SmsController {
    @Reference
    private SmsService smsService;

    @PostMapping("/createAndSendSmsAuthCode")
    public Object createAndSendSmsAuthCode(@RequestBody SmsAuthCodeDTO smsAuthCodeDTO) {
        return smsService.createAndSendSmsAuthCode(smsAuthCodeDTO);
    }
}
