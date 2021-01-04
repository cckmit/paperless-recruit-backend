package com.xiaohuashifu.recruit.registration.api.po;

import com.xiaohuashifu.recruit.registration.api.constant.InterviewEvaluationConstants;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 描述：保存面试评价的参数对象
 *
 * @author xhsf
 * @create 2021/1/4 17:42
 */
@Data
@Builder
public class SaveInterviewEvaluationPO implements Serializable {

    /**
     * 面试表编号
     */
    @NotNull(message = "The interviewFormId can't be null.")
    @Positive(message = "The interviewFormId must be greater than 0.")
    private Long interviewFormId;

    /**
     * 面试官编号
     */
    @NotNull(message = "The interviewerId can't be null.")
    @Positive(message = "The interviewerId must be greater than 0.")
    private Long interviewerId;

    /**
     * 评价
     */
    @NotBlank(message = "The evaluation can't be blank.")
    @Size(max = InterviewEvaluationConstants.MAX_EVALUATION_LENGTH,
            message = "The length of evaluation must not be greater than "
                    + InterviewEvaluationConstants.MAX_EVALUATION_LENGTH + ".")
    private String evaluation;

}
