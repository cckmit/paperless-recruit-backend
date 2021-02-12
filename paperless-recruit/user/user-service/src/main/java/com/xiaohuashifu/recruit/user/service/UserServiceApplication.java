package com.xiaohuashifu.recruit.user.service;

import io.seata.spring.annotation.datasource.EnableAutoDataSourceProxy;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAutoDataSourceProxy
@SpringBootApplication
@EnableAspectJAutoProxy(exposeProxy = true)
@EnableDiscoveryClient
@EnableDubbo
@MapperScan("com.xiaohuashifu.recruit.user.service.dao")
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

}
