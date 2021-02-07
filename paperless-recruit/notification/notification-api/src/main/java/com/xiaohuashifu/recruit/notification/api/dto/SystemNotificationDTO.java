package com.xiaohuashifu.recruit.notification.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 描述：系统通知传输对象
 *
 * @author xhsf
 * @create 2020/12/15 20:43
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

}
