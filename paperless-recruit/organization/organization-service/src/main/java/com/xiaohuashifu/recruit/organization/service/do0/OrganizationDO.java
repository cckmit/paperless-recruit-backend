package com.xiaohuashifu.recruit.organization.service.do0;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
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
@TableName(value = "organization", autoResultMap = true)
public class OrganizationDO {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private String organizationName;
    private String introduction;
    private String logoUrl;
    private String organizationType;
    private String size;
    private String address;
    private String website;
    @TableField(typeHandler = FastjsonTypeHandler.class)
    private Set<String> labels;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
