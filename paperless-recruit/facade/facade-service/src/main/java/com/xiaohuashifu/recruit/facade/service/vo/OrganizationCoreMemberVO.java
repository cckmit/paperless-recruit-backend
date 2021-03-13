package com.xiaohuashifu.recruit.facade.service.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 描述：组织核心成员
 *
 * @author xhsf
 * @create 2020/12/15 16:26
 */
@ApiModel
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationCoreMemberVO {

    @ApiModelProperty(value = "核心成员编号", example = "123")
    private Long id;

    @ApiModelProperty(value = "组织编号", example = "123")
    private Long organizationId;

    @ApiModelProperty(value = "成员名", example = "李大花")
    private String memberName;

    @ApiModelProperty(value = "职位", example = "部长")
    private String position;

    @ApiModelProperty(value = "头像", example = "https://oss.xiaohuashifu.top/" +
            "organizations/core-members/avartars/b54a4e2d50f24dd891b92c2519c6492a社科部科科.jpg")
    private String avatarUrl;

    @ApiModelProperty(value = "介绍", example = "社科部部长")
    private String introduction;

    @ApiModelProperty(value = "职位优先级", example = "0")
    private Integer priority;

}
