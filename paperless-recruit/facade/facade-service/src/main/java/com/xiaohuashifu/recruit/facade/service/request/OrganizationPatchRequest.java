package com.xiaohuashifu.recruit.facade.service.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 描述：
 *
 * @author xhsf
 * @create 2021/1/27 17:30
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class OrganizationPatchRequest {
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
     * 组织 logoUrl
     */
    @ApiModelProperty(value = "组织 logoUrl",
            example = "organizations/logos/738d8e7485d3455da22765e199cc65721601000702(1).jpg")
    private String logoUrl;

//    /**
//     * 组织标签
//     */
//    @ApiModelProperty(value = "组织标签", example = "科技, 竞赛")
//    private Set<String> labels;

}
