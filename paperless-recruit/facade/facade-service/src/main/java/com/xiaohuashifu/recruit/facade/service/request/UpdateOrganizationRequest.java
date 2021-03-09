package com.xiaohuashifu.recruit.facade.service.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * 描述：UpdateOrganizationRequest
 *
 * @author xhsf
 * @create 2021/1/27 17:30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class UpdateOrganizationRequest {
    /**
     * 组织名
     */
    @ApiModelProperty(value = "组织名", example = "华南农业大学学生科技创新与创业联合会")
    private String organizationName;

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

    /**
     * 组织类型
     */
    @ApiModelProperty(value = "组织类型", example = "综合")
    private String organizationType;

    /**
     * 组织规模
     */
    @ApiModelProperty(value = "组织规模", example = "100-200人")
    private String size;

    /**
     * 组织地址
     */
    @ApiModelProperty(value = "组织地址", example = "创客空间")
    private String address;

    /**
     * 组织网址
     */
    @ApiModelProperty(value = "组织网址", example = "www.xiaohuashifu.top")
    private String website;

    /**
     * 组织标签
     */
    @ApiModelProperty(value = "组织标签", example = "创新,创业")
    private Set<String> labels;
}
