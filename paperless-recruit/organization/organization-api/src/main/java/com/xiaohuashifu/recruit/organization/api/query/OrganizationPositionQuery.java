package com.xiaohuashifu.recruit.organization.api.query;

import com.xiaohuashifu.recruit.common.constant.QueryConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.List;

/**
 * 描述：组织标签查询参数
 *
 * @author xhsf
 * @create 2020/12/7 13:29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationPositionQuery implements Serializable {

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
     * 组织职位编号
     */
    private Long id;

    /**
     * 组织职位编号列表
     */
    private List<Long> ids;

    /**
     * 组织编号
     */
    private Long organizationId;

    /**
     * 组织职位名，可模糊
     */
    private String positionName;

    /**
     * 职位优先级
     */
    private Integer priority;

    /**
     * 按照职位名排序
     */
    private Boolean orderByPositionName;

    /**
     * 按照职位名逆序排序
     */
    private Boolean orderByPositionNameDesc;

    /**
     * 按照职位优先级排序
     */
    private Boolean orderByPriority;

    /**
     * 按照标职位优先级逆序排序
     */
    private Boolean orderByPriorityDesc;

    /**
     * 按照创建时间排序
     */
    private Boolean orderByCreateTime;

    /**
     * 按照创建时间逆序排序
     */
    private Boolean orderByCreateTimeDesc;

    /**
     * 按照更新时间排序
     */
    private Boolean orderByUpdateTime;

    /**
     * 按照更新时间逆序排序
     */
    private Boolean orderByUpdateTimeDesc;

}
