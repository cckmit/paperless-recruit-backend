package com.xiaohuashifu.recruit.registration.api.service;

import com.xiaohuashifu.recruit.common.exception.NotFoundServiceException;
import com.xiaohuashifu.recruit.common.exception.ServiceException;
import com.xiaohuashifu.recruit.registration.api.constant.ApplicationFormTemplateConstants;
import com.xiaohuashifu.recruit.registration.api.dto.ApplicationFormTemplateDTO;
import com.xiaohuashifu.recruit.registration.api.request.CreateApplicationFormTemplateRequest;
import com.xiaohuashifu.recruit.registration.api.request.UpdateApplicationFormTemplateRequest;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

/**
 * 描述：报名表模板服务
 *
 * @author xhsf
 * @create 2020/12/28 20:42
 */
public interface ApplicationFormTemplateService {

    /**
     * 给一个招新添加报名表模板，必须是招新状态是 ENDED 之前才可以添加
     *
     * @permission 需要是该招新所属组织所属用户主体本身
     *
     * @param request CreateApplicationFormTemplateRequest
     * @return ApplicationFormTemplateDTO 报名表模板
     */
    ApplicationFormTemplateDTO createApplicationFormTemplate(@NotNull CreateApplicationFormTemplateRequest request)
            throws ServiceException;

    /**
     * 获取报名表模板
     *
     * @param id 报名表模板编号
     * @return 报名表模板
     */
    ApplicationFormTemplateDTO getApplicationFormTemplate(@NotNull @Positive Long id) throws NotFoundServiceException;

    /**
     * 获取报名表模板，通过招新编号
     *
     * @param recruitmentId 招新编号
     * @return 报名表模板
     */
    ApplicationFormTemplateDTO getApplicationFormTemplateByRecruitmentId(@NotNull @Positive Long recruitmentId)
            throws NotFoundServiceException;

    /**
     * 更新报名表模板
     *
     * @permission 需要是该报名表模板所属招新所属组织所属用户主体本身
     *
     * @param request UpdateApplicationFormTemplateRequest
     * @return 更新后的报名表模板
     */
    ApplicationFormTemplateDTO updateApplicationFormTemplate(@NotNull UpdateApplicationFormTemplateRequest request)
            throws ServiceException;

    /**
     * 更新报名提示
     *
     * @permission 需要是该报名表模板所属招新所属组织所属用户主体本身
     *
     * @param id 报名表模板编号
     * @param prompt 报名提示
     * @return 更新后的报名表模板
     */
    ApplicationFormTemplateDTO updatePrompt(
            @NotNull @Positive Long id,
            @NotBlank @Size(max = ApplicationFormTemplateConstants.MAX_PROMPT_LENGTH) String prompt)
            throws ServiceException;

    /**
     * 停用报名表模板，停用之后无法报名，可以再次启用
     *
     * @permission 需要是该报名表模板所属招新所属组织所属用户主体本身
     *
     * @param id 报名表模板编号
     * @return 停用后的报名表模板
     */
    ApplicationFormTemplateDTO deactivateApplicationFormTemplate(@NotNull @Positive Long id) throws ServiceException;

    /**
     * 启用报名表模板
     *
     * @permission 需要是该报名表模板所属招新所属组织所属用户主体本身
     *
     * @param id 报名表模板编号
     * @return 启用后的报名表模板
     */
    ApplicationFormTemplateDTO enableApplicationFormTemplate(@NotNull @Positive Long id) throws ServiceException;

    /**
     * 判断一个招新是否可以报名
     *
     * @param recruitmentId 招新编号
     * @return 是否可以报名
     */
    ApplicationFormTemplateDTO canRegistration(@NotNull @Positive Long recruitmentId) throws ServiceException;

}
