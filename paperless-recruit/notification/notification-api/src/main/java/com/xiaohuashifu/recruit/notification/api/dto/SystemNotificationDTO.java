package com.xiaohuashifu.recruit.notification.api.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 描述：系统通知传输对象
 *
 * @author xhsf
 * @create 2020/12/15 20:43
 */
public class SystemNotificationDTO implements Serializable {
    /**
     * 通知编号
     */
    private Long id;

    /**
     * 通知的目标用户
     */
    private Long userId;

    /**
     * 通知标题
     */
    private String notificationTitle;

    /**
     * @see com.xiaohuashifu.recruit.notification.api.constant.SystemNotificationTypeEnum
     * 通知类型
     */
    private String notificationType;

    /**
     * 通知内容
     */
    private String notificationContent;

    /**
     * 通知时间
     */
    private LocalDateTime notificationTime;

    /**
     * 是否已经查看
     */
    private Boolean checked;

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

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
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

    @Override
    public String toString() {
        return "SystemNotificationDTO{" +
                "id=" + id +
                ", userId=" + userId +
                ", notificationTitle='" + notificationTitle + '\'' +
                ", notificationType='" + notificationType + '\'' +
                ", notificationContent='" + notificationContent + '\'' +
                ", notificationTime=" + notificationTime +
                ", checked=" + checked +
                '}';
    }

    public static final class Builder {
        private Long id;
        private Long userId;
        private String notificationTitle;
        private String notificationType;
        private String notificationContent;
        private LocalDateTime notificationTime;
        private Boolean checked;

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

        public Builder notificationType(String notificationType) {
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

        public SystemNotificationDTO build() {
            SystemNotificationDTO systemNotificationDTO = new SystemNotificationDTO();
            systemNotificationDTO.setId(id);
            systemNotificationDTO.setUserId(userId);
            systemNotificationDTO.setNotificationTitle(notificationTitle);
            systemNotificationDTO.setNotificationType(notificationType);
            systemNotificationDTO.setNotificationContent(notificationContent);
            systemNotificationDTO.setNotificationTime(notificationTime);
            systemNotificationDTO.setChecked(checked);
            return systemNotificationDTO;
        }
    }
}
