package com.xiaohuashifu.recruit.facade.service.controller.v1;

import com.xiaohuashifu.recruit.facade.service.authorize.UserContext;
import com.xiaohuashifu.recruit.facade.service.manager.MyManager;
import com.xiaohuashifu.recruit.facade.service.vo.MyVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述：My 页面
 *
 * @author xhsf
 * @create 2021/1/9 00:43
 */
@Api(tags = "我的页面")
@RestController
@RequestMapping("my")
public class MyController {

    private final MyManager myManager;

    private final UserContext userContext;

    public MyController(MyManager myManager, UserContext userContext) {
        this.myManager = myManager;
        this.userContext = userContext;
    }

    /**
     * 获取用户个人信息
     *
     * @return 用户个人信息
     */
    @ApiOperation(value = "获取我的页面")
    @GetMapping
    public MyVO getPage() {
        return myManager.getPage(userContext.getId());
    }

}
