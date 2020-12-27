package com.xiaohuashifu.recruit.user.api.dto;

import java.io.Serializable;

/**
 * 描述：学院传输对象
 *
 * @author: xhsf
 * @create: 2020/10/30 15:05
 */
public class CollegeDTO implements Serializable {

    /**
     * 学院编号
     */
    private Long id;

    /**
     * 学院名
     */
    private String collegeName;

    /**
     * 学院是否被停用
     */
    private Boolean deactivated;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public Boolean getDeactivated() {
        return deactivated;
    }

    public void setDeactivated(Boolean deactivated) {
        this.deactivated = deactivated;
    }

    @Override
    public String toString() {
        return "CollegeDTO{" +
                "id=" + id +
                ", collegeName='" + collegeName + '\'' +
                ", deactivated=" + deactivated +
                '}';
    }

    public static final class Builder {
        private Long id;
        private String collegeName;
        private Boolean deactivated;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder collegeName(String collegeName) {
            this.collegeName = collegeName;
            return this;
        }

        public Builder deactivated(Boolean deactivated) {
            this.deactivated = deactivated;
            return this;
        }

        public CollegeDTO build() {
            CollegeDTO collegeDTO = new CollegeDTO();
            collegeDTO.setId(id);
            collegeDTO.setCollegeName(collegeName);
            collegeDTO.setDeactivated(deactivated);
            return collegeDTO;
        }
    }
}
