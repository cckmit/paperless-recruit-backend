package com.xiaohuashifu.recruit.user.service.service;

import com.xiaohuashifu.recruit.user.api.query.CollegeQuery;
import com.xiaohuashifu.recruit.user.api.service.CollegeService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.junit.Before;
import org.junit.Test;

/**
 * 描述：
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/23 21:22
 */
public class CollegeServiceImplTest {

    private CollegeService collegeService;

    @Before
    public void before() {
        ApplicationConfig application = new ApplicationConfig();
        application.setName("collegeServiceTest");
        ReferenceConfig<CollegeService> reference = new ReferenceConfig<>();
        reference.setUrl("dubbo://127.0.0.1:20881/com.xiaohuashifu.recruit.user.api.service.CollegeService");
        reference.setApplication(application);
        reference.setInterface(CollegeService.class);
        reference.setTimeout(10000000);
        collegeService = reference.get();
    }

    @Test
    public void saveCollege() {
        System.out.println(collegeService.createCollege("兽医学院"));
    }

    @Test
    public void getCollege() {
        System.out.println(collegeService.getCollege(1L));
    }

    @Test
    public void testGetCollege() {
        System.out.println(collegeService.listColleges(CollegeQuery.builder().collegeName("软件").build()));
    }

    @Test
    public void updateCollegeName() {
        System.out.println(collegeService.updateCollegeName(5L, "经济管理学院"));
    }

    @Test
    public void deactivateCollege() {
        System.out.println(collegeService.deactivateCollege(5L));
    }

}