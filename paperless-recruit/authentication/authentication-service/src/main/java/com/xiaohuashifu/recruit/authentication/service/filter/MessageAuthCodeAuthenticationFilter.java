package com.xiaohuashifu.recruit.authentication.service.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xiaohuashifu.recruit.authentication.service.pojo.token.MessageAuthCodeAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 描述：短信验证码登录过滤器，用于拦截通过短信验证码登录的请求
 *      拦截路径为/login/phone
 *      拦截方法为POST
 *      封装MessageAuthCodeAuthenticationToken给AuthenticationManager
 *      AuthenticationManager会将请求转发给接受MessageAuthCodeAuthenticationToken的Provider
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/11 19:38
 */
@Component
public class MessageAuthCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    /**
     * 拦截路径
     */
    private static final String INTERCEPT_PATH = "/login/phone";

    /**
     * 拦截方法
     */
    private static final String INTERCEPT_METHOD = "POST";

    /**
     * 手机号码在请求里的body里的JSON对象里的key
     */
    private static final String PHONE_KEY = "phone";

    /**
     * 短信验证码在请求里的body里的JSON对象里的key
     */
    private static final String MESSAGE_AUTH_CODE_KEY = "authCode";

    public MessageAuthCodeAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher(INTERCEPT_PATH, INTERCEPT_METHOD));
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }
        // 把请求里面的body解析
        String body = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);
        String phone = null;
        String authCode = null;
        if (StringUtils.hasText(body)) {
            JSONObject jsonObj = JSON.parseObject(body);
            phone = jsonObj.getString(PHONE_KEY);
            authCode = jsonObj.getString(MESSAGE_AUTH_CODE_KEY);
        }

        if (phone == null) {
            phone = "";
        }
        if (authCode == null) {
            authCode = "";
        }

        phone = phone.trim();
        // 封装成MessageAuthCodeAuthenticationToken对象，然后提交给AuthenticationManager
        MessageAuthCodeAuthenticationToken authRequest = new MessageAuthCodeAuthenticationToken(phone, authCode);
        return getAuthenticationManager().authenticate(authRequest);
    }

}
