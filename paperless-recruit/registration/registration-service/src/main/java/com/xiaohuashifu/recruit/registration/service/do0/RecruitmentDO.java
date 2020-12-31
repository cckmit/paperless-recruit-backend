package com.xiaohuashifu.recruit.registration.service.do0;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * 描述：招新数据对象
 *
 * @author xhsf
 * @create 2020/12/23 16:08
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
public class RecruitmentDO {
    private Long id;
    private Long organizationId;
    private Set<Long> recruitmentDepartmentIds;
    private String positionName;
    private String recruitmentNumbers;
    private String positionDuty;
    private String positionRequirement;
    private Set<String> recruitmentGrades;
    private Set<Long> recruitmentCollegeIds;
    private Set<Long> recruitmentMajorIds;
    private LocalDateTime releaseTime;
    private LocalDateTime registrationTimeFrom;
    private LocalDateTime registrationTimeTo;
    private String recruitmentStatus;
    private Boolean available;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
