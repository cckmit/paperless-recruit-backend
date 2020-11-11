package com.xiaohuashifu.recruit.authentication.service.service;

import com.xiaohuashifu.recruit.authentication.api.dto.MessageAuthCodeLoginDTO;
import com.xiaohuashifu.recruit.authentication.api.service.PhoneLoginService;
import com.xiaohuashifu.recruit.authentication.service.service.constant.PhoneLoginServiceConstant;
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
 * 描述：
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
            // TODO: 2020/11/11 若短信发送不成功的处理逻辑
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


    @Override
    public Result<Void> checkMessageAuthCode(String phone, String authCode) {
        return null;
    }

    /**
     * 随机创建短信验证码，格式为6位数字
     * @return 短信验证码
     */
    private String createMessageAuthCode() {
        return RandomStringUtils.randomNumeric(6);
    }
}
