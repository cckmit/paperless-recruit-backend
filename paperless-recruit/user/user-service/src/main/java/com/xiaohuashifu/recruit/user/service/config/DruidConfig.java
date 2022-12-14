package com.xiaohuashifu.recruit.user.service.config;

import com.alibaba.druid.support.http.StatViewServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

/**
 * 描述：Druid 数据源配置
 *
 * @author: xhsf
 * @create: 2020/10/30 15:05
 */
@Configuration
public class DruidConfig {

    /**
     * 配置 servlet 开启 druid 监控页面
     *
     * @return ServletRegistrationBean
     */
    @Bean
    public ServletRegistrationBean<StatViewServlet> statViewServlet(){
        ServletRegistrationBean<StatViewServlet> bean = new ServletRegistrationBean<>(
                new StatViewServlet(), "/druid/*");
        HashMap<String, String> initParameters = new HashMap<>();

        // 设置账号密码
        initParameters.put("loginUsername", "admin");
        initParameters.put("loginPassword", "123456");
        bean.setInitParameters(initParameters);
        return bean;
    }

}
