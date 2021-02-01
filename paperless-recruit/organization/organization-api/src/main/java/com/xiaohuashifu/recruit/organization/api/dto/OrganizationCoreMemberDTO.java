package com.xiaohuashifu.recruit.organization.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 描述：组织核心成员传输对象
 *
 * @author xhsf
 * @create 2020/12/15 16:26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationCoreMemberDTO implements Serializable {

    /**
     * 核心成员编号
     */
    private Long id;

    /**
     * 组织编号
     */
    private Long organizationId;

    /**
     * 组织成员编号
     */
    private Long organizationMemberId;

}
