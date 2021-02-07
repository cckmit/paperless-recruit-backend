package com.xiaohuashifu.recruit.external.service.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 描述: 封装 Session 的各种属性
 *
 * @author xhsf
 * @create 2019-02-28 16:46
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeChatMpSessionDTO {
    private String openId;
    private String sessionKey;
    private String unionId;
    private Integer errorCode;
    private String errorMessage;
}
