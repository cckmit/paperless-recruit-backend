package com.xiaohuashifu.recruit.api.query;

/**
 * 描述：
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/10/29 23:48
 */
public class UserQuery {

    private Long id;
    private String username;
    private Boolean available;

    public UserQuery() {
    }

    public UserQuery(Long id, String username, Boolean available) {
        this.id = id;
        this.username = username;
        this.available = available;
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
                "id=" + id +
                ", username='" + username + '\'' +
                ", available=" + available +
                '}';
    }


    public static final class Builder {
        private Long id;
        private String username;
        private Boolean available;

        public Builder() {
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
            return new Builder().id(id).username(username).available(available);
        }

        public UserQuery build() {
            UserQuery userQuery = new UserQuery();
            userQuery.setId(id);
            userQuery.setUsername(username);
            userQuery.setAvailable(available);
            return userQuery;
        }
    }
}
