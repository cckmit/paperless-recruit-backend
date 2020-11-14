package com.xiaohuashifu.recruit.user.api.query;

import java.io.Serializable;
import java.util.List;

/**
 * 描述：
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/10/29 23:48
 */
public class PermissionQuery implements Serializable {

    private Integer pageNum = 1;
    private Integer pageSize = 10;
    private List<Long> idList;
    private Long id;
    private Long parentPermissionId;
    private String permissionName;
    private String authorizationUrl;
    private Boolean available;

    public PermissionQuery() {
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
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
                ", idList=" + idList +
                ", id=" + id +
                ", parentPermissionId=" + parentPermissionId +
                ", permissionName='" + permissionName + '\'' +
                ", authorizationUrl='" + authorizationUrl + '\'' +
                ", available=" + available +
                '}';
    }


    public static final class Builder {
        private Integer pageNum = 1;
        private Integer pageSize = 10;
        private List<Long> idList;
        private Long id;
        private Long parentPermissionId;
        private String permissionName;
        private String authorizationUrl;
        private Boolean available;

        public Builder() {
        }

        public Builder pageNum(Integer pageNum) {
            this.pageNum = pageNum;
            return this;
        }

        public Builder pageSize(Integer pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        public Builder idList(List<Long> idList) {
            this.idList = idList;
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
            permissionQuery.setIdList(idList);
            permissionQuery.setId(id);
            permissionQuery.setParentPermissionId(parentPermissionId);
            permissionQuery.setPermissionName(permissionName);
            permissionQuery.setAuthorizationUrl(authorizationUrl);
            permissionQuery.setAvailable(available);
            return permissionQuery;
        }
    }
}
