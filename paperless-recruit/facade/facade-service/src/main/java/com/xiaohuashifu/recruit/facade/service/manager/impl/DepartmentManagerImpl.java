package com.xiaohuashifu.recruit.facade.service.manager.impl;

import com.xiaohuashifu.recruit.facade.service.assembler.DepartmentAssembler;
import com.xiaohuashifu.recruit.facade.service.manager.DepartmentManager;
import com.xiaohuashifu.recruit.facade.service.request.DepartmentLabelPostRequest;
import com.xiaohuashifu.recruit.facade.service.request.DepartmentPatchRequest;
import com.xiaohuashifu.recruit.facade.service.request.DepartmentPostRequest;
import com.xiaohuashifu.recruit.facade.service.vo.DepartmentVO;
import com.xiaohuashifu.recruit.organization.api.dto.DepartmentDTO;
import com.xiaohuashifu.recruit.organization.api.query.DepartmentQuery;
import com.xiaohuashifu.recruit.organization.api.request.CreateDepartmentRequest;
import com.xiaohuashifu.recruit.organization.api.service.DepartmentService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.aop.framework.AopContext;
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
    public DepartmentVO createDepartment(Long organizationId, DepartmentPostRequest request) {
        CreateDepartmentRequest createDepartmentRequest =
                departmentAssembler.departmentPostRequestToCreateDepartmentRequest(request);
        createDepartmentRequest.setOrganizationId(organizationId);
        return departmentAssembler.departmentDTOToDepartmentVO(departmentService.createDepartment(createDepartmentRequest));
    }

    @Caching(evict = {
            @CacheEvict(key = "'departments:' + #departmentId")
    })
    @Override
    public DepartmentVO addLabel(Long departmentId, DepartmentLabelPostRequest request) {
        return departmentAssembler.departmentDTOToDepartmentVO(departmentService.addLabel(departmentId, request.getLabel()));
    }

    @Caching(evict = {
            @CacheEvict(key = "'departments:' + #departmentId")
    })
    @Override
    public void removeLabel(Long departmentId, String labelName) {
        departmentService.removeLabel(departmentId, labelName);
    }

    @Cacheable(key = "'departments:' + #departmentId")
    @Override
    public DepartmentVO getDepartment(Long departmentId) {
        return departmentAssembler.departmentDTOToDepartmentVO(departmentService.getDepartment(departmentId));
    }

    @Cacheable(key = "'departments:' + #query")
    @Override
    public List<DepartmentVO> listDepartments(DepartmentQuery query) {
        Collection<DepartmentDTO> departmentDTOList = departmentService.listDepartments(query).getResult();
        return departmentDTOList.stream()
                .map(departmentAssembler::departmentDTOToDepartmentVO)
                .collect(Collectors.toList());
    }

    @Caching(evict = {
            @CacheEvict(key = "'departments:' + #departmentId", beforeInvocation = true)
    })
    @Override
    public DepartmentVO updateDepartment(Long departmentId, DepartmentPatchRequest request) {
        if (request.getDepartmentName() != null) {
            departmentService.updateDepartmentName(departmentId, request.getDepartmentName());
        }

        if (request.getAbbreviationDepartmentName() != null) {
            departmentService.updateAbbreviationDepartmentName(
                    departmentId, request.getAbbreviationDepartmentName());
        }

        if (request.getIntroduction() != null) {
            departmentService.updateIntroduction(departmentId, request.getIntroduction());
        }

        if (request.getLogoUrl() != null) {
            departmentService.updateLogo(departmentId, request.getLogoUrl());
        }

        if (request.getDeactivated() != null) {
            departmentService.deactivateDepartment(departmentId);
        }

        return ((DepartmentManager) AopContext.currentProxy()).getDepartment(departmentId);
    }

}
