package com.xiaohuashifu.recruit.organization.api.dto;

import com.xiaohuashifu.recruit.organization.api.constant.OrganizationMemberStatusEnum;

import java.io.Serializable;

/**
 * 描述：组织成员传输对象
 *
 * @author xhsf
 * @create 2020/12/15 16:26
 */
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Long getOrganizationPositionId() {
        return organizationPositionId;
    }

    public void setOrganizationPositionId(Long organizationPositionId) {
        this.organizationPositionId = organizationPositionId;
    }

    public String getMemberStatus() {
        return memberStatus;
    }

    public void setMemberStatus(String memberStatus) {
        this.memberStatus = memberStatus;
    }

    @Override
    public String toString() {
        return "OrganizationMemberDTO{" +
                "id=" + id +
                ", userId=" + userId +
                ", organizationId=" + organizationId +
                ", departmentId=" + departmentId +
                ", organizationPositionId=" + organizationPositionId +
                ", memberStatus='" + memberStatus + '\'' +
                '}';
    }

    public static final class Builder {
        private Long id;
        private Long userId;
        private Long organizationId;
        private Long departmentId;
        private Long organizationPositionId;
        private String memberStatus;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public Builder organizationId(Long organizationId) {
            this.organizationId = organizationId;
            return this;
        }

        public Builder departmentId(Long departmentId) {
            this.departmentId = departmentId;
            return this;
        }

        public Builder organizationPositionId(Long organizationPositionId) {
            this.organizationPositionId = organizationPositionId;
            return this;
        }

        public Builder memberStatus(String memberStatus) {
            this.memberStatus = memberStatus;
            return this;
        }

        public OrganizationMemberDTO build() {
            OrganizationMemberDTO organizationMemberDTO = new OrganizationMemberDTO();
            organizationMemberDTO.setId(id);
            organizationMemberDTO.setUserId(userId);
            organizationMemberDTO.setOrganizationId(organizationId);
            organizationMemberDTO.setDepartmentId(departmentId);
            organizationMemberDTO.setOrganizationPositionId(organizationPositionId);
            organizationMemberDTO.setMemberStatus(memberStatus);
            return organizationMemberDTO;
        }
    }
}
