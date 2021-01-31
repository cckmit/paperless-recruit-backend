package com.xiaohuashifu.recruit.facade.service.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 描述：CreateOrganizationLabelRequest
 *
 * @author xhsf
 * @create 2021/2/1 00:40
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class CreateOrganizationLabelRequest {
    @ApiModelProperty(value = "标签名", required = true, example = "创新")
    private String labelName;
}
