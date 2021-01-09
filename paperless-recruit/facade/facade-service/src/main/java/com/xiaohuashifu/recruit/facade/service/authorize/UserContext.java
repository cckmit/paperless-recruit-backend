package com.xiaohuashifu.recruit.facade.service.authorize;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 描述：用户上下文
 *
 * @author xhsf
 * @create 2021/1/9 12:16
 */
@Component
public class UserContext {

    /**
     * 获取用户主体的编号
     *
     * @return 用户主体编号
     */
    public Long getId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return Long.valueOf(String.valueOf(authentication.getPrincipal()));
    }

    public boolean authenticatePrincipal(Object principal) {
        return Objects.equals(principal, getId());
    }

}
