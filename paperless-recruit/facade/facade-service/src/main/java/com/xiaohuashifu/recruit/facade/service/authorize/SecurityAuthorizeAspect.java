package com.xiaohuashifu.recruit.facade.service.authorize;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * 描述：SecurityAuthorize 的切面
 *      用于把 SecurityAuthorize 注入到需要该对象的方法里
 *      只需要在方法添加 SecurityAuthorize 参数即可
 *
 * @author xhsf
 * @create 2020/11/29 13:23
 */
@Aspect
@Component
public class SecurityAuthorizeAspect {
    private final RoleHierarchy roleHierarchy;

    public SecurityAuthorizeAspect(RoleHierarchy roleHierarchy) {
        this.roleHierarchy = roleHierarchy;
    }

    @Before(value = "within(com.xiaohuashifu.recruit.facade.service.controller.v1.*)")
    public void securityAuthorize(JoinPoint joinPoint) {
        // 判断该方法参数是否带有 SecurityAuthorize，有的话帮忙插入需要的参数
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof SecurityAuthorize) {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                ((SecurityAuthorize) arg).setAuthentication(authentication);
                ((SecurityAuthorize) arg).setRoleHierarchy(roleHierarchy);
                break;
            }
        }
    }
}
