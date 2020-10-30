package com.xiaohuashifu.recruit.common.pojo.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class UserDTO implements Serializable {
    private Long id;
    private String username;
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

    public static final class UserDTOBuilder {
        private Long id;
        private String username;
        private String password;
        private Boolean available;
        private LocalDateTime createTime;
        private LocalDateTime updateTime;

        private UserDTOBuilder() {
        }

        public static UserDTOBuilder anUserDTO() {
            return new UserDTOBuilder();
        }

        public UserDTOBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public UserDTOBuilder withUsername(String username) {
            this.username = username;
            return this;
        }

        public UserDTOBuilder withPassword(String password) {
            this.password = password;
            return this;
        }

        public UserDTOBuilder withAvailable(Boolean available) {
            this.available = available;
            return this;
        }

        public UserDTOBuilder withCreateTime(LocalDateTime createTime) {
            this.createTime = createTime;
            return this;
        }

        public UserDTOBuilder withUpdateTime(LocalDateTime updateTime) {
            this.updateTime = updateTime;
            return this;
        }

        public UserDTOBuilder but() {
            return anUserDTO().withId(id).withUsername(username).withPassword(password).withAvailable(available).withCreateTime(createTime).withUpdateTime(updateTime);
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
