package com.xiaohuashifu.recruit.registration.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 描述：面试官数据传输对象
 *
 * @author xhsf
 * @create 2021/1/4 16:43
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class InterviewerDTO implements Serializable {

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

}
