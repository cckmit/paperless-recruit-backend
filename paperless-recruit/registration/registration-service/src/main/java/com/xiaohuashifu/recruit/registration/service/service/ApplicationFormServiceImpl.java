package com.xiaohuashifu.recruit.registration.service.service;

import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.registration.api.dto.ApplicationFormDTO;
import com.xiaohuashifu.recruit.registration.api.dto.ApplicationFormTemplateDTO;
import com.xiaohuashifu.recruit.registration.api.po.CreateApplicationFormPO;
import com.xiaohuashifu.recruit.registration.api.service.ApplicationFormService;
import com.xiaohuashifu.recruit.registration.api.service.ApplicationFormTemplateService;
import com.xiaohuashifu.recruit.registration.service.do0.ApplicationFormDO;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;

import javax.validation.constraints.NotNull;

/**
 * 描述：报名表服务实现
 *
 * @author xhsf
 * @create 2020/12/29 21:03
 */
@Service
public class ApplicationFormServiceImpl implements ApplicationFormService {

    @Reference
    private ApplicationFormTemplateService applicationFormTemplateService;

    /**
     * 创建报名表
     *
     * @permission 必须是用户本身
     *
     * @return 创建的报名表
     */
    @Override
    public Result<ApplicationFormDTO> createApplicationForm(CreateApplicationFormPO createApplicationFormPO) {
        // 判断该招新是否可以报名
        Long recruitmentId = createApplicationFormPO.getRecruitmentId();
        Result<ApplicationFormDTO> canRegistration = applicationFormTemplateService.canRegistration(recruitmentId);
        if (canRegistration.isFailure()) {
            return canRegistration;
        }

        // 获取该招新的报名表模板
        Result<ApplicationFormTemplateDTO> getApplicationFormTemplateResult =
                applicationFormTemplateService.getApplicationFormTemplateByRecruitmentId(recruitmentId);
        ApplicationFormTemplateDTO applicationFormTemplateDTO = getApplicationFormTemplateResult.getData();

        // 构造插入数据库的数据对象
        ApplicationFormDO applicationFormDO =
                createApplicationFormPO2ApplicationFormDO(createApplicationFormPO, applicationFormTemplateDTO);
        return null;
    }

    private ApplicationFormDO createApplicationFormPO2ApplicationFormDO(
            CreateApplicationFormPO createApplicationFormPO, ApplicationFormTemplateDTO applicationFormTemplateDTO) {
//        avatar
//                fullName
//        phone
//                firstDepartment
//        secondDepartment
//                email
//        introduction
//                attachment
//        studentNumber
//                college
//        major
//                note
//        if ()

//        ApplicationFormDO applicationFormDO = ApplicationFormDO.builder()
        return null;
    }
}
