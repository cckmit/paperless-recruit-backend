package com.xiaohuashifu.recruit.facade.service.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 描述：部门标签
 *
 * @author xhsf
 * @create 2021/2/1 00:43
 */
@ApiModel
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentLabelVO {
    @ApiModelProperty(value = "部门标签编号", example = "123")
    private Long id;

    @ApiModelProperty(value = "标签名", example = "创新")
    private String labelName;

    @ApiModelProperty(value = "引用次数", example = "123")
    private Long referenceNumber;

    @ApiModelProperty(value = "是否可用", example = "true")
    private Boolean available;
}
