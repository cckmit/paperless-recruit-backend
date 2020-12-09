package com.xiaohuashifu.recruit.external.service.service;

import com.xiaohuashifu.recruit.common.constant.AppEnum;
import com.xiaohuashifu.recruit.external.api.constant.WeChatMpSubscribeMessageTemplateStatusEnum;
import com.xiaohuashifu.recruit.external.api.dto.WeChatMpSubscribeMessageTemplateDTO;
import com.xiaohuashifu.recruit.external.api.query.WeChatMpSubscribeMessageTemplateQuery;
import com.xiaohuashifu.recruit.external.api.service.WeChatMpSubscribeMessageTemplateService;
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

    private WeChatMpSubscribeMessageTemplateService wechatMpSubscribeMessageTemplateService;

    @Before
    public void setUp() throws Exception {
        ApplicationConfig application = new ApplicationConfig();
        application.setName("wechatMpSubscribeMessageTemplateServiceTest");
        ReferenceConfig<WeChatMpSubscribeMessageTemplateService> reference = new ReferenceConfig<>();
        reference.setUrl("dubbo://127.0.0.1:20883/com.xiaohuashifu.recruit.external.api.service." +
                "WeChatMpSubscribeMessageTemplateService");
        reference.setApplication(application);
        reference.setInterface(WeChatMpSubscribeMessageTemplateService.class);
        reference.setTimeout(10000000);
        wechatMpSubscribeMessageTemplateService = reference.get();
    }

    @Test
    public void saveWechatMpSubscribeMessageTemplate() {
        System.out.println(wechatMpSubscribeMessageTemplateService.saveWeChatMpSubscribeMessageTemplate(
                new WeChatMpSubscribeMessageTemplateDTO.Builder()
                        .app(AppEnum.SCAU_RECRUIT_INTERVIEWEE_MP)
                        .templateId("PwgUsyL54zBnWyB1bHDuOP6Oc8EAG5GvQplx8E2kU")
                        .title("面试结果通知")
                        .type("预约/报名")
                        .description("招新面试结果通知")
                        .status(WeChatMpSubscribeMessageTemplateStatusEnum.AVAILABLE)
                        .build()));
    }

    @Test
    public void getWechatMpSubscribeMessageTemplate() {
        System.out.println(wechatMpSubscribeMessageTemplateService.getWeChatMpSubscribeMessageTemplate(1L));
    }

    @Test
    public void testGetWechatMpSubscribeMessageTemplate() {
        System.out.println(wechatMpSubscribeMessageTemplateService.listWeChatMpSubscribeMessageTemplates(
                new WeChatMpSubscribeMessageTemplateQuery.Builder().pageNum(1L).pageSize(50L).build()
        ));
    }

    @Test
    public void updateWechatMpSubscribeMessageTemplate() {
        System.out.println(wechatMpSubscribeMessageTemplateService.updateWeChatMpSubscribeMessageTemplate(
                new WeChatMpSubscribeMessageTemplateDTO.Builder()
//                        .id(1L)
//                        .status(TriStatus.DEPRECATED)
                        .build()));
    }

}