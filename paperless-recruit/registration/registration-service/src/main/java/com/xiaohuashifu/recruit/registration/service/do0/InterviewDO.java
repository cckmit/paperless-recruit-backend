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
 * 描述：面试数据对象
 *
 * @author xhsf
 * @create 2021/1/4 16:43
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@TableName("interview")
public class InterviewDO {

    /**
     * 面试编号
     */
    @TableId(type = IdType.AUTO)
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
