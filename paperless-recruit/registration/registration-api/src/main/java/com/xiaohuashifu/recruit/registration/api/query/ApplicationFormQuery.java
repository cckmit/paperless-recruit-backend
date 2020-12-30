package com.xiaohuashifu.recruit.registration.api.query;

import com.xiaohuashifu.recruit.common.constant.QueryConstants;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;

/**
 * 描述：基础的查询
 *
 * @author xhsf
 * @create 2020/12/30 19:09
 */
public class ApplicationFormQuery {

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
    @Max(value = QueryConstants.MAX_PAGE_SIZE,
            message = "The pageSize must be less than or equal to " + QueryConstants.MAX_PAGE_SIZE + ".")
    private Long pageSize;

}
