package com.xiaohuashifu.recruit.user.api.query;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.List;

/**
 * 描述：专业查询参数
 *
 * @author: xhsf
 * @create: 2020/10/29 23:48
 */
public class MajorQuery implements Serializable {

    @NotNull
    @Positive
    private Long pageNum;
    @NotNull
    @Positive
    @Max(50)
    private Long pageSize;
    private Long id;
    private List<Long> ids;
    private Long collegeId;
    private String majorName;

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

    @Override
    public String toString() {
        return "MajorQuery{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", id=" + id +
                ", ids=" + ids +
                ", collegeId=" + collegeId +
                ", majorName='" + majorName + '\'' +
                '}';
    }


    public static final class Builder {
        private Long pageNum;
        private Long pageSize;
        private Long id;
        private List<Long> ids;
        private Long collegeId;
        private String majorName;

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

        public Builder ids(List<Long> ids) {
            this.ids = ids;
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

        public MajorQuery build() {
            MajorQuery majorQuery = new MajorQuery();
            majorQuery.setPageNum(pageNum);
            majorQuery.setPageSize(pageSize);
            majorQuery.setId(id);
            majorQuery.setIds(ids);
            majorQuery.setCollegeId(collegeId);
            majorQuery.setMajorName(majorName);
            return majorQuery;
        }
    }
}
