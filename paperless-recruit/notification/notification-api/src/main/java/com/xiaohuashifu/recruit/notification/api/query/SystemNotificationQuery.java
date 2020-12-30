package com.xiaohuashifu.recruit.notification.api.query;

import com.xiaohuashifu.recruit.common.constant.QueryConstants;
import com.xiaohuashifu.recruit.notification.api.constant.SystemNotificationTypeEnum;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.List;

/**
 * 描述：系统通知查询参数
 *
 * @author xhsf
 * @create 2020/12/15 13:29
 */
public class SystemNotificationQuery implements Serializable {

    /**
     * 页码
     */
    @NotNull(message = "The pageNum can't be null.")
    @Positive(message = "The pageNum must be greater than 0.")
    private Long pageNum;

    /**
     * 页条数
     */
    @NotNull(message = "The pageSize can't be null.")
    @Positive(message = "The pageSize must be greater than 0.")
    @Max(value = QueryConstants.MAX_PAGE_SIZE,
            message = "The pageSize must be less than or equal to " + QueryConstants.MAX_PAGE_SIZE + ".")
    private Long pageSize;

    /**
     * 系统通知编号
     */
    private Long id;

    /**
     * 系统通知编号列表
     */
    private List<Long> ids;

    /**
     * 系统通知用户编号
     */
    private Long userId;

    /**
     * 系统通知标题，可模糊
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

    /**
     * 按照通知时间排序
     */
    private Boolean orderByNotificationTime;

    /**
     * 按照组通知时间逆序排序
     */
    private Boolean orderByNotificationTimeDesc;

    /**
     * 按照是否已经查看排序
     */
    private Boolean orderByChecked;

    /**
     * 按照是否已经查看逆序排序
     */
    private Boolean orderByCheckedDesc;

    /**
     * 按照创建时间排序
     */
    private Boolean orderByCreateTime;

    /**
     * 按照创建时间逆序排序
     */
    private Boolean orderByCreateTimeDesc;

    /**
     * 按照更新时间排序
     */
    private Boolean orderByUpdateTime;

    /**
     * 按照更新时间逆序排序
     */
    private Boolean orderByUpdateTimeDesc;

    public Long getPageNum() {
        return pageNum;
    }

    public void setPageNum(Long pageNum) {
        this.pageNum = pageNum;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
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

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Boolean getOrderByNotificationTime() {
        return orderByNotificationTime;
    }

    public void setOrderByNotificationTime(Boolean orderByNotificationTime) {
        this.orderByNotificationTime = orderByNotificationTime;
    }

    public Boolean getOrderByNotificationTimeDesc() {
        return orderByNotificationTimeDesc;
    }

    public void setOrderByNotificationTimeDesc(Boolean orderByNotificationTimeDesc) {
        this.orderByNotificationTimeDesc = orderByNotificationTimeDesc;
    }

    public Boolean getOrderByChecked() {
        return orderByChecked;
    }

    public void setOrderByChecked(Boolean orderByChecked) {
        this.orderByChecked = orderByChecked;
    }

    public Boolean getOrderByCheckedDesc() {
        return orderByCheckedDesc;
    }

    public void setOrderByCheckedDesc(Boolean orderByCheckedDesc) {
        this.orderByCheckedDesc = orderByCheckedDesc;
    }

    public Boolean getOrderByCreateTime() {
        return orderByCreateTime;
    }

    public void setOrderByCreateTime(Boolean orderByCreateTime) {
        this.orderByCreateTime = orderByCreateTime;
    }

    public Boolean getOrderByCreateTimeDesc() {
        return orderByCreateTimeDesc;
    }

    public void setOrderByCreateTimeDesc(Boolean orderByCreateTimeDesc) {
        this.orderByCreateTimeDesc = orderByCreateTimeDesc;
    }

    public Boolean getOrderByUpdateTime() {
        return orderByUpdateTime;
    }

    public void setOrderByUpdateTime(Boolean orderByUpdateTime) {
        this.orderByUpdateTime = orderByUpdateTime;
    }

    public Boolean getOrderByUpdateTimeDesc() {
        return orderByUpdateTimeDesc;
    }

    public void setOrderByUpdateTimeDesc(Boolean orderByUpdateTimeDesc) {
        this.orderByUpdateTimeDesc = orderByUpdateTimeDesc;
    }

    @Override
    public String toString() {
        return "SystemNotificationQuery{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", id=" + id +
                ", ids=" + ids +
                ", userId=" + userId +
                ", notificationTitle='" + notificationTitle + '\'' +
                ", notificationType=" + notificationType +
                ", checked=" + checked +
                ", orderByNotificationTime=" + orderByNotificationTime +
                ", orderByNotificationTimeDesc=" + orderByNotificationTimeDesc +
                ", orderByChecked=" + orderByChecked +
                ", orderByCheckedDesc=" + orderByCheckedDesc +
                ", orderByCreateTime=" + orderByCreateTime +
                ", orderByCreateTimeDesc=" + orderByCreateTimeDesc +
                ", orderByUpdateTime=" + orderByUpdateTime +
                ", orderByUpdateTimeDesc=" + orderByUpdateTimeDesc +
                '}';
    }

    public static final class Builder {
        private Long pageNum;
        private Long pageSize;
        private Long id;
        private List<Long> ids;
        private Long userId;
        private String notificationTitle;
        private SystemNotificationTypeEnum notificationType;
        private Boolean checked;
        private Boolean orderByNotificationTime;
        private Boolean orderByNotificationTimeDesc;
        private Boolean orderByChecked;
        private Boolean orderByCheckedDesc;
        private Boolean orderByCreateTime;
        private Boolean orderByCreateTimeDesc;
        private Boolean orderByUpdateTime;
        private Boolean orderByUpdateTimeDesc;

        public Builder pageNum(Long pageNum) {
            this.pageNum = pageNum;
            return this;
        }

        public Builder pageSize(Long pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder ids(List<Long> ids) {
            this.ids = ids;
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

        public Builder checked(Boolean checked) {
            this.checked = checked;
            return this;
        }

        public Builder orderByNotificationTime(Boolean orderByNotificationTime) {
            this.orderByNotificationTime = orderByNotificationTime;
            return this;
        }

        public Builder orderByNotificationTimeDesc(Boolean orderByNotificationTimeDesc) {
            this.orderByNotificationTimeDesc = orderByNotificationTimeDesc;
            return this;
        }

        public Builder orderByChecked(Boolean orderByChecked) {
            this.orderByChecked = orderByChecked;
            return this;
        }

        public Builder orderByCheckedDesc(Boolean orderByCheckedDesc) {
            this.orderByCheckedDesc = orderByCheckedDesc;
            return this;
        }

        public Builder orderByCreateTime(Boolean orderByCreateTime) {
            this.orderByCreateTime = orderByCreateTime;
            return this;
        }

        public Builder orderByCreateTimeDesc(Boolean orderByCreateTimeDesc) {
            this.orderByCreateTimeDesc = orderByCreateTimeDesc;
            return this;
        }

        public Builder orderByUpdateTime(Boolean orderByUpdateTime) {
            this.orderByUpdateTime = orderByUpdateTime;
            return this;
        }

        public Builder orderByUpdateTimeDesc(Boolean orderByUpdateTimeDesc) {
            this.orderByUpdateTimeDesc = orderByUpdateTimeDesc;
            return this;
        }

        public SystemNotificationQuery build() {
            SystemNotificationQuery systemNotificationQuery = new SystemNotificationQuery();
            systemNotificationQuery.setPageNum(pageNum);
            systemNotificationQuery.setPageSize(pageSize);
            systemNotificationQuery.setId(id);
            systemNotificationQuery.setIds(ids);
            systemNotificationQuery.setUserId(userId);
            systemNotificationQuery.setNotificationTitle(notificationTitle);
            systemNotificationQuery.setNotificationType(notificationType);
            systemNotificationQuery.setChecked(checked);
            systemNotificationQuery.setOrderByNotificationTime(orderByNotificationTime);
            systemNotificationQuery.setOrderByNotificationTimeDesc(orderByNotificationTimeDesc);
            systemNotificationQuery.setOrderByChecked(orderByChecked);
            systemNotificationQuery.setOrderByCheckedDesc(orderByCheckedDesc);
            systemNotificationQuery.setOrderByCreateTime(orderByCreateTime);
            systemNotificationQuery.setOrderByCreateTimeDesc(orderByCreateTimeDesc);
            systemNotificationQuery.setOrderByUpdateTime(orderByUpdateTime);
            systemNotificationQuery.setOrderByUpdateTimeDesc(orderByUpdateTimeDesc);
            return systemNotificationQuery;
        }
    }
}
