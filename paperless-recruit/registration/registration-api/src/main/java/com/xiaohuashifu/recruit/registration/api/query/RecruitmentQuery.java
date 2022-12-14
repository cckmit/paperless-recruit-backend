package com.xiaohuashifu.recruit.registration.api.query;

import com.xiaohuashifu.recruit.common.constant.QueryConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;

/**
 * 描述：招新的查询
 *
 * @author xhsf
 * @create 2020/12/30 19:09
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RecruitmentQuery implements Serializable {

    /**
     * 页码
     */
    @NotNull
    @Positive
    private Long pageNum;

    /**
     * 页条数
     */
    @NotNull
    @Positive
    @Max(value = QueryConstants.MAX_PAGE_SIZE)
    private Long pageSize;

    /**
     * 组织编号
     */
    private Long organizationId;

    /**
     * 招新名，右模糊
     */
    private String recruitmentName;

    /**
     * @see com.xiaohuashifu.recruit.registration.api.constant.RecruitmentStatusEnum
     * 招新状态
     */
    private String recruitmentStatus;

    /**
     * 根据招新状态排序
     */
    private Boolean orderByRecruitmentStatus;

    /**
     * 根据招新状态排序，降序
     */
    private Boolean orderByRecruitmentStatusDesc;

    /**
     * 根据更新时间排序
     */
    private Boolean orderByUpdateTime;

    /**
     * 根据更新时间排序，降序
     */
    private Boolean orderByUpdateTimeDesc;

}
