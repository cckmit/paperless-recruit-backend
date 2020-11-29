package com.xiaohuashifu.recruit.external.api.service;

import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.external.api.dto.SmsAuthCodeDTO;
import com.xiaohuashifu.recruit.external.api.dto.SmsDTO;

import javax.validation.constraints.NotNull;

/**
 * 描述：手机短信服务
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/11 15:20
 */
public interface SmsService {

    /**
     * 短信验证码的Redis key前缀
     * 推荐格式为SMS_AUTH_CODE_REDIS_PREFIX:{subject}:{phone}
     */
    String SMS_AUTH_CODE_REDIS_PREFIX = "sms:auth-code";

    @interface SendSms{}
    /**
     * 发送手机短信
     * @param smsDTO 手机短信
     * @return 发送结果
     */
    Result<Object> sendSms(SmsDTO smsDTO);

    @interface CreateAndSendSmsAuthCode{}
    /**
     * 发送短信验证码服务
     * 该服务会把短信验证码进行缓存
     *
     * @param smsAuthCodeDTO 短信验证码对象
     * @return Result<Void> 返回结果若Result.isSuccess()为true表示发送成功，否则发送失败
     */
    default Result<Void> createAndSendSmsAuthCode(@NotNull SmsAuthCodeDTO smsAuthCodeDTO) {
        throw new UnsupportedOperationException();
    }

    @interface CheckSmsAuthCode{}
    /**
     * 短信验证码检验验证码是否有效的服务
     * 该服务检验成功后，可以清除该验证码，即一个验证码只能使用一次（SmsAuthCodeDTO.delete == true即可）
     *
     * @errorCode InvalidParameter: 验证码格式错误
     *              InvalidParameter.NotFound: 找不到对应手机号码的验证码，有可能已经过期或者没有发送成功
     *              InvalidParameter.Incorrect: 短信验证码值不正确
     *
     *
     * @param smsAuthCodeDTO 短信验证码对象
     * @return Result<Void> 返回结果若Result.isSuccess()为true表示验证成功，否则验证失败
     */
    default Result<Void> checkSmsAuthCode(@NotNull SmsAuthCodeDTO smsAuthCodeDTO) {
        throw new UnsupportedOperationException();
    }

}
