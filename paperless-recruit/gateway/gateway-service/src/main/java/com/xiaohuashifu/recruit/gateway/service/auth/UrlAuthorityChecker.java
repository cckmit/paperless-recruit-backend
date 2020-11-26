package com.xiaohuashifu.recruit.gateway.service.auth;

import com.xiaohuashifu.recruit.user.api.dto.PermissionDTO;
import com.xiaohuashifu.recruit.user.api.service.PermissionService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 描述：基于Url的权限检查器
 *
 * @author xhsf
 * @create 2020/11/26 15:10
 */
@Component
@EnableScheduling
public class UrlAuthorityChecker {

    @Reference
    private PermissionService permissionService;

    /**
     * 刷新permissionNameAuthorizationUrlMap的间隔
     */
    private static final int REFRESH_DELAY = 60000;

    /**
     * 权限名和授权URL的对应关系
     */
    private Map<String, String> permissionNameAuthorizationUrlMap;

    private final AntPathMatcher antPathMatcher;

    public UrlAuthorityChecker(AntPathMatcher antPathMatcher) {
        this.antPathMatcher = antPathMatcher;
    }

    /**
     * 通过用户拥有的权限进行鉴权
     *
     * @param authorities 权限列表
     * @param url 请求的Url
     * @return 是否通过鉴权
     */
    public boolean check(Collection<? extends GrantedAuthority> authorities, String url) {
        // 判断用户的权限里有没有满足获取该路径资源的
        for (GrantedAuthority authority : authorities) {
            String authorizationUrl = permissionNameAuthorizationUrlMap.get(authority.getAuthority());
            if (authorizationUrl != null && antPathMatcher.match(authorizationUrl, url)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 创建newPermissionNameAuthorizationUrlMap并替换旧的
     * 这里添加定时任务，会每REFRESH_DELAY毫秒刷新一次permissionNameAuthorizationUrlMap
     */
    @Scheduled(initialDelay = 0, fixedDelay = REFRESH_DELAY)
    private void updatePermissionNameAuthorizationUrlMap() {
        System.out.println("updatePermissionNameAuthorizationUrlMap");
        List<PermissionDTO> permissionDTOList = permissionService.getAllPermission().getData();
        Map<String, String> newPermissionNameAuthorizationUrlMap = new ConcurrentHashMap<>();
        for (PermissionDTO permissionDTO : permissionDTOList) {
            newPermissionNameAuthorizationUrlMap.put(permissionDTO.getPermissionName(),
                    permissionDTO.getAuthorizationUrl());
        }
        permissionNameAuthorizationUrlMap = newPermissionNameAuthorizationUrlMap;
    }
}
