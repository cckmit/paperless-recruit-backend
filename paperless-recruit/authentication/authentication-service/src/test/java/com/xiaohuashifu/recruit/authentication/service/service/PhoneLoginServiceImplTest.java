package com.xiaohuashifu.recruit.authentication.service.service;

import com.xiaohuashifu.recruit.authentication.api.service.SmsLoginService;
import com.xiaohuashifu.recruit.authentication.service.AuthenticationServiceApplicationTests;
import org.apache.dubbo.config.annotation.Reference;
import org.junit.Test;

/**
 * 描述：
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/11 16:52
 */
public class PhoneLoginServiceImplTest extends AuthenticationServiceApplicationTests {

    @Reference
    private SmsLoginService phoneLoginService;

    @Test
    public void createMessageAuthCodeAndSend() {
        System.out.println(phoneLoginService.createSmsAuthCodeAndSend("15992321303"));
    }

    @Test
    public void checkMessageAuthCode() {
    }
}