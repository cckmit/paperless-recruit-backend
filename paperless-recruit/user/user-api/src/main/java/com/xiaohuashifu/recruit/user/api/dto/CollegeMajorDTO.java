package com.xiaohuashifu.recruit.user.api.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 描述：学院专业传输对象
 *
 * @author: xhsf
 * @create: 2020/10/30 15:05
 */
public class CollegeMajorDTO implements Serializable {

    /**
     * 学院编号
     */
    private Long id;

    /**
     * 学院名
     */
    private String collegeName;

    /**
     * 专业列表
     */
    private List<MajorDTO> majorList;

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

    public List<MajorDTO> getMajorList() {
        return majorList;
    }

    public void setMajorList(List<MajorDTO> majorList) {
        this.majorList = majorList;
    }

    @Override
    public String toString() {
        return "CollegeMajorDTO{" +
                "id=" + id +
                ", collegeName='" + collegeName + '\'' +
                ", majorList=" + majorList +
                '}';
    }

    public static final class Builder {
        private Long id;
        private String collegeName;
        private List<MajorDTO> majorList;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder collegeName(String collegeName) {
            this.collegeName = collegeName;
            return this;
        }

        public Builder majorList(List<MajorDTO> majorList) {
            this.majorList = majorList;
            return this;
        }

        public CollegeMajorDTO build() {
            CollegeMajorDTO collegeMajorDTO = new CollegeMajorDTO();
            collegeMajorDTO.setId(id);
            collegeMajorDTO.setCollegeName(collegeName);
            collegeMajorDTO.setMajorList(majorList);
            return collegeMajorDTO;
        }
    }
}
