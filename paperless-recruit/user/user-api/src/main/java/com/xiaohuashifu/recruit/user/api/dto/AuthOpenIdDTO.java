package com.xiaohuashifu.recruit.user.api.dto;

import com.xiaohuashifu.recruit.common.constant.AppEnum;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 描述：AuthOpenId 的传输对象
 *
 * @author: xhsf
 * @create: 2020/11/20 19:40
 */
public class AuthOpenIdDTO implements Serializable {

    private Long id;

    @Positive
    private Long userId;

    private AppEnum app;

    @Size(min = 28, max = 28)
    private String openId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

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

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "AuthOpenIdDTO{" +
                "id=" + id +
                ", userId=" + userId +
                ", app=" + app +
                ", openId='" + openId + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

    public static final class Builder {
        private Long id;
        private Long userId;
        private AppEnum app;
        private String openId;
        private LocalDateTime createTime;
        private LocalDateTime updateTime;

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

        public Builder createTime(LocalDateTime createTime) {
            this.createTime = createTime;
            return this;
        }

        public Builder updateTime(LocalDateTime updateTime) {
            this.updateTime = updateTime;
            return this;
        }

        public AuthOpenIdDTO build() {
            AuthOpenIdDTO authOpenIdDTO = new AuthOpenIdDTO();
            authOpenIdDTO.setId(id);
            authOpenIdDTO.setUserId(userId);
            authOpenIdDTO.setApp(app);
            authOpenIdDTO.setOpenId(openId);
            authOpenIdDTO.setCreateTime(createTime);
            authOpenIdDTO.setUpdateTime(updateTime);
            return authOpenIdDTO;
        }
    }
}
