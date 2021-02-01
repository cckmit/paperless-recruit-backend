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
    private Long total;

    /**
     * 查询结果
     */
    private Collection<T> result;

    public QueryResult() {}

    public QueryResult(Long total, Collection<T> result) {
        this.total = total;
        this.result = result;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
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
                "total=" + total +
                ", result=" + result +
                '}';
    }
}
