package com.xiaohuashifu.recruit.organization.service.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaohuashifu.recruit.common.aspect.annotation.DistributedLock;
import com.xiaohuashifu.recruit.common.exception.NotFoundServiceException;
import com.xiaohuashifu.recruit.common.exception.unprocessable.DuplicateServiceException;
import com.xiaohuashifu.recruit.common.exception.unprocessable.InvalidStatusServiceException;
import com.xiaohuashifu.recruit.common.exception.unprocessable.UnavailableServiceException;
import com.xiaohuashifu.recruit.common.query.QueryResult;
import com.xiaohuashifu.recruit.notification.api.constant.SystemNotificationTypeEnum;
import com.xiaohuashifu.recruit.notification.api.request.SendSystemNotificationRequest;
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
import com.xiaohuashifu.recruit.organization.api.request.UpdateOrganizationMemberRequest;
import com.xiaohuashifu.recruit.organization.api.service.DepartmentService;
import com.xiaohuashifu.recruit.organization.api.service.OrganizationMemberService;
import com.xiaohuashifu.recruit.organization.api.service.OrganizationService;
import com.xiaohuashifu.recruit.organization.service.assembler.OrganizationMemberAssembler;
import com.xiaohuashifu.recruit.organization.service.assembler.OrganizationMemberInvitationAssembler;
import com.xiaohuashifu.recruit.organization.service.dao.OrganizationMemberInvitationMapper;
import com.xiaohuashifu.recruit.organization.service.dao.OrganizationMemberMapper;
import com.xiaohuashifu.recruit.organization.service.do0.OrganizationMemberDO;
import com.xiaohuashifu.recruit.organization.service.do0.OrganizationMemberInvitationDO;
import com.xiaohuashifu.recruit.organization.service.mq.NotificationTemplate;
import com.xiaohuashifu.recruit.user.api.service.UserService;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * ?????????????????????????????????
 *
 * @author xhsf
 * @create 2020/12/16 16:45
 */
@Service
public class OrganizationMemberServiceImpl implements OrganizationMemberService {

    private final OrganizationMemberMapper organizationMemberMapper;

    private final OrganizationMemberInvitationMapper organizationMemberInvitationMapper;

    private final OrganizationMemberAssembler organizationMemberAssembler;

    private final OrganizationMemberInvitationAssembler organizationMemberInvitationAssembler;

    private final NotificationTemplate notificationTemplate;

    @Reference
    private OrganizationService organizationService;

    @Reference
    private UserService userService;

    @Reference
    private DepartmentService departmentService;

    /**
     * ???????????????????????????????????????{0}??????????????????
     */
    private static final String ORGANIZATION_MEMBER_INVITATION_NOTIFICATION_TITLE_PATTERN =
            "{0}??????????????????";

    /**
     * ???????????????????????????????????????{0}???????????????{1}????????????????????????
     */
    private static final String ORGANIZATION_MEMBER_INVITATION_NOTIFICATION_CONTENT_PATTERN =
            "{0}????????????????????????????????????????????????{1}?????????????????????????????????";

    /**
     * ?????????????????????????????????24??????
     */
    private static final int ORGANIZATION_MEMBER_INVITATION_EXPIRATION_TIME = 24;

    /**
     * ?????????????????????????????????{0}?????????????????????
     */
    private static final String ORGANIZATION_MEMBER_LOCK_KEY_PATTERN = "organization-member:{0}";

    /**
     * ????????????????????????????????????????????????{0}??????????????????{1}???????????????
     */
    private static final String ORGANIZATION_MEMBER_SEND_INVITATION_LOCK_KEY_PATTERN =
            "organization:{0}:member:user-id:{1}";

    /**
     * ???????????????????????????????????????{0}???????????????????????????
     */
    private static final String ORGANIZATION_MEMBER_INVITATION_LOCK_KEY_PATTERN = "organization-member-invitation:{0}";

    public OrganizationMemberServiceImpl(OrganizationMemberMapper organizationMemberMapper,
                                         OrganizationMemberInvitationMapper organizationMemberInvitationMapper,
                                         OrganizationMemberAssembler organizationMemberAssembler,
                                         OrganizationMemberInvitationAssembler organizationMemberInvitationAssembler,
                                         NotificationTemplate notificationTemplate) {
        this.organizationMemberMapper = organizationMemberMapper;
        this.organizationMemberInvitationMapper = organizationMemberInvitationMapper;
        this.organizationMemberAssembler = organizationMemberAssembler;
        this.organizationMemberInvitationAssembler = organizationMemberInvitationAssembler;
        this.notificationTemplate = notificationTemplate;
    }

    @Override
    @Transactional
    @DistributedLock(value = ORGANIZATION_MEMBER_SEND_INVITATION_LOCK_KEY_PATTERN,
            parameters = {"#{#organizationId}", "#{#userId}"},
            errorMessage = "Failed to acquire organization member lock.")
    public OrganizationMemberInvitationDTO sendInvitation(Long organizationId, Long userId) {
        // ?????????????????????????????????
        userService.getUser(userId);
        organizationService.getOrganization(organizationId);

        // ?????????????????????
        // ???????????????????????????????????????
        LambdaQueryWrapper<OrganizationMemberDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrganizationMemberDO::getOrganizationId, organizationId)
                .eq(OrganizationMemberDO::getUserId, userId);
        int count = organizationMemberMapper.selectCount(wrapper);
        if (count > 0) {
            throw new DuplicateServiceException("The member already exist.");
        }

        // ?????????????????????????????????????????????????????????????????????????????????
        LambdaQueryWrapper<OrganizationMemberInvitationDO> wrapperForInvitation = new LambdaQueryWrapper<>();
        wrapperForInvitation.eq(OrganizationMemberInvitationDO::getOrganizationId, organizationId)
                .eq(OrganizationMemberInvitationDO::getUserId, userId)
                .eq(OrganizationMemberInvitationDO::getInvitationStatus,
                        OrganizationMemberInvitationStatusEnum.WAITING_FOR_ACCEPTANCE);
        count = organizationMemberInvitationMapper.selectCount(wrapperForInvitation);
        if (count > 0) {
            throw new DuplicateServiceException("Please do not send invitations repeatedly.");
        }

        // ??????????????????????????????
        OrganizationMemberInvitationDO organizationMemberInvitationDOForInsert =
                OrganizationMemberInvitationDO.builder().organizationId(organizationId).userId(userId)
                        .invitationTime(LocalDateTime.now())
                        .invitationStatus(OrganizationMemberInvitationStatusEnum.WAITING_FOR_ACCEPTANCE).build();
        organizationMemberInvitationMapper.insert(organizationMemberInvitationDOForInsert);

        // ??????????????????????????????
        sendInvitationNotification(organizationMemberInvitationDOForInsert.getId(), organizationId, userId);
        return getOrganizationMemberInvitation(organizationMemberInvitationDOForInsert.getId());
    }

    @Transactional
    @DistributedLock(value = ORGANIZATION_MEMBER_INVITATION_LOCK_KEY_PATTERN,
            parameters = "#{#organizationMemberInvitationId}",
            errorMessage = "Failed to acquire organizationMemberInvitation lock.")
    @Override
    public OrganizationMemberDTO acceptInvitation(Long organizationMemberInvitationId) {
        // ??????????????????????????????????????????????????????
        OrganizationMemberInvitationDTO organizationMemberInvitationDTO =
                getOrganizationMemberInvitation(organizationMemberInvitationId);
        if (!Objects.equals(OrganizationMemberInvitationStatusEnum.WAITING_FOR_ACCEPTANCE.name(),
                organizationMemberInvitationDTO.getInvitationStatus())) {
            throw new InvalidStatusServiceException("The invitationStatus must be \"WAITING_FOR_ACCEPTANCE\".");
        }

        // ??????????????????
        Long userId = organizationMemberInvitationDTO.getUserId();
        Long organizationId = organizationMemberInvitationDTO.getOrganizationId();
        OrganizationMemberDO organizationMemberDOForInsert = OrganizationMemberDO.builder().userId(userId)
                .organizationId(organizationId).build();
        organizationMemberMapper.insert(organizationMemberDOForInsert);

        // ???????????????????????????????????? ACCEPTED
        OrganizationMemberInvitationDO organizationMemberInvitationDOForUpdate =
                OrganizationMemberInvitationDO.builder().id(organizationMemberInvitationId)
                        .invitationStatus(OrganizationMemberInvitationStatusEnum.ACCEPTED).build();
        organizationMemberInvitationMapper.updateById(organizationMemberInvitationDOForUpdate);

        return getOrganizationMember(organizationMemberDOForInsert.getId());
    }

    @Override
    public OrganizationMemberDTO getOrganizationMember(Long id) {
        OrganizationMemberDO organizationMemberDO = organizationMemberMapper.selectById(id);
        if (organizationMemberDO == null) {
            throw new NotFoundServiceException("organizationMember", "id", id);
        }
        return organizationMemberAssembler.organizationMemberDOToOrganizationMemberDTO(organizationMemberDO);
    }

    @Override
    public OrganizationMemberInvitationDTO getOrganizationMemberInvitation(Long id) {
        OrganizationMemberInvitationDO organizationMemberInvitationDO =
                organizationMemberInvitationMapper.selectById(id);
        if (organizationMemberInvitationDO == null) {
            throw new NotFoundServiceException("organizationMember", "id", id);
        }
        return organizationMemberInvitationAssembler
                .organizationMemberInvitationDOToOrganizationMemberInvitationDTO(organizationMemberInvitationDO);
    }

    @Override
    public QueryResult<OrganizationMemberDTO> listOrganizationMember(OrganizationMemberQuery query) {
        LambdaQueryWrapper<OrganizationMemberDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(query.getOrganizationId() != null, OrganizationMemberDO::getOrganizationId,
                query.getOrganizationId())
                .eq(query.getDepartmentId() != null, OrganizationMemberDO::getDepartmentId,
                        query.getDepartmentId())
                .eq(query.getOrganizationPositionId() != null,
                        OrganizationMemberDO::getOrganizationPositionId, query.getOrganizationPositionId())
                .eq(query.getMemberStatus() != null, OrganizationMemberDO::getMemberStatus,
                        query.getMemberStatus());

        Page<OrganizationMemberDO> page = new Page<>(query.getPageNum(), query.getPageSize(), true);
        organizationMemberMapper.selectPage(page, wrapper);
        List<OrganizationMemberDTO> organizationMemberDTOS = page.getRecords()
                .stream().map(organizationMemberAssembler::organizationMemberDOToOrganizationMemberDTO)
                .collect(Collectors.toList());
        return new QueryResult<>(page.getTotal(), organizationMemberDTOS);
    }

    @Override
    public QueryResult<OrganizationMemberInvitationDTO> listOrganizationMemberInvitation(
            OrganizationMemberInvitationQuery query) {
        LambdaQueryWrapper<OrganizationMemberInvitationDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(query.getOrganizationId() != null, OrganizationMemberInvitationDO::getOrganizationId,
                query.getOrganizationId())
                .eq(query.getInvitationStatus() != null, OrganizationMemberInvitationDO::getInvitationStatus,
                        query.getInvitationStatus());

        Page<OrganizationMemberInvitationDO> page =
                new Page<>(query.getPageNum(), query.getPageSize(), true);
        organizationMemberInvitationMapper.selectPage(page, wrapper);
        List<OrganizationMemberInvitationDTO> organizationMemberDTOS = page.getRecords()
                .stream().map(organizationMemberInvitationAssembler::
                        organizationMemberInvitationDOToOrganizationMemberInvitationDTO)
                .collect(Collectors.toList());
        return new QueryResult<>(page.getTotal(), organizationMemberDTOS);
    }

    @Override
    @DistributedLock(value = ORGANIZATION_MEMBER_LOCK_KEY_PATTERN, parameters = "#{#request.id}",
            errorMessage = "Failed to acquire organizationMember lock.")
    @Transactional
    public OrganizationMemberDTO updateOrganizationMember(UpdateOrganizationMemberRequest request) {
        OrganizationMemberDTO organizationMemberDTO = getOrganizationMember(request.getId());

        // ??????????????????
        OrganizationMemberDO organizationMemberDOFOrUpdate =
                organizationMemberAssembler.updateOrganizationMemberRequestToOrganizationMemberDO(request);
        organizationMemberMapper.updateById(organizationMemberDOFOrUpdate);

        // ??????????????????????????????????????????????????????????????????????????????????????????
        if (request.getDepartmentId() != null || request.getOrganizationPositionId() != null) {
            if (Objects.equals(OrganizationMemberStatusEnum.ON_JOB.name(), organizationMemberDTO.getMemberStatus() )) {
                throw new InvalidStatusServiceException("The memberStatus must be \"ON_JOB\".");
            }
        }

        return getOrganizationMember(request.getId());
    }

    @Override
    public Integer clearOrganizationPositions(Long organizationPositionId) {
        LambdaUpdateWrapper<OrganizationMemberDO> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(OrganizationMemberDO::getOrganizationPositionId, organizationPositionId);
        OrganizationMemberDO organizationMemberDOForUpdate = OrganizationMemberDO.builder()
                .organizationPositionId(OrganizationPositionConstants.ORGANIZATION_POSITION_ID_WHEN_NO_POSITION).build();
        return organizationMemberMapper.update(organizationMemberDOForUpdate, wrapper);
    }


    @DistributedLock(value = ORGANIZATION_MEMBER_INVITATION_LOCK_KEY_PATTERN, parameters = "#{#id}",
            errorMessage = "Failed to acquire organizationMemberInvitation lock.")
    @Override
    public OrganizationMemberInvitationDTO rejectInvitation(Long id) {
        OrganizationMemberInvitationDTO organizationMemberInvitationDTO =
                getOrganizationMemberInvitation(id);
        // ??????????????????????????????????????????????????????
        if (!Objects.equals(organizationMemberInvitationDTO.getInvitationStatus(),
                OrganizationMemberInvitationStatusEnum.WAITING_FOR_ACCEPTANCE.name())) {
            throw new InvalidStatusServiceException("The invitationStatus must be \"WAITING_FOR_ACCEPTANCE\".");
        }

        // ??????????????????
        OrganizationMemberInvitationDO organizationMemberInvitationDOForUpdate =
                OrganizationMemberInvitationDO.builder().id(id)
                        .invitationStatus(OrganizationMemberInvitationStatusEnum.REJECTED).build();
        organizationMemberInvitationMapper.updateById(organizationMemberInvitationDOForUpdate);
        return getOrganizationMemberInvitation(id);
    }

    @DistributedLock(value = ORGANIZATION_MEMBER_INVITATION_LOCK_KEY_PATTERN, parameters = "#{#id}",
            errorMessage = "Failed to acquire organizationMemberInvitation lock.")
    @Override
    public OrganizationMemberInvitationDTO updateInvitationStatusToExpired(Long id) {
        // ???????????????????????????????????? EXPIRED
        OrganizationMemberInvitationDO organizationMemberInvitationDOForUpdate =
                OrganizationMemberInvitationDO.builder().id(id)
                        .invitationStatus(OrganizationMemberInvitationStatusEnum.EXPIRED).build();
        organizationMemberInvitationMapper.updateById(organizationMemberInvitationDOForUpdate);
        return getOrganizationMemberInvitation(id);
    }

    /**
     * ???????????????????????????
     *
     * @param organizationMemberInvitationId ????????????????????????
     * @param organizationId ????????????
     * @param userId ????????????????????????
     */
    private void sendInvitationNotification(Long organizationMemberInvitationId, Long organizationId, Long userId) {
        // ??????????????????
        OrganizationDTO organizationDTO = organizationService.getOrganization(organizationId);
        String notificationTitle = MessageFormat.format(ORGANIZATION_MEMBER_INVITATION_NOTIFICATION_TITLE_PATTERN,
                organizationDTO.getOrganizationName());

        // ??????????????????
        String notificationContentMessage = MessageFormat.format(
                ORGANIZATION_MEMBER_INVITATION_NOTIFICATION_CONTENT_PATTERN, organizationDTO.getOrganizationName(),
                ORGANIZATION_MEMBER_INVITATION_EXPIRATION_TIME);
        JSONObject notificationContentJsonObject = new JSONObject();
        notificationContentJsonObject.put("organizationId", organizationId);
        notificationContentJsonObject.put("organizationMemberInvitationId", organizationMemberInvitationId);
        notificationContentJsonObject.put("message", notificationContentMessage);
        String notificationContent = notificationContentJsonObject.toJSONString();

        // ????????????
        SendSystemNotificationRequest sendSystemNotificationPO = SendSystemNotificationRequest.builder()
                .userId(userId).notificationType(SystemNotificationTypeEnum.ORGANIZATION_INVITATION)
                .notificationTitle(notificationTitle).notificationContent(notificationContent).build();
        notificationTemplate.sendSystemNotification(sendSystemNotificationPO);
    }

}
