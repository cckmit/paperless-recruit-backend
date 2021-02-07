package com.xiaohuashifu.recruit.registration.service.service;

import com.alibaba.fastjson.JSONObject;
import com.xiaohuashifu.recruit.common.aspect.annotation.DistributedLock;
import com.xiaohuashifu.recruit.common.result.ErrorCodeEnum;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.notification.api.constant.SystemNotificationTypeEnum;
import com.xiaohuashifu.recruit.notification.api.request.SendSystemNotificationRequest;
import com.xiaohuashifu.recruit.notification.api.service.SystemNotificationService;
import com.xiaohuashifu.recruit.organization.api.dto.OrganizationDTO;
import com.xiaohuashifu.recruit.organization.api.service.OrganizationMemberService;
import com.xiaohuashifu.recruit.organization.api.service.OrganizationService;
import com.xiaohuashifu.recruit.registration.api.dto.InterviewerDTO;
import com.xiaohuashifu.recruit.registration.api.service.InterviewerService;
import com.xiaohuashifu.recruit.registration.service.assembler.InterviewerAssembler;
import com.xiaohuashifu.recruit.registration.service.dao.InterviewerMapper;
import com.xiaohuashifu.recruit.registration.service.do0.InterviewerDO;
import com.xiaohuashifu.recruit.user.api.service.RoleService;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;

import java.util.Objects;

/**
 * 描述：面试官服务
 *
 * @author xhsf
 * @create 2021/1/4 20:43
 */
@Service
public class InterviewerServiceImpl implements InterviewerService {

    private final InterviewerAssembler interviewerAssembler = InterviewerAssembler.INSTANCES;

    private final InterviewerMapper interviewerMapper;

    @Reference
    private OrganizationMemberService organizationMemberService;

    @Reference
    private OrganizationService organizationService;

    @Reference
    private RoleService roleService;

    @Reference
    private SystemNotificationService systemNotificationService;

    /**
     * 面试官角色的编号
     */
    private static final Long INTERVIEWER_ROLE_ID = 3L;

    /**
     * 更新面试官 available 状态的锁定键模式，{0}是面试官编号
     */
    private static final String UPDATE_INTERVIEWER_AVAILABLE_LOCK_KEY_PATTERN = "interviewer:{0}:update-available";

    public InterviewerServiceImpl(InterviewerMapper interviewerMapper) {
        this.interviewerMapper = interviewerMapper;
    }

    /**
     * 保存面试官
     *
     * @permission 必须是组织的主体
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 组织不存在
     *              Forbidden.Unavailable: 组织不可用
     *              Forbidden: 该组织成员不是该组织的
     *              OperationConflict: 面试官已经存在
     *
     * @param organizationId 组织编号
     * @param organizationMemberId 组织成员编号
     * @return 面试官对象
     */
    @Override
    public Result<InterviewerDTO> saveInterviewer(Long organizationId, Long organizationMemberId) {
        // 判断组织是否存在
        organizationService.getOrganization(organizationId);

        // 判断该成员是否是该组织的
        Long organizationId0 = organizationMemberService.getOrganizationId(organizationMemberId);
        if (!Objects.equals(organizationId, organizationId0)) {
            return Result.fail(ErrorCodeEnum.FORBIDDEN);
        }

        // 判断该组织是否已经存在该面试官
        int count = interviewerMapper.countByOrganizationMemberId(organizationMemberId);
        if (count > 0) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT, "The interviewer already exist.");
        }

        // 添加面试官
        InterviewerDO interviewerDO = InterviewerDO.builder()
                .organizationId(organizationId)
                .organizationMemberId(organizationMemberId)
                .build();
        interviewerMapper.insertInterviewer(interviewerDO);

        // 为面试官的组织成员的用户主体授予面试官角色
        Long userId = organizationMemberService.getUserId(organizationMemberId);
        roleService.createUserRole(userId, INTERVIEWER_ROLE_ID);

        // 发送成为面试官的系统通知
        sendBecomeInterviewerSystemNotification(userId, organizationId);

        // 获取创建的面试官对象
        return getInterviewer(interviewerDO.getId());
    }

    /**
     * 禁用面试官
     *
     * @permission 必须是面试官的主体
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 面试官不存在
     *              OperationConflict.Unmodified: 面试官已经不可用
     *              OperationConflict.Lock: 获取更新 available 的锁失败
     *
     * @param id 面试官编号
     * @return 禁用后的面试官对象
     */
    @DistributedLock(value = UPDATE_INTERVIEWER_AVAILABLE_LOCK_KEY_PATTERN, parameters = "#{#id}",
            errorMessage = "Failed to acquire update available interviewer lock.")
    @Override
    public Result<InterviewerDTO> disableInterviewer(Long id) {
        // 判断面试官是否存在
        int count = interviewerMapper.count(id);
        if (count < 1) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_EXIST,
                    "The interviewer does not exist.");
        }

        // 更新面试官状态
        count = interviewerMapper.updateAvailable(id, false);

        // 如果更新失败表示面试官已经不可用
        if (count < 1) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT_UNMODIFIED,
                    "The interviewer already unavailable.");
        }

        // 回收面试官的权限
        Long organizationMemberId = interviewerMapper.getOrganizationMemberId(id);
        Long userId = organizationMemberService.getUserId(organizationMemberId);
        roleService.removeUserRole(userId, INTERVIEWER_ROLE_ID);

        // 发送取消面试官的系统通知
        sendDisableOrEnableInterviewerSystemNotification(userId, organizationMemberId, "取消");

        // 获取禁用后的面试官
        return getInterviewer(id);
    }

    /**
     * 解禁面试官
     *
     * @permission 必须是面试官的主体
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 面试官不存在
     *              OperationConflict.Unmodified: 面试官已经可用
     *              OperationConflict.Lock: 获取更新 available 的锁失败
     *
     * @param id 面试官编号
     * @return 解禁后的面试官对象
     */
    @DistributedLock(value = UPDATE_INTERVIEWER_AVAILABLE_LOCK_KEY_PATTERN, parameters = "#{#id}",
            errorMessage = "Failed to acquire update available interviewer lock.")
    @Override
    public Result<InterviewerDTO> enableInterviewer(Long id) {
        // 判断面试官是否存在
        int count = interviewerMapper.count(id);
        if (count < 1) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_EXIST,
                    "The interviewer does not exist.");
        }

        // 更新面试官状态
        count = interviewerMapper.updateAvailable(id, true);

        // 如果更新失败表示面试官已经不可用
        if (count < 1) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT_UNMODIFIED,
                    "The interviewer already available.");
        }

        // 赋予面试官的成员所属用户主体面试官权限
        Long organizationMemberId = interviewerMapper.getOrganizationMemberId(id);
        Long userId = organizationMemberService.getUserId(organizationMemberId);
        roleService.createUserRole(userId, INTERVIEWER_ROLE_ID);

        // 发送恢复面试官的系统通知
        sendDisableOrEnableInterviewerSystemNotification(userId, organizationMemberId, "恢复");

        // 获取解禁后的面试官
        return getInterviewer(id);
    }

    /**
     * 获取组织编号
     *
     * @private 内部方法
     *
     * @param id 面试官编号
     * @return 组织编号，若找不到返回 null
     */
    @Override
    public Long getOrganizationId(Long id) {
        return interviewerMapper.getOrganizationId(id);
    }

    /**
     * 获取组织成员编号
     *
     * @private 内部方法
     *
     * @param id 面试官编号
     * @return 组织成员编号，若找不到返回 null
     */
    @Override
    public Long getOrganizationMemberId(Long id) {
        return interviewerMapper.getOrganizationMemberId(id);
    }

    /**
     * 获取面试官
     *
     * @param id 面试官编号
     * @return 面试官
     */
    private Result<InterviewerDTO> getInterviewer(Long id) {
        InterviewerDO interviewerDO = interviewerMapper.getInterviewer(id);
        return Result.success(interviewerAssembler.toDTO(interviewerDO));
    }

    /**
     * 发送成为面试官的系统通知
     *
     * @param userId 用户编号
     * @param organizationId 组织编号
     */
    private void sendBecomeInterviewerSystemNotification(Long userId, Long organizationId) {
        OrganizationDTO organizationDTO = organizationService.getOrganization(organizationId).getData();
        String abbreviationOrganizationName = organizationDTO.getAbbreviationOrganizationName();
        String notificationTitle = abbreviationOrganizationName + "已将您设置为的面试官";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", notificationTitle + "。您现在可以查看" + abbreviationOrganizationName
                + "的报名表，并进行面试工作啦！");
        jsonObject.put("organizationId", organizationId);
        String notificationContent = jsonObject.toJSONString();
        SendSystemNotificationRequest sendSystemNotificationPO = SendSystemNotificationRequest.builder()
                .userId(userId)
                .notificationType(SystemNotificationTypeEnum.INTERVIEWER)
                .notificationTitle(notificationTitle)
                .notificationContent(notificationContent)
                .build();
        systemNotificationService.sendSystemNotification(sendSystemNotificationPO);
    }

    /**
     * 发送取消或回复面试官资格的系统通知
     *
     * @param userId 用户编号
     * @param organizationMemberId 组织成员编号
     * @param disableOrEnable "取消" or "恢复"
     */
    private void sendDisableOrEnableInterviewerSystemNotification(
            Long userId, Long organizationMemberId, String disableOrEnable) {
        Long organizationId = organizationMemberService.getOrganizationId(organizationMemberId);
        OrganizationDTO organizationDTO = organizationService.getOrganization(organizationId).getData();
        String abbreviationOrganizationName = organizationDTO.getAbbreviationOrganizationName();
        String notificationTitle = abbreviationOrganizationName + "已" + disableOrEnable+ "您的面试官资格";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", notificationTitle);
        jsonObject.put("organizationId", organizationId);
        String notificationContent = jsonObject.toJSONString();
        SendSystemNotificationRequest sendSystemNotificationPO = SendSystemNotificationRequest.builder()
                .userId(userId)
                .notificationType(SystemNotificationTypeEnum.INTERVIEWER)
                .notificationTitle(notificationTitle)
                .notificationContent(notificationContent)
                .build();
        systemNotificationService.sendSystemNotification(sendSystemNotificationPO);
    }
}
