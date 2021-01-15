package com.xiaohuashifu.recruit.facade.service.controller.v1.oauth.token.processor;

import com.xiaohuashifu.recruit.facade.service.controller.v1.oauth.token.constant.GrantTypeEnum;
import org.springframework.http.HttpHeaders;

import java.util.Map;

/**
 * 描述：认证的处理器
 *
 * @author xhsf
 * @create 2021/1/14 19:07
 */
public interface AuthenticationProcessor {

    /**
     * 进行认证
     *
     * @param httpHeaders Http 头
     * @param body 请求体
     * @return 认证结果
     */
    Object authenticate(HttpHeaders httpHeaders, Map<String, String> body);

    /**
     * 判断是否支持该 grant type 的认证
     *
     * @param grantType 认证类型
     * @return 是否支持
     */
    boolean isSupport(GrantTypeEnum grantType);

}
