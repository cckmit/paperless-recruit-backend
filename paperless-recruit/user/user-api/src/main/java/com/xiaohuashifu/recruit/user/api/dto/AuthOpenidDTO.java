package com.xiaohuashifu.recruit.user.api.dto;

import com.xiaohuashifu.recruit.common.constant.App;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 描述：
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/20 19:40
 */
public class AuthOpenidDTO implements Serializable {
    private Long id;

    @Positive
    private Long userId;

    private App app;

    @Size(min = 28, max = 28)
    private String openid;

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

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
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
        return "AuthOpenidDTO{" +
                "id=" + id +
                ", userId=" + userId +
                ", app=" + app +
                ", openid='" + openid + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }


    public static final class Builder {
        private Long id;
        private Long userId;
        private App app;
        private String openid;
        private LocalDateTime createTime;
        private LocalDateTime updateTime;

        public Builder() {
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public Builder app(App app) {
            this.app = app;
            return this;
        }

        public Builder openid(String openid) {
            this.openid = openid;
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

        public AuthOpenidDTO build() {
            AuthOpenidDTO authOpenidDTO = new AuthOpenidDTO();
            authOpenidDTO.setId(id);
            authOpenidDTO.setUserId(userId);
            authOpenidDTO.setApp(app);
            authOpenidDTO.setOpenid(openid);
            authOpenidDTO.setCreateTime(createTime);
            authOpenidDTO.setUpdateTime(updateTime);
            return authOpenidDTO;
        }
    }
}
