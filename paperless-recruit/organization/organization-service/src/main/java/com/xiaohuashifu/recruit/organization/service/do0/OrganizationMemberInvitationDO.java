package com.xiaohuashifu.recruit.organization.service.do0;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xiaohuashifu.recruit.organization.api.constant.OrganizationMemberInvitationStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 描述：组织成员邀请数据对象
 *
 * @author xhsf
 * @create 2020/12/15 16:26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("organization_member_invitation")
public class OrganizationMemberInvitationDO {
    private Long id;
    private Long organizationId;
    private Long userId;
    private LocalDateTime invitationTime;
    private OrganizationMemberInvitationStatusEnum invitationStatus;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
