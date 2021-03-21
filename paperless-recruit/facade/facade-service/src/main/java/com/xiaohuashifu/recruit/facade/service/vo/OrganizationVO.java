package com.xiaohuashifu.recruit.facade.service.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
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
public class OrganizationVO implements Serializable {

    /**
     * 组织编号
     */
    @ApiModelProperty(value = "组织编号", example = "123")
    private Long id;

    /**
     * 用户编号
     */
    @ApiModelProperty(value = "用户编号", example = "123")
    private Long userId;

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
     * 组织 logo
     */
    @ApiModelProperty(value = "组织 logo",
            example = "https://oss.xiaohuashifu.top/organization/logo/dc098266-814f-450e-aa98-837810833d7b1.jpg")
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
