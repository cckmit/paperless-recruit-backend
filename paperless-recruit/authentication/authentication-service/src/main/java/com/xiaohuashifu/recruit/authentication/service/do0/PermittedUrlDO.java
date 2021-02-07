package com.xiaohuashifu.recruit.authentication.service.do0;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 描述：PermittedUrlDO
 *
 * @author xhsf
 * @create 2020/11/27 17:27
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("permitted_url")
public class PermittedUrlDO {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String url;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
