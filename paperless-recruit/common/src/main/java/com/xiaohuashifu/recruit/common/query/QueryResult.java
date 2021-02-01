package com.xiaohuashifu.recruit.common.query;

import java.io.Serializable;
import java.util.Collection;

/**
 * 描述：查询结果
 *
 * @author xhsf
 * @create 2021/1/28 23:40
 */
public class QueryResult<T> implements Serializable {

    /**
     * 该查询条件下的匹配记录总数
     */
    private Long totalCount;

    /**
     * 查询结果
     */
    private Collection<T> result;

    public QueryResult() {}

    public QueryResult(Long totalCount, Collection<T> result) {
        this.totalCount = totalCount;
        this.result = result;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public Collection<T> getResult() {
        return result;
    }

    public void setResult(Collection<T> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "QueryResult{" +
                "totalCount=" + totalCount +
                ", result=" + result +
                '}';
    }
}
