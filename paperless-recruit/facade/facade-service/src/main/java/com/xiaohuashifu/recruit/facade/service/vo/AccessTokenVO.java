package com.xiaohuashifu.recruit.facade.service.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 描述：令牌
 *
 * @author xhsf
 * @create 2021/1/14 19:49
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccessTokenVO {

    /**
     * 令牌
     */
    private String accessToken;

    /**
     * 令牌类型
     */
    private String tokenType;

    /**
     * 过期时间
     */
    private Long expireTime;
}
