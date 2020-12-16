package com.xiaohuashifu.recruit.organization.service.service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.recruit.common.aspect.annotation.DistributedLock;
import com.xiaohuashifu.recruit.common.result.ErrorCodeEnum;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.notification.api.constant.SystemNotificationTypeEnum;
import com.xiaohuashifu.recruit.notification.api.po.SendSystemNotificationPO;
import com.xiaohuashifu.recruit.notification.api.service.SystemNotificationService;
import com.xiaohuashifu.recruit.organization.api.constant.OrganizationMemberInvitationStatusEnum;
import com.xiaohuashifu.recruit.organization.api.constant.OrganizationMemberStatusEnum;
import com.xiaohuashifu.recruit.organization.api.dto.OrganizationDTO;
import com.xiaohuashifu.recruit.organization.api.dto.OrganizationMemberDTO;
import com.xiaohuashifu.recruit.organization.api.dto.OrganizationMemberInvitationDTO;
import com.xiaohuashifu.recruit.organization.api.query.OrganizationMemberInvitationQuery;
import com.xiaohuashifu.recruit.organization.api.query.OrganizationMemberQuery;
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
     * 组织成员的锁定键模式，{0}是组织编号，{1}是用户编号
     */
    private static final String ORGANIZATION_MEMBER_LOCK_KEY_PATTERN = "organization:{0}:member:user-id:{1}";

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
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 组织不存在
     *              Forbidden: 组织不可用
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
        if (!getUserResult.isSuccess()) {
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
     * @errorCode InvalidParameter: 参数格式错误
     *              OperationConflict.Lock: 获取组织成员邀请的锁失败
     *              InvalidParameter.NotExist: 组织成员邀请不存在
     *              InvalidParameter.Status: 组织成员邀请的状态不是等待接受，无法拒绝
     *              Forbidden: 组织不可用
     *              Forbidden.User: 用户不可用
     *
     * @param organizationMemberInvitationId 组织成员邀请编号
     * @return 组织成员对象
     */
    @DistributedLock(value = ORGANIZATION_MEMBER_INVITATION_LOCK_KEY_PATTERN,
            parameters = "#{#organizationMemberInvitationId}",
            errorMessage = "Failed to acquire organizationMemberInvitation lock.")
    @Override
    public Result<OrganizationMemberDTO> acceptInvitation(Long organizationMemberInvitationId) {
        // 检查组织成员邀请、发送邀请的组织、被邀请的用户的状态是否适合更新
        Result<OrganizationMemberInvitationDO> checkResult =
                checkOrganizationAndUserAndOrganizationMemberInvitationStatus(organizationMemberInvitationId);
        if (!checkResult.isSuccess()) {
            return Result.fail(checkResult.getErrorCode(), checkResult.getErrorMessage());
        }

        // 创建组织成员
        OrganizationMemberInvitationDO organizationMemberInvitationDO = checkResult.getData();
        Long userId = organizationMemberInvitationDO.getUserId();
        Long organizationId = organizationMemberInvitationDO.getOrganizationId();
        OrganizationMemberDO organizationMemberDO = new OrganizationMemberDO.Builder()
                .userId(userId)
                .organizationId(organizationId)
                .build();
        organizationMemberMapper.insertOrganizationMember(organizationMemberDO);

        // 更新组织成员邀请的状态为 ACCEPTED
        organizationMemberInvitationMapper.updateInvitationStatus(organizationMemberInvitationId,
                OrganizationMemberInvitationStatusEnum.ACCEPTED);
        return getOrganizationMember(organizationMemberDO.getId());
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
    public Result<OrganizationMemberDTO> updateMemberStatus(Long organizationMemberId,
                                                            OrganizationMemberStatusEnum memberStatus) {
        return null;
    }

    /**
     * 拒绝加入组织邀请
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 组织成员邀请不存在
     *              InvalidParameter.Status: 组织成员邀请的状态不是等待接受，无法拒绝
     *              Forbidden: 组织不可用
     *              Forbidden.User: 用户不可用
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
        // 检查组织成员邀请、发送邀请的组织、被邀请的用户的状态是否适合更新
        Result<OrganizationMemberInvitationDO> checkResult =
                checkOrganizationAndUserAndOrganizationMemberInvitationStatus(organizationMemberInvitationId);
        if (!checkResult.isSuccess()) {
            return Result.fail(checkResult.getErrorCode(), checkResult.getErrorMessage());
        }

        // 拒绝组织邀请
        organizationMemberInvitationMapper.updateInvitationStatus(organizationMemberInvitationId,
                OrganizationMemberInvitationStatusEnum.REJECTED);
        return getOrganizationMemberInvitation(organizationMemberInvitationId);
    }

    /**
     * 更新组织成员邀请的状态为 EXPIRED
     *
     * @see com.xiaohuashifu.recruit.organization.api.constant.OrganizationMemberInvitationStatusEnum -> EXPIRED
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 组织成员邀请不存在
     *              InvalidParameter.Status: 组织成员邀请的状态不是等待接受，无法更新
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
        // 检查组织成员邀请的状态是否适合更新
        Result<OrganizationMemberInvitationDO> checkOrganizationMemberInvitationStatusResult =
                checkOrganizationMemberInvitationStatusForUpdate(organizationMemberInvitationId);
        if (!checkOrganizationMemberInvitationStatusResult.isSuccess()) {
            return Result.fail(checkOrganizationMemberInvitationStatusResult.getErrorCode(),
                    checkOrganizationMemberInvitationStatusResult.getErrorMessage());
        }

        // 更新组织成员邀请的状态为 EXPIRED
        organizationMemberInvitationMapper.updateInvitationStatus(organizationMemberInvitationId,
                OrganizationMemberInvitationStatusEnum.EXPIRED);
        return getOrganizationMemberInvitation(organizationMemberInvitationId);
    }

    /**
     * 发送加入组织邀请
     *
     * @errorCode InvalidParameter.NotExist: 组织不存在
     *              Forbidden: 组织不可用
     *              Forbidden.User: 用户不可用
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
    @DistributedLock(value = ORGANIZATION_MEMBER_LOCK_KEY_PATTERN, parameters = {"#{#organizationId}", "#{#userId}"},
            errorMessage = "Failed to acquire organization member lock.")
    public Result<OrganizationMemberInvitationDTO> sendInvitation(Long organizationId, Long userId) {
        // 检查组织和用户的状态
        Result<OrganizationMemberInvitationDTO> checkResult = checkOrganizationAndUserStatus(organizationId, userId);
        if (!checkResult.isSuccess()) {
            return checkResult;
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
        OrganizationMemberInvitationDO organizationMemberInvitationDO = new OrganizationMemberInvitationDO.Builder()
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
        Result<OrganizationDTO> getOrganizationResult = organizationService.getOrganization(organizationId);
        OrganizationDTO organizationDTO = getOrganizationResult.getData();
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
        SendSystemNotificationPO sendSystemNotificationPO = new SendSystemNotificationPO.Builder()
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
        OrganizationMemberDTO organizationMemberDTO = new OrganizationMemberDTO.Builder()
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
        OrganizationMemberInvitationDTO organizationMemberInvitationDTO = new OrganizationMemberInvitationDTO.Builder()
                .id(organizationMemberInvitationDO.getId())
                .organizationId(organizationMemberInvitationDO.getOrganizationId())
                .userId(organizationMemberInvitationDO.getUserId())
                .invitationTime(organizationMemberInvitationDO.getInvitationTime())
                .invitationStatus(organizationMemberInvitationDO.getInvitationStatus().name())
                .build();
        return Result.success(organizationMemberInvitationDTO);
    }

    /**
     * 检查组织和用户的状态
     *
     * @errorCode InvalidParameter.NotExist: 组织不存在
     *              Forbidden: 组织不可用
     *              InvalidParameter.User.NotExist: 用户不存在
     *              Forbidden.User: 用户不可用
     *
     * @param organizationId 组织编号
     * @param userId 用户编号
     * @return 检查结果
     */
    private <T> Result<T> checkOrganizationAndUserStatus(Long organizationId, Long userId) {
        // 判断组织状态
        Result<T> checkOrganizationStatusResult =
                organizationService.checkOrganizationStatus(organizationId);
        if (!checkOrganizationStatusResult.isSuccess()) {
            return checkOrganizationStatusResult;
        }

        // 判断用户状态
        Result<T> checkUserStatusResult = userService.checkUserStatus(userId);
        if (!checkUserStatusResult.isSuccess()) {
            return checkUserStatusResult;
        }

        return Result.success();
    }

    /**
     * 检查组织成员邀请的状态，用于更新，也是就组织成员邀请要存在且状态为等待接受
     *
     * @errorCode InvalidParameter.NotExist: 组织成员邀请不存在
     *              InvalidParameter.Status: 组织成员邀请的状态不是等待接受，无法更新
     *
     * @param organizationMemberInvitationId 组织成员邀请的编号
     * @return 检查结果
     */
    private Result<OrganizationMemberInvitationDO> checkOrganizationMemberInvitationStatusForUpdate(
            Long organizationMemberInvitationId) {
        // 判断组织成员邀请存不存在
        OrganizationMemberInvitationDO organizationMemberInvitationDO =
                organizationMemberInvitationMapper.getOrganizationMemberInvitation(organizationMemberInvitationId);
        if (organizationMemberInvitationDO == null) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_EXIST,
                    "The organizationMemberInvitation does not exist.");
        }

        // 判断组织成员邀请是否处于等待接受状态
        if (organizationMemberInvitationDO.getInvitationStatus()
                != OrganizationMemberInvitationStatusEnum.WAITING_FOR_ACCEPTANCE) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_STATUS,
                    "The invitationStatus must be \"WAITING_FOR_ACCEPTANCE\".");
        }

        // 通过检查
        return Result.success(organizationMemberInvitationDO);
    }

    /**
     * 检查组织成员邀请、发送邀请的组织、被邀请的用户的状态。
     * 其中组织成员邀请的状态必须是 WAITING_FOR_ACCEPTANCE （等待接受）
     * 用户和组织的状态必须是 available
     *
     * @errorCode InvalidParameter.NotExist: 组织成员邀请不存在
     *              InvalidParameter.Status: 组织成员邀请的状态不是等待接受，无法拒绝
     *              Forbidden: 组织不可用
     *              Forbidden.User: 用户不可用
     *
     * @param organizationMemberInvitationId 组织成员邀请编号
     * @return 检查结果
     */
    private Result<OrganizationMemberInvitationDO> checkOrganizationAndUserAndOrganizationMemberInvitationStatus(
            Long organizationMemberInvitationId) {
        // 检查组织成员邀请的状态是否适合更新
        Result<OrganizationMemberInvitationDO> checkOrganizationMemberInvitationStatusResult =
                checkOrganizationMemberInvitationStatusForUpdate(organizationMemberInvitationId);
        if (!checkOrganizationMemberInvitationStatusResult.isSuccess()) {
            return Result.fail(checkOrganizationMemberInvitationStatusResult.getErrorCode(),
                    checkOrganizationMemberInvitationStatusResult.getErrorMessage());
        }

        // 检查组织和用户的状态
        OrganizationMemberInvitationDO organizationMemberInvitationDO =
                checkOrganizationMemberInvitationStatusResult.getData();
        Result<OrganizationMemberInvitationDO> checkOrganizationAndUserStatusResult =
                checkOrganizationAndUserStatus(organizationMemberInvitationDO.getOrganizationId(),
                        organizationMemberInvitationDO.getUserId());
        if (!checkOrganizationAndUserStatusResult.isSuccess()) {
            return checkOrganizationAndUserStatusResult;
        }

        // 通过检查
        return Result.success(organizationMemberInvitationDO);
    }

}
