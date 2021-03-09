package com.xiaohuashifu.recruit.organization.service.do0;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 描述：组织类型
 *
 * @author xhsf
 * @create 2021/3/9 14:16
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "organization_type")
public class OrganizationTypeDO {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String typeName;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
