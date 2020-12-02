package com.xiaohuashifu.recruit.user.api.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 描述：专业传输对象
 *
 * @author: xhsf
 * @create: 2020/10/30 15:05
 */
public class MajorDTO implements Serializable {
    private Long id;

    private Long collegeId;

    private String majorName;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

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
        return "MajorDTO{" +
                "id=" + id +
                ", collegeId=" + collegeId +
                ", majorName='" + majorName + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

    public static final class Builder {
        private Long id;
        private Long collegeId;
        private String majorName;
        private LocalDateTime createTime;
        private LocalDateTime updateTime;

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

        public Builder createTime(LocalDateTime createTime) {
            this.createTime = createTime;
            return this;
        }

        public Builder updateTime(LocalDateTime updateTime) {
            this.updateTime = updateTime;
            return this;
        }

        public MajorDTO build() {
            MajorDTO majorDTO = new MajorDTO();
            majorDTO.setId(id);
            majorDTO.setCollegeId(collegeId);
            majorDTO.setMajorName(majorName);
            majorDTO.setCreateTime(createTime);
            majorDTO.setUpdateTime(updateTime);
            return majorDTO;
        }
    }
}
