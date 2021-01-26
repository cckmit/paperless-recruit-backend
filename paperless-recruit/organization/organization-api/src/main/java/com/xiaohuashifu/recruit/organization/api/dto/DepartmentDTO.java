package com.xiaohuashifu.recruit.organization.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

/**
 * 描述：部门传输对象
 *
 * @author xhsf
 * @create 2020/12/6 21:11
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDTO implements Serializable {

    /**
     * 部门编号
     */
    private Long id;

    /**
     * 部门所属组织编号
     */
    private Long organizationId;

    /**
     * 部门名
     */
    private String departmentName;

    /**
     * 部门名缩写
     */
    private String abbreviationDepartmentName;

    /**
     * 部门介绍
     */
    private String introduction;

    /**
     * 部门 logo
     */
    private String logoUrl;

    /**
     * 部门成员数
     */
    private Integer numberOfMembers;

    /**
     * 部门是否被废弃
     */
    private Boolean deactivated;

    /**
     * 部门标签
     */
    private Set<String> labels;

}
