package com.xiaohuashifu.recruit.user.api.query;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.List;

/**
 * 描述：
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/10/29 23:48
 */
public class UserProfileQuery implements Serializable {

    @NotNull
    @Positive
    private Long pageNum = 1L;
    @NotNull
    @Positive
    private Long pageSize = 10L;
    private Long id;
    private List<Long> idList;
    private Long userId;
    private String fullName;
    private String studentNumber;
    private String college;
    private String major;
    private String introduction;

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

    public List<Long> getIdList() {
        return idList;
    }

    public void setIdList(List<Long> idList) {
        this.idList = idList;
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

    @Override
    public String toString() {
        return "UserProfileQuery{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", id=" + id +
                ", idList=" + idList +
                ", userId=" + userId +
                ", fullName='" + fullName + '\'' +
                ", studentNumber='" + studentNumber + '\'' +
                ", college='" + college + '\'' +
                ", major='" + major + '\'' +
                ", introduction='" + introduction + '\'' +
                '}';
    }

    public static final class Builder {
        private Long pageNum = 1L;
        private Long pageSize = 10L;
        private Long id;
        private List<Long> idList;
        private Long userId;
        private String fullName;
        private String studentNumber;
        private String college;
        private String major;
        private String introduction;

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

        public Builder idList(List<Long> idList) {
            this.idList = idList;
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

        public UserProfileQuery build() {
            UserProfileQuery userProfileQuery = new UserProfileQuery();
            userProfileQuery.setPageNum(pageNum);
            userProfileQuery.setPageSize(pageSize);
            userProfileQuery.setId(id);
            userProfileQuery.setIdList(idList);
            userProfileQuery.setUserId(userId);
            userProfileQuery.setFullName(fullName);
            userProfileQuery.setStudentNumber(studentNumber);
            userProfileQuery.setCollege(college);
            userProfileQuery.setMajor(major);
            userProfileQuery.setIntroduction(introduction);
            return userProfileQuery;
        }
    }
}