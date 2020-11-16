package com.xiaohuashifu.recruit.authentication.service.controller;

import com.xiaohuashifu.recruit.user.api.dto.RoleDTO;
import com.xiaohuashifu.recruit.user.api.service.RoleService;
import com.xiaohuashifu.recruit.user.api.service.UserService;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

/**
 * 描述：
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/10 16:00
 */
@RestController
public class UserController {

    @Value("${jwt.signingKey}")
    private String signingKey;

    @Reference
    private UserService userService;

    @Reference
    private RoleService roleService;

    @GetMapping("index")
    public Object index(Authentication authentication) {
        return authentication;
    }

    @GetMapping("index1")
    public Object index1(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        return Jwts.parser().setSigningKey(signingKey.getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(StringUtils.substringAfter(authorization, "bearer "));
    }

    @GetMapping("test1")
    public Object test1(Long id) {
        return userService.getUser(id);
    }

    @GetMapping("test2")
    public Object test2() {
        return null;
    }

    @GetMapping("test3")
    public Object test3() {
//        new RoleDTO.Builder()
//                .parentRoleId(-1L)
//                .roleName(null)
//                .description("超级权限")
//                .available(true).build()
        return roleService.saveRole(null);
    }

    @GetMapping("test4")
    public Object test4(Long id, String email) {
        return userService.updateEmail(id, email);
    }
}
