package com.xiaohuashifu.recruit.organization.service.do0;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

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
@TableName("organization")
public class OrganizationDO {
    private Long id;
    private Long userId;
    private String organizationName;
    private String abbreviationOrganizationName;
    private String introduction;
    private String logoUrl;
    private Integer numberOfMembers;
    private Integer numberOfDepartments;
    private List<String> labels;
    private Boolean available;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
