package com.xiaohuashifu.recruit.external.api.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 描述：对象信息
 *
 * @author xhsf
 * @create 2020/12/7 20:07
 */
public class ObjectInfoDTO implements Serializable {

    /**
     * 对象名，如 users/avatars/1321.jpg
     */
    private String objectName;

    /**
     * 对象尺寸，单位字节
     */
    private Long size;

    /**
     * 对象更新时间
     */
    private LocalDateTime updateTime;

    public ObjectInfoDTO() {}

    public ObjectInfoDTO(String objectName, Long size, LocalDateTime updateTime) {
        this.objectName = objectName;
        this.size = size;
        this.updateTime = updateTime;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "ObjectInfo{" +
                "objectName='" + objectName + '\'' +
                ", size=" + size +
                ", updateTime=" + updateTime +
                '}';
    }


    public static final class Builder {
        private String objectName;
        private Long size;
        private LocalDateTime updateTime;

        public Builder objectName(String objectName) {
            this.objectName = objectName;
            return this;
        }

        public Builder size(Long size) {
            this.size = size;
            return this;
        }

        public Builder updateTime(LocalDateTime updateTime) {
            this.updateTime = updateTime;
            return this;
        }

        public ObjectInfoDTO build() {
            ObjectInfoDTO objectInfo = new ObjectInfoDTO();
            objectInfo.setObjectName(objectName);
            objectInfo.setSize(size);
            objectInfo.setUpdateTime(updateTime);
            return objectInfo;
        }
    }

}
