package com.xiaohuashifu.recruit.authentication.service.enhancer;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述：在JWT中添加一些额外的信息，我们需要实现TokenEnhancer（Token增强器）
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/10 21:20
 */
public class JwtTokenEnhancer implements TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        Map<String, Object> info = new HashMap<>();
        info.put("message", "xhsf");
        ((DefaultOAuth2AccessToken)oAuth2AccessToken).setAdditionalInformation(info);
        return oAuth2AccessToken;
    }
}
