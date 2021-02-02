package com.xiaohuashifu.recruit.organization.service.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaohuashifu.recruit.common.query.QueryResult;
import com.xiaohuashifu.recruit.common.result.ErrorCodeEnum;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.organization.api.dto.DepartmentLabelDTO;
import com.xiaohuashifu.recruit.organization.api.dto.DisableDepartmentLabelDTO;
import com.xiaohuashifu.recruit.organization.api.query.DepartmentLabelQuery;
import com.xiaohuashifu.recruit.organization.api.service.DepartmentLabelService;
import com.xiaohuashifu.recruit.organization.api.service.DepartmentService;
import com.xiaohuashifu.recruit.organization.service.assembler.DepartmentLabelAssembler;
import com.xiaohuashifu.recruit.organization.service.dao.DepartmentLabelMapper;
import com.xiaohuashifu.recruit.organization.service.do0.DepartmentLabelDO;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 描述：部门标签服务
 *
 * @author xhsf
 * @create 2020/12/8 18:41
 */
@Service
@Slf4j
public class DepartmentLabelServiceImpl implements DepartmentLabelService {

    @Reference
    private DepartmentService departmentService;

    private final DepartmentLabelMapper departmentLabelMapper;

    private final DepartmentLabelAssembler departmentLabelAssembler;

    public DepartmentLabelServiceImpl(DepartmentLabelMapper departmentLabelMapper,
                                      DepartmentLabelAssembler departmentLabelAssembler) {
        this.departmentLabelMapper = departmentLabelMapper;
        this.departmentLabelAssembler = departmentLabelAssembler;
    }

    @Override
    public Result<DepartmentLabelDTO> createDepartmentLabel(String labelName) {
        // 判断标签名是否已经存在
        DepartmentLabelDO departmentLabelDO = departmentLabelMapper.selectByLabelName(labelName);
        if (departmentLabelDO != null) {
            return Result.fail(ErrorCodeEnum.UNPROCESSABLE_ENTITY_EXIST, "This label name already exist.");
        }

        // 保存标签
        DepartmentLabelDO departmentLabelDOForInsert = DepartmentLabelDO.builder().labelName(labelName).build();
        departmentLabelMapper.insert(departmentLabelDOForInsert);
        return getDepartmentLabel(departmentLabelDOForInsert.getId());
    }

    @Override
    public Result<DepartmentLabelDTO> getDepartmentLabel(Long id) {
        DepartmentLabelDO departmentLabelDO = departmentLabelMapper.selectById(id);
        if (departmentLabelDO == null) {
            return Result.fail(ErrorCodeEnum.NOT_FOUND);
        }
        return Result.success(departmentLabelAssembler.departmentLabelDOToDepartmentLabelDTO(departmentLabelDO));
    }

    @Override
    public Result<DepartmentLabelDTO> getDepartmentLabelByLabelName(String labelName) {
        DepartmentLabelDO departmentLabelDO = departmentLabelMapper.selectByLabelName(labelName);
        if (departmentLabelDO == null) {
            return Result.fail(ErrorCodeEnum.NOT_FOUND);
        }
        return Result.success(departmentLabelAssembler.departmentLabelDOToDepartmentLabelDTO(departmentLabelDO));
    }

    @Override
    public Result<QueryResult<DepartmentLabelDTO>> listDepartmentLabels(DepartmentLabelQuery query) {
        LambdaQueryWrapper<DepartmentLabelDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(query.getAvailable() != null, DepartmentLabelDO::getAvailable, query.getAvailable())
                .likeRight(query.getLabelName() != null, DepartmentLabelDO::getLabelName,
                        query.getLabelName());
        Page<DepartmentLabelDO> page = new Page<>(query.getPageNum(), query.getPageSize(), true);
        departmentLabelMapper.selectPage(page, wrapper);
        List<DepartmentLabelDTO> departmentLabelDTOS = page.getRecords()
                .stream()
                .map(departmentLabelAssembler::departmentLabelDOToDepartmentLabelDTO)
                .collect(Collectors.toList());
        QueryResult<DepartmentLabelDTO> queryResult = new QueryResult<>(page.getTotal(), departmentLabelDTOS);
        return Result.success(queryResult);
    }

    @Override
    @Transactional
    public Result<DisableDepartmentLabelDTO> disableDepartmentLabel(Long id) {
        // 判断标签是否存在
        DepartmentLabelDO departmentLabelDO = departmentLabelMapper.selectById(id);
        if (departmentLabelDO == null) {
            return Result.fail(ErrorCodeEnum.UNPROCESSABLE_ENTITY_NOT_EXIST,
                    "The label does not exist.");
        }

        // 判断标签是否已经被禁用
        if (!departmentLabelDO.getAvailable()) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT, "The label already unavailable.");
        }

        // 禁用标签
        DepartmentLabelDO departmentLabelDOForUpdate = DepartmentLabelDO.builder()
                .id(id)
                .available(false)
                .build();
        departmentLabelMapper.updateById(departmentLabelDOForUpdate);

        // 删除部门的这个标签
        int deletedNumber = departmentService.removeLabels(departmentLabelDO.getLabelName());

        // 封装删除数量和禁用后的部门标签对象
        DepartmentLabelDTO departmentLabelDTO = getDepartmentLabel(id).getData();
        DisableDepartmentLabelDTO disableDepartmentLabelDTO =
                new DisableDepartmentLabelDTO(departmentLabelDTO, deletedNumber);
        return Result.success(disableDepartmentLabelDTO);
    }

    @Override
    @Transactional
    public Result<DepartmentLabelDTO> enableDepartmentLabel(Long id) {
        // 判断标签是否存在
        DepartmentLabelDO departmentLabelDO = departmentLabelMapper.selectById(id);
        if (departmentLabelDO == null) {
            return Result.fail(ErrorCodeEnum.UNPROCESSABLE_ENTITY_NOT_EXIST,
                    "The label does not exist.");
        }

        // 判断标签是否已经可用
        if (departmentLabelDO.getAvailable()) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT, "The label already available.");
        }

        // 解禁标签
        DepartmentLabelDO departmentLabelDOForUpdate = DepartmentLabelDO.builder()
                .id(id)
                .available(true)
                .build();
        departmentLabelMapper.updateById(departmentLabelDOForUpdate);
        return getDepartmentLabel(id);
    }

    @Override
    public Result<DepartmentLabelDTO> increaseReferenceNumberOrSaveDepartmentLabel(String labelName) {
        // 判断标签是否已经存在
        DepartmentLabelDO departmentLabelDO = departmentLabelMapper.selectByLabelName(labelName);

        // 若存在且被禁用则不可用增加引用数量
        if (departmentLabelDO != null && !departmentLabelDO.getAvailable()) {
            return Result.fail(ErrorCodeEnum.UNPROCESSABLE_ENTITY_UNAVAILABLE,
                    "The department label unavailable.");
        }

        // 若不存在先添加标签
        if (departmentLabelDO == null) {
            departmentLabelDO = DepartmentLabelDO.builder().labelName(labelName).build();
            departmentLabelMapper.insert(departmentLabelDO);
        }

        // 添加标签引用数量
        departmentLabelMapper.increaseReferenceNumber(departmentLabelDO.getId());
        return getDepartmentLabel(departmentLabelDO.getId());
    }

}
