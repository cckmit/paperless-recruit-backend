package com.xiaohuashifu.recruit.organization.api.dto;

import java.io.Serializable;

/**
 * 描述：组织职位传输对象
 *
 * @author xhsf
 * @create 2020/12/13 19:25
 */
public class OrganizationPositionDTO implements Serializable {

    /**
     * 组织职位编号
     */
    private Long id;

    /**
     * 组织编号
     */
    private Long organizationId;

    /**
     * 职位名
     */
    private String positionName;

    /**
     * 职位优先级，0最高，9最低
     */
    private Long priority;

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

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public Long getPriority() {
        return priority;
    }

    public void setPriority(Long priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "OrganizationPositionDTO{" +
                "id=" + id +
                ", organizationId=" + organizationId +
                ", positionName='" + positionName + '\'' +
                ", priority=" + priority +
                '}';
    }

    public static final class Builder {
        private Long id;
        private Long organizationId;
        private String positionName;
        private Long priority;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder organizationId(Long organizationId) {
            this.organizationId = organizationId;
            return this;
        }

        public Builder positionName(String positionName) {
            this.positionName = positionName;
            return this;
        }

        public Builder priority(Long priority) {
            this.priority = priority;
            return this;
        }

        public OrganizationPositionDTO build() {
            OrganizationPositionDTO organizationPositionDTO = new OrganizationPositionDTO();
            organizationPositionDTO.setId(id);
            organizationPositionDTO.setOrganizationId(organizationId);
            organizationPositionDTO.setPositionName(positionName);
            organizationPositionDTO.setPriority(priority);
            return organizationPositionDTO;
        }
    }
}
