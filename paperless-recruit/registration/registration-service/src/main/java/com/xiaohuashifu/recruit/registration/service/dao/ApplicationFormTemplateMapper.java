package com.xiaohuashifu.recruit.registration.service.dao;

import com.xiaohuashifu.recruit.registration.service.do0.ApplicationFormTemplateDO;

/**
 * 描述：报名表模板数据库映射层
 *
 * @author xhsf
 * @create 2020/12/26 20:33
 */
public interface ApplicationFormTemplateMapper {

    int insertApplicationFormTemplate(ApplicationFormTemplateDO applicationFormTemplateDO);

    ApplicationFormTemplateDO getApplicationFormTemplate(Long id);

    ApplicationFormTemplateDO getApplicationFormTemplateByRecruitmentId(Long recruitmentId);

    Long getRecruitmentId(Long id);

    Boolean getDeactivatedByRecruitmentId(Long recruitmentId);

    int countByRecruitmentId(Long recruitmentId);


}
