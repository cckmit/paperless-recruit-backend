package com.xiaohuashifu.recruit.facade.service.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 描述：招新
 *
 * @author xhsf
 * @create 2020/12/23 16:08
 */
@ApiModel
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecruitmentVO {

    /**
     * 招新编号
     */
    @ApiModelProperty(value = "招新编号", example = "123")
    private Long id;

    /**
     * 组织
     */
    @ApiModelProperty(value = "组织", example = "123")
    private OrganizationVO organization;

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
    @ApiModelProperty(value = "招新人数", example = "约30人")
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
     * @see com.xiaohuashifu.recruit.registration.api.constant.RecruitmentStatusEnum
     * 招新状态
     */
    @ApiModelProperty(value = "招新状态", example = "STARTED")
    private String recruitmentStatus;

    /**
     * 报名表数量
     */
    @ApiModelProperty(value = "报名表数量", example = "12")
    private Integer numberOfApplicationForms;

    /**
     * 招新开始时间
     */
    @ApiModelProperty(value = "招新开始时间", example = "2021-03-17 23:35:00")
    private LocalDateTime startTime;

    /**
     * 招新结束时间
     */
    @ApiModelProperty(value = "招新结束时间", example = "2021-03-17 23:35:00")
    private LocalDateTime endTime;

}
