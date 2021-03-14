package com.xiaohuashifu.recruit.registration.api.request;

import com.xiaohuashifu.recruit.registration.api.constant.RecruitmentConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 描述：创建招新的参数对象
 *
 * @author xhsf
 * @create 2020/12/23 21:23
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
public class CreateRecruitmentRequest implements Serializable {

    /**
     * 招新的组织编号
     */
    @NotNull
    @Positive
    private Long organizationId;

    /**
     * 招新名
     */
    @NotBlank
    @Size(max = RecruitmentConstants.MAX_RECRUITMENT_NAME_LENGTH)
    private String recruitmentName;

    /**
     * 职位名
     */
    @NotBlank
    @Size(max = RecruitmentConstants.MAX_POSITION_LENGTH)
    private String position;

    /**
     * 招新人数
     */
    @NotBlank
    @Size(max = RecruitmentConstants.MAX_NUMBER_OF_RECRUITMENTS_LENGTH)
    private String numberOfRecruitments;

    /**
     * 职责
     */
    @NotBlank
    @Size(max = RecruitmentConstants.MAX_DUTY_LENGTH)
    private String duty;

    /**
     * 要求
     */
    @NotBlank
    @Size(max = RecruitmentConstants.MAX_REQUIREMENT_LENGTH)
    private String requirement;

}
