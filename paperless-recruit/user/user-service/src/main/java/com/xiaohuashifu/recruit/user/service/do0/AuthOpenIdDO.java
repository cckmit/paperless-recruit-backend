package com.xiaohuashifu.recruit.user.service.do0;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xiaohuashifu.recruit.common.constant.AppEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 描述：AuthOpenId DO
 *
 * @author: xhsf
 * @create: 2020/10/30 15:05
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("auth_open_id")
public class AuthOpenIdDO {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private AppEnum appName;
    private String openId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
