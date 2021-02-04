package com.xiaohuashifu.recruit.organization.service.service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.recruit.common.aspect.annotation.DistributedLock;
import com.xiaohuashifu.recruit.common.result.ErrorCodeEnum;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.notification.api.constant.SystemNotificationTypeEnum;
import com.xiaohuashifu.recruit.notification.api.po.SendSystemNotificationPO;
import com.xiaohuashifu.recruit.notification.api.service.SystemNotificationService;
import com.xiaohuashifu.recruit.organization.api.constant.DepartmentConstants;
import com.xiaohuashifu.recruit.organization.api.constant.OrganizationMemberInvitationStatusEnum;
import com.xiaohuashifu.recruit.organization.api.constant.OrganizationMemberStatusEnum;
import com.xiaohuashifu.recruit.organization.api.constant.OrganizationPositionConstants;
import com.xiaohuashifu.recruit.organization.api.dto.DepartmentDTO;
import com.xiaohuashifu.recruit.organization.api.dto.OrganizationDTO;
import com.xiaohuashifu.recruit.organization.api.dto.OrganizationMemberDTO;
import com.xiaohuashifu.recruit.organization.api.dto.OrganizationMemberInvitationDTO;
import com.xiaohuashifu.recruit.organization.api.query.OrganizationMemberInvitationQuery;
import com.xiaohuashifu.recruit.organization.api.query.OrganizationMemberQuery;
import com.xiaohuashifu.recruit.organization.api.service.DepartmentService;
import com.xiaohuashifu.recruit.organization.api.service.OrganizationMemberService;
import com.xiaohuashifu.recruit.organization.api.service.OrganizationService;
import com.xiaohuashifu.recruit.organization.service.dao.OrganizationMemberInvitationMapper;
import com.xiaohuashifu.recruit.organization.service.dao.OrganizationMemberMapper;
import com.xiaohuashifu.recruit.organization.service.do0.OrganizationMemberDO;
import com.xiaohuashifu.recruit.organization.service.do0.OrganizationMemberInvitationDO;
import com.xiaohuashifu.recruit.user.api.dto.UserDTO;
import com.xiaohuashifu.recruit.user.api.service.UserService;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.aop.framework.AopContext;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 描述：组织成员服务实现
 *
 * @author xhsf
 * @create 2020/12/16 16:45
 */
@Service
public class OrganizationMemberServiceImpl implements OrganizationMemberService {

    private final OrganizationMemberMapper organizationMemberMapper;

    private final OrganizationMemberInvitationMapper organizationMemberInvitationMapper;

    @Reference
    private OrganizationService organizationService;

    @Reference
    private UserService userService;

    @Reference
    private SystemNotificationService systemNotificationService;

    @Reference
    private DepartmentService departmentService;

    /**
     * 组织成员邀请通知标题模式，{0}是组织名缩写
     */
    private static final String ORGANIZATION_MEMBER_INVITATION_NOTIFICATION_TITLE_PATTERN =
            "{0}邀请你加入！";

    /**
     * 组织成员邀请通知内容模式，{0}是组织名，{1}是邀请的过期时间
     */
    private static final String ORGANIZATION_MEMBER_INVITATION_NOTIFICATION_CONTENT_PATTERN =
            "{0}邀请你加入，成为他们的一员。请在{1}小时内确认，逾期失效。";

    /**
     * 组织成员邀请过期时间，24小时
     */
    private static final int ORGANIZATION_MEMBER_INVITATION_EXPIRATION_TIME = 24;

    /**
     * 组织成员的锁定键模式，{0}是组织成员编号
     */
    private static final String ORGANIZATION_MEMBER_LOCK_KEY_PATTERN = "organization-member:{0}";

    /**
     * 组织成员发送邀请时的锁定键模式，{0}是组织编号，{1}是用户编号
     */
    private static final String ORGANIZATION_MEMBER_SEND_INVITATION_LOCK_KEY_PATTERN =
            "organization:{0}:member:user-id:{1}";

    /**
     * 组织成员邀请的锁定键模式，{0}是组织成员邀请编号
     */
    private static final String ORGANIZATION_MEMBER_INVITATION_LOCK_KEY_PATTERN = "organization-member-invitation:{0}";

    public OrganizationMemberServiceImpl(OrganizationMemberMapper organizationMemberMapper,
                                         OrganizationMemberInvitationMapper organizationMemberInvitationMapper) {
        this.organizationMemberMapper = organizationMemberMapper;
        this.organizationMemberInvitationMapper = organizationMemberInvitationMapper;
    }

    /**
     * 发送加入组织邀请
     *
     * @permission 必须是该组织的主体用户
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.User.NotExist: 用户不存在
     *              Forbidden.User: 用户不可用
     *              OperationConflict: 该组织已经存在该成员
     *              OperationConflict.Duplicate: 已经发送了邀请，且邀请状态是等待接受
     *              OperationConflict.Lock: 获取组织成员的锁失败
     *
     * @param organizationId 组织编号
     * @param username       用户名
     * @return 发送结果
     */
    @Override
    public Result<OrganizationMemberInvitationDTO> sendInvitation(Long organizationId, String username) {
        // 获取用户
        Result<UserDTO> getUserResult = userService.getUserByUsername(username);
        if (getUserResult.isFailure()) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_USER_NOT_EXIST, "The user does not exist.");
        }
        UserDTO userDTO = getUserResult.getData();
        Long userId = userDTO.getId();

        // 调用主处理服务
        return ((OrganizationMemberServiceImpl)AopContext.currentProxy()).sendInvitation(organizationId, userId);
    }

    /**
     * 接受加入组织邀请
     *
     * @permission 必须检查 organizationMemberInvitationId 是不是属于该用户
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              OperationConflict.Lock: 获取组织成员邀请的锁失败
     *              OperationConflict.Status: 组织成员邀请的状态不是等待接受，无法拒绝
     *
     * @param organizationMemberInvitationId 组织成员邀请编号
     * @return 组织成员对象
     */
    @DistributedLock(value = ORGANIZATION_MEMBER_INVITATION_LOCK_KEY_PATTERN,
            parameters = "#{#organizationMemberInvitationId}",
            errorMessage = "Failed to acquire organizationMemberInvitation lock.")
    @Override
    public Result<OrganizationMemberDTO> acceptInvitation(Long organizationMemberInvitationId) {
        // 判断组织成员邀请是否处于等待接受状态
        OrganizationMemberInvitationDO organizationMemberInvitationDO =
                organizationMemberInvitationMapper.getOrganizationMemberInvitation(organizationMemberInvitationId);
        if (organizationMemberInvitationMapper.getInvitationStatus(organizationMemberInvitationId)
                != OrganizationMemberInvitationStatusEnum.WAITING_FOR_ACCEPTANCE) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT_STATUS,
                    "The invitationStatus must be \"WAITING_FOR_ACCEPTANCE\".");
        }

        // 创建组织成员
        Long userId = organizationMemberInvitationDO.getUserId();
        Long organizationId = organizationMemberInvitationDO.getOrganizationId();
        OrganizationMemberDO organizationMemberDO = OrganizationMemberDO.builder()
                .userId(userId)
                .organizationId(organizationId)
                .build();
        organizationMemberMapper.insertOrganizationMember(organizationMemberDO);

        // 更新组织成员邀请的状态为 ACCEPTED
        organizationMemberInvitationMapper.updateInvitationStatus(organizationMemberInvitationId,
                OrganizationMemberInvitationStatusEnum.ACCEPTED);

        // 增加组织的成员数量
        organizationService.increaseNumberOfMembers(organizationId);
        return getOrganizationMember(organizationMemberDO.getId());
    }

    /**
     * 查询组织成员
     *
     * @permission 只能查询自己组织的记录
     *
     * @errorCode InvalidParameter: 查询参数格式错误
     *
     * @param query 查询参数
     * @return PageInfo<OrganizationPositionDTO> 带分页参数的组织成员列表，可能返回空列表
     */
    @Override
    public Result<PageInfo<OrganizationMemberDTO>> listOrganizationMember(OrganizationMemberQuery query) {
        List<OrganizationMemberDO> organizationMemberDOList = organizationMemberMapper.listOrganizationMembers(query);
        List<OrganizationMemberDTO> organizationMemberDTOList = organizationMemberDOList
                .stream()
                .map(organizationMemberDO -> OrganizationMemberDTO
                        .builder()
                        .id(organizationMemberDO.getId())
                        .userId(organizationMemberDO.getUserId())
                        .organizationId(organizationMemberDO.getOrganizationId())
                        .departmentId(organizationMemberDO.getDepartmentId())
                        .organizationPositionId(organizationMemberDO.getOrganizationPositionId())
                        .memberStatus(organizationMemberDO.getMemberStatus().name())
                        .build())
                .collect(Collectors.toList());
        PageInfo<OrganizationMemberDTO> pageInfo = new PageInfo<>(organizationMemberDTOList);
        return Result.success(pageInfo);
    }

    /**
     * 查询组织成员邀请
     *
     * @permission 只能查询自己组织的记录
     *
     * @errorCode InvalidParameter: 查询参数格式错误
     *
     * @param query 查询参数
     * @return PageInfo<OrganizationMemberInvitationDTO> 带分页参数的组织成员邀请列表，可能返回空列表
     */
    @Override
    public Result<PageInfo<OrganizationMemberInvitationDTO>> listOrganizationMemberInvitation(
            OrganizationMemberInvitationQuery query) {
        List<OrganizationMemberInvitationDO> organizationMemberInvitationDOList =
                organizationMemberInvitationMapper.listOrganizationMemberInvitations(query);
        List<OrganizationMemberInvitationDTO> organizationMemberInvitationDTOList =
                organizationMemberInvitationDOList.stream()
                .map(organizationMemberInvitationDO -> OrganizationMemberInvitationDTO
                        .builder()
                        .id(organizationMemberInvitationDO.getId())
                        .userId(organizationMemberInvitationDO.getUserId())
                        .organizationId(organizationMemberInvitationDO.getOrganizationId())
                        .invitationTime(organizationMemberInvitationDO.getInvitationTime())
                        .invitationStatus(organizationMemberInvitationDO.getInvitationStatus().name())
                        .build())
                .collect(Collectors.toList());
        PageInfo<OrganizationMemberInvitationDTO> pageInfo = new PageInfo<>(organizationMemberInvitationDTOList);
        return Result.success(pageInfo);
    }

    /**
     * 获取组织编号
     *
     * @private 内部方法
     *
     * @param id 组织成员编号
     * @return 组织编号，若组织成员不存在返回 null
     */
    @Override
    public Long getOrganizationId(Long id) {
        return organizationMemberMapper.getOrganizationId(id);
    }

    /**
     * 获取用户编号
     *
     * @private 内部方法
     *
     * @param id 组织成员编号
     * @return 用户编号，若组织成员不存在返回 null
     */
    @Override
    public Long getUserId(Long id) {
        return organizationMemberMapper.getUserId(id);
    }

    /**
     * 更新组织成员的部门
     *
     * @permission 需要检查 organizationMemberId，newDepartmentId 是否属于该组织
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              OperationConflict.Lock: 获取组织成员的锁失败
     *              OperationConflict.Status: 组织成员状态必须是在职
     *              OperationConflict.Unmodified: 新旧部门相同
     *              OperationConflict.Deactivated: 新部门已经被停用
     *
     * @param organizationMemberId 组织成员编号
     * @param newDepartmentId 部门编号，若为0表示不绑定任何部门
     * @return 更新部门后的组织成员对象
     */
    @DistributedLock(value = ORGANIZATION_MEMBER_LOCK_KEY_PATTERN, parameters = "#{#organizationMemberId}",
            errorMessage = "Failed to acquire organizationMember lock.")
    @Override
    public Result<OrganizationMemberDTO> updateDepartment(Long organizationMemberId, Long newDepartmentId) {
        // 检查新旧部门是否相同，组织成员状态是否是在职，部门是否已经停用
        Result<OrganizationMemberDO> checkResult = checkForUpdateDepartment(organizationMemberId, newDepartmentId);
        if (checkResult.isFailure()) {
            return Result.fail(checkResult);
        }

        // 更新组织成员的部门
        organizationMemberMapper.updateDepartment(organizationMemberId, newDepartmentId);

        // 更新部门的成员数量
        OrganizationMemberDO organizationMemberDO = checkResult.getData();
        updateDepartmentMemberNumber(organizationMemberDO.getDepartmentId(), newDepartmentId);

        // 更新后的组织成员对象
        return getOrganizationMember(organizationMemberId);
    }

    /**
     * 更新组织成员的组织职位
     *
     * @permission 需要检查 organizationMemberId 和 newOrganizationPositionId 是不是该组织的
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              OperationConflict.Lock: 获取组织成员的锁失败
     *              OperationConflict.Status: 组织成员状态必须是在职
     *              OperationConflict.Unmodified: 新旧组织职位相同
     *
     * @param organizationMemberId   组织成员编号
     * @param newOrganizationPositionId 组织职位编号，若为0表示不绑定任何组织职位
     * @return 更新部门后的组织成员对象
     */
    @DistributedLock(value = ORGANIZATION_MEMBER_LOCK_KEY_PATTERN, parameters = "#{#organizationMemberId}",
            errorMessage = "Failed to acquire organizationMember lock.")
    @Override
    public Result<OrganizationMemberDTO> updateOrganizationPosition(Long organizationMemberId,
                                                                    Long newOrganizationPositionId) {
        // 检查组织成员状态是否是在职，新旧组织职位相同
        Result<OrganizationMemberDO> checkResult = checkForUpdateOrganizationPosition(
                organizationMemberId, newOrganizationPositionId);
        if (checkResult.isFailure()) {
            return Result.fail(checkResult);
        }

        // 更新组织成员的职位
        organizationMemberMapper.updateOrganizationPosition(organizationMemberId, newOrganizationPositionId);

        // 更新后的组织成员对象
        return getOrganizationMember(organizationMemberId);
    }

    /**
     * 更新组织成员的状态
     *
     * @permission 需要检查 organizationMemberId 是否属于该组织
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              OperationConflict.Lock: 获取组织成员的锁失败
     *              OperationConflict.Unmodified: 新旧成员状态相同
     *
     * @param organizationMemberId 组织成员编号
     * @param newMemberStatus 成员状态
     * @return 更新部门后的组织成员对象
     */
    @DistributedLock(value = ORGANIZATION_MEMBER_LOCK_KEY_PATTERN, parameters = "#{#organizationMemberId}",
            errorMessage = "Failed to acquire organizationMember lock.")
    @Override
    public Result<OrganizationMemberDTO> updateMemberStatus(Long organizationMemberId,
                                                            OrganizationMemberStatusEnum newMemberStatus) {
        // 如果新旧状态相同，则不进行更新操作
        OrganizationMemberDO organizationMemberDO =
                organizationMemberMapper.getOrganizationMember(organizationMemberId);
        if (organizationMemberDO.getMemberStatus() == newMemberStatus) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT_UNMODIFIED,
                    "The newMemberStatus can't be the same as the oldMemberStatus.");
        }

        // 更新组织成员状态
        organizationMemberMapper.updateMemberStatus(organizationMemberId, newMemberStatus);

        // 更新组织和部门的成员数量
        updateOrganizationAndDepartmentMemberNumber(organizationMemberDO.getMemberStatus(), newMemberStatus,
                organizationMemberDO.getOrganizationId(), organizationMemberDO.getDepartmentId());

        // 更新后的组织成员对象
        return getOrganizationMember(organizationMemberId);
    }

    /**
     * 清除组织成员的组织职位
     * 即通过组织职位编号，是该组织职位编号的成员，设置组织职位为0
     *
     * @private 内部方法
     *
     * @errorCode InvalidParameter: 参数格式错误
     *
     * @param organizationPositionId 组织职位编号
     * @return 被清除职位的组织成员数量
     */
    @Override
    public Result<Integer> clearOrganizationPositions(Long organizationPositionId) {
        int clearCount = organizationMemberMapper.updateOrganizationPositionByOrganizationPositionId(
                organizationPositionId, OrganizationPositionConstants.ORGANIZATION_POSITION_ID_WHEN_NO_POSITION);
        return Result.success(clearCount);
    }

    /**
     * 拒绝加入组织邀请
     *
     * @permission 需要检查 organizationMemberInvitationId 是否属于该用户
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              OperationConflict.Status: 组织成员邀请的状态不是等待接受，无法拒绝
     *              OperationConflict.Lock: 获取组织成员邀请的锁失败
     *
     * @param organizationMemberInvitationId 组织成员邀请编号
     * @return 拒绝结果
     */
    @DistributedLock(value = ORGANIZATION_MEMBER_INVITATION_LOCK_KEY_PATTERN,
            parameters = "#{#organizationMemberInvitationId}",
            errorMessage = "Failed to acquire organizationMemberInvitation lock.")
    @Override
    public Result<OrganizationMemberInvitationDTO> rejectInvitation(Long organizationMemberInvitationId) {
        // 判断组织成员邀请是否处于等待接受状态
        if (organizationMemberInvitationMapper.getInvitationStatus(organizationMemberInvitationId)
                != OrganizationMemberInvitationStatusEnum.WAITING_FOR_ACCEPTANCE) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT_STATUS,
                    "The invitationStatus must be \"WAITING_FOR_ACCEPTANCE\".");
        }

        // 拒绝组织邀请
        organizationMemberInvitationMapper.updateInvitationStatus(organizationMemberInvitationId,
                OrganizationMemberInvitationStatusEnum.REJECTED);
        return getOrganizationMemberInvitation(organizationMemberInvitationId);
    }

    /**
     * 更新组织成员邀请的状态为 EXPIRED
     *
     * @private 内部方法
     *
     * @see com.xiaohuashifu.recruit.organization.api.constant.OrganizationMemberInvitationStatusEnum -> EXPIRED
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              OperationConflict.Lock: 获取组织成员邀请的锁失败
     *
     * @param organizationMemberInvitationId 组织成员邀请编号
     * @return 更新结果
     */
    @DistributedLock(value = ORGANIZATION_MEMBER_INVITATION_LOCK_KEY_PATTERN,
            parameters = "#{#organizationMemberInvitationId}",
            errorMessage = "Failed to acquire organizationMemberInvitation lock.")
    @Override
    public Result<OrganizationMemberInvitationDTO> updateInvitationStatusToExpired(
            Long organizationMemberInvitationId) {
        // 更新组织成员邀请的状态为 EXPIRED
        organizationMemberInvitationMapper.updateInvitationStatus(organizationMemberInvitationId,
                OrganizationMemberInvitationStatusEnum.EXPIRED);
        return getOrganizationMemberInvitation(organizationMemberInvitationId);
    }

    /**
     * 发送加入组织邀请
     *
     * @errorCode Forbidden.User: 用户不可用
     *              OperationConflict: 该组织已经存在该成员
     *              OperationConflict.Duplicate: 已经发送了邀请，且邀请状态是等待接受
     *              OperationConflict.Lock: 获取组织成员的锁失败
     *
     * @param organizationId 组织编号
     * @param userId 用户编号
     * @return 发送结果
     *
     * @tips 这里不可使用 private，会因为AOP的原因而导致注入失败
     */
    @DistributedLock(value = ORGANIZATION_MEMBER_SEND_INVITATION_LOCK_KEY_PATTERN, parameters = {"#{#organizationId}", "#{#userId}"},
            errorMessage = "Failed to acquire organization member lock.")
    public Result<OrganizationMemberInvitationDTO> sendInvitation(Long organizationId, Long userId) {
        // 检查用户的状态
        Result<OrganizationMemberInvitationDTO> checkUserStatusResult = userService.checkUserStatus(userId);
        if (checkUserStatusResult.isFailure()) {
            return checkUserStatusResult;
        }

        // 判断该组织是否已经有该成员
        int count = organizationMemberMapper.countByOrganizationIdAndUserId(organizationId, userId);
        if (count > 0) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT, "The member already exist.");
        }

        // 判断该组织是否已已经发送了邀请，且邀请的状态是等待接受
        count = organizationMemberInvitationMapper.countByOrganizationIdAndUserIdAndInvitationStatus(
                organizationId, userId, OrganizationMemberInvitationStatusEnum.WAITING_FOR_ACCEPTANCE);
        if (count > 0) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT_DUPLICATE,
                    "Please do not send invitations repeatedly.");
        }

        // 添加组织成员邀请记录
        OrganizationMemberInvitationDO organizationMemberInvitationDO = OrganizationMemberInvitationDO.builder()
                .organizationId(organizationId)
                .userId(userId)
                .invitationTime(LocalDateTime.now())
                .invitationStatus(OrganizationMemberInvitationStatusEnum.WAITING_FOR_ACCEPTANCE)
                .build();
        organizationMemberInvitationMapper.insertOrganizationMemberInvitation(organizationMemberInvitationDO);

        // 发送组织成员邀请通知
        sendInvitationNotification(organizationMemberInvitationDO.getId(), organizationId, userId);
        return getOrganizationMemberInvitation(organizationMemberInvitationDO.getId());
    }

    /**
     * 构造系统通知并发送
     *
     * @param organizationMemberInvitationId 组织成员邀请编号
     * @param organizationId 组织编号
     * @param userId 被邀请的用户编号
     */
    private void sendInvitationNotification(Long organizationMemberInvitationId, Long organizationId, Long userId) {
        // 构造通知标题
        OrganizationDTO organizationDTO = organizationService.getOrganization(organizationId);
        String notificationTitle = MessageFormat.format(ORGANIZATION_MEMBER_INVITATION_NOTIFICATION_TITLE_PATTERN,
                organizationDTO.getAbbreviationOrganizationName());

        // 构造通知内容
        String notificationContentMessage = MessageFormat.format(
                ORGANIZATION_MEMBER_INVITATION_NOTIFICATION_CONTENT_PATTERN, organizationDTO.getOrganizationName(),
                ORGANIZATION_MEMBER_INVITATION_EXPIRATION_TIME);
        JSONObject notificationContentJsonObject = new JSONObject();
        notificationContentJsonObject.put("organizationId", organizationId);
        notificationContentJsonObject.put("organizationMemberInvitationId", organizationMemberInvitationId);
        notificationContentJsonObject.put("message", notificationContentMessage);
        String notificationContent = notificationContentJsonObject.toJSONString();

        // 发送通知
        SendSystemNotificationPO sendSystemNotificationPO = SendSystemNotificationPO.builder()
                .userId(userId)
                .notificationType(SystemNotificationTypeEnum.ORGANIZATION_INVITATION)
                .notificationTitle(notificationTitle)
                .notificationContent(notificationContent)
                .build();
        systemNotificationService.sendSystemNotification(sendSystemNotificationPO);
    }

    /**
     * 获取组织成员
     *
     * @param organizationMemberId 组织成员编号
     * @return 组织成员 DTO
     */
    private Result<OrganizationMemberDTO> getOrganizationMember(Long organizationMemberId) {
        OrganizationMemberDO organizationMemberDO =
                organizationMemberMapper.getOrganizationMember(organizationMemberId);
        OrganizationMemberDTO organizationMemberDTO = OrganizationMemberDTO.builder()
                .id(organizationMemberDO.getId())
                .organizationId(organizationMemberDO.getOrganizationId())
                .departmentId(organizationMemberDO.getDepartmentId())
                .organizationPositionId(organizationMemberDO.getOrganizationPositionId())
                .userId(organizationMemberDO.getUserId())
                .memberStatus(organizationMemberDO.getMemberStatus().name())
                .build();
        return Result.success(organizationMemberDTO);
    }

    /**
     * 获取组织成员邀请
     *
     * @param organizationMemberInvitationId 组织成员邀请编号
     * @return 组织成员邀请 DTO
     */
    private Result<OrganizationMemberInvitationDTO> getOrganizationMemberInvitation(
            Long organizationMemberInvitationId) {
        OrganizationMemberInvitationDO organizationMemberInvitationDO =
                organizationMemberInvitationMapper.getOrganizationMemberInvitation(organizationMemberInvitationId);
        OrganizationMemberInvitationDTO organizationMemberInvitationDTO = OrganizationMemberInvitationDTO.builder()
                .id(organizationMemberInvitationDO.getId())
                .organizationId(organizationMemberInvitationDO.getOrganizationId())
                .userId(organizationMemberInvitationDO.getUserId())
                .invitationTime(organizationMemberInvitationDO.getInvitationTime())
                .invitationStatus(organizationMemberInvitationDO.getInvitationStatus().name())
                .build();
        return Result.success(organizationMemberInvitationDTO);
    }

    /**
     * 检查组织成员状态必须是在职，新旧部门是否相同，部门是否被停用
     *
     * @errorCode OperationConflict.Status: 组织成员状态必须是在职
     *              OperationConflict.Unmodified: 新旧部门相同
     *              OperationConflict.Deactivated: 新部门已经被停用
     *
     * @param organizationMemberId 组织成员编号
     * @param newDepartmentId 新部门编号
     * @return 检查结果，若通过检查，附带 OrganizationMemberDO
     */
    private Result<OrganizationMemberDO> checkForUpdateDepartment(Long organizationMemberId, Long newDepartmentId) {
        // 判断组织成员状态是否为在职，必须在职才可以改变部门
        OrganizationMemberDO organizationMemberDO = organizationMemberMapper.getOrganizationMember(organizationMemberId);
        if (organizationMemberDO.getMemberStatus() != OrganizationMemberStatusEnum.ON_JOB) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT_STATUS,
                    "The memberStatus must be \"ON_JOB\".");
        }

        // 判断新旧部门是否相同
        if (Objects.equals(organizationMemberDO.getDepartmentId(), newDepartmentId)) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT_UNMODIFIED,
                    "The newDepartment can't be the same as the oldDepartment.");
        }

        // 判断部门是否被停用
        DepartmentDTO departmentDTO = departmentService.getDepartment(newDepartmentId);
        if (departmentDTO.getDeactivated()) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT_DEACTIVATED,
                    "The department already deactivated.");
        }

        // 通过检查
        return Result.success(organizationMemberDO);
    }

    /**
     * 检查组织成员状态是否是在职，新旧组织职位相同
     *
     * @errorCode OperationConflict.Status: 组织成员状态必须是在职
     *              OperationConflict.Unmodified: 新旧组织职位相同
     *
     * @param organizationMemberId 组织成员编号
     * @param newOrganizationPositionId 新组织职位编号
     * @return 检查结果，若通过检查，附带 OrganizationMemberDO
     */
    private Result<OrganizationMemberDO> checkForUpdateOrganizationPosition(Long organizationMemberId,
                                                                            Long newOrganizationPositionId) {
        // 判断组织成员状态是否为在职，必须在职才可以改变部门
        OrganizationMemberDO organizationMemberDO = organizationMemberMapper.getOrganizationMember(organizationMemberId);
        if (organizationMemberDO.getMemberStatus() != OrganizationMemberStatusEnum.ON_JOB) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT_STATUS,
                    "The memberStatus must be \"ON_JOB\".");
        }

        // 判断新旧职位是否相同
        if (Objects.equals(organizationMemberDO.getOrganizationPositionId(), newOrganizationPositionId)) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT_UNMODIFIED,
                    "The newOrganizationPosition can't be the same as the oldOrganizationPosition.");
        }

        // 通过检查
        return Result.success(organizationMemberDO);
    }

    /**
     * 更新部门成员数量
     *
     * @param oldDepartmentId 原部门编号
     * @param newDepartmentId 新部门编号
     */
    private void updateDepartmentMemberNumber(Long oldDepartmentId, Long newDepartmentId) {
        // 当原来部门编号不为0（没有职位时的编号）时，需要减少原来部门的人数
        if (!Objects.equals(oldDepartmentId, DepartmentConstants.DEPARTMENT_ID_WHEN_NO_DEPARTMENT)) {
            departmentService.decreaseNumberOfMembers(oldDepartmentId);
        }
        // 增加当前部门的人数
        departmentService.increaseNumberOfMembers(newDepartmentId);
    }

    /**
     * 更新组织和部门的成员数量
     *
     * @param oldMemberStatus 原成员状态
     * @param newMemberStatus 新成员状态
     * @param organizationId 组织编号
     * @param departmentId 部门编号
     */
    private void updateOrganizationAndDepartmentMemberNumber(OrganizationMemberStatusEnum oldMemberStatus,
                                                             OrganizationMemberStatusEnum newMemberStatus,
                                                             Long organizationId, Long departmentId) {
        // 如果原状态为在职，组织和部门的成员数量-1
        if (oldMemberStatus == OrganizationMemberStatusEnum.ON_JOB) {
            organizationService.decreaseNumberOfMembers(organizationId);
            departmentService.decreaseNumberOfMembers(departmentId);
        }
        // 如果新状态为在职，组织和部门的成员数量+1
        if (newMemberStatus == OrganizationMemberStatusEnum.ON_JOB) {
            organizationService.increaseNumberOfMembers(organizationId);
            departmentService.increaseNumberOfMembers(departmentId);
        }
    }

}
