package com.xiaohuashifu.recruit.registration.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * 描述：创建报名表的参数对象
 *
 * @author xhsf
 * @create 2020/12/23 21:23
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
public class CreateApplicationFormRequest extends ApplicationFormRequest {

    /**
     * 报名者用户编号
     */
    @NotNull
    @Positive
    private Long userId;

    /**
     * 招新编号
     */
    @NotNull
    @Positive
    private Long recruitmentId;

}
