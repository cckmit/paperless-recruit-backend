package com.xiaohuashifu.recruit.registration.api.service;

import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.registration.api.dto.ApplicationFormDTO;

/**
 * 描述：报名表服务
 *
 * @author xhsf
 * @create 2020/12/28 22:00
 */
public interface ApplicationFormService {

    /**
     * @permission 必须是用户本身
     * @return
     */
    Result<ApplicationFormDTO> createApplicationForm();

}
