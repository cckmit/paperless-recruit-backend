package com.xiaohuashifu.recruit.userapi.query;

import java.io.Serializable;

/**
 * 描述：
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/10/29 23:48
 */
public class UserQuery implements Serializable {

    private Integer pageNum;
    private Integer pageSize;
    private Long id;
    private String username;
    private Boolean available;

    public UserQuery() {
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "UserQuery{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", id=" + id +
                ", username='" + username + '\'' +
                ", available=" + available +
                '}';
    }


    public static final class Builder {
        private Integer pageNum;
        private Integer pageSize;
        private Long id;
        private String username;
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

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder available(Boolean available) {
            this.available = available;
            return this;
        }

        public Builder but() {
            return new Builder().pageNum(pageNum).pageSize(pageSize).id(id).username(username).available(available);
        }

        public UserQuery build() {
            UserQuery userQuery = new UserQuery();
            userQuery.setPageNum(pageNum);
            userQuery.setPageSize(pageSize);
            userQuery.setId(id);
            userQuery.setUsername(username);
            userQuery.setAvailable(available);
            return userQuery;
        }
    }
}
