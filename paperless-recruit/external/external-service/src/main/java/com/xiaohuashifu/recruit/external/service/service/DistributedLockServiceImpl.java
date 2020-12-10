package com.xiaohuashifu.recruit.external.service.service;

import com.xiaohuashifu.recruit.common.result.ErrorCodeEnum;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.external.api.service.DistributedLockService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * 描述：分布式锁服务
 *
 * @author xhsf
 * @create 2020/12/10 19:13
 */
@Service
public class DistributedLockServiceImpl implements DistributedLockService {

    private final StringRedisTemplate redisTemplate;

    /**
     * 锁的 key 在 Redis 里的前缀
     */
    private static final String LOCK_KEY_REDIS_PREFIX = "distributed-lock:";

    public DistributedLockServiceImpl(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 获取分布式锁，不会自动释放锁
     *
     * @errorCode InvalidParameter: key 格式错误
     *              OperationConflict: 获取锁失败
     *
     * @param key 锁对应的唯一 key
     * @return 获取结果
     */
    @Override
    public Result<Void> getLock(String key) {
        String redisKey = LOCK_KEY_REDIS_PREFIX + key;
        if (Boolean.FALSE.equals(redisTemplate.opsForValue().setIfAbsent(redisKey, ""))) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT, "Failed to acquire lock.");
        }
        return Result.success();
    }

    /**
     * 获取分布式锁
     *
     * @errorCode InvalidParameter: key 或 expirationTime 格式错误
     *              OperationConflict: 获取锁失败
     *
     * @param key 锁对应的唯一 key
     * @param expirationTime 锁自动释放时间，单位秒
     * @return 获取结果
     */
    @Override
    public Result<Void> getLock(String key, Long expirationTime) {
        String redisKey = LOCK_KEY_REDIS_PREFIX + key;
        if (Boolean.FALSE.equals(
                redisTemplate.opsForValue().setIfAbsent(redisKey, "", expirationTime, TimeUnit.SECONDS))) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT, "Failed to acquire lock.");
        }
        return Result.success();
    }

    /**
     * 释放锁
     *
     * @errorCode InvalidParameter: key 格式错误
     *              InvalidParameter.NotExist: key 不存在
     *
     * @param key 锁对应的唯一 key
     * @return 释放结果
     */
    @Override
    public Result<Void> releaseLock(String key) {
        String redisKey = LOCK_KEY_REDIS_PREFIX + key;
        if (Boolean.FALSE.equals(redisTemplate.delete(redisKey))) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_EXIST, "The lock does not exist.");
        }
        return Result.success();
    }

}
