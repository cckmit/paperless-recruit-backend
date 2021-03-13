package com.xiaohuashifu.recruit.facade.service.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * 描述：CreateDepartmentRequest
 *
 * @author xhsf
 * @create 2021/1/31 01:27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class CreateDepartmentRequest {

    @ApiModelProperty(value = "部门名", required = true, example = "自然科学部")
    private String departmentName;

    @ApiModelProperty(value = "部门介绍", required = true,
            example = "1、参加科研项目和科技比赛，加强对成员的技术培训，培养和提高自身的科研力量和技术支持，拥有自身的科研成果")
    private String introduction;

    @ApiModelProperty(value = "部门 logo", required = true, example = "departments/logos/738d8e7485d3455da22765e199cc65721601000702(1).jpg")
    private String logoUrl;

    @ApiModelProperty(value = "部门类型", required = true, example = "技术")
    private String departmentType;

    @ApiModelProperty(value = "部门规模", required = true, example = "20人")
    private String size;

    @ApiModelProperty(value = "部门标签", required = true, example = "软件, 硬件")
    private Set<String> labels;

}
