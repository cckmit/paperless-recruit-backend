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

    public DepartmentServiceImpl(DepartmentMapper departmentMapper) {
        this.departmentMapper = departmentMapper;
    }

    /**
     * 创建部门
     *
     * @permission 需要 organizationId 是该组织自身
     *
     * @errorCode InvalidParameter: 组织编号或部门猛或部门名缩写格式错误
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
     * @permission 需要该部门属于该组织
     *
     * @errorCode InvalidParameter: 参数格式错误
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
     * @permission 需要该部门属于该组织
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              OperationConflict: 该标签不存在
     *
     * @param departmentId 部门编号
     * @param labelName 标签名
     * @return 删除标签后的部门
     */
    @Override
    public Result<DepartmentDTO> removeLabel(Long departmentId, String labelName) {
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
     * 获取该部门所属组织的编号
     *
     * @private 内部方法
     *
     * @param id 部门编号
     * @return 组织编号，若该部门不存在则返回 null
     */
    @Override
    public Long getOrganizationId(Long id) {
        return departmentMapper.getOrganizationIdByDepartmentId(id);
    }

    /**
     * 更新部门名
     *
     * @permission 需要该部门属于该组织
     *
     * @errorCode InvalidParameter: 部门编号或部门名格式错误
     *              OperationConflict: 该组织已经存在相同的部门名
     *              OperationConflict.Lock: 获取组织的部门名锁失败
     *
     * @param organizationId 部门所属组织编号
     * @param departmentId 部门编号
     * @param newDepartmentName 新部门名
     * @return 更新后的部门
     */
    @DistributedLock(value = ORGANIZATION_DEPARTMENT_DEPARTMENT_NAME_LOCK_KEY_PATTERN,
            parameters = {"#{#organizationId}", "#{#newDepartmentName}"},
            errorMessage = "Failed to acquire departmentName lock.")
    @Override
    public Result<DepartmentDTO> updateDepartmentName(Long organizationId, Long departmentId,
                                                      String newDepartmentName) {
        // 判断该组织是否已经存在该部门名
        int count = departmentMapper.countByOrganizationIdAndDepartmentName(organizationId, newDepartmentName);
        if (count > 0) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT, "The departmentName already exist.");
        }

        // 更新部门名
        departmentMapper.updateDepartmentName(departmentId, newDepartmentName);

        // 获取更新后的部门对象
        return getDepartment(departmentId);
    }

    /**
     * 更新部门名缩写
     *
     * @permission 需要该部门属于该组织
     *
     * @errorCode InvalidParameter: 部门编号或部门名缩写格式错误
     *              OperationConflict.Lock: 获取组织的部门名缩写锁失败
     *              OperationConflict: 该组织已经存在相同的部门名缩写
     *
     * @param organizationId 部门所属组织编号
     * @param departmentId 部门编号
     * @param newAbbreviationDepartmentName 新部门名缩写
     * @return 更新后的部门
     */
    @DistributedLock(value = ORGANIZATION_DEPARTMENT_ABBREVIATION_DEPARTMENT_NAME_LOCK_KEY_PATTERN,
            parameters = {"#{#organizationId}", "#{#newAbbreviationDepartmentName}"},
            errorMessage = "Failed to acquire abbreviationDepartmentName lock.")
    @Override
    public Result<DepartmentDTO> updateAbbreviationDepartmentName(
            Long organizationId, Long departmentId, String newAbbreviationDepartmentName) {
        // 判断该组织是否已经存在该部门名缩写
        int count = departmentMapper.countByOrganizationIdAndAbbreviationDepartmentName(
                organizationId, newAbbreviationDepartmentName);
        if (count > 0) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT,
                    "The abbreviationDepartmentName already exist.");
        }

        // 更新部门名缩写
        departmentMapper.updateAbbreviationDepartmentName(departmentId, newAbbreviationDepartmentName);

        // 获取更新后的部门对象
        return getDepartment(departmentId);
    }

    /**
     * 更新部门介绍
     *
     * @permission 需要该部门属于该组织
     *
     * @errorCode InvalidParameter: 部门编号或部门介绍格式错误
     *
     * @param id 部门编号
     * @param newIntroduction 新部门介绍
     * @return 更新后的部门
     */
    @Override
    public Result<DepartmentDTO> updateIntroduction(Long id, String newIntroduction) {
        // 更新部门介绍
        departmentMapper.updateIntroduction(id, newIntroduction);

        // 获取更新后的部门对象
        return getDepartment(id);
    }

    /**
     * 更新部门 Logo
     *
     * @permission 需要该部门属于该组织
     *
     * @errorCode InvalidParameter: 更新参数格式错误
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
     * @private 内部方法
     *
     * @errorCode InvalidParameter: 部门编号格式错误
     *
     * @param id 部门编号
     * @return 增加成员数后的部门对象
     */
    @Override
    public Result<DepartmentDTO> increaseMemberNumber(Long id) {
        // 增加成员数
        departmentMapper.increaseMemberNumber(id);

        // 添加成员数后的部门对象
        return getDepartment(id);
    }

    /**
     * 减少成员数，-1
     *
     * @private 内部方法
     *
     * @errorCode InvalidParameter: 部门编号格式错误
     *
     * @param id 部门编号
     * @return 减少成员数后的部门对象
     */
    @Override
    public Result<DepartmentDTO> decreaseMemberNumber(Long id) {
        // 减少成员数
        departmentMapper.decreaseMemberNumber(id);

        // 减少成员数后的部门对象
        return getDepartment(id);
    }

}
