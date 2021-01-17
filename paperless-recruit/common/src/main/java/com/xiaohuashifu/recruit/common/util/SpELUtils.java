package com.xiaohuashifu.recruit.common.util;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParserContext;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述：Spring Expression Language 工具类
 *
 * @author xhsf
 * @create 2020/12/22 19:14
 */
public class SpELUtils {

    /**
     * EL 表达式解析器
     */
    private static final ExpressionParser expressionParser = new SpelExpressionParser();

    /**
     * 获取表达式的值
     *
     * @param expression 表达式
     * @param joinPoint joinPoint
     * @param parserContext 解析器上下文
     * @return 表达式的值
     */
    public static String getExpressionValue(String expression, JoinPoint joinPoint, ParserContext parserContext) {
        // 获得方法参数的 Map
        String[] parameterNames = ((CodeSignature) joinPoint.getSignature()).getParameterNames();
        Object[] parameterValues = joinPoint.getArgs();
        Map<String, Object> parameterMap = new HashMap<>();
        for (int i = 0; i < parameterNames.length; i++) {
            parameterMap.put(parameterNames[i], parameterValues[i]);
        }

        // 解析 EL 表达式
        return getExpressionValue(expression, parameterMap, parserContext);
    }

    /**
     * 获取 EL 表达式的值
     *
     * @param elExpression EL 表达式
     * @param parameterMap 参数名-值 Map
     * @param parserContext 解析器上下文
     * @return 表达式的值
     */
    public static String getExpressionValue(String elExpression, Map<String, Object> parameterMap,
                                            ParserContext parserContext) {
        Expression expression;
        if (parserContext == null) {
            expression = expressionParser.parseExpression(elExpression);
        } else {
            expression = expressionParser.parseExpression(elExpression, parserContext);
        }
        EvaluationContext context = new StandardEvaluationContext();
        for (Map.Entry<String, Object> entry : parameterMap.entrySet()) {
            context.setVariable(entry.getKey(), entry.getValue());
        }
        return expression.getValue(context, String.class);
    }

}
