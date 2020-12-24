package com.xiaohuashifu.recruit.user.service.do0;

import java.time.LocalDateTime;

/**
 * 描述：用户信息表映射对象
 *
 * @author: xhsf
 * @create: 2020/10/30 15:05
 */
public class UserProfileDO {
    private Long id;

    private Long userId;

    private String fullName;

    private String studentNumber;

    private Long collegeId;

    private Long majorId;

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

    public Long getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(Long collegeId) {
        this.collegeId = collegeId;
    }

    public Long getMajorId() {
        return majorId;
    }

    public void setMajorId(Long majorId) {
        this.majorId = majorId;
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
        return "UserProfileDO{" +
                "id=" + id +
                ", userId=" + userId +
                ", fullName='" + fullName + '\'' +
                ", studentNumber='" + studentNumber + '\'' +
                ", collegeId=" + collegeId +
                ", majorId=" + majorId +
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
        private Long collegeId;
        private Long majorId;
        private String introduction;
        private LocalDateTime createTime;
        private LocalDateTime updateTime;

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

        public Builder collegeId(Long collegeId) {
            this.collegeId = collegeId;
            return this;
        }

        public Builder majorId(Long majorId) {
            this.majorId = majorId;
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

        public UserProfileDO build() {
            UserProfileDO userProfileDO = new UserProfileDO();
            userProfileDO.setId(id);
            userProfileDO.setUserId(userId);
            userProfileDO.setFullName(fullName);
            userProfileDO.setStudentNumber(studentNumber);
            userProfileDO.setCollegeId(collegeId);
            userProfileDO.setMajorId(majorId);
            userProfileDO.setIntroduction(introduction);
            userProfileDO.setCreateTime(createTime);
            userProfileDO.setUpdateTime(updateTime);
            return userProfileDO;
        }
    }
}
