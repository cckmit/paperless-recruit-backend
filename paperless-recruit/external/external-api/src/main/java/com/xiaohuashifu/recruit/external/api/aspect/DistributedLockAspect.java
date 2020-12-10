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
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
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
     * EL 表达式解析器
     */
    private static final ExpressionParser expressionParser = new SpelExpressionParser();

    /**
     * 给方法添加分布式锁
     *
     * @param joinPoint ProceedingJoinPoint
     * @return Object
     */
    @Around("@annotation(com.xiaohuashifu.recruit.external.api.aspect.annotation.DistributedLock) " +
            "&& @annotation(distributedLock)")
    public Object handler(ProceedingJoinPoint joinPoint, DistributedLock distributedLock) throws Throwable {
        // 获得键
        String key = getKey(joinPoint, distributedLock);

        // 尝试获取锁
        if (!getLock(key, distributedLock)) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT, "Failed to acquire lock.");
        }

        // 执行业务逻辑
        try {
            return joinPoint.proceed();
        } finally {
            // 释放锁
            releaseLock(key, joinPoint);
        }
    }

    /**
     * 获取 key
     *
     * @param joinPoint ProceedingJoinPoint
     * @param distributedLock DistributedLock
     * @return key
     */
    private String getKey(ProceedingJoinPoint joinPoint, DistributedLock distributedLock) {
        // 获得方法参数的 Map
        String[] parameterNames = ((CodeSignature) joinPoint.getSignature()).getParameterNames();
        Object[] parameterValues = joinPoint.getArgs();
        Map<String, Object> parameterMap = new HashMap<>();
        for (int i = 0; i < parameterNames.length; i++) {
            parameterMap.put(parameterNames[i], parameterValues[i]);
        }

        // 解析 EL 表达式
        String key = distributedLock.value();
        return getExpressionValue(key, parameterMap);
    }

    /**
     * 获取锁
     *
     * @param key 键
     * @param distributedLock DistributedLock
     * @return 获取结果
     */
    private boolean getLock(String key, DistributedLock distributedLock) {
        // 判断是否需要设置超时时间
        long expirationTime = distributedLock.expirationTime();
        if (expirationTime > 0) {
            TimeUnit timeUnit = distributedLock.timeUnit();
            return distributedLockService.getLock(key, expirationTime, timeUnit).isSuccess();
        }
        return distributedLockService.getLock(key).isSuccess();
    }

    /**
     * 释放锁
     *
     * @param key 键
     * @param joinPoint ProceedingJoinPoint
     */
    private void releaseLock(String key, ProceedingJoinPoint joinPoint) {
        if (!distributedLockService.releaseLock(key).isSuccess()) {
            logger.error("Failed to release lock. key={}, signature={}, parameters={}",
                    key, joinPoint.getSignature(), Arrays.toString(joinPoint.getArgs()));
        }
    }

    /**
     * 获取 EL 表达式的值
     *
     * @param elExpression EL 表达式
     * @param parameterMap 参数名-值 Map
     * @return 表达式的值
     */
    private String getExpressionValue(String elExpression, Map<String, Object> parameterMap) {
        Expression expression = expressionParser.parseExpression(elExpression, new TemplateParserContext());
        EvaluationContext context = new StandardEvaluationContext();
        for (Map.Entry<String, Object> entry : parameterMap.entrySet()) {
            context.setVariable(entry.getKey(), entry.getValue());
        }
        return expression.getValue(context, String.class);
    }

}
