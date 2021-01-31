package com.xiaohuashifu.recruit.facade.service.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 描述：DepartmentLabelPostRequest
 *
 * @author xhsf
 * @create 2021/1/31 19:16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class DepartmentLabelPostRequest {
    @ApiModelProperty(value = "标签", required = true, example = "创新")
    private String label;
}
