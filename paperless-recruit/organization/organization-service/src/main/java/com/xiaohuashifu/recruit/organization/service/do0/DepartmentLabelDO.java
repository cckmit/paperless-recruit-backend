package com.xiaohuashifu.recruit.organization.service.do0;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
public class DepartmentLabelDO {
    private Long id;
    private String labelName;
    private Long referenceNumber;
    private Boolean available;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
