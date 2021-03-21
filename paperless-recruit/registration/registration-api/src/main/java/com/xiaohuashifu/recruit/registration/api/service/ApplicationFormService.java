package com.xiaohuashifu.recruit.registration.api.service;

import com.xiaohuashifu.recruit.common.exception.NotFoundServiceException;
import com.xiaohuashifu.recruit.common.exception.ServiceException;
import com.xiaohuashifu.recruit.common.query.QueryResult;
import com.xiaohuashifu.recruit.registration.api.dto.ApplicationFormDTO;
import com.xiaohuashifu.recruit.registration.api.query.ApplicationFormQuery;
import com.xiaohuashifu.recruit.registration.api.request.CreateApplicationFormRequest;
import com.xiaohuashifu.recruit.registration.api.request.UpdateApplicationFormRequest;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * 描述：报名表服务
 *
 * @author xhsf
 * @create 2020/12/28 22:00
 */
public interface ApplicationFormService {

    /**
     * 创建报名表
     *
     * @permission 必须是用户本身
     *
     * @param request CreateApplicationFormRequest
     * @return 创建的报名表
     */
    ApplicationFormDTO createApplicationForm(@NotNull CreateApplicationFormRequest request) throws ServiceException;

    /**
     * 获取报名表
     *
     * @permission 必须是用户本身，或者该报名表所属招新所属组织所属用户主体是用户本身
     *              或者是该报名表所属招新所属组织的面试官的用户主体本身
     *
     * @param id 报名表编号
     * @return 报名表
     */
    ApplicationFormDTO getApplicationForm(@NotNull @Positive Long id) throws NotFoundServiceException;

    /**
     * 查询报名表
     *
     * @param query ApplicationFormQuery
     * @return 可能返回空列表
     */
    QueryResult<ApplicationFormDTO> listApplicationForms(ApplicationFormQuery query);

    /**
     * 更新报名表
     *
     * @param request UpdateApplicationFormRequest
     * @return ApplicationFormDTO
     */
    ApplicationFormDTO updateApplicationForm(@NotNull UpdateApplicationFormRequest request) throws ServiceException;

}
