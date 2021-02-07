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
 * 描述：面试表数据对象
 *
 * @author xhsf
 * @create 2021/1/4 16:43
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@TableName("interview_form")
public class InterviewFormDO {

    /**
     * 面试表编号
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 面试编号
     */
    private Long interviewId;

    /**
     * 报名表编号
     */
    private Long applicationFormId;

    /**
     * 面试时间
     */
    private String interviewTime;

    /**
     * 面试地点
     */
    private String interviewLocation;

    /**
     * 备注
     */
    private String note;

    /**
     * 面试状态
     */
    private String interviewStatus;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;

}
