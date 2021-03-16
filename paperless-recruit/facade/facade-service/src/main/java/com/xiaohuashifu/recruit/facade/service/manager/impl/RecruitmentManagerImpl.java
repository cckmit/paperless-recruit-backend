package com.xiaohuashifu.recruit.facade.service.manager.impl;

import com.xiaohuashifu.recruit.facade.service.assembler.RecruitmentAssembler;
import com.xiaohuashifu.recruit.facade.service.manager.OrganizationManager;
import com.xiaohuashifu.recruit.facade.service.manager.RecruitmentManager;
import com.xiaohuashifu.recruit.facade.service.request.CreateRecruitmentRequest;
import com.xiaohuashifu.recruit.facade.service.request.UpdateRecruitmentRequest;
import com.xiaohuashifu.recruit.facade.service.vo.RecruitmentVO;
import com.xiaohuashifu.recruit.registration.api.dto.RecruitmentDTO;
import com.xiaohuashifu.recruit.registration.api.query.RecruitmentQuery;
import com.xiaohuashifu.recruit.registration.api.service.RecruitmentService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 描述：招新管理器
 *
 * @author xhsf
 * @create 2021/1/9 14:57
 */
@Component
@CacheConfig(cacheNames = "default")
public class RecruitmentManagerImpl implements RecruitmentManager {

    private final RecruitmentAssembler recruitmentAssembler;

    private final OrganizationManager organizationManager;

    @Reference
    private RecruitmentService recruitmentService;

    public RecruitmentManagerImpl(RecruitmentAssembler recruitmentAssembler, OrganizationManager organizationManager) {
        this.recruitmentAssembler = recruitmentAssembler;
        this.organizationManager = organizationManager;
    }

    @Override
    public RecruitmentVO createRecruitment(Long organizationId, CreateRecruitmentRequest request) {
        com.xiaohuashifu.recruit.registration.api.request.CreateRecruitmentRequest createRecruitmentRequest =
                recruitmentAssembler.createRecruitmentRequestToCreateRecruitmentRequest(request);
        createRecruitmentRequest.setOrganizationId(organizationId);
        RecruitmentDTO recruitmentDTO = recruitmentService.createRecruitment(createRecruitmentRequest);
        return deepAssembler(recruitmentDTO);
    }

//    @Cacheable(key = "'departments:' + #departmentId")
    @Override
    public RecruitmentVO getRecruitment(Long id) {
        return deepAssembler(recruitmentService.getRecruitment(id));
    }

//    @Cacheable(key = "'departments:' + #query")
    @Override
    public List<RecruitmentVO> listRecruitments(RecruitmentQuery query) {
        Collection<RecruitmentDTO> recruitmentDTOS = recruitmentService.listRecruitments(query).getResult();
        return recruitmentDTOS.stream()
                .map(this::deepAssembler)
                .collect(Collectors.toList());
    }

//    @Caching(evict = {
//            @CacheEvict(key = "'departments:' + #departmentId", beforeInvocation = true)
//    })
    @Override
    public RecruitmentVO updateRecruitment(Long recruitmentId, UpdateRecruitmentRequest request) {
        com.xiaohuashifu.recruit.registration.api.request.UpdateRecruitmentRequest updateRecruitmentRequest =
                recruitmentAssembler.updateRecruitmentRequestToUpdateRecruitmentRequest(request);
        updateRecruitmentRequest.setId(recruitmentId);
        RecruitmentDTO recruitmentDTO = recruitmentService.updateRecruitment(updateRecruitmentRequest);
        return deepAssembler(recruitmentDTO);
    }

    /**
     * 深装配
     *
     * @param recruitmentDTO RecruitmentDTO
     * @return RecruitmentVO
     */
    private RecruitmentVO deepAssembler(RecruitmentDTO recruitmentDTO) {
        RecruitmentVO recruitmentVO = recruitmentAssembler.recruitmentDTOToRecruitmentVO(recruitmentDTO);
        recruitmentVO.setOrganization(organizationManager.getOrganization(recruitmentDTO.getOrganizationId()));
        return recruitmentVO;
    }

}
