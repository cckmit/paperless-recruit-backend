package com.xiaohuashifu.recruit.facade.service.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

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
@ApiModel
public class TokenVO implements Serializable {

    /**
     * 用户编号
     */
    @ApiModelProperty(value = "用户编号", example = "123")
    private Long userId;

    /**
     * 令牌
     */
    @ApiModelProperty(value = "令牌", example = "xx.xx.xx")
    private String accessToken;

    /**
     * 刷新令牌
     */
    @ApiModelProperty(value = "刷新令牌", example = "xx.xx.xx")
    private String refreshToken;

    /**
     * 令牌类型
     */
    @ApiModelProperty(value = "令牌类型", allowableValues = "[bearer]", example = "bearer")
    private String tokenType;

    /**
     * 令牌过期时间
     */
    @ApiModelProperty(value = "令牌过期时间", example = "1611315669")
    private Long accessTokenExpireTime;

    /**
     * 刷新令牌过期时间
     */
    @ApiModelProperty(value = "刷新令牌过期时间", example = "1611315669")
    private Long refreshTokenExpireTime;
}
