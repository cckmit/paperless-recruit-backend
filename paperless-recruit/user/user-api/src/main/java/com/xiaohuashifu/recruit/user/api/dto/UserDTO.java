package com.xiaohuashifu.recruit.user.api.dto;

import com.xiaohuashifu.recruit.common.group.Group;
import com.xiaohuashifu.recruit.common.validator.annotation.Id;
import com.xiaohuashifu.recruit.common.validator.annotation.Password;
import com.xiaohuashifu.recruit.common.validator.annotation.Username;

import java.io.Serializable;
import java.time.LocalDateTime;

public class UserDTO implements Serializable {

    @Id(groups = {Group.class})
    private Long id;

    @Username(groups = {Group.class})
    private String username;

    @Password(groups = {Group.class})
    private String password;

    private Boolean available;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


    public UserDTO() {
    }

    public UserDTO(Long id, String username, String password, Boolean available, LocalDateTime createTime,
                   LocalDateTime updateTime) {
        this.id = id;
        this.username = username;
        this.password = password;
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
                ", available=" + available +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }


    public static final class Builder {
        private Long id;
        private String username;
        private String password;
        private Boolean available;
        private LocalDateTime createTime;
        private LocalDateTime updateTime;

        private Builder() {
        }

        public static Builder anUserDTO() {
            return new Builder();
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

        public Builder but() {
            return anUserDTO().id(id).username(username).password(password).available(available).createTime(createTime).updateTime(updateTime);
        }

        public UserDTO build() {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(id);
            userDTO.setUsername(username);
            userDTO.setPassword(password);
            userDTO.setAvailable(available);
            userDTO.setCreateTime(createTime);
            userDTO.setUpdateTime(updateTime);
            return userDTO;
        }
    }
}
