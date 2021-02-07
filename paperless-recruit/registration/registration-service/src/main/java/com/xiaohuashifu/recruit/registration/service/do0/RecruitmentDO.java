package com.xiaohuashifu.recruit.registration.service.do0;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xiaohuashifu.recruit.common.mybatis.type.LongSetTypeHandler;
import com.xiaohuashifu.recruit.common.mybatis.type.StringSetTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@Builder
@TableName(value = "recruitment", autoResultMap = true)
public class RecruitmentDO {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long organizationId;
    @TableField(typeHandler = LongSetTypeHandler.class)
    private Set<Long> recruitmentDepartmentIds;
    private String positionName;
    private String recruitmentNumbers;
    private String positionDuty;
    private String positionRequirement;
    @TableField(typeHandler = StringSetTypeHandler.class)
    private Set<String> recruitmentGrades;
    @TableField(typeHandler = LongSetTypeHandler.class)
    private Set<Long> recruitmentCollegeIds;
    @TableField(typeHandler = LongSetTypeHandler.class)
    private Set<Long> recruitmentMajorIds;
    private LocalDateTime releaseTime;
    private LocalDateTime registrationTimeFrom;
    private LocalDateTime registrationTimeTo;
    private String recruitmentStatus;
    @TableField("is_available")
    private Boolean available;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
