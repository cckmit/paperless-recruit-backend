package com.xiaohuashifu.recruit.user.api.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 描述：学院专业传输对象
 *
 * @author: xhsf
 * @create: 2020/10/30 15:05
 */
public class CollegeMajorDTO implements Serializable {

    private Long id;

    private String collegeName;

    private List<MajorDTO> majorList;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

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
        return "CollegeMajorDTO{" +
                "id=" + id +
                ", collegeName='" + collegeName + '\'' +
                ", majorList=" + majorList +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

    public static final class Builder {
        private Long id;
        private String collegeName;
        private List<MajorDTO> majorList;
        private LocalDateTime createTime;
        private LocalDateTime updateTime;

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

        public Builder createTime(LocalDateTime createTime) {
            this.createTime = createTime;
            return this;
        }

        public Builder updateTime(LocalDateTime updateTime) {
            this.updateTime = updateTime;
            return this;
        }

        public CollegeMajorDTO build() {
            CollegeMajorDTO collegeMajorDTO = new CollegeMajorDTO();
            collegeMajorDTO.setId(id);
            collegeMajorDTO.setCollegeName(collegeName);
            collegeMajorDTO.setMajorList(majorList);
            collegeMajorDTO.setCreateTime(createTime);
            collegeMajorDTO.setUpdateTime(updateTime);
            return collegeMajorDTO;
        }
    }
}
