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
    public void saveMajor() {
        System.out.println(majorService.saveMajor(6L, "动物药学"));
    }

    @Test
    public void getMajor() {
        System.out.println(majorService.getMajor(8L));
    }

    @Test
    public void listMajors() {
        System.out.println(majorService.listMajors(new MajorQuery.Builder().pageNum(1L).pageSize(50L).collegeId(1L).build()));
    }

    @Test
    public void updateMajorName() {
    }

    @Test
    public void deactivateMajor() {
        System.out.println(majorService.deactivateMajor(9L));
    }

    @Test
    public void checkMajorStatus() {
        System.out.println(majorService.checkMajorStatus(10L));
    }
}