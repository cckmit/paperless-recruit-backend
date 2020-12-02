package com.xiaohuashifu.recruit.facade.service.vo;

/**
 * 描述：
 *
 * @author xhsf
 * @create 2020/11/29 17:56
 */
public class UserVO {
    private Long id;

    private String username;

    private String phone;

    private String email;

    private Boolean available;

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
        return "UserVO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", available=" + available +
                '}';
    }


    public static final class Builder {
        private Long id;
        private String username;
        private String phone;
        private String email;
        private Boolean available;

        public Builder id(Long id) {
            this.id = id;
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

        public UserVO build() {
            UserVO userVO = new UserVO();
            userVO.setId(id);
            userVO.setUsername(username);
            userVO.setPhone(phone);
            userVO.setEmail(email);
            userVO.setAvailable(available);
            return userVO;
        }
    }
}
