package com.xiaohuashifu.recruit.common.limiter.frequency;

import com.xiaohuashifu.recruit.common.result.ErrorCodeEnum;
import com.xiaohuashifu.recruit.common.result.Result;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * 描述：限频切面，配合 {@link FrequencyLimit} 可以便捷的使用限频
 *
 * @author xhsf
 * @create 2020/12/18 21:10
 */
@Aspect
public class FrequencyLimitAspect {

    private final FrequencyLimiter frequencyLimiter;

    /**
     * EL 表达式解析器
     */
    private static final ExpressionParser expressionParser = new SpelExpressionParser();

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
    @Around("@annotation(frequencyLimit)")
    public Object handler(ProceedingJoinPoint joinPoint, FrequencyLimit frequencyLimit) throws Throwable {
        // 获取键
        String key = getKey(joinPoint, frequencyLimit);

        // 尝试获取 token
        boolean isAllowed = frequencyLimiter.isAllowed(
                key,frequencyLimit.frequency(), frequencyLimit.time(),frequencyLimit.unit());
        if (!isAllowed) {
            String errorMessageExpression = frequencyLimit.errorMessage();
            String errorMessage = getExpressionValue(errorMessageExpression, joinPoint);
            return Result.fail(ErrorCodeEnum.TOO_MANY_REQUESTS, errorMessage);
        }

        // 执行业务逻辑
        return joinPoint.proceed();
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
