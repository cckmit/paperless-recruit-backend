package com.xiaohuashifu.recruit.registration.api.request;

import com.xiaohuashifu.recruit.registration.api.constant.InterviewConstants;
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
 * 描述：创建面试请求
 *
 * @author xhsf
 * @create 2021/2/9 16:09
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CreateInterviewRequest implements Serializable {

    /**
     * 招新编号
     */
    @NotNull
    @Positive
    private Long recruitmentId;

    /**
     * 面试标题
     */
    @NotBlank
    @Size(max = InterviewConstants.MAX_TITLE_LENGTH)
    private String title;

}
