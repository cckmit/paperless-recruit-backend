package com.xiaohuashifu.recruit.authentication.service.service;

import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.user.api.dto.PermissionDTO;
import com.xiaohuashifu.recruit.user.api.dto.UserDTO;
import com.xiaohuashifu.recruit.user.api.service.PermissionService;
import com.xiaohuashifu.recruit.user.api.service.UserService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 描述：
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/10 15:38
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;

    @Reference
    private PermissionService permissionService;

    @Reference
    private UserService userService;

    public UserDetailsServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Result<UserDTO> getUserResult = userService.getUserByUsername(username);
        if (!getUserResult.isSuccess()) {
           return null;
        }

        final UserDTO userDTO = getUserResult.getData();
        final Result<List<PermissionDTO>> getPermissionResult = permissionService.getPermissionByUserId(userDTO.getId());
        final List<PermissionDTO> permissionDTOList = getPermissionResult.getData();
        final List<SimpleGrantedAuthority> authorityList = permissionDTOList.stream()
                .map(permissionDTO -> new SimpleGrantedAuthority(permissionDTO.getPermissionName()))
                .collect(Collectors.toList());
        return new User(username, userDTO.getPassword(), userDTO.getAvailable(), true,
                true, true, authorityList);
    }
}
