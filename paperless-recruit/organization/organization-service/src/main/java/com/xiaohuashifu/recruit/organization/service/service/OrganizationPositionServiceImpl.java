package com.xiaohuashifu.recruit.organization.service.service;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.recruit.common.result.ErrorCodeEnum;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.organization.api.dto.OrganizationDTO;
import com.xiaohuashifu.recruit.organization.api.dto.OrganizationPositionDTO;
import com.xiaohuashifu.recruit.organization.api.query.OrganizationPositionQuery;
import com.xiaohuashifu.recruit.organization.api.service.OrganizationMemberService;
import com.xiaohuashifu.recruit.organization.api.service.OrganizationPositionService;
import com.xiaohuashifu.recruit.organization.api.service.OrganizationService;
import com.xiaohuashifu.recruit.organization.service.dao.OrganizationPositionMapper;
import com.xiaohuashifu.recruit.organization.service.do0.OrganizationPositionDO;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 描述：组织职位服务
 *
 * @author xhsf
 * @create 2020/12/15 14:35
 */
@Service
public class OrganizationPositionServiceImpl implements OrganizationPositionService {

    @Reference
    private OrganizationService organizationService;

    @Reference
    private OrganizationMemberService organizationMemberService;

    private final OrganizationPositionMapper organizationPositionMapper;

    public OrganizationPositionServiceImpl(OrganizationPositionMapper organizationPositionMapper) {
        this.organizationPositionMapper = organizationPositionMapper;
    }

    /**
     * 保存组织职位
     *
     * @permission 必须是该组织的主体用户
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 组织不存在
     *              OperationConflict: 操作冲突，该组织已经存在该部门名
     *
     * @param organizationId 组织编号
     * @param positionName 职位名
     * @param priority 优先级，0-9，0最大，9最小
     * @return 组织职位对象
     */
    @Override
    public Result<OrganizationPositionDTO> saveOrganizationPosition(Long organizationId, String positionName,
                                                                    Integer priority) {
        // 判断组织存不存在
        Result<OrganizationDTO> getOrganizationResult = organizationService.getOrganization(organizationId);
        if (getOrganizationResult.isFailure()) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_EXIST,
                    "The organization does not exist.");
        }

        // 判断该组织是否已经存在该职位名
        int count = organizationPositionMapper.countByOrganizationIdPositionName(organizationId, positionName);
        if (count > 0) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT, "The positionName already exist.");
        }

        // 插入职位
        OrganizationPositionDO organizationPositionDO = new OrganizationPositionDO.Builder()
                .organizationId(organizationId)
                .positionName(positionName)
                .priority(priority).build();
        organizationPositionMapper.insertOrganizationPosition(organizationPositionDO);
        return getOrganizationPosition(organizationPositionDO.getId());
    }

    /**
     * 删除组织职位
     * 会把该组织职位的成员的职位都清除
     *
     * @permission 必须是该组织职位所属组织的主体用户
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 组织职位不存在
     *
     * @param id 组织职位编号
     * @return 删除结果
     */
    @Override
    public Result<Integer> removeOrganizationPosition(Long id) {
        // 判断该组织职位是否存在
        int count = organizationPositionMapper.count(id);
        if (count < 1) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_EXIST,
                    "The organizationPosition does not exist.");
        }

        // 把该职位的组织成员的职位都清除（设置为0）
        Result<Integer> clearResult = organizationMemberService.clearOrganizationPositions(id);
        Integer clearCount = clearResult.getData();

        // 删除组织职位
        organizationPositionMapper.deleteOrganizationPosition(id);
        return Result.success(clearCount);
    }

    /**
     * 获取组织职位
     *
     * @errorCode InvalidParameter: 组织职位编号格式错误
     *              InvalidParameter.NotFound: 找不到该编号的组织职位
     *
     * @param id 组织职位编号
     * @return OrganizationPositionDTO 组织职位对象
     */
    @Override
    public Result<OrganizationPositionDTO> getOrganizationPosition(Long id) {
        OrganizationPositionDO organizationPositionDO = organizationPositionMapper.getOrganizationPosition(id);
        // 判断组织职位是否存在
        if (organizationPositionDO == null) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_FOUND,
                    "The organization position does not exist.");
        }
        return Result.success(organizationPositionDO2OrganizationPositionDTO(organizationPositionDO));
    }

    /**
     * 查询组织职位
     *
     * @errorCode InvalidParameter: 查询参数格式错误
     *
     * @param query 查询参数
     * @return PageInfo<OrganizationPositionDTO> 带分页参数的组织职位列表，可能返回空列表
     */
    @Override
    public Result<PageInfo<OrganizationPositionDTO>> listOrganizationPositions(OrganizationPositionQuery query) {
        List<OrganizationPositionDO> organizationPositionDOList =
                organizationPositionMapper.listOrganizationPositions(query);
        List<OrganizationPositionDTO> organizationPositionDTOList = organizationPositionDOList
                .stream()
                .map(this::organizationPositionDO2OrganizationPositionDTO)
                .collect(Collectors.toList());
        PageInfo<OrganizationPositionDTO> pageInfo = new PageInfo<>(organizationPositionDTOList);
        return Result.success(pageInfo);
    }

    /**
     * 获取组织职位所属组织的主体用户
     *
     * @private 内部方法
     *
     * @param id 组织职位编号
     * @return 主体用户编号，若找不到返回 null
     */
    @Override
    public Long getUserId(Long id) {
        Long organizationId = organizationPositionMapper.getOrganizationId(id);
        return organizationService.getUserId(organizationId);
    }

    /**
     * 更新组织职位名
     *
     * @permission 必须是该组织职位所属组织的主体用户
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 组织职位不存在
     *              OperationConflict: 职位名已经存在
     *
     * @param id 组织职位编号
     * @param newPositionName 新职位名
     * @return 更新后的组织职位对象
     */
    @Override
    public Result<OrganizationPositionDTO> updatePositionName(Long id, String newPositionName) {
        // 判断该组织职位是否存在
        OrganizationPositionDO organizationPositionDO = organizationPositionMapper.getOrganizationPosition(id);
        if (organizationPositionDO == null) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_EXIST,
                    "The organizationPosition does not exist.");
        }

        // 判断组织是否存在相同的职位名
        int count = organizationPositionMapper.countByOrganizationIdPositionName(
                organizationPositionDO.getOrganizationId(), newPositionName);
        if (count > 0) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT, "The newPositionName already exist.");
        }

        // 更新组织职位名
        organizationPositionMapper.updatePositionName(id, newPositionName);
        return getOrganizationPosition(id);
    }

    /**
     * 更新组织职位优先级
     *
     * @permission 必须是该组织职位所属组织的主体用户
     *              InvalidParameter.NotExist: 组织职位不存在
     *
     * @errorCode InvalidParameter: 参数格式错误
     *
     * @param id 组织职位编号
     * @param newPriority 新职位优先级
     * @return 更新后的组织职位对象
     */
    @Override
    public Result<OrganizationPositionDTO> updatePriority(Long id, Integer newPriority) {
        // 判断该组织职位是否存在
        int count = organizationPositionMapper.count(id);
        if (count < 1) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_EXIST,
                    "The organizationPosition does not exist.");
        }

        // 更新组织组织优先级
        organizationPositionMapper.updatePriority(id, newPriority);
        return getOrganizationPosition(id);
    }

    /**
     * OrganizationPositionDO to OrganizationPositionDTO
     *
     * @param organizationPositionDO OrganizationPositionDO
     * @return OrganizationPositionDTO
     */
    private OrganizationPositionDTO organizationPositionDO2OrganizationPositionDTO(
            OrganizationPositionDO organizationPositionDO) {
        return new OrganizationPositionDTO.Builder()
                .id(organizationPositionDO.getId())
                .organizationId(organizationPositionDO.getOrganizationId())
                .positionName(organizationPositionDO.getPositionName())
                .priority(organizationPositionDO.getPriority())
                .build();
    }

}
