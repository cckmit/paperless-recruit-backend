package com.xiaohuashifu.recruit.organization.api.dto;

import com.xiaohuashifu.recruit.organization.api.constant.OrganizationMemberStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 描述：组织成员传输对象
 *
 * @author xhsf
 * @create 2020/12/15 16:26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationMemberDTO implements Serializable {

    /**
     * 成员编号
     */
    private Long id;

    /**
     * 成员所属用户主体
     */
    private Long userId;

    /**
     * 成员所属组织
     */
    private Long organizationId;

    /**
     * 成员所属部门，0为没有部门
     */
    private Long departmentId;

    /**
     * 成员的职位，0为没有职位
     */
    private Long organizationPositionId;

    /**
     * @see OrganizationMemberStatusEnum
     * 成员的状态
     */
    private String memberStatus;

}
