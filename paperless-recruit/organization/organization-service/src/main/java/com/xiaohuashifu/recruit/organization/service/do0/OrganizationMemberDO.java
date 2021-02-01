package com.xiaohuashifu.recruit.organization.service.do0;

import com.xiaohuashifu.recruit.organization.api.constant.OrganizationMemberStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 描述：组织成员数据对象
 *
 * @author xhsf
 * @create 2020/12/15 16:26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationMemberDO {
    private Long id;
    private Long userId;
    private Long organizationId;
    private Long departmentId;
    private Long organizationPositionId;
    private OrganizationMemberStatusEnum memberStatus;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
