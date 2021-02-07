package com.xiaohuashifu.recruit.external.service.service;

import com.xiaohuashifu.recruit.common.constant.AppEnum;
import com.xiaohuashifu.recruit.external.api.request.SendWeChatMpSubscribeMessageDataRequest;
import com.xiaohuashifu.recruit.external.api.request.SendWeChatMpSubscribeMessageRequest;
import com.xiaohuashifu.recruit.external.api.service.WeChatMpService;
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
public class WeChatMpServiceImplTest {

    private WeChatMpService weChatMpService;

    @Before
    public void before() {
        ApplicationConfig application = new ApplicationConfig();
        application.setName("weChatMpServiceTest");
        ReferenceConfig<WeChatMpService> reference = new ReferenceConfig<>();
        reference.setUrl("dubbo://127.0.0.1:20883/com.xiaohuashifu.recruit.external.api.service.WeChatMpService");
        reference.setApplication(application);
        reference.setInterface(WeChatMpService.class);
        reference.setTimeout(10000000);
        weChatMpService = reference.get();
    }

    @Test
    public void getOpenId() {
        System.out.println(weChatMpService.getOpenId("043WLGll2yCLZ541iKkl2lC1RC3WLGlC", AppEnum.SCAU_RECRUIT_INTERVIEWEE_MP));
    }

    @Test
    public void sendSubscribeMessage() {
        SendWeChatMpSubscribeMessageRequest sendWeChatMpSubscribeMessagePO = new SendWeChatMpSubscribeMessageRequest();
        sendWeChatMpSubscribeMessagePO.setTemplateId("PwgUsyL54zBnWyB1bHDuOP6Oc8EAG5GvQplx8E2kU-s");
        Map<String, SendWeChatMpSubscribeMessageDataRequest> map = new HashMap<>();
        map.put("thing1", new SendWeChatMpSubscribeMessageDataRequest("吴嘉贤"));
        map.put("thing2", new SendWeChatMpSubscribeMessageDataRequest("华农科联"));
        map.put("thing3", new SendWeChatMpSubscribeMessageDataRequest("自科部"));
        map.put("phrase4", new SendWeChatMpSubscribeMessageDataRequest("一轮面试"));
        map.put("phrase5", new SendWeChatMpSubscribeMessageDataRequest("通过"));
        sendWeChatMpSubscribeMessagePO.setTemplateData(map);
        sendWeChatMpSubscribeMessagePO.setApp(AppEnum.SCAU_RECRUIT_INTERVIEWEE_MP);
        sendWeChatMpSubscribeMessagePO.setUserId(1L);
        weChatMpService.sendSubscribeMessage(sendWeChatMpSubscribeMessagePO);
    }

    @Test
    public void getUserInfo() {
        String encryptedData =
                "MvWCTWz4aWiF8KOVoN8qHQt24Bh5byY/jZb/brUuFFSEKIUu4aLf7z8Ko8PrEBMG8s4Da1fc5UpO3469gffDC8DdUA0rr/6Z1gKokayX/DyAUAtPclkouvO26TcmYtSMrHfreOW7D8yc8dnUndKdzVm6Xqcmn6eaHFjsv/2bKTRHULJ73REEgZlFI+doJI4CjCzZk9d40QCsTXQAUnpxuaD1EjRqJHgtm+mpXkdZvOBi6uOf84dlTNbXLcC8tK1npK/i6NL1nU0155eJBz7rnuY05+6RItpAJnETUoSluCU1+Re4WV2tF683g1zBEb3IP4oMEmxQvOzzs5yY3BlN/2mKURJEa4AwB4AuXE8hL4CQTAbGyFwBltuMUz8to0t8FhsmLuY5T5izsoSiDPpC5IKCD0aOgLuk98iGW0TaKnRtBpLHVb7bcIhlb9OLHky54Vxc4VuDY2DITKmHzJLX/s56JQde3GVVROctAgDt3CE=";
        String iv = "0oKdWTo11NZ4Lm/JeL5YYg==";
        String code = "073H2oHa16SQ6A00h3Ia1VE5cM1H2oHS";
        weChatMpService.getUserInfo(encryptedData, iv, code);
    }
}