package com.xiaohuashifu.recruit.registration.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 描述：招新数据传输对象
 *
 * @author xhsf
 * @create 2020/12/23 16:08
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RecruitmentDTO implements Serializable {

    /**
     * 招新编号
     */
    private Long id;

    /**
     * 组织编号
     */
    private Long organizationId;

    /**
     * 招新名
     */
    private String recruitmentName;

    /**
     * 职位名
     */
    private String position;

    /**
     * 招新人数
     */
    private String numberOfRecruitments;

    /**
     * 职责
     */
    private String duty;

    /**
     * 要求
     */
    private String requirement;

    /**
     * @see com.xiaohuashifu.recruit.registration.api.constant.RecruitmentStatusEnum
     * 招新状态
     */
    private String recruitmentStatus;

    /**
     * 报名表数量
     */
    private Integer numberOfApplicationForms;

}
