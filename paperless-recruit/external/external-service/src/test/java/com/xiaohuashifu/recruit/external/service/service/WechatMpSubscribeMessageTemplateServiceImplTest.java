package com.xiaohuashifu.recruit.external.service.service;

import com.xiaohuashifu.recruit.common.constant.AppEnum;
import com.xiaohuashifu.recruit.common.constant.TriStatusEnum;
import com.xiaohuashifu.recruit.external.api.dto.WechatMpSubscribeMessageTemplateDTO;
import com.xiaohuashifu.recruit.external.api.query.WechatMpSubscribeMessageTemplateQuery;
import com.xiaohuashifu.recruit.external.api.service.WechatMpSubscribeMessageTemplateService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.junit.Before;
import org.junit.Test;

/**
 * 描述：
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/22 13:26
 */
public class WechatMpSubscribeMessageTemplateServiceImplTest {

    private WechatMpSubscribeMessageTemplateService wechatMpSubscribeMessageTemplateService;

    @Before
    public void setUp() throws Exception {
        ApplicationConfig application = new ApplicationConfig();
        application.setName("wechatMpSubscribeMessageTemplateServiceTest");
        ReferenceConfig<WechatMpSubscribeMessageTemplateService> reference = new ReferenceConfig<>();
        reference.setUrl("dubbo://127.0.0.1:20883/com.xiaohuashifu.recruit.external.api.service." +
                "WechatMpSubscribeMessageTemplateService");
        reference.setApplication(application);
        reference.setInterface(WechatMpSubscribeMessageTemplateService.class);
        reference.setTimeout(10000000);
        wechatMpSubscribeMessageTemplateService = reference.get();
    }

    @Test
    public void saveWechatMpSubscribeMessageTemplate() {
        System.out.println(wechatMpSubscribeMessageTemplateService.saveWechatMpSubscribeMessageTemplate(
                new WechatMpSubscribeMessageTemplateDTO.Builder()
                        .app(AppEnum.SCAU_RECRUIT_INTERVIEWEE_MP)
                        .templateId("PwgUsyL54zBnWyB1bHDuOP6Oc8EAG5GvQplx8E2kU")
                        .title("面试结果通知")
                        .type("预约/报名")
                        .description("招新面试结果通知")
                        .status(TriStatusEnum.AVAILABLE)
                        .build()));
    }

    @Test
    public void getWechatMpSubscribeMessageTemplate() {
        System.out.println(wechatMpSubscribeMessageTemplateService.getWechatMpSubscribeMessageTemplate(1L));
    }

    @Test
    public void testGetWechatMpSubscribeMessageTemplate() {
        System.out.println(wechatMpSubscribeMessageTemplateService.getWechatMpSubscribeMessageTemplate(
                new WechatMpSubscribeMessageTemplateQuery.Builder().app(AppEnum.SCAU_RECRUIT_INTERVIEWEE_MP).build()
        ));
    }

    @Test
    public void updateWechatMpSubscribeMessageTemplate() {
        System.out.println(wechatMpSubscribeMessageTemplateService.updateWechatMpSubscribeMessageTemplate(
                new WechatMpSubscribeMessageTemplateDTO.Builder()
//                        .id(1L)
//                        .status(TriStatus.DEPRECATED)
                        .build()));
    }

}