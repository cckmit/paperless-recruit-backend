package com.xiaohuashifu.recruit.facade.service.request;

import com.xiaohuashifu.recruit.common.constant.QueryConstants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * 描述：组织标签查询请求
 *
 * @author xhsf
 * @create 2021/1/18 16:01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class OrganizationLabelQueryRequest {

    @NotNull
    @Positive
    @ApiModelProperty(value = "页码", required = true, example = "1")
    private Integer pageNum;

    @NotNull
    @Positive
    @Max(value = QueryConstants.MAX_PAGE_SIZE)
    @ApiModelProperty(value = "页条数", required = true, example = "10")
    private Integer pageSize;

    @ApiModelProperty(value = "标签名，可左模糊", example = "科技")
    private String labelName;

    @ApiModelProperty(value = "标签是否可用", example = "true")
    private Boolean available;

}
