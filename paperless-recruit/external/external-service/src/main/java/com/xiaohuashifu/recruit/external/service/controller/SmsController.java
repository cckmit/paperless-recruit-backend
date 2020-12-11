package com.xiaohuashifu.recruit.external.service.controller;

import com.xiaohuashifu.recruit.external.api.po.CreateAndSendSmsAuthCodePO;
import com.xiaohuashifu.recruit.external.api.service.SmsService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述：
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/19 17:04
 */
@RestController
@RequestMapping("/sms")
public class SmsController {

    @Reference
    private SmsService smsService;

    @PostMapping("/createAndSendSmsAuthCode")
    public Object createAndSendSmsAuthCode(@RequestBody CreateAndSendSmsAuthCodePO createAndSendSmsAuthCodePO) {
        return smsService.createAndSendSmsAuthCode(createAndSendSmsAuthCodePO);
    }

}
