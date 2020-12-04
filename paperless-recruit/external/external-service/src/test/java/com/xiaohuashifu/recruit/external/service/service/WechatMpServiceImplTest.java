package com.xiaohuashifu.recruit.external.service.service;

import com.xiaohuashifu.recruit.common.constant.AppEnum;
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
        System.out.println(wechatMpService.getOpenid("043WLGll2yCLZ541iKkl2lC1RC3WLGlC", AppEnum.SCAU_RECRUIT_INTERVIEWEE_MP));
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
                AppEnum.SCAU_RECRUIT_INTERVIEWEE_MP, 1L, subscribeMessageDTO));
    }

    @Test
    public void getUserInfo() {
        String encryptedData =
                "MvWCTWz4aWiF8KOVoN8qHQt24Bh5byY/jZb/brUuFFSEKIUu4aLf7z8Ko8PrEBMG8s4Da1fc5UpO3469gffDC8DdUA0rr/6Z1gKokayX/DyAUAtPclkouvO26TcmYtSMrHfreOW7D8yc8dnUndKdzVm6Xqcmn6eaHFjsv/2bKTRHULJ73REEgZlFI+doJI4CjCzZk9d40QCsTXQAUnpxuaD1EjRqJHgtm+mpXkdZvOBi6uOf84dlTNbXLcC8tK1npK/i6NL1nU0155eJBz7rnuY05+6RItpAJnETUoSluCU1+Re4WV2tF683g1zBEb3IP4oMEmxQvOzzs5yY3BlN/2mKURJEa4AwB4AuXE8hL4CQTAbGyFwBltuMUz8to0t8FhsmLuY5T5izsoSiDPpC5IKCD0aOgLuk98iGW0TaKnRtBpLHVb7bcIhlb9OLHky54Vxc4VuDY2DITKmHzJLX/s56JQde3GVVROctAgDt3CE=";
        String iv = "0oKdWTo11NZ4Lm/JeL5YYg==";
        String code = "073H2oHa16SQ6A00h3Ia1VE5cM1H2oHS";
        wechatMpService.getUserInfo(encryptedData, iv, code);
    }
}