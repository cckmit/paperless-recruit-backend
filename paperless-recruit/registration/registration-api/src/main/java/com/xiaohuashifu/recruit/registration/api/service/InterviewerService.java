package com.xiaohuashifu.recruit.registration.api.service;

import com.xiaohuashifu.recruit.common.exception.NotFoundServiceException;
import com.xiaohuashifu.recruit.common.exception.ServiceException;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.registration.api.dto.InterviewerDTO;
import com.xiaohuashifu.recruit.registration.api.request.CreateInterviewerRequest;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * 描述：面试官服务
 *
 * @author xhsf
 * @create 2021/1/4 20:21
 */
public interface InterviewerService {

    /**
     * 保存面试官
     *
     * @permission Role: organization
     *
     * @param request CreateInterviewerRequest
     * @return 面试官对象
     */
    InterviewerDTO createInterviewer(@NotNull CreateInterviewerRequest request) throws ServiceException;

    /**
     * 获取面试官
     *
     * @param id 面试官编号
     * @return InterviewerDTO
     */
    InterviewerDTO getInterviewer(Long id) throws NotFoundServiceException;

    /**
     * 禁用面试官
     *
     * @permission 必须是面试官的主体
     *
     * @param id 面试官编号
     * @return 禁用后的面试官对象
     */
    InterviewerDTO disableInterviewer(@NotNull @Positive Long id) throws ServiceException;

    /**
     * 解禁面试官
     *
     * @permission 必须是面试官的主体
     *
     * @param id 面试官编号
     * @return 解禁后的面试官对象
     */
    InterviewerDTO enableInterviewer(@NotNull @Positive Long id) throws ServiceException;

}
