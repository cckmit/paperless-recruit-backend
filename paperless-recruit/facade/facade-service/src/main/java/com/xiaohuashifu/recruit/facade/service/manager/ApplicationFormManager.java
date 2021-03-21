package com.xiaohuashifu.recruit.facade.service.manager;

import com.xiaohuashifu.recruit.common.query.QueryResult;
import com.xiaohuashifu.recruit.facade.service.vo.ApplicationFormVO;
import com.xiaohuashifu.recruit.registration.api.query.ApplicationFormQuery;
import com.xiaohuashifu.recruit.registration.api.request.CreateApplicationFormRequest;
import com.xiaohuashifu.recruit.registration.api.request.UpdateApplicationFormRequest;

/**
 * 描述：报名表管理器
 *
 * @author xhsf
 * @create 2021/1/9 14:49
 */
public interface ApplicationFormManager {

    /**
     * 创建报名表
     *
     * @param request CreateApplicationFormRequest
     * @return ApplicationFormVO
     */
    ApplicationFormVO createApplicationForm(CreateApplicationFormRequest request);

    /**
     * 获取报名表
     *
     * @param id 报名表编号
     * @return ApplicationFormVO
     */
    ApplicationFormVO getApplicationForm(Long id);

    /**
     * 列出招报名表
     *
     * @param query ApplicationFormQuery
     * @return QueryResult<ApplicationFormVO>
     */
    QueryResult<ApplicationFormVO> listApplicationForms(ApplicationFormQuery query);

    /**
     * 更新报名表
     *
     * @param request UpdateApplicationFormRequest
     * @return ApplicationFormVO
     */
    ApplicationFormVO updateApplicationForm(UpdateApplicationFormRequest request);

}
