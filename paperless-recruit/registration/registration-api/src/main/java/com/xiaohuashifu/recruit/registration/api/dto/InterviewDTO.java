package com.xiaohuashifu.recruit.registration.api.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 描述：面试数据传输对象
 *
 * @author xhsf
 * @create 2021/1/4 16:43
 */
@Builder
@Data
public class InterviewDTO implements Serializable {

    /**
     * 面试编号
     */
    private Long id;

    /**
     * 招新编号
     */
    private Long recruitmentId;

    /**
     * 第几轮
     */
    private Integer round;

    /**
     * 面试标题
     */
    private String title;

}
