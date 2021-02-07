package com.xiaohuashifu.recruit.authentication.service.service;

import com.xiaohuashifu.recruit.authentication.service.constant.AuthorityConstants;
import com.xiaohuashifu.recruit.common.exception.NotFoundServiceException;
import com.xiaohuashifu.recruit.user.api.dto.UserDTO;
import com.xiaohuashifu.recruit.user.api.service.AuthorityService;
import com.xiaohuashifu.recruit.user.api.service.UserService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

// TODO: 2020/12/12  👇↓ 这个类我不知道哪里用到，但是不能删，删除后如果认证出错会无限递归，希望有高手看看出什么问题
// FIXME: 2020/12/12 👇↓ 这个类我不知道哪里用到，但是不能删，删除后如果认证出错会无限递归，希望有高手看看出什么问题
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
        UserDTO userDTO;
        try {
            userDTO = userService.getUserByUsernameOrPhoneOrEmail(usernameOrPhoneOrEmail);
        } catch (NotFoundServiceException e) {
            throw new UsernameNotFoundException("The user does not exist.");
        }

        // 判断用户是否可用
        if (!userDTO.getAvailable()) {
            throw new DisabledException("The user unavailable.");
        }

        // 获取权限列表
        Set<String> authoritySet = authorityService.listAuthoritiesByUserId(
                userDTO.getId(), AuthorityConstants.SPRING_SECURITY_ROLE_PREFIX);
        List<SimpleGrantedAuthority> authorityList = authoritySet.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        // 返回
        return new User(userDTO.getUsername(), userDTO.getPassword(), userDTO.getAvailable(), true,
                true, true, authorityList);
    }

}