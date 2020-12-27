package com.xiaohuashifu.recruit.user.service.do0;

import java.time.LocalDateTime;

/**
 * 描述：学院表映射对象
 *
 * @author: xhsf
 * @create: 2020/10/30 15:05
 */
public class CollegeDO {
    private Long id;

    private String collegeName;

    private Boolean deactivated;

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

    public Boolean getDeactivated() {
        return deactivated;
    }

    public void setDeactivated(Boolean deactivated) {
        this.deactivated = deactivated;
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
        return "CollegeDO{" +
                "id=" + id +
                ", collegeName='" + collegeName + '\'' +
                ", deactivated=" + deactivated +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

    public static final class Builder {
        private Long id;
        private String collegeName;
        private Boolean deactivated;
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

        public Builder deactivated(Boolean deactivated) {
            this.deactivated = deactivated;
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

        public CollegeDO build() {
            CollegeDO collegeDO = new CollegeDO();
            collegeDO.setId(id);
            collegeDO.setCollegeName(collegeName);
            collegeDO.setDeactivated(deactivated);
            collegeDO.setCreateTime(createTime);
            collegeDO.setUpdateTime(updateTime);
            return collegeDO;
        }
    }
}
