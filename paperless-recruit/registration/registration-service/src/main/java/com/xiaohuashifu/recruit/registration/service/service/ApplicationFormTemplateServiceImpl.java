package com.xiaohuashifu.recruit.registration.service.service;

import com.xiaohuashifu.recruit.common.aspect.annotation.DistributedLock;
import com.xiaohuashifu.recruit.common.result.ErrorCodeEnum;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.registration.api.constant.ApplicationFormTemplateConstants;
import com.xiaohuashifu.recruit.registration.api.constant.RecruitmentStatusEnum;
import com.xiaohuashifu.recruit.registration.api.dto.ApplicationFormTemplateDTO;
import com.xiaohuashifu.recruit.registration.api.request.AddApplicationFormTemplatePO;
import com.xiaohuashifu.recruit.registration.api.request.ApplicationFormTemplatePO;
import com.xiaohuashifu.recruit.registration.api.request.UpdateApplicationFormTemplatePO;
import com.xiaohuashifu.recruit.registration.api.service.ApplicationFormTemplateService;
import com.xiaohuashifu.recruit.registration.api.service.RecruitmentService;
import com.xiaohuashifu.recruit.registration.service.dao.ApplicationFormTemplateMapper;
import com.xiaohuashifu.recruit.registration.service.do0.ApplicationFormTemplateDO;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;

import java.lang.reflect.Field;

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

    /**
     * 添加报名表模板的锁定键模式，{0}是招新编号
     */
    private static final String ADD_APPLICATION_FORM_TEMPLATE_LOCK_KEY_PATTERN =
            "application-form-template:add-application-form-template:recruitment-id:{0}";

    /**
     * 更新报名表模板的锁定键模式，{0}是报名表模板编号
     */
    private static final String UPDATE_APPLICATION_FORM_TEMPLATE_LOCK_KEY_PATTERN =
            "application-form-template:{0}:update-application-form-template";

    public ApplicationFormTemplateServiceImpl(ApplicationFormTemplateMapper applicationFormTemplateMapper) {
        this.applicationFormTemplateMapper = applicationFormTemplateMapper;
    }

    /**
     * 给一个招新添加报名表模板，必须是招新状态是 ENDED 之前才可以添加
     *
     * @permission 需要是该招新所属组织所属用户主体本身
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.Number: 报名表模板的域个数小于最少限制
     *              InvalidParameter.NotExist: 招新不存在
     *              Forbidden.Unavailable: 招新不可用 | 组织不可用
     *              OperationConflict.Duplicate: 报名表模板已经存在
     *              OperationConflict.Status: 招新状态不允许
     *              OperationConflict.Lock: 获取报名表模板的锁失败
     *
     * @param addApplicationFormTemplatePO 添加的参数对象
     * @return ApplicationFormTemplateDTO 报名表模板
     */
    @DistributedLock(value = ADD_APPLICATION_FORM_TEMPLATE_LOCK_KEY_PATTERN,
            parameters = "#{#addApplicationFormTemplatePO.recruitmentId}",
            errorMessage = "Failed to acquire applicationFormTemplate lock.")
    @Override
    public Result<ApplicationFormTemplateDTO> addApplicationFormTemplate(
            AddApplicationFormTemplatePO addApplicationFormTemplatePO) {
        // 判断报名表模板的字段是否小于最低限制
        int count = countApplicationFormTemplateFields(addApplicationFormTemplatePO);
        if (count < ApplicationFormTemplateConstants.MIN_APPLICATION_FORM_TEMPLATE_FIELD_NUMBERS) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NUMBER,
                    "The number of applicationFormTemplate fields must not be less than " +
                            ApplicationFormTemplateConstants.MIN_APPLICATION_FORM_TEMPLATE_FIELD_NUMBERS + ".");
        }

        // 判断报名表模板是否存在
        Long recruitmentId = addApplicationFormTemplatePO.getRecruitmentId();
        count = applicationFormTemplateMapper.countByRecruitmentId(recruitmentId);
        if (count > 0) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT_DUPLICATE,
                    "The applicationFormTemplate already exist.");
        }

        // 判断招新状态
        Result<RecruitmentStatusEnum> checkRecruitmentStatusResult =
                recruitmentService.checkRecruitmentStatus(recruitmentId, RecruitmentStatusEnum.ENDED);
        if (checkRecruitmentStatusResult.isFailure()) {
            return Result.fail(checkRecruitmentStatusResult);
        }

        // 添加报名表模板
        ApplicationFormTemplateDO applicationFormTemplateDO = ApplicationFormTemplateDO.builder()
                .recruitmentId(addApplicationFormTemplatePO.getRecruitmentId())
                .prompt(addApplicationFormTemplatePO.getPrompt())
                .avatar(addApplicationFormTemplatePO.getAvatar())
                .fullName(addApplicationFormTemplatePO.getFullName())
                .phone(addApplicationFormTemplatePO.getPhone())
                .firstDepartment(addApplicationFormTemplatePO.getFirstDepartment())
                .secondDepartment(addApplicationFormTemplatePO.getSecondDepartment())
                .email(addApplicationFormTemplatePO.getEmail())
                .introduction(addApplicationFormTemplatePO.getIntroduction())
                .attachment(addApplicationFormTemplatePO.getAttachment())
                .studentNumber(addApplicationFormTemplatePO.getStudentNumber())
                .college(addApplicationFormTemplatePO.getCollege())
                .major(addApplicationFormTemplatePO.getMajor())
                .note(addApplicationFormTemplatePO.getNote())
                .build();
        applicationFormTemplateMapper.insertApplicationFormTemplate(applicationFormTemplateDO);
        return getApplicationFormTemplate(applicationFormTemplateDO.getId());
    }

    /**
     * 获取报名表模板，通过招新编号
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotFound: 找不到该招新编号的报名表模板
     *
     * @param recruitmentId 招新编号
     * @return 报名表模板
     */
    @Override
    public Result<ApplicationFormTemplateDTO> getApplicationFormTemplateByRecruitmentId(Long recruitmentId) {
        ApplicationFormTemplateDO applicationFormTemplateDO =
                applicationFormTemplateMapper.getApplicationFormTemplateByRecruitmentId(recruitmentId);
        if (applicationFormTemplateDO == null) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_FOUND,
                    "The applicationFormTemplate not found.");
        }

        // 封装成DTO
        ApplicationFormTemplateDTO applicationFormTemplateDTO =
                applicationFormTemplateDO2ApplicationFormTemplateDTO(applicationFormTemplateDO);
        return Result.success(applicationFormTemplateDTO);
    }

    /**
     * 更新报名表模板
     *
     * @permission 需要是该报名表模板所属招新所属组织所属用户主体本身
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.Number: 报名表模板的域个数小于最少限制
     *              InvalidParameter.NotExist: 报名表模板不存在
     *              Forbidden.Unavailable: 招新不可用 | 组织不可用
     *              OperationConflict.Status: 招新状态不是 STARTED 之前
     *              OperationConflict.Lock: 获取报名表模板的锁失败
     *
     * @param updateApplicationFormTemplatePO 更新的参数对象
     * @return 更新后的报名表模板
     */
    @DistributedLock(value = UPDATE_APPLICATION_FORM_TEMPLATE_LOCK_KEY_PATTERN,
            parameters = "#{#updateApplicationFormTemplatePO.id}",
            errorMessage = "Failed to acquire applicationFormTemplate lock.")
    @Override
    public Result<ApplicationFormTemplateDTO> updateApplicationFormTemplate(
            UpdateApplicationFormTemplatePO updateApplicationFormTemplatePO) {
        // 判断报名表模板的字段是否小于最低限制
        int count = countApplicationFormTemplateFields(updateApplicationFormTemplatePO);
        if (count < ApplicationFormTemplateConstants.MIN_APPLICATION_FORM_TEMPLATE_FIELD_NUMBERS) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NUMBER,
                    "The number of applicationFormTemplate fields must not be less than " +
                            ApplicationFormTemplateConstants.MIN_APPLICATION_FORM_TEMPLATE_FIELD_NUMBERS + ".");
        }

        // 检查报名表模板的状态是否适合更新
        Result<ApplicationFormTemplateDTO> checkResult = checkApplicationFormTemplateStatusForUpdate(
                updateApplicationFormTemplatePO.getId(), RecruitmentStatusEnum.STARTED);
        if (checkResult.isFailure()) {
            return checkResult;
        }

        // 更新报名表模板
        ApplicationFormTemplateDO applicationFormTemplateDO = ApplicationFormTemplateDO.builder()
                .id(updateApplicationFormTemplatePO.getId())
                .avatar(updateApplicationFormTemplatePO.getAvatar())
                .fullName(updateApplicationFormTemplatePO.getFullName())
                .phone(updateApplicationFormTemplatePO.getPhone())
                .firstDepartment(updateApplicationFormTemplatePO.getFirstDepartment())
                .secondDepartment(updateApplicationFormTemplatePO.getSecondDepartment())
                .email(updateApplicationFormTemplatePO.getEmail())
                .introduction(updateApplicationFormTemplatePO.getIntroduction())
                .attachment(updateApplicationFormTemplatePO.getAttachment())
                .studentNumber(updateApplicationFormTemplatePO.getStudentNumber())
                .college(updateApplicationFormTemplatePO.getCollege())
                .major(updateApplicationFormTemplatePO.getMajor())
                .note(updateApplicationFormTemplatePO.getNote())
                .build();
        applicationFormTemplateMapper.updateApplicationFormTemplate(applicationFormTemplateDO);
        return getApplicationFormTemplate(updateApplicationFormTemplatePO.getId());
    }

    /**
     * 更新报名提示
     *
     * @permission 需要是该报名表模板所属招新所属组织所属用户主体本身
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 报名表模板不存在
     *              Forbidden.Unavailable: 招新不可用 | 组织不可用
     *              OperationConflict.Status: 招新状态不是 ENDED 之前
     *
     * @param id 报名表模板编号
     * @param prompt 报名提示
     * @return 更新后的报名表模板
     */
    @Override
    public Result<ApplicationFormTemplateDTO> updatePrompt(Long id, String prompt) {
        // 检查报名表模板的状态是否适合更新
        Result<ApplicationFormTemplateDTO> checkResult = checkApplicationFormTemplateStatusForUpdate(id,
                RecruitmentStatusEnum.ENDED);
        if (checkResult.isFailure()) {
            return checkResult;
        }

        // 更新报名提示
        applicationFormTemplateMapper.updatePrompt(id, prompt);
        return getApplicationFormTemplate(id);
    }

    /**
     * 停用报名表模板，停用之后无法报名，可以再次启用
     *
     * @permission 需要是该报名表模板所属招新所属组织所属用户主体本身
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 报名表模板不存在
     *              Forbidden.Unavailable: 招新不可用 | 组织不可用
     *              OperationConflict.Status: 招新状态不是 ENDED 之前
     *              OperationConflict.Unmodified: 报名表模板已经被停用
     *
     * @param id 报名表模板编号
     * @return 停用后的报名表模板
     */
    @Override
    public Result<ApplicationFormTemplateDTO> deactivateApplicationFormTemplate(Long id) {
        // 检查报名表模板的状态是否适合更新
        Result<ApplicationFormTemplateDTO> checkResult = checkApplicationFormTemplateStatusForUpdate(id,
                RecruitmentStatusEnum.ENDED);
        if (checkResult.isFailure()) {
            return checkResult;
        }

        // 停用报名表模板
        int count = applicationFormTemplateMapper.updateDeactivated(id, true);
        if (count < 1) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT_UNMODIFIED,
                    "The applicationFormTemplate already deactivated.");
        }
        return getApplicationFormTemplate(id);
    }

    /**
     * 启用报名表模板
     *
     * @permission 需要是该报名表模板所属招新所属组织所属用户主体本身
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 报名表模板不存在
     *              Forbidden.Unavailable: 招新不可用 | 组织不可用
     *              OperationConflict.Status: 招新状态不是 ENDED 之前
     *              OperationConflict.Unmodified: 报名表模板已经被启用
     *
     * @param id 报名表模板编号
     * @return 启用后的报名表模板
     */
    @Override
    public Result<ApplicationFormTemplateDTO> enableApplicationFormTemplate(Long id) {
        // 检查报名表模板的状态是否适合更新
        Result<ApplicationFormTemplateDTO> checkResult = checkApplicationFormTemplateStatusForUpdate(id,
                RecruitmentStatusEnum.ENDED);
        if (checkResult.isFailure()) {
            return checkResult;
        }

        // 启用报名表模板
        int count = applicationFormTemplateMapper.updateDeactivated(id, false);
        if (count < 1) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT_UNMODIFIED,
                    "The applicationFormTemplate already enable.");
        }
        return getApplicationFormTemplate(id);
    }

    /**
     * 判断一个招新是否可以报名
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 招新不存在 | 报名表模板不存在
     *              Forbidden.Unavailable: 招新不可用 | 组织不可用
     *              Forbidden.Deactivated: 报名表模板被停用
     *              OperationConflict.Status: 招新的状态必须是 STARTED
     *
     * @param recruitmentId 招新编号
     * @return 是否可以报名
     */
    @Override
    public <T> Result<T> canRegistration(Long recruitmentId) {
        // 检查招新状态
        Result<RecruitmentStatusEnum> checkRecruitmentStatusResult =
                recruitmentService.checkRecruitmentStatus(recruitmentId, RecruitmentStatusEnum.ENDED);
        if (checkRecruitmentStatusResult.isFailure()) {
            return Result.fail(checkRecruitmentStatusResult);
        }

        // 判断当前状态是否为 STARTED
        RecruitmentStatusEnum recruitmentStatus = checkRecruitmentStatusResult.getData();
        if (recruitmentStatus !=  RecruitmentStatusEnum.STARTED) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT_STATUS,
                    "The recruitment status must be STARTED.");
        }

        // 判断报名表模板是否存在
        Boolean deactivated = applicationFormTemplateMapper.getDeactivatedByRecruitmentId(recruitmentId);
        if (deactivated == null) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_EXIST,
                    "The applicationFormTemplate does not exist.");
        }

        // 检查报名表模板的状态
        if (deactivated) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT_DEACTIVATED,
                    "The applicationFormTemplate already deactivated.");
        }

        // 可以报名
        return Result.success();
    }

    /**
     * 获取招新编号
     *
     * @private 内部方法
     *
     * @param id 报名表模板编号
     * @return 招新编号，如果该报名表不存在则返回 null
     */
    @Override
    public Long getRecruitmentId(Long id) {
        return applicationFormTemplateMapper.getRecruitmentId(id);
    }

    /**
     * 检查报名表模板的状态，为了更新
     *
     * @errorCode InvalidParameter.NotExist: 报名表模板不存在
     *              Forbidden.Unavailable: 招新不可用 | 组织不可用
     *              OperationConflict.Status: 招新状态不允许
     *
     * @param id 报名表模板编号
     * @param followRecruitmentStatus 招新的后续状态，必须在这个状态之前
     * @return 检查结果
     */
    private <T> Result<T> checkApplicationFormTemplateStatusForUpdate(
            Long id, RecruitmentStatusEnum followRecruitmentStatus) {
        // 判断报名表模板是否存在
        Long recruitmentId = applicationFormTemplateMapper.getRecruitmentId(id);
        if (recruitmentId == null) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_EXIST,
                    "The applicationFormTemplate does not exist.");
        }

        // 判断招新状态
        Result<RecruitmentStatusEnum> checkRecruitmentStatusResult =
                recruitmentService.checkRecruitmentStatus(recruitmentId, followRecruitmentStatus);
        if (checkRecruitmentStatusResult.isFailure()) {
            return Result.fail(checkRecruitmentStatusResult);
        }

        // 通过检查
        return Result.success();
    }

    /**
     * 计算一个报名表模板的字段数量
     *
     * @param applicationFormTemplatePO 报名表模板参数对象
     * @return 字段数量
     */
    private int countApplicationFormTemplateFields(ApplicationFormTemplatePO applicationFormTemplatePO) {
        int count = 0;
        for (Field declaredField : ApplicationFormTemplatePO.class.getDeclaredFields()) {
            declaredField.setAccessible(true);
            Boolean value;
            try {
                value = (Boolean) declaredField.get(applicationFormTemplatePO);
                if (value != null && value) {
                    count++;
                }
            } catch (IllegalAccessException ignored) {}
        }
        return count;
    }

    /**
     * 获取报名表模板
     *
     * @param id 报名表模板编号
     * @return 报名表模板
     */
    private Result<ApplicationFormTemplateDTO> getApplicationFormTemplate(Long id) {
        ApplicationFormTemplateDO applicationFormTemplateDO =
                applicationFormTemplateMapper.getApplicationFormTemplate(id);
        ApplicationFormTemplateDTO applicationFormTemplateDTO =
                applicationFormTemplateDO2ApplicationFormTemplateDTO(applicationFormTemplateDO);
        return Result.success(applicationFormTemplateDTO);
    }

    /**
     * ApplicationFormTemplateDO to ApplicationFormTemplateDTO
     *
     * @param applicationFormTemplateDO ApplicationFormTemplateDO
     * @return ApplicationFormTemplateDTO
     */
    private ApplicationFormTemplateDTO applicationFormTemplateDO2ApplicationFormTemplateDTO(
            ApplicationFormTemplateDO applicationFormTemplateDO) {
        return ApplicationFormTemplateDTO.builder()
                .id(applicationFormTemplateDO.getId())
                .recruitmentId(applicationFormTemplateDO.getRecruitmentId())
                .prompt(applicationFormTemplateDO.getPrompt())
                .avatar(applicationFormTemplateDO.getAvatar())
                .fullName(applicationFormTemplateDO.getFullName())
                .phone(applicationFormTemplateDO.getPhone())
                .firstDepartment(applicationFormTemplateDO.getFirstDepartment())
                .secondDepartment(applicationFormTemplateDO.getSecondDepartment())
                .email(applicationFormTemplateDO.getEmail())
                .introduction(applicationFormTemplateDO.getIntroduction())
                .attachment(applicationFormTemplateDO.getAttachment())
                .studentNumber(applicationFormTemplateDO.getStudentNumber())
                .college(applicationFormTemplateDO.getCollege())
                .major(applicationFormTemplateDO.getMajor())
                .note(applicationFormTemplateDO.getNote())
                .deactivated(applicationFormTemplateDO.getDeactivated())
                .build();
    }

}
