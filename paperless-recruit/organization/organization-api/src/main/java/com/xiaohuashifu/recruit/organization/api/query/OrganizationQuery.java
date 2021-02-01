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
 * 描述：组织查询参数
 *
 * @author xhsf
 * @create 2020/12/7 13:29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationQuery implements Serializable {

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
    private Long id;

    /**
     * 组织编号列表
     */
    private List<Long> ids;

    /**
     * 组织主体编号
     */
    private Long userId;

    /**
     * 组织名，可模糊
     */
    private String organizationName;

    /**
     * 组织名缩写，可模糊
     */
    private String abbreviationOrganizationName;

    /**
     * 组织是否可用
     */
    private Boolean available;

    /**
     * 按照组织是否可用排序
     */
    private Boolean orderByAvailable;

    /**
     * 按照组织是否可用逆序排序
     */
    private Boolean orderByAvailableDesc;

    /**
     * 按照组织名排序
     */
    private Boolean orderByOrganizationName;

    /**
     * 按照组织名逆序排序
     */
    private Boolean orderByOrganizationNameDesc;

    /**
     * 按照组织名缩写排序
     */
    private Boolean orderByAbbreviationOrganizationName;

    /**
     * 按照组织名缩写逆序排序
     */
    private Boolean orderByAbbreviationOrganizationNameDesc;

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
