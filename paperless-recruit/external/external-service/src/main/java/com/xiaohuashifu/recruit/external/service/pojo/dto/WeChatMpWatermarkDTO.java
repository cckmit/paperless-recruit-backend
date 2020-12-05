package com.xiaohuashifu.recruit.external.service.pojo.dto;

import java.time.LocalDateTime;

/**
 * 描述：WeChatMpUserInfoDTO 里的 Watermark
 *
 * @author xhsf
 * @create 2020/12/4 20:46
 */
public class WeChatMpWatermarkDTO {
    private String appId;
    private LocalDateTime timestamp;

    public WeChatMpWatermarkDTO() {
    }

    public WeChatMpWatermarkDTO(String appId, LocalDateTime timestamp) {
        this.appId = appId;
        this.timestamp = timestamp;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "WeChatMpWatermarkDTO{" +
                "appId='" + appId + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
