package com.xiaohuashifu.recruit.facade.service.authorize;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.SecurityExpressionOperations;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

/**
 * 描述：自定义 SecurityExpressionRoot,可以使用 API 进行鉴权
 *      如 hasRole()，hasAnyAuthority()
 *      改造自 SecurityExpressionRoot
 *
 * @author xhsf
 * @create 2020/11/28 21:53
 */
public class SecurityAuthorize implements SecurityExpressionOperations {
    protected Authentication authentication;
    private AuthenticationTrustResolver trustResolver;
    private RoleHierarchy roleHierarchy;
    private Set<String> roles;
    private String defaultRolePrefix = "ROLE_";

    /** Allows "permitAll" expression */
    public final boolean permitAll = true;

    /** Allows "denyAll" expression */
    public final boolean denyAll = false;
    private PermissionEvaluator permissionEvaluator;
    public final String read = "read";
    public final String write = "write";
    public final String create = "create";
    public final String delete = "delete";
    public final String admin = "administration";

    public SecurityAuthorize() {
    }

    /**
     * Creates a new instance
     * @param authentication the {@link Authentication} to use. Cannot be null.
     */
    public SecurityAuthorize(Authentication authentication) {
        if (authentication == null) {
            throw new IllegalArgumentException("Authentication object cannot be null");
        }
        this.authentication = authentication;
    }

    public final boolean hasAuthority(String authority) {
        return hasAnyAuthority(authority);
    }

    public final boolean hasAnyAuthority(String... authorities) {
        return hasAnyAuthorityName(null, authorities);
    }

    public final boolean hasRole(String role) {
        return hasAnyRole(role);
    }

    public final boolean hasAnyRole(String... roles) {
        return hasAnyAuthorityName(defaultRolePrefix, roles);
    }

    private boolean hasAnyAuthorityName(String prefix, String... roles) {
        Set<String> roleSet = getAuthoritySet();

        for (String role : roles) {
            String defaultedRole = getRoleWithDefaultPrefix(prefix, role);
            if (roleSet.contains(defaultedRole)) {
                return true;
            }
        }

        return false;
    }

    public final Authentication getAuthentication() {
        return authentication;
    }

    public final boolean permitAll() {
        return true;
    }

    public final boolean denyAll() {
        return false;
    }

    public final boolean isAnonymous() {
        return trustResolver.isAnonymous(authentication);
    }

    public final boolean isAuthenticated() {
        return !isAnonymous();
    }

    public final boolean isRememberMe() {
        return trustResolver.isRememberMe(authentication);
    }

    public final boolean isFullyAuthenticated() {
        return !trustResolver.isAnonymous(authentication)
                && !trustResolver.isRememberMe(authentication);
    }

    /**
     * Convenience method to access {@link Authentication#getPrincipal()} from
     * {@link #getAuthentication()}
     * @return
     */
    public Object getPrincipal() {
        return authentication.getPrincipal();
    }

    public void setTrustResolver(AuthenticationTrustResolver trustResolver) {
        this.trustResolver = trustResolver;
    }

    public void setRoleHierarchy(RoleHierarchy roleHierarchy) {
        this.roleHierarchy = roleHierarchy;
    }

    /**
     * <p>
     * Sets the default prefix to be added to {@link #hasAnyRole(String...)} or
     * {@link #hasRole(String)}. For example, if hasRole("ADMIN") or hasRole("ROLE_ADMIN")
     * is passed in, then the role ROLE_ADMIN will be used when the defaultRolePrefix is
     * "ROLE_" (default).
     * </p>
     *
     * <p>
     * If null or empty, then no default role prefix is used.
     * </p>
     *
     * @param defaultRolePrefix the default prefix to add to roles. Default "ROLE_".
     */
    public void setDefaultRolePrefix(String defaultRolePrefix) {
        this.defaultRolePrefix = defaultRolePrefix;
    }

    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }

    private Set<String> getAuthoritySet() {
        if (roles == null) {
            Collection<? extends GrantedAuthority> userAuthorities = authentication
                    .getAuthorities();

            if (roleHierarchy != null) {
                userAuthorities = roleHierarchy
                        .getReachableGrantedAuthorities(userAuthorities);
            }

            roles = AuthorityUtils.authorityListToSet(userAuthorities);
        }

        return roles;
    }

    public boolean hasPermission(Object target, Object permission) {
        return permissionEvaluator.hasPermission(authentication, target, permission);
    }

    public boolean hasPermission(Object targetId, String targetType, Object permission) {
        return permissionEvaluator.hasPermission(authentication, (Serializable) targetId,
                targetType, permission);
    }

    public void setPermissionEvaluator(PermissionEvaluator permissionEvaluator) {
        this.permissionEvaluator = permissionEvaluator;
    }

    /**
     * Prefixes role with defaultRolePrefix if defaultRolePrefix is non-null and if role
     * does not already start with defaultRolePrefix.
     *
     * @param defaultRolePrefix
     * @param role
     * @return
     */
    private static String getRoleWithDefaultPrefix(String defaultRolePrefix, String role) {
        if (role == null) {
            return role;
        }
        if (defaultRolePrefix == null || defaultRolePrefix.length() == 0) {
            return role;
        }
        if (role.startsWith(defaultRolePrefix)) {
            return role;
        }
        return defaultRolePrefix + role;
    }
}
