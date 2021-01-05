package com.xiaohuashifu.recruit.notification.api.po;

import com.xiaohuashifu.recruit.notification.api.constant.SystemNotificationConstants;
import com.xiaohuashifu.recruit.notification.api.constant.SystemNotificationTypeEnum;
import lombok.Builder;
import lombok.Data;

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
@Data
@Builder
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
}
