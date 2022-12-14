package com.xiaohuashifu.recruit.user.service.do0;

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
 * 描述：权限DO
 *
 * @author: xhsf
 * @create: 2020/11/12 19:42
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("permission")
public class PermissionDO {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long parentPermissionId;
    private String permissionName;
    private String authorizationUrl;
    private String description;
    @TableField("is_available")
    private Boolean available;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
