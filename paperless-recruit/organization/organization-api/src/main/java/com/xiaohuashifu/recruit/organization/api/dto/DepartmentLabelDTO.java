package com.xiaohuashifu.recruit.organization.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 描述：部门标签
 *
 * @author xhsf
 * @create 2020/12/7 13:10
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentLabelDTO implements Serializable {

    /**
     * 部门标签编号
     */
    private Long id;

    /**
     * 标签名
     */
    private String labelName;

    /**
     * 引用次数
     */
    private Long referenceNumber;

    /**
     * 标签是否可用
     */
    private Boolean available;

}
