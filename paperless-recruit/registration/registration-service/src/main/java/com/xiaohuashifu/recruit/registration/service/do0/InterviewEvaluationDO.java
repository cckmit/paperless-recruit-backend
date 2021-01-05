package com.xiaohuashifu.recruit.registration.service.do0;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 描述：面试评价数据对象
 *
 * @author xhsf
 * @create 2021/1/4 16:43
 */
@Builder
@Data
public class InterviewEvaluationDO {

    /**
     * 面试评价编号
     */
    private Long id;

    /**
     * 面试表编号
     */
    private Long interviewFormId;

    /**
     * 面试编号
     */
    private Long interviewerId;

    /**
     * 评价
     */
    private String evaluation;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;

}
