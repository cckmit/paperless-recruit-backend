package com.xiaohuashifu.recruit.facade.service.manager;

import com.xiaohuashifu.recruit.facade.service.request.OAuthTokenPostRequest;
import com.xiaohuashifu.recruit.facade.service.vo.TokenVO;
import org.springframework.http.HttpHeaders;

/**
 * 描述：OAuth token 管理器
 *
 * @author xhsf
 * @create 2021/1/15 20:14
 */
public interface OAuthTokenManager {

    /**
     * OAuth 认证
     *
     * @param httpHeaders HttpHeaders
     * @param request OAuthTokenPostRequest
     * @return TokenVO
     */
    TokenVO authenticate(HttpHeaders httpHeaders, OAuthTokenPostRequest request);

    /**
     * 退出登录
     *
     * @param userId 用户编号
     */
    void logout(Long userId);

}
