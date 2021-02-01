package com.xiaohuashifu.recruit.organization.api.query;

import com.xiaohuashifu.recruit.common.constant.QueryConstants;
import com.xiaohuashifu.recruit.organization.api.constant.OrganizationMemberStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;

/**
 * 描述：组织成员查询参数
 *
 * @author xhsf
 * @create 2020/12/16 13:29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationMemberQuery implements Serializable {

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
     * 部门编号
     */
    private Long departmentId;

    /**
     * 组织职位编号
     */
    private Long organizationPositionId;

    /**
     * 成员状态
     */
    private OrganizationMemberStatusEnum memberStatus;

    /**
     * 按照组织编号排序
     */
    private Boolean orderByOrganizationId;

    /**
     * 按照组织编号逆序排序
     */
    private Boolean orderByOrganizationIdDesc;

    /**
     * 按照部门编号排序
     */
    private Boolean orderByDepartmentId;

    /**
     * 按照部门编号逆序排序
     */
    private Boolean orderByDepartmentIdDesc;

    /**
     * 按照成员状态排序
     */
    private Boolean orderByMemberStatus;

    /**
     * 按照成员状态逆序排序
     */
    private Boolean orderByMemberStatusDesc;

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
