package com.xiaohuashifu.recruit.registration.api.service;

import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.registration.api.constant.InterviewEvaluationConstants;
import com.xiaohuashifu.recruit.registration.api.dto.InterviewEvaluationDTO;
import com.xiaohuashifu.recruit.registration.api.request.SaveInterviewEvaluationPO;

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
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 面试表不存在 | 面试官不存在
     *              Forbidden: 面试官和面试表不是同一个组织的
     *              OperationConflict.Status: 面试表状态已经在 PENDING 之后，不可评价
     *              OperationConflict.Duplicate: 面试评价已经存在
     *              OperationConflict.Lock: 获取保存面试评价的锁失败
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
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 面试评价不存在
     *              OperationConflict.Status: 面试表状态已经在 PENDING 之后，不可评价
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
     * 验证面试评价的主体，也就是面试评价->面试官->组织成员->用户的主体
     *
     * @private 内部方法
     *
     * @param id 面试评价编号
     * @param userId 主体编号
     * @return 若是返回 true，不是返回 false
     */
    Boolean authenticatePrincipal(Long id, Long userId);

}
