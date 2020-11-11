package com.xiaohuashifu.recruit.authentication.service.controller;

import com.xiaohuashifu.recruit.authentication.api.service.PhoneLoginService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 描述：通过手机号码登录的对外服务
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/11 15:29
 */
@RestController
@RequestMapping("/login/phone/")
public class PhoneLoginController {

    @Reference
    private PhoneLoginService phoneLoginService;

    @PostMapping("createMessageAuthCodeAndSend")
    public Object createMessageAuthCodeAndSend(@RequestBody Map<String, String> phone) {
        System.out.println(phone);
        return phoneLoginService.createMessageAuthCodeAndSend(phone.get("phone"));
    }

    @PostMapping("checkMessageAuthCode")
    public Object checkMessageAuthCode(@RequestBody Map<String, String> map) {
        return phoneLoginService.checkMessageAuthCode(map.get("phone"), map.get("authCode"));
    }
}
