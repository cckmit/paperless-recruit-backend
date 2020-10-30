package com.xiaohuashifu.recruit.certification;

import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@DubboComponentScan
public class CertificationApplication {

    public static void main(String[] args) {
        SpringApplication.run(CertificationApplication.class, args);
    }

}
