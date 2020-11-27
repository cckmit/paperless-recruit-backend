package com.xiaohuashifu.recruit.gateway.service.config;


import com.xiaohuashifu.recruit.gateway.service.auth.AccessManager;
import com.xiaohuashifu.recruit.gateway.service.constant.ResourceServerConstant;
import com.xiaohuashifu.recruit.gateway.service.handler.CustomServerAccessDeniedHandler;
import com.xiaohuashifu.recruit.gateway.service.handler.CustomServerAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

/**
 * 描述：资源服务器配置
 *
 * @author xhsf
 * @create 2020/11/27 15:19
 */
@EnableWebFluxSecurity
public class ResourceServerConfig {

    private final AccessManager authorizationManager;
    private final CustomServerAccessDeniedHandler customServerAccessDeniedHandler;
    private final CustomServerAuthenticationEntryPoint customServerAuthenticationEntryPoint;
    private final WhiteListConfig whiteListConfig;

    public ResourceServerConfig(AccessManager authorizationManager,
                                CustomServerAccessDeniedHandler customServerAccessDeniedHandler,
                                CustomServerAuthenticationEntryPoint customServerAuthenticationEntryPoint,
                                WhiteListConfig whiteListConfig) {
        this.authorizationManager = authorizationManager;
        this.customServerAccessDeniedHandler = customServerAccessDeniedHandler;
        this.customServerAuthenticationEntryPoint = customServerAuthenticationEntryPoint;
        this.whiteListConfig = whiteListConfig;
    }

    /**
     * Web Security过滤器链配置
     * @param http ServerHttpSecurity
     * @return SecurityWebFilterChain
     */
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http.oauth2ResourceServer().jwt()
                .jwtAuthenticationConverter(jwtAuthenticationConverter());
        // 自定义处理JWT请求头过期或签名错误的结果
        http.oauth2ResourceServer().authenticationEntryPoint(customServerAuthenticationEntryPoint);
        http.authorizeExchange()
                .pathMatchers(whiteListConfig.getUrls()).permitAll()
                .anyExchange().access(authorizationManager)
                .and()
                .exceptionHandling()
                .accessDeniedHandler(customServerAccessDeniedHandler) // 处理未授权
                .authenticationEntryPoint(customServerAuthenticationEntryPoint) //处理未认证
                .and()
                .csrf().disable()
                // TODO: 2020/11/27 这里要去找一下资料看CorsConfigurationSource怎么配置
                .cors();

        return http.build();
    }

    /**
     * ServerHttpSecurity没有将jwt中authorities的负载部分当做Authentication
     * 需要把jwt的Claim中的authorities加入
     * 重新定义ReactiveAuthenticationManager权限管理器，添加默认转换器JwtGrantedAuthoritiesConverter
     * @return ReactiveJwtAuthenticationConverterAdapter
     */
    @Bean
    public Converter<Jwt, ? extends Mono<? extends AbstractAuthenticationToken>> jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix(ResourceServerConstant.AUTHORITY_PREFIX);
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName(ResourceServerConstant.AUTHORITIES_CLAIM_NAME);

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
    }

}