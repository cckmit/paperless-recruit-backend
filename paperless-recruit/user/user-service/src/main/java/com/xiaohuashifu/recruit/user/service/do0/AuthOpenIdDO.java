package com.xiaohuashifu.recruit.user.service.do0;

import com.xiaohuashifu.recruit.common.constant.AppEnum;

import java.time.LocalDateTime;

/**
 * 描述：AuthOpenId 表映射对象
 *
 * @author: xhsf
 * @create: 2020/10/30 15:05
 */
public class AuthOpenIdDO {
    private Long id;

    private Long userId;

    private AppEnum appName;

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

    public AppEnum getAppName() {
        return appName;
    }

    public void setAppName(AppEnum appName) {
        this.appName = appName;
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
        return "AuthOpenIdDO{" +
                "id=" + id +
                ", userId=" + userId +
                ", appName=" + appName +
                ", openId='" + openId + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

    public static final class Builder {
        private Long id;
        private Long userId;
        private AppEnum appName;
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

        public Builder appName(AppEnum appName) {
            this.appName = appName;
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

        public AuthOpenIdDO build() {
            AuthOpenIdDO authOpenIdDO = new AuthOpenIdDO();
            authOpenIdDO.setId(id);
            authOpenIdDO.setUserId(userId);
            authOpenIdDO.setAppName(appName);
            authOpenIdDO.setOpenId(openId);
            authOpenIdDO.setCreateTime(createTime);
            authOpenIdDO.setUpdateTime(updateTime);
            return authOpenIdDO;
        }
    }
}
