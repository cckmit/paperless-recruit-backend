package com.xiaohuashifu.recruit.user.api.query;

import com.xiaohuashifu.recruit.common.constant.QueryConstants;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.List;

/**
 * 描述：角色查询参数
 *
 * @author: xhsf
 * @create: 2020/10/29 23:48
 */
public class RoleQuery implements Serializable {

    /**
     * 页码
     */
    @NotNull(message = "The pageNum can't be null.")
    @Positive(message = "The pageNum must be greater than 0.")
    private Long pageNum;

    /**
     * 页条数
     */
    @NotNull(message = "The pageSize can't be null.")
    @Positive(message = "The pageSize must be greater than 0.")
    @Max(value = QueryConstants.DEFAULT_PAGE_SIZE,
            message = "The pageSize must be less than or equal to " + QueryConstants.DEFAULT_PAGE_SIZE + ".")
    private Long pageSize;

    /**
     * 角色编号
     */
    private Long id;

    /**
     * 角色编号列表
     */
    private List<Long> ids;

    /**
     * 父角色编号
     */
    private Long parentRoleId;

    /**
     * 角色名，可模糊
     */
    private String roleName;

    /**
     * 角色是否可用
     */
    private Boolean available;

    public RoleQuery() {
    }

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

    public Long getParentRoleId() {
        return parentRoleId;
    }

    public void setParentRoleId(Long parentRoleId) {
        this.parentRoleId = parentRoleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "RoleQuery{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", id=" + id +
                ", ids=" + ids +
                ", parentRoleId=" + parentRoleId +
                ", roleName='" + roleName + '\'' +
                ", available=" + available +
                '}';
    }

    public static final class Builder {
        private Long pageNum;
        private Long pageSize;
        private Long id;
        private List<Long> ids;
        private Long parentRoleId;
        private String roleName;
        private Boolean available;

        public Builder() {
        }

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

        public Builder parentRoleId(Long parentRoleId) {
            this.parentRoleId = parentRoleId;
            return this;
        }

        public Builder roleName(String roleName) {
            this.roleName = roleName;
            return this;
        }

        public Builder available(Boolean available) {
            this.available = available;
            return this;
        }

        public RoleQuery build() {
            RoleQuery roleQuery = new RoleQuery();
            roleQuery.setPageNum(pageNum);
            roleQuery.setPageSize(pageSize);
            roleQuery.setId(id);
            roleQuery.setIds(ids);
            roleQuery.setParentRoleId(parentRoleId);
            roleQuery.setRoleName(roleName);
            roleQuery.setAvailable(available);
            return roleQuery;
        }
    }
}
