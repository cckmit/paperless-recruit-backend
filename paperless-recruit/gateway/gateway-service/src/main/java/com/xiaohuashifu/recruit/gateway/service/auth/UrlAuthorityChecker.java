package com.xiaohuashifu.recruit.gateway.service.auth;

import com.xiaohuashifu.recruit.authentication.api.service.WhiteListService;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.gateway.service.constant.ResourceServerConstants;
import com.xiaohuashifu.recruit.user.api.service.AuthorityService;
import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 描述：基于 Url 的权限检查器
 *
 * @author xhsf
 * @create 2020/11/26 15:10
 */
@Component
@EnableScheduling
public class UrlAuthorityChecker {

    private static final Logger logger = LoggerFactory.getLogger(UrlAuthorityChecker.class);

    @Reference
    private AuthorityService authorityService;

    @Reference
    private WhiteListService whiteListService;

    /**
     * 刷新 permissionNameAuthorizationUrlMap 和 whiteList 的固定间隔
     */
    private static final int REFRESH_FIXED_DELAY = 60000;

    /**
     * 刷新 permissionNameAuthorizationUrlMap 和 whiteList 的初始延迟
     */
    private static final int REFRESH_INITIAL_DELAY = 0;

    /**
     * 权限名和授权 URL 的对应关系
     */
    private Map<String, String> permissionNameAuthorizationUrlMap;

    /**
     * 路径白名单
     */
    private List<String> whiteList;

    private final AntPathMatcher antPathMatcher;

    public UrlAuthorityChecker(AntPathMatcher antPathMatcher) {
        this.antPathMatcher = antPathMatcher;
    }

    /**
     * 通过用户拥有的权限进行鉴权
     *
     * @param authorities 权限列表
     * @param url 请求的 Url
     * @return 是否通过鉴权
     */
    public boolean check(Collection<? extends GrantedAuthority> authorities, String url) {
        // 判断该路径是否在白名单里，如果是直接放行
        for (String permittedUrl : whiteList) {
            if (antPathMatcher.match(permittedUrl, url)) {
                return true;
            }
        }

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
     * 创建 newPermissionNameAuthorizationUrlMap 并替换旧的
     * 这里添加定时任务，会每 REFRESH_DELAY 毫秒刷新一次 permissionNameAuthorizationUrlMap
     */
    @Scheduled(initialDelay = REFRESH_INITIAL_DELAY, fixedDelay = REFRESH_FIXED_DELAY)
    private void refreshPermissionNameAuthorizationUrlMap() {
        Result<Map<String, String>> createPermissionNameAuthorizationUrlMapResult =
                authorityService.createPermissionNameAuthorizationUrlMap(ResourceServerConstants.AUTHORITY_PREFIX);
        if (!createPermissionNameAuthorizationUrlMapResult.isSuccess()) {
            logger.error("Refresh PermissionNameAuthorizationUrlMap failed.");
            return;
        }
        this.permissionNameAuthorizationUrlMap = createPermissionNameAuthorizationUrlMapResult.getData();
    }

    /**
     * 创建 newWhiteList 并替换旧的
     * 这里添加定时任务，会每 REFRESH_DELAY 毫秒刷新一次 whiteList
     */
    @Scheduled(initialDelay = REFRESH_INITIAL_DELAY, fixedDelay = REFRESH_FIXED_DELAY)
    private void refreshWhiteList() {
        Result<List<String>> getWhiteListResult = whiteListService.getWhiteList();
        if (!getWhiteListResult.isSuccess()) {
            logger.error("Refresh refreshWhiteList failed.");
            return;
        }
        whiteList = getWhiteListResult.getData();
    }
}
