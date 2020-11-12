package com.xiaohuashifu.recruit.authentication.service.handler;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 描述：认证失败处理
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/10 18:57
 */
//@Component
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {

    public MyAuthenticationFailureHandler() {
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(exception.getMessage());
    }
}
