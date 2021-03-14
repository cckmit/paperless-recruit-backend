package com.xiaohuashifu.recruit.registration.service.do0;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
    private String recruitmentName;
    private String position;
    private String numberOfRecruitments;
    private String duty;
    private String requirement;
    private String recruitmentStatus;
    private Integer numberOfApplicationForms;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
