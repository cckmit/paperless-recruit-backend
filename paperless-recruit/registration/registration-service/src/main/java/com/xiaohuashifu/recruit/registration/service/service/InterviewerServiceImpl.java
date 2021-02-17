package com.xiaohuashifu.recruit.registration.service.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaohuashifu.recruit.common.aspect.annotation.DistributedLock;
import com.xiaohuashifu.recruit.common.exception.NotFoundServiceException;
import com.xiaohuashifu.recruit.common.exception.unprocessable.DuplicateServiceException;
import com.xiaohuashifu.recruit.common.exception.unprocessable.MisMatchServiceException;
import com.xiaohuashifu.recruit.common.exception.unprocessable.UnmodifiedServiceException;
import com.xiaohuashifu.recruit.notification.api.constant.SystemNotificationTypeEnum;
import com.xiaohuashifu.recruit.notification.api.request.SendSystemNotificationRequest;
import com.xiaohuashifu.recruit.organization.api.dto.OrganizationDTO;
import com.xiaohuashifu.recruit.organization.api.dto.OrganizationMemberDTO;
import com.xiaohuashifu.recruit.organization.api.service.OrganizationMemberService;
import com.xiaohuashifu.recruit.organization.api.service.OrganizationService;
import com.xiaohuashifu.recruit.registration.api.dto.InterviewerDTO;
import com.xiaohuashifu.recruit.registration.api.request.CreateInterviewerRequest;
import com.xiaohuashifu.recruit.registration.api.service.InterviewerService;
import com.xiaohuashifu.recruit.registration.service.assembler.InterviewerAssembler;
import com.xiaohuashifu.recruit.registration.service.dao.InterviewerMapper;
import com.xiaohuashifu.recruit.registration.service.do0.InterviewerDO;
import com.xiaohuashifu.recruit.registration.service.mq.NotificationTemplate;
import com.xiaohuashifu.recruit.user.api.service.RoleService;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * 描述：面试官服务
 *
 * @author xhsf
 * @create 2021/1/4 20:43
 */
@Service
public class InterviewerServiceImpl implements InterviewerService {

    private final InterviewerAssembler interviewerAssembler;

    private final InterviewerMapper interviewerMapper;

    private final NotificationTemplate notificationTemplate;

    @Reference
    private OrganizationMemberService organizationMemberService;

    @Reference
    private OrganizationService organizationService;

    @Reference
    private RoleService roleService;

    /**
     * 面试官角色的编号
     */
    private static final Long INTERVIEWER_ROLE_ID = 3L;

    /**
     * 更新面试官 available 状态的锁定键模式，{0}是面试官编号
     */
    private static final String UPDATE_INTERVIEWER_AVAILABLE_LOCK_KEY_PATTERN = "interviewer:{0}:update-available";

    public InterviewerServiceImpl(InterviewerAssembler interviewerAssembler, InterviewerMapper interviewerMapper,
                                  NotificationTemplate notificationTemplate) {
        this.interviewerAssembler = interviewerAssembler;
        this.interviewerMapper = interviewerMapper;
        this.notificationTemplate = notificationTemplate;
    }

    @Override
    @Transactional
    // TODO: 2021/2/8 消息队列保证最终一致性
    public InterviewerDTO createInterviewer(CreateInterviewerRequest request) {
        // 判断组织是否存在
        OrganizationDTO organizationDTO = organizationService.getOrganization(request.getOrganizationId());

        // 判断该成员是否是该组织的
        OrganizationMemberDTO organizationMemberDTO =
                organizationMemberService.getOrganizationMember(request.getOrganizationMemberId());
        if (!Objects.equals(request.getOrganizationId(), organizationMemberDTO.getOrganizationId())) {
            throw new MisMatchServiceException("组织编号与组织成员所属的组织不匹配");
        }

        // 判断该组织是否已经存在该面试官
        LambdaQueryWrapper<InterviewerDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(InterviewerDO::getOrganizationMemberId, request.getOrganizationMemberId());
        int count = interviewerMapper.selectCount(wrapper);
        if (count > 0) {
            throw new DuplicateServiceException("The interviewer already exist.");
        }

        // 添加面试官
        InterviewerDO interviewerDOForInsert = InterviewerDO.builder().organizationId(request.getOrganizationId())
                .organizationMemberId(request.getOrganizationMemberId()).build();
        interviewerMapper.insert(interviewerDOForInsert);

        // 为面试官的组织成员的用户主体授予面试官角色
        roleService.createUserRole(organizationMemberDTO.getUserId(), INTERVIEWER_ROLE_ID);

        // 发送成为面试官的系统通知
        sendBecomeInterviewerSystemNotification(organizationMemberDTO.getUserId(), organizationDTO);

        // 获取创建的面试官对象
        return getInterviewer(interviewerDOForInsert.getId());
    }

    @Override
    public InterviewerDTO getInterviewer(Long id) {
        InterviewerDO interviewerDO = interviewerMapper.selectById(id);
        if (interviewerDO == null) {
            throw new NotFoundServiceException("interviewer", "id", id);
        }
        return interviewerAssembler.interviewerDOToInterviewerDTO(interviewerDO);
    }

    @DistributedLock(value = UPDATE_INTERVIEWER_AVAILABLE_LOCK_KEY_PATTERN, parameters = "#{#id}",
            errorMessage = "Failed to acquire update available interviewer lock.")
    @Override
    @Transactional
    public InterviewerDTO disableInterviewer(Long id) {
        // 判断面试官是否存在
        InterviewerDTO interviewerDTO = getInterviewer(id);

        // 判断面试官是否已经不可用
        if (!interviewerDTO.getAvailable()) {
            throw new UnmodifiedServiceException("The interviewer already unavailable.");
        }

        // 更新面试官状态
        InterviewerDO interviewerDOForUpdate = InterviewerDO.builder().id(id).available(false).build();
        interviewerMapper.updateById(interviewerDOForUpdate);

        // 回收面试官的权限
        OrganizationMemberDTO organizationMemberDTO =
                organizationMemberService.getOrganizationMember(interviewerDTO.getOrganizationMemberId());
        roleService.removeUserRole(organizationMemberDTO.getUserId(), INTERVIEWER_ROLE_ID);

        // 发送取消面试官的系统通知
        sendDisableOrEnableInterviewerSystemNotification(organizationMemberDTO, "取消");

        // 获取禁用后的面试官
        return getInterviewer(id);
    }

    @DistributedLock(value = UPDATE_INTERVIEWER_AVAILABLE_LOCK_KEY_PATTERN, parameters = "#{#id}",
            errorMessage = "Failed to acquire update available interviewer lock.")
    @Override
    @Transactional
    public InterviewerDTO enableInterviewer(Long id) {
        // 判断面试官是否存在
        InterviewerDTO interviewerDTO = getInterviewer(id);

        // 判断面试官是否已经可用
        if (interviewerDTO.getAvailable()) {
            throw new UnmodifiedServiceException("The interviewer already available.");
        }

        // 更新面试官状态
        InterviewerDO interviewerDOForUpdate = InterviewerDO.builder().id(id).available(true).build();
        interviewerMapper.updateById(interviewerDOForUpdate);

        // 赋予面试官的成员所属用户主体面试官权限
        OrganizationMemberDTO organizationMemberDTO =
                organizationMemberService.getOrganizationMember(interviewerDTO.getOrganizationMemberId());
        roleService.createUserRole(organizationMemberDTO.getUserId(), INTERVIEWER_ROLE_ID);

        // 发送恢复面试官的系统通知
        sendDisableOrEnableInterviewerSystemNotification(organizationMemberDTO, "恢复");

        // 获取解禁后的面试官
        return getInterviewer(id);
    }

    /**
     * 发送成为面试官的系统通知
     *
     * @param userId 用户编号
     * @param organizationDTO OrganizationDTO
     */
    private void sendBecomeInterviewerSystemNotification(Long userId, OrganizationDTO organizationDTO) {
        String abbreviationOrganizationName = organizationDTO.getAbbreviationOrganizationName();
        String notificationTitle = abbreviationOrganizationName + "已将您设置为的面试官";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", notificationTitle + "。您现在可以查看" + abbreviationOrganizationName
                + "的报名表，并进行面试工作啦！");
        jsonObject.put("organizationId", organizationDTO.getId());
        String notificationContent = jsonObject.toJSONString();
        SendSystemNotificationRequest sendSystemNotificationPO = SendSystemNotificationRequest.builder()
                .userId(userId)
                .notificationType(SystemNotificationTypeEnum.INTERVIEWER)
                .notificationTitle(notificationTitle)
                .notificationContent(notificationContent)
                .build();
        notificationTemplate.sendSystemNotification(sendSystemNotificationPO);
    }

    /**
     * 发送取消或回复面试官资格的系统通知
     *
     * @param organizationMemberDTO OrganizationMemberDTO
     * @param disableOrEnable "取消" or "恢复"
     */
    private void sendDisableOrEnableInterviewerSystemNotification(
            OrganizationMemberDTO organizationMemberDTO, String disableOrEnable) {
        OrganizationDTO organizationDTO = organizationService.getOrganization(organizationMemberDTO.getOrganizationId());
        String abbreviationOrganizationName = organizationDTO.getAbbreviationOrganizationName();
        String notificationTitle = abbreviationOrganizationName + "已" + disableOrEnable+ "您的面试官资格";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", notificationTitle);
        jsonObject.put("organizationId", organizationMemberDTO.getOrganizationId());
        String notificationContent = jsonObject.toJSONString();
        SendSystemNotificationRequest sendSystemNotificationPO = SendSystemNotificationRequest.builder()
                .userId(organizationMemberDTO.getUserId())
                .notificationType(SystemNotificationTypeEnum.INTERVIEWER)
                .notificationTitle(notificationTitle)
                .notificationContent(notificationContent)
                .build();
        notificationTemplate.sendSystemNotification(sendSystemNotificationPO);
    }

}
