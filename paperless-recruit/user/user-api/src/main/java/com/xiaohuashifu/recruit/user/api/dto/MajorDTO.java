package com.xiaohuashifu.recruit.user.api.dto;

import java.io.Serializable;

/**
 * 描述：专业传输对象
 *
 * @author: xhsf
 * @create: 2020/10/30 15:05
 */
public class MajorDTO implements Serializable {

    /**
     * 专业编号
     */
    private Long id;

    /**
     * 专业所属学院编号
     */
    private Long collegeId;

    /**
     * 专业名
     */
    private String majorName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(Long collegeId) {
        this.collegeId = collegeId;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    @Override
    public String toString() {
        return "MajorDTO{" +
                "id=" + id +
                ", collegeId=" + collegeId +
                ", majorName='" + majorName + '\'' +
                '}';
    }

    public static final class Builder {
        private Long id;
        private Long collegeId;
        private String majorName;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder collegeId(Long collegeId) {
            this.collegeId = collegeId;
            return this;
        }

        public Builder majorName(String majorName) {
            this.majorName = majorName;
            return this;
        }

        public MajorDTO build() {
            MajorDTO majorDTO = new MajorDTO();
            majorDTO.setId(id);
            majorDTO.setCollegeId(collegeId);
            majorDTO.setMajorName(majorName);
            return majorDTO;
        }
    }
}
