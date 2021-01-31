package com.xiaohuashifu.recruit.facade.service.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.xiaohuashifu.recruit.facade.service.authorize.UserContext;
import com.xiaohuashifu.recruit.facade.service.manager.TokenManager;
import com.xiaohuashifu.recruit.facade.service.request.TokenPostRequest;
import com.xiaohuashifu.recruit.facade.service.vo.TokenVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * 描述：令牌控制器
 *
 * @author xhsf
 * @create 2021/1/14 14:52
 */
@ApiSupport(author = "XHSF")
@Api(tags = "令牌")
@RestController
public class TokenController {

    private final TokenManager tokenManager;

    private final UserContext userContext;

    public TokenController(TokenManager tokenManager, UserContext userContext) {
        this.tokenManager = tokenManager;
        this.userContext = userContext;
    }

    @ApiOperation(value = "创建令牌")
    @PostMapping("tokens")
    @ResponseStatus(HttpStatus.CREATED)
    public TokenVO post(@RequestHeader HttpHeaders httpHeaders, @RequestBody TokenPostRequest request) {
        return tokenManager.authenticate(httpHeaders, request);
    }

    @ApiOperation(value = "删除令牌", notes = "用于退出登录，将会使得 Refresh Token 失效")
    @DeleteMapping("tokens")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete() {
        tokenManager.logout(userContext.getUserId());
    }

}
