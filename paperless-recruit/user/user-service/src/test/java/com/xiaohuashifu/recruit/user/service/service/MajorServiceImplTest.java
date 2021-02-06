package com.xiaohuashifu.recruit.user.service.service;

import com.xiaohuashifu.recruit.user.api.query.MajorQuery;
import com.xiaohuashifu.recruit.user.api.service.MajorService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.junit.Before;
import org.junit.Test;

/**
 * 描述：
 *
 * @author xhsf
 * @create 2020/12/24 13:25
 */
public class MajorServiceImplTest {
    private MajorService majorService;

    @Before
    public void before() {
        ApplicationConfig application = new ApplicationConfig();
        application.setName("majorServiceTest");
        ReferenceConfig<MajorService> reference = new ReferenceConfig<>();
        reference.setUrl("dubbo://127.0.0.1:20881/com.xiaohuashifu.recruit.user.api.service.MajorService");
        reference.setApplication(application);
        reference.setInterface(MajorService.class);
        reference.setTimeout(10000000);
        majorService = reference.get();
    }

    @Test
    public void createMajor() {
        System.out.println(majorService.createMajor(7L, "海洋科学"));
    }

    @Test
    public void getMajor() {
        System.out.println(majorService.getMajor(15L));
    }

    @Test
    public void listMajors() {
        System.out.println(majorService.listMajors(MajorQuery.builder().pageNum(1L).pageSize(50L).collegeId(7L).build()));
    }

    @Test
    public void updateMajorName() {
    }

}