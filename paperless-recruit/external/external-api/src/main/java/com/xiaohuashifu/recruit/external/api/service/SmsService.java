package com.xiaohuashifu.recruit.external.api.service;

import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.common.validator.annotation.Phone;
import com.xiaohuashifu.recruit.common.validator.annotation.Sms;
import com.xiaohuashifu.recruit.external.api.po.CheckSmsAuthCodePO;
import com.xiaohuashifu.recruit.external.api.po.CreateAndSendSmsAuthCodePO;

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
     * @errorCode InvalidParameter: 手机号码或主题或过期时间的格式错误
     *              UnknownError: 发送短信验证码错误，需要重试
     *
     * @param createAndSendSmsAuthCodePO 创建并发送短信验证码的参数对象
     * @return Result<Void> 返回结果若 Result.isSuccess()为true 表示发送成功，否则发送失败
     */
    Result<Void> createAndSendSmsAuthCode(@NotNull CreateAndSendSmsAuthCodePO createAndSendSmsAuthCodePO);

    /**
     * 短信验证码检验验证码是否有效的服务
     * 该服务检验成功后，可以清除该验证码，即一个验证码只能使用一次（SmsAuthCodeDTO.delete == true 即可）
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *              InvalidParameter.AuthCode.NotExist: 找不到对应手机号码的验证码，有可能已经过期或者没有发送成功
     *              InvalidParameter.AuthCode.Incorrect: 短信验证码值不正确
     *
     * @param checkSmsAuthCodePO 检查短信验证码的对象
     * @return Result<Void> 返回结果若 Result.isSuccess() 为 true 表示验证成功，否则验证失败
     */
    Result<Void> checkSmsAuthCode(@NotNull CheckSmsAuthCodePO checkSmsAuthCodePO);

}
