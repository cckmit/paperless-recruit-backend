package com.xiaohuashifu.recruit.authentication.service.service;

import com.xiaohuashifu.recruit.authentication.api.service.PhoneLoginService;
import com.xiaohuashifu.recruit.authentication.service.AuthenticationServiceApplicationTests;
import org.apache.dubbo.config.annotation.Reference;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * 描述：
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/11 16:52
 */
public class PhoneLoginServiceImplTest extends AuthenticationServiceApplicationTests {

    @Reference
    private PhoneLoginService phoneLoginService;

    @Test
    public void createMessageAuthCodeAndSend() {
        System.out.println(phoneLoginService.createMessageAuthCodeAndSend("15992321303"));
    }

    @Test
    public void checkMessageAuthCode() {
    }
}