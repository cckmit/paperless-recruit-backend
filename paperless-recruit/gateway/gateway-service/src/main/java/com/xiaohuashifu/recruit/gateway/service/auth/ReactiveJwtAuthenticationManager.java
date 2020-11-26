package com.xiaohuashifu.recruit.gateway.service.auth;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * 描述：接受请求，判断是否有access-token或者access-token是否合法
 *
 * @author xhsf
 * @create 2020/11/26 1:01
 */
@Component
public class ReactiveJwtAuthenticationManager implements ReactiveAuthenticationManager {

    private final TokenStore tokenStore;

    public ReactiveJwtAuthenticationManager(TokenStore tokenStore){
        this.tokenStore = tokenStore;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.justOrEmpty(authentication)
                .filter(authentication0 -> authentication0 instanceof BearerTokenAuthenticationToken)
                .cast(BearerTokenAuthenticationToken.class)
                .map(BearerTokenAuthenticationToken::getToken)
                .flatMap(accessToken ->{
                    // access-token不能过期
                    OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(accessToken);
                    if (oAuth2AccessToken.isExpired()) {
                        return Mono.error(new InvalidTokenException("Access token already expired."));
                    }

                     // 获取OAuth2Authentication给AccessManager处理
                     OAuth2Authentication oAuth2Authentication = tokenStore.readAuthentication(accessToken);
                     return Mono.just(oAuth2Authentication);
                })
                .cast(Authentication.class);
    }

}
