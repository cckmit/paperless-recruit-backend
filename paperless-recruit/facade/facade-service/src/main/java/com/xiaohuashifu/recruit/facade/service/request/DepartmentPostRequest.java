package com.xiaohuashifu.recruit.facade.service.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 描述：DepartmentPostRequest
 *
 * @author xhsf
 * @create 2021/1/31 01:27
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class DepartmentPostRequest {

    @ApiModelProperty(value = "部门名", required = true, example = "自然科学部")
    private String departmentName;

    @ApiModelProperty(value = "部门名缩写", required = true, example = "自科部")
    private String abbreviationDepartmentName;

}
