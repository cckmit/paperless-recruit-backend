package com.xiaohuashifu.recruit.authentication.api.query;

import com.xiaohuashifu.recruit.common.constant.QueryConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;

/**
 * 描述：被允许路径查询参数
 *
 * @author xhsf
 * @create 2020/11/27 17:30
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PermittedUrlQuery implements Serializable {

    /**
     * 页码
     */
    @NotNull
    @Positive
    private Long pageNum;

    /**
     * 页条数
     */
    @NotNull
    @Positive
    @Max(value = QueryConstants.MAX_PAGE_SIZE)
    private Long pageSize;

    /**
     * 被允许路径，可右模糊
     */
    private String url;

}
