package com.xiaohuashifu.recruit.facade.service.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 描述：UpdateRecruitmentRequest
 *
 * @author xhsf
 * @create 2020/12/23 21:23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class UpdateRecruitmentRequest {

    /**
     * 招新编号
     */
    @ApiModelProperty(value = "招新编号", required = true, example = "123")
    private Long id;

    /**
     * 招新名
     */
    @ApiModelProperty(value = "招新名", example = "校科联自科部招新")
    private String recruitmentName;

    /**
     * 职位名
     */
    @ApiModelProperty(value = "职位名", example = "干事")
    private String position;

    /**
     * 招新人数
     */
    @ApiModelProperty(value = "招新人数", example = "20人左右")
    private String numberOfRecruitments;

    /**
     * 职责
     */
    @ApiModelProperty(value = "职责", example = "xx")
    private String duty;

    /**
     * 要求
     */
    @ApiModelProperty(value = "要求", example = "xx")
    private String requirement;

    /**
     * 招新状态
     */
    @ApiModelProperty(value = "招新状态", example = "STARTED， ENDED")
    private String recruitmentStatus;

}
