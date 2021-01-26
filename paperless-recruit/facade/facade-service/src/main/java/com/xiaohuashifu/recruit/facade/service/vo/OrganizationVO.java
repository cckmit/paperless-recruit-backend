package com.xiaohuashifu.recruit.facade.service.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * 描述：组织 VO
 *
 * @author xhsf
 * @create 2021/1/9 14:55
 */
@ApiModel
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationVO {

    /**
     * 组织编号
     */
    @ApiModelProperty(value = "组织编号", example = "123")
    private Long id;

    /**
     * 组织名
     */
    @ApiModelProperty(value = "组织名", example = "华南农业大学学生科技创新与创业联合会")
    private String organizationName;

    /**
     * 组织名缩写
     */
    @ApiModelProperty(value = "组织名缩写", example = "校科联")
    private String abbreviationOrganizationName;

    /**
     * 组织介绍
     */
    @ApiModelProperty(value = "组织介绍", example = "华南农业大学学生科技创新与创业联合会")
    private String introduction;

    /**
     * 组织 logo
     */
    @ApiModelProperty(value = "组织 logo",
            example = "https://oss.xiaohuashifu.top/organization/logo/dc098266-814f-450e-aa98-837810833d7b1.jpg")
    private String logoUrl;

    /**
     * 组织成员数
     */
    @ApiModelProperty(value = "组织成员数", example = "321")
    private Integer memberNumber;

    /**
     * 组织部门数
     */
    @ApiModelProperty(value = "组织部门数", example = "13")
    private Integer numberOfDepartments;

    /**
     * 组织标签
     */
    @ApiModelProperty(value = "组织标签", example = "科技, 竞赛")
    private Set<String> labels;

    /**
     * 组织是否有效
     */
    @ApiModelProperty(value = "组织是否有效", example = "true")
    private Boolean available;
}
