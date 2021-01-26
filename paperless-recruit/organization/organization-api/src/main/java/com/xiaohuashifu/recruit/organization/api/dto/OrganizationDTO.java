package com.xiaohuashifu.recruit.organization.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

/**
 * 描述：组织传输对象
 *
 * @author xhsf
 * @create 2020/12/6 21:11
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationDTO implements Serializable {

    /**
     * 组织编号
     */
    private Long id;

    /**
     * 组织所属主体编号
     */
    private Long userId;

    /**
     * 组织名
     */
    private String organizationName;

    /**
     * 组织名缩写
     */
    private String abbreviationOrganizationName;

    /**
     * 组织介绍
     */
    private String introduction;

    /**
     * 组织 logo
     */
    private String logoUrl;

    /**
     * 组织成员数
     */
    private Integer numberOfMembers;

    /**
     * 组织部门数
     */
    private Integer numberOfDepartments;

    /**
     * 组织标签
     */
    private Set<String> labels;

    /**
     * 组织是否有效
     */
    private Boolean available;

}
