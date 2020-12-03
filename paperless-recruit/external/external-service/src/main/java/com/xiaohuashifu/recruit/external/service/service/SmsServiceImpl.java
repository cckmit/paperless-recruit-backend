package com.xiaohuashifu.recruit.external.service.service;

import com.xiaohuashifu.recruit.common.result.ErrorCodeEnum;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.common.util.AuthCodeUtils;
import com.xiaohuashifu.recruit.external.api.dto.SmsAuthCodeDTO;
import com.xiaohuashifu.recruit.external.api.dto.SmsDTO;
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

    // TODO: 2020/11/19 通用短信接口未实现
    @Override
    public Result<Object> sendSms(SmsDTO smsDTO) {
        return Result.success("Send sms success.");
    }

    /**
     * 发送短信验证码服务
     * 该服务会把短信验证码进行缓存
     *
     * @errorCode InvalidParameter: 手机号码或主题或过期时间的格式错误
     *              UnknownError: 发送短信验证码错误，需要重试
     *
     * @param smsAuthCodeDTO 短信验证码对象
     * @return Result<Void> 返回结果若 Result.isSuccess() 为 true 表示发送成功，否则发送失败
     */
    @Override
    public Result<Void> createAndSendSmsAuthCode(SmsAuthCodeDTO smsAuthCodeDTO) {
        // 发送短信验证码到手机
        String authCode = AuthCodeUtils.randomAuthCode();
        if (!smsManager.sendSmsAuthCode(smsAuthCodeDTO.getPhone(), authCode)) {
            return Result.fail(ErrorCodeEnum.UNKNOWN_ERROR, "Send sms auth code failed.");
        }

        // 添加短信验证码到缓存
        String redisKey = SMS_AUTH_CODE_REDIS_PREFIX
                + ":" + smsAuthCodeDTO.getSubject() + ":" + smsAuthCodeDTO.getPhone();
        redisTemplate.opsForValue().set(redisKey, authCode, smsAuthCodeDTO.getExpiredTime(), TimeUnit.MINUTES);

        return Result.success();
    }

    /**
     * 短信验证码检验验证码是否有效的服务
     * 该服务检验成功后，可以清除该验证码，即一个验证码只能使用一次 （SmsAuthCodeDTO.delete == true 即可）
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *              InvalidParameter.AuthCode.NotExist: 找不到对应手机号码的验证码，有可能已经过期或者没有发送成功
     *              InvalidParameter.AuthCode.Incorrect: 短信验证码值不正确
     *
     * @param smsAuthCodeDTO 短信验证码对象
     * @return Result<Void> 返回结果若 Result.isSuccess() 为 true 表示验证成功，否则验证失败
     */
    @Override
    public Result<Void> checkSmsAuthCode(SmsAuthCodeDTO smsAuthCodeDTO) {
        // 从缓存取出验证码
        String redisKey = SMS_AUTH_CODE_REDIS_PREFIX
                + ":" + smsAuthCodeDTO.getSubject() + ":" + smsAuthCodeDTO.getPhone();
        String authCode = redisTemplate.opsForValue().get(redisKey);

        // 验证码不存在
        if (authCode == null) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_AUTH_CODE_NOT_EXIST,
                    "Auth code does not exists.");
        }

        // 验证码不正确
        if (!Objects.equals(authCode, smsAuthCodeDTO.getAuthCode())) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_AUTH_CODE_INCORRECT,
                    "Auth code is incorrect.");
        }

        // 验证通过，如果需要删除验证码，则删除
        if (smsAuthCodeDTO.getDelete()) {
            redisTemplate.delete(redisKey);
        }

        return Result.success();
    }

}
