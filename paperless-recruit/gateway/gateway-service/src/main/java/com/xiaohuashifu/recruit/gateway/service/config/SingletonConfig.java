package com.xiaohuashifu.recruit.gateway.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;

/**
 * 描述：一些单例
 *
 * @author xhsf
 * @create 2020/11/26 14:43
 */
@Configuration
public class SingletonConfig {

    @Bean
    public AntPathMatcher antPathMatcher() {
        return new AntPathMatcher();
    }

}
