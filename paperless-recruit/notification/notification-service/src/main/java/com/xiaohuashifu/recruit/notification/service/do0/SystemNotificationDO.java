package com.xiaohuashifu.recruit.notification.service.do0;

import com.xiaohuashifu.recruit.notification.api.constant.SystemNotificationTypeEnum;

import java.time.LocalDateTime;

/**
 * 描述：系统通知数据对象
 *
 * @author xhsf
 * @create 2020/12/15 20:43
 */
public class SystemNotificationDO {
    private Long id;
    private Long userId;
    private String notificationTitle;
    private SystemNotificationTypeEnum notificationType;
    private String notificationContent;
    private LocalDateTime notificationTime;
    private Boolean checked;
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

    public String getNotificationTitle() {
        return notificationTitle;
    }

    public void setNotificationTitle(String notificationTitle) {
        this.notificationTitle = notificationTitle;
    }

    public SystemNotificationTypeEnum getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(SystemNotificationTypeEnum notificationType) {
        this.notificationType = notificationType;
    }

    public String getNotificationContent() {
        return notificationContent;
    }

    public void setNotificationContent(String notificationContent) {
        this.notificationContent = notificationContent;
    }

    public LocalDateTime getNotificationTime() {
        return notificationTime;
    }

    public void setNotificationTime(LocalDateTime notificationTime) {
        this.notificationTime = notificationTime;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
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
        return "SystemNotificationDO{" +
                "id=" + id +
                ", userId=" + userId +
                ", notificationTitle='" + notificationTitle + '\'' +
                ", notificationType=" + notificationType +
                ", notificationContent='" + notificationContent + '\'' +
                ", notificationTime=" + notificationTime +
                ", checked=" + checked +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

    public static final class Builder {
        private Long id;
        private Long userId;
        private String notificationTitle;
        private SystemNotificationTypeEnum notificationType;
        private String notificationContent;
        private LocalDateTime notificationTime;
        private Boolean checked;
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

        public Builder notificationTitle(String notificationTitle) {
            this.notificationTitle = notificationTitle;
            return this;
        }

        public Builder notificationType(SystemNotificationTypeEnum notificationType) {
            this.notificationType = notificationType;
            return this;
        }

        public Builder notificationContent(String notificationContent) {
            this.notificationContent = notificationContent;
            return this;
        }

        public Builder notificationTime(LocalDateTime notificationTime) {
            this.notificationTime = notificationTime;
            return this;
        }

        public Builder checked(Boolean checked) {
            this.checked = checked;
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

        public SystemNotificationDO build() {
            SystemNotificationDO systemNotificationDO = new SystemNotificationDO();
            systemNotificationDO.setId(id);
            systemNotificationDO.setUserId(userId);
            systemNotificationDO.setNotificationTitle(notificationTitle);
            systemNotificationDO.setNotificationType(notificationType);
            systemNotificationDO.setNotificationContent(notificationContent);
            systemNotificationDO.setNotificationTime(notificationTime);
            systemNotificationDO.setChecked(checked);
            systemNotificationDO.setCreateTime(createTime);
            systemNotificationDO.setUpdateTime(updateTime);
            return systemNotificationDO;
        }
    }
}
