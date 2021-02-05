package com.xiaohuashifu.recruit.external.service.manager.impl;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.xiaohuashifu.recruit.common.exception.ThirdPartyServiceException;
import com.xiaohuashifu.recruit.external.service.manager.SmsManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 描述：短信相关服务封装
 *
 * @author xhsf
 * @create 2020/12/3 15:00
 */
@Component
@Slf4j
public class SmsManagerImpl implements SmsManager {

    @Value("${aliyun.access-key-id}")
    private String accessKeyId;

    @Value("${aliyun.access-key-secret}")
    private String accessKeySecret;

    /**
     * 发送短信验证码的具体逻辑
     *
     * @param phone 手机号码
     * @param authCode 验证码
     */
    @Override
    public void sendSmsAuthCode(String phone, String authCode) {
        DefaultProfile profile = DefaultProfile.getProfile(
                "cn-hangzhou", accessKeyId, accessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", "招新小程序");
        request.putQueryParameter("TemplateCode", "SMS_205464852");
        request.putQueryParameter("TemplateParam", "{\"code\":\"" + authCode + "\"}");
        try {
            client.getCommonResponse(request);
        } catch (ClientException clientException) {
            String message = "Send sms auth code error. phone=" + phone + ", authCode=" + authCode + ".";
            log.warn(message, clientException);
            throw new ThirdPartyServiceException(message, clientException);
        }
    }
}
