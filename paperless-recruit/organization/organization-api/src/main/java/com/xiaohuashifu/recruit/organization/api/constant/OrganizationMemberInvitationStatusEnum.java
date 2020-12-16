package com.xiaohuashifu.recruit.organization.api.constant;

/**
 * 描述：组织成员邀请状态
 *
 * @author xhsf
 * @create 2020/12/15 16:46
 */
public enum OrganizationMemberInvitationStatusEnum {

    /**
     * 等待接受
     */
    WAITING_FOR_ACCEPTANCE,

    /**
     * 被拒绝
     */
    REJECTED,

    /**
     * 邀请已失效
     */
    EXPIRED,

    /**
     * 已接受
     */
    ACCEPTED

}
