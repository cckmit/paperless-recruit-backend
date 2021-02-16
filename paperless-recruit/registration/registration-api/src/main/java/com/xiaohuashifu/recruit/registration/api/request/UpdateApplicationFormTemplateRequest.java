package com.xiaohuashifu.recruit.registration.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * 描述：更新报名表模板的参数对象
 *
 * @author xhsf
 * @create 2020/12/23 21:23
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class UpdateApplicationFormTemplateRequest extends ApplicationFormTemplateRequest {

    /**
     * 报名表模板编号
     */
    @NotNull
    @Positive
    private Long id;

}