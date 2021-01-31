package com.xiaohuashifu.recruit.organization.service.service;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.recruit.common.result.ErrorCodeEnum;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.organization.api.dto.DisableOrganizationLabelDTO;
import com.xiaohuashifu.recruit.organization.api.dto.OrganizationLabelDTO;
import com.xiaohuashifu.recruit.organization.api.query.OrganizationLabelQuery;
import com.xiaohuashifu.recruit.organization.api.service.OrganizationLabelService;
import com.xiaohuashifu.recruit.organization.api.service.OrganizationService;
import com.xiaohuashifu.recruit.organization.service.dao.OrganizationLabelMapper;
import com.xiaohuashifu.recruit.organization.service.do0.OrganizationLabelDO;
import org.apache.dubbo.config.annotation.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 描述：组织标签服务
 *
 * @author xhsf
 * @create 2020/12/8 18:41
 */
@Service
public class OrganizationLabelServiceImpl implements OrganizationLabelService {

    private final OrganizationLabelMapper organizationLabelMapper;

    private final OrganizationService organizationService;

    public OrganizationLabelServiceImpl(OrganizationLabelMapper organizationLabelMapper,
                                        OrganizationService organizationService) {
        this.organizationLabelMapper = organizationLabelMapper;
        this.organizationService = organizationService;
    }

    /**
     * 保存组织标签，初始引用数0
     *
     * @permission 需要管理员权限
     *
     * @errorCode InvalidParameter: 标签名格式错误
     *              OperationConflict: 标签名已经存在
     *
     * @param labelName 标签名
     * @return OrganizationLabelDTO
     */
    @Override
    public Result<OrganizationLabelDTO> saveOrganizationLabel(String labelName) {
        // 判断标签名是否已经存在
        int count = organizationLabelMapper.countByLabelName(labelName);
        if (count > 0) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT, "This label name already exist.");
        }

        // 保存标签
        OrganizationLabelDO organizationLabelDO = new OrganizationLabelDO.Builder().labelName(labelName).build();
        organizationLabelMapper.insertOrganizationLabel(organizationLabelDO);
        return getOrganizationLabel(organizationLabelDO.getId());
    }

    /**
     * 获取组织标签
     *
     * @errorCode InvalidParameter: 标签名格式错误
     *              NotFound: 组织标签不存在
     *
     * @param id 组织标签编号
     * @return OrganizationLabelDTO
     */
    @Override
    public Result<OrganizationLabelDTO> getOrganizationLabel(Long id) {
        OrganizationLabelDO organizationLabelDO = organizationLabelMapper.getOrganizationLabel(id);
        if (organizationLabelDO == null) {
            return Result.fail(ErrorCodeEnum.NOT_FOUND);
        }
        return Result.success(organizationLabelDO2OrganizationLabelDTO(organizationLabelDO));
    }

    /**
     * 查询组织标签
     *
     * @errorCode InvalidParameter: 查询参数格式错误
     *
     * @param query 查询参数
     * @return PageInfo<OrganizationLabelDTO> 若查询不到返回空列表
     */
    @Override
    public Result<PageInfo<OrganizationLabelDTO>> listOrganizationLabels(OrganizationLabelQuery query) {
        List<OrganizationLabelDO> organizationLabelDOList = organizationLabelMapper.listOrganizationLabels(query);
        List<OrganizationLabelDTO> organizationLabelDTOList = organizationLabelDOList
                .stream()
                .map(this::organizationLabelDO2OrganizationLabelDTO)
                .collect(Collectors.toList());
        PageInfo<OrganizationLabelDTO> pageInfo = new PageInfo<>(organizationLabelDTOList);
        return Result.success(pageInfo);
    }

    /**
     * 禁用一个组织标签，会把所有拥有这个标签的社团的这个标签给删了
     *
     * @permission 需要管理员权限
     *
     * @errorCode InvalidParameter: 组织标签编号格式错误
     *              InvalidParameter.NotExist: 组织标签不存在
     *              OperationConflict: 组织标签已经被禁用
     *
     * @param id 社团标签编号
     * @return DisableOrganizationLabelDTO 禁用后的组织标签对象和被删除标签的社团数量
     */
    @Override
    public Result<DisableOrganizationLabelDTO> disableOrganizationLabel(Long id) {
        // 判断标签是否存在
        OrganizationLabelDO organizationLabelDO = organizationLabelMapper.getOrganizationLabel(id);
        if (organizationLabelDO == null) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_EXIST, "The label does not exist.");
        }

        // 判断标签是否已经被禁用
        if (!organizationLabelDO.getAvailable()) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT, "The label already unavailable.");
        }

        // 禁用标签
        organizationLabelMapper.updateAvailable(id, false);

        // 删除社团的这个标签
        int deletedNumber = organizationService.removeLabels(organizationLabelDO.getLabelName());

        // 封装删除数量和禁用后的组织标签对象
        OrganizationLabelDTO organizationLabelDTO = getOrganizationLabel(id).getData();
        DisableOrganizationLabelDTO disableOrganizationLabelDTO =
                new DisableOrganizationLabelDTO(organizationLabelDTO, deletedNumber);
        return Result.success(disableOrganizationLabelDTO);
    }

    /**
     * 解禁标签
     *
     * @permission 需要管理员权限
     *
     * @errorCode InvalidParameter: 组织标签编号格式错误
     *              InvalidParameter.NotExist: 组织标签不存在
     *              OperationConflict: 组织标签已经可用
     *
     * @param id 社团标签编号
     * @return 解禁后的组织标签对象
     */
    @Override
    public Result<OrganizationLabelDTO> enableOrganizationLabel(Long id) {
        // 判断标签是否存在
        OrganizationLabelDO organizationLabelDO = organizationLabelMapper.getOrganizationLabel(id);
        if (organizationLabelDO == null) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_EXIST, "The label does not exist.");
        }

        // 判断标签是否已经可用
        if (organizationLabelDO.getAvailable()) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT, "The label already available.");
        }

        // 解禁标签
        organizationLabelMapper.updateAvailable(id, true);
        return getOrganizationLabel(id);
    }

    /**
     * 判断一个标签是否合法
     *
     * @errorCode InvalidParameter: 标签名格式错误
     *              InvalidParameter.NotAvailable: 该标签名已经被禁用
     *
     * @param labelName 标签名
     * @return 标签是否合法
     */
    @Override
    public Result<Void> isValidOrganizationLabel(String labelName) {
        // 若该标签名存在，且被禁用，则不合法
        Boolean available = organizationLabelMapper.getAvailableByLabelName(labelName);
        if (available != null && !available) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_AVAILABLE,
                    "This organization label is not available.");
        }

        // 合法
        return Result.success();
    }

    /**
     * 增加标签引用数量，若标签不存在则保存标签，初始引用数1
     *
     * @private 内部方法
     *
     * @errorCode InvalidParameter: 标签名格式错误
     *              Forbidden: 该标签已经被禁用，不可用增加引用
     *
     * @param labelName 标签名
     * @return 操作是否成功
     */
    @Override
    public Result<OrganizationLabelDTO> increaseReferenceNumberOrSaveOrganizationLabel(String labelName) {
        // 判断标签是否已经存在
        OrganizationLabelDO organizationLabelDO = organizationLabelMapper.getOrganizationLabelByLabelName(labelName);

        // 若存在且被禁用则不可用增加引用数量
        if (organizationLabelDO != null && !organizationLabelDO.getAvailable()) {
            return Result.fail(ErrorCodeEnum.FORBIDDEN, "The organization label unavailable.");
        }

        // 若不存在先添加标签
        if (organizationLabelDO == null) {
            organizationLabelDO = new OrganizationLabelDO.Builder().labelName(labelName).build();
            organizationLabelMapper.insertOrganizationLabel(organizationLabelDO);
        }

        // 添加标签引用数量
        organizationLabelMapper.increaseReferenceNumber(organizationLabelDO.getId());
        return getOrganizationLabel(organizationLabelDO.getId());
    }

    /**
     * OrganizationLabelDO to OrganizationLabelDTO
     *
     * @param organizationLabelDO OrganizationLabelDO
     * @return OrganizationLabelDTO
     */
    private OrganizationLabelDTO organizationLabelDO2OrganizationLabelDTO(OrganizationLabelDO organizationLabelDO) {
        return new OrganizationLabelDTO
                .Builder()
                .id(organizationLabelDO.getId())
                .labelName(organizationLabelDO.getLabelName())
                .referenceNumber(organizationLabelDO.getReferenceNumber())
                .available(organizationLabelDO.getAvailable())
                .build();
    }

}
