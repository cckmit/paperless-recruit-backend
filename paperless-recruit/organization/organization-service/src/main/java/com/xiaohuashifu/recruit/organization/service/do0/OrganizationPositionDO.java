package com.xiaohuashifu.recruit.organization.service.do0;

/**
 * 描述：组织职位数据对象
 *
 * @author xhsf
 * @create 2020/12/13 19:25
 */
public class OrganizationPositionDO {
    private Long id;
    private Long organizationId;
    private String positionName;
    private Integer priority;
    private String createTime;
    private String updateTime;

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

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "OrganizationPositionDO{" +
                "id=" + id +
                ", organizationId=" + organizationId +
                ", positionName='" + positionName + '\'' +
                ", priority=" + priority +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }

    public static final class Builder {
        private Long id;
        private Long organizationId;
        private String positionName;
        private Integer priority;
        private String createTime;
        private String updateTime;

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

        public Builder priority(Integer priority) {
            this.priority = priority;
            return this;
        }

        public Builder createTime(String createTime) {
            this.createTime = createTime;
            return this;
        }

        public Builder updateTime(String updateTime) {
            this.updateTime = updateTime;
            return this;
        }

        public OrganizationPositionDO build() {
            OrganizationPositionDO organizationPositionDO = new OrganizationPositionDO();
            organizationPositionDO.setId(id);
            organizationPositionDO.setOrganizationId(organizationId);
            organizationPositionDO.setPositionName(positionName);
            organizationPositionDO.setPriority(priority);
            organizationPositionDO.setCreateTime(createTime);
            organizationPositionDO.setUpdateTime(updateTime);
            return organizationPositionDO;
        }
    }
}
