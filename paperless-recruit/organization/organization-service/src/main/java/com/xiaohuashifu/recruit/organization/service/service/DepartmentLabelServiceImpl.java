package com.xiaohuashifu.recruit.organization.service.service;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.recruit.common.result.ErrorCodeEnum;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.organization.api.dto.DepartmentLabelDTO;
import com.xiaohuashifu.recruit.organization.api.dto.DisableDepartmentLabelDTO;
import com.xiaohuashifu.recruit.organization.api.query.DepartmentLabelQuery;
import com.xiaohuashifu.recruit.organization.api.service.DepartmentLabelService;
import com.xiaohuashifu.recruit.organization.service.dao.DepartmentLabelMapper;
import com.xiaohuashifu.recruit.organization.service.dao.DepartmentMapper;
import com.xiaohuashifu.recruit.organization.service.do0.DepartmentLabelDO;
import org.apache.dubbo.config.annotation.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 描述：部门标签服务
 *
 * @author xhsf
 * @create 2020/12/8 18:41
 */
@Service
public class DepartmentLabelServiceImpl implements DepartmentLabelService {

    private final DepartmentLabelMapper departmentLabelMapper;

    private final DepartmentMapper departmentMapper;

    public DepartmentLabelServiceImpl(DepartmentLabelMapper departmentLabelMapper, DepartmentMapper departmentMapper) {
        this.departmentLabelMapper = departmentLabelMapper;
        this.departmentMapper = departmentMapper;
    }

    /**
     * 保存部门标签，初始引用数0
     *
     * @permission 需要管理员权限
     *
     * @errorCode InvalidParameter: 标签名格式错误
     *              OperationConflict: 标签名已经存在
     *
     * @param labelName 标签名
     * @return DepartmentLabelDTO
     */
    @Override
    public Result<DepartmentLabelDTO> saveDepartmentLabel(String labelName) {
        // 判断标签名是否已经存在
        int count = departmentLabelMapper.countByLabelName(labelName);
        if (count > 0) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT, "This label name already exist.");
        }

        // 保存标签
        DepartmentLabelDO departmentLabelDO = new DepartmentLabelDO.Builder().labelName(labelName).build();
        departmentLabelMapper.insertDepartmentLabel(departmentLabelDO);
        return getDepartmentLabel(departmentLabelDO.getId());
    }

    /**
     * 查询部门标签
     *
     * @errorCode InvalidParameter: 查询参数格式错误
     *
     * @param query 查询参数
     * @return PageInfo<DepartmentLabelDTO> 若查询不到返回空列表
     */
    @Override
    public Result<PageInfo<DepartmentLabelDTO>> listDepartmentLabels(DepartmentLabelQuery query) {
        List<DepartmentLabelDO> departmentLabelDOList = departmentLabelMapper.listDepartmentLabels(query);
        List<DepartmentLabelDTO> departmentLabelDTOList = departmentLabelDOList
                .stream()
                .map(this::departmentLabelDO2DepartmentLabelDTO)
                .collect(Collectors.toList());
        PageInfo<DepartmentLabelDTO> pageInfo = new PageInfo<>(departmentLabelDTOList);
        return Result.success(pageInfo);
    }

    /**
     * 禁用一个部门标签，会把所有拥有这个标签的部门的这个标签给删了
     *
     * @permission 需要管理员权限
     *
     * @errorCode InvalidParameter: 部门标签编号格式错误
     *              InvalidParameter.NotExist: 部门标签不存在
     *              OperationConflict: 部门标签已经被禁用
     *
     * @param id 部门标签编号
     * @return DisableDepartmentLabelDTO 禁用后的部门标签对象和被删除标签的部门数量；
     */
    @Override
    public Result<DisableDepartmentLabelDTO> disableDepartmentLabel(Long id) {
        // 判断标签是否存在
        DepartmentLabelDO departmentLabelDO = departmentLabelMapper.getDepartmentLabel(id);
        if (departmentLabelDO == null) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_EXIST, "The label does not exist.");
        }

        // 判断标签是否已经被禁用
        if (!departmentLabelDO.getAvailable()) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT, "The label already unavailable.");
        }

        // 禁用标签
        departmentLabelMapper.updateAvailable(id, false);

        // 删除部门的这个标签
        int deletedNumber = departmentMapper.deleteLabelsByLabelName(departmentLabelDO.getLabelName());

        // 封装删除数量和禁用后的部门标签对象
        DepartmentLabelDTO departmentLabelDTO = getDepartmentLabel(id).getData();
        DisableDepartmentLabelDTO disableDepartmentLabelDTO =
                new DisableDepartmentLabelDTO(departmentLabelDTO, deletedNumber);
        return Result.success(disableDepartmentLabelDTO);
    }

    /**
     * 解禁标签
     *
     * @permission 需要管理员权限
     *
     * @errorCode InvalidParameter: 部门标签编号格式错误
     *              InvalidParameter.NotExist: 部门标签不存在
     *              OperationConflict: 部门标签已经可用
     *
     * @param id 部门标签编号
     * @return 解禁后的部门标签对象
     */
    @Override
    public Result<DepartmentLabelDTO> enableDepartmentLabel(Long id) {
        // 判断标签是否存在
        DepartmentLabelDO departmentLabelDO = departmentLabelMapper.getDepartmentLabel(id);
        if (departmentLabelDO == null) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_EXIST, "The label does not exist.");
        }

        // 判断标签是否已经可用
        if (departmentLabelDO.getAvailable()) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT, "The label already available.");
        }

        // 解禁标签
        departmentLabelMapper.updateAvailable(id, true);
        return getDepartmentLabel(id);
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
    public Result<Void> isValidDepartmentLabel(String labelName) {
        // 若该标签名存在，且被禁用，则不合法
        Boolean available = departmentLabelMapper.getAvailableByLabelName(labelName);
        if (available != null && !available) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_AVAILABLE,
                    "This department label is not available.");
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
    public Result<DepartmentLabelDTO> increaseReferenceNumberOrSaveDepartmentLabel(String labelName) {
        // 判断标签是否已经存在
        DepartmentLabelDO departmentLabelDO = departmentLabelMapper.getDepartmentLabelByLabelName(labelName);

        // 若存在且被禁用则不可用增加引用数量
        if (departmentLabelDO != null && !departmentLabelDO.getAvailable()) {
            return Result.fail(ErrorCodeEnum.FORBIDDEN, "The department label unavailable.");
        }

        // 若不存在先添加标签
        if (departmentLabelDO == null) {
            departmentLabelDO = new DepartmentLabelDO.Builder().labelName(labelName).build();
            departmentLabelMapper.insertDepartmentLabel(departmentLabelDO);
        }

        // 添加标签引用数量
        departmentLabelMapper.increaseReferenceNumber(departmentLabelDO.getId());
        return getDepartmentLabel(departmentLabelDO.getId());
    }

    /**
     * 获取部门标签
     *
     * @errorCode InvalidParameter.NotFound: 该编号的部门标签不存在
     *
     * @param id 部门编号
     * @return Result<DepartmentLabelDTO>
     */
    private Result<DepartmentLabelDTO> getDepartmentLabel(Long id) {
        DepartmentLabelDO departmentLabelDO = departmentLabelMapper.getDepartmentLabel(id);
        if (departmentLabelDO == null) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_FOUND,
                    "The department does not exist.");
        }
        return Result.success(departmentLabelDO2DepartmentLabelDTO(departmentLabelDO));
    }

    /**
     * DepartmentLabelDO to DepartmentLabelDTO
     *
     * @param departmentLabelDO DepartmentLabelDO
     * @return DepartmentLabelDTO
     */
    private DepartmentLabelDTO departmentLabelDO2DepartmentLabelDTO(DepartmentLabelDO departmentLabelDO) {
        return new DepartmentLabelDTO
                .Builder()
                .id(departmentLabelDO.getId())
                .labelName(departmentLabelDO.getLabelName())
                .referenceNumber(departmentLabelDO.getReferenceNumber())
                .available(departmentLabelDO.getAvailable())
                .build();
    }

}
