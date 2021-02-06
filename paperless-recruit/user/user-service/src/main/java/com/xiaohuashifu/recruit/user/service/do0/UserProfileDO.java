package com.xiaohuashifu.recruit.user.service.do0;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 描述：用户信息DO
 *
 * @author: xhsf
 * @create: 2020/10/30 15:05
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("user_profile")
public class UserProfileDO {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private String fullName;
    private String studentNumber;
    private Long collegeId;
    private Long majorId;
    private String introduction;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
