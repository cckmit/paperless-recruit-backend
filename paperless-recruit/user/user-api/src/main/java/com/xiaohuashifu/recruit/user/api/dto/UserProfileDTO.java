package com.xiaohuashifu.recruit.user.api.dto;

import java.io.Serializable;

/**
 * 描述：用户个人信息传输对象
 *
 * @author: xhsf
 * @create: 2020/10/30 15:05
 */
public class UserProfileDTO implements Serializable {

    /**
     * 用户个人信息编号
     */
    private Long id;

    /**
     * 该用户信息所属用户的编号
     */
    private Long userId;

    /**
     * 姓名
     */
    private String fullName;

    /**
     * 学号
     */
    private String studentNumber;

    /**
     * 学院编号
     */
    private Long collegeId;

    /**
     * 专业编号
     */
    private Long majorId;

    /**
     * 自我介绍
     */
    private String introduction;

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

    @Override
    public String toString() {
        return "UserProfileDTO{" +
                "id=" + id +
                ", userId=" + userId +
                ", fullName='" + fullName + '\'' +
                ", studentNumber='" + studentNumber + '\'' +
                ", collegeId=" + collegeId +
                ", majorId=" + majorId +
                ", introduction='" + introduction + '\'' +
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

        public UserProfileDTO build() {
            UserProfileDTO userProfileDTO = new UserProfileDTO();
            userProfileDTO.setId(id);
            userProfileDTO.setUserId(userId);
            userProfileDTO.setFullName(fullName);
            userProfileDTO.setStudentNumber(studentNumber);
            userProfileDTO.setCollegeId(collegeId);
            userProfileDTO.setMajorId(majorId);
            userProfileDTO.setIntroduction(introduction);
            return userProfileDTO;
        }
    }
}
