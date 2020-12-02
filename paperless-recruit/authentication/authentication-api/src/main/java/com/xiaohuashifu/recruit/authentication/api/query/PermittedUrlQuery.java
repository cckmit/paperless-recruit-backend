package com.xiaohuashifu.recruit.authentication.api.query;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.List;

/**
 * 描述：被允许路径查询参数
 *
 * @author xhsf
 * @create 2020/11/27 17:30
 */
public class PermittedUrlQuery implements Serializable {
    @NotNull
    @Positive
    private Long pageNum;
    @NotNull
    @Positive
    private Long pageSize;
    private Long id;
    private List<Long> idList;
    private String url;

    public Long getPageNum() {
        return pageNum;
    }

    public void setPageNum(Long pageNum) {
        this.pageNum = pageNum;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Long> getIdList() {
        return idList;
    }

    public void setIdList(List<Long> idList) {
        this.idList = idList;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "PermittedUrlQuery{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", id=" + id +
                ", idList=" + idList +
                ", url='" + url + '\'' +
                '}';
    }

    public static final class Builder {
        private Long pageNum;
        private Long pageSize;
        private Long id;
        private List<Long> idList;
        private String url;

        public Builder pageNum(Long pageNum) {
            this.pageNum = pageNum;
            return this;
        }

        public Builder pageSize(Long pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder idList(List<Long> idList) {
            this.idList = idList;
            return this;
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public PermittedUrlQuery build() {
            PermittedUrlQuery permittedUrlQuery = new PermittedUrlQuery();
            permittedUrlQuery.setPageNum(pageNum);
            permittedUrlQuery.setPageSize(pageSize);
            permittedUrlQuery.setId(id);
            permittedUrlQuery.setIdList(idList);
            permittedUrlQuery.setUrl(url);
            return permittedUrlQuery;
        }
    }
}
