package com.xiaohuashifu.recruit.external.service.pojo.dto;

/**
 * 描述: 封装 Session 的各种属性
 *
 * @author xhsf
 * @create 2019-02-28 16:46
 */
public class WeChatMpSessionDTO {

    private String openId;
    private String sessionKey;
    private String unionId;
    private Integer errorCode;
    private String errorMessage;

    public WeChatMpSessionDTO() {
    }

    public WeChatMpSessionDTO(String openId, String sessionKey, String unionId, Integer errorCode,
                              String errorMessage) {
        this.openId = openId;
        this.sessionKey = sessionKey;
        this.unionId = unionId;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "WeChatMpSessionDTO{" +
                "openId='" + openId + '\'' +
                ", sessionKey='" + sessionKey + '\'' +
                ", unionId='" + unionId + '\'' +
                ", errorCode=" + errorCode +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
