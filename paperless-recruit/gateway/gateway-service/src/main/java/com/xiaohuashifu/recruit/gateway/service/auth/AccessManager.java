package com.xiaohuashifu.recruit.gateway.service.auth;

import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * 描述：鉴权管理器
 *
 * @author xhsf
 * @create 2020/11/26 11:26
 */
@Component
public class AccessManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    private final UrlAuthorityChecker urlAuthorityChecker;

    public AccessManager(UrlAuthorityChecker urlAuthorityChecker){
        this.urlAuthorityChecker = urlAuthorityChecker;
    }

    /**
     * 实现权限验证判断
     */
    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authenticationMono,
                                             AuthorizationContext authorizationContext) {
        ServerHttpRequest request = authorizationContext.getExchange().getRequest();

        // 对应跨域的预检请求直接放行
        if (request.getMethod() == HttpMethod.OPTIONS) {
            return Mono.just(new AuthorizationDecision(true));
        }

        // 进行鉴权
        String url0 = request.getPath().value();
        String url = url0.startsWith("/v1") ? url0.substring(3) : url0;
        return authenticationMono
                .map(auth -> new AuthorizationDecision(urlAuthorityChecker.check(auth.getAuthorities(), url)))
                .defaultIfEmpty(new AuthorizationDecision(urlAuthorityChecker.check(null, url)));
    }

}