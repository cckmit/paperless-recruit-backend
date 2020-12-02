package com.xiaohuashifu.recruit.user.service.service;

import com.xiaohuashifu.recruit.user.api.service.AuthOpenidService;
import com.xiaohuashifu.recruit.common.constant.App;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.junit.Before;
import org.junit.Test;

/**
 * 描述：
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/21 01:07
 */
public class AuthOpenidServiceImplTest {

    private AuthOpenidService authOpenidService;
    @Before
    public void before() {
        ApplicationConfig application = new ApplicationConfig();
        application.setName("smsServiceTest");
        ReferenceConfig<AuthOpenidService> reference = new ReferenceConfig<>();
        reference.setUrl("dubbo://127.0.0.1:20881/com.xiaohuashifu.recruit.user.api.service.AuthOpenidService");
        reference.setApplication(application);
        reference.setInterface(AuthOpenidService.class);
        reference.setTimeout(10000000);
        authOpenidService = reference.get();
    }

    @Test
    public void bindAuthOpenidForWechatMp() {
        System.out.println(authOpenidService.bindAuthOpenidForWechatMp(1L, App.SCAU_RECRUIT_INTERVIEWEE_MP,
                "003XGYZv3GLqlV2QF31w3yt3Mo2XGYZ0"));
    }

    @Test
    public void checkAuthOpenidForWechatMp() {
        System.out.println(authOpenidService.checkAuthOpenidForWechatMp(App.SCAU_RECRUIT_INTERVIEWEE_MP,
                "073kJA2w36gHrV28Xb3w3gusrp1kJA2P"));
    }

    @Test
    public void getOpenid() {
        System.out.println(authOpenidService.getOpenid(App.SCAU_RECRUIT_INTERVIEWEE_MP, 1L));
    }
}