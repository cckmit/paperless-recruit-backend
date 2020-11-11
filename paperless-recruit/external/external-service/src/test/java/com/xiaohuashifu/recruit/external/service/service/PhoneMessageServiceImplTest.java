package com.xiaohuashifu.recruit.external.service.service;

import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.external.api.dto.PhoneMessageDTO;
import com.xiaohuashifu.recruit.external.api.service.PhoneMessageService;
import com.xiaohuashifu.recruit.external.service.ExternalServiceApplicationTests;
import org.apache.dubbo.config.annotation.Reference;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * 描述：
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/11 15:23
 */
public class PhoneMessageServiceImplTest extends ExternalServiceApplicationTests {

    @Reference
    private PhoneMessageService phoneMessageService;

    @Test
    public void sendPhoneMessage() {
        final Result<Object> sendPhoneMessage = phoneMessageService.sendPhoneMessage(
                new PhoneMessageDTO("15992321303", "zzzz"));
        System.out.println(sendPhoneMessage);
    }
}