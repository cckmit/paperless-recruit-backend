package com.xiaohuashifu.recruit.organization.api.request;

import com.xiaohuashifu.recruit.organization.api.constant.OrganizationMemberStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;

/**
 * 描述：更新组织成员请求
 *
 * @author xhsf
 * @create 2021/2/7 20:18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrganizationMemberRequest implements Serializable {

    /**
     * 组织成员编号
     */
    @NotNull
    @Positive
    private Long id;

    /**
     * 部门编号，若为0表示不绑定任何部门
     */
    @PositiveOrZero
    private Long departmentId;

    /**
     * 组织职位编号，若为0表示不绑定任何组织职位
     */
    @PositiveOrZero
    private Long organizationPositionId;

    /**
     * 成员状态
     */
    private OrganizationMemberStatusEnum memberStatus;

}
