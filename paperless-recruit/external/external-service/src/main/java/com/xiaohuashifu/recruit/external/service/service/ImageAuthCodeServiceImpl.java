package com.xiaohuashifu.recruit.external.service.service;

import com.xiaohuashifu.recruit.common.exception.NotFoundServiceException;
import com.xiaohuashifu.recruit.common.exception.unprocessable.IncorrectValueServiceException;
import com.xiaohuashifu.recruit.common.util.ImageAuthCodeUtils;
import com.xiaohuashifu.recruit.external.api.dto.ImageAuthCodeDTO;
import com.xiaohuashifu.recruit.external.api.request.CreateImageAuthCodeRequest;
import com.xiaohuashifu.recruit.external.api.service.ImageAuthCodeService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 描述：图形验证码服务
 *
 * @author: xhsf
 * @create: 2020/11/22 16:02
 */
@Service
public class ImageAuthCodeServiceImpl implements ImageAuthCodeService {

    private final StringRedisTemplate redisTemplate;

    /**
     * 图形验证码的 Redis key 前缀
     */
    private static final String IMAGE_AUTH_CODE_REDIS_KEY_PREFIX = "image-auth-code:auth-code";

    /**
     * 图形验证码自增 id Redis key，用于避免图形验证码编号重复
     */
    private static final String IMAGE_AUTH_CODE_INCREMENT_ID_REDIS_KEY = "image-auth-code:increment-id";

    public ImageAuthCodeServiceImpl(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public ImageAuthCodeDTO createImageAuthCode(CreateImageAuthCodeRequest request) {
        // 创建图形验证码
        ImageAuthCodeUtils.ImageAuthCode imageAuthCode = ImageAuthCodeUtils.createImageCode(
                request.getWidth(), request.getHeight(), request.getLength());

        // 创建图形验证码编号
        Long incrementId = redisTemplate.opsForValue().increment(IMAGE_AUTH_CODE_INCREMENT_ID_REDIS_KEY);
        String id = UUID.randomUUID().toString() + incrementId;

        // 添加验证码到缓存
        String redisKey = IMAGE_AUTH_CODE_REDIS_KEY_PREFIX + ":" + id;
        redisTemplate.opsForValue().set(redisKey, imageAuthCode.getAuthCode());
        redisTemplate.expire(redisKey, request.getExpirationTime(), TimeUnit.MINUTES);

        // 构造并返回
        return new ImageAuthCodeDTO(id, imageAuthCode.getBase64Image());
    }

    @Override
    public void checkImageAuthCode(String id, String authCode) {
        // 从缓存获取图形验证码
        String redisKey = IMAGE_AUTH_CODE_REDIS_KEY_PREFIX + ":" + id;
        String authCodeInRedis = redisTemplate.opsForValue().get(redisKey);
        // 验证码不存在
        if (authCodeInRedis == null) {
            throw new NotFoundServiceException("Auth code does not exist.");
        }

        // 删除验证码
        redisTemplate.delete(redisKey);

        // 判断验证码是否相同
        if (!Objects.equals(authCode, authCodeInRedis)) {
            throw new IncorrectValueServiceException("Auth code is incorrect.");
        }
    }

}
