package com.xiaohuashifu.recruit.registration.api.service;

import com.xiaohuashifu.recruit.common.exception.NotFoundServiceException;
import com.xiaohuashifu.recruit.common.exception.ServiceException;
import com.xiaohuashifu.recruit.common.exception.unprocessable.OverLimitServiceException;
import com.xiaohuashifu.recruit.registration.api.dto.InterviewDTO;
import com.xiaohuashifu.recruit.registration.api.dto.RecruitmentDTO;
import com.xiaohuashifu.recruit.registration.api.request.CreateInterviewRequest;
import com.xiaohuashifu.recruit.registration.api.request.UpdateInterviewRequest;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * 描述：面试服务
 *
 * @author xhsf
 * @create 2021/1/4 16:32
 */
public interface InterviewService {

    /**
     * 创建一个面试
     *
     * @permission Role: organization
     *
     * @param request CreateInterviewRequest
     * @return 面试对象
     */
    InterviewDTO createInterview(@NotNull CreateInterviewRequest request) throws ServiceException;

    /**
     * 获取面试
     *
     * @param id 面试编号
     * @return 面试
     */
    InterviewDTO getInterview(Long id) throws NotFoundServiceException;

    /**
     * 更新面试
     *
     * @permission Role: organization
     *
     * @param request UpdateInterviewRequest
     * @return 更新后的面试对象
     */
    InterviewDTO updateInterview(@NotNull UpdateInterviewRequest request) throws ServiceException;

    /**
     * 获取下一个轮次
     *
     * @permission Role: organization
     *
     * @param recruitmentId 招新编号
     * @return 下一个轮次
     */
    Integer getNextRound(@NotNull @Positive Long recruitmentId) throws OverLimitServiceException;

    /**
     * 检查面试状态
     *
     * @private 内部方法
     *
     * @param id 面试编号
     * @return 检查结果
     */
    InterviewDTO checkInterviewStatus(@NotNull @Positive Long id) throws ServiceException;

}
