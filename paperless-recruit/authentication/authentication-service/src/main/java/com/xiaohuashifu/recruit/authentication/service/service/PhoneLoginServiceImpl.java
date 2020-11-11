package com.xiaohuashifu.recruit.authentication.service.service;

import com.xiaohuashifu.recruit.authentication.api.dto.MessageAuthCodeLoginDTO;
import com.xiaohuashifu.recruit.authentication.api.service.PhoneLoginService;
import com.xiaohuashifu.recruit.authentication.service.service.constant.PhoneLoginServiceConstant;
import com.xiaohuashifu.recruit.common.result.ErrorCode;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.external.api.dto.PhoneMessageDTO;
import com.xiaohuashifu.recruit.external.api.service.PhoneMessageService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * 描述：通过手机号码登录的RPC服务
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/11 16:13
 */
@Service
public class PhoneLoginServiceImpl implements PhoneLoginService {

    @Value("${phoneLoginService.messageAuthCodeExpiredTime}")
    private Long messageAuthCodeExpiredTime;

    @Reference
    private PhoneMessageService phoneMessageService;

    private final RedisTemplate<Object, Object> redisTemplate;

    public PhoneLoginServiceImpl(RedisTemplate<Object, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 手机号码+短信验证码登录的创建并发送手机短信验证码服务
     * 该服务会把短信验证码进行缓存，并在验证码失效后删除缓存
     *
     * @param phone 要发送验证码的手机号码
     * @return PhoneAuthCodeLoginDTO 该对象表示这次发送验证码的信息
     */
    @Override
    public Result<MessageAuthCodeLoginDTO> createMessageAuthCodeAndSend(String phone) {
        // 发送短信到进行登录的用户手机
        String messageAuthCode = createMessageAuthCode();
        String message = "【招新】您的验证码为：" + messageAuthCode + "。验证码有效时间为"
                + messageAuthCodeExpiredTime + "秒，请尽快使用。";
        final Result<Object> sendPhoneMessageResult =
                phoneMessageService.sendPhoneMessage(new PhoneMessageDTO(phone, message));
        if (!sendPhoneMessageResult.isSuccess()) {
            // TODO: 2020/11/11 发送短信失败错误逻辑
            return Result.fail(ErrorCode.INTERNAL_ERROR);
        }

        // 添加短信验证码到缓存
        String redisKey = PhoneLoginServiceConstant.MESSAGE_AUTH_CODE_REDIS_PREFIX + phone;
        redisTemplate.opsForValue().set(redisKey, messageAuthCode, messageAuthCodeExpiredTime, TimeUnit.SECONDS);

        return Result.success(new MessageAuthCodeLoginDTO.Builder()
                .phone(phone)
                .authCode(messageAuthCode)
                .message(message)
                .expireTime(LocalDateTime.now().plusSeconds(messageAuthCodeExpiredTime))
                .build());
    }

    /**
     * 手机号码+短信验证码登录的检验验证码是否有效的服务
     * 该服务检验成功后，会清除该验证码，即一个验证码只能使用一次
     *
     * @param phone 要验证的手机号码
     * @param authCode 短信验证码
     * @return Result<Void> 返回结果若Result.isSuccess()为true表示验证成功，否则验证失败
     */
    @Override
    public Result<Void> checkMessageAuthCode(String phone, String authCode) {
        // 从缓存取出验证码
        String redisKey = PhoneLoginServiceConstant.MESSAGE_AUTH_CODE_REDIS_PREFIX + phone;
        String messageAuthCode = (String) redisTemplate.opsForValue().get(redisKey);

        // 验证码不存在
        if (messageAuthCode == null) {
            return Result.fail(ErrorCode.INVALID_PARAMETER, "Auth code not exists.");
        }

        // 验证码不正确
        if (!messageAuthCode.equals(authCode)) {
            return Result.fail(ErrorCode.INVALID_PARAMETER, "Auth code error.");
        }

        // 验证通过，删除验证码
        redisTemplate.delete(redisKey);

        return Result.success();
    }

    /**
     * 随机创建短信验证码，格式为6位数字
     * @return 短信验证码
     */
    private String createMessageAuthCode() {
        return RandomStringUtils.randomNumeric(6);
    }
}
