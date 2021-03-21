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
 * 描述：基础的查询
 *
 * @author xhsf
 * @create 2020/12/30 19:09
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ApplicationFormQuery implements Serializable {

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
     * 招新编号
     */
    private Long recruitmentId;

    /**
     * 学院，右模糊
     */
    private String college;

    /**
     * 专业，右模糊
     */
    private String major;

    /**
     * 按报名时间排序
     */
    private Boolean orderByApplicationTime;

    /**
     * 按报名时间排序，降序
     */
    private Boolean orderByApplicationTimeDesc;

}
