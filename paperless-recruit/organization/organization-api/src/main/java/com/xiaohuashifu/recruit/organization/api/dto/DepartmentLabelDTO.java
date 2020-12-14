package com.xiaohuashifu.recruit.organization.api.dto;

import java.io.Serializable;

/**
 * 描述：部门标签
 *
 * @author xhsf
 * @create 2020/12/7 13:10
 */
public class DepartmentLabelDTO implements Serializable {

    /**
     * 部门标签编号
     */
    private Long id;

    /**
     * 标签名
     */
    private String labelName;

    /**
     * 引用次数
     */
    private Long referenceNumber;

    /**
     * 标签是否可用
     */
    private Boolean available;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public Long getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(Long referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "DepartmentLabelDTO{" +
                "id=" + id +
                ", labelName='" + labelName + '\'' +
                ", referenceNumber=" + referenceNumber +
                ", available=" + available +
                '}';
    }

    public static final class Builder {
        private Long id;
        private String labelName;
        private Long referenceNumber;
        private Boolean available;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder labelName(String labelName) {
            this.labelName = labelName;
            return this;
        }

        public Builder referenceNumber(Long referenceNumber) {
            this.referenceNumber = referenceNumber;
            return this;
        }

        public Builder available(Boolean available) {
            this.available = available;
            return this;
        }

        public DepartmentLabelDTO build() {
            DepartmentLabelDTO departmentLabelDTO = new DepartmentLabelDTO();
            departmentLabelDTO.setId(id);
            departmentLabelDTO.setLabelName(labelName);
            departmentLabelDTO.setReferenceNumber(referenceNumber);
            departmentLabelDTO.setAvailable(available);
            return departmentLabelDTO;
        }
    }

}
