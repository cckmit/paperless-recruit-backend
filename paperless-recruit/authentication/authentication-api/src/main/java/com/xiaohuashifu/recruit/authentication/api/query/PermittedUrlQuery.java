package com.xiaohuashifu.recruit.authentication.api.query;

import com.xiaohuashifu.recruit.common.constant.QueryConstants;

import javax.validation.constraints.Max;
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

    /**
     * 页码
     */
    @NotNull(message = "The pageNum can't be null.")
    @Positive(message = "The pageNum must be greater than 0.")
    private Long pageNum;

    /**
     * 页条数
     */
    @NotNull(message = "The pageSize can't be null.")
    @Positive(message = "The pageSize must be greater than 0.")
    @Max(value = QueryConstants.DEFAULT_PAGE_SIZE,
            message = "The pageSize must be less than or equal to " + QueryConstants.DEFAULT_PAGE_SIZE + ".")
    private Long pageSize;

    /**
     * 被允许路径编号
     */
    private Long id;

    /**
     * 被允许路径编号列表
     */
    private List<Long> ids;

    /**
     * 被允许路径，可模糊
     */
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

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
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
                ", ids=" + ids +
                ", url='" + url + '\'' +
                '}';
    }

    public static final class Builder {
        private Long pageNum;
        private Long pageSize;
        private Long id;
        private List<Long> ids;
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

        public Builder ids(List<Long> ids) {
            this.ids = ids;
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
            permittedUrlQuery.setIds(ids);
            permittedUrlQuery.setUrl(url);
            return permittedUrlQuery;
        }
    }

}
