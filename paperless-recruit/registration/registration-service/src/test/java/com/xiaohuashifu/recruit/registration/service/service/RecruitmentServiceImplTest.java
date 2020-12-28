package com.xiaohuashifu.recruit.registration.service.service;

import com.xiaohuashifu.recruit.common.constant.GradeEnum;
import com.xiaohuashifu.recruit.registration.api.constant.RecruitmentStatusEnum;
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
        System.out.println(recruitmentService.addRecruitmentCollege(3L, 4L));
    }

    @Test
    public void addRecruitmentMajor() {
        System.out.println(recruitmentService.addRecruitmentMajor(3L, 3L));
    }

    @Test
    public void addRecruitmentGrade() {
        System.out.println(recruitmentService.addRecruitmentGrade(3L, null));
    }

    @Test
    public void addRecruitmentDepartment() {
        System.out.println(recruitmentService.addRecruitmentDepartment(3L, 2L));
    }

    @Test
    public void removeRecruitmentCollege() {
        System.out.println(recruitmentService.removeRecruitmentCollege(3L, 1L));
    }

    @Test
    public void removeRecruitmentMajor() {
        System.out.println(recruitmentService.removeRecruitmentMajor(4L, 3L));
    }

    @Test
    public void removeRecruitmentGrade() {
        System.out.println(recruitmentService.removeRecruitmentGrade(4L, GradeEnum.FRESHMEN));
    }

    @Test
    public void removeRecruitmentDepartment() {
        System.out.println(recruitmentService.removeRecruitmentDepartment(4L, 2L));
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
        System.out.println(recruitmentService.updatePositionName(3L, "部长"));
    }

    @Test
    public void updateRecruitmentNumbers() {
        System.out.println(recruitmentService.updateRecruitmentNumbers(7L, "500人"));
    }

    @Test
    public void updatePositionDuty() {
        System.out.println(recruitmentService.updatePositionDuty(3L, "参与项目研发"));
    }

    @Test
    public void updatePositionRequirement() {
        System.out.println(recruitmentService.updatePositionRequirement(3L, "熟悉Java"));
    }

    @Test
    public void updateReleaseTime() {
        System.out.println(recruitmentService.updateReleaseTime(3L,null));

    }

    @Test
    public void updateRegistrationTimeFrom() {
        System.out.println(recruitmentService.updateRegistrationTimeFrom(7L, LocalDateTime.now().minusHours(1)));
    }

    @Test
    public void updateRegistrationTimeTo() {
        System.out.println(recruitmentService.updateRegistrationTimeTo(3L, LocalDateTime.now()));

    }

    @Test
    public void updateRecruitmentStatus() {
        System.out.println(recruitmentService.updateRecruitmentStatus(3L, RecruitmentStatusEnum.WAITING_FOR_RELEASE, RecruitmentStatusEnum.STARTED));
    }

    @Test
    public void endRegistration() {
        System.out.println(recruitmentService.endRegistration(7L));
    }

    @Test
    public void closeRecruitment() {
        System.out.println(recruitmentService.closeRecruitment(7L));
    }

    @Test
    public void disableRecruitment() {
    }

    @Test
    public void enableRecruitment() {
    }


}