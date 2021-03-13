package com.xiaohuashifu.recruit.organization.service.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaohuashifu.recruit.common.exception.NotFoundServiceException;
import com.xiaohuashifu.recruit.common.exception.unprocessable.OverLimitServiceException;
import com.xiaohuashifu.recruit.common.util.ObjectUtils;
import com.xiaohuashifu.recruit.organization.api.constant.OrganizationCoreMemberConstants;
import com.xiaohuashifu.recruit.organization.api.dto.OrganizationCoreMemberDTO;
import com.xiaohuashifu.recruit.organization.api.request.CreateOrganizationCoreMemberRequest;
import com.xiaohuashifu.recruit.organization.api.request.UpdateOrganizationCoreMemberRequest;
import com.xiaohuashifu.recruit.organization.api.service.OrganizationCoreMemberService;
import com.xiaohuashifu.recruit.organization.api.service.OrganizationService;
import com.xiaohuashifu.recruit.organization.service.assembler.OrganizationCoreMemberAssembler;
import com.xiaohuashifu.recruit.organization.service.dao.OrganizationCoreMemberMapper;
import com.xiaohuashifu.recruit.organization.service.do0.OrganizationCoreMemberDO;
import com.xiaohuashifu.recruit.oss.api.service.ObjectStorageService;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 描述：组织核心成员服务实现
 *
 * @author xhsf
 * @create 2020/12/20 16:52
 */
@Service
public class OrganizationCoreMemberServiceImpl implements OrganizationCoreMemberService {

    private final OrganizationCoreMemberMapper organizationCoreMemberMapper;

    private final OrganizationCoreMemberAssembler organizationCoreMemberAssembler;

    @Reference
    private ObjectStorageService objectStorageService;

    @Reference
    private OrganizationService organizationService;

    public OrganizationCoreMemberServiceImpl(OrganizationCoreMemberMapper organizationCoreMemberMapper,
                                             OrganizationCoreMemberAssembler organizationCoreMemberAssembler) {
        this.organizationCoreMemberMapper = organizationCoreMemberMapper;
        this.organizationCoreMemberAssembler = organizationCoreMemberAssembler;
    }

    @Override
    public OrganizationCoreMemberDTO createOrganizationCoreMember(CreateOrganizationCoreMemberRequest request) {
        // 判断组织是否存在
        organizationService.getOrganization(request.getOrganizationId());

        // 判断该组织的成员数是否大于 MAX_ORGANIZATION_CORE_MEMBER_NUMBER
        LambdaQueryWrapper<OrganizationCoreMemberDO> wrapperForCount = new LambdaQueryWrapper<>();
        wrapperForCount.eq(OrganizationCoreMemberDO::getOrganizationId, request.getOrganizationId());
        int count = organizationCoreMemberMapper.selectCount(wrapperForCount);
        if (count > OrganizationCoreMemberConstants.MAX_ORGANIZATION_CORE_MEMBER_NUMBER) {
            throw new OverLimitServiceException("The number of organization core member must not be greater than "
                    + OrganizationCoreMemberConstants.MAX_ORGANIZATION_CORE_MEMBER_NUMBER + ".");
        }

        // 链接头像
        objectStorageService.linkObject(request.getAvatarUrl());

        // 插入组织核心成员
        OrganizationCoreMemberDO organizationCoreMemberDOForInsert =
                organizationCoreMemberAssembler.createOrganizationCoreMemberRequestToOrganizationCoreMemberDO(request);
        organizationCoreMemberMapper.insert(organizationCoreMemberDOForInsert);

        return getOrganizationCoreMember(organizationCoreMemberDOForInsert.getId());
    }

    @Override
    public void removeOrganizationCoreMember(Long id) {
        // 删除该核心成员
        organizationCoreMemberMapper.deleteById(id);
    }

    @Override
    public OrganizationCoreMemberDTO getOrganizationCoreMember(Long id) {
        OrganizationCoreMemberDO organizationCoreMemberDO = organizationCoreMemberMapper.selectById(id);
        if (organizationCoreMemberDO == null) {
            throw new NotFoundServiceException("organizationCoreMember", "id", id);
        }
        return organizationCoreMemberAssembler.organizationCoreMemberDOToOrganizationCoreMemberDTO(
                organizationCoreMemberDO);
    }

    @Override
    public List<OrganizationCoreMemberDTO> listOrganizationCoreMembersByOrganizationId(Long organizationId) {
        LambdaQueryWrapper<OrganizationCoreMemberDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrganizationCoreMemberDO::getOrganizationId, organizationId)
                .orderByAsc(OrganizationCoreMemberDO::getPriority)
                .orderByAsc(OrganizationCoreMemberDO::getCreateTime);

        List<OrganizationCoreMemberDO> organizationCoreMemberDOS =
                organizationCoreMemberMapper.selectList(wrapper);
        return organizationCoreMemberDOS.stream()
                .map(organizationCoreMemberAssembler::organizationCoreMemberDOToOrganizationCoreMemberDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OrganizationCoreMemberDTO updateOrganizationCoreMember(UpdateOrganizationCoreMemberRequest request) {
        // 判断组织核心成员是否存在
        getOrganizationCoreMember(request.getId());

        // 更新组织
        OrganizationCoreMemberDO organizationCoreMemberDOForUpdate = checkForUpdate(request);
        organizationCoreMemberMapper.updateById(organizationCoreMemberDOForUpdate);
        return getOrganizationCoreMember(request.getId());
    }

    /**
     * 检查更新参数
     *
     * @param request UpdateOrganizationCoreMemberRequest
     * @return OrganizationCoreMemberDO
     */
    private OrganizationCoreMemberDO checkForUpdate(UpdateOrganizationCoreMemberRequest request) {
        // 转换成 DO 对象
        OrganizationCoreMemberDO organizationCoreMemberDO =
                organizationCoreMemberAssembler.updateOrganizationCoreMemberRequestToOrganizationCoreMemberDO(request);
        ObjectUtils.trimAllStringFields(organizationCoreMemberDO);

        // 链接 logo
        if (organizationCoreMemberDO.getAvatarUrl() != null) {
            objectStorageService.linkObject(organizationCoreMemberDO.getAvatarUrl());
        }

        return organizationCoreMemberDO;
    }
}
