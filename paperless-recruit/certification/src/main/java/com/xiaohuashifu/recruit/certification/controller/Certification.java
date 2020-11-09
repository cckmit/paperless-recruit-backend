package com.xiaohuashifu.recruit.certification.controller;

import com.xiaohuashifu.recruit.userapi.dto.UserDTO;
import com.xiaohuashifu.recruit.userapi.service.UserService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class Certification {
    private final LoadBalancerClient loadBalancerClient;
    private final RestTemplate restTemplate;
    @Reference
    private UserService userService;

    @Value("${spring.application.name}")
    private String appName;

    public Certification(LoadBalancerClient loadBalancerClient, RestTemplate restTemplate) {
        this.loadBalancerClient = loadBalancerClient;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/echo/app-name")
    public String echoAppName(){
        //使用 LoadBalanceClient 和 RestTemplate 结合的方式来访问
        ServiceInstance serviceInstance = loadBalancerClient.choose("user-provider");
        String url = String.format("http://%s:%s/echo/%s",serviceInstance.getHost(),serviceInstance.getPort(), appName);
        System.out.println("request url:"+url);
        return restTemplate.getForObject(url,String.class);
    }

    @GetMapping("/rpc/user/{id}")
    public Object rpc(@PathVariable Long id){
        return userService.getUser(id);
    }

    @GetMapping("/rpc/user")
    public Object rpc(UserDTO userDTO){
        return userService.saveUser("xhszzz", "321321");
    }
}
