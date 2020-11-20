package com.xiaohuashifu.recruit.external.service.service;

import com.xiaohuashifu.recruit.common.constant.App;
import com.xiaohuashifu.recruit.external.api.service.WechatMpService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.junit.Before;
import org.junit.Test;

/**
 * 描述：
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/20 14:40
 */
public class WechatMpServiceImplTest {

    private WechatMpService wechatMpService;

    @Before
    public void before() {
        ApplicationConfig application = new ApplicationConfig();
        application.setName("wechatMpServiceTest");
        ReferenceConfig<WechatMpService> reference = new ReferenceConfig<>();
        reference.setUrl("dubbo://127.0.0.1:20883/com.xiaohuashifu.recruit.external.api.service.WechatMpService");
        reference.setApplication(application);
        reference.setInterface(WechatMpService.class);
        reference.setTimeout(10000000);
        wechatMpService = reference.get();
    }

    @Test
    public void getOpenid() {
        System.out.println(wechatMpService.getOpenid("043WLGll2yCLZ541iKkl2lC1RC3WLGlC", App.SCAU_RECRUIT_INTERVIEWEE_MP));
    }

    @Test
    public void sendTemplateMessage() {
    }
}