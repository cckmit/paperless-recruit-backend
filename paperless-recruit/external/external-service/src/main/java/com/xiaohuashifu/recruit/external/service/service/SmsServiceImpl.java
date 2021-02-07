package com.xiaohuashifu.recruit.external.service.service;

import com.xiaohuashifu.recruit.common.exception.NotFoundServiceException;
import com.xiaohuashifu.recruit.common.exception.unprocessable.IncorrectValueServiceException;
import com.xiaohuashifu.recruit.common.util.AuthCodeUtils;
import com.xiaohuashifu.recruit.external.api.request.CheckSmsAuthCodeRequest;
import com.xiaohuashifu.recruit.external.api.request.CreateAndSendSmsAuthCodeRequest;
import com.xiaohuashifu.recruit.external.api.service.SmsService;
import com.xiaohuashifu.recruit.external.service.manager.SmsManager;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 描述：发送短信服务
 *
 * @author: xhsf
 * @create: 2020/11/11 15:22
 */
@Service
public class SmsServiceImpl implements SmsService {

    private final StringRedisTemplate redisTemplate;

    private final SmsManager smsManager;

    /**
     * 短信验证码的 Redis key 前缀
     * 推荐格式为 SMS_AUTH_CODE_REDIS_PREFIX:{subject}:{phone}
     */
    private static final String SMS_AUTH_CODE_REDIS_PREFIX = "sms:auth-code";

    public SmsServiceImpl(StringRedisTemplate redisTemplate, SmsManager smsManager) {
        this.redisTemplate = redisTemplate;
        this.smsManager = smsManager;
    }

    @Override
    public String createAndSendSmsAuthCode(CreateAndSendSmsAuthCodeRequest request) {
        // 发送短信验证码到手机
        String authCode = AuthCodeUtils.randomAuthCode();
        smsManager.sendSmsAuthCode(request.getPhone(), authCode);

        // 添加短信验证码到缓存
        String redisKey = SMS_AUTH_CODE_REDIS_PREFIX + ":" + request.getSubject() + ":" + request.getPhone();
        redisTemplate.opsForValue().set(redisKey, authCode, request.getExpirationTime(), TimeUnit.MINUTES);
        return authCode;
    }

    @Override
    public void checkSmsAuthCode(CheckSmsAuthCodeRequest request) {
        // 从缓存取出验证码
        String redisKey = SMS_AUTH_CODE_REDIS_PREFIX + ":" + request.getSubject() + ":" + request.getPhone();
        String authCode = redisTemplate.opsForValue().get(redisKey);

        // 验证码不存在
        if (authCode == null) {
            throw new NotFoundServiceException("Auth code does not exists.");
        }

        // 验证码不正确
        if (!Objects.equals(authCode, request.getAuthCode())) {
            throw new IncorrectValueServiceException("Auth code is incorrect.");
        }

        // 验证通过，如果需要删除验证码，则删除
        if (request.getDelete()) {
            redisTemplate.delete(redisKey);
        }
    }

}
