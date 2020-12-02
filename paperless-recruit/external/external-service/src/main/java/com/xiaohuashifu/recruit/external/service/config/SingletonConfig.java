package com.xiaohuashifu.recruit.external.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：单例的配置
 *
 * @author: xhsf
 * @create: 2020/11/19 13:56
 */
@Configuration
public class SingletonConfig {

    /**
     * RestTemplate 单例
     * @return RestTemplate
     */
    @Bean
    public RestTemplate restTemplate() {
        List<MediaType> mediaTypes = new ArrayList<>();
        // 加入 text/plain 类型的支持
        mediaTypes.add(MediaType.TEXT_PLAIN);
        // 加入 text/html 类型的支持
        mediaTypes.add(MediaType.TEXT_HTML);
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        messageConverter.setSupportedMediaTypes(mediaTypes);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(messageConverter);
        return restTemplate;
    }
}
