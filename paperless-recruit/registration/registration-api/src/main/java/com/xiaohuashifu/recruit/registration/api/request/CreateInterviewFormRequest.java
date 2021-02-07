package com.xiaohuashifu.recruit.registration.api.request;

import com.xiaohuashifu.recruit.registration.api.constant.InterviewFormConstants;
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
 * 描述：保存面试表的参数对象
 *
 * @author xhsf
 * @create 2021/1/4 17:42
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateInterviewFormRequest implements Serializable {

    /**
     * 面试编号
     */
    @NotNull
    @Positive
    private Long interviewId;

    /**
     * 报名表编号
     */
    @NotNull
    @Positive
    private Long applicationFormId;

    /**
     * 面试时间
     */
    @NotBlank
    @Size(max = InterviewFormConstants.MAX_INTERVIEW_TIME_LENGTH)
    private String interviewTime;

    /**
     * 面试地点
     */
    @NotBlank
    @Size(max = InterviewFormConstants.MAX_INTERVIEW_LOCATION_LENGTH)
    private String interviewLocation;

    /**
     * 备注
     */
    @NotNull
    @Size(max = InterviewFormConstants.MAX_NOTE_LENGTH)
    private String note;

}
