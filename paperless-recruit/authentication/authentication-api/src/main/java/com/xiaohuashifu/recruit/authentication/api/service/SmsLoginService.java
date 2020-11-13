package com.xiaohuashifu.recruit.authentication.api.service;

import com.xiaohuashifu.recruit.authentication.api.dto.SmsLoginDTO;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.common.validator.annotation.SmsAuthCode;
import com.xiaohuashifu.recruit.common.validator.annotation.Phone;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * 描述：短信登录服务
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/11 15:37
 */
@Validated
public interface SmsLoginService {

    /**
     * 手机号码+短信验证码登录的创建并发送手机短信验证码服务
     * 该服务会把短信验证码进行缓存，并在验证码失效后删除缓存
     *
     * @param phone 要发送验证码的手机号码
     * @return SmsLoginDTO 该对象表示这次发送验证码的信息
     */
    Result<SmsLoginDTO> createSmsAuthCodeAndSend(
            @NotNull(message = "INVALID_PARAMETER_IS_NULL: The phone must be not null.")
            @Phone String phone);

    /**
     * 手机号码+短信验证码登录的检验验证码是否有效的服务
     * 该服务检验成功后，会清除该验证码，即一个验证码只能使用一次
     *
     * @param phone 要验证的手机号码
     * @param authCode 短信验证码
     * @return Result<Void> 返回结果若Result.isSuccess()为true表示验证成功，否则验证失败
     */
    Result<Void> checkSmsAuthCode(
            @NotNull(message = "INVALID_PARAMETER_IS_NULL: The phone must be not null.")
            @Phone String phone,
            @NotNull(message = "INVALID_PARAMETER_IS_NULL: The authCode must be not null.")
            @SmsAuthCode String authCode);

}
