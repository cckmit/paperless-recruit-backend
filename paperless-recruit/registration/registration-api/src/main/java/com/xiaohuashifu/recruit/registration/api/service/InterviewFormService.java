package com.xiaohuashifu.recruit.registration.api.service;

import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.registration.api.constant.InterviewFormConstants;
import com.xiaohuashifu.recruit.registration.api.constant.InterviewStatusEnum;
import com.xiaohuashifu.recruit.registration.api.dto.InterviewFormDTO;
import com.xiaohuashifu.recruit.registration.api.po.SaveInterviewFormPO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

/**
 * 描述：面试表服务
 *
 * @author xhsf
 * @create 2021/1/4 16:47
 */
public interface InterviewFormService {

    /**
     * 保存面试表
     *
     * @permission 必须是所属面试的主体
     *
     * @param saveInterviewFormPO 保存面试表的参数对象
     * @return 创建的面试表
     */
    Result<InterviewFormDTO> saveInterviewForm(@NotNull(message = "The saveInterviewFormPO can't be null.")
                                                       SaveInterviewFormPO saveInterviewFormPO);

    /**
     * 更新面试时间
     *
     * @permission 必须是面试表的主体
     *
     * @param id 面试表编号
     * @param interviewTime 面试时间
     * @return 更新后的面试表
     */
    Result<InterviewFormDTO> updateInterviewTime(
            @NotNull(message = "The id can't be null.") @Positive(message = "The id must be greater than 0.") Long id,
            @NotBlank(message = "The interviewTime can't be blank.")
            @Size(max = InterviewFormConstants.MAX_INTERVIEW_TIME_LENGTH,
                    message = "The length of interviewTime must not be greater than "
                            + InterviewFormConstants.MAX_INTERVIEW_TIME_LENGTH + ".") String interviewTime);

    /**
     * 更新面试地点
     *
     * @permission 必须是面试表的主体
     *
     * @param id 面试表编号
     * @param interviewLocation 面试地点
     * @return 更新后的面试表
     */
    Result<InterviewFormDTO> updateInterviewLocation(
            @NotNull(message = "The id can't be null.") @Positive(message = "The id must be greater than 0.") Long id,
            @NotBlank(message = "The interviewLocation can't be blank.")
            @Size(max = InterviewFormConstants.MAX_INTERVIEW_LOCATION_LENGTH,
                    message = "The length of interviewLocation must not be greater than "
                            + InterviewFormConstants.MAX_INTERVIEW_LOCATION_LENGTH + ".") String interviewLocation);

    /**
     * 更新备注
     *
     * @permission 必须是面试表的主体
     *
     * @param id 面试表编号
     * @param note 备注
     * @return 更新后的面试表
     */
    Result<InterviewFormDTO> updateNote(
            @NotNull(message = "The id can't be null.") @Positive(message = "The id must be greater than 0.") Long id,
            @NotNull(message = "The note can't be null.")
            @Size(max = InterviewFormConstants.MAX_NOTE_LENGTH,
                    message = "The length of note must not be greater than "
                            + InterviewFormConstants.MAX_NOTE_LENGTH + ".") String note);

    /**
     * 更新面试状态
     *
     * @permission 必须是面试表的主体
     *
     * @param id 面试表编号
     * @param oldInterviewStatus 旧面试状态
     * @param newInterviewStatus 新面试状态
     * @return 更新后的面试表
     */
    Result<InterviewFormDTO> updateInterviewStatus(
            @NotNull(message = "The id can't be null.") @Positive(message = "The id must be greater than 0.") Long id,
            @NotNull(message = "The oldInterviewStatus can't be null.") InterviewStatusEnum oldInterviewStatus,
            @NotNull(message = "The newInterviewStatus can't be null.") InterviewStatusEnum newInterviewStatus);

    /**
     * 验证面试表的主体
     *
     * @private 内部方法
     *
     * @param id 面试表编号
     * @param userId 主体编号
     * @return 若是返回 true，不是返回 false
     */
    Boolean authenticatePrincipal(Long id, Long userId);
}