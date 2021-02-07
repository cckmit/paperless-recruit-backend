package com.xiaohuashifu.recruit.registration.api.request;

import com.xiaohuashifu.recruit.registration.api.constant.InterviewEvaluationConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@NoArgsConstructor
@AllArgsConstructor
public class CreateInterviewEvaluationRequest implements Serializable {

    /**
     * 面试表编号
     */
    @NotNull
    @Positive
    private Long interviewFormId;

    /**
     * 面试官编号
     */
    @NotNull
    @Positive
    private Long interviewerId;

    /**
     * 评价
     */
    @NotBlank
    @Size(max = InterviewEvaluationConstants.MAX_EVALUATION_LENGTH)
    private String evaluation;

}
