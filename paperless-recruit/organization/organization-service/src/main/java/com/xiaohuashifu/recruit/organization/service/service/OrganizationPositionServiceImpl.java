package com.xiaohuashifu.recruit.organization.service.service;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.recruit.common.result.ErrorCodeEnum;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.organization.api.dto.OrganizationPositionDTO;
import com.xiaohuashifu.recruit.organization.api.query.OrganizationPositionQuery;
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

    private final OrganizationPositionMapper organizationPositionMapper;

    public OrganizationPositionServiceImpl(OrganizationPositionMapper organizationPositionMapper) {
        this.organizationPositionMapper = organizationPositionMapper;
    }

    /**
     * 保存组织职位
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 组织不存在
     *              Forbidden: 组织不可用
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
        // 检查组织状态
        Result<OrganizationPositionDTO> checkResult = organizationService.checkOrganizationStatus(organizationId);
        if (!checkResult.isSuccess()) {
            return checkResult;
        }

        // 判断该组织是否已经存在该部门名
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
     *
     * 会把该组织职位的成员的职位都清除
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 组织不存在或组织职位不存在
     *              Forbidden: 组织不可用
     *
     * @param id 组织职位编号
     * @return 删除结果
     */
    @Override
    public Result<Void> removeOrganizationPosition(Long id) {
        // 检查组织和组织职位状态
        Result<Long> checkResult = checkOrganizationAndPositionStatus(id);
        if (checkResult.isFailure()) {
            return Result.fail(checkResult);
        }

        // 把该职位的组织成员的职位都清除（设置为0）

        return null;
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
        OrganizationPositionDO organizationPositionDO =
                organizationPositionMapper.getOrganizationPosition(id);
        // 判断组织职位是否存在
        if (organizationPositionDO == null) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_FOUND,
                    "The organization position does not exist.");
        }

        // 封装成 DTO
        OrganizationPositionDTO organizationPositionDTO = new OrganizationPositionDTO.Builder()
                .id(organizationPositionDO.getId())
                .organizationId(organizationPositionDO.getOrganizationId())
                .positionName(organizationPositionDO.getPositionName())
                .priority(organizationPositionDO.getPriority())
                .build();
        return Result.success(organizationPositionDTO);
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
                .map(organizationPositionDO -> new OrganizationPositionDTO
                        .Builder()
                        .id(organizationPositionDO.getId())
                        .organizationId(organizationPositionDO.getOrganizationId())
                        .positionName(organizationPositionDO.getPositionName())
                        .priority(organizationPositionDO.getPriority())
                        .build())
                .collect(Collectors.toList());
        PageInfo<OrganizationPositionDTO> pageInfo = new PageInfo<>(organizationPositionDTOList);
        return Result.success(pageInfo);
    }

    /**
     * 更新组织职位名
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 组织不存在或组织职位不存在
     *              Forbidden: 组织不可用
     *              OperationConflict: 职位名已经存在
     *
     * @param id 组织职位编号
     * @param newPositionName 新职位名
     * @return 更新后的组织职位对象
     */
    @Override
    public Result<OrganizationPositionDTO> updatePositionName(Long id, String newPositionName) {
        // 检查组织和组织职位状态
        Result<Long> checkResult = checkOrganizationAndPositionStatus(id);
        if (!checkResult.isSuccess()) {
            return Result.fail(checkResult.getErrorCode(), checkResult.getErrorMessage());
        }

        // 判断组织是否存在相同的职位名
        Long organizationId = checkResult.getData();
        int count = organizationPositionMapper.countByOrganizationIdPositionName(organizationId, newPositionName);
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
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 组织不存在或组织职位不存在
     *              Forbidden: 组织不可用
     *
     * @param id 组织职位编号
     * @param newPriority 新职位优先级
     * @return 更新后的组织职位对象
     */
    @Override
    public Result<OrganizationPositionDTO> updatePriority(Long id, Integer newPriority) {
        // 检查组织和组织职位状态
        Result<Long> checkResult = checkOrganizationAndPositionStatus(id);
        if (!checkResult.isSuccess()) {
            return Result.fail(checkResult.getErrorCode(), checkResult.getErrorMessage());
        }

        // 更新组织组织优先级
        organizationPositionMapper.updatePriority(id, newPriority);
        return getOrganizationPosition(id);
    }

    /**
     * 检查组织和组织职位的状态
     *
     * @errorCode InvalidParameter.NotExist: 组织不存在或组织职位不存在
     *              Forbidden: 组织不可用
     *
     * @param organizationPositionId 组织职位编号
     * @return 若检查成功则返回组织编号
     */
    private Result<Long> checkOrganizationAndPositionStatus(Long organizationPositionId) {
        // 判断组织职位存不存在
        Long organizationId = organizationPositionMapper.getOrganizationId(organizationPositionId);
        if (organizationId == null) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_EXIST,
                    "The organizationPosition does not exist.");
        }

        // 检查组织状态
        Result<Long> checkOrganizationStatusResult =
                organizationService.checkOrganizationStatus(organizationId);
        if (checkOrganizationStatusResult.isFailure()) {
            return checkOrganizationStatusResult;
        }

        // 通过检查
        return Result.success(organizationId);
    }

}
