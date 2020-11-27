package com.xiaohuashifu.recruit.gateway.service.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 描述：白名单配置
 *
 * @author xhsf
 * @create 2020/11/27 16:45
 */
@Configuration
@ConfigurationProperties(prefix = "white-list")
public class WhiteListConfig {

    private String[] urls;

    public String[] getUrls() {
        return urls;
    }

    public void setUrls(String[] urls) {
        this.urls = urls;
    }
}
