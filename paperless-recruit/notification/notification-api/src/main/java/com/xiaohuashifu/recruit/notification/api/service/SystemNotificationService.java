package com.xiaohuashifu.recruit.notification.api.service;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.notification.api.dto.SystemNotificationDTO;
import com.xiaohuashifu.recruit.notification.api.po.SendSystemNotificationPO;
import com.xiaohuashifu.recruit.notification.api.query.SystemNotificationQuery;

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
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.User.NotExist: 对应编号的用户不存在
     *
     * @param sendSystemNotificationPO 发送系统消息参数对象
     * @return 发送结果
     */
    Result<SystemNotificationDTO> sendSystemNotification(
            @NotNull(message = "The sendSystemNotificationPO can't be null.")
                    SendSystemNotificationPO sendSystemNotificationPO);

    /**
     * 查询系统通知
     *
     * @permission 只能查询自身的通知，即必须设置 userId
     *
     * @errorCode InvalidParameter: 查询参数格式错误
     *
     * @param query 查询参数
     * @return PageInfo<SystemNotificationDTO> 查询结果，可能返回空列表
     */
    Result<PageInfo<SystemNotificationDTO>> listSystemNotifications(SystemNotificationQuery query);

    /**
     * 查看系统通知，也就是把系统通知的 checked 该为 true
     *
     * @permission 需要该通知所属的用户（userId）是该用户本身
     *
     * @errorCode InvalidParameter: 通知编号格式错误
     *              InvalidParameter.NotExist: 该通知不存在
     *
     * @param id 系统通知编号
     * @return 更新后的系统通知
     */
    Result<SystemNotificationDTO> checkSystemNotification(Long id);

}
