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
 * 描述：学院DO
 *
 * @author: xhsf
 * @create: 2020/10/30 15:05
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("college")
public class CollegeDO {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String collegeName;
    @TableField("is_deactivated")
    private Boolean deactivated;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
