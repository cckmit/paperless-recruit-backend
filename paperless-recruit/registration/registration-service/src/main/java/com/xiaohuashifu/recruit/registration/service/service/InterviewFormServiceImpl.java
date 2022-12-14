package com.xiaohuashifu.recruit.registration.service.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaohuashifu.recruit.common.aspect.annotation.DistributedLock;
import com.xiaohuashifu.recruit.common.exception.MqServiceException;
import com.xiaohuashifu.recruit.common.exception.NotFoundServiceException;
import com.xiaohuashifu.recruit.common.exception.unprocessable.DuplicateServiceException;
import com.xiaohuashifu.recruit.common.exception.unprocessable.InvalidStatusServiceException;
import com.xiaohuashifu.recruit.common.exception.unprocessable.MisMatchServiceException;
import com.xiaohuashifu.recruit.notification.api.constant.SystemNotificationTypeEnum;
import com.xiaohuashifu.recruit.notification.api.request.SendSystemNotificationRequest;
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
import com.xiaohuashifu.recruit.registration.service.mq.NotificationTemplate;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * ??????????????????????????????
 *
 * @author xhsf
 * @create 2021/1/5 14:29
 */
@Service
public class InterviewFormServiceImpl implements InterviewFormService {

    private final InterviewFormAssembler interviewFormAssembler;

    private final InterviewFormMapper interviewFormMapper;

    private final NotificationTemplate notificationTemplate;

    @Reference
    private InterviewService interviewService;

    @Reference
    private ApplicationFormService applicationFormService;

    @Reference
    private RecruitmentService recruitmentService;

    @Reference
    private OrganizationService organizationService;

    /**
     * ?????????????????????????????????{0}??????????????????{1}??????????????????
     */
    private final static String CREATE_INTERVIEW_FORM_LOCK_KEY_PATTERN =
            "interview-form:create-interview-form:interview-id:{0}:application-form-id:{1}";

    /**
     * ?????????????????????????????????{0}??????????????????
     */
    private final static String UPDATE_INTERVIEW_FORM_LOCK_KEY_PATTERN = "interview-form:{0}";

    public InterviewFormServiceImpl(InterviewFormAssembler interviewFormAssembler,
                                    InterviewFormMapper interviewFormMapper, NotificationTemplate notificationTemplate) {
        this.interviewFormAssembler = interviewFormAssembler;
        this.interviewFormMapper = interviewFormMapper;
        this.notificationTemplate = notificationTemplate;
    }

    @DistributedLock(value = CREATE_INTERVIEW_FORM_LOCK_KEY_PATTERN,
            parameters = {"#{#request.interviewId}", "#{#request.applicationFormId}"})
    @Transactional
    @Override
    public InterviewFormDTO createInterviewForm(CreateInterviewFormRequest request) {
        // ??????????????????
        InterviewDTO interviewDTO = interviewService.checkInterviewStatus(request.getInterviewId());

        // ????????????????????????????????????
        LambdaQueryWrapper<InterviewFormDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(InterviewFormDO::getApplicationFormId, request.getApplicationFormId())
                .eq(InterviewFormDO::getInterviewId, request.getInterviewId());
        int count = interviewFormMapper.selectCount(wrapper);
        if (count > 0) {
            throw new DuplicateServiceException("The interviewForm already exist.");
        }

        // ???????????????????????????????????????????????????
        ApplicationFormDTO applicationFormDTO =
                applicationFormService.getApplicationForm(request.getApplicationFormId());
        if (!Objects.equals(interviewDTO.getRecruitmentId(), applicationFormDTO.getRecruitmentId())) {
            throw new MisMatchServiceException("???????????????????????????????????????");
        }

        // ???????????????
        InterviewFormDO interviewFormDOForInsert = InterviewFormDO.builder().interviewId(request.getInterviewId())
                .applicationFormId(request.getApplicationFormId()).interviewTime(request.getInterviewTime())
                .interviewLocation(request.getInterviewLocation()).note(request.getNote())
                .interviewStatus(InterviewStatusEnum.WAITING_INTERVIEW.name()).build();
        interviewFormMapper.insert(interviewFormDOForInsert);

        // ??????????????????
        sendInterviewSystemNotification(interviewDTO, interviewFormDOForInsert.getId(),
                applicationFormDTO);

        // ????????????????????????
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
        // ???????????????????????????
        InterviewFormDTO interviewFormDTO = getInterviewForm(request.getId());

        // ??????????????????????????? WAITING_INTERVIEW
        if (!Objects.equals(interviewFormDTO.getInterviewStatus(), InterviewStatusEnum.WAITING_INTERVIEW.name())) {
            throw new InvalidStatusServiceException("The status of interviewForm must be "
                    + InterviewStatusEnum.WAITING_INTERVIEW.name() + ".");
        }

        // ??????????????????
        InterviewFormDO interviewFormDOForUpdate =
                interviewFormAssembler.updateInterviewFormRequestToInterviewFormDO(request);
        interviewFormMapper.updateById(interviewFormDOForUpdate);

        // ???????????????????????????
        InterviewFormDTO interviewFormDTO0 = getInterviewForm(interviewFormDOForUpdate.getId());

        // ??????????????????????????????
        sendUpdateInterviewFormSystemNotification(interviewFormDTO0);
        return interviewFormDTO0;
    }

    @Override
    @Transactional
    @DistributedLock(value = UPDATE_INTERVIEW_FORM_LOCK_KEY_PATTERN, parameters = "#{#id}")
    public InterviewFormDTO updateInterviewStatus(Long id, InterviewStatusEnum oldInterviewStatus,
                                                          InterviewStatusEnum newInterviewStatus) {
        // ??????????????????????????????????????????
        if (oldInterviewStatus.getCode() >= newInterviewStatus.getCode()) {
            throw new InvalidStatusServiceException("The newInterviewStatus must be greater than oldInterviewStatus.");
        }

        // ???????????????????????????
        InterviewFormDTO interviewFormDTO = getInterviewForm(id);

        // ???????????????????????????
        if (!Objects.equals(interviewFormDTO.getInterviewStatus(), oldInterviewStatus.name())) {
            throw new MisMatchServiceException("??????????????????");
        }

        // ??????????????????
        InterviewFormDO interviewFormDOForUpdate = InterviewFormDO.builder().id(id)
                .interviewStatus(newInterviewStatus.name()).build();
        interviewFormMapper.updateById(interviewFormDOForUpdate);

        // ???????????????????????????
        InterviewFormDTO interviewFormDTO0 = getInterviewForm(id);

        // ??????????????????????????????
        sendInterviewResultSystemNotification(interviewFormDTO0);

        return interviewFormDTO0;
    }

    /**
     * ???????????????????????????
     *
     * @param interviewDTO ????????????
     * @param interviewFormId ???????????????
     * @param applicationFormDTO ApplicationFormDTO
     */
    private void sendInterviewSystemNotification(InterviewDTO interviewDTO, Long interviewFormId,
                                                 ApplicationFormDTO applicationFormDTO) throws MqServiceException {
        RecruitmentDTO recruitmentDTO = recruitmentService.getRecruitment(interviewDTO.getRecruitmentId());
        OrganizationDTO organizationDTO = organizationService.getOrganization(recruitmentDTO.getOrganizationId());

        String notificationTitle = interviewDTO.getTitle() + "??????";
        String organizationName = organizationDTO.getOrganizationName();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", "???" + organizationName + "???????????????????????????" + interviewDTO.getTitle()
                + "???????????????????????????????????????????????????");
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
        notificationTemplate.sendSystemNotification(sendSystemNotificationPO);
    }

    /**
     * ??????????????????????????????
     *
     * @param interviewFormDTO InterviewFormDTO
     */
    private void sendUpdateInterviewFormSystemNotification(InterviewFormDTO interviewFormDTO) throws MqServiceException {
        Long interviewId = interviewFormDTO.getInterviewId();
        InterviewDTO interviewDTO = interviewService.getInterview(interviewId);
        Long recruitmentId = interviewDTO.getRecruitmentId();
        RecruitmentDTO recruitmentDTO = recruitmentService.getRecruitment(recruitmentId);
        OrganizationDTO organizationDTO = organizationService.getOrganization(recruitmentDTO.getOrganizationId());

        String notificationTitle = interviewDTO.getTitle();
        String organizationName = organizationDTO.getOrganizationName();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", "???" + organizationName + "?????????????????????" + notificationTitle
                + "?????????????????????????????????????????????????????????????????????");
        jsonObject.put("interviewId", interviewId);
        jsonObject.put("interviewFormId", interviewFormDTO.getId());
        jsonObject.put("organizationId", organizationDTO.getId());
        String notificationContent = jsonObject.toJSONString();

        Long applicationFormId = interviewFormDTO.getApplicationFormId();
        ApplicationFormDTO applicationFormDTO = applicationFormService.getApplicationForm(applicationFormId);
        SendSystemNotificationRequest sendSystemNotificationPO = SendSystemNotificationRequest.builder()
                .userId(applicationFormDTO.getUserId()).notificationType(SystemNotificationTypeEnum.INTERVIEW)
                .notificationTitle(notificationTitle).notificationContent(notificationContent).build();
        notificationTemplate.sendSystemNotification(sendSystemNotificationPO);
    }

    /**
     * ??????????????????????????????
     *
     * @param interviewFormDTO InterviewFormDTO
     */
    private void sendInterviewResultSystemNotification(InterviewFormDTO interviewFormDTO) throws MqServiceException {
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
            notificationTitle = "?????????????????????" + interviewDTO.getTitle() + "?????????";
            String organizationName = organizationDTO.getOrganizationName();
            jsonObject.put("message", "???" + organizationName + "??????????????????" + interviewDTO.getTitle()
                    + "??????????????????????????????");
        } else if (interviewStatus == InterviewStatusEnum.NOT_PASS) {
            notificationTitle = "?????????????????????" + interviewDTO.getTitle() + "????????????";
            String organizationName = organizationDTO.getOrganizationName();
            jsonObject.put("message", "???" + organizationName + "???????????????????????????" + interviewDTO.getTitle()
                    + "??????");
        } else {
            notificationTitle = "?????????????????????" + interviewDTO.getTitle() + "?????????";
            String organizationName = organizationDTO.getOrganizationName();
            jsonObject.put("message", "???" + organizationName + "????????????????????????" + interviewDTO.getTitle()
                    + "??????");
        }

        String notificationContent = jsonObject.toJSONString();
        ApplicationFormDTO applicationFormDTO = applicationFormService.getApplicationForm(
                interviewFormDTO.getApplicationFormId());
        SendSystemNotificationRequest sendSystemNotificationPO = SendSystemNotificationRequest.builder()
                .userId(applicationFormDTO.getUserId()).notificationType(SystemNotificationTypeEnum.INTERVIEW)
                .notificationTitle(notificationTitle).notificationContent(notificationContent).build();
        notificationTemplate.sendSystemNotification(sendSystemNotificationPO);
    }


}
