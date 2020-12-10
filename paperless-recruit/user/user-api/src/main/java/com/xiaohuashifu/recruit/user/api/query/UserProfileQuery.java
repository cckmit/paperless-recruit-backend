package com.xiaohuashifu.recruit.user.api.query;

import com.xiaohuashifu.recruit.common.constant.QueryConstants;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.List;

/**
 * 描述：用户个人信息查询参数
 *
 * @author: xhsf
 * @create: 2020/10/29 23:48
 */
public class UserProfileQuery implements Serializable {

    /**
     * 页码
     */
    @NotNull(message = "The pageNum can't be null.")
    @Positive(message = "The pageNum must be greater than 0.")
    private Long pageNum;

    /**
     * 页条数
     */
    @NotNull(message = "The pageSize can't be null.")
    @Positive(message = "The pageSize must be greater than 0.")
    @Max(value = QueryConstants.DEFAULT_PAGE_SIZE,
            message = "The pageSize must be less than or equal to " + QueryConstants.DEFAULT_PAGE_SIZE + ".")
    private Long pageSize;

    /**
     * 用户个人信息编号
     */
    private Long id;

    /**
     * 用户个人信息编号列表
     */
    private List<Long> ids;

    /**
     * 用户编号
     */
    private Long userId;

    /**
     * 姓名，可模糊
     */
    private String fullName;

    /**
     * 学号，可模糊
     */
    private String studentNumber;

    /**
     * 学院，可模糊
     */
    private String college;

    /**
     * 专业，可模糊
     */
    private String major;

    /**
     * 自我介绍，可模糊
     */
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

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
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
                ", ids=" + ids +
                ", userId=" + userId +
                ", fullName='" + fullName + '\'' +
                ", studentNumber='" + studentNumber + '\'' +
                ", college='" + college + '\'' +
                ", major='" + major + '\'' +
                ", introduction='" + introduction + '\'' +
                '}';
    }

    public static final class Builder {
        private Long pageNum;
        private Long pageSize;
        private Long id;
        private List<Long> ids;
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

        public Builder ids(List<Long> ids) {
            this.ids = ids;
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
            userProfileQuery.setIds(ids);
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
