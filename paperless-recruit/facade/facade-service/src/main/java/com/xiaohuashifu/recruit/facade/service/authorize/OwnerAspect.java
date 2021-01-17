package com.xiaohuashifu.recruit.facade.service.authorize;

import com.xiaohuashifu.recruit.common.util.SpELUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述：验证是否是资源拥有者切面，配合 {@link Owner} 进行判断
 *
 * @author xhsf
 * @create 2021-01-17 21:16
 */
@Aspect
@Component
public class OwnerAspect {

    /**
     * 上下文列表
     */
    private final Map<Class<? extends Context>, Context> contexts;

    public OwnerAspect(List<Context> contexts) {
        this.contexts = new HashMap<>(contexts.size());
        for (Context context : contexts) {
            this.contexts.put(context.getClass(), context);
        }
    }

    /**
     * 判断是否属于资源拥有者
     *
     * @param joinPoint ProceedingJoinPoint
     */
    @Before("@annotation(owner)")
    public void isOwner(JoinPoint joinPoint, Owner owner) {
        String expressionValue = SpELUtils.getExpressionValue(owner.id(), joinPoint, null);
        Long id = Long.valueOf(expressionValue);
        Context context = contexts.get(owner.context());
        context.isOwner(id);
    }

}
