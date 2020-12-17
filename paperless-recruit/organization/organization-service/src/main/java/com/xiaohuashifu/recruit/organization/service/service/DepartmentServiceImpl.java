package com.xiaohuashifu.recruit.organization.service.service;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.recruit.common.aspect.annotation.DistributedLock;
import com.xiaohuashifu.recruit.common.result.ErrorCodeEnum;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.external.api.service.ObjectStorageService;
import com.xiaohuashifu.recruit.organization.api.constant.DepartmentConstants;
import com.xiaohuashifu.recruit.organization.api.dto.DepartmentDTO;
import com.xiaohuashifu.recruit.organization.api.po.UpdateDepartmentLogoPO;
import com.xiaohuashifu.recruit.organization.api.query.DepartmentQuery;
import com.xiaohuashifu.recruit.organization.api.service.DepartmentLabelService;
import com.xiaohuashifu.recruit.organization.api.service.DepartmentService;
import com.xiaohuashifu.recruit.organization.api.service.OrganizationService;
import com.xiaohuashifu.recruit.organization.service.dao.DepartmentMapper;
import com.xiaohuashifu.recruit.organization.service.do0.DepartmentDO;
import com.xiaohuashifu.recruit.organization.service.do0.DepartmentDepartmentLabelDO;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 描述：部门服务
 *
 * @author xhsf
 * @create 2020/12/8 21:56
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Reference
    private DepartmentLabelService departmentLabelService;

    @Reference
    private OrganizationService organizationService;

    @Reference
    private ObjectStorageService objectStorageService;

    private final DepartmentMapper departmentMapper;

    private final RedissonClient redissonClient;

    /**
     * 部门标签锁定键模式
     */
    private static final String DEPARTMENT_LABELS_LOCK_KEY_PATTERN = "department:{0}:labels";

    /**
     * 组织的部门名锁定键模式
     */
    private static final String ORGANIZATION_DEPARTMENT_DEPARTMENT_NAME_LOCK_KEY_PATTERN =
            "organization:{0}:department:department-name:{1}";

    /**
     * 组织的部门名缩写锁定键模式
     */
    private static final String ORGANIZATION_DEPARTMENT_ABBREVIATION_DEPARTMENT_NAME_LOCK_KEY_PATTERN =
            "organization:{0}:department:abbreviation-department-name:{1}";

    /**
     * 部门 logo 的锁定键模式
     */
    private static final String DEPARTMENT_LOGO_LOCK_KEY_PATTERN = "department:{0}:logo";

    /**
     * 部门 logo url 的前缀
     */
    private static final String DEPARTMENT_LOGO_URL_PREFIX = "organization/department/logo/";

    public DepartmentServiceImpl(DepartmentMapper departmentMapper, RedissonClient redissonClient) {
        this.departmentMapper = departmentMapper;
        this.redissonClient = redissonClient;
    }

    /**
     * 创建部门
     *
     * @errorCode InvalidParameter: 组织编号或部门猛或部门名缩写格式错误
     *              InvalidParameter.NotExist: 部门所属组织不存在
     *              Forbidden: 部门所属组织不可用
     *              OperationConflict: 该组织已经存在该部门名或部门名缩写
     *
     * @param organizationId 部门所属组织的编号
     * @param departmentName 部门名
     * @param abbreviationDepartmentName 部门名缩写
     * @return DepartmentDTO 部门对象
     */
    @Override
    public Result<DepartmentDTO> createDepartment(Long organizationId, String departmentName,
                                                  String abbreviationDepartmentName) {
        // 检查所属组织状态
        Result<DepartmentDTO> checkResult = organizationService.checkOrganizationStatus(organizationId);
        if (!checkResult.isSuccess()) {
            return checkResult;
        }

        // 判断组织是否已经有这个部门名了
        int count = departmentMapper.countByOrganizationIdAndDepartmentName(organizationId, departmentName);
        if (count > 0) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT, "The departmentName already exist.");
        }

        // 判断组织是否已经有这个部门名缩写了
        count = departmentMapper.countByOrganizationIdAndAbbreviationDepartmentName(
                organizationId, abbreviationDepartmentName);
        if (count > 0) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT,
                    "The abbreviationDepartmentName already exist.");
        }

        // 创建部门
        DepartmentDO departmentDO = new DepartmentDO.Builder()
                .organizationId(organizationId)
                .departmentName(departmentName)
                .abbreviationDepartmentName(abbreviationDepartmentName)
                .build();
        departmentMapper.insertDepartment(departmentDO);

        // 获取部门
        return getDepartment(departmentDO.getId());
    }

    /**
     * 添加部门的标签
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 组织不存在或部门不存在
     *              Forbidden: 组织不可用
     *              InvalidParameter.NotAvailable: 标签不可用
     *              OperationConflict: 该标签已经存在
     *              OperationConflict.OverLimit: 部门标签数量超过规定数量
     *              OperationConflict.Lock: 获取部门标签的锁失败
     *
     * @param departmentId 部门编号
     * @param labelName 标签名
     * @return 添加后的部门对象
     */
    @Override
    @DistributedLock(value = DEPARTMENT_LABELS_LOCK_KEY_PATTERN, parameters = "#{#departmentId}",
            errorMessage = "Failed to acquire department labels lock.")
    public Result<DepartmentDTO> addLabel(Long departmentId, String labelName) {
        // 检查部门和组织的状态
        Result<DepartmentDTO> checkResult = checkDepartmentStatus(departmentId);
        if (!checkResult.isSuccess()) {
            return checkResult;
        }

        // 判断该部门是否已经存在该标签
        int count = departmentMapper.countLabelByDepartmentIdAndLabelName(departmentId, labelName);
        if (count > 0) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT,
                    "The department already owns this label.");
        }

        // 判断标签数量是否大于 MAX_DEPARTMENT_LABEL_NUMBER
        count = departmentMapper.countLabelByDepartmentId(departmentId);
        if (count >= DepartmentConstants.MAX_DEPARTMENT_LABEL_NUMBER) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT_OVER_LIMIT,
                    "The number of label must not be greater than "
                            + DepartmentConstants.MAX_DEPARTMENT_LABEL_NUMBER + ".");
        }

        // 判断该标签是否可用
        Result<Void> checkDepartmentLabelResult = departmentLabelService.isValidDepartmentLabel(labelName);
        if (!checkDepartmentLabelResult.isSuccess()) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_AVAILABLE, "The label unavailable.");
        }

        // 部门标签的引用数增加
        departmentLabelService.increaseReferenceNumberOrSaveDepartmentLabel(labelName);

        // 添加部门的标签
        DepartmentDepartmentLabelDO departmentDepartmentLabelDO =
                new DepartmentDepartmentLabelDO.Builder()
                        .departmentId(departmentId)
                        .labelName(labelName)
                        .build();
        departmentMapper.insertLabel(departmentDepartmentLabelDO);

        // 获取添加标签后的部门对象
        return getDepartment(departmentId);
    }

    /**
     * 删除部门的标签
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 组织不存在或部门不存在
     *              Forbidden: 组织不可用
     *              OperationConflict: 该标签不存在
     *
     * @param departmentId 部门编号
     * @param labelName 标签名
     * @return 删除标签后的部门
     */
    @Override
    public Result<DepartmentDTO> removeLabel(Long departmentId, String labelName) {
        // 检查组织和部门状态
        Result<DepartmentDTO> checkResult = checkDepartmentStatus(departmentId);
        if (!checkResult.isSuccess()) {
            return checkResult;
        }

        // 判断该部门是否拥有该标签
        int count = departmentMapper.countLabelByDepartmentIdAndLabelName(departmentId, labelName);
        if (count < 1) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT, "The label does not exist.");
        }

        // 删除标签
        departmentMapper.deleteLabelByDepartmentIdAndLabelName(departmentId, labelName);

        // 获取删除标签后的部门对象
        return getDepartment(departmentId);
    }

    /**
     * 获取部门
     *
     * @errorCode InvalidParameter: 部门编号格式错误
     *              InvalidParameter.NotFound: 该编号的部门不存在
     *
     * @param id 部门编号
     * @return DepartmentDTO
     */
    @Override
    public Result<DepartmentDTO> getDepartment(Long id) {
        DepartmentDO departmentDO = departmentMapper.getDepartment(id);
        // 部门不存在
        if (departmentDO == null) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_FOUND,
                    "The department does not exist.");
        }

        // 封装成 DTO
        List<String> labels = departmentMapper.listDepartmentLabelNamesByDepartmentId(id);
        DepartmentDTO departmentDTO = new DepartmentDTO.Builder()
                .id(departmentDO.getId())
                .organizationId(departmentDO.getOrganizationId())
                .departmentName(departmentDO.getDepartmentName())
                .abbreviationDepartmentName(departmentDO.getAbbreviationDepartmentName())
                .introduction(departmentDO.getIntroduction())
                .logoUrl(departmentDO.getLogoUrl())
                .memberNumber(departmentDO.getMemberNumber())
                .labels(labels)
                .build();
        return Result.success(departmentDTO);
    }

    /**
     * 查询部门
     *
     * @errorCode InvalidParameter: 查询参数格式错误
     *
     * @param query 查询参数
     * @return PageInfo<DepartmentDTO> 查询结果，可能返回空列表
     */
    @Override
    public Result<PageInfo<DepartmentDTO>> listDepartments(DepartmentQuery query) {
        List<DepartmentDO> departmentDOList = departmentMapper.listDepartments(query);
        List<DepartmentDTO> departmentDTOList = departmentDOList
                .stream()
                .map(departmentDO -> new DepartmentDTO
                        .Builder()
                        .id(departmentDO.getId())
                        .organizationId(departmentDO.getOrganizationId())
                        .departmentName(departmentDO.getDepartmentName())
                        .abbreviationDepartmentName(departmentDO.getAbbreviationDepartmentName())
                        .introduction(departmentDO.getIntroduction())
                        .logoUrl(departmentDO.getLogoUrl())
                        .memberNumber(departmentDO.getMemberNumber())
                        .labels(departmentMapper.listDepartmentLabelNamesByDepartmentId(departmentDO.getId()))
                        .build())
                .collect(Collectors.toList());
        PageInfo<DepartmentDTO> pageInfo = new PageInfo<>(departmentDTOList);
        return Result.success(pageInfo);
    }

    /**
     * 更新部门名
     *
     * @errorCode InvalidParameter: 部门编号或部门名格式错误
     *              InvalidParameter.NotExist: 组织不存在或部门不存在
     *              Forbidden: 组织不可用
     *              OperationConflict: 该组织已经存在相同的部门名
     *              OperationConflict.Lock: 获取组织的部门名锁失败
     *
     * @param id 部门编号
     * @param newDepartmentName 新部门名
     * @return 更新后的部门
     */
    @Override
    public Result<DepartmentDTO> updateDepartmentName(Long id, String newDepartmentName) {
        // 检查组织和部门状态
        Result<DepartmentDTO> checkResult = checkDepartmentStatus(id);
        if (!checkResult.isSuccess()) {
            return checkResult;
        }

        // 获取组织编号
        Long organizationId = departmentMapper.getOrganizationIdByDepartmentId(id);

        // 尝试对这个组织的这个部门名加锁
        String lockKey = MessageFormat.format(ORGANIZATION_DEPARTMENT_DEPARTMENT_NAME_LOCK_KEY_PATTERN,
                organizationId, newDepartmentName);
        RLock lock = redissonClient.getLock(lockKey);

        // 加锁失败
        if (!lock.tryLock()) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT_LOCK,
                    "Failed to acquire departmentName lock.");
        }

        try {
            // 判断该组织是否已经存在该部门名
            int count = departmentMapper.countByOrganizationIdAndDepartmentName(organizationId, newDepartmentName);
            if (count > 0) {
                return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT, "The departmentName already exist.");
            }

            // 更新部门名
            departmentMapper.updateDepartmentName(id, newDepartmentName);

            // 获取更新后的部门对象
            return getDepartment(id);
        } finally {
            // 释放锁
            lock.unlock();
        }
    }

    /**
     * 更新部门名缩写
     *
     * @errorCode InvalidParameter: 部门编号或部门名缩写格式错误
     *              InvalidParameter.NotExist: 组织不存在或部门不存在
     *              Forbidden: 组织不可用
     *              OperationConflict: 该组织已经存在相同的部门名缩写
     *              OperationConflict.Lock: 获取组织的部门名缩写锁失败
     *
     * @param id 部门编号
     * @param newAbbreviationDepartmentName 新部门名缩写
     * @return 更新后的部门
     */
    @Override
    public Result<DepartmentDTO> updateAbbreviationDepartmentName(Long id, String newAbbreviationDepartmentName) {
        // 检查组织和部门状态
        Result<DepartmentDTO> checkResult = checkDepartmentStatus(id);
        if (!checkResult.isSuccess()) {
            return checkResult;
        }

        // 获取组织编号
        Long organizationId = departmentMapper.getOrganizationIdByDepartmentId(id);

        // 尝试对这个组织的这个部门名缩写加锁
        String lockKey = MessageFormat.format(ORGANIZATION_DEPARTMENT_ABBREVIATION_DEPARTMENT_NAME_LOCK_KEY_PATTERN,
                organizationId, newAbbreviationDepartmentName);
        RLock lock = redissonClient.getLock(lockKey);

        // 加锁失败
        if (!lock.tryLock()) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT_LOCK,
                    "Failed to acquire abbreviationDepartmentName lock.");
        }

        try {
            // 判断该组织是否已经存在该部门名缩写
            int count = departmentMapper.countByOrganizationIdAndAbbreviationDepartmentName(
                    organizationId, newAbbreviationDepartmentName);
            if (count > 0) {
                return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT,
                        "The abbreviationDepartmentName already exist.");
            }

            // 更新部门名缩写
            departmentMapper.updateAbbreviationDepartmentName(id, newAbbreviationDepartmentName);

            // 获取更新后的部门对象
            return getDepartment(id);
        } finally {
            // 释放锁
            lock.unlock();
        }
    }

    /**
     * 更新部门介绍
     *
     * @errorCode InvalidParameter: 部门编号或部门介绍格式错误
     *              InvalidParameter.NotExist: 组织不存在或部门不存在
     *              Forbidden: 组织不可用
     *
     * @param id 部门编号
     * @param newIntroduction 新部门介绍
     * @return 更新后的部门
     */
    @Override
    public Result<DepartmentDTO> updateIntroduction(Long id, String newIntroduction) {
        // 检查组织和部门状态
        Result<DepartmentDTO> checkResult = checkDepartmentStatus(id);
        if (!checkResult.isSuccess()) {
            return checkResult;
        }

        // 更新部门介绍
        departmentMapper.updateIntroduction(id, newIntroduction);

        // 获取更新后的部门对象
        return getDepartment(id);
    }

    /**
     * 更新部门 Logo
     *
     * @errorCode InvalidParameter: 更新参数格式错误
     *              InvalidParameter.NotExist: 组织不存在或部门不存在
     *              Forbidden: 组织不可用
     *              InternalError: 上传文件失败
     *              OperationConflict.Lock: 获取部门 logo 的锁失败
     *
     * @param updateDepartmentLogoPO 更新 logo 的参数对象
     * @return 更新后的部门
     */
    @Override
    @DistributedLock(value = DEPARTMENT_LOGO_LOCK_KEY_PATTERN, parameters = "#{#updateDepartmentLogoPO.id}",
            errorMessage = "Failed to acquire department logo lock.")
    public Result<DepartmentDTO> updateLogo(UpdateDepartmentLogoPO updateDepartmentLogoPO) {
        // 检查组织和部门状态
        Result<DepartmentDTO> checkResult = checkDepartmentStatus(updateDepartmentLogoPO.getId());
        if (!checkResult.isSuccess()) {
            return checkResult;
        }

        // 获取部门 logoUrl
        String logoUrl = departmentMapper.getDepartmentLogoUrlByDepartmentId(updateDepartmentLogoPO.getId());
        // 若原来的 logoUrl 为空，则随机产生一个
        boolean needUpdateLogoUrl = false;
        if (StringUtils.isBlank(logoUrl)) {
            needUpdateLogoUrl = true;
            logoUrl = DEPARTMENT_LOGO_URL_PREFIX + UUID.randomUUID().toString()
                    + updateDepartmentLogoPO.getId() + updateDepartmentLogoPO.getLogoExtensionName();
        }

        // 更新 logo
        objectStorageService.putObject(logoUrl, updateDepartmentLogoPO.getLogo());

        // 若需要更新 logoUrl 到数据库，则更新
        if (needUpdateLogoUrl) {
            departmentMapper.updateLogoUrl(updateDepartmentLogoPO.getId(), logoUrl);
        }

        // 更新后的组织信息
        return getDepartment(updateDepartmentLogoPO.getId());
    }

    /**
     * 增加成员数，+1
     *
     * @errorCode InvalidParameter: 部门编号格式错误
     *              InvalidParameter.NotExist: 组织不存在或部门不存在
     *              Forbidden: 组织不可用
     *
     * @param id 部门编号
     * @return 增加成员数后的部门对象
     */
    @Override
    public Result<DepartmentDTO> increaseMemberNumber(Long id) {
        // 检查组织和部门状态
        Result<DepartmentDTO> checkResult = checkDepartmentStatus(id);
        if (!checkResult.isSuccess()) {
            return checkResult;
        }

        // 增加成员数
        departmentMapper.increaseMemberNumber(id);

        // 添加成员数后的部门对象
        return getDepartment(id);
    }

    /**
     * 减少成员数，-1
     *
     * @errorCode InvalidParameter: 部门编号格式错误
     *              InvalidParameter.NotExist: 组织不存在或部门不存在
     *              Forbidden: 组织不可用
     *
     * @param id 部门编号
     * @return 减少成员数后的部门对象
     */
    @Override
    public Result<DepartmentDTO> decreaseMemberNumber(Long id) {
        // 检查组织和部门状态
        Result<DepartmentDTO> checkResult = checkDepartmentStatus(id);
        if (!checkResult.isSuccess()) {
            return checkResult;
        }

        // 减少成员数
        departmentMapper.decreaseMemberNumber(id);

        // 减少成员数后的部门对象
        return getDepartment(id);
    }

    /**
     * 检查部门状态
     *
     * @errorCode InvalidParameter.NotExist: 组织不存在或部门不存在
     *              Forbidden: 组织不可用
     *
     * @param departmentId 部门编号
     * @return 检查结果
     */
    private <T> Result<T> checkDepartmentStatus(Long departmentId) {
        // 判断部门存不存在
        Long organizationId = departmentMapper.getOrganizationIdByDepartmentId(departmentId);
        if (organizationId == null) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_EXIST,
                    "The department does not exist.");
        }

        // 检查组织状态
        return organizationService.checkOrganizationStatus(organizationId);
    }

}
