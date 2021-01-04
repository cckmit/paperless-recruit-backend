package com.xiaohuashifu.recruit.registration.service.do0;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 描述：面试数据对象
 *
 * @author xhsf
 * @create 2021/1/4 16:43
 */
@Builder
@Data
public class InterviewDO {

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

    private LocalDateTime createTime;
    private LocalDateTime updateTime;

}
