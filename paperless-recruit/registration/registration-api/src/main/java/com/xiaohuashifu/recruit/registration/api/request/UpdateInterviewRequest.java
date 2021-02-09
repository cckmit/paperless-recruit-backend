package com.xiaohuashifu.recruit.registration.api.request;

import com.xiaohuashifu.recruit.common.validator.annotation.NotAllCharactersBlank;
import com.xiaohuashifu.recruit.registration.api.constant.InterviewConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 描述：更新面试请求
 *
 * @author xhsf
 * @create 2021/2/9 16:13
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateInterviewRequest implements Serializable {

    /**
     * 面试编号
     */
    @NotNull
    @Positive
    private Long id;

    /**
     * 面试标题
     */
    @NotAllCharactersBlank
    @Size(max = InterviewConstants.MAX_TITLE_LENGTH)
    private String title;

}
