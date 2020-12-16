package com.xiaohuashifu.recruit.organization.service.service;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.organization.api.constant.OrganizationMemberStatusEnum;
import com.xiaohuashifu.recruit.organization.api.dto.OrganizationMemberDTO;
import com.xiaohuashifu.recruit.organization.api.dto.OrganizationMemberInvitationDTO;
import com.xiaohuashifu.recruit.organization.api.query.OrganizationMemberInvitationQuery;
import com.xiaohuashifu.recruit.organization.api.query.OrganizationMemberQuery;
import com.xiaohuashifu.recruit.organization.api.service.OrganizationMemberService;
import com.xiaohuashifu.recruit.organization.api.service.OrganizationService;
import com.xiaohuashifu.recruit.organization.service.dao.OrganizationMemberInvitationMapper;
import com.xiaohuashifu.recruit.organization.service.dao.OrganizationMemberMapper;
import com.xiaohuashifu.recruit.user.api.service.UserService;
import org.apache.dubbo.config.annotation.Reference;

/**
 * 描述：组织成员服务实现
 *
 * @author xhsf
 * @create 2020/12/16 16:45
 */
public class OrganizationMemberServiceImpl implements OrganizationMemberService {

    private final OrganizationMemberMapper organizationMemberMapper;

    private final OrganizationMemberInvitationMapper organizationMemberInvitationMapper;

    @Reference
    private OrganizationService organizationService;

    @Reference
    private UserService userService;

    public OrganizationMemberServiceImpl(OrganizationMemberMapper organizationMemberMapper,
                                         OrganizationMemberInvitationMapper organizationMemberInvitationMapper) {
        this.organizationMemberMapper = organizationMemberMapper;
        this.organizationMemberInvitationMapper = organizationMemberInvitationMapper;
    }

    /**
     * 发送加入组织邀请
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 组织不存在
     *              Forbidden: 组织不可用
     *
     *
     * @param organizationId 组织编号
     * @param username       用户名
     * @return 发送结果
     */
    @Override
    public Result<OrganizationMemberInvitationDTO> sendInvitation(Long organizationId, String username) {
        // 判断组织状态
        Result<OrganizationMemberInvitationDTO> checkOrganizationStatusResult =
                organizationService.checkOrganizationStatus(organizationId);
        if (!checkOrganizationStatusResult.isSuccess()) {
            return checkOrganizationStatusResult;
        }

        // 判断用户状态


        return null;
    }

    /**
     * 接受加入组织邀请
     *
     * @param organizationId 组织编号
     * @param userId         用户编号
     * @return 接受结果
     */
    @Override
    public Result<Void> acceptInvitation(Long organizationId, Long userId) {
        return null;
    }

    /**
     * 查询组织成员
     *
     * @param query 查询参数
     * @return PageInfo<OrganizationPositionDTO> 带分页参数的组织成员列表，可能返回空列表
     * @errorCode InvalidParameter: 查询参数格式错误
     */
    @Override
    public Result<PageInfo<OrganizationMemberDTO>> listOrganizationMemberDTO(OrganizationMemberQuery query) {
        return null;
    }

    /**
     * 查询组织成员邀请
     *
     * @param query 查询参数
     * @return PageInfo<OrganizationMemberInvitationDTO> 带分页参数的组织成员邀请列表，可能返回空列表
     * @errorCode InvalidParameter: 查询参数格式错误
     */
    @Override
    public Result<PageInfo<OrganizationMemberInvitationDTO>> listOrganizationMemberInvitationDTO(
            OrganizationMemberInvitationQuery query) {
        return null;
    }

    /**
     * 更新组织成员的部门
     *
     * @param organizationMemberId 组织成员编号
     * @param departmentId         部门编号，若为0表示不绑定任何部门
     * @return 更新部门后的组织成员对象
     */
    @Override
    public Result<OrganizationMemberDTO> updateDepartment(Long organizationMemberId, Long departmentId) {
        return null;
    }

    /**
     * 更新组织成员的组织职位
     *
     * @param organizationMemberId   组织成员编号
     * @param organizationPositionId 组织职位编号，若为0表示不绑定任何组织职位
     * @return 更新部门后的组织成员对象
     */
    @Override
    public Result<OrganizationMemberDTO> updateOrganizationPosition(Long organizationMemberId,
                                                                    Long organizationPositionId) {
        return null;
    }

    /**
     * 更新组织成员的状态
     *
     * @param organizationMemberId 组织成员编号
     * @param memberStatus         成员状态
     * @return 更新部门后的组织成员对象
     */
    @Override
    public Result<OrganizationMemberDTO> updateOrganizationPosition(Long organizationMemberId,
                                                                    OrganizationMemberStatusEnum memberStatus) {
        return null;
    }

}
