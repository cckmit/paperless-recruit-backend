package com.xiaohuashifu.recruit.facade.service.authorize;

import com.xiaohuashifu.recruit.facade.service.exception.ForbiddenException;
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
public class UserContext implements Context {

    /**
     * 获取用户主体的编号
     *
     * @return 用户主体编号
     */
    public Long getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return Long.valueOf(String.valueOf(authentication.getPrincipal()));
    }

    /**
     * 验证是不是该用户的拥有者
     *
     * @param userId 用户编号
     */
    @Override
    public void isOwner(Long userId) {
        if (!Objects.equals(userId, getUserId())) {
            throw new ForbiddenException("Forbidden");
        }
    }

}
