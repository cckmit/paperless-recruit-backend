package com.xiaohuashifu.recruit.user.api.dto;

import com.xiaohuashifu.recruit.common.validator.annotation.Password;
import com.xiaohuashifu.recruit.common.validator.annotation.Phone;
import com.xiaohuashifu.recruit.common.validator.annotation.Username;

import javax.validation.constraints.Email;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 描述：用户传输对象
 *
 * @author: xhsf
 * @create: 2020/11/12 19:42
 */
public class UserDTO implements Serializable {

    @Positive
    private Long id;

    @Username
    private String username;

    @Password
    private String password;

    @Phone
    private String phone;

    @Email
    private String email;

    private Boolean available;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    public UserDTO() {
    }

    public UserDTO(Long id, String username, String password, String phone, String email, Boolean available,
                   LocalDateTime createTime, LocalDateTime updateTime) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.available = available;
        this.createTime = createTime;
        this.updateTime = updateTime;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", available=" + available +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

    public static final class Builder {
        private Long id;
        private String username;
        private String password;
        private String phone;
        private String email;
        private Boolean available;
        private LocalDateTime createTime;
        private LocalDateTime updateTime;

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

        public Builder password(String password) {
            this.password = password;
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

        public Builder createTime(LocalDateTime createTime) {
            this.createTime = createTime;
            return this;
        }

        public Builder updateTime(LocalDateTime updateTime) {
            this.updateTime = updateTime;
            return this;
        }

        public UserDTO build() {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(id);
            userDTO.setUsername(username);
            userDTO.setPassword(password);
            userDTO.setPhone(phone);
            userDTO.setEmail(email);
            userDTO.setAvailable(available);
            userDTO.setCreateTime(createTime);
            userDTO.setUpdateTime(updateTime);
            return userDTO;
        }
    }
}
