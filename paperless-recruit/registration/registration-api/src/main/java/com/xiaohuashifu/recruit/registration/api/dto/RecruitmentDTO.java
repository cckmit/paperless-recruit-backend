package com.xiaohuashifu.recruit.registration.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

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
     * 招新部门编号列表
     */
    private Set<Long> recruitmentDepartmentIds;

    /**
     * 职位名
     */
    private String positionName;

    /**
     * 招新人数
     */
    private String recruitmentNumbers;

    /**
     * 职位职责
     */
    private String positionDuty;

    /**
     * 职位要求
     */
    private String positionRequirement;

    /**
     * @see com.xiaohuashifu.recruit.common.constant.GradeEnum
     * 招新年级
     */
    private Set<String> recruitmentGrades;

    /**
     * 招新学院编号列表
     */
    private Set<Long> recruitmentCollegeIds;

    /**
     * 招新专业编号列表
     */
    private Set<Long> recruitmentMajorIds;

    /**
     * 发布时间
     */
    private LocalDateTime releaseTime;

    /**
     * 报名开始时间
     */
    private LocalDateTime registrationTimeFrom;

    /**
     * 报名结束时间
     */
    private LocalDateTime registrationTimeTo;

    /**
     * @see com.xiaohuashifu.recruit.registration.api.constant.RecruitmentStatusEnum
     * 招新状态
     */
    private String recruitmentStatus;

    /**
     * 招新是否可用
     */
    private Boolean available;
}
