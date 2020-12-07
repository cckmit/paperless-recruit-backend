//package com.xiaohuashifu.recruit.external.service.config;
//
//import com.aliyun.oss.OSS;
//import com.aliyun.oss.OSSClientBuilder;
//import org.springframework.beans.factory.DisposableBean;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * 描述：阿里云对象存储服务配置
// *
// * @author xhsf
// * @create 2020/12/7 21:30
// */
//@Configuration
//public class AliyunOssConfig {
//
//    /**
//     * 端点
//     */
//    private static final String ENDPOINT = "https://oss-cn-guangzhou.aliyuncs.com";
//
//    @Value("${aliyun.access-key-id}")
//    private String accessKeyId;
//
//    @Value("${aliyun.access-key-secret}")
//    private String accessKeySecret;
//
//    public OSS oss1() {
//        return new OSSClientBuilder().build(ENDPOINT, accessKeyId, accessKeySecret);
//    }
//
//    /**
//     * oss 客户端配置
//     *
//     * @return OSS 客户端
//     */
//    @Bean
//    public OSS oss() {
//        return new OSSClientBuilder().build(ENDPOINT, accessKeyId, accessKeySecret);
//    }
//
//    /**
//     * oss 客户端销毁器
//     *
//     * @return DisposableBean
//     */
//    @Bean
//    public DisposableBean disposableBean() {
//        return () -> oss().shutdown();
//    }
//
//}
