package com.xiaohuashifu.recruit.authentication.service.pojo.do0;

import com.xiaohuashifu.recruit.common.constant.App;
import com.xiaohuashifu.recruit.common.constant.Platform;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;

/**
 * 描述：
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/10/30 15:05
 */
@Alias("authOpenid")
public class AuthOpenidDO {
    private Long id;

    private Long userId;

    private App appName;

    private Platform thirdPartyPlatformName;

    private String openid;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    public AuthOpenidDO() {
    }

    public AuthOpenidDO(Long id, Long userId, App appName, Platform thirdPartyPlatformName,
                        String openid, LocalDateTime createTime, LocalDateTime updateTime) {
        this.id = id;
        this.userId = userId;
        this.appName = appName;
        this.thirdPartyPlatformName = thirdPartyPlatformName;
        this.openid = openid;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

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

    public App getAppName() {
        return appName;
    }

    public void setAppName(App appName) {
        this.appName = appName;
    }

    public Platform getThirdPartyPlatformName() {
        return thirdPartyPlatformName;
    }

    public void setThirdPartyPlatformName(Platform thirdPartyPlatformName) {
        this.thirdPartyPlatformName = thirdPartyPlatformName;
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
        return "AuthOpenidDO{" +
                "id=" + id +
                ", userId=" + userId +
                ", appName=" + appName +
                ", thirdPartyPlatformName=" + thirdPartyPlatformName +
                ", openid='" + openid + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }


    public static final class Builder {
        private Long id;
        private Long userId;
        private App appName;
        private Platform thirdPartyPlatformName;
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

        public Builder appName(App appName) {
            this.appName = appName;
            return this;
        }

        public Builder thirdPartyPlatformName(Platform thirdPartyPlatformName) {
            this.thirdPartyPlatformName = thirdPartyPlatformName;
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

        public AuthOpenidDO build() {
            AuthOpenidDO authOpenidDO = new AuthOpenidDO();
            authOpenidDO.setId(id);
            authOpenidDO.setUserId(userId);
            authOpenidDO.setAppName(appName);
            authOpenidDO.setThirdPartyPlatformName(thirdPartyPlatformName);
            authOpenidDO.setOpenid(openid);
            authOpenidDO.setCreateTime(createTime);
            authOpenidDO.setUpdateTime(updateTime);
            return authOpenidDO;
        }
    }
}
