package com.xiaohuashifu.recruit.registration.service.service;

import com.xiaohuashifu.recruit.registration.api.request.UpdateApplicationFormTemplateRequest;
import com.xiaohuashifu.recruit.registration.api.service.ApplicationFormTemplateService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.junit.Before;
import org.junit.Test;

/**
 * 描述：
 *
 * @author xhsf
 * @create 2020/12/29 15:35
 */
public class ApplicationFormTemplateServiceImplTest {

    private ApplicationFormTemplateService applicationFormTemplateService;

    @Before
    public void before() {
        ApplicationConfig application = new ApplicationConfig();
        application.setName("applicationFormTemplateServiceTest");
        ReferenceConfig<ApplicationFormTemplateService> reference = new ReferenceConfig<>();
        reference.setUrl("dubbo://127.0.0.1:20887/com.xiaohuashifu.recruit.registration.api.service.ApplicationFormTemplateService");
        reference.setApplication(application);
        reference.setInterface(ApplicationFormTemplateService.class);
        reference.setTimeout(10000000);
        applicationFormTemplateService = reference.get();
    }


    @Test
    public void createApplicationFormTemplate() {
        System.out.println(applicationFormTemplateService.createApplicationFormTemplate(1L));
    }

    @Test
    public void getApplicationFormTemplateByUserId() {
        System.out.println(applicationFormTemplateService.getApplicationFormTemplateByUserId(1L));
    }

    @Test
    public void updateApplicationFormTemplate() {
        System.out.println(applicationFormTemplateService.updateApplicationFormTemplate(
                UpdateApplicationFormTemplateRequest.builder()
                        .id(13L)
                        .fullName("吴嘉贤")
                        .phone("15992321303")
                        .email("827032783@qq.com")
                        .introduction("xx")
                        .studentNumber("201734020124")
                        .college("软件学院")
                        .major("软件工程")
                        .build()));
    }

}