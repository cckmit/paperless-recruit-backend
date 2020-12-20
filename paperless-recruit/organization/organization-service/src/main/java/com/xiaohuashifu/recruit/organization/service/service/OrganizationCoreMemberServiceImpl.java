package com.xiaohuashifu.recruit.organization.service.service;

import com.xiaohuashifu.recruit.common.result.ErrorCodeEnum;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.organization.api.constant.OrganizationCoreMemberConstants;
import com.xiaohuashifu.recruit.organization.api.dto.OrganizationCoreMemberDTO;
import com.xiaohuashifu.recruit.organization.api.service.OrganizationCoreMemberService;
import com.xiaohuashifu.recruit.organization.service.dao.OrganizationCoreMemberMapper;
import com.xiaohuashifu.recruit.organization.service.do0.OrganizationCoreMemberDO;
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

    public OrganizationCoreMemberServiceImpl(OrganizationCoreMemberMapper organizationCoreMemberMapper) {
        this.organizationCoreMemberMapper = organizationCoreMemberMapper;
    }

    /**
     * 保存组织核心成员
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              OperationConflict.Duplicate: 该组织核心成员已经存在
     *              OperationConflict.OverLimit: 该组织的核心成员数量已经超过限制
     *
     * @permission 必须验证 organizationId 和 organizationMemberId 是属于用户本身的
     *
     * @param organizationId 组织编号
     * @param organizationMemberId 组织成员编号
     * @return OrganizationCoreMemberDTO
     */
    @Override
    public Result<OrganizationCoreMemberDTO> saveOrganizationCoreMember(Long organizationId,
                                                                        Long organizationMemberId) {
        // 判断该成员是否已经存在
        int count = organizationCoreMemberMapper.countByOrganizationIdAndOrganizationMemberId(
                organizationId, organizationMemberId);
        if (count > 0) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT_DUPLICATE,
                    "The organization core member already exist.");
        }

        // 判断该组织的成员数是否大于等于 MAX_ORGANIZATION_CORE_MEMBER_NUMBER
        count = organizationCoreMemberMapper.countByOrganizationId(organizationId);
        if (count >= OrganizationCoreMemberConstants.MAX_ORGANIZATION_CORE_MEMBER_NUMBER) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT_OVER_LIMIT,
                    "The number of organization core member must not be greater than "
                            + OrganizationCoreMemberConstants.MAX_ORGANIZATION_CORE_MEMBER_NUMBER + ".");
        }

        // 插入组织核心成员
        OrganizationCoreMemberDO organizationCoreMemberDO = new OrganizationCoreMemberDO.Builder()
                .organizationId(organizationId)
                .organizationMemberId(organizationMemberId)
                .build();
        organizationCoreMemberMapper.insertOrganizationCoreMember(organizationCoreMemberDO);
        return getOrganizationCoreMember(organizationCoreMemberDO.getId());
    }

    /**
     * 获取组织编号
     *
     * @private 内部方法
     *
     * @param id 组织核心成员编号
     * @return 组织编号
     */
    @Override
    public Long getOrganizationId(Long id) {
        return organizationCoreMemberMapper.getOrganizationId(id);
    }

    /**
     * 删除组织核心成员
     *
     * @errorCode InvalidParameter: 参数格式错误
     *
     * @permission 必须验证该 id 是属于该组织的
     *
     * @param id 组织核心成员编号
     * @return OrganizationCoreMemberDTO
     */
    @Override
    public Result<Void> deleteOrganizationCoreMember(Long id) {
        // 删除该核心成员
        organizationCoreMemberMapper.deleteOrganizationCoreMember(id);
        return Result.success();
    }

    /**
     * 获取组织的所有核心成员
     *
     * @errorCode InvalidParameter: 参数格式错误
     *
     * @param organizationId 组织编号
     * @return OrganizationCoreMemberDTO 可能返回空列表，如果该组织没有核心成员
     */
    @Override
    public Result<List<OrganizationCoreMemberDTO>> listOrganizationCoreMembersByOrganizationId(Long organizationId) {
        List<OrganizationCoreMemberDO> organizationCoreMemberDOList =
                organizationCoreMemberMapper.listOrganizationCoreMembersByOrganizationId(organizationId);
        List<OrganizationCoreMemberDTO> organizationCoreMemberDTOList = organizationCoreMemberDOList.stream()
                .map(organizationCoreMemberDO -> new OrganizationCoreMemberDTO(
                        organizationCoreMemberDO.getId(),
                        organizationCoreMemberDO.getOrganizationId(),
                        organizationCoreMemberDO.getOrganizationMemberId()))
                .collect(Collectors.toList());
        return Result.success(organizationCoreMemberDTOList);
    }

    /**
     * 获取组织核心成员
     *
     * @param id 组织核心成员编号
     * @return OrganizationCoreMemberDTO
     */
    private Result<OrganizationCoreMemberDTO> getOrganizationCoreMember(Long id) {
        OrganizationCoreMemberDO organizationCoreMemberDO = organizationCoreMemberMapper.getOrganizationCoreMember(id);
        OrganizationCoreMemberDTO organizationCoreMemberDTO = new OrganizationCoreMemberDTO(
                organizationCoreMemberDO.getId(), organizationCoreMemberDO.getOrganizationId(),
                organizationCoreMemberDO.getOrganizationMemberId());
        return Result.success(organizationCoreMemberDTO);
    }

}
