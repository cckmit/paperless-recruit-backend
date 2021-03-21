package com.xiaohuashifu.recruit.registration.service.service;

import com.xiaohuashifu.recruit.registration.api.query.ApplicationFormQuery;
import com.xiaohuashifu.recruit.registration.api.request.*;
import com.xiaohuashifu.recruit.registration.api.service.ApplicationFormService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * 描述：
 *
 * @author xhsf
 * @create 2020/12/30 16:42
 */
public class ApplicationFormServiceImplTest {

    private ApplicationFormService applicationFormService;

    @Before
    public void before() {
        ApplicationConfig application = new ApplicationConfig();
        application.setName("applicationFormServiceTest");
        ReferenceConfig<ApplicationFormService> reference = new ReferenceConfig<>();
        reference.setUrl("dubbo://127.0.0.1:20887/com.xiaohuashifu.recruit.registration.api.service.ApplicationFormService");
        reference.setApplication(application);
        reference.setInterface(ApplicationFormService.class);
        reference.setTimeout(10000000);
        applicationFormService = reference.get();
    }

    @Test
    public void createApplicationForm() {
        System.out.println(applicationFormService.createApplicationForm(
                CreateApplicationFormRequest.builder()
                        .userId(1L)
        .recruitmentId(26L)
        .avatarUrl("application-forms/avatars/f31183136ff94442840568817ab919d6u=336497710,1373556175&fm=26&gp=0.jpg")
        .fullName("刘洗洗")
        .phone("13333333333")
                        .email("827032783@qq.com")
                        .introduction("我是刘洗洗我是刘洗洗我是刘洗洗我是刘洗洗我是刘洗洗我是刘洗洗我是刘洗洗我是刘洗洗" +
                                "我是刘洗洗我是刘洗洗我是刘洗洗我是刘洗洗我是刘洗洗我是刘洗洗我是刘洗洗我是刘洗洗我是刘洗洗我" +
                                "是刘洗洗我是刘洗洗")
                        .studentNumber("201734020124")
                        .college("数信")
                        .major("软工")
                        .note("请选我请选我请选我请选我请选我请选我请选我请选我请选我请选我请选" +
                                "我请选我请选我请选我请选我请选我请选我请选我请选我请选我请选我请选我请选")
        .build()));
    }

    @Test
    public void getApplicationForm() {
        System.out.println(applicationFormService.getApplicationForm(1L));
    }

    @Test
    public void listApplicationForms() {
        System.out.println(applicationFormService.listApplicationForms(ApplicationFormQuery.builder()
                .pageNum(1L)
                .pageSize(50L)
                .recruitmentId(26L)
                .orderByApplicationTimeDesc(true)
                .build()));
    }

    @Test
    public void updateApplicationForm() throws IOException {
//        System.out.println(applicationFormService.updateApplicationForm(1L, ""));
    }


}