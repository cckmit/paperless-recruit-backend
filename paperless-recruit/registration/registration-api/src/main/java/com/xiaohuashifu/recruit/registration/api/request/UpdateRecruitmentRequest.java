package com.xiaohuashifu.recruit.registration.api.request;

import com.xiaohuashifu.recruit.common.validator.annotation.NotAllCharactersBlank;
import com.xiaohuashifu.recruit.registration.api.constant.RecruitmentConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
     * 招新名
     */
    @NotAllCharactersBlank
    @Size(max = RecruitmentConstants.MAX_RECRUITMENT_NAME_LENGTH)
    private String recruitmentName;

    /**
     * 职位名
     */
    @NotAllCharactersBlank
    @Size(max = RecruitmentConstants.MAX_POSITION_LENGTH)
    private String position;

    /**
     * 招新人数
     */
    @NotAllCharactersBlank
    @Size(max = RecruitmentConstants.MAX_NUMBER_OF_RECRUITMENTS_LENGTH)
    private String numberOfRecruitments;

    /**
     * 职责
     */
    @NotAllCharactersBlank
    @Size(max = RecruitmentConstants.MAX_DUTY_LENGTH)
    private String duty;

    /**
     * 要求
     */
    @NotAllCharactersBlank
    @Size(max = RecruitmentConstants.MAX_REQUIREMENT_LENGTH)
    private String requirement;

    /**
     * 招新状态
     */
    @NotAllCharactersBlank
    private String recruitmentStatus;

}
