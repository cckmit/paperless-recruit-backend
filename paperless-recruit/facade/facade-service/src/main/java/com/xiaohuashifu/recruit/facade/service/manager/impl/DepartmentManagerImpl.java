package com.xiaohuashifu.recruit.facade.service.manager.impl;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.facade.service.assembler.DepartmentAssembler;
import com.xiaohuashifu.recruit.facade.service.exception.ResponseEntityException;
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
        Result<DepartmentDTO> result = departmentService.createDepartment(createDepartmentRequest);
        if (result.isFailure()) {
            throw new ResponseEntityException(result);
        }
        return departmentAssembler.departmentDTOToDepartmentVO(result.getData());
    }

    @Caching(evict = {
            @CacheEvict(key = "'departments:' + #departmentId")
    })
    @Override
    public DepartmentVO addLabel(Long departmentId, DepartmentLabelPostRequest request) {
        Result<DepartmentDTO> result = departmentService.addLabel(departmentId, request.getLabel());
        if (result.isFailure()) {
            throw new ResponseEntityException(result);
        }
        return departmentAssembler.departmentDTOToDepartmentVO(result.getData());
    }

    @Caching(evict = {
            @CacheEvict(key = "'departments:' + #departmentId")
    })
    @Override
    public void removeLabel(Long departmentId, String labelName) {
        Result<DepartmentDTO> result = departmentService.removeLabel(departmentId, labelName);
        if (result.isFailure()) {
            throw new ResponseEntityException(result);
        }
    }

    @Cacheable(key = "'departments:' + #departmentId")
    @Override
    public DepartmentVO getDepartment(Long departmentId) {
        Result<DepartmentDTO> result = departmentService.getDepartment(departmentId);
        if (result.isFailure()) {
            throw new ResponseEntityException(result);
        }
        return departmentAssembler.departmentDTOToDepartmentVO(result.getData());
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

    @Caching(evict = {
            @CacheEvict(key = "'departments:' + #departmentId", beforeInvocation = true)
    })
    @Override
    public DepartmentVO updateDepartment(Long departmentId, DepartmentPatchRequest request) {
        if (request.getDepartmentName() != null) {
            Result<DepartmentDTO> result =
                    departmentService.updateDepartmentName(departmentId, request.getDepartmentName());
            if (result.isFailure()) {
                throw new ResponseEntityException(result);
            }
        }

        if (request.getAbbreviationDepartmentName() != null) {
            Result<DepartmentDTO> result = departmentService.updateAbbreviationDepartmentName(
                    departmentId, request.getAbbreviationDepartmentName());
            if (result.isFailure()) {
                throw new ResponseEntityException(result);
            }
        }

        if (request.getIntroduction() != null) {
            Result<DepartmentDTO> result =
                    departmentService.updateIntroduction(departmentId, request.getIntroduction());
            if (result.isFailure()) {
                throw new ResponseEntityException(result);
            }
        }

        if (request.getLogoUrl() != null) {
            Result<DepartmentDTO> result = departmentService.updateLogo(departmentId, request.getLogoUrl());
            if (result.isFailure()) {
                throw new ResponseEntityException(result);
            }
        }

        if (request.getDeactivated() != null) {
            Result<DepartmentDTO> result = departmentService.deactivateDepartment(departmentId);
            if (result.isFailure()) {
                throw new ResponseEntityException(result);
            }
        }

        return ((DepartmentManager) AopContext.currentProxy()).getDepartment(departmentId);
    }

}
