package com.xiaohuashifu.recruit.registration.api.request;

import com.xiaohuashifu.recruit.common.validator.annotation.NotAllCharactersBlank;
import com.xiaohuashifu.recruit.registration.api.constant.InterviewFormConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 描述：更新面试表请求
 *
 * @author xhsf
 * @create 2021/2/9 16:50
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateInterviewFormRequest implements Serializable {

    /**
     * 面试表编号
     */
    @NotNull
    @Positive
    private Long id;

    /**
     * 面试时间
     */
    @NotAllCharactersBlank
    @Size(max = InterviewFormConstants.MAX_INTERVIEW_TIME_LENGTH)
    private String interviewTime;

    /**
     * 面试地点
     */
    @NotAllCharactersBlank
    @Size(max = InterviewFormConstants.MAX_INTERVIEW_LOCATION_LENGTH)
    private String interviewLocation;

    /**
     * 面试备注
     */
    @NotAllCharactersBlank
    @Size(max = InterviewFormConstants.MAX_NOTE_LENGTH)
    private String note;

}
