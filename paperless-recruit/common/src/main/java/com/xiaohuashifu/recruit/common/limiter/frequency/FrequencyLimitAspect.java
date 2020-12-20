package com.xiaohuashifu.recruit.common.limiter.frequency;

import com.xiaohuashifu.recruit.common.result.ErrorCodeEnum;
import com.xiaohuashifu.recruit.common.result.Result;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.core.TimeoutUtils;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.scheduling.support.CronSequenceGenerator;

import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.*;

/**
 * 描述：限频切面，配合 {@link FrequencyLimit} 可以便捷的使用限频
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

    /**
     * EL 表达式解析器
     */
    private static final ExpressionParser expressionParser = new SpelExpressionParser();

    /**
     * cron 表达式的 CronSequenceGenerator 的缓存
     */
    private final Map<String, CronSequenceGenerator> cronSequenceGeneratorMap = new HashMap<>();

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
    @Around("@annotation(FrequencyLimits) || @annotation(FrequencyLimit)")
    public Object isAllowed(ProceedingJoinPoint joinPoint) throws Throwable {
        FrequencyLimit[] frequencyLimits = getFrequencyLimits(joinPoint);
        int notAllowIndex = isAllowed(joinPoint, frequencyLimits);
        if (notAllowIndex != -1) {
            System.out.println(frequencyLimits[notAllowIndex].errorMessage());
            String errorMessageExpression = frequencyLimits[notAllowIndex].errorMessage();
            String errorMessage = getExpressionValue(errorMessageExpression, joinPoint);
            return Result.fail(ErrorCodeEnum.TOO_MANY_REQUESTS, errorMessage);
        }

        // 执行业务逻辑
        return joinPoint.proceed();
    }

    /**
     * 是否允许
     *
     * @param joinPoint ProceedingJoinPoint
     * @param frequencyLimits FrequencyLimit[]
     * @return 是否允许，-1表示允许，其他表示获取失败时的下标
     */
    private int isAllowed(ProceedingJoinPoint joinPoint, FrequencyLimit[] frequencyLimits) {
        FrequencyLimiterType[] frequencyLimiterTypes = new FrequencyLimiterType[frequencyLimits.length];
        List<String> keys = new ArrayList<>(frequencyLimits.length);
        long[] frequencies = new long[frequencyLimits.length];
        long[] timeouts = new long[frequencyLimits.length];
        for (int i = 0; i < frequencyLimits.length; i++) {
            // 如果 cron 是空，表示是 RANGE_REFRESH 类型的限频
            if (frequencyLimits[i].cron().equals("")) {
                frequencyLimiterTypes[i] = FrequencyLimiterType.RANGE_REFRESH;
                timeouts[i] = TimeoutUtils.toMillis(frequencyLimits[i].refreshTime(), frequencyLimits[i].timeUnit());
            }
            // 否则是 FIXED_POINT_REFRESH 类型的限频，需要解析 cron
            else {
                frequencyLimiterTypes[i] = FrequencyLimiterType.FIXED_POINT_REFRESH;
                String cron = frequencyLimits[i].cron();
                CronSequenceGenerator cronSequenceGenerator = cronSequenceGeneratorMap.getOrDefault(
                        cron, cronSequenceGeneratorMap.put(cron, new CronSequenceGenerator(cron)));
                Date now = new Date();
                Date next = cronSequenceGenerator.next(now);
                timeouts[i] = next.getTime() - now.getTime();
            }
            keys.add(getKey(joinPoint, frequencyLimits[i]));
            frequencies[i] = frequencyLimits[i].frequency();
        }

        // 判断是否允许
        return frequencyLimiter.isAllowed(frequencyLimiterTypes, keys, frequencies, timeouts);
    }

    /**
     * 获取限频注解列表
     *
     * @param joinPoint ProceedingJoinPoint
     * @return FrequencyLimit[]
     */
    private FrequencyLimit[] getFrequencyLimits(ProceedingJoinPoint joinPoint) {
        Method method;
        try {
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            method = joinPoint.getTarget()
                    .getClass()
                    .getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
            return method.getAnnotationsByType(FrequencyLimit.class);
        } catch (NoSuchMethodException ignored) {
        }
        return new FrequencyLimit[]{};
    }

    /**
     * 获取限频的键
     *
     * @param joinPoint ProceedingJoinPoint
     * @param frequencyLimit FrequencyLimit
     * @return 限频键
     */
    private String getKey(ProceedingJoinPoint joinPoint, FrequencyLimit frequencyLimit) {
        // 获得键表达式模式
        String keyExpressionPattern = frequencyLimit.value();
        // 获取参数
        Object[] parameters = frequencyLimit.parameters();
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
