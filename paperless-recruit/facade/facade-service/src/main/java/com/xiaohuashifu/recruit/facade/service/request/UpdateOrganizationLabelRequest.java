package com.xiaohuashifu.recruit.facade.service.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 描述：UpdateOrganizationLabelRequest
 *
 * @author xhsf
 * @create 2021/2/1 00:40
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class UpdateOrganizationLabelRequest {
    @ApiModelProperty(value = "组织标签是否可用", example = "true")
    private Boolean available;
}
