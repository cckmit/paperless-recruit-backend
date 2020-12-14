package com.xiaohuashifu.recruit.organization.api.dto;

import java.io.Serializable;

/**
 * 描述：禁用部门标签传输对象
 *
 * @author xhsf
 * @create 2020/12/14 0:51
 */
public class DisableDepartmentLabelDTO implements Serializable {
    /**
     * 部门标签对象
     */
    private DepartmentLabelDTO departmentLabelDTO;

    /**
     * 被删除该部门标签的部门数量
     */
    private Integer deletedNumber;

    public DisableDepartmentLabelDTO(DepartmentLabelDTO departmentLabelDTO, Integer deletedNumber) {
        this.departmentLabelDTO = departmentLabelDTO;
        this.deletedNumber = deletedNumber;
    }

    public DepartmentLabelDTO getDepartmentLabelDTO() {
        return departmentLabelDTO;
    }

    public void setDepartmentLabelDTO(DepartmentLabelDTO departmentLabelDTO) {
        this.departmentLabelDTO = departmentLabelDTO;
    }

    public Integer getDeletedNumber() {
        return deletedNumber;
    }

    public void setDeletedNumber(Integer deletedNumber) {
        this.deletedNumber = deletedNumber;
    }

    @Override
    public String toString() {
        return "DisableDepartmentLabelDTO{" +
                "departmentLabelDTO=" + departmentLabelDTO +
                ", deletedNumber=" + deletedNumber +
                '}';
    }
}
