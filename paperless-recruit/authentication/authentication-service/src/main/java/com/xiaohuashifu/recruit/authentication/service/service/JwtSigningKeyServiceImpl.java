package com.xiaohuashifu.recruit.authentication.service.service;

import com.xiaohuashifu.recruit.authentication.api.service.JwtSigningKeyService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Value;

/**
 * 描述：获取JWT签名密钥的服务实现类
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/14 18:45
 */
@Service
public class JwtSigningKeyServiceImpl implements JwtSigningKeyService {
    /**
     * jwt的签名密钥
     */
    @Value("${jwt.signingKey}")
    private String signingKey;

    /**
     * 这个服务可以获取认证服务器用于JWT签名的密钥
     * @return 签名密钥
     */
    @Override
    public String getSigningKey() {
        return signingKey;
    }
}
