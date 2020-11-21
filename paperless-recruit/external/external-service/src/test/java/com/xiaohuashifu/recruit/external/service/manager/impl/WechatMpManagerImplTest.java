package com.xiaohuashifu.recruit.external.service.manager.impl;

import com.xiaohuashifu.recruit.common.constant.App;
import com.xiaohuashifu.recruit.external.service.ExternalServiceApplicationTests;
import com.xiaohuashifu.recruit.external.service.manager.WechatMpManager;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * 描述：
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/21 18:45
 */
public class WechatMpManagerImplTest extends ExternalServiceApplicationTests {

    @Autowired
    private WechatMpManager wechatMpManager;

    @Test
    public void getCode2Session() {
    }

    @Test
    public void getAccessToken() {
        System.out.println(wechatMpManager.getAccessToken(App.SCAU_RECRUIT_INTERVIEWEE_MP));
    }

    @Test
    public void refreshAccessToken() {
        System.out.println(wechatMpManager.refreshAccessToken(App.SCAU_RECRUIT_INTERVIEWEE_MP));
    }

}