package com.xiaohuashifu.recruit.facade.service.authorize;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

/**
 * 描述：为需要 UserInfo 的地方插入 UserInfo
 *
 * @author xhsf
 * @create 2021/1/9 12:18
 */
@Aspect
@Component
public class UserInfoAspect {

    @Before(value = "within(com.xiaohuashifu.recruit.facade.service.controller.v1.*)")
    public void userInfo(JoinPoint joinPoint) {
        // 判断该方法参数是否带有 UserInfo，有的话帮忙插入需要的参数
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof UserInfo) {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if (authentication instanceof OAuth2Authentication) {
                    OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
                    LinkedHashMap<String, Object> decodedDetails =
                            (LinkedHashMap<String, Object>) details.getDecodedDetails();
                    LinkedHashMap<String, Object> userInfo =
                            (LinkedHashMap<String, Object>) decodedDetails.get("userInfo");
                    ((UserInfo) arg).setId(Long.valueOf(String.valueOf(userInfo.get("id"))));
                    ((UserInfo) arg).setUsername((String) userInfo.get("username"));
                    ((UserInfo) arg).setPhone((String) userInfo.get("phone"));
                    ((UserInfo) arg).setEmail((String) userInfo.get("email"));
                    ((UserInfo) arg).setAvailable((Boolean) userInfo.get("available"));
                }
                break;
            }
        }
    }

}
