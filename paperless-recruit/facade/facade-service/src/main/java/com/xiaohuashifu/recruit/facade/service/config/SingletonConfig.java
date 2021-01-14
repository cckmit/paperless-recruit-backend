package com.xiaohuashifu.recruit.facade.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

/**
 * 描述：单例配置
 *
 * @author xhsf
 * @create 2020/11/29 18:19
 */
@Configuration
public class SingletonConfig {
    /**
     * RestTemplate 配置
     *
     * @return RestTemplate
     */
    @Bean
    public RestTemplate restTemplate() {
        class CustomResponseErrorHandler extends DefaultResponseErrorHandler {
            @Override
            public void handleError(ClientHttpResponse response) {}
        }
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setOutputStreaming(false);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        restTemplate.setErrorHandler(new CustomResponseErrorHandler());
        return restTemplate;
    }

}
