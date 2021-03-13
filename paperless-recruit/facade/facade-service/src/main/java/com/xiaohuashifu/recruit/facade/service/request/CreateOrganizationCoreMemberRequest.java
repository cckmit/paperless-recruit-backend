package com.xiaohuashifu.recruit.facade.service.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 描述：CreateOrganizationCoreMemberRequest
 *
 * @author xhsf
 * @create 2021/3/13 17:12
 */
@Data
@ApiModel
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrganizationCoreMemberRequest {

    @ApiModelProperty(value = "成员名", required = true, example = "李大花")
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
