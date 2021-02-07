package com.xiaohuashifu.recruit.registration.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 描述：面试评价数据传输对象
 *
 * @author xhsf
 * @create 2021/1/4 16:43
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class InterviewEvaluationDTO implements Serializable {

    /**
     * 面试评价编号
     */
    private Long id;

    /**
     * 面试表编号
     */
    private Long interviewFormId;

    /**
     * 面试官编号
     */
    private Long interviewer;

    /**
     * 评价
     */
    private String evaluation;

}
