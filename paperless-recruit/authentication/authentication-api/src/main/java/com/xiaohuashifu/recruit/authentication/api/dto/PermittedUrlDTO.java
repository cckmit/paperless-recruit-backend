package com.xiaohuashifu.recruit.authentication.api.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 描述：被允许路径传输对象
 *
 * @author xhsf
 * @create 2020/11/27 17:27
 */
public class PermittedUrlDTO implements Serializable {
    private Long id;

    private String url;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
        return "PermittedUrlDTO{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

    public static final class Builder {
        private Long id;
        private String url;
        private LocalDateTime createTime;
        private LocalDateTime updateTime;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder url(String url) {
            this.url = url;
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

        public PermittedUrlDTO build() {
            PermittedUrlDTO permittedUrlDTO = new PermittedUrlDTO();
            permittedUrlDTO.setId(id);
            permittedUrlDTO.setUrl(url);
            permittedUrlDTO.setCreateTime(createTime);
            permittedUrlDTO.setUpdateTime(updateTime);
            return permittedUrlDTO;
        }
    }
}
