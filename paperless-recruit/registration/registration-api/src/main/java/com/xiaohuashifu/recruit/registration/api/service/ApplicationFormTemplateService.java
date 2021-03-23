package com.xiaohuashifu.recruit.registration.api.service;

import com.xiaohuashifu.recruit.common.exception.NotFoundServiceException;
import com.xiaohuashifu.recruit.common.exception.ServiceException;
import com.xiaohuashifu.recruit.registration.api.dto.ApplicationFormTemplateDTO;
import com.xiaohuashifu.recruit.registration.api.request.UpdateApplicationFormTemplateRequest;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * 描述：报名表模板服务
 *
 * @author xhsf
 * @create 2020/12/28 20:42
 */
public interface ApplicationFormTemplateService {

    /**
     * 创建报名表模板
     *
     * 会创建一个空模板
     *
     * @param userId 用户编号
     * @return ApplicationFormTemplateDTO 报名表模板
     */
    ApplicationFormTemplateDTO createApplicationFormTemplate(@NotNull @Positive Long userId)
            throws ServiceException;

    /**
     * 获取报名表模板
     *
     * @param id 报名表模板编号
     * @return 报名表模板
     */
    ApplicationFormTemplateDTO getApplicationFormTemplate(@NotNull @Positive Long id) throws NotFoundServiceException;

    /**
     * 获取报名表模板，通过用户编号
     *
     * @param userId 用户编号
     * @return 报名表模板
     */
    ApplicationFormTemplateDTO getApplicationFormTemplateByUserId(@NotNull @Positive Long userId)
            throws NotFoundServiceException;

    /**
     * 更新报名表模板
     *
     * @param request UpdateApplicationFormTemplateRequest
     * @return 更新后的报名表模板
     */
    ApplicationFormTemplateDTO updateApplicationFormTemplate(@NotNull UpdateApplicationFormTemplateRequest request)
            throws ServiceException;

}
