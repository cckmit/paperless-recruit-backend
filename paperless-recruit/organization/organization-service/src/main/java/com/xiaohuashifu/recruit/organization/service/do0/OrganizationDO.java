package com.xiaohuashifu.recruit.organization.service.do0;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * 描述：组织
 *
 * @author xhsf
 * @create 2020/12/7 13:10
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationDO {
    private Long id;
    private Long userId;
    private String organizationName;
    private String abbreviationOrganizationName;
    private String introduction;
    private String logoUrl;
    private Integer memberNumber;
    private Integer numberOfDepartments;
    private Set<String> labels;
    private Boolean available;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
