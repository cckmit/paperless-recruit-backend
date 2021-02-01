package com.xiaohuashifu.recruit.organization.service.do0;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 描述：组织标签
 *
 * @author xhsf
 * @create 2020/12/7 13:10
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("organization_label")
public class OrganizationLabelDO {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String labelName;
    private Long referenceNumber;
    @TableField("is_available")
    private Boolean available;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
