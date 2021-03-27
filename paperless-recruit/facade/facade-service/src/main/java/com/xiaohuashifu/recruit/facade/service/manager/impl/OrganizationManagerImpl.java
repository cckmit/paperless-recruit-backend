package com.xiaohuashifu.recruit.facade.service.manager.impl;

import com.xiaohuashifu.recruit.common.query.QueryResult;
import com.xiaohuashifu.recruit.facade.service.assembler.OrganizationAssembler;
import com.xiaohuashifu.recruit.facade.service.assembler.OrganizationTypeAssembler;
import com.xiaohuashifu.recruit.facade.service.manager.OrganizationManager;
import com.xiaohuashifu.recruit.facade.service.request.CreateOrganizationCoreMemberRequest;
import com.xiaohuashifu.recruit.facade.service.request.UpdateOrganizationCoreMemberRequest;
import com.xiaohuashifu.recruit.facade.service.request.UpdateOrganizationRequest;
import com.xiaohuashifu.recruit.facade.service.vo.OrganizationCoreMemberVO;
import com.xiaohuashifu.recruit.facade.service.vo.OrganizationTypeVO;
import com.xiaohuashifu.recruit.facade.service.vo.OrganizationVO;
import com.xiaohuashifu.recruit.organization.api.constant.OrganizationConstants;
import com.xiaohuashifu.recruit.organization.api.dto.OrganizationCoreMemberDTO;
import com.xiaohuashifu.recruit.organization.api.dto.OrganizationDTO;
import com.xiaohuashifu.recruit.organization.api.dto.OrganizationTypeDTO;
import com.xiaohuashifu.recruit.organization.api.query.OrganizationQuery;
import com.xiaohuashifu.recruit.organization.api.query.OrganizationTypeQuery;
import com.xiaohuashifu.recruit.organization.api.request.CreateOrganizationRequest;
import com.xiaohuashifu.recruit.organization.api.service.OrganizationCoreMemberService;
import com.xiaohuashifu.recruit.organization.api.service.OrganizationService;
import com.xiaohuashifu.recruit.organization.api.service.OrganizationTypeService;
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
 * 描述：组织管理器
 *
 * @author xhsf
 * @create 2021/1/9 14:57
 */
@Component
@CacheConfig(cacheNames = "default")
public class OrganizationManagerImpl implements OrganizationManager {

    private final OrganizationAssembler organizationAssembler;

    private final OrganizationTypeAssembler organizationTypeAssembler;

    @Reference
    private OrganizationService organizationService;

    @Reference
    private OrganizationCoreMemberService organizationCoreMemberService;

    @Reference
    private OrganizationTypeService organizationTypeService;

    public OrganizationManagerImpl(OrganizationAssembler organizationAssembler,
                                   OrganizationTypeAssembler organizationTypeAssembler) {
        this.organizationAssembler = organizationAssembler;
        this.organizationTypeAssembler = organizationTypeAssembler;
    }

    @Override
    public void sendEmailAuthCodeForCreateOrganization(String email) {
        organizationService.sendEmailAuthCodeForCreateOrganization(email);
    }

    @Override
    public OrganizationVO createOrganization(CreateOrganizationRequest request) {
        OrganizationDTO organizationDTO = organizationService.createOrganization(request);
        return organizationAssembler.organizationDTOToOrganizationVO(organizationDTO);
    }

    @CacheEvict(key = "'organizations:' + #organizationId + ':core-members'")
    @Override
    public OrganizationCoreMemberVO createOrganizationCoreMember(Long organizationId,
                                                                 CreateOrganizationCoreMemberRequest request) {
        com.xiaohuashifu.recruit.organization.api.request.CreateOrganizationCoreMemberRequest
                createOrganizationCoreMemberRequest =
                organizationAssembler.createOrganizationCoreMemberRequestToCreateOrganizationCoreMemberRequest(request);
        createOrganizationCoreMemberRequest.setOrganizationId(organizationId);
        OrganizationCoreMemberDTO organizationCoreMemberDTO =
                organizationCoreMemberService.createOrganizationCoreMember(createOrganizationCoreMemberRequest);
        return organizationAssembler.organizationCoreMemberDTOToOrganizationCoreMemberVO(organizationCoreMemberDTO);
    }

    @Caching(evict = {
            @CacheEvict(key = "'organizations:' + #organizationId + ':core-members'"),
            @CacheEvict(key = "'organizations:core-members:' + #organizationCoreMemberId")
    })
    @Override
    public void removeOrganizationCoreMember(Long organizationId, Long organizationCoreMemberId) {
        organizationCoreMemberService.removeOrganizationCoreMember(organizationCoreMemberId);
    }

    @Cacheable(key = "'organizations:' + #organizationId")
    @Override
    public OrganizationVO getOrganization(Long organizationId) {
        return organizationAssembler.organizationDTOToOrganizationVO(organizationService.getOrganization(organizationId));
    }

    @Cacheable(key = "'user:' +  #userId + ':organization'")
    @Override
    public OrganizationVO getOrganizationByUserId(Long userId) {
        return organizationAssembler.organizationDTOToOrganizationVO(organizationService.getOrganizationByUserId(userId));
    }

    @Cacheable(key = "'organizations:core-members:' + #organizationCoreMemberId")
    @Override
    public OrganizationCoreMemberVO getOrganizationCoreMember(Long organizationCoreMemberId) {
        OrganizationCoreMemberDTO organizationCoreMemberDTO =
                organizationCoreMemberService.getOrganizationCoreMember(organizationCoreMemberId);
        return organizationAssembler.organizationCoreMemberDTOToOrganizationCoreMemberVO(organizationCoreMemberDTO);
    }

    @Cacheable(key = "'organizations:' + #query")
    @Override
    public QueryResult<OrganizationVO> listOrganizations(OrganizationQuery query) {
        QueryResult<OrganizationDTO> queryResult = organizationService.listOrganizations(query);
        List<OrganizationVO> organizationVOS = queryResult.getResult().stream()
                .map(organizationAssembler::organizationDTOToOrganizationVO)
                .collect(Collectors.toList());
        return new QueryResult<>(queryResult.getTotal(), organizationVOS);
    }

    @Cacheable(key = "'organization:types' + #query")
    @Override
    public QueryResult<OrganizationTypeVO> listOrganizationTypes(OrganizationTypeQuery query) {
        QueryResult<OrganizationTypeDTO> queryResult = organizationTypeService.listOrganizationTypes(query);
        List<OrganizationTypeVO> organizationTypeVOS = queryResult.getResult().stream()
                .map(organizationTypeAssembler::organizationTypeDTOToOrganizationTypeVO)
                .collect(Collectors.toList());
        return new QueryResult<>(queryResult.getTotal(), organizationTypeVOS);
    }

//    @Cacheable(key = "'organization:sizes'")
    @Override
    public List<String> listOrganizationSizes() {
        return OrganizationConstants.ORGANIZATION_SIZE_LIST;
    }

    @Cacheable(key = "'organizations:' + #organizationId + ':core-members'")
    @Override
    public List<OrganizationCoreMemberVO> listOrganizationCoreMembersByOrganizationId(Long organizationId) {
        return organizationCoreMemberService.listOrganizationCoreMembersByOrganizationId(organizationId)
                .stream()
                .map(organizationAssembler::organizationCoreMemberDTOToOrganizationCoreMemberVO)
                .collect(Collectors.toList());
    }

    @Caching(evict = {
            @CacheEvict(key = "'organizations:' + #id"),
            @CacheEvict(key = "'user:' +  #result.userId + ':organization'")
    })
    @Override
    public OrganizationVO updateOrganization(Long id, UpdateOrganizationRequest request) {
        com.xiaohuashifu.recruit.organization.api.request.UpdateOrganizationRequest updateOrganizationRequest =
                organizationAssembler.updateOrganizationRequestToUpdateOrganizationRequest(request);
        updateOrganizationRequest.setId(id);
        organizationService.updateOrganization(updateOrganizationRequest);

        return ((OrganizationManagerImpl) AopContext.currentProxy()).getOrganization(id);
    }

    @Caching(evict = {
            @CacheEvict(key = "'organizations:' + #organizationId + ':core-members'"),
            @CacheEvict(key = "'organizations:core-members:' + #organizationCoreMemberId")
    })
    @Override
    public OrganizationCoreMemberVO updateOrganizationCoreMember(Long organizationId, Long organizationCoreMemberId,
                                                                 UpdateOrganizationCoreMemberRequest request) {
        com.xiaohuashifu.recruit.organization.api.request.UpdateOrganizationCoreMemberRequest
                updateOrganizationCoreMemberRequest =
                organizationAssembler.updateOrganizationCoreMemberRequestToUpdateOrganizationCoreMemberRequest(request);
        updateOrganizationCoreMemberRequest.setId(organizationCoreMemberId);
        OrganizationCoreMemberDTO organizationCoreMemberDTO =
                organizationCoreMemberService.updateOrganizationCoreMember(updateOrganizationCoreMemberRequest);
        return organizationAssembler.organizationCoreMemberDTOToOrganizationCoreMemberVO(organizationCoreMemberDTO);
    }
}
