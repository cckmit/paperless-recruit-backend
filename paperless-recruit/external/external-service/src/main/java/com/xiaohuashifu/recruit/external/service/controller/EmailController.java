package com.xiaohuashifu.recruit.external.service.controller;

import com.xiaohuashifu.recruit.external.api.dto.EmailAuthCodeDTO;
import com.xiaohuashifu.recruit.external.api.service.EmailService;
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
@RequestMapping("/email")
public class EmailController {
    @Reference
    private EmailService emailService;

    @PostMapping("/createAndSendEmailAuthCode")
    public Object createAndSendEmailAuthCode(@RequestBody EmailAuthCodeDTO emailAuthCodeDTO) {
        return emailService.createAndSendEmailAuthCode(emailAuthCodeDTO);
    }
}
