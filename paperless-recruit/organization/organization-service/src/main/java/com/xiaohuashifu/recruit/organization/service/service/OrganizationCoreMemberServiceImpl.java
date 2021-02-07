package com.xiaohuashifu.recruit.organization.service.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaohuashifu.recruit.common.exception.NotFoundServiceException;
import com.xiaohuashifu.recruit.common.exception.unprocessable.DuplicateServiceException;
import com.xiaohuashifu.recruit.common.exception.unprocessable.InvalidValueServiceException;
import com.xiaohuashifu.recruit.common.exception.unprocessable.OverLimitServiceException;
import com.xiaohuashifu.recruit.organization.api.constant.OrganizationCoreMemberConstants;
import com.xiaohuashifu.recruit.organization.api.dto.OrganizationCoreMemberDTO;
import com.xiaohuashifu.recruit.organization.api.dto.OrganizationMemberDTO;
import com.xiaohuashifu.recruit.organization.api.service.OrganizationCoreMemberService;
import com.xiaohuashifu.recruit.organization.api.service.OrganizationMemberService;
import com.xiaohuashifu.recruit.organization.api.service.OrganizationService;
import com.xiaohuashifu.recruit.organization.service.assembler.OrganizationCoreMemberAssembler;
import com.xiaohuashifu.recruit.organization.service.dao.OrganizationCoreMemberMapper;
import com.xiaohuashifu.recruit.organization.service.do0.OrganizationCoreMemberDO;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
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
    private OrganizationMemberService organizationMemberService;

    @Reference
    private OrganizationService organizationService;

    public OrganizationCoreMemberServiceImpl(OrganizationCoreMemberMapper organizationCoreMemberMapper,
                                             OrganizationCoreMemberAssembler organizationCoreMemberAssembler) {
        this.organizationCoreMemberMapper = organizationCoreMemberMapper;
        this.organizationCoreMemberAssembler = organizationCoreMemberAssembler;
    }

    @Override
    @Transactional
    public OrganizationCoreMemberDTO createOrganizationCoreMember(Long organizationId, Long organizationMemberId) {
        // 判断组织成员是否存在
        OrganizationMemberDTO organizationMemberDTO =
                organizationMemberService.getOrganizationMember(organizationMemberId);

        // 检查组织状态
        organizationService.getOrganization(organizationId);

        // 判断该组织成员是否属于该组织的
        if (!Objects.equals(organizationMemberDTO.getOrganizationId(), organizationId)) {
            throw new InvalidValueServiceException("The organization member not belong to organization.");
        }

        // 判断该成员是否已经存在
        LambdaQueryWrapper<OrganizationCoreMemberDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrganizationCoreMemberDO::getOrganizationId, organizationId)
                .eq(OrganizationCoreMemberDO::getOrganizationMemberId, organizationMemberId);
        int count = organizationCoreMemberMapper.selectCount(wrapper);
        if (count > 0) {
            throw new DuplicateServiceException("The organization core member already exist.");
        }

        // 插入组织核心成员
        OrganizationCoreMemberDO organizationCoreMemberDOForInsert = OrganizationCoreMemberDO.builder()
                .organizationId(organizationId).organizationMemberId(organizationMemberId).build();
        organizationCoreMemberMapper.insert(organizationCoreMemberDOForInsert);

        // 判断该组织的成员数是否大于 MAX_ORGANIZATION_CORE_MEMBER_NUMBER
        LambdaQueryWrapper<OrganizationCoreMemberDO> wrapperForCount = new LambdaQueryWrapper<>();
        wrapperForCount.eq(OrganizationCoreMemberDO::getOrganizationId, organizationId);
        count = organizationCoreMemberMapper.selectCount(wrapperForCount);
        if (count > OrganizationCoreMemberConstants.MAX_ORGANIZATION_CORE_MEMBER_NUMBER) {
            throw new OverLimitServiceException("The number of organization core member must not be greater than "
                    + OrganizationCoreMemberConstants.MAX_ORGANIZATION_CORE_MEMBER_NUMBER + ".");
        }

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
        wrapper.eq(OrganizationCoreMemberDO::getOrganizationId, organizationId);

        List<OrganizationCoreMemberDO> organizationCoreMemberDOS =
                organizationCoreMemberMapper.selectList(wrapper);
        return organizationCoreMemberDOS.stream()
                .map(organizationCoreMemberAssembler::organizationCoreMemberDOToOrganizationCoreMemberDTO)
                .collect(Collectors.toList());
    }

}
