package com.xiaohuashifu.recruit.authentication.service.controller;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.KeyPair;
import java.security.Principal;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

/**
 * 描述：认证相关的公开接口
 *
 * @author: xhsf
 * @create: 2020/11/10 16:00
 */
@RestController
@RequestMapping("/oauth")
public class AuthenticationController {

    private final TokenEndpoint tokenEndpoint;

    private final KeyPair keyPair;

    public AuthenticationController(TokenEndpoint tokenEndpoint, KeyPair keyPair) {
        this.tokenEndpoint = tokenEndpoint;
        this.keyPair = keyPair;
    }

    /**
     * 获得解析 JWT 的公钥
     *
     * @return Map<String, Object>
     */
    @GetMapping("/rsa/public-key")
    public Object getKey() {
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAKey key = new RSAKey.Builder(publicKey).build();
        return new JWKSet(key).toJSONObject();
    }

    /**
     * 解析 JWT
     *
     * @param request HttpServletRequest
     * @return 解析后的 JWT 对象
     */
    @GetMapping("/jwt")
    public Object getJwt(HttpServletRequest request) {
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        String authorization = request.getHeader("Authorization");
        return Jwts.parser().setSigningKey(publicKey)
                .parseClaimsJws(StringUtils.substringAfter(authorization, "bearer "));
    }

    /**
     * 对认证请求进行拦截
     *
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
