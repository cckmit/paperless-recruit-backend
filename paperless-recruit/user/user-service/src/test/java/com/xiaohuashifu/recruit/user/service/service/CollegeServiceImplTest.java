package com.xiaohuashifu.recruit.user.service.service;

import com.xiaohuashifu.recruit.user.api.query.CollegeQuery;
import com.xiaohuashifu.recruit.user.api.query.MajorQuery;
import com.xiaohuashifu.recruit.user.api.service.CollegeService;
import com.xiaohuashifu.recruit.user.api.service.UserService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

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
        System.out.println(collegeService.saveCollege("海洋学院"));
    }

    @Test
    public void saveMajor() {
        System.out.println(collegeService.saveMajor(1L, "计算机科学与技术"));
    }

    @Test
    public void deleteCollege() {
        System.out.println(collegeService.deleteCollege(2L));
    }

    @Test
    public void deleteMajor() {
        System.out.println(collegeService.deleteMajor(1L));
    }

    @Test
    public void getCollege() {
        System.out.println(collegeService.getCollege(1L));
    }

    @Test
    public void testGetCollege() {
        System.out.println(collegeService.getCollege(new CollegeQuery.Builder().collegeName("软件").build()));
    }

    @Test
    public void getCollegeMajor() {
        System.out.println(collegeService.getCollegeMajor(new CollegeQuery.Builder().id(1L).build()));
    }

    @Test
    public void getMajor() {
        System.out.println(collegeService.getMajor(1L));
    }

    @Test
    public void testGetMajor() {
        System.out.println(collegeService.getMajor(new MajorQuery.Builder().id(1L).build()));
    }

    @Test
    public void updateCollegeName() {
        System.out.println(collegeService.updateCollegeName(1L, "数学与信息学院/软件学院"));
    }

    @Test
    public void updateMajorName() {
        System.out.println(collegeService.updateMajorName(1L, "计算机科学与技术"));
    }
}