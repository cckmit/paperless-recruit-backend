package com.xiaohuashifu.recruit.organization.service.do0;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import com.xiaohuashifu.recruit.common.mybatis.type.StringListTypeHandler;
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
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private String organizationName;
    private String abbreviationOrganizationName;
    private String introduction;
    private String logoUrl;
    private Integer numberOfMembers;
    private Integer numberOfDepartments;
    @TableField(typeHandler = StringListTypeHandler.class)
    private List<String> labels;
    @TableField("is_available")
    private Boolean available;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
