package com.xiaohuashifu.recruit.facade.service.manager.impl;

import com.xiaohuashifu.recruit.facade.service.assembler.DepartmentAssembler;
import com.xiaohuashifu.recruit.facade.service.manager.DepartmentManager;
import com.xiaohuashifu.recruit.facade.service.request.CreateDepartmentRequest;
import com.xiaohuashifu.recruit.facade.service.request.UpdateDepartmentRequest;
import com.xiaohuashifu.recruit.facade.service.vo.DepartmentVO;
import com.xiaohuashifu.recruit.organization.api.dto.DepartmentDTO;
import com.xiaohuashifu.recruit.organization.api.query.DepartmentQuery;
import com.xiaohuashifu.recruit.organization.api.service.DepartmentService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import java.util.Collection;
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

    @Override
    public DepartmentVO createDepartment(Long organizationId, CreateDepartmentRequest request) {
        com.xiaohuashifu.recruit.organization.api.request.CreateDepartmentRequest createDepartmentRequest =
                departmentAssembler.createDepartmentRequestToCreateDepartmentRequest(request);
        createDepartmentRequest.setOrganizationId(organizationId);
        DepartmentDTO departmentDTO = departmentService.createDepartment(createDepartmentRequest);
        return departmentAssembler.departmentDTOToDepartmentVO(departmentDTO);
    }

    @Override
    public void removeDepartment(Long departmentId) {
        departmentService.removeDepartment(departmentId);
    }

//    @Cacheable(key = "'departments:' + #departmentId")
    @Override
    public DepartmentVO getDepartment(Long departmentId) {
        return departmentAssembler.departmentDTOToDepartmentVO(departmentService.getDepartment(departmentId));
    }

//    @Cacheable(key = "'departments:' + #query")
    @Override
    public List<DepartmentVO> listDepartments(DepartmentQuery query) {
        Collection<DepartmentDTO> departmentDTOList = departmentService.listDepartments(query).getResult();
        return departmentDTOList.stream()
                .map(departmentAssembler::departmentDTOToDepartmentVO)
                .collect(Collectors.toList());
    }

//    @Caching(evict = {
//            @CacheEvict(key = "'departments:' + #departmentId", beforeInvocation = true)
//    })
    @Override
    public DepartmentVO updateDepartment(Long departmentId, UpdateDepartmentRequest request) {
        com.xiaohuashifu.recruit.organization.api.request.UpdateDepartmentRequest updateDepartmentRequest =
                departmentAssembler.updateDepartmentRequestToUpdateDepartmentRequest(request);
        updateDepartmentRequest.setId(departmentId);
        DepartmentDTO departmentDTO = departmentService.updateDepartment(updateDepartmentRequest);
        return departmentAssembler.departmentDTOToDepartmentVO(departmentDTO);
    }

}
