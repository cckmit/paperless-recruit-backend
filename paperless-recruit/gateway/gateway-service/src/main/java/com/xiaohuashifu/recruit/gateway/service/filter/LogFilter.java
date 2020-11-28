package com.xiaohuashifu.recruit.gateway.service.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 描述：日志过滤器
 *
 * @author xhsf
 * @create 2020/11/28 13:29
 */
@Component
// TODO: 2020/11/28 这里还没写
public class LogFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        HttpHeaders headers = exchange.getRequest().getHeaders();
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return OrderedGatewayFilter.LOWEST_PRECEDENCE;
    }
}
