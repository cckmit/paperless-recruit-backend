package com.xiaohuashifu.recruit.external.service.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 描述：WeChatMpUserInfoDTO 里的 Watermark
 *
 * @author xhsf
 * @create 2020/12/4 20:46
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeChatMpWatermarkDTO {
    private String appId;
    private LocalDateTime timestamp;
}
