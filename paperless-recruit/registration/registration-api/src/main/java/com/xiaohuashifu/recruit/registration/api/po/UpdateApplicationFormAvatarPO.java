package com.xiaohuashifu.recruit.registration.api.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * 描述：更新报名表头像参数对象
 *
 * @author xhsf
 * @create 2020/12/28 22:08
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class UpdateApplicationFormAvatarPO extends ApplicationFormAvatarPO {

    /**
     * 报名表编号
     */
    @NotNull(message = "The id can't be null.")
    @Positive(message = "The id must be greater than 0.")
    private Long id;

}
