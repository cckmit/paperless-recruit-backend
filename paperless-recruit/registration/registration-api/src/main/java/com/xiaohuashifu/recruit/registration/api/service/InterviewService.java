package com.xiaohuashifu.recruit.registration.api.service;

import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.registration.api.constant.InterviewConstants;
import com.xiaohuashifu.recruit.registration.api.dto.InterviewDTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

/**
 * 描述：面试服务
 *
 * @author xhsf
 * @create 2021/1/4 16:32
 */
public interface InterviewService {

    /**
     * 创建一个面试
     *
     * @permission 必须是招新所属组织所属用户主体
     *
     * @errorCode InvalidParameter: 参数格式错误
     *
     * @param recruitmentId 招新编号
     * @param title 面试标题
     * @return 面试对象
     */
    Result<InterviewDTO> createInterview(
            @NotNull(message = "The recruitmentId can't be null.")
            @Positive(message = "The recruitmentId must be greater than 0.") Long recruitmentId,
            @NotBlank(message = "The title can't be blank.")
            @Size(max = InterviewConstants.MAX_TITLE_LENGTH,
                    message = "The length of title must not be greater than "
                            + InterviewConstants.MAX_TITLE_LENGTH + ".") String title);

    /**
     * 更新面试标题
     *
     * @permission 必须是面试所属的主体
     *
     * @param id 面试编号
     * @param title 面试标题
     * @return 更新后的面试对象
     */
    Result<InterviewDTO> updateTitle(
            @NotNull(message = "The id can't be null.") @Positive(message = "The id must be greater than 0.") Long id,
            @NotBlank(message = "The title can't be blank.")
            @Size(max = InterviewConstants.MAX_TITLE_LENGTH,
                    message = "The length of title must not be greater than "
                            + InterviewConstants.MAX_TITLE_LENGTH + ".") String title);

    /**
     * 获取面试所属的招新
     *
     * @private 内部方法
     *
     * @param id 面试编号
     * @return 面试所属招新的编号，若找不到返回 null
     */
    Long getRecruitmentId(Long id);

    /**
     * 验证面试的主体
     *
     * @private 内部方法
     *
     * @param id 面试编号
     * @param userId 主体编号
     * @return 若是返回 true，不是返回 false
     */
    Boolean authenticatePrincipal(Long id, Long userId);

}
