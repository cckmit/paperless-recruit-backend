package com.xiaohuashifu.recruit.facade.service.manager.impl.oauth.processor;

import com.xiaohuashifu.recruit.facade.service.manager.impl.oauth.constant.GrantTypeEnum;
import com.xiaohuashifu.recruit.facade.service.request.OAuthTokenPostRequest;
import com.xiaohuashifu.recruit.facade.service.vo.AccessTokenVO;
import org.springframework.http.HttpHeaders;

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
     * @param request 请求
     * @return 认证结果
     */
    AccessTokenVO authenticate(HttpHeaders httpHeaders, OAuthTokenPostRequest request);

    /**
     * 判断是否支持该 grant type 的认证
     *
     * @param grantType 认证类型
     * @return 是否支持
     */
    boolean isSupport(GrantTypeEnum grantType);

}
