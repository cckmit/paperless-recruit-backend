package com.xiaohuashifu.recruit.user.api.dto;

import com.xiaohuashifu.recruit.common.constant.AppEnum;

import java.io.Serializable;

/**
 * 描述：AuthOpenId 的传输对象
 *
 * @author: xhsf
 * @create: 2020/11/20 19:40
 */
public class AuthOpenIdDTO implements Serializable {

    /**
     * AuthOpenId 的编号
     */
    private Long id;

    /**
     * 对应该 AuthOpenId 的用户的编号
     */
    private Long userId;

    /**
     * 该 OpenId 所属应用
     */
    private AppEnum app;

    /**
     * OpenId
     */
    private String openId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public AppEnum getApp() {
        return app;
    }

    public void setApp(AppEnum app) {
        this.app = app;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    @Override
    public String toString() {
        return "AuthOpenIdDTO{" +
                "id=" + id +
                ", userId=" + userId +
                ", app=" + app +
                ", openId='" + openId + '\'' +
                '}';
    }

    public static final class Builder {
        private Long id;
        private Long userId;
        private AppEnum app;
        private String openId;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public Builder app(AppEnum app) {
            this.app = app;
            return this;
        }

        public Builder openId(String openId) {
            this.openId = openId;
            return this;
        }

        public AuthOpenIdDTO build() {
            AuthOpenIdDTO authOpenIdDTO = new AuthOpenIdDTO();
            authOpenIdDTO.setId(id);
            authOpenIdDTO.setUserId(userId);
            authOpenIdDTO.setApp(app);
            authOpenIdDTO.setOpenId(openId);
            return authOpenIdDTO;
        }
    }
}
