package com.xiaohuashifu.recruit.facade.service.manager.impl;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.facade.service.assembler.DepartmentAssembler;
import com.xiaohuashifu.recruit.facade.service.manager.DepartmentManager;
import com.xiaohuashifu.recruit.facade.service.vo.DepartmentVO;
import com.xiaohuashifu.recruit.organization.api.dto.DepartmentDTO;
import com.xiaohuashifu.recruit.organization.api.query.DepartmentQuery;
import com.xiaohuashifu.recruit.organization.api.service.DepartmentService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 描述：部门管理器
 *
 * @author xhsf
 * @create 2021/1/9 14:57
 */
@Component
@CacheConfig(cacheNames = "default")
public class DepartmentManagerImpl implements DepartmentManager {

    private final DepartmentAssembler departmentAssembler;

    @Reference
    private DepartmentService departmentService;

    public DepartmentManagerImpl(DepartmentAssembler departmentAssembler) {
        this.departmentAssembler = departmentAssembler;
    }

    @Cacheable(key = "'departments:' + #query")
    @Override
    public List<DepartmentVO> listDepartments(DepartmentQuery query) {
        Result<PageInfo<DepartmentDTO>> listDepartmentsResult = departmentService.listDepartments(query);
        PageInfo<DepartmentDTO> pageInfo = listDepartmentsResult.getData();
        List<DepartmentDTO> departmentDTOList = pageInfo.getList();
        return departmentDTOList.stream()
                .map(departmentAssembler::departmentDTOToDepartmentVO)
                .collect(Collectors.toList());
    }

    @Cacheable(key = "'organizations:' + #organizationId + ':departments'")
    @Override
    public List<DepartmentVO> listDepartmentsByOrganizationId(Long organizationId) {
//        departmentService.listDepartments()
        return null;
    }
}
