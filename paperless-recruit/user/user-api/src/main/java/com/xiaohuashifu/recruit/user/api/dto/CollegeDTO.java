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

    @Override
    public String toString() {
        return "CollegeDTO{" +
                "id=" + id +
                ", collegeName='" + collegeName + '\'' +
                '}';
    }

    public static final class Builder {
        private Long id;
        private String collegeName;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder collegeName(String collegeName) {
            this.collegeName = collegeName;
            return this;
        }

        public CollegeDTO build() {
            CollegeDTO collegeDTO = new CollegeDTO();
            collegeDTO.setId(id);
            collegeDTO.setCollegeName(collegeName);
            return collegeDTO;
        }
    }
}
