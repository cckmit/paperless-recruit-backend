package com.xiaohuashifu.recruit.organization.service.do0;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 描述：组织核心成员数据对象
 *
 * @author xhsf
 * @create 2020/12/15 16:26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("organization_core_member")
public class OrganizationCoreMemberDO {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long organizationId;
    private Long organizationMemberId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
