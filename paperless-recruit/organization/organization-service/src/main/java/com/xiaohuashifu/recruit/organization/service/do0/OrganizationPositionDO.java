package com.xiaohuashifu.recruit.organization.service.do0;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 描述：组织职位数据对象
 *
 * @author xhsf
 * @create 2020/12/13 19:25
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationPositionDO {
    private Long id;
    private Long organizationId;
    private String positionName;
    private Integer priority;
    private String createTime;
    private String updateTime;
}
