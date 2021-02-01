package com.xiaohuashifu.recruit.organization.api.dto;

import com.xiaohuashifu.recruit.organization.api.constant.OrganizationMemberInvitationStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 描述：组织成员邀请传输对象
 *
 * @author xhsf
 * @create 2020/12/15 16:26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationMemberInvitationDTO implements Serializable {

    /**
     * 组织成员邀请编号
     */
    private Long id;

    /**
     * 发送邀请的组织
     */
    private Long organizationId;

    /**
     * 邀请的目标用户主体
     */
    private Long userId;

    /**
     * 邀请的时间
     */
    private LocalDateTime invitationTime;

    /**
     * @see OrganizationMemberInvitationStatusEnum
     * 邀请的状态
     */
    private String invitationStatus;

}
