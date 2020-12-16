package com.xiaohuashifu.recruit.notification.service.dao;

import com.xiaohuashifu.recruit.notification.api.query.SystemNotificationQuery;
import com.xiaohuashifu.recruit.notification.service.do0.SystemNotificationDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 描述：系统通知数据库映射
 *
 * @author xhsf
 * @create 2020/12/15 18:49
 */
public interface SystemNotificationMapper {

    int insertSystemNotification(SystemNotificationDO systemNotificationDO);

    SystemNotificationDO getSystemNotification(Long id);

    List<SystemNotificationDO> listSystemNotifications(SystemNotificationQuery query);

    int count(Long id);

    int updateChecked(@Param("id") Long id, @Param("checked") Boolean checked);

}
