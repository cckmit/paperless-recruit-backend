package com.xiaohuashifu.recruit.facade.service.config;

import com.xiaohuashifu.recruit.facade.service.constant.AuthorityConstants;
import com.xiaohuashifu.recruit.user.api.service.AuthorityService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;

/**
 * 描述：层级权限
 *
 * @author: xhsf
 * @create: 2020/11/28 14:30
 */
@Configuration
public class RoleHierarchyConfig {

    @Reference
    private AuthorityService authorityService;

    @Bean
    public RoleHierarchy roleHierarchy() {
        final RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy(authorityService.createRoleHierarchy(
                AuthorityConstants.SPRING_SECURITY_ROLE_PREFIX).getData());
        return roleHierarchy;
    }

}
