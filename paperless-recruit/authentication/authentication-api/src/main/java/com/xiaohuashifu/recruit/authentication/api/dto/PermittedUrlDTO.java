package com.xiaohuashifu.recruit.authentication.api.dto;

import java.io.Serializable;

/**
 * 描述：被允许路径传输对象
 *
 * @author xhsf
 * @create 2020/11/27 17:27
 */
public class PermittedUrlDTO implements Serializable {

    /**
     * 被允许路径编号
     */
    private Long id;

    /**
     * 被允许路径
     */
    private String url;

    public PermittedUrlDTO(Long id, String url) {
        this.id = id;
        this.url = url;
    }

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

    @Override
    public String toString() {
        return "PermittedUrlDTO{" +
                "id=" + id +
                ", url='" + url + '\'' +
                '}';
    }

}
