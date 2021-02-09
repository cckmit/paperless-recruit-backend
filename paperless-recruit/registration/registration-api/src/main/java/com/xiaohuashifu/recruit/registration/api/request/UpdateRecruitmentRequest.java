package com.xiaohuashifu.recruit.registration.api.request;

import com.xiaohuashifu.recruit.common.validator.annotation.NotAllCharactersBlank;
import com.xiaohuashifu.recruit.registration.api.constant.RecruitmentConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 描述：更新招新的请求
 *
 * @author xhsf
 * @create 2021/2/8 17:17
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRecruitmentRequest implements Serializable {

    /**
     * 招新编号
     */
    @NotNull
    @Positive
    private Long id;

    /**
     * 招新职位名
     */
    @NotAllCharactersBlank
    @Size(max = RecruitmentConstants.MAX_POSITION_NAME_LENGTH)
    private String positionName;

    /**
     * 招新人数
     */
    @NotAllCharactersBlank
    @Size(max = RecruitmentConstants.MAX_RECRUITMENT_NUMBERS_LENGTH)
    private String recruitmentNumbers;

    /**
     * 职位职责
     */
    @NotAllCharactersBlank
    @Size(max = RecruitmentConstants.MAX_POSITION_DUTY_LENGTH)
    private String positionDuty;

    /**
     * 职位要求
     */
    @NotAllCharactersBlank
    @Size(max = RecruitmentConstants.MAX_POSITION_REQUIREMENT_LENGTH)
    private String positionRequirement;

}
