package com.xiaohuashifu.recruit.organization.api.query;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.List;

/**
 * 描述：组织标签查询参数
 *
 * @author xhsf
 * @create 2020/12/7 13:29
 */
public class OrganizationLabelQuery implements Serializable {
    @NotNull
    @Positive
    private Long pageNum;
    @NotNull
    @Positive
    @Max(50)
    private Long pageSize;
    private Long id;
    private List<Long> ids;
    private String labelName;
    private Boolean available;

    public Long getPageNum() {
        return pageNum;
    }

    public void setPageNum(Long pageNum) {
        this.pageNum = pageNum;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "OrganizationLabelQuery{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", id=" + id +
                ", ids=" + ids +
                ", labelName='" + labelName + '\'' +
                ", available=" + available +
                '}';
    }

    public static final class Builder {
        private Long pageNum;
        private Long pageSize;
        private Long id;
        private List<Long> ids;
        private String labelName;
        private Boolean available;

        public Builder pageNum(Long pageNum) {
            this.pageNum = pageNum;
            return this;
        }

        public Builder pageSize(Long pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder ids(List<Long> ids) {
            this.ids = ids;
            return this;
        }

        public Builder labelName(String labelName) {
            this.labelName = labelName;
            return this;
        }

        public Builder available(Boolean available) {
            this.available = available;
            return this;
        }

        public OrganizationLabelQuery build() {
            OrganizationLabelQuery organizationLabelQuery = new OrganizationLabelQuery();
            organizationLabelQuery.setPageNum(pageNum);
            organizationLabelQuery.setPageSize(pageSize);
            organizationLabelQuery.setId(id);
            organizationLabelQuery.setIds(ids);
            organizationLabelQuery.setLabelName(labelName);
            organizationLabelQuery.setAvailable(available);
            return organizationLabelQuery;
        }
    }
}
