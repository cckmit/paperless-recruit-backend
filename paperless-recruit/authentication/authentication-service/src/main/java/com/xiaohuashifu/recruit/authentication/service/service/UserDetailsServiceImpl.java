package com.xiaohuashifu.recruit.authentication.service.service;

import com.xiaohuashifu.recruit.authentication.service.constant.AuthorityConstants;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.user.api.dto.UserDTO;
import com.xiaohuashifu.recruit.user.api.service.AuthorityService;
import com.xiaohuashifu.recruit.user.api.service.UserService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 描述：密码登录使用的服务
 *
 * @author: xhsf
 * @create: 2020/11/10 15:38
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Reference
    private AuthorityService authorityService;

    @Reference
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String usernameOrPhoneOrEmail) throws UsernameNotFoundException {
        // 查找用户
        Result<UserDTO> getUserResult = userService.getUserByUsernameOrPhoneOrEmail(usernameOrPhoneOrEmail);
        if (!getUserResult.isSuccess()) {
           throw new UsernameNotFoundException("The user does not exist.");
        }
        UserDTO userDTO = getUserResult.getData();

        // 获取权限列表
        Set<String> authoritySet = authorityService.listAuthoritiesByUserId(
                userDTO.getId(), AuthorityConstants.SPRING_SECURITY_ROLE_PREFIX).getData();
        List<SimpleGrantedAuthority> authorityList = authoritySet.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        // 返回
        return new User(userDTO.getUsername(), userDTO.getPassword(), userDTO.getAvailable(), true,
                true, true, authorityList);
    }

}
