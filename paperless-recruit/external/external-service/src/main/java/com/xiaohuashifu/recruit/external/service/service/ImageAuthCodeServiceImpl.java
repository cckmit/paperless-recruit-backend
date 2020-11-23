package com.xiaohuashifu.recruit.external.service.service;

import com.xiaohuashifu.recruit.common.result.ErrorCode;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.common.util.ImageAuthCodeUtils;
import com.xiaohuashifu.recruit.external.api.dto.ImageAuthCodeDTO;
import com.xiaohuashifu.recruit.external.api.service.ImageAuthCodeService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;

import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 描述：图形验证码服务
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/22 16:02
 */
@Service
public class ImageAuthCodeServiceImpl implements ImageAuthCodeService {

    private final RedisTemplate<Object, Object> redisTemplate;

    /**
     * 图形验证码的Redis key前缀
     */
    private static final String IMAGE_AUTH_CODE_REDIS_KEY_PREFIX = "image-auth-code:auth-code";

    /**
     * 自增id Redis key前缀
     */
    private static final String INCREMENT_ID_REDIS_KEY_PREFIX = "image-auth-code:increment-id";

    /**
     * 自增id的脚本
     */
    private final RedisScript<Long> incrementIdRedisScript;

    public ImageAuthCodeServiceImpl(
            RedisTemplate<Object, Object> redisTemplate,
            @Qualifier("incrementIdRedisScript") RedisScript<Long> incrementIdRedisScript) {
        this.redisTemplate = redisTemplate;
        this.incrementIdRedisScript = incrementIdRedisScript;
    }

    /**
     * 创建图形验证码
     * 会把验证码缓存，可用通过checkImageAuthCode检查是否通过校验
     *
     * @param imageAuthCodeDTO ImageAuthCodeDTO
     * @return ImageAuthCodeDTO
     */
    @Override
    public Result<ImageAuthCodeDTO> createImageAuthCode(ImageAuthCodeDTO imageAuthCodeDTO) {
        // 创建图形验证码
        ImageAuthCodeUtils.ImageAuthCode imageAuthCode = ImageAuthCodeUtils.createImageCode(
                imageAuthCodeDTO.getWidth(), imageAuthCodeDTO.getHeight(), imageAuthCodeDTO.getLength());

        // 创建图形验证码编号
        Long incrementId = redisTemplate.execute(incrementIdRedisScript,
                Collections.singletonList(INCREMENT_ID_REDIS_KEY_PREFIX), "0");
        String id = UUID.randomUUID().toString() + incrementId;

        // 添加验证码到缓存
        String redisKey = IMAGE_AUTH_CODE_REDIS_KEY_PREFIX + ":" + id;
        redisTemplate.opsForValue().set(redisKey, imageAuthCode.getAuthCode());
        redisTemplate.expire(redisKey, imageAuthCodeDTO.getExpiredTime(), TimeUnit.MINUTES);

        // 构造并返回
        return Result.success(new ImageAuthCodeDTO.Builder()
                .id(id)
                .authCode(imageAuthCode.getBase64Image())
                .build());
    }

    /**
     * 校验验证码
     * 会从缓存读取验证码进行校验
     * 该接口不管校验是否通过都会删除缓存里的验证码
     * 即验证码只能进行一次校验（进行一次校验后即失效）
     *
     * @param id 图形验证码编号
     * @param authCode 用户输入的验证码字符串
     * @return 校验结果
     */
    @Override
    public Result<Void> checkImageAuthCode(String id, String authCode) {
        // 从缓存获取图形验证码并删除
        String redisKey = IMAGE_AUTH_CODE_REDIS_KEY_PREFIX + ":" + id;
        String authCodeInRedis = (String) redisTemplate.opsForValue().get(redisKey);
        redisTemplate.delete(redisKey);

        // 判断验证码是否相同
        if (!authCode.equals(authCodeInRedis)) {
            return Result.fail(ErrorCode.INVALID_PARAMETER);
        }
        return Result.success();
    }

}
