package com.xiaohuashifu.recruit.registration.service.service;

import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.registration.api.dto.ApplicationFormDTO;
import com.xiaohuashifu.recruit.registration.api.po.CreateApplicationFormPO;
import com.xiaohuashifu.recruit.registration.api.service.ApplicationFormService;
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
    /**
     * 创建报名表
     *
     * @param createApplicationFormPO
     * @return 创建的报名表
     * @permission 必须是用户本身
     */
    @Override
    public Result<ApplicationFormDTO> createApplicationForm(@NotNull(message = "The createApplicationFormPO can't be null.") CreateApplicationFormPO createApplicationFormPO) {
        return null;
    }
}
