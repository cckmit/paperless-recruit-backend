package com.xiaohuashifu.recruit.external.api.service;

import com.xiaohuashifu.recruit.common.exception.ServiceException;
import com.xiaohuashifu.recruit.common.exception.ThirdPartyServiceException;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.common.validator.annotation.Phone;
import com.xiaohuashifu.recruit.common.validator.annotation.Sms;
import com.xiaohuashifu.recruit.external.api.request.CheckSmsAuthCodeRequest;
import com.xiaohuashifu.recruit.external.api.request.CreateAndSendSmsAuthCodeRequest;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 描述：手机短信服务
 *
 * @author: xhsf
 * @create: 2020/11/11 15:20
 */
public interface SmsService {

    /**
     * 发送手机短信
     *
     * @private 内部方法
     *
     * @param phone 手机号码
     * @param message 短信内容
     * @return 发送结果
     */
    // TODO: 2020/12/9  该方法暂不支持
    default Result<Object> sendSms(@NotBlank @Phone String phone, @NotBlank @Sms String message) {
        throw new UnsupportedOperationException();
    }

    /**
     * 发送短信验证码服务
     * 该服务会把短信验证码进行缓存
     *
     * @private 内部方法
     *
     * @param request CreateAndSendSmsAuthCodeRequest
     */
    void createAndSendSmsAuthCode(@NotNull CreateAndSendSmsAuthCodeRequest request) throws ThirdPartyServiceException;

    /**
     * 短信验证码检验验证码是否有效的服务
     * 该服务检验成功后，可以清除该验证码，即一个验证码只能使用一次（SmsAuthCodeDTO.delete == true 即可）
     *
     * @private 内部方法
     *
     * @param request CheckSmsAuthCodeRequest
     */
    void checkSmsAuthCode(@NotNull CheckSmsAuthCodeRequest request) throws ServiceException;

}
