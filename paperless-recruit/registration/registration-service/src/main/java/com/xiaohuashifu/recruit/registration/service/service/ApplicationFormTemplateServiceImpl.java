package com.xiaohuashifu.recruit.registration.service.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaohuashifu.recruit.common.aspect.annotation.DistributedLock;
import com.xiaohuashifu.recruit.common.exception.NotFoundServiceException;
import com.xiaohuashifu.recruit.common.exception.unprocessable.DuplicateServiceException;
import com.xiaohuashifu.recruit.common.exception.unprocessable.InvalidStatusServiceException;
import com.xiaohuashifu.recruit.common.exception.unprocessable.OverLimitServiceException;
import com.xiaohuashifu.recruit.common.exception.unprocessable.UnavailableServiceException;
import com.xiaohuashifu.recruit.registration.api.constant.ApplicationFormTemplateConstants;
import com.xiaohuashifu.recruit.registration.api.constant.RecruitmentStatusEnum;
import com.xiaohuashifu.recruit.registration.api.dto.ApplicationFormTemplateDTO;
import com.xiaohuashifu.recruit.registration.api.dto.RecruitmentDTO;
import com.xiaohuashifu.recruit.registration.api.request.ApplicationFormTemplateRequest;
import com.xiaohuashifu.recruit.registration.api.request.CreateApplicationFormTemplateRequest;
import com.xiaohuashifu.recruit.registration.api.request.UpdateApplicationFormTemplateRequest;
import com.xiaohuashifu.recruit.registration.api.service.ApplicationFormTemplateService;
import com.xiaohuashifu.recruit.registration.api.service.RecruitmentService;
import com.xiaohuashifu.recruit.registration.service.assembler.ApplicationFormTemplateAssembler;
import com.xiaohuashifu.recruit.registration.service.dao.ApplicationFormTemplateMapper;
import com.xiaohuashifu.recruit.registration.service.do0.ApplicationFormTemplateDO;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * 描述：报名表模板服务实现
 *
 * @author xhsf
 * @create 2020/12/28 21:55
 */
@Service
public class ApplicationFormTemplateServiceImpl implements ApplicationFormTemplateService {

    @Reference
    private RecruitmentService recruitmentService;

    private final ApplicationFormTemplateMapper applicationFormTemplateMapper;

    private final ApplicationFormTemplateAssembler applicationFormTemplateAssembler;

    /**
     * 添加报名表模板的锁定键模式，{0}是招新编号
     */
    private static final String CREATE_APPLICATION_FORM_TEMPLATE_LOCK_KEY_PATTERN =
            "application-form-template:create-application-form-template:recruitment-id:{0}";

    /**
     * 报名表模板的锁定键模式，{0}是报名表模板编号
     */
    private static final String APPLICATION_FORM_TEMPLATE_LOCK_KEY_PATTERN = "application-form-template:{0}";

    public ApplicationFormTemplateServiceImpl(ApplicationFormTemplateMapper applicationFormTemplateMapper,
                                              ApplicationFormTemplateAssembler applicationFormTemplateAssembler) {
        this.applicationFormTemplateMapper = applicationFormTemplateMapper;
        this.applicationFormTemplateAssembler = applicationFormTemplateAssembler;
    }

    @DistributedLock(value = CREATE_APPLICATION_FORM_TEMPLATE_LOCK_KEY_PATTERN, parameters = "#{#request.recruitmentId}")
    @Override
    public ApplicationFormTemplateDTO createApplicationFormTemplate(CreateApplicationFormTemplateRequest request) {
        // 判断报名表模板的字段是否小于最低限制
        checkApplicationFormTemplateFieldsCount(request);

        // 判断报名表模板是否存在
        LambdaQueryWrapper<ApplicationFormTemplateDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ApplicationFormTemplateDO::getRecruitmentId, request.getRecruitmentId());
        int count = applicationFormTemplateMapper.selectCount(wrapper);
        if (count > 0) {
            throw new DuplicateServiceException( "The applicationFormTemplate already exist.");
        }

        // 判断招新状态
        recruitmentService.checkRecruitmentStatus(request.getRecruitmentId(), RecruitmentStatusEnum.ENDED);

        // 添加报名表模板
        ApplicationFormTemplateDO applicationFormTemplateDOForInsert =
                applicationFormTemplateAssembler.createApplicationFormTemplateRequestToApplicationFormTemplateDO(request);
        applicationFormTemplateMapper.insert(applicationFormTemplateDOForInsert);
        return getApplicationFormTemplate(applicationFormTemplateDOForInsert.getId());
    }

    @Override
    public ApplicationFormTemplateDTO getApplicationFormTemplate(Long id) {
        ApplicationFormTemplateDO applicationFormTemplateDO = applicationFormTemplateMapper.selectById(id);
        if (applicationFormTemplateDO == null) {
            throw new NotFoundServiceException("applicationFormTemplate", "id", id);
        }
        return applicationFormTemplateAssembler.applicationFormTemplateDOToApplicationFormTemplateDTO(
                applicationFormTemplateDO);
    }

    @Override
    public ApplicationFormTemplateDTO getApplicationFormTemplateByRecruitmentId(Long recruitmentId) {
        LambdaQueryWrapper<ApplicationFormTemplateDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ApplicationFormTemplateDO::getRecruitmentId, recruitmentId);
        ApplicationFormTemplateDO applicationFormTemplateDO = applicationFormTemplateMapper.selectOne(wrapper);
        if (applicationFormTemplateDO == null) {
            throw new NotFoundServiceException("applicationFormTemplate", "recruitmentId", recruitmentId);
        }
        return applicationFormTemplateAssembler.applicationFormTemplateDOToApplicationFormTemplateDTO(
                applicationFormTemplateDO);
    }

    @DistributedLock(value = APPLICATION_FORM_TEMPLATE_LOCK_KEY_PATTERN, parameters = "#{#request.id}")
    @Override
    public ApplicationFormTemplateDTO updateApplicationFormTemplate(UpdateApplicationFormTemplateRequest request) {
        // 判断报名表模板的字段是否小于最低限制
        checkApplicationFormTemplateFieldsCount(request);

        // 检查报名表模板的状态是否适合更新
        checkApplicationFormTemplateStatusForUpdate(request.getId(), RecruitmentStatusEnum.STARTED);

        // 更新报名表模板
        ApplicationFormTemplateDO applicationFormTemplateDOForUpdate =
                applicationFormTemplateAssembler.updateApplicationFormTemplateRequestToApplicationFormTemplateDO(request);
        applicationFormTemplateMapper.updateById(applicationFormTemplateDOForUpdate);
        return getApplicationFormTemplate(request.getId());
    }

    @Override
    @DistributedLock(value = APPLICATION_FORM_TEMPLATE_LOCK_KEY_PATTERN, parameters = "#{#id}")
    public ApplicationFormTemplateDTO updatePrompt(Long id, String prompt) {
        // 检查报名表模板的状态是否适合更新
        checkApplicationFormTemplateStatusForUpdate(id, RecruitmentStatusEnum.ENDED);

        // 更新报名提示
        ApplicationFormTemplateDO applicationFormTemplateDOForUpdate = ApplicationFormTemplateDO.builder().id(id)
                .prompt(prompt).build();
        applicationFormTemplateMapper.updateById(applicationFormTemplateDOForUpdate);
        return getApplicationFormTemplate(id);
    }

    @Override
    @DistributedLock(value = APPLICATION_FORM_TEMPLATE_LOCK_KEY_PATTERN, parameters = "#{#id}")
    public ApplicationFormTemplateDTO deactivateApplicationFormTemplate(Long id) {
        // 检查报名表模板的状态是否适合更新
        checkApplicationFormTemplateStatusForUpdate(id, RecruitmentStatusEnum.ENDED);

        // 停用报名表模板
        ApplicationFormTemplateDO applicationFormTemplateDOForUpdate = ApplicationFormTemplateDO.builder().id(id)
                .deactivated(true).build();
        applicationFormTemplateMapper.updateById(applicationFormTemplateDOForUpdate);
        return getApplicationFormTemplate(id);
    }

    @Override
    @DistributedLock(value = APPLICATION_FORM_TEMPLATE_LOCK_KEY_PATTERN, parameters = "#{#id}")
    public ApplicationFormTemplateDTO enableApplicationFormTemplate(Long id) {
        // 检查报名表模板的状态是否适合更新
        checkApplicationFormTemplateStatusForUpdate(id, RecruitmentStatusEnum.ENDED);

        // 启用报名表模板
        ApplicationFormTemplateDO applicationFormTemplateDOForUpdate = ApplicationFormTemplateDO.builder().id(id)
                .deactivated(false).build();
        applicationFormTemplateMapper.updateById(applicationFormTemplateDOForUpdate);
        return getApplicationFormTemplate(id);
    }

    @Override
    public ApplicationFormTemplateDTO canRegistration(Long recruitmentId) {
        // 检查招新状态
        RecruitmentDTO recruitmentDTO =
                recruitmentService.checkRecruitmentStatus(recruitmentId, RecruitmentStatusEnum.ENDED);

        // 判断当前状态是否为 STARTED
        if (!Objects.equals(recruitmentDTO.getRecruitmentStatus(), RecruitmentStatusEnum.STARTED.name())) {
            throw new InvalidStatusServiceException("The recruitment status must be STARTED.");
        }

        // 判断报名表模板是否存在
        ApplicationFormTemplateDTO applicationFormTemplateDTO =
                getApplicationFormTemplateByRecruitmentId(recruitmentId);

        // 检查报名表模板的状态
        if (applicationFormTemplateDTO.getDeactivated()) {
            throw new UnavailableServiceException("The applicationFormTemplate already deactivated.");
        }

        return applicationFormTemplateDTO;
    }

    /**
     * 检查报名表模板的状态，为了更新
     *
     * @param id 报名表模板编号
     * @param followRecruitmentStatus 招新的后续状态，必须在这个状态之前
     * @return 检查结果
     */
    private ApplicationFormTemplateDTO checkApplicationFormTemplateStatusForUpdate(
            Long id, RecruitmentStatusEnum followRecruitmentStatus) {
        // 判断报名表模板是否存在
        ApplicationFormTemplateDTO applicationFormTemplateDTO = getApplicationFormTemplate(id);

        // 判断招新状态
        recruitmentService.checkRecruitmentStatus(applicationFormTemplateDTO.getRecruitmentId(), followRecruitmentStatus);

        // 通过检查
        return applicationFormTemplateDTO;
    }

    /**
     * 计算一个报名表模板的字段数量
     *
     * @param request ApplicationFormTemplateRequest
     */
    private void checkApplicationFormTemplateFieldsCount(ApplicationFormTemplateRequest request)
            throws OverLimitServiceException{
        int count = 0;
        for (Field declaredField : ApplicationFormTemplateRequest.class.getDeclaredFields()) {
            declaredField.setAccessible(true);
            Boolean value;
            try {
                value = (Boolean) declaredField.get(request);
                if (value != null && value) {
                    count++;
                }
            } catch (IllegalAccessException ignored) {}
        }

        if (count < ApplicationFormTemplateConstants.MIN_APPLICATION_FORM_TEMPLATE_FIELD_NUMBERS) {
            throw new OverLimitServiceException("The number of applicationFormTemplate fields must not be less than " +
                    ApplicationFormTemplateConstants.MIN_APPLICATION_FORM_TEMPLATE_FIELD_NUMBERS + ".");
        }
    }

}
