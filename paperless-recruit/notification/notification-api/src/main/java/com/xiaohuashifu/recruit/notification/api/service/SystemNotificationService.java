package com.xiaohuashifu.recruit.notification.api.service;

import com.xiaohuashifu.recruit.common.exception.NotFoundServiceException;
import com.xiaohuashifu.recruit.common.query.QueryResult;
import com.xiaohuashifu.recruit.notification.api.dto.SystemNotificationDTO;
import com.xiaohuashifu.recruit.notification.api.query.SystemNotificationQuery;
import com.xiaohuashifu.recruit.notification.api.request.SendSystemNotificationRequest;

import javax.validation.constraints.NotNull;

/**
 * 描述：系统通知服务
 *
 * @author xhsf
 * @create 2020/12/15 20:35
 */
public interface SystemNotificationService {

    /**
     * 发送系统消息
     *
     * @private 内部方法
     *
     * @param request SendSystemNotificationRequest
     * @return 发送结果
     */
    SystemNotificationDTO sendSystemNotification(@NotNull SendSystemNotificationRequest request)
            throws NotFoundServiceException;

    /**
     * 获取系统通知
     *
     * @param id 系统通知编号
     * @return SystemNotificationDTO
     */
    SystemNotificationDTO getSystemNotification(Long id) throws NotFoundServiceException;

    /**
     * 查询系统通知
     *
     * @permission 只能查询自身的通知，即必须设置 userId
     *
     * @param query 查询参数
     * @return QueryResult<SystemNotificationDTO> 查询结果，可能返回空列表
     */
    QueryResult<SystemNotificationDTO> listSystemNotifications(SystemNotificationQuery query);

    /**
     * 查看系统通知，也就是把系统通知的 checked 该为 true
     *
     * @permission 需要该通知所属的用户（userId）是该用户本身
     *
     * @param id 系统通知编号
     * @return 更新后的系统通知
     */
    SystemNotificationDTO checkSystemNotification(Long id);

}
