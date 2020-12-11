package com.xiaohuashifu.recruit.authentication.service.do0;

import java.time.LocalDateTime;

/**
 * 描述：
 *
 * @author xhsf
 * @create 2020/11/27 17:27
 */
public class PermittedUrlDO {

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
        return "PermittedUrlDO{" +
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

        public PermittedUrlDO build() {
            PermittedUrlDO permittedUrlDO = new PermittedUrlDO();
            permittedUrlDO.setId(id);
            permittedUrlDO.setUrl(url);
            permittedUrlDO.setCreateTime(createTime);
            permittedUrlDO.setUpdateTime(updateTime);
            return permittedUrlDO;
        }
    }

}
