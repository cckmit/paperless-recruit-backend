package com.xiaohuashifu.recruit.registration.service.do0;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 描述：面试官数据对象
 *
 * @author xhsf
 * @create 2021/1/4 20:44
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@TableName("interviewer")
public class InterviewerDO {
    /**
     * 面试官编号
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 组织编号
     */
    private Long organizationId;

    /**
     * 组织成员编号
     */
    private Long organizationMemberId;

    /**
     * 是否可用
     */
    @TableField("is_available")
    private Boolean available;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
