package com.xiaohuashifu.recruit.organization.service.do0;

import java.time.LocalDateTime;

/**
 * 描述：部门的标签映射
 *
 * @author xhsf
 * @create 2020/12/7 13:10
 */
public class DepartmentDepartmentLabelDO {
    private Long id;
    private Long departmentId;
    private String labelName;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "DepartmentDepartmentLabelDO{" +
                "id=" + id +
                ", departmentId=" + departmentId +
                ", labelName='" + labelName + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

    public static final class Builder {
        private Long id;
        private Long departmentId;
        private String labelName;
        private LocalDateTime createTime;
        private LocalDateTime updateTime;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder departmentId(Long departmentId) {
            this.departmentId = departmentId;
            return this;
        }

        public Builder labelName(String labelName) {
            this.labelName = labelName;
            return this;
        }

        public Builder createTime(LocalDateTime createTime) {
            this.createTime = createTime;
            return this;
        }

        public Builder updateTime(LocalDateTime updateTime) {
            this.updateTime = updateTime;
            return this;
        }

        public DepartmentDepartmentLabelDO build() {
            DepartmentDepartmentLabelDO departmentDepartmentLabelDO = new DepartmentDepartmentLabelDO();
            departmentDepartmentLabelDO.setId(id);
            departmentDepartmentLabelDO.setDepartmentId(departmentId);
            departmentDepartmentLabelDO.setLabelName(labelName);
            departmentDepartmentLabelDO.setCreateTime(createTime);
            departmentDepartmentLabelDO.setUpdateTime(updateTime);
            return departmentDepartmentLabelDO;
        }
    }
}
