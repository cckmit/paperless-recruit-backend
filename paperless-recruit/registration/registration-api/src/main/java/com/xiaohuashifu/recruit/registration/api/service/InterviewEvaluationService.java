package com.xiaohuashifu.recruit.registration.api.service;

import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.registration.api.constant.InterviewEvaluationConstants;
import com.xiaohuashifu.recruit.registration.api.dto.InterviewEvaluationDTO;
import com.xiaohuashifu.recruit.registration.api.po.SaveInterviewEvaluationPO;

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
     * @permission 必须是面试官的主体
     *
     * @param saveInterviewEvaluationPO 保存面试评价的参数对象
     * @return 新创建的面试评价
     */
    Result<InterviewEvaluationDTO> saveInterviewEvaluation(
            @NotNull(message = "The saveInterviewEvaluationPO can't be null.")
                    SaveInterviewEvaluationPO saveInterviewEvaluationPO);

    /**
     * 更新面试评价
     *
     * @permission 必须是面试评价的主体
     *
     * @param id 面试评价编号
     * @param evaluation 评价
     * @return 更新后的评价
     */
    Result<InterviewEvaluationDTO> updateEvaluation(
            @NotNull(message = "The id can't be null.") @Positive(message = "The id must be greater than 0.") Long id,
            @NotBlank(message = "The evaluation can't be blank.")
            @Size(max = InterviewEvaluationConstants.MAX_EVALUATION_LENGTH,
                    message = "The length of evaluation must not be greater than "
                            + InterviewEvaluationConstants.MAX_EVALUATION_LENGTH + ".") String evaluation);

    /**
     * 验证面试评价的主体，也就是面试官的主体
     *
     * @private 内部方法
     *
     * @param id 面试评价编号
     * @param userId 主体编号
     * @return 若是返回 true，不是返回 false
     */
    Boolean authenticatePrincipal(Long id, Long userId);

}
