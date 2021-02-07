package com.xiaohuashifu.recruit.notification.api.query;

import com.xiaohuashifu.recruit.common.constant.QueryConstants;
import com.xiaohuashifu.recruit.notification.api.constant.SystemNotificationTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;

/**
 * 描述：系统通知查询参数
 *
 * @author xhsf
 * @create 2020/12/15 13:29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemNotificationQuery implements Serializable {

    /**
     * 页码
     */
    @NotNull
    @Positive
    private Long pageNum;

    /**
     * 页条数
     */
    @NotNull
    @Positive
    @Max(value = QueryConstants.MAX_PAGE_SIZE)
    private Long pageSize;

    /**
     * 系统通知用户编号
     */
    private Long userId;

    /**
     * 系统通知标题，可右模糊
     */
    private String notificationTitle;

    /**
     * 系统通知类型
     */
    private SystemNotificationTypeEnum notificationType;

    /**
     * 系统通知是否已经查看
     */
    private Boolean checked;

}
