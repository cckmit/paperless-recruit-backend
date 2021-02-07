package com.xiaohuashifu.recruit.registration.api.request;

import com.xiaohuashifu.recruit.registration.api.constant.ApplicationFormTemplateConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

/**
 * 描述：创建报名表模板的参数对象
 *
 * @author xhsf
 * @create 2020/12/23 21:23
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class CreateApplicationFormTemplateRequest extends ApplicationFormTemplateRequest {

    /**
     * 招新编号
     */
    @NotNull
    @Positive
    private Long recruitmentId;

    /**
     * 报名提示
     */
    @NotBlank
    @Size(max = ApplicationFormTemplateConstants.MAX_PROMPT_LENGTH)
    private String prompt;
}
