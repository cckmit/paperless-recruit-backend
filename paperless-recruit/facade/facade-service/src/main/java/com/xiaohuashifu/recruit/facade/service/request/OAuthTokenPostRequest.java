package com.xiaohuashifu.recruit.facade.service.request;

import com.xiaohuashifu.recruit.common.constant.AppEnum;
import com.xiaohuashifu.recruit.facade.service.manager.impl.oauth.constant.GrantTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 描述：OAuthTokenPostRequest
 *
 * @author xhsf
 * @create 2021/1/15 18:42
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class OAuthTokenPostRequest {

    @ApiModelProperty(value = "认证方式", required = true, example = "PASSWORD")
    private GrantTypeEnum grantType;

    @ApiModelProperty(value = "可以是[用户名 | 手机 | 邮箱]。PASSWORD 方式下必须", example = "13333333333")
    private String principal;

    @ApiModelProperty(value = "密码。PASSWORD 方式下必须", name = "password", example = "123456")
    private String password;

    @ApiModelProperty(value = "App 类型。OPEN_ID 方式下必须", example = "SCAU_RECRUIT_INTERVIEWEE_MP")
    private AppEnum app;

    @ApiModelProperty(value = "微信小程序的 wx.login() 接口返回值", example = "023g4b1w41lSGV2uzB1w3V3peY0g3b1Y")
    private String code;

    @ApiModelProperty(value = "手机号码。SMS 方式下必须", example = "13333333333")
    private String phone;

    @ApiModelProperty(value = "验证码，6位整数。SMS 方式下必须", example = "123456")
    private String authCode;

    @ApiModelProperty(value = "刷新令牌。REFRESH_TOKEN 方式下必须", example = "xx.xx.xx")
    private String refreshToken;
}
