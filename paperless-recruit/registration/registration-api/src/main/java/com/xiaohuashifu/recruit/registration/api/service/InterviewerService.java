package com.xiaohuashifu.recruit.registration.api.service;

import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.registration.api.dto.InterviewerDTO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * 描述：面试官服务
 *
 * @author xhsf
 * @create 2021/1/4 20:21
 */
public interface InterviewerService {

    /**
     * 保存面试官
     *
     * @permission 必须是组织的主体
     *
     * @param organizationId 组织编号
     * @param organizationMemberId 组织成员编号
     * @return 面试官对象
     */
    Result<InterviewerDTO> saveInterviewer(
            @NotNull(message = "The organizationId can't be null.")
            @Positive(message = "The organizationId must be greater than 0.") Long organizationId,
            @NotNull(message = "The organizationMemberId can't be null.")
            @Positive(message = "The organizationMemberId must be greater than 0.") Long organizationMemberId);

    /**
     * 禁用面试官
     *
     * @permission 必须是面试官的主体
     *
     * @param interviewerId 面试官编号
     * @return 禁用后的面试官对象
     */
    Result<InterviewerDTO> disableInterviewer(
            @NotNull(message = "The interviewerId can't be null.")
            @Positive(message = "The interviewerId must be greater than 0.") Long interviewerId);

    /**
     * 解禁面试官
     *
     * @permission 必须是面试官的主体
     *
     * @param interviewerId 面试官编号
     * @return 解禁后的面试官对象
     */
    Result<InterviewerDTO> enableInterviewer(
            @NotNull(message = "The interviewerId can't be null.")
            @Positive(message = "The interviewerId must be greater than 0.") Long interviewerId);

    /**
     * 获取组织编号
     *
     * @private 内部方法
     *
     * @param id 面试官编号
     * @return 组织编号，若找不到返回 null
     */
    Long getOrganizationId(Long id);

    /**
     * 验证面试官的主体，也就是组织的主体
     *
     * @private 内部方法
     *
     * @param id 面试官编号
     * @param userId 主体编号
     * @return 若是返回 true，不是返回 false
     */
    Boolean authenticatePrincipal(Long id, Long userId);

}