package com.xiaohuashifu.recruit.registration.service.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaohuashifu.recruit.common.aspect.annotation.DistributedLock;
import com.xiaohuashifu.recruit.common.exception.NotFoundServiceException;
import com.xiaohuashifu.recruit.common.exception.unprocessable.DuplicateServiceException;
import com.xiaohuashifu.recruit.common.exception.unprocessable.InvalidStatusServiceException;
import com.xiaohuashifu.recruit.common.exception.unprocessable.MisMatchServiceException;
import com.xiaohuashifu.recruit.notification.api.constant.SystemNotificationTypeEnum;
import com.xiaohuashifu.recruit.notification.api.request.SendSystemNotificationRequest;
import com.xiaohuashifu.recruit.notification.api.service.SystemNotificationService;
import com.xiaohuashifu.recruit.organization.api.dto.OrganizationDTO;
import com.xiaohuashifu.recruit.organization.api.service.OrganizationService;
import com.xiaohuashifu.recruit.registration.api.constant.InterviewStatusEnum;
import com.xiaohuashifu.recruit.registration.api.dto.ApplicationFormDTO;
import com.xiaohuashifu.recruit.registration.api.dto.InterviewDTO;
import com.xiaohuashifu.recruit.registration.api.dto.InterviewFormDTO;
import com.xiaohuashifu.recruit.registration.api.dto.RecruitmentDTO;
import com.xiaohuashifu.recruit.registration.api.request.CreateInterviewFormRequest;
import com.xiaohuashifu.recruit.registration.api.request.UpdateInterviewFormRequest;
import com.xiaohuashifu.recruit.registration.api.service.ApplicationFormService;
import com.xiaohuashifu.recruit.registration.api.service.InterviewFormService;
import com.xiaohuashifu.recruit.registration.api.service.InterviewService;
import com.xiaohuashifu.recruit.registration.api.service.RecruitmentService;
import com.xiaohuashifu.recruit.registration.service.assembler.InterviewFormAssembler;
import com.xiaohuashifu.recruit.registration.service.dao.InterviewFormMapper;
import com.xiaohuashifu.recruit.registration.service.do0.InterviewFormDO;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * 描述：面试表服务实现
 *
 * @author xhsf
 * @create 2021/1/5 14:29
 */
@Service
public class InterviewFormServiceImpl implements InterviewFormService {

    private final InterviewFormAssembler interviewFormAssembler;

    private final InterviewFormMapper interviewFormMapper;

    @Reference
    private InterviewService interviewService;

    @Reference
    private ApplicationFormService applicationFormService;

    @Reference
    private SystemNotificationService systemNotificationService;

    @Reference
    private RecruitmentService recruitmentService;

    @Reference
    private OrganizationService organizationService;

    /**
     * 保存面试表锁定键模式，{0}是面试编号，{1}是报名表编号
     */
    private final static String CREATE_INTERVIEW_FORM_LOCK_KEY_PATTERN =
            "interview-form:create-interview-form:interview-id:{0}:application-form-id:{1}";

    /**
     * 更新面试表锁定键模式，{0}是面试表编号
     */
    private final static String UPDATE_INTERVIEW_FORM_LOCK_KEY_PATTERN = "interview-form:{0}";

    public InterviewFormServiceImpl(InterviewFormAssembler interviewFormAssembler,
                                    InterviewFormMapper interviewFormMapper) {
        this.interviewFormAssembler = interviewFormAssembler;
        this.interviewFormMapper = interviewFormMapper;
    }

    @DistributedLock(value = CREATE_INTERVIEW_FORM_LOCK_KEY_PATTERN,
            parameters = {"#{#request.interviewId}", "#{#request.applicationFormId}"})
    @Transactional
    @Override
    public InterviewFormDTO createInterviewForm(CreateInterviewFormRequest request) {
        // 检查面试状态
        InterviewDTO interviewDTO = interviewService.checkInterviewStatus(request.getInterviewId());

        // 判断是否已经存在该面试表
        LambdaQueryWrapper<InterviewFormDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(InterviewFormDO::getApplicationFormId, request.getApplicationFormId())
                .eq(InterviewFormDO::getInterviewId, request.getInterviewId());
        int count = interviewFormMapper.selectCount(wrapper);
        if (count > 0) {
            throw new DuplicateServiceException("The interviewForm already exist.");
        }

        // 判断面试和报名表是否是同一个招新的
        ApplicationFormDTO applicationFormDTO =
                applicationFormService.getApplicationForm(request.getApplicationFormId());
        if (!Objects.equals(interviewDTO.getRecruitmentId(), applicationFormDTO.getRecruitmentId())) {
            throw new MisMatchServiceException("面试和报名表不是同一个招新");
        }

        // 保存面试表
        InterviewFormDO interviewFormDOForInsert = InterviewFormDO.builder().interviewId(request.getInterviewId())
                .applicationFormId(request.getApplicationFormId()).interviewTime(request.getInterviewTime())
                .interviewLocation(request.getInterviewLocation()).note(request.getNote())
                .interviewStatus(InterviewStatusEnum.WAITING_INTERVIEW.name()).build();
        interviewFormMapper.insert(interviewFormDOForInsert);

        // 发送面试通知
        sendInterviewSystemNotification(interviewDTO, interviewFormDOForInsert.getId(),
                applicationFormDTO);

        // 获取创建的面试表
        return getInterviewForm(interviewFormDOForInsert.getId());
    }

    @Override
    public InterviewFormDTO getInterviewForm(Long id) {
        InterviewFormDO interviewFormDO = interviewFormMapper.selectById(id);
        if (interviewFormDO == null) {
            throw new NotFoundServiceException("interview form", "id", id);
        }
        return interviewFormAssembler.interviewFormDOToInterviewFormDTO(interviewFormDO);
    }

    @Override
    @Transactional
    @DistributedLock(value = UPDATE_INTERVIEW_FORM_LOCK_KEY_PATTERN, parameters = "#{#request.id}")
    public InterviewFormDTO updateInterviewForm(UpdateInterviewFormRequest request) {
        // 判断面试表是否存在
        InterviewFormDTO interviewFormDTO = getInterviewForm(request.getId());

        // 判断面试状态是否是 WAITING_INTERVIEW
        if (!Objects.equals(interviewFormDTO.getInterviewStatus(), InterviewStatusEnum.WAITING_INTERVIEW.name())) {
            throw new InvalidStatusServiceException("The status of interviewForm must be "
                    + InterviewStatusEnum.WAITING_INTERVIEW.name() + ".");
        }

        // 更新面试时间
        InterviewFormDO interviewFormDOForUpdate =
                interviewFormAssembler.updateInterviewFormRequestToInterviewFormDO(request);
        interviewFormMapper.updateById(interviewFormDOForUpdate);

        // 获取更新后的面试表
        InterviewFormDTO interviewFormDTO0 = getInterviewForm(interviewFormDOForUpdate.getId());

        // 发送更新面试时间通知
        sendUpdateInterviewFormSystemNotification(interviewFormDTO0);
        return interviewFormDTO0;
    }

    @Override
    @Transactional
    @DistributedLock(value = UPDATE_INTERVIEW_FORM_LOCK_KEY_PATTERN, parameters = "#{#id}")
    public InterviewFormDTO updateInterviewStatus(Long id, InterviewStatusEnum oldInterviewStatus,
                                                          InterviewStatusEnum newInterviewStatus) {
        // 旧面试状态必须小于新模式状态
        if (oldInterviewStatus.getCode() >= newInterviewStatus.getCode()) {
            throw new InvalidStatusServiceException("The newInterviewStatus must be greater than oldInterviewStatus.");
        }

        // 判断面试表是否存在
        InterviewFormDTO interviewFormDTO = getInterviewForm(id);

        // 旧面试状态必须正确
        if (!Objects.equals(interviewFormDTO.getInterviewStatus(), oldInterviewStatus.name())) {
            throw new MisMatchServiceException("旧状态不正确");
        }

        // 更新面试状态
        InterviewFormDO interviewFormDOForUpdate = InterviewFormDO.builder().id(id)
                .interviewStatus(newInterviewStatus.name()).build();
        interviewFormMapper.updateById(interviewFormDOForUpdate);

        // 获取更新后的面试表
        InterviewFormDTO interviewFormDTO0 = getInterviewForm(id);

        // 发送更新面试结果通知
        sendInterviewResultSystemNotification(interviewFormDTO0);

        return interviewFormDTO0;
    }

    /**
     * 发送面试的系统通知
     *
     * @param interviewDTO 面试对象
     * @param interviewFormId 面试表编号
     * @param applicationFormDTO ApplicationFormDTO
     */
    private void sendInterviewSystemNotification(InterviewDTO interviewDTO, Long interviewFormId,
                                                 ApplicationFormDTO applicationFormDTO) {
        RecruitmentDTO recruitmentDTO = recruitmentService.getRecruitment(interviewDTO.getRecruitmentId());
        OrganizationDTO organizationDTO = organizationService.getOrganization(recruitmentDTO.getOrganizationId());

        String notificationTitle = interviewDTO.getTitle() + "通知";
        String abbreviationOrganizationName = organizationDTO.getAbbreviationOrganizationName();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", "【" + abbreviationOrganizationName + "】邀请您参加面试：" + interviewDTO.getTitle()
                + "。请注意好面试要求，准时参加面试。");
        jsonObject.put("interviewId", interviewDTO.getId());
        jsonObject.put("interviewFormId", interviewFormId);
        jsonObject.put("organizationId", organizationDTO.getId());
        String notificationContent = jsonObject.toJSONString();

        SendSystemNotificationRequest sendSystemNotificationPO = SendSystemNotificationRequest.builder()
                .userId(applicationFormDTO.getUserId())
                .notificationType(SystemNotificationTypeEnum.INTERVIEW)
                .notificationTitle(notificationTitle)
                .notificationContent(notificationContent)
                .build();
        systemNotificationService.sendSystemNotification(sendSystemNotificationPO);
    }

    /**
     * 更新面试表的系统通知
     *
     * @param interviewFormDTO InterviewFormDTO
     */
    private void sendUpdateInterviewFormSystemNotification(InterviewFormDTO interviewFormDTO) {
        Long interviewId = interviewFormDTO.getInterviewId();
        InterviewDTO interviewDTO = interviewService.getInterview(interviewId);
        Long recruitmentId = interviewDTO.getRecruitmentId();
        RecruitmentDTO recruitmentDTO = recruitmentService.getRecruitment(recruitmentId);
        OrganizationDTO organizationDTO = organizationService.getOrganization(recruitmentDTO.getOrganizationId());

        String notificationTitle = interviewDTO.getTitle();
        String abbreviationOrganizationName = organizationDTO.getAbbreviationOrganizationName();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", "【" + abbreviationOrganizationName + "】更新了面试【" + notificationTitle
                + "】的信息。请查看最新的面试要求，以免错过面试。");
        jsonObject.put("interviewId", interviewId);
        jsonObject.put("interviewFormId", interviewFormDTO.getId());
        jsonObject.put("organizationId", organizationDTO.getId());
        String notificationContent = jsonObject.toJSONString();

        Long applicationFormId = interviewFormDTO.getApplicationFormId();
        ApplicationFormDTO applicationFormDTO = applicationFormService.getApplicationForm(applicationFormId);
        SendSystemNotificationRequest sendSystemNotificationPO = SendSystemNotificationRequest.builder()
                .userId(applicationFormDTO.getUserId()).notificationType(SystemNotificationTypeEnum.INTERVIEW)
                .notificationTitle(notificationTitle).notificationContent(notificationContent).build();
        systemNotificationService.sendSystemNotification(sendSystemNotificationPO);
    }

    /**
     * 更新面试表的系统通知
     *
     * @param interviewFormDTO InterviewFormDTO
     */
    private void sendInterviewResultSystemNotification(InterviewFormDTO interviewFormDTO) {
        InterviewDTO interviewDTO = interviewService.getInterview(interviewFormDTO.getInterviewId());
        RecruitmentDTO recruitmentDTO = recruitmentService.getRecruitment(interviewDTO.getRecruitmentId());
        OrganizationDTO organizationDTO = organizationService.getOrganization(recruitmentDTO.getOrganizationId());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("interviewId", interviewFormDTO.getInterviewId());
        jsonObject.put("interviewFormId", interviewFormDTO.getId());
        jsonObject.put("organizationId", organizationDTO.getId());
        String notificationTitle;
        InterviewStatusEnum interviewStatus = InterviewStatusEnum.valueOf(interviewFormDTO.getInterviewStatus());
        if (interviewStatus == InterviewStatusEnum.PENDING) {
            notificationTitle = "【面试结果】【" + interviewDTO.getTitle() + "】待定";
            String abbreviationOrganizationName = organizationDTO.getAbbreviationOrganizationName();
            jsonObject.put("message", "【" + abbreviationOrganizationName + "】您参加的【" + interviewDTO.getTitle()
                    + "】目前处于待定状态。");
        } else if (interviewStatus == InterviewStatusEnum.NOT_PASS) {
            notificationTitle = "【面试结果】【" + interviewDTO.getTitle() + "】未通过";
            String abbreviationOrganizationName = organizationDTO.getAbbreviationOrganizationName();
            jsonObject.put("message", "【" + abbreviationOrganizationName + "】很遗憾您未通过【" + interviewDTO.getTitle()
                    + "】。");
        } else {
            notificationTitle = "【面试结果】【" + interviewDTO.getTitle() + "】通过";
            String abbreviationOrganizationName = organizationDTO.getAbbreviationOrganizationName();
            jsonObject.put("message", "【" + abbreviationOrganizationName + "】恭喜您通过了【" + interviewDTO.getTitle()
                    + "】。");
        }

        String notificationContent = jsonObject.toJSONString();
        ApplicationFormDTO applicationFormDTO = applicationFormService.getApplicationForm(
                interviewFormDTO.getApplicationFormId());
        SendSystemNotificationRequest sendSystemNotificationPO = SendSystemNotificationRequest.builder()
                .userId(applicationFormDTO.getUserId()).notificationType(SystemNotificationTypeEnum.INTERVIEW)
                .notificationTitle(notificationTitle).notificationContent(notificationContent).build();
        systemNotificationService.sendSystemNotification(sendSystemNotificationPO);
    }

}
