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
 * 描述：专业表映射对象
 *
 * @author: xhsf
 * @create: 2020/10/30 15:05
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("major")
public class MajorDO {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long collegeId;
    private String majorName;
    @TableField("is_deactivated")
    private Boolean deactivated;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
