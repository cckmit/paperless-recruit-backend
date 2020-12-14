package com.xiaohuashifu.recruit.organization.api.dto;

import java.io.Serializable;

/**
 * 描述：禁用组织标签传输对象
 *
 * @author xhsf
 * @create 2020/12/14 0:51
 */
public class DisableOrganizationLabelDTO implements Serializable {

    /**
     * 组织标签对象
     */
    private OrganizationLabelDTO organizationLabelDTO;

    /**
     * 被删除该组织标签的组织数量
     */
    private Integer deletedNumber;

    public DisableOrganizationLabelDTO(OrganizationLabelDTO organizationLabelDTO, Integer deletedNumber) {
        this.organizationLabelDTO = organizationLabelDTO;
        this.deletedNumber = deletedNumber;
    }

    public OrganizationLabelDTO getOrganizationLabelDTO() {
        return organizationLabelDTO;
    }

    public void setOrganizationLabelDTO(OrganizationLabelDTO organizationLabelDTO) {
        this.organizationLabelDTO = organizationLabelDTO;
    }

    public Integer getDeletedNumber() {
        return deletedNumber;
    }

    public void setDeletedNumber(Integer deletedNumber) {
        this.deletedNumber = deletedNumber;
    }

    @Override
    public String toString() {
        return "DisableOrganizationLabelDTO{" +
                "organizationLabelDTO=" + organizationLabelDTO +
                ", deletedNumber=" + deletedNumber +
                '}';
    }
}
