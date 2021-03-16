package com.xiaohuashifu.recruit.facade.service.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 描述：CreateRecruitmentRequest
 *
 * @author xhsf
 * @create 2020/12/23 21:23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class CreateRecruitmentRequest {

    /**
     * 招新名
     */
    @ApiModelProperty(value = "招新名", required = true, example = "校科联自科部招新")
    private String recruitmentName;

    /**
     * 职位名
     */
    @ApiModelProperty(value = "职位名", required = true, example = "干事")
    private String position;

    /**
     * 招新人数
     */
    @ApiModelProperty(value = "招新人数", required = true, example = "20人左右")
    private String numberOfRecruitments;

    /**
     * 职责
     */
    @ApiModelProperty(value = "职责", required = true, example = "xx")
    private String duty;

    /**
     * 要求
     */
    @ApiModelProperty(value = "要求", required = true, example = "xx")
    private String requirement;

}
