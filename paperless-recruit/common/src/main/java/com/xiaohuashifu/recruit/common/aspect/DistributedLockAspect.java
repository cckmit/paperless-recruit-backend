package com.xiaohuashifu.recruit.common.aspect;

import com.xiaohuashifu.recruit.common.aspect.annotation.DistributedLock;
import com.xiaohuashifu.recruit.common.exception.OperationConflictServiceException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.CodeSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * 描述：分布式锁切面，配合 {@link DistributedLock} 可以便捷的使用分布式锁
 *
 * @author xhsf
 * @create 2020/12/10 21:10
 */
@Aspect
public class DistributedLockAspect {

    private final RedissonClient redissonClient;

    /**
     * EL 表达式解析器
     */
    private static final ExpressionParser expressionParser = new SpelExpressionParser();

    public DistributedLockAspect(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    /**
     * 给方法添加分布式锁
     *
     * @errorCode OperationConflict.Lock: 获取锁失败
     *
     * @param joinPoint ProceedingJoinPoint
     * @throws OperationConflictServiceException 获取锁失败
     * @return Object
     */
    @Around("@annotation(distributedLock)")
    public Object lock(ProceedingJoinPoint joinPoint, DistributedLock distributedLock) throws Throwable {
        // 获取键
        String key = getKey(joinPoint, distributedLock);

        // 构造锁
        RLock lock = redissonClient.getLock(key);

        // 尝试获取锁
        if (!tryLock(lock, distributedLock)) {
            String errorMessageExpression = distributedLock.errorMessage();
            String errorMessage = getExpressionValue(errorMessageExpression, joinPoint);
            throw new OperationConflictServiceException(errorMessage);
        }

        // 执行业务逻辑
        try {
            return joinPoint.proceed();
        } finally {
            // 释放锁
            unlock(lock);
        }
    }

    /**
     * 获取锁定的键
     *
     * @param joinPoint ProceedingJoinPoint
     * @param distributedLock DistributedLock
     * @return 锁定键
     */
    private String getKey(ProceedingJoinPoint joinPoint, DistributedLock distributedLock) {
        // 获得键表达式模式
        String keyExpressionPattern = distributedLock.value();
        // 获取参数
        Object[] parameters = distributedLock.parameters();
        // 构造键表达式
        String keyExpression = keyExpressionPattern;
        // 若有参数则需要填充参数
        if (parameters.length > 0) {
            keyExpression = MessageFormat.format(keyExpressionPattern, parameters);
        }
        // 获取键
        return getExpressionValue(keyExpression, joinPoint);
    }

    /**
     * 获取表达式的值
     *
     * @param expression 表达式
     * @param joinPoint ProceedingJoinPoint
     * @return value
     */
    private String getExpressionValue(String expression, ProceedingJoinPoint joinPoint) {
        // 获得方法参数的 Map
        String[] parameterNames = ((CodeSignature) joinPoint.getSignature()).getParameterNames();
        Object[] parameterValues = joinPoint.getArgs();
        Map<String, Object> parameterMap = new HashMap<>();
        for (int i = 0; i < parameterNames.length; i++) {
            parameterMap.put(parameterNames[i], parameterValues[i]);
        }

        // 解析 EL 表达式
        return getExpressionValue(expression, parameterMap);
    }

    /**
     * 获取锁
     *
     * @param lock 锁
     * @param distributedLock DistributedLock
     * @return 获取结果
     */
    private boolean tryLock(Lock lock, DistributedLock distributedLock) throws InterruptedException {
        // 判断是否需要设置超时时间
        long expirationTime = distributedLock.expirationTime();
        if (expirationTime > 0) {
            TimeUnit timeUnit = distributedLock.timeUnit();
            return lock.tryLock(expirationTime, timeUnit);
        }
        return lock.tryLock();
    }

    /**
     * 释放锁
     *
     * @param lock 锁
     */
    private void unlock(Lock lock) {
        lock.unlock();
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
