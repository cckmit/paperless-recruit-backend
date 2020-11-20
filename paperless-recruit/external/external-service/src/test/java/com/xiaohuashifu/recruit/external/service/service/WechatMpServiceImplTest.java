package com.xiaohuashifu.recruit.external.service.service;

import com.xiaohuashifu.recruit.external.api.service.WechatMpService;
import com.xiaohuashifu.recruit.external.api.service.constant.WechatMp;
import com.xiaohuashifu.recruit.user.api.service.UserService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

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
        System.out.println(wechatMpService.getOpenid("003dNtll2YVNZ54nFqnl2OasQW2dNtlt", WechatMp.SCAU_RECRUIT_INTERVIEWEE_MP));
    }

    @Test
    public void sendTemplateMessage() {
    }
}