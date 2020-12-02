package com.xiaohuashifu.recruit.user.api.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 描述：用户个人信息传输对象
 *
 * @author: xhsf
 * @create: 2020/10/30 15:05
 */
public class UserProfileDTO implements Serializable {
    private Long id;

    private Long userId;

    private String fullName;

    private String studentNumber;

    private String college;

    private String major;

    private String introduction;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
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
        return "UserProfileDTO{" +
                "id=" + id +
                ", userId=" + userId +
                ", fullName='" + fullName + '\'' +
                ", studentNumber='" + studentNumber + '\'' +
                ", college='" + college + '\'' +
                ", major='" + major + '\'' +
                ", introduction='" + introduction + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

    public static final class Builder {
        private Long id;
        private Long userId;
        private String fullName;
        private String studentNumber;
        private String college;
        private String major;
        private String introduction;
        private LocalDateTime createTime;
        private LocalDateTime updateTime;

        public Builder() {
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public Builder fullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public Builder studentNumber(String studentNumber) {
            this.studentNumber = studentNumber;
            return this;
        }

        public Builder college(String college) {
            this.college = college;
            return this;
        }

        public Builder major(String major) {
            this.major = major;
            return this;
        }

        public Builder introduction(String introduction) {
            this.introduction = introduction;
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

        public UserProfileDTO build() {
            UserProfileDTO userProfileDTO = new UserProfileDTO();
            userProfileDTO.setId(id);
            userProfileDTO.setUserId(userId);
            userProfileDTO.setFullName(fullName);
            userProfileDTO.setStudentNumber(studentNumber);
            userProfileDTO.setCollege(college);
            userProfileDTO.setMajor(major);
            userProfileDTO.setIntroduction(introduction);
            userProfileDTO.setCreateTime(createTime);
            userProfileDTO.setUpdateTime(updateTime);
            return userProfileDTO;
        }
    }
}
