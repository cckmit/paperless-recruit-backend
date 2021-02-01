package com.xiaohuashifu.recruit.organization.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 描述：禁用部门标签传输对象
 *
 * @author xhsf
 * @create 2020/12/14 0:51
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DisableDepartmentLabelDTO implements Serializable {

    /**
     * 部门标签对象
     */
    private DepartmentLabelDTO departmentLabelDTO;

    /**
     * 被删除该部门标签的部门数量
     */
    private Integer deletedNumber;

}
