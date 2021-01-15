package com.xiaohuashifu.recruit.facade.service.controller.v1.oauth.token;

import com.xiaohuashifu.recruit.facade.service.controller.v1.oauth.token.constant.GrantTypeEnum;
import com.xiaohuashifu.recruit.facade.service.controller.v1.oauth.token.processor.AuthenticationProcessor;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 描述：oauth 控制器
 *
 * @author xhsf
 * @create 2021/1/14 14:52
 */
@RestController
@RequestMapping("oauth/token")
public class OAuthTokenController {

    private final List<AuthenticationProcessor> authenticationProcessorList;

    public OAuthTokenController(List<AuthenticationProcessor> authenticationProcessorList) {
        this.authenticationProcessorList = authenticationProcessorList;
    }

    /**
     * 认证接口
     *
     * 支持 [PASSWORD, OPEN_ID, SMS, REFRESH_TOKEN] 四种认证方式
     *
     * PASSWORD:
     *  参数：{
     *     "grantType": "PASSWORD",
     *     "principal": 用户名 | 手机 | 邮箱,
     *     "password": 密码
     * }
     *
     * OPEN_ID:
     *  参数：{
     *     "grantType": "OPEN_ID",
     *     "app": SCAU_RECRUIT_INTERVIEWEE_MP | SCAU_RECRUIT_INTERVIEWER_MP
     *              @see com.xiaohuashifu.recruit.common.constant.AppEnum
     *     "code": 微信小程序的 wx.login() 接口返回值
     * }
     *
     * SMS:
     *  参数：{
     *     "grantType": "SMS",
     *     "phone": 手机号码
     *     "authCode": 短信验证码
     * }
     *
     * REFRESH_TOKEN:
     *  参数：{
     *     "grantType": "REFRESH_TOKEN",
     *     "refreshToken": 刷新令牌
     * }
     *
     * @param httpHeaders HttpHeaders
     * @param body 请求体
     * @return AccessTokenVO 令牌
     */
    @ApiParam
    @PostMapping
    public Object post(@RequestHeader HttpHeaders httpHeaders, @RequestBody Map<String, String> body) {
        String grantType = body.remove("grantType");
        GrantTypeEnum grantTypeEnum = GrantTypeEnum.valueOf(grantType);
        body.put("grant_type", grantTypeEnum.getGrantType());
        for (AuthenticationProcessor authenticationProcessor : authenticationProcessorList) {
            if (authenticationProcessor.isSupport(grantTypeEnum)) {
                return authenticationProcessor.authenticate(httpHeaders, body);
            }
        }

        // 不支持的认证类型
        throw new InvalidGrantException("Unsupported grant type.");
    }

}
