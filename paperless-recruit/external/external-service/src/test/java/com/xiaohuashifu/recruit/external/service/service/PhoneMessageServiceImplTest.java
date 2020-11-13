package com.xiaohuashifu.recruit.external.service.service;

import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.external.api.dto.SmsDTO;
import com.xiaohuashifu.recruit.external.api.service.SmsService;
import com.xiaohuashifu.recruit.external.service.ExternalServiceApplicationTests;
import org.apache.dubbo.config.annotation.Reference;
import org.junit.Test;

/**
 * 描述：
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/11 15:23
 */
public class PhoneMessageServiceImplTest extends ExternalServiceApplicationTests {

    @Reference
    private SmsService phoneMessageService;

    @Test
    public void sendPhoneMessage() {
        final Result<Object> sendPhoneMessage = phoneMessageService.sendSms(
                new SmsDTO("15992321303", "zzzz"));
        System.out.println(sendPhoneMessage);
    }
}