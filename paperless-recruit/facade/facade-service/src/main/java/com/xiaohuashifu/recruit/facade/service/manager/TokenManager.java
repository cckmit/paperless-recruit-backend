package com.xiaohuashifu.recruit.facade.service.manager;

import com.xiaohuashifu.recruit.facade.service.request.TokenPostRequest;
import com.xiaohuashifu.recruit.facade.service.vo.TokenVO;
import org.springframework.http.HttpHeaders;

/**
 * 描述：Token 管理器
 *
 * @author xhsf
 * @create 2021/1/15 20:14
 */
public interface TokenManager {

    /**
     * 认证
     *
     * @param httpHeaders HttpHeaders
     * @param request TokenPostRequest
     * @return TokenVO
     */
    TokenVO authenticate(HttpHeaders httpHeaders, TokenPostRequest request);

    /**
     * 退出登录
     *
     * @param userId 用户编号
     */
    void logout(Long userId);

}
