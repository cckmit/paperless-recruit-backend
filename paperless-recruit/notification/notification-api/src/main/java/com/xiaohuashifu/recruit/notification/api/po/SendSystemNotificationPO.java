package com.xiaohuashifu.recruit.notification.api.po;

import com.xiaohuashifu.recruit.notification.api.constant.SystemNotificationConstants;
import com.xiaohuashifu.recruit.notification.api.constant.SystemNotificationTypeEnum;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 描述：发送系统通知参数对象
 *
 * @author xhsf
 * @create 2020/12/15 20:53
 */
public class SendSystemNotificationPO implements Serializable {

    /**
     * 通知的目标用户
     */
    @NotNull(message = "The userId can't be null.")
    @Positive(message = "The userId must be greater than 0.")
    private Long userId;

    /**
     * 通知标题
     */
    @NotBlank(message = "The notificationTitle can't be blank.")
    @Size(max = SystemNotificationConstants.MAX_NOTIFICATION_TITLE_LENGTH,
            message = "The length of notificationTitle must not be greater than "
                    + SystemNotificationConstants.MAX_NOTIFICATION_TITLE_LENGTH + ".")
    private String notificationTitle;

    /**
     * 通知类型
     */
    @NotNull(message = "The notificationType can't be null.")
    private SystemNotificationTypeEnum notificationType;

    /**
     * 通知内容
     */
    @NotBlank(message = "The notificationContent can't be blank.")
    @Size(max = SystemNotificationConstants.MAX_NOTIFICATION_CONTENT_LENGTH,
            message = "The length of notificationContent must not be greater than "
                    + SystemNotificationConstants.MAX_NOTIFICATION_CONTENT_LENGTH + ".")
    private String notificationContent;

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

    @Override
    public String toString() {
        return "SendSystemNotificationPO{" +
                "userId=" + userId +
                ", notificationTitle='" + notificationTitle + '\'' +
                ", notificationType=" + notificationType +
                ", notificationContent='" + notificationContent + '\'' +
                '}';
    }

    public static final class Builder {
        private Long userId;
        private String notificationTitle;
        private SystemNotificationTypeEnum notificationType;
        private String notificationContent;

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

        public SendSystemNotificationPO build() {
            SendSystemNotificationPO sendSystemNotificationPO = new SendSystemNotificationPO();
            sendSystemNotificationPO.setUserId(userId);
            sendSystemNotificationPO.setNotificationTitle(notificationTitle);
            sendSystemNotificationPO.setNotificationType(notificationType);
            sendSystemNotificationPO.setNotificationContent(notificationContent);
            return sendSystemNotificationPO;
        }
    }
}
