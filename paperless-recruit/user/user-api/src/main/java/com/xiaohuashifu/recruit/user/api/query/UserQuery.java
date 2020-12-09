package com.xiaohuashifu.recruit.user.api.query;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.List;

/**
 * 描述：用户查询参数
 *
 * @author: xhsf
 * @create: 2020/10/29 23:48
 */
public class UserQuery implements Serializable {

    @NotNull
    @Positive
    private Long pageNum;
    @NotNull
    @Positive
    @Max(50)
    private Long pageSize;
    private Long id;
    private List<Long> ids;
    private String username;
    private String phone;
    private String email;
    private Boolean available;

    public UserQuery() {
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
                ", ids=" + ids +
                ", username='" + username + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", available=" + available +
                '}';
    }

    public static final class Builder {
        private Long pageNum;
        private Long pageSize;
        private Long id;
        private List<Long> ids;
        private String username;
        private String phone;
        private String email;
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

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder available(Boolean available) {
            this.available = available;
            return this;
        }

        public UserQuery build() {
            UserQuery userQuery = new UserQuery();
            userQuery.setPageNum(pageNum);
            userQuery.setPageSize(pageSize);
            userQuery.setId(id);
            userQuery.setIds(ids);
            userQuery.setUsername(username);
            userQuery.setPhone(phone);
            userQuery.setEmail(email);
            userQuery.setAvailable(available);
            return userQuery;
        }
    }
}
