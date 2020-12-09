package com.xiaohuashifu.recruit.user.api.query;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.List;

/**
 * 描述：权限查询参数
 *
 * @author: xhsf
 * @create: 2020/10/29 23:48
 */
public class PermissionQuery implements Serializable {

    @NotNull
    @Positive
    private Long pageNum;
    @NotNull
    @Positive
    @Max(50)
    private Long pageSize;
    private Long id;
    private List<Long> ids;
    private Long parentPermissionId;
    private String permissionName;
    private String authorizationUrl;
    private Boolean available;

    public PermissionQuery() {
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

    public Long getParentPermissionId() {
        return parentPermissionId;
    }

    public void setParentPermissionId(Long parentPermissionId) {
        this.parentPermissionId = parentPermissionId;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getAuthorizationUrl() {
        return authorizationUrl;
    }

    public void setAuthorizationUrl(String authorizationUrl) {
        this.authorizationUrl = authorizationUrl;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "PermissionQuery{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", ids=" + ids +
                ", id=" + id +
                ", parentPermissionId=" + parentPermissionId +
                ", permissionName='" + permissionName + '\'' +
                ", authorizationUrl='" + authorizationUrl + '\'' +
                ", available=" + available +
                '}';
    }

    public static final class Builder {
        private Long pageNum;
        private Long pageSize;
        private List<Long> ids;
        private Long id;
        private Long parentPermissionId;
        private String permissionName;
        private String authorizationUrl;
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

        public Builder ids(List<Long> ids) {
            this.ids = ids;
            return this;
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder parentPermissionId(Long parentPermissionId) {
            this.parentPermissionId = parentPermissionId;
            return this;
        }

        public Builder permissionName(String permissionName) {
            this.permissionName = permissionName;
            return this;
        }

        public Builder authorizationUrl(String authorizationUrl) {
            this.authorizationUrl = authorizationUrl;
            return this;
        }

        public Builder available(Boolean available) {
            this.available = available;
            return this;
        }

        public PermissionQuery build() {
            PermissionQuery permissionQuery = new PermissionQuery();
            permissionQuery.setPageNum(pageNum);
            permissionQuery.setPageSize(pageSize);
            permissionQuery.setIds(ids);
            permissionQuery.setId(id);
            permissionQuery.setParentPermissionId(parentPermissionId);
            permissionQuery.setPermissionName(permissionName);
            permissionQuery.setAuthorizationUrl(authorizationUrl);
            permissionQuery.setAvailable(available);
            return permissionQuery;
        }
    }
}
