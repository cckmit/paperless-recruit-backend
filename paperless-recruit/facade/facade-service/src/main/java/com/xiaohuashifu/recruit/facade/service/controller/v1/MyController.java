package com.xiaohuashifu.recruit.facade.service.controller.v1;

import com.xiaohuashifu.recruit.facade.service.authorize.UserInfo;
import com.xiaohuashifu.recruit.facade.service.manager.MyManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述：My 页面
 *
 * @author xhsf
 * @create 2021/1/9 00:43
 */
@RestController
@RequestMapping("my")
public class MyController {

    private final MyManager myManager;

    public MyController(MyManager myManager) {
        this.myManager = myManager;
    }

    /**
     * 获取用户个人信息
     *
     * @param userInfo UserInfo
     * @return 用户个人信息
     */
    @GetMapping
    public Object getPage(UserInfo userInfo) {
        return myManager.getPage(userInfo.getId());
    }

}
