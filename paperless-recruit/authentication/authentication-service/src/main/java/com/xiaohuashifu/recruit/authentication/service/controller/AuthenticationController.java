package com.xiaohuashifu.recruit.authentication.service.controller;

import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

/**
 * 描述：
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/10 16:00
 */
@RestController("/authentications")
public class AuthenticationController {

    @Value("${jwt.signingKey}")
    private String signingKey;

    @GetMapping
    public Object get(Authentication authentication) {
        return authentication;
    }

    @GetMapping("/jwt")
    public Object getJwt(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        return Jwts.parser().setSigningKey(signingKey.getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(StringUtils.substringAfter(authorization, "bearer "));
    }

}
