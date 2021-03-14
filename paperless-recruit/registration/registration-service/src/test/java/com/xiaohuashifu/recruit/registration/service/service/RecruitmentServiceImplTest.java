package com.xiaohuashifu.recruit.registration.service.service;

import com.xiaohuashifu.recruit.registration.api.dto.RecruitmentDTO;
import com.xiaohuashifu.recruit.registration.api.query.RecruitmentQuery;
import com.xiaohuashifu.recruit.registration.api.request.CreateRecruitmentRequest;
import com.xiaohuashifu.recruit.registration.api.request.UpdateRecruitmentRequest;
import com.xiaohuashifu.recruit.registration.api.service.RecruitmentService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.junit.Before;
import org.junit.Test;

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
        CreateRecruitmentRequest createRecruitmentPO = CreateRecruitmentRequest.builder()
                .organizationId(1L)
                .recruitmentName("校科联自科部招新")
                .position("干事")
                .numberOfRecruitments("约30人")
                .duty("1、参加科研项目和科技比赛，加强对成员的技术培训，培养和提高自身的科研力量和技术支持，拥有自身的科研成果；\n" +
                        "2、加强内外交流，组织学生参观学校各学院或其他高校的实验室或工作室、开展项目分享会、科研交流会、科技成果展览会等，培养和促进学生的科研兴趣，并提供其学习、实践的机会；\n" +
                        "3、打造学生科技协同创新的平台—创新梦工厂，对全校的科技创新团队进行组织和管理，并提供相应的帮助和支持，促进学生对科技创新发明创造的热情，以此加强对创新团队和创新科技成果的培育。")
                .requirement("对软件感兴趣，最好有学习过编程语言方面的知识")
                .build();
        System.out.println(recruitmentService.createRecruitment(createRecruitmentPO));
    }

    @Test
    public void getRecruitment() {
        RecruitmentDTO recruitmentDTO = recruitmentService.getRecruitment(23L);
        System.out.println(recruitmentDTO);
    }

    @Test
    public void updateRecruitment() {
        System.out.println(recruitmentService.updateRecruitment(UpdateRecruitmentRequest.builder()
                .id(11L)
                .recruitmentStatus("ENDED1")
                .build()));
    }

    @Test
    public void listRecruitments() {
        System.out.println(recruitmentService.listRecruitments(RecruitmentQuery.builder()
                .organizationId(1L)
                .pageNum(1L)
                .pageSize(50L)
                .build()));
    }

    @Test
    public void increaseNumberOfApplicationForms() {
        recruitmentService.increaseNumberOfApplicationForms(11L);
    }
}