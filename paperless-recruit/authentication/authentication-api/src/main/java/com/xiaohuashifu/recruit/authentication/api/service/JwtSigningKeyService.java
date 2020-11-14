package com.xiaohuashifu.recruit.authentication.api.service;

/**
 * 描述：获取JWT签名密钥的服务
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/14 18:43
 */
public interface JwtSigningKeyService {
    /**
     * 这个服务可以获取认证服务器用于JWT签名的密钥
     * @return 签名密钥
     */
    default String getSigningKey() {
        throw new UnsupportedOperationException();
    }
}
