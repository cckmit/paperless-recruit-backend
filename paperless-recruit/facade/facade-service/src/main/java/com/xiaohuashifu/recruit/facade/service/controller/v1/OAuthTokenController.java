package com.xiaohuashifu.recruit.facade.service.controller.v1;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.xiaohuashifu.recruit.facade.service.authorize.UserContext;
import com.xiaohuashifu.recruit.facade.service.manager.OAuthTokenManager;
import com.xiaohuashifu.recruit.facade.service.request.OAuthTokenPostRequest;
import com.xiaohuashifu.recruit.facade.service.vo.AccessTokenVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * 描述：oauth 控制器
 *
 * @author xhsf
 * @create 2021/1/14 14:52
 */
@ApiSupport(author = "XHSF")
@Api(tags = "OAuth 认证")
@RestController
@RequestMapping("oauth/token")
public class OAuthTokenController {

    private final OAuthTokenManager oAuthTokenManager;

    private final UserContext userContext;

    public OAuthTokenController(OAuthTokenManager oAuthTokenManager, UserContext userContext) {
        this.oAuthTokenManager = oAuthTokenManager;
        this.userContext = userContext;
    }

    /**
     * 认证接口
     *
     * @param httpHeaders HttpHeaders
     * @param request 请求
     * @return AccessTokenVO 令牌
     */
    @ApiOperation(value = "Oauth 认证")
    @ApiResponses(@ApiResponse(code = 201, message = "认证成功", response = AccessTokenVO.class))
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccessTokenVO post(@RequestHeader HttpHeaders httpHeaders, @RequestBody OAuthTokenPostRequest request) {
        return oAuthTokenManager.authenticate(httpHeaders, request);
    }

    /**
     * 撤销认证
     */
    @ApiOperation(value = "Oauth 撤销认证", notes = "会删除用户的 refresh_token")
    @ApiResponses(@ApiResponse(code = 204, message = "撤销认证成功"))
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete() {
        oAuthTokenManager.logout(userContext.getId());
    }

}
