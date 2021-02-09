package com.xiaohuashifu.recruit.registration.api.service;

import com.xiaohuashifu.recruit.common.exception.NotFoundServiceException;
import com.xiaohuashifu.recruit.common.exception.ServiceException;
import com.xiaohuashifu.recruit.registration.api.constant.InterviewEvaluationConstants;
import com.xiaohuashifu.recruit.registration.api.dto.InterviewEvaluationDTO;
import com.xiaohuashifu.recruit.registration.api.request.CreateInterviewEvaluationRequest;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

/**
 * 描述：面试评价服务
 *
 * @author xhsf
 * @create 2021/1/4 20:07
 */
public interface InterviewEvaluationService {

    /**
     * 保存面试评价
     *
     * @permission 必须是面试官->组织成员->用户的主体
     *
     * @param request CreateInterviewEvaluationRequest
     * @return 新创建的面试评价
     */
    InterviewEvaluationDTO createInterviewEvaluation(@NotNull CreateInterviewEvaluationRequest request)
            throws ServiceException;

    /**
     * 获取面试评价
     *
     * @param id 面试评价编号
     * @return InterviewEvaluationDTO
     */
    InterviewEvaluationDTO getInterviewEvaluation(Long id) throws NotFoundServiceException;

    /**
     * 更新面试评价
     *
     * @permission 必须是面试评价的主体
     *
     * @param id 面试评价编号
     * @param evaluation 评价
     * @return 更新后的评价
     */
    InterviewEvaluationDTO updateEvaluation(
            @NotNull @Positive Long id,
            @NotBlank @Size(max = InterviewEvaluationConstants.MAX_EVALUATION_LENGTH) String evaluation)
            throws ServiceException;

}
