package com.xiaohuashifu.recruit.organization.service.do0;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * 描述：部门
 *
 * @author xhsf
 * @create 2020/12/7 13:10
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDO {
    private Long id;
    private Long organizationId;
    private String departmentName;
    private String abbreviationDepartmentName;
    private String introduction;
    private String logoUrl;
    private Integer memberNumber;
    private Set<String> labels;
    private Boolean deactivated;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
