package com.xiaohuashifu.recruit.facade.service.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * 描述：组织 VO
 *
 * @author xhsf
 * @create 2021/1/9 14:55
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationVO {
    /**
     * 组织编号
     */
    private Long id;

    /**
     * 组织名
     */
    private String organizationName;

    /**
     * 组织名缩写
     */
    private String abbreviationOrganizationName;

    /**
     * 组织介绍
     */
    private String introduction;

    /**
     * 组织 logo
     */
    private String logoUrl;

    /**
     * 组织成员数
     */
    private String memberNumber;

    /**
     * 组织标签
     */
    private Set<String> labels;

    /**
     * 组织是否有效
     */
    private Boolean available;
}
