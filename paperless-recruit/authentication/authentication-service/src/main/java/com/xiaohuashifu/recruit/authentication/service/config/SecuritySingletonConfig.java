package com.xiaohuashifu.recruit.authentication.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;

import javax.sql.DataSource;

/**
 * 描述：与Spring Security相关的一些单例的配置类
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/12 18:13
 */
@Configuration
public class SecuritySingletonConfig {

    /**
     * 客户端服务
     * @param dataSource 数据源
     * @return ClientDetailsService 这里是JdbcClientDetailsService
     */
    @Bean
    public ClientDetailsService clientDetailsService(DataSource dataSource) {
        return new JdbcClientDetailsService(dataSource);
    }

    /**
     * 密码编码器
     * 默认使用 bcrypt， strength=10
     * @return PasswordEncoder 密码编码器
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
