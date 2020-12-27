package com.xiaohuashifu.recruit.registration.service.service;

import com.xiaohuashifu.recruit.common.constant.GradeEnum;
import com.xiaohuashifu.recruit.registration.api.po.CreateRecruitmentPO;
import com.xiaohuashifu.recruit.registration.api.service.RecruitmentService;
import com.xiaohuashifu.recruit.user.api.service.UserService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * 描述：
 *
 * @author xhsf
 * @create 2020/12/26 21:54
 */
public class RecruitmentServiceImplTest {

    private RecruitmentService recruitmentService;

    @Before
    public void before() {
        ApplicationConfig application = new ApplicationConfig();
        application.setName("recruitmentServiceTest");
        ReferenceConfig<RecruitmentService> reference = new ReferenceConfig<>();
        reference.setUrl("dubbo://127.0.0.1:20887/com.xiaohuashifu.recruit.registration.api.service.RecruitmentService");
        reference.setApplication(application);
        reference.setInterface(RecruitmentService.class);
        reference.setTimeout(10000000);
        recruitmentService = reference.get();
    }

    @Test
    public void createRecruitment() {
        Set<GradeEnum> recruitmentGrades = new HashSet<>();
        recruitmentGrades.add(GradeEnum.FRESHMEN);
        recruitmentGrades.add(GradeEnum.SOPHOMORE);
        Set<Long> recruitmentDepartmentIds = new HashSet<>();
        recruitmentDepartmentIds.add(2L);
        recruitmentDepartmentIds.add(3L);
        Set<Long> recruitmentCollegeIds = new HashSet<>();
        recruitmentCollegeIds.add(1L);
        recruitmentCollegeIds.add(4L);
        Set<Long> recruitmentMajorIds = new HashSet<>();
        recruitmentMajorIds.add(2L);
        recruitmentMajorIds.add(3L);

        CreateRecruitmentPO createRecruitmentPO = new CreateRecruitmentPO.Builder()
                .organizationId(1L)
                .positionName("干事")
                .recruitmentNumbers("约30人")
                .positionDuty("xxx")
                .positionRequirement("xxxx")
                .recruitmentGrades(recruitmentGrades)
                .recruitmentDepartmentIds(recruitmentDepartmentIds)
                .recruitmentCollegeIds(recruitmentCollegeIds)
                .recruitmentMajorIds(recruitmentMajorIds)
                .releaseTime(null)
                .registrationTimeFrom(null)
                .registrationTimeTo(null).build();
        System.out.println(recruitmentService.createRecruitment(createRecruitmentPO));
    }

    @Test
    public void addRecruitmentCollege() {
    }

    @Test
    public void addRecruitmentMajor() {
    }

    @Test
    public void addRecruitmentGrade() {
    }

    @Test
    public void addRecruitmentDepartment() {
    }

    @Test
    public void removeRecruitmentCollege() {
    }

    @Test
    public void removeRecruitmentMajor() {
    }

    @Test
    public void removeRecruitmentGrade() {
    }

    @Test
    public void removeRecruitmentDepartment() {
    }

    @Test
    public void getRecruitment() {
        System.out.println(recruitmentService.getRecruitment(3L));
    }

    @Test
    public void getOrganizationId() {
    }

    @Test
    public void updatePositionName() {
    }

    @Test
    public void updateRecruitmentNumbers() {
    }

    @Test
    public void updatePositionDuty() {
    }

    @Test
    public void updatePositionRequirement() {
    }

    @Test
    public void updateReleaseTime() {
    }

    @Test
    public void updateRegistrationTimeFrom() {
    }

    @Test
    public void updateRegistrationTimeTo() {
    }

    @Test
    public void updateRecruitmentStatus() {
    }

    @Test
    public void disableRecruitment() {
    }

    @Test
    public void enableRecruitment() {
    }

}