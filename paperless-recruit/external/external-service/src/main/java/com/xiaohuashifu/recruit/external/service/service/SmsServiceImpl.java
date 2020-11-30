package com.xiaohuashifu.recruit.external.service.service;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.xiaohuashifu.recruit.common.result.ErrorCode;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.common.util.AuthCodeUtils;
import com.xiaohuashifu.recruit.external.api.dto.SmsAuthCodeDTO;
import com.xiaohuashifu.recruit.external.api.dto.SmsDTO;
import com.xiaohuashifu.recruit.external.api.service.SmsService;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * 描述：发送短信服务
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/11 15:22
 */
@Service
public class SmsServiceImpl implements SmsService {

    private static final Logger logger = LoggerFactory.getLogger(SmsServiceImpl.class);

    private final RedisTemplate<Object, Object> redisTemplate;

    @Value("${aliyun.access-key-id}")
    private String accessKeyId;

    @Value("${aliyun.access-key-secret}")
    private String accessKeySecret;

    public SmsServiceImpl(RedisTemplate<Object, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
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
     *              InternalError: 发送短信验证码错误，需要重试
     *
     * @param smsAuthCodeDTO 短信验证码对象
     * @return Result<Void> 返回结果若Result.isSuccess()为true表示发送成功，否则发送失败
     */
    @Override
    public Result<Void> createAndSendSmsAuthCode(SmsAuthCodeDTO smsAuthCodeDTO) {
        // 发送短信验证码到手机
        String authCode = AuthCodeUtils.randomAuthCode();
        try {
            sendSmsAuthCode(smsAuthCodeDTO.getPhone(), authCode);
        } catch (ClientException clientException) {
            logger.warn("Send sms auth code fail");
            return Result.fail(ErrorCode.INTERNAL_ERROR, "Send sms auth code failed.");
        }

        // 添加短信验证码到缓存
        String redisKey = SMS_AUTH_CODE_REDIS_PREFIX
                + ":" + smsAuthCodeDTO.getSubject() + ":" + smsAuthCodeDTO.getPhone();
        redisTemplate.opsForValue().set(redisKey, authCode, smsAuthCodeDTO.getExpiredTime(), TimeUnit.MINUTES);

        return Result.success();
    }

    /**
     * 短信验证码检验验证码是否有效的服务
     * 该服务检验成功后，可以清除该验证码，即一个验证码只能使用一次（SmsAuthCodeDTO.delete == true即可）
     *
     * @errorCode InvalidParameter: 需要正确的手机号码或验证码参数格式错误
     *              InvalidParameter.AuthCode.NotFound: 找不到对应手机号码的验证码，有可能已经过期或者没有发送成功
     *              InvalidParameter.AuthCode.Incorrect: 短信验证码值不正确
     *
     * @param smsAuthCodeDTO 短信验证码对象
     * @return Result<Void> 返回结果若Result.isSuccess()为true表示验证成功，否则验证失败
     */
    @Override
    public Result<Void> checkSmsAuthCode(SmsAuthCodeDTO smsAuthCodeDTO) {
        // 从缓存取出验证码
        String redisKey = SMS_AUTH_CODE_REDIS_PREFIX
                + ":" + smsAuthCodeDTO.getSubject() + ":" + smsAuthCodeDTO.getPhone();
        String authCode = (String) redisTemplate.opsForValue().get(redisKey);

        // 验证码不存在
        if (authCode == null) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_AUTH_CODE_NOT_FOUND, "Auth code does not exists.");
        }

        // 验证码不正确
        if (!authCode.equals(smsAuthCodeDTO.getAuthCode())) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_AUTH_CODE_INCORRECT, "Auth code is incorrect.");
        }

        // 验证通过，如果需要删除验证码，则删除
        if (smsAuthCodeDTO.getDelete()) {
            redisTemplate.delete(redisKey);
        }

        return Result.success();
    }

    /**
     * 发送短信验证码的具体逻辑
     *
     * @param phone 手机号码
     * @param authCode 验证码
     * @throws ClientException 发送短信验证码出错
     */
    private void sendSmsAuthCode(String phone, String authCode) throws ClientException {
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
        client.getCommonResponse(request);
    }

}
