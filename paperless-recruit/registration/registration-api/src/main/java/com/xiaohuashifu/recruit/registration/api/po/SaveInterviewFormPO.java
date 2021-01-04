package com.xiaohuashifu.recruit.registration.api.po;

import com.xiaohuashifu.recruit.registration.api.constant.InterviewFormConstants;
import lombok.Builder;
import lombok.Data;

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
public class SaveInterviewFormPO implements Serializable {

    /**
     * 面试编号
     */
    @NotNull(message = "The interviewId can't be null.")
    @Positive(message = "The interviewId must be greater than 0.")
    private Long interviewId;

    /**
     * 报名表编号
     */
    @NotNull(message = "The applicationFormId can't be null.")
    @Positive(message = "The applicationFormId must be greater than 0.")
    private Long applicationFormId;

    /**
     * 面试时间
     */
    @NotBlank(message = "The interviewTime can't be blank.")
    @Size(max = InterviewFormConstants.MAX_INTERVIEW_TIME_LENGTH,
            message = "The length of interviewTime must not be greater than "
                    + InterviewFormConstants.MAX_INTERVIEW_TIME_LENGTH + ".")
    private String interviewTime;

    /**
     * 面试地点
     */
    @NotBlank(message = "The interviewLocation can't be blank.")
    @Size(max = InterviewFormConstants.MAX_INTERVIEW_LOCATION_LENGTH,
            message = "The length of interviewLocation must not be greater than "
                    + InterviewFormConstants.MAX_INTERVIEW_LOCATION_LENGTH + ".")
    private String interviewLocation;

    /**
     * 备注
     */
    @NotNull(message = "The note can't be null.")
    @Size(max = InterviewFormConstants.MAX_NOTE_LENGTH,
            message = "The length of note must not be greater than "
                    + InterviewFormConstants.MAX_NOTE_LENGTH + ".")
    private String note;

}
