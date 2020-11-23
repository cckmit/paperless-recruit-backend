package com.xiaohuashifu.recruit.user.api.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 描述：学院对象
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/10/30 15:05
 */
public class CollegeDTO implements Serializable {
    private Long id;

    private String collegeName;

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
        return "CollegeDTO{" +
                "id=" + id +
                ", collegeName='" + collegeName + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

    public static final class Builder {
        private Long id;
        private String collegeName;
        private LocalDateTime createTime;
        private LocalDateTime updateTime;

        public Builder() {
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder collegeName(String collegeName) {
            this.collegeName = collegeName;
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

        public CollegeDTO build() {
            CollegeDTO collegeDTO = new CollegeDTO();
            collegeDTO.setId(id);
            collegeDTO.setCollegeName(collegeName);
            collegeDTO.setCreateTime(createTime);
            collegeDTO.setUpdateTime(updateTime);
            return collegeDTO;
        }
    }
}
