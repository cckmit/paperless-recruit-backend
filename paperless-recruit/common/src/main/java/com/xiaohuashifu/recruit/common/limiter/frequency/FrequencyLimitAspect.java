package com.xiaohuashifu.recruit.common.limiter.frequency;

import com.xiaohuashifu.recruit.common.util.CronUtils;
import com.xiaohuashifu.recruit.common.util.SpELUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.core.TimeoutUtils;
import org.springframework.expression.common.TemplateParserContext;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.*;

/**
 * 描述：限频切面，配合 {@link FixedDelayRefreshFrequencyLimit}、{@link FixedPointRefreshFrequencyLimit}、
 *                  {@link RangeRefreshFrequencyLimit} 可以便捷的使用限频
 *
 * @author xhsf
 * @create 2020/12/18 21:10
 */
@Aspect
public class FrequencyLimitAspect {

    /**
     * 限频器
     */
    private final FrequencyLimiter frequencyLimiter;

    public FrequencyLimitAspect(FrequencyLimiter frequencyLimiter) {
        this.frequencyLimiter = frequencyLimiter;
    }

    /**
     * 限制请求频率
     *
     * @errorCode TooManyRequests: 请求太频繁
     *
     * @param joinPoint ProceedingJoinPoint
     * @return Object
     */
    @Around("@annotation(FixedDelayRefreshFrequencyLimit.List) || @annotation(FixedDelayRefreshFrequencyLimit) " +
            "|| @annotation(FixedPointRefreshFrequencyLimit.List) || @annotation(FixedPointRefreshFrequencyLimit) " +
            "|| @annotation(RangeRefreshFrequencyLimit.List) || @annotation(RangeRefreshFrequencyLimit)")
    public Object isAllowed(ProceedingJoinPoint joinPoint) throws Throwable {
        List<Annotation> frequencyLimits = getFrequencyLimits(joinPoint);
        int notAllowIndex = isAllowed(joinPoint, frequencyLimits);
        if (notAllowIndex != -1) {
            String errorMessage = getErrorMessage(joinPoint, frequencyLimits.get(notAllowIndex));
            throw new TooManyListenersException(errorMessage);
        }

        // 执行业务逻辑
        return joinPoint.proceed();
    }

    /**
     * 是否允许
     *
     * @param joinPoint ProceedingJoinPoint
     * @param frequencyLimits List<Annotation>
     * @return 是否允许，-1表示允许，其他表示获取失败时的下标
     */
    private int isAllowed(ProceedingJoinPoint joinPoint, List<Annotation> frequencyLimits) {
        List<String> keys = new ArrayList<>(frequencyLimits.size());
        String[] args = new String[frequencyLimits.size() * 3 + 1];
        Date now = new Date();
        long currentTime = now.getTime();
        args[frequencyLimits.size() * 3] = String.valueOf(currentTime);
        for (int i = 0; i < frequencyLimits.size(); i++) {
            // 范围刷新限频
            if (frequencyLimits.get(i) instanceof RangeRefreshFrequencyLimit) {
                RangeRefreshFrequencyLimit rangeRefreshFrequencyLimit =
                        (RangeRefreshFrequencyLimit) frequencyLimits.get(i);
                keys.add(getKey(joinPoint, rangeRefreshFrequencyLimit.key(), rangeRefreshFrequencyLimit.parameters()));
                args[i * 3] = String.valueOf(rangeRefreshFrequencyLimit.frequency());
                args[i * 3 + 1] = String.valueOf(TimeoutUtils.toMillis(rangeRefreshFrequencyLimit.refreshTime(),
                        rangeRefreshFrequencyLimit.timeUnit()));
                args[i * 3 + 2] = FrequencyLimitType.RANGE_REFRESH.name();
            }
            // 固定时间点刷新限频
            else if (frequencyLimits.get(i) instanceof FixedPointRefreshFrequencyLimit) {
                FixedPointRefreshFrequencyLimit fixedPointRefreshFrequencyLimit =
                        (FixedPointRefreshFrequencyLimit) frequencyLimits.get(i);
                keys.add(getKey(joinPoint, fixedPointRefreshFrequencyLimit.key(),
                        fixedPointRefreshFrequencyLimit.parameters()));
                args[i * 3] = String.valueOf(fixedPointRefreshFrequencyLimit.frequency());
                args[i * 3 + 1] = String.valueOf(CronUtils.next(fixedPointRefreshFrequencyLimit.cron(), now).getTime());
                args[i * 3 + 2] = FrequencyLimitType.FIXED_POINT_REFRESH.name();
            }
            // 固定延迟刷新限频
            else {
                FixedDelayRefreshFrequencyLimit fixedDelayRefreshFrequencyLimit =
                        (FixedDelayRefreshFrequencyLimit) frequencyLimits.get(i);
                keys.add(getKey(joinPoint, fixedDelayRefreshFrequencyLimit.key(),
                        fixedDelayRefreshFrequencyLimit.parameters()));
                args[i * 3] = String.valueOf(fixedDelayRefreshFrequencyLimit.frequency());
                args[i * 3 + 1] = String.valueOf(TimeoutUtils.toMillis(fixedDelayRefreshFrequencyLimit.delayTime(),
                        fixedDelayRefreshFrequencyLimit.timeUnit()));
                args[i * 3 + 2] = FrequencyLimitType.FIXED_DELAY_REFRESH.name();
            }
        }

        // 判断是否允许
        return frequencyLimiter.isAllowed(keys, args);
    }

    /**
     * 获取限频注解列表
     *
     * @param joinPoint ProceedingJoinPoint
     * @return List<Annotation>
     */
    private List<Annotation> getFrequencyLimits(ProceedingJoinPoint joinPoint) {
        Method method;
        try {
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            method = joinPoint.getTarget()
                    .getClass()
                    .getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
            Annotation[] rangeRefreshFrequencyLimits =
                    method.getAnnotationsByType(RangeRefreshFrequencyLimit.class);
            Annotation[] fixedPointRefreshFrequencyLimits =
                    method.getAnnotationsByType(FixedPointRefreshFrequencyLimit.class);
            Annotation[] fixedDelayRefreshFrequencyLimits =
                    method.getAnnotationsByType(FixedDelayRefreshFrequencyLimit.class);
            List<Annotation> frequencyLimits = new ArrayList<>(rangeRefreshFrequencyLimits.length
                    + fixedPointRefreshFrequencyLimits.length + fixedDelayRefreshFrequencyLimits.length);
            frequencyLimits.addAll(Arrays.asList(rangeRefreshFrequencyLimits));
            frequencyLimits.addAll(Arrays.asList(fixedPointRefreshFrequencyLimits));
            frequencyLimits.addAll(Arrays.asList(fixedDelayRefreshFrequencyLimits));
            return frequencyLimits;
        } catch (NoSuchMethodException ignored) {
        }
        return new ArrayList<>();
    }

    /**
     * 获取错误信息
     *
     * @param joinPoint ProceedingJoinPoint
     * @param frequencyLimit frequencyLimit
     * @return 错误信息
     */
    private String getErrorMessage(ProceedingJoinPoint joinPoint, Annotation frequencyLimit) {
        String errorMessageExpression;
        if (frequencyLimit instanceof RangeRefreshFrequencyLimit) {
            errorMessageExpression = ((RangeRefreshFrequencyLimit) frequencyLimit).errorMessage();
        } else if (frequencyLimit instanceof FixedPointRefreshFrequencyLimit) {
            errorMessageExpression = ((FixedPointRefreshFrequencyLimit) frequencyLimit).errorMessage();
        } else {
            errorMessageExpression = ((FixedDelayRefreshFrequencyLimit) frequencyLimit).errorMessage();
        }
        return SpELUtils.getExpressionValue(errorMessageExpression, joinPoint, new TemplateParserContext());
    }

    /**
     * 获取限频的键
     *
     * @param joinPoint ProceedingJoinPoint
     * @param key Key
     * @param parameters parameters
     * @return 限频键
     */
    private String getKey(ProceedingJoinPoint joinPoint, String key, Object[] parameters) {
        // 构造键表达式
        String keyExpression = key;
        // 若有参数则需要填充参数
        if (parameters.length > 0) {
            keyExpression = MessageFormat.format(key, parameters);
        }
        // 获取键
        return SpELUtils.getExpressionValue(keyExpression, joinPoint, new TemplateParserContext());
    }

}
