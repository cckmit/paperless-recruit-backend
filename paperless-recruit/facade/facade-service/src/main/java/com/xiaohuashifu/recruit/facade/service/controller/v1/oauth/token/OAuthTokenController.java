package com.xiaohuashifu.recruit.facade.service.controller.v1.oauth.token;

import com.xiaohuashifu.recruit.facade.service.controller.v1.oauth.token.constant.GrantTypeEnum;
import com.xiaohuashifu.recruit.facade.service.controller.v1.oauth.token.processor.AuthenticationProcessor;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 描述：oauth 控制器
 *
 * @author xhsf
 * @create 2021/1/14 14:52
 */
@RestController
@RequestMapping("oauth/token")
public class OAuthTokenController {

    private final List<AuthenticationProcessor> authenticationProcessorList;

    public OAuthTokenController(List<AuthenticationProcessor> authenticationProcessorList) {
        this.authenticationProcessorList = authenticationProcessorList;
    }

    /**
     * 认证接口
     *
     * 支持 [PASSWORD, OPEN_ID, SMS, REFRESH_TOKEN] 四种认证方式
     *
     * @param httpHeaders HttpHeaders
     * @param body 请求体
     * @return Token 令牌
     */
    @ApiParam
    @PostMapping
    public Object post(@RequestHeader HttpHeaders httpHeaders, @RequestBody Map<String, Object> body) {
        String grantType = String.valueOf(body.remove("grantType"));
        GrantTypeEnum grantTypeEnum = GrantTypeEnum.valueOf(grantType);
        body.put("grant_type", grantTypeEnum.getGrantType());
        for (AuthenticationProcessor authenticationProcessor : authenticationProcessorList) {
            if (authenticationProcessor.isSupport(grantTypeEnum)) {
                return authenticationProcessor.authenticate(httpHeaders, body);
            }
        }
        return "not support grant type";
    }

}
