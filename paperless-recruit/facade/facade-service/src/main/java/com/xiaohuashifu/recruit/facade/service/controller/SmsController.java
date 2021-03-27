package com.xiaohuashifu.recruit.facade.service.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.xiaohuashifu.recruit.external.api.request.CreateAndSendSmsAuthCodeRequest;
import com.xiaohuashifu.recruit.external.api.service.SmsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述：短信控制器
 *
 * @author xhsf
 * @create 2021/1/9 14:48
 */
@ApiSupport(author = "XHSF")
@Api(tags = "短信")
@RestController
@Validated
public class SmsController {

    @Reference
    private SmsService smsService;

    @ApiOperation(value = "创建并发送短信")
    @PostMapping("/sms")
    public String createAndSendSmsAuthCode(@RequestBody CreateAndSendSmsAuthCodeRequest request) {
        request.setExpirationTime(5);
        smsService.createAndSendSmsAuthCode(request);
        return "OK";
    }

}
