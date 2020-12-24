package com.xiaohuashifu.recruit.organization.api.service;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.organization.api.constant.OrganizationPositionConstants;
import com.xiaohuashifu.recruit.organization.api.dto.OrganizationPositionDTO;
import com.xiaohuashifu.recruit.organization.api.query.OrganizationPositionQuery;

import javax.validation.constraints.*;

/**
 * 描述：组织职位服务
 *
 * @author xhsf
 * @create 2020/12/13 19:22
 */
public interface OrganizationPositionService {

    /**
     * 保存组织职位
     *
     * @permission 必须是该组织的主体用户
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 组织不存在
     *              Forbidden.Unavailable: 组织不可用
     *              OperationConflict: 操作冲突，该组织已经存在该部门名
     *
     * @param organizationId 组织编号
     * @param positionName 职位名
     * @param priority 优先级，0-9，0最大，9最小
     * @return 组织职位对象
     */
    Result<OrganizationPositionDTO> saveOrganizationPosition(
            @NotNull(message = "The organizationId can't be null.")
            @Positive(message = "The organizationId must be greater than 0.") Long organizationId,
            @NotBlank(message = "The positionName can't be blank.")
            @Size(max = OrganizationPositionConstants.MAX_ORGANIZATION_POSITION_NAME_LENGTH,
                    message = "The length of positionName must not be greater than "
                            + OrganizationPositionConstants.MAX_ORGANIZATION_POSITION_NAME_LENGTH + ".")
                    String positionName,
            @NotNull(message = "The priority can't be null.")
            @Min(value = OrganizationPositionConstants.MIN_ORGANIZATION_POSITION_PRIORITY,
                    message = "The priority must be greater than or equal to "
                            + OrganizationPositionConstants.MIN_ORGANIZATION_POSITION_PRIORITY + ".")
            @Max(value = OrganizationPositionConstants.MAX_ORGANIZATION_POSITION_PRIORITY,
                    message = "The priority must be less than or equal to "
                            + OrganizationPositionConstants.MAX_ORGANIZATION_POSITION_PRIORITY + ".") Integer priority);

    /**
     * 删除组织职位
     * 会把该组织职位的成员的职位都清除
     *
     * @permission 必须是该组织职位所属组织的主体用户
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 组织职位不存在
     *              Forbidden.Unavailable: 组织不可用
     *
     * @param id 组织职位编号
     * @return 删除结果
     */
    Result<Integer> removeOrganizationPosition(@NotNull(message = "The id can't be null.")
                                               @Positive(message = "The id must be greater than 0.") Long id);

    /**
     * 获取组织职位
     *
     * @errorCode InvalidParameter: 组织职位编号格式错误
     *              InvalidParameter.NotFound: 找不到该编号的组织职位
     *
     * @param id 组织职位编号
     * @return OrganizationPositionDTO 组织职位对象
     */
    Result<OrganizationPositionDTO> getOrganizationPosition(
            @NotNull(message = "The organizationPositionId can't be null.")
            @Positive(message = "The organizationPositionId must be greater than 0.") Long id);

    /**
     * 查询组织职位
     *
     * @errorCode InvalidParameter: 查询参数格式错误
     *
     * @param query 查询参数
     * @return PageInfo<OrganizationPositionDTO> 带分页参数的组织职位列表，可能返回空列表
     */
    Result<PageInfo<OrganizationPositionDTO>> listOrganizationPositions(
            @NotNull(message = "The query can't be null.") OrganizationPositionQuery query);

    /**
     * 更新组织职位名
     *
     * @permission 必须是该组织职位所属组织的主体用户
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 组织职位不存在
     *              Forbidden.Unavailable: 组织不可用
     *              OperationConflict: 职位名已经存在
     *
     * @param id 组织职位编号
     * @param newPositionName 新职位名
     * @return 更新后的组织职位对象
     */
    Result<OrganizationPositionDTO> updatePositionName(
            @NotNull(message = "The id can't be null.") @Positive(message = "The id must be greater than 0.") Long id,
            @NotBlank(message = "The newPositionName can't be blank.")
            @Size(max = OrganizationPositionConstants.MAX_ORGANIZATION_POSITION_NAME_LENGTH,
                    message = "The length of newPositionName must not be greater than "
                            + OrganizationPositionConstants.MAX_ORGANIZATION_POSITION_NAME_LENGTH + ".")
                    String newPositionName);

    /**
     * 更新组织职位优先级
     *
     * @permission 必须是该组织职位所属组织的主体用户
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 组织职位不存在
     *              Forbidden.Unavailable: 组织不可用
     *
     * @param id 组织职位编号
     * @param newPriority 新职位优先级
     * @return 更新后的组织职位对象
     */
    Result<OrganizationPositionDTO> updatePriority(
            @NotNull(message = "The id can't be null.")
            @Positive(message = "The id must be greater than 0.") Long id,
            @NotNull(message = "The priority can't be null.")
            @Min(value = OrganizationPositionConstants.MIN_ORGANIZATION_POSITION_PRIORITY,
                    message = "The newPriority must be greater than or equal to "
                            + OrganizationPositionConstants.MIN_ORGANIZATION_POSITION_PRIORITY + ".")
            @Max(value = OrganizationPositionConstants.MAX_ORGANIZATION_POSITION_PRIORITY,
                    message = "The newPriority must be less than or equal to "
                            + OrganizationPositionConstants.MAX_ORGANIZATION_POSITION_PRIORITY + ".")
                    Integer newPriority);

    /**
     * 获取组织编号
     *
     * @private 内部方法
     *
     * @param id 组织职位编号
     * @return 组织编号，若找不到返回 null
     */
    Long getOrganizationId(Long id);

}
