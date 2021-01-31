package com.xiaohuashifu.recruit.facade.service.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.AssertTrue;

/**
 * 描述：DepartmentPatchRequest
 *
 * @author xhsf
 * @create 2021/1/27 17:30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class DepartmentPatchRequest {

    @ApiModelProperty(value = "部门名", example = "自然科学部")
    private String departmentName;

    @ApiModelProperty(value = "部门名缩写", example = "自科部")
    private String abbreviationDepartmentName;

    @ApiModelProperty(value = "部门介绍", example = "自然科学部是一个。。。")
    private String introduction;

    @ApiModelProperty(value = "部门 logoUrl",
            example = "departments/logos/738d8e7485d3455da22765e199cc65721601000702(1).jpg")
    private String logoUrl;

    @ApiModelProperty(value = "是否停用部门。只允许true", example = "true")
    @AssertTrue
    private Boolean deactivated;

}
