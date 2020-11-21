package com.xiaohuashifu.recruit.external.service.service;

import com.xiaohuashifu.recruit.common.constant.App;
import com.xiaohuashifu.recruit.external.api.dto.SubscribeTemplateDataDTO;
import com.xiaohuashifu.recruit.external.api.dto.SubscribeMessageDTO;
import com.xiaohuashifu.recruit.external.api.service.WechatMpService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

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
    public void sendSubscribeMessage() {
        SubscribeMessageDTO subscribeMessageDTO = new SubscribeMessageDTO();
        subscribeMessageDTO.setTemplate_id("PwgUsyL54zBnWyB1bHDuOP6Oc8EAG5GvQplx8E2kU-s");
        Map<String, SubscribeTemplateDataDTO> map = new HashMap<>();
        map.put("thing1", new SubscribeTemplateDataDTO("吴嘉贤"));
        map.put("thing2", new SubscribeTemplateDataDTO("华农科联"));
        map.put("thing3", new SubscribeTemplateDataDTO("自科部"));
        map.put("phrase4", new SubscribeTemplateDataDTO("一轮面试"));
        map.put("phrase5", new SubscribeTemplateDataDTO("通过"));
        subscribeMessageDTO.setData(map);
        System.out.println(wechatMpService.sendSubscribeMessage(
                App.SCAU_RECRUIT_INTERVIEWEE_MP, 1L, subscribeMessageDTO));
    }
}