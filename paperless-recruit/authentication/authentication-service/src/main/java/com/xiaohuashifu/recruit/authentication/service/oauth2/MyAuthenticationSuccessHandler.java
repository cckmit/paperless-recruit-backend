package com.xiaohuashifu.recruit.authentication.service.oauth2;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;

/**
 * 描述：Spring Security框架认证成功的处理器
 *      该处理器实现了Spring Security OAuth2的令牌生成过程
 *      因此可以通过Spring Security认证生成令牌
 *      也就是把Spring Security OAuth2与Spring Security结合
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/10 16:30
 */
@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private static final Logger logger = LoggerFactory.getLogger(MyAuthenticationSuccessHandler.class);

    private final ClientDetailsService clientDetailsService;

    private final AuthorizationServerTokenServices authorizationServerTokenServices;

    private final ObjectMapper objectMapper;

    private final PasswordEncoder passwordEncoder;

    public MyAuthenticationSuccessHandler(AuthorizationServerTokenServices authorizationServerTokenServices,
                                          ClientDetailsService clientDetailsService, ObjectMapper objectMapper,
                                          PasswordEncoder passwordEncoder) {
        this.authorizationServerTokenServices = authorizationServerTokenServices;
        this.clientDetailsService = clientDetailsService;
        this.objectMapper = objectMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        String[] clientIdAndClientSecret = parseAuthorization(request.getHeader("Authorization"));
        String clientId = clientIdAndClientSecret[0];
        String clientSecret = clientIdAndClientSecret[1];

        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);
        if (clientDetails == null) {
            throw new UnapprovedClientAuthenticationException("ClientId not found.");
        }
        if (!passwordEncoder.matches(clientSecret, clientDetails.getClientSecret())) {
            throw new UnapprovedClientAuthenticationException("Error clientSecret.");
        }
        // 构造TokenRequest，其中grantType为custom表示自定义类型
        TokenRequest tokenRequest =
                new TokenRequest(new HashMap<>(), clientId, clientDetails.getScope(), "custom");

        // 升级成OAuth2Request
        final OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);

        // 构造OAuth2Authentication
        final OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);

        // 获取OAuth2AccessToken，也就是发送POST /oauth/token的返回结果
        // 格式为
        // {
        //    "access_token": "ff792587-3657-4212-81ab-915b5a616d71",
        //    "token_type": "bearer",
        //    "refresh_token": "c1d28699-daf5-498a-9268-6180d00aeec8",
        //    "expires_in": 43199,
        //    "scope": "all"
        //}
        final OAuth2AccessToken accessToken = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);
        logger.info(authentication.getName(), authentication.getCredentials());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(accessToken));
    }

    /**
     * 解析Http header Authorization
     *
     * @param authorization Authorization默认格式为Basic {clientId}:{clientSecret}
     *                      若不满足该格式则报错
     * @return 返回的数组格式为 [clientId],[clientSecret]
     */
    private String[] parseAuthorization(String authorization) {
        // 无Authorization
        if (authorization == null) {
            throw new UnapprovedClientAuthenticationException("Http header of Authorization not found.");
        }

        // Authorization格式错误
        if (!authorization.startsWith("Basic ")) {
            throw new BadCredentialsException("The format of HTTP header Authorization error. " +
                    "The correct format is 'Basic {clientId}:{clientSecret}' with base64 encode.");
        }

        // 从Base64解码
        final byte[] clientIdAndClientSecretBase64Bytes = authorization.substring(6).getBytes(StandardCharsets.UTF_8);
        byte[] clientIdAndClientSecretBytes;
        try {
            clientIdAndClientSecretBytes = Base64.getDecoder().decode(clientIdAndClientSecretBase64Bytes);
        } catch (IllegalArgumentException e) {
            throw new BadCredentialsException("Failed to decode the HTTP header Authorization." +
                    " The correct format is 'Basic {clientId}:{clientSecret}' with base64 encode.");
        }

        // 拆分成ClientId和ClientSecret
        String clientIdAndClientSecret = new String(clientIdAndClientSecretBytes, StandardCharsets.UTF_8);
        int colonIndex = clientIdAndClientSecret.indexOf(":");
        if (colonIndex == -1) {
            throw new BadCredentialsException("Failed to decode the HTTP header Authorization." +
                    " The correct format is 'Basic {clientId}:{clientSecret}' with base64 encode.");
        }

        return new String[] {clientIdAndClientSecret.substring(0, colonIndex),
                clientIdAndClientSecret.substring(colonIndex + 1)};
    }
}
