package com.xiaohuashifu.recruit.external.api.aspect;

import com.xiaohuashifu.recruit.common.result.ErrorCodeEnum;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.external.api.aspect.annotation.DistributedLock;
import com.xiaohuashifu.recruit.external.api.service.DistributedLockService;
import org.apache.dubbo.config.annotation.Reference;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.CodeSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 描述：分布式锁切面，配合 {@link DistributedLock} 可以便捷的使用分布式锁
 *
 * @author xhsf
 * @create 2020/12/10 21:10
 */
@Aspect
public class DistributedLockAspect {

    private static final Logger logger = LoggerFactory.getLogger(DistributedLockAspect.class);

    @Reference
    private DistributedLockService distributedLockService;

    /**
     * 给方法添加分布式锁
     *
     * @param joinPoint ProceedingJoinPoint
     * @return Object
     */
    @Around("@annotation(com.xiaohuashifu.recruit.external.api.aspect.annotation.DistributedLock) " +
            "&& @annotation(distributedLock)")
    public Object handler(ProceedingJoinPoint joinPoint, DistributedLock distributedLock) throws Throwable {
        // 获得锁定键
        String lockKey = getLockKey(joinPoint, distributedLock);

        // 尝试获取锁
        if (!getLock(lockKey, distributedLock)) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT, "Failed to acquire lock.");
        }

        // 执行业务逻辑
        try {
            return joinPoint.proceed();
        } finally {
            // 释放锁
            releaseLock(lockKey, joinPoint);
        }
    }

    /**
     * 获取 lockKey
     *
     * @param joinPoint ProceedingJoinPoint
     * @param distributedLock DistributedLock
     * @return lockKey
     */
    private String getLockKey(ProceedingJoinPoint joinPoint, DistributedLock distributedLock) {
        // 若 distributedLock.key() 或者 distributedLock.value() 不为空，直接返回其值
        if (!distributedLock.key().isBlank()) {
            return distributedLock.key();
        }
        if (!distributedLock.value().isBlank()) {
            return distributedLock.value();
        }

        // 构造 lockKey
        String keyParameterName = distributedLock.keyParameterName();
        String[] parameterNames = ((CodeSignature) joinPoint.getSignature()).getParameterNames();
        for (int i = 0; i < parameterNames.length; i++) {
            if (Objects.equals(keyParameterName, parameterNames[i])) {
                String keyPrefix = distributedLock.keyPrefix();
                Object[] args = joinPoint.getArgs();
                return keyPrefix + args[i];
            }
        }

        // 参数错误
        throw new IllegalArgumentException("The keyParameterName " + keyParameterName + " not exist.");
    }

    /**
     * 获取锁
     *
     * @param lockKey 锁定键
     * @param distributedLock DistributedLock
     * @return 获取结果
     */
    private boolean getLock(String lockKey, DistributedLock distributedLock) {
        // 判断是否需要设置超时时间
        long expirationTime = distributedLock.expirationTime();
        TimeUnit timeUnit = distributedLock.timeUnit();
        if (expirationTime > 0) {
            return distributedLockService.getLock(lockKey, expirationTime, timeUnit).isSuccess();
        }
        return distributedLockService.getLock(lockKey).isSuccess();
    }

    /**
     * 释放锁
     *
     * @param lockKey 锁定键
     * @param joinPoint ProceedingJoinPoint
     */
    private void releaseLock(String lockKey, ProceedingJoinPoint joinPoint) {
        if (!distributedLockService.releaseLock(lockKey).isSuccess()) {
            logger.error("Failed to release lock. lockKey={}, signature={}, args={}",
                    lockKey, joinPoint.getSignature(), Arrays.toString(joinPoint.getArgs()));
        }
    }

}
