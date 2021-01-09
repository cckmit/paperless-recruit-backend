package com.xiaohuashifu.recruit.authentication.service.enhancer;

import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.user.api.dto.UserDTO;
import com.xiaohuashifu.recruit.user.api.service.UserService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述：在 JWT 中添加一些额外的信息
 *
 * @author: xhsf
 * @create: 2020/11/10 21:20
 */
//@Component
public class JwtTokenEnhancer implements TokenEnhancer {

//    @Reference
    private UserService userService;

    /**
     * JWT 中用户信息在 Map 中的 Key
     */
    private static final String JWT_USER_INFO_KEY = "userInfo";

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        // 获取用户名
        Object user = oAuth2Authentication.getUserAuthentication().getPrincipal();
        String username;
        if (user instanceof UserDetails) {
            username = ((UserDetails)user).getUsername();
        } else {
            username = (String) user;
        }

        // 获取用户信息
        Result<UserDTO> getUserResult = userService.getUserByUsername(username);
        UserDTO userDTO = getUserResult.getData();
        JwtTokenUserInfo jwtTokenUserInfo = new JwtTokenUserInfo(
                userDTO.getId(), userDTO.getUsername(), userDTO.getPhone(), userDTO.getEmail(), userDTO.getAvailable());

        // 设置到 JwtToken 中
        Map<String, Object> info = new HashMap<>();
        info.put(JWT_USER_INFO_KEY, jwtTokenUserInfo);
        ((DefaultOAuth2AccessToken)oAuth2AccessToken).setAdditionalInformation(info);
        return oAuth2AccessToken;
    }
}
