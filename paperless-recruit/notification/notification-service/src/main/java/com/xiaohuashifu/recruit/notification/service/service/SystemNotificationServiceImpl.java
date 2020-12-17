package com.xiaohuashifu.recruit.notification.service.service;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.recruit.common.result.ErrorCodeEnum;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.notification.api.dto.SystemNotificationDTO;
import com.xiaohuashifu.recruit.notification.api.po.SendSystemNotificationPO;
import com.xiaohuashifu.recruit.notification.api.query.SystemNotificationQuery;
import com.xiaohuashifu.recruit.notification.api.service.SystemNotificationService;
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

    @Reference
    private UserService userService;

    public SystemNotificationServiceImpl(SystemNotificationMapper systemNotificationMapper) {
        this.systemNotificationMapper = systemNotificationMapper;
    }

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
    @Override
    public Result<SystemNotificationDTO> sendSystemNotification(SendSystemNotificationPO sendSystemNotificationPO) {
        // 判断目标用户存不存在
        Result<SystemNotificationDTO> userExistsResult = userService.userExists(sendSystemNotificationPO.getUserId());
        if (!userExistsResult.isSuccess()) {
            return userExistsResult;
        }

        // 发送系统通知
        SystemNotificationDO systemNotificationDO = new SystemNotificationDO.Builder()
                .userId(sendSystemNotificationPO.getUserId())
                .notificationTitle(sendSystemNotificationPO.getNotificationTitle())
                .notificationType(sendSystemNotificationPO.getNotificationType())
                .notificationContent(sendSystemNotificationPO.getNotificationContent())
                .notificationTime(LocalDateTime.now()).build();
        systemNotificationMapper.insertSystemNotification(systemNotificationDO);

        // 获取发送结果
        return getSystemNotification(systemNotificationDO.getId());
    }

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
    @Override
    public Result<PageInfo<SystemNotificationDTO>> listSystemNotifications(SystemNotificationQuery query) {
        List<SystemNotificationDO> systemNotificationDOList = systemNotificationMapper.listSystemNotifications(query);
        List<SystemNotificationDTO> systemNotificationDTOList = systemNotificationDOList
                .stream()
                .map(systemNotificationDO -> new SystemNotificationDTO.Builder()
                        .id(systemNotificationDO.getId())
                        .userId(systemNotificationDO.getUserId())
                        .notificationTitle(systemNotificationDO.getNotificationTitle())
                        .notificationType(systemNotificationDO.getNotificationType().name())
                        .notificationContent(systemNotificationDO.getNotificationContent())
                        .notificationTime(systemNotificationDO.getNotificationTime())
                        .checked(systemNotificationDO.getChecked())
                        .build())
                .collect(Collectors.toList());
        PageInfo<SystemNotificationDTO> pageInfo = new PageInfo<>(systemNotificationDTOList);
        return Result.success(pageInfo);
    }

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
    @Override
    public Result<SystemNotificationDTO> checkSystemNotification(Long id) {
        // 判断该消息是否存在
        int count = systemNotificationMapper.count(id);
        if (count < 1) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_EXIST,
                    "The systemNotification does not exist.");
        }

        // 更新 checked 字段
        systemNotificationMapper.updateChecked(id, true);
        return getSystemNotification(id);
    }

    /**
     * 获取系统通知
     *
     * @param id 系统通知编号
     * @return SystemNotificationDTO
     */
    private Result<SystemNotificationDTO> getSystemNotification(Long id) {
        // 获取系统通知
        SystemNotificationDO systemNotificationDO = systemNotificationMapper.getSystemNotification(id);
        if (systemNotificationDO == null) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_FOUND,
            "The system notification does not exist.");
        }

        // 封装成 DTO
        SystemNotificationDTO systemNotificationDTO = new SystemNotificationDTO.Builder()
                .id(systemNotificationDO.getId())
                .userId(systemNotificationDO.getUserId())
                .notificationTitle(systemNotificationDO.getNotificationTitle())
                .notificationType(systemNotificationDO.getNotificationType().name())
                .notificationContent(systemNotificationDO.getNotificationContent())
                .notificationTime(systemNotificationDO.getNotificationTime())
                .checked(systemNotificationDO.getChecked())
                .build();
        return Result.success(systemNotificationDTO);
    }

}
