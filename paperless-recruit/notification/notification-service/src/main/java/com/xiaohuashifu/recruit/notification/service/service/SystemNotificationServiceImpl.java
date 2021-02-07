package com.xiaohuashifu.recruit.notification.service.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaohuashifu.recruit.common.exception.NotFoundServiceException;
import com.xiaohuashifu.recruit.common.query.QueryResult;
import com.xiaohuashifu.recruit.notification.api.dto.SystemNotificationDTO;
import com.xiaohuashifu.recruit.notification.api.query.SystemNotificationQuery;
import com.xiaohuashifu.recruit.notification.api.request.SendSystemNotificationRequest;
import com.xiaohuashifu.recruit.notification.api.service.SystemNotificationService;
import com.xiaohuashifu.recruit.notification.service.assembler.SystemNotificationAssembler;
import com.xiaohuashifu.recruit.notification.service.dao.SystemNotificationMapper;
import com.xiaohuashifu.recruit.notification.service.do0.SystemNotificationDO;
import com.xiaohuashifu.recruit.user.api.service.UserService;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 描述：系统通知服务实现
 *
 * @author xhsf
 * @create 2020/12/15 21:09
 */
@Service
public class SystemNotificationServiceImpl implements SystemNotificationService {

    private final SystemNotificationMapper systemNotificationMapper;

    private final SystemNotificationAssembler systemNotificationAssembler;

    @Reference
    private UserService userService;

    public SystemNotificationServiceImpl(SystemNotificationMapper systemNotificationMapper,
                                         SystemNotificationAssembler systemNotificationAssembler) {
        this.systemNotificationMapper = systemNotificationMapper;
        this.systemNotificationAssembler = systemNotificationAssembler;
    }

    @Override
    public SystemNotificationDTO sendSystemNotification(SendSystemNotificationRequest request) {
        // 判断目标用户存不存在
        userService.getUser(request.getUserId());

        // 发送系统通知
        SystemNotificationDO systemNotificationDOForInsert =
                systemNotificationAssembler.sendSystemNotificationRequestToSystemNotificationDO(request);
        systemNotificationDOForInsert.setNotificationTime(LocalDateTime.now());
        systemNotificationMapper.insert(systemNotificationDOForInsert);

        // 获取发送结果
        return getSystemNotification(systemNotificationDOForInsert.getId());
    }

    @Override
    public SystemNotificationDTO getSystemNotification(Long id) {
        SystemNotificationDO systemNotificationDO = systemNotificationMapper.selectById(id);
        if (systemNotificationDO == null) {
            throw new NotFoundServiceException("systemNotification", "id", id);
        }
        return systemNotificationAssembler.systemNotificationDOToSystemNotificationDTO(systemNotificationDO);
    }

    @Override
    public QueryResult<SystemNotificationDTO> listSystemNotifications(SystemNotificationQuery query) {
        LambdaQueryWrapper<SystemNotificationDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(query.getUserId() != null, SystemNotificationDO::getUserId, query.getUserId())
                .likeRight(query.getNotificationTitle() != null, SystemNotificationDO::getNotificationTitle,
                        query.getNotificationTitle())
                .eq(query.getNotificationType() != null, SystemNotificationDO::getNotificationType,
                        query.getNotificationType())
                .eq(query.getChecked() != null, SystemNotificationDO::getChecked, query.getChecked());

        Page<SystemNotificationDO> page = new Page<>(query.getPageNum(), query.getPageSize(), true);
        systemNotificationMapper.selectPage(page, wrapper);
        List<SystemNotificationDTO> systemNotificationDTOS = page.getRecords()
                .stream()
                .map(systemNotificationAssembler::systemNotificationDOToSystemNotificationDTO)
                .collect(Collectors.toList());
        return new QueryResult<>(page.getTotal(), systemNotificationDTOS);
    }

    @Override
    public SystemNotificationDTO checkSystemNotification(Long id) {
        SystemNotificationDO systemNotificationDOForUpdate =
                SystemNotificationDO.builder().id(id).checked(true).build();
        systemNotificationMapper.updateById(systemNotificationDOForUpdate);
        return getSystemNotification(id);
    }

}
