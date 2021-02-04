package com.xiaohuashifu.recruit.organization.service.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaohuashifu.recruit.common.exception.NotFoundServiceException;
import com.xiaohuashifu.recruit.common.exception.unprocessable.DuplicateServiceException;
import com.xiaohuashifu.recruit.common.exception.unprocessable.UnavailableServiceException;
import com.xiaohuashifu.recruit.common.exception.unprocessable.UnmodifiedServiceException;
import com.xiaohuashifu.recruit.common.query.QueryResult;
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
    @Transactional
    public DepartmentLabelDTO createDepartmentLabel(String labelName) {
        // 判断标签名是否已经存在
        DepartmentLabelDO departmentLabelDO = departmentLabelMapper.selectByLabelName(labelName);
        if (departmentLabelDO != null) {
            throw new DuplicateServiceException("This label name already exist.");
        }

        // 保存标签
        DepartmentLabelDO departmentLabelDOForInsert = DepartmentLabelDO.builder().labelName(labelName).build();
        departmentLabelMapper.insert(departmentLabelDOForInsert);
        return getDepartmentLabel(departmentLabelDOForInsert.getId());
    }

    @Override
    public DepartmentLabelDTO getDepartmentLabel(Long id) {
        DepartmentLabelDO departmentLabelDO = departmentLabelMapper.selectById(id);
        if (departmentLabelDO == null) {
            throw new NotFoundServiceException();
        }
        return departmentLabelAssembler.departmentLabelDOToDepartmentLabelDTO(departmentLabelDO);
    }

    @Override
    public DepartmentLabelDTO getDepartmentLabelByLabelName(String labelName) {
        DepartmentLabelDO departmentLabelDO = departmentLabelMapper.selectByLabelName(labelName);
        if (departmentLabelDO == null) {
            throw new NotFoundServiceException();
        }
        return departmentLabelAssembler.departmentLabelDOToDepartmentLabelDTO(departmentLabelDO);
    }

    @Override
    public QueryResult<DepartmentLabelDTO> listDepartmentLabels(DepartmentLabelQuery query) {
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
        return new QueryResult<>(page.getTotal(), departmentLabelDTOS);
    }

    @Override
    @Transactional
    public DisableDepartmentLabelDTO disableDepartmentLabel(Long id) {
        // 判断标签是否存在
        DepartmentLabelDTO departmentLabelDTO = getDepartmentLabel(id);

        // 判断标签是否已经被禁用
        if (!departmentLabelDTO.getAvailable()) {
            throw new UnmodifiedServiceException("The label already unavailable.");
        }

        // 禁用标签
        DepartmentLabelDO departmentLabelDOForUpdate = DepartmentLabelDO.builder().id(id).available(false).build();
        departmentLabelMapper.updateById(departmentLabelDOForUpdate);

        // 删除部门的这个标签
        int deletedNumber = departmentService.removeLabels(departmentLabelDTO.getLabelName());

        // 封装删除数量和禁用后的部门标签对象
        return new DisableDepartmentLabelDTO(getDepartmentLabel(id), deletedNumber);
    }

    @Override
    @Transactional
    public DepartmentLabelDTO enableDepartmentLabel(Long id) {
        // 判断标签是否存在
        DepartmentLabelDTO departmentLabelDTO = getDepartmentLabel(id);

        // 判断标签是否已经可用
        if (departmentLabelDTO.getAvailable()) {
            throw new UnmodifiedServiceException("The label already available.");
        }

        // 解禁标签
        DepartmentLabelDO departmentLabelDOForUpdate = DepartmentLabelDO.builder().id(id).available(true).build();
        departmentLabelMapper.updateById(departmentLabelDOForUpdate);
        return getDepartmentLabel(id);
    }

    @Override
    @Transactional
    public DepartmentLabelDTO increaseReferenceNumberOrSaveDepartmentLabel(String labelName) {
        // 判断标签是否已经存在
        DepartmentLabelDO departmentLabelDO = departmentLabelMapper.selectByLabelName(labelName);

        // 若存在且被禁用则不可用增加引用数量
        if (departmentLabelDO != null && !departmentLabelDO.getAvailable()) {
            throw new UnavailableServiceException("The department label unavailable.");
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
