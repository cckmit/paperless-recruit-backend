package com.xiaohuashifu.recruit.user.service.config;

import com.github.dozermapper.spring.DozerBeanMapperFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 描述：一些单例的配置
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/10/30 21:31
 */
@Configuration
public class SingletonConfig {
    /**
     * dozer配置
     *
     * @return Mapper
     */
    @Bean
    public DozerBeanMapperFactoryBean dozerMapper() {
        return new DozerBeanMapperFactoryBean();
    }
}
