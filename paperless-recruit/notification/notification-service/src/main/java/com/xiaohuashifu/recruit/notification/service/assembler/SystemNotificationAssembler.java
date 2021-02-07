package com.xiaohuashifu.recruit.notification.service.assembler;

import com.xiaohuashifu.recruit.notification.api.dto.SystemNotificationDTO;
import com.xiaohuashifu.recruit.notification.api.request.SendSystemNotificationRequest;
import com.xiaohuashifu.recruit.notification.service.do0.SystemNotificationDO;
import org.mapstruct.Mapper;

/**
 * 描述：SystemNotification 的装配器
 *
 * @author xhsf
 * @create 2021/1/26 13:02
 */
@Mapper(componentModel = "spring")
public interface SystemNotificationAssembler {

    SystemNotificationDTO systemNotificationDOToSystemNotificationDTO(SystemNotificationDO systemNotificationDO);

    SystemNotificationDO sendSystemNotificationRequestToSystemNotificationDO(
            SendSystemNotificationRequest sendSystemNotificationRequest);

}
