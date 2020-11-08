package com.xiaohuashifu.recruit.user;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@EnableDubbo
@MapperScan
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);

    }

}
