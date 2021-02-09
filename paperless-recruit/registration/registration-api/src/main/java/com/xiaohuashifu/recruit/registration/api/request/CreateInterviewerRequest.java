package com.xiaohuashifu.recruit.registration.api.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;

/**
 * 描述：创建面试官请求
 *
 * @author xhsf
 * @create 2021/2/8 16:07
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateInterviewerRequest implements Serializable {

    /**
     * 组织编号
     */
    @NotNull
    @Positive
    private Long organizationId;

    /**
     * 组织成员编号
     */
    @NotNull
    @Positive
    private Long organizationMemberId;

}
