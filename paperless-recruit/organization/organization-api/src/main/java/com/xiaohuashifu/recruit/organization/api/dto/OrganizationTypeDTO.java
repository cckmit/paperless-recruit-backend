package com.xiaohuashifu.recruit.organization.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 描述：组织类型
 *
 * @author xhsf
 * @create 2021/3/9 14:13
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationTypeDTO implements Serializable {

    /**
     * 组织类型编号
     */
    private Long id;

    /**
     * 组织类型名
     */
    private String typeName;

}
