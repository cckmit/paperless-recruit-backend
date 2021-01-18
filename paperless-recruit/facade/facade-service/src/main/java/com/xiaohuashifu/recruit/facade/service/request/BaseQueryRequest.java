package com.xiaohuashifu.recruit.facade.service.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 描述：基本查询请求
 *
 * @author xhsf
 * @create 2021/1/18 16:01
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class BaseQueryRequest {

    /**
     * 最小页码
     */
    public static final int MIN_PAGE_NUM = 1;

    /**
     * 最小页条数
     */
    public static final int MIN_PAGE_SIZE = 1;

    /**
     * 最大页条数
     */
    public static final int MAX_PAGE_SIZE = 50;

    /**
     * 页码
     */
    @Min(value = MIN_PAGE_NUM)
    @ApiModelProperty(value = "页码", required = true, example = "1", allowableValues = "pageNum >= " + MIN_PAGE_NUM)
    private Integer pageNum;

    /**
     * 页条数
     */
    @NotNull
    @Min(value = MIN_PAGE_SIZE)
    @Min(value = MAX_PAGE_SIZE)
    @ApiModelProperty(value = "页条数", required = true, example = "10",
            allowableValues = MIN_PAGE_SIZE + "-" + MAX_PAGE_SIZE)
    private Integer pageSize;

}
