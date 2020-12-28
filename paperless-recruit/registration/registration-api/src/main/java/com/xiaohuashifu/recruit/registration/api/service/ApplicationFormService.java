package com.xiaohuashifu.recruit.registration.api.service;

import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.registration.api.dto.ApplicationFormDTO;
import com.xiaohuashifu.recruit.registration.api.po.CreateApplicationFormPO;

import javax.validation.constraints.NotNull;

/**
 * 描述：报名表服务
 *
 * @author xhsf
 * @create 2020/12/28 22:00
 */
public interface ApplicationFormService {

    /**
     * 创建报名表
     *
     * @permission 必须是用户本身
     *
     * @return
     */
    Result<ApplicationFormDTO> createApplicationForm(@NotNull CreateApplicationFormPO createApplicationFormPO);

    

}
