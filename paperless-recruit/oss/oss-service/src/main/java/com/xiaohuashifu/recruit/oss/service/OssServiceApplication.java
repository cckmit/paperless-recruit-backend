package com.xiaohuashifu.recruit.oss.service;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableDiscoveryClient
@EnableDubbo
@MapperScan("com.xiaohuashifu.recruit.oss.service.dao")
@EnableScheduling
public class OssServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OssServiceApplication.class, args);
	}

}
