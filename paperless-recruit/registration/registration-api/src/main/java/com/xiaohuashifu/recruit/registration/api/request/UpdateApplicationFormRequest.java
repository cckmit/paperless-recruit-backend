package com.xiaohuashifu.recruit.registration.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * 描述：更新报名表请求
 *
 * @author xhsf
 * @create 2021/2/9 19:53
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
public class UpdateApplicationFormRequest extends ApplicationFormRequest {

    /**
     * 报名表编号
     */
    @NotNull
    @Positive
    private Long id;

}
