package com.xiaohuashifu.recruit.registration.service.do0;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 描述：面试官数据对象
 *
 * @author xhsf
 * @create 2021/1/4 20:44
 */
@Data
@Builder
public class InterviewerDO {
    /**
     * 面试官编号
     */
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
    private Boolean available;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
