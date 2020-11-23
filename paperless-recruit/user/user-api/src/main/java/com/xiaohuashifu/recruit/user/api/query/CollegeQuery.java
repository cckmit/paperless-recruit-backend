package com.xiaohuashifu.recruit.user.api.query;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.List;

/**
 * 描述：
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/10/29 23:48
 */
public class CollegeQuery implements Serializable {

    @NotNull
    @Positive
    private Long pageNum = 1L;
    @NotNull
    @Positive
    private Long pageSize = 10L;
    private Long id;
    private List<Long> idList;
    private String collegeName;

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

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    @Override
    public String toString() {
        return "CollegeQuery{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", id=" + id +
                ", idList=" + idList +
                ", collegeName='" + collegeName + '\'' +
                '}';
    }


    public static final class Builder {
        private Long pageNum = 1L;
        private Long pageSize = 10L;
        private Long id;
        private List<Long> idList;
        private String collegeName;

        public Builder() {
        }

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

        public Builder collegeName(String collegeName) {
            this.collegeName = collegeName;
            return this;
        }

        public CollegeQuery build() {
            CollegeQuery collegeQuery = new CollegeQuery();
            collegeQuery.setPageNum(pageNum);
            collegeQuery.setPageSize(pageSize);
            collegeQuery.setId(id);
            collegeQuery.setIdList(idList);
            collegeQuery.setCollegeName(collegeName);
            return collegeQuery;
        }
    }
}
