package com.xiaohuashifu.recruit.organization.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 描述：禁用组织标签传输对象
 *
 * @author xhsf
 * @create 2020/12/14 0:51
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DisableOrganizationLabelDTO implements Serializable {

    /**
     * 组织标签对象
     */
    private OrganizationLabelDTO organizationLabelDTO;

    /**
     * 被删除该组织标签的组织数量
     */
    private Integer deletedNumber;

}
