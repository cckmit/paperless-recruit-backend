package com.xiaohuashifu.recruit.organization.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 描述：组织职位传输对象
 *
 * @author xhsf
 * @create 2020/12/13 19:25
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationPositionDTO implements Serializable {

    /**
     * 组织职位编号
     */
    private Long id;

    /**
     * 组织编号
     */
    private Long organizationId;

    /**
     * 职位名
     */
    private String positionName;

    /**
     * 职位优先级，0最高，9最低
     */
    private Integer priority;

}
