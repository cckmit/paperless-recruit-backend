package com.xiaohuashifu.recruit.facade.service.manager.impl;

import com.xiaohuashifu.recruit.common.query.QueryResult;
import com.xiaohuashifu.recruit.facade.service.assembler.DepartmentLabelAssembler;
import com.xiaohuashifu.recruit.facade.service.manager.DepartmentLabelManager;
import com.xiaohuashifu.recruit.facade.service.request.CreateDepartmentLabelRequest;
import com.xiaohuashifu.recruit.facade.service.request.QueryDepartmentLabelRequest;
import com.xiaohuashifu.recruit.facade.service.request.UpdateDepartmentLabelRequest;
import com.xiaohuashifu.recruit.facade.service.vo.DepartmentLabelVO;
import com.xiaohuashifu.recruit.organization.api.dto.DepartmentLabelDTO;
import com.xiaohuashifu.recruit.organization.api.service.DepartmentLabelService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 描述：部门标签管理器
 *
 * @author xhsf
 * @create 2021/1/9 14:57
 */
@Component
@CacheConfig(cacheNames = "default")
public class DepartmentLabelManagerImpl implements DepartmentLabelManager {

    private final DepartmentLabelAssembler departmentLabelAssembler;

    @Reference
    private DepartmentLabelService departmentLabelService;

    public DepartmentLabelManagerImpl(DepartmentLabelAssembler departmentLabelAssembler) {
        this.departmentLabelAssembler = departmentLabelAssembler;
    }

    @Override
    public DepartmentLabelVO createDepartmentLabel(CreateDepartmentLabelRequest request) {
        return departmentLabelAssembler.departmentLabelDTOToDepartmentLabelVO(
                departmentLabelService.createDepartmentLabel(request.getLabelName()));
    }

    @Cacheable(key = "'departments:labels:' + #request")
    @Override
    public QueryResult<DepartmentLabelVO> listDepartmentLabels(QueryDepartmentLabelRequest request) {
        QueryResult<DepartmentLabelDTO> data = departmentLabelService.listDepartmentLabels(
                departmentLabelAssembler.queryDepartmentLabelRequestToDepartmentLabelQuery(request));
        List<DepartmentLabelVO> departmentLabelVOS = data.getResult().stream()
                .map(departmentLabelAssembler::departmentLabelDTOToDepartmentLabelVO)
                .collect(Collectors.toList());
        return new QueryResult<>(data.getTotal(), departmentLabelVOS);
    }

    @CacheEvict(key = "'departments:labels:' + #labelId", beforeInvocation = true)
    @Override
    public DepartmentLabelVO updateDepartmentLabel(Long labelId, UpdateDepartmentLabelRequest request) {
        DepartmentLabelDTO departmentLabelDTO = null;
        if (request.getAvailable() != null) {
            if (request.getAvailable()) {
                departmentLabelDTO = departmentLabelService.enableDepartmentLabel(labelId);
            } else {
                departmentLabelDTO = departmentLabelService.disableDepartmentLabel(labelId).getDepartmentLabelDTO();
            }
        }

        return departmentLabelAssembler.departmentLabelDTOToDepartmentLabelVO(departmentLabelDTO);
    }

}
