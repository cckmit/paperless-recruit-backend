package com.xiaohuashifu.recruit.registration.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 描述：面试表数据传输对象
 *
 * @author xhsf
 * @create 2021/1/4 16:43
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class InterviewFormDTO implements Serializable {

    /**
     * 面试表编号
     */
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

}
