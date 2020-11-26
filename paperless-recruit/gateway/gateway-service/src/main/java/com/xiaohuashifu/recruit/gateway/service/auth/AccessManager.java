package com.xiaohuashifu.recruit.gateway.service.auth;

import org.apache.dubbo.common.utils.ConcurrentHashSet;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Set;

/**
 * 描述：鉴权管理器
 *
 * @author xhsf
 * @create 2020/11/26 11:26
 */
@Component
public class AccessManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    private final AntPathMatcher antPathMatcher;

    private final UrlAuthorityChecker urlAuthorityChecker;

    /**
     * 默认允许的Url集合
     */
    private Set<String> permittedUrlSet;

    public AccessManager(AntPathMatcher antPathMatcher, UrlAuthorityChecker urlAuthorityChecker){
        this.antPathMatcher = antPathMatcher;
        this.urlAuthorityChecker = urlAuthorityChecker;
        initPermittedUrlSet();
    }

    /**
     * 实现权限验证判断
     */
    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authenticationMono,
                                             AuthorizationContext authorizationContext) {
        ServerHttpRequest request = authorizationContext.getExchange().getRequest();
        String url = request.getURI().getPath();

        // 对应跨域的预检请求直接放行
        if (request.getMethod() == HttpMethod.OPTIONS) {
            return Mono.just(new AuthorizationDecision(true));
        }

        // 是否在默认允许的Url集合里
        if (permittedUrlSet.stream().anyMatch(r -> antPathMatcher.match(r, url))) {
            return Mono.just(new AuthorizationDecision(true));
        }


        // 进行鉴权
        return authenticationMono
                .map(auth -> new AuthorizationDecision(urlAuthorityChecker.check(auth.getAuthorities(), url)))
                .defaultIfEmpty(new AuthorizationDecision(false));
    }

    /**
     * 初始化默认允许的集合
     */
    // TODO: 2020/11/26 这里更好的方式是从数据库获取
    private void initPermittedUrlSet() {
        permittedUrlSet = new ConcurrentHashSet<>();
        permittedUrlSet.add("/");
        permittedUrlSet.add("/error");
        permittedUrlSet.add("/favicon.ico");
        permittedUrlSet.add("/**/v2/api-docs/**");
        permittedUrlSet.add("/**/swagger-resources/**");
        permittedUrlSet.add("/webjars/**");
        permittedUrlSet.add("/doc.html");
        permittedUrlSet.add("/swagger-ui.html");
//        permittedUrlSet.add("/**/oauth/**");
        permittedUrlSet.add("/**/current/get");
    }
}