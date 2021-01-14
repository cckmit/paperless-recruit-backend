package com.xiaohuashifu.recruit.facade.service.controller.v1.oauth.token.constant;

/**
 * 描述：认证类型
 *
 * @author xhsf
 * @create 2021/1/14 18:55
 */
public enum GrantTypeEnum {

    /**
     * 账号密码认证类型
     */
    PASSWORD("password"),

    /**
     * openId 认证类型
     */
    OPEN_ID("openId"),

    /**
     * 短信认证类型
     */
    SMS("sms"),

    /**
     * refreshToken 认证类型
     */
    REFRESH_TOKEN("refresh_token");

    /**
     * 认证类型
     */
    private final String grantType;

    GrantTypeEnum(String grantType) {
        this.grantType = grantType;
    }

    public String getGrantType() {
        return grantType;
    }

}
