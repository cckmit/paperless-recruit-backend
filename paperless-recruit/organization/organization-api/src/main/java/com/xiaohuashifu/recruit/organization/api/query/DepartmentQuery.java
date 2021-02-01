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
 * 描述：部门查询参数
 *
 * @author xhsf
 * @create 2020/12/7 13:29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentQuery implements Serializable {

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
     * 部门编号
     */
    private Long id;

    /**
     * 部门编号列表
     */
    private List<Long> ids;

    /**
     * 部门所属组织编号
     */
    private Long organizationId;

    /**
     * 部门名，可模糊
     */
    private String departmentName;

    /**
     * 部门名缩写，可模糊
     */
    private String abbreviationDepartmentName;

    /**
     * 部门是否被废弃
     */
    private Boolean deactivated;

    /**
     * 按照部门是否被废弃排序
     */
    private Boolean orderByDeactivated;

    /**
     * 按照部门是否被废弃逆序排序
     */
    private Boolean orderByDeactivatedDesc;

    /**
     * 按照部门名排序
     */
    private Boolean orderByDepartmentName;

    /**
     * 按照部门名逆序排序
     */
    private Boolean orderByDepartmentNameDesc;

    /**
     * 按照部门名缩写排序
     */
    private Boolean orderByAbbreviationDepartmentName;

    /**
     * 按照部门名缩写逆序排序
     */
    private Boolean orderByAbbreviationDepartmentNameDesc;

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
