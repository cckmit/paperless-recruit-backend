package com.xiaohuashifu.recruit.user.service.service;

import com.xiaohuashifu.recruit.user.api.service.AuthOpenIdService;
import com.xiaohuashifu.recruit.common.constant.AppEnum;
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
public class AuthOpenIdServiceImplTest {

    private AuthOpenIdService authOpenIdService;

    @Before
    public void before() {
        ApplicationConfig application = new ApplicationConfig();
        application.setName("smsServiceTest");
        ReferenceConfig<AuthOpenIdService> reference = new ReferenceConfig<>();
        reference.setUrl("dubbo://127.0.0.1:20881/com.xiaohuashifu.recruit.user.api.service.AuthOpenIdService");
        reference.setApplication(application);
        reference.setInterface(AuthOpenIdService.class);
        reference.setTimeout(10000000);
        authOpenIdService = reference.get();
    }

    @Test
    public void bindAuthOpenIdForWeChatMp() {
        System.out.println(authOpenIdService.bindAuthOpenIdForWeChatMp(1L, AppEnum.SCAU_RECRUIT_INTERVIEWEE_MP,
                "003XGYZv3GLqlV2QF31w3yt3Mo2XGYZ0"));
    }

    @Test
    public void checkAuthOpenIdForWeChatMp() {
        System.out.println(authOpenIdService.checkAuthOpenIdForWeChatMp(AppEnum.SCAU_RECRUIT_INTERVIEWEE_MP,
                "073qXK0w3VdoqV2nfL1w39utrE4qXK0R"));
    }

    @Test
    public void getOpenId() {
        System.out.println(authOpenIdService.getOpenId(AppEnum.SCAU_RECRUIT_INTERVIEWEE_MP, 1L));
    }
}