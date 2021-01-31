package com.xiaohuashifu.recruit.facade.service.manager.impl;

import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.facade.service.assembler.OrganizationLabelAssembler;
import com.xiaohuashifu.recruit.facade.service.exception.ResponseEntityException;
import com.xiaohuashifu.recruit.facade.service.manager.OrganizationLabelManager;
import com.xiaohuashifu.recruit.facade.service.request.CreateOrganizationLabelRequest;
import com.xiaohuashifu.recruit.facade.service.request.UpdateOrganizationLabelRequest;
import com.xiaohuashifu.recruit.facade.service.vo.OrganizationLabelVO;
import com.xiaohuashifu.recruit.organization.api.dto.OrganizationLabelDTO;
import com.xiaohuashifu.recruit.organization.api.service.OrganizationLabelService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.aop.framework.AopContext;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * 描述：组织标签管理器
 *
 * @author xhsf
 * @create 2021/1/9 14:57
 */
@Component
@CacheConfig(cacheNames = "default")
public class OrganizationLabelManagerImpl implements OrganizationLabelManager {

    private final OrganizationLabelAssembler organizationLabelAssembler;

    @Reference
    private OrganizationLabelService organizationLabelService;

    public OrganizationLabelManagerImpl(OrganizationLabelAssembler organizationLabelAssembler) {
        this.organizationLabelAssembler = organizationLabelAssembler;
    }

    @Override
    public OrganizationLabelVO createOrganizationLabel(CreateOrganizationLabelRequest request) {
        Result<OrganizationLabelDTO> result = organizationLabelService.saveOrganizationLabel(request.getLabelName());
        if (result.isFailure()) {
            throw new ResponseEntityException(result);
        }
        return organizationLabelAssembler.organizationLabelDTOToOrganizationLabelVO(result.getData());
    }

    @Cacheable(key = "'organizations:labels:' + #labelId")
    @Override
    public OrganizationLabelVO getOrganizationLabel(Long labelId) {
        Result<OrganizationLabelDTO> result = organizationLabelService.getOrganizationLabel(labelId);
        if (result.isFailure()) {
            throw new ResponseEntityException(result);
        }
        return organizationLabelAssembler.organizationLabelDTOToOrganizationLabelVO(result.getData());
    }

    @CacheEvict(key = "'organizations:labels:' + #labelId", beforeInvocation = true)
    @Override
    public OrganizationLabelVO updateOrganizationLabel(Long labelId, UpdateOrganizationLabelRequest request) {
        if (request.getAvailable() != null) {
            if (request.getAvailable()) {
                Result<OrganizationLabelDTO> result = organizationLabelService.enableOrganizationLabel(labelId);
                if (result.isFailure()) {
                    throw new ResponseEntityException(result);
                }
            } else {
//                Result<OrganizationLabelDTO> result = organizationLabelService.disableOrganizationLabel(labelId);
//                if (result.isFailure()) {
//                    throw new ResponseEntityException(result);
//                }
            }
        }

        return ((OrganizationLabelManager) AopContext.currentProxy()).getOrganizationLabel(labelId);
    }

}
