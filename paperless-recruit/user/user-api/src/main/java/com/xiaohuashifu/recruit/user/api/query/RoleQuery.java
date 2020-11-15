package com.xiaohuashifu.recruit.user.api.query;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.List;

/**
 * 描述：
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/10/29 23:48
 */
public class RoleQuery implements Serializable {

    @NotNull
    @Positive
    private Long pageNum = 1L;
    @NotNull
    @Positive
    private Long pageSize = 10L;
    private Long id;
    private List<Long> idList;
    private Long parentRoleId;
    private String roleName;
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

    public List<Long> getIdList() {
        return idList;
    }

    public void setIdList(List<Long> idList) {
        this.idList = idList;
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
                ", idList=" + idList +
                ", parentRoleId=" + parentRoleId +
                ", roleName='" + roleName + '\'' +
                ", available=" + available +
                '}';
    }


    public static final class Builder {
        private Long pageNum = 1L;
        private Long pageSize = 10L;
        private Long id;
        private List<Long> idList;
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

        public Builder idList(List<Long> idList) {
            this.idList = idList;
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
            roleQuery.setIdList(idList);
            roleQuery.setParentRoleId(parentRoleId);
            roleQuery.setRoleName(roleName);
            roleQuery.setAvailable(available);
            return roleQuery;
        }
    }
}
