package com.xiaohuashifu.recruit.authentication.service.controller;

import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.Map;

/**
 * 描述：
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/10 16:00
 */
@RestController
@RequestMapping("/oauth")
public class AuthenticationController {

    @Value("${jwt.signingKey}")
    private String signingKey;

    private final TokenEndpoint tokenEndpoint;

    public AuthenticationController(TokenEndpoint tokenEndpoint) {
        this.tokenEndpoint = tokenEndpoint;
    }

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

    /**
     * 对认证请求进行拦截
     * @param principal 主体
     * @param parameters 请求参数
     * @return ResponseEntity<OAuth2AccessToken>
     * @throws HttpRequestMethodNotSupportedException .
     */
    @PostMapping("/token")
    public Object postAccessToken(Principal principal, @RequestBody Map<String, String> parameters)
            throws HttpRequestMethodNotSupportedException {
        return tokenEndpoint.postAccessToken(principal, parameters).getBody();
    }

}
