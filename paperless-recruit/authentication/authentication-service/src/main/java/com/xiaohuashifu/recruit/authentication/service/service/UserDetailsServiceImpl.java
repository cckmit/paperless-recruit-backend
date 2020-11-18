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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 描述：密码登录使用的服务
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/10 15:38
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Reference
    private PermissionService permissionService;

    @Reference
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String usernameOrPhoneOrEmail) throws UsernameNotFoundException {
        // 查找用户
        Result<UserDTO> getUserResult = userService.getUserByUsernameOrPhoneOrEmail(usernameOrPhoneOrEmail);
        if (!getUserResult.isSuccess()) {
           throw new UsernameNotFoundException("User not found.");
        }
        UserDTO userDTO = getUserResult.getData();

        // 获取权限列表
        Result<List<PermissionDTO>> getPermissionResult = permissionService.getPermissionByUserId(userDTO.getId());
        List<PermissionDTO> permissionDTOList = getPermissionResult.getData();
        List<SimpleGrantedAuthority> authorityList = permissionDTOList.stream()
                .map(permissionDTO -> new SimpleGrantedAuthority(permissionDTO.getPermissionName()))
                .collect(Collectors.toList());

        // 返回
        return new User(userDTO.getUsername(), userDTO.getPassword(), userDTO.getAvailable(), true,
                true, true, authorityList);
    }
}
