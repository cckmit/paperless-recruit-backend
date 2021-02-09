package com.xiaohuashifu.recruit.registration.api.service;

import com.xiaohuashifu.recruit.common.exception.NotFoundServiceException;
import com.xiaohuashifu.recruit.common.exception.ServiceException;
import com.xiaohuashifu.recruit.registration.api.constant.InterviewStatusEnum;
import com.xiaohuashifu.recruit.registration.api.dto.InterviewFormDTO;
import com.xiaohuashifu.recruit.registration.api.request.CreateInterviewFormRequest;
import com.xiaohuashifu.recruit.registration.api.request.UpdateInterviewFormRequest;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * 描述：面试表服务
 *
 * @author xhsf
 * @create 2021/1/4 16:47
 */
public interface InterviewFormService {

    /**
     * 保存面试表
     *
     * @permission 必须是所属面试的主体
     *
     * @param request CreateInterviewFormRequest
     * @return 创建的面试表
     */
    InterviewFormDTO createInterviewForm(@NotNull CreateInterviewFormRequest request) throws ServiceException;

    /**
     * 获取面试表
     *
     * @param id 面试表编号
     * @return InterviewFormDTO
     */
    InterviewFormDTO getInterviewForm(Long id) throws NotFoundServiceException;

    /**
     * 更新面试表
     *
     * @permission 必须是面试表的主体
     *
     * @param request UpdateInterviewFormRequest
     * @return 更新后的面试表
     */
    InterviewFormDTO updateInterviewForm(@NotNull UpdateInterviewFormRequest request) throws ServiceException;

    /**
     * 更新面试状态
     *
     * @permission 必须是面试表的主体
     *
     * @param id 面试表编号
     * @param oldInterviewStatus 旧面试状态
     * @param newInterviewStatus 新面试状态
     * @return 更新后的面试表
     */
    InterviewFormDTO updateInterviewStatus(@NotNull @Positive Long id, @NotNull InterviewStatusEnum oldInterviewStatus,
                                           @NotNull InterviewStatusEnum newInterviewStatus) throws ServiceException;

}
