package com.xiaohuashifu.recruit.authentication.service.oauth2;

import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.user.api.dto.UserDTO;
import com.xiaohuashifu.recruit.user.api.service.UserService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    private UserService userService;

    public UserDetailsServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Result<UserDTO> getUserResult = userService.getUserByUsername(username);
        if (!getUserResult.isSuccess()) {
            // TODO: 2020/11/10 这里要处理获取用户失败的情况
        }

        final UserDTO userDTO = getUserResult.getData();
        return new User(username, userDTO.getPassword(), userDTO.getAvailable(), true,
                true, true, AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }
}
