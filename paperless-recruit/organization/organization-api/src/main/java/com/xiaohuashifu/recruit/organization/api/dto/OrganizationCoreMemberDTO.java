package com.xiaohuashifu.recruit.organization.api.dto;

import java.io.Serializable;

/**
 * 描述：组织核心成员传输对象
 *
 * @author xhsf
 * @create 2020/12/15 16:26
 */
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

    public OrganizationCoreMemberDTO(Long id, Long organizationId, Long organizationMemberId) {
        this.id = id;
        this.organizationId = organizationId;
        this.organizationMemberId = organizationMemberId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public Long getOrganizationMemberId() {
        return organizationMemberId;
    }

    public void setOrganizationMemberId(Long organizationMemberId) {
        this.organizationMemberId = organizationMemberId;
    }

    @Override
    public String toString() {
        return "OrganizationCoreMemberDTO{" +
                "id=" + id +
                ", organizationId=" + organizationId +
                ", organizationMemberId=" + organizationMemberId +
                '}';
    }
}
