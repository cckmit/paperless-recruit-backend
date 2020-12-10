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

import java.util.Objects;

/**
 * 描述：分布式锁切面
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
        // 构造 lockKey
        String lockKey = null;
        String keyParameterName = distributedLock.keyParameterName();
        String[] parameterNames = ((CodeSignature) joinPoint.getSignature()).getParameterNames();
        for (int i = 0; i < parameterNames.length; i++) {
            if (Objects.equals(keyParameterName, parameterNames[i])) {
                String keyPrefix = distributedLock.keyPrefix();
                Object[] args = joinPoint.getArgs();
                lockKey = keyPrefix + args[i];
                break;
            }
        }

        // 参数错误
        if (lockKey == null) {
            throw new IllegalArgumentException("The keyParameterName " + keyParameterName + " not exist.");
        }

        // 尝试获取锁
        long expirationTime = distributedLock.expirationTime();
        Boolean success;
        if (expirationTime > 0) {
            success = distributedLockService.getLock(lockKey, expirationTime).isSuccess();
        } else {
            success = distributedLockService.getLock(lockKey).isSuccess();
        }
        if (!success) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT, "Failed to acquire lock.");
        }

        // 执行业务逻辑
        Object ret = joinPoint.proceed();

        // 释放锁
        if (!distributedLockService.releaseLock(lockKey).isSuccess()) {
            logger.error("Failed to release lock. lockKey={}", lockKey);
        }
        return ret;
    }

}
