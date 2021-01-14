package com.xiaohuashifu.recruit.facade.service.controller.v1.oauth.token;

import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * 描述：oauth 控制器
 *
 * @author xhsf
 * @create 2021/1/14 14:52
 */
@RestController
@RequestMapping(OAuthTokenController.CURRENT_PATH)
public class OAuthTokenController {

    private final RestTemplate restTemplate;

    @Value("${oauth2.server.url}")
    private String oauthServerUrl;

    @Value("${oauth2.encoded-credentials}")
    private String encodedCredentials;

    /**
     * 当前的路径
     */
    public static final String CURRENT_PATH = "oauth/token";

    public OAuthTokenController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * 认证接口
     *
     * @param httpHeaders HttpHeaders
     * @param body 请求体
     * @return Token 令牌
     */
    @ApiParam
    @PostMapping
    public Object post(@RequestHeader HttpHeaders httpHeaders, @RequestBody Object body) {
        httpHeaders.setBasicAuth(encodedCredentials);
        HttpEntity<Object> httpEntity = new HttpEntity<>(body, httpHeaders);
        ResponseEntity<Object> responseEntity =
                restTemplate.postForEntity(oauthServerUrl + CURRENT_PATH, httpEntity, Object.class);
        return responseEntity;
    }

}
