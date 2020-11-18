package com.xiaohuashifu.recruit.authentication.service.controller;

import com.xiaohuashifu.recruit.authentication.api.service.SmsLoginService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 描述：通过短信验证码登录的对外服务
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/11 15:29
 */
@RestController
@RequestMapping("/login/phone/sms/")
public class SmsLoginController {

    @Reference
    private SmsLoginService smsLoginService;

    @PostMapping("createSmsAuthCodeAndSend")
    public Object createSmsAuthCodeAndSend(@RequestBody Map<String, String> phone) {
        return smsLoginService.createAndSendSmsAuthCode(phone.get("phone"));
    }

    @PostMapping("checkSmsAuthCode")
    public Object checkSmsAuthCode(@RequestBody Map<String, String> map) {
        return smsLoginService.checkSmsAuthCode(map.get("phone"), map.get("authCode"));
    }
}
