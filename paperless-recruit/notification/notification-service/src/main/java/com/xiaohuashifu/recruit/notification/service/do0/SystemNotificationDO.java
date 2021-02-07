package com.xiaohuashifu.recruit.notification.service.do0;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xiaohuashifu.recruit.notification.api.constant.SystemNotificationTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 描述：系统通知数据对象
 *
 * @author xhsf
 * @create 2020/12/15 20:43
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("system_notification")
public class SystemNotificationDO {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private String notificationTitle;
    private SystemNotificationTypeEnum notificationType;
    private String notificationContent;
    private LocalDateTime notificationTime;
    @TableField("is_checked")
    private Boolean checked;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
