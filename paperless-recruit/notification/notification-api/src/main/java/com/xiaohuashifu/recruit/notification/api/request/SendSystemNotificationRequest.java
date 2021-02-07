package com.xiaohuashifu.recruit.notification.api.request;

import com.xiaohuashifu.recruit.notification.api.constant.SystemNotificationConstants;
import com.xiaohuashifu.recruit.notification.api.constant.SystemNotificationTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@NoArgsConstructor
@AllArgsConstructor
public class SendSystemNotificationRequest implements Serializable {

    /**
     * 通知的目标用户
     */
    @NotNull
    @Positive
    private Long userId;

    /**
     * 通知标题
     */
    @NotBlank
    @Size(max = SystemNotificationConstants.MAX_NOTIFICATION_TITLE_LENGTH)
    private String notificationTitle;

    /**
     * 通知类型
     */
    @NotNull
    private SystemNotificationTypeEnum notificationType;

    /**
     * 通知内容
     */
    @NotBlank
    @Size(max = SystemNotificationConstants.MAX_NOTIFICATION_CONTENT_LENGTH)
    private String notificationContent;
}
