package com.xiaohuashifu.recruit.registration.service.service;

import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.registration.api.dto.ApplicationFormTemplateDTO;
import com.xiaohuashifu.recruit.registration.api.po.AddApplicationFormTemplatePO;
import com.xiaohuashifu.recruit.registration.api.po.UpdateApplicationFormTemplatePO;
import com.xiaohuashifu.recruit.registration.api.service.ApplicationFormTemplateService;
import org.apache.dubbo.config.annotation.Service;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

/**
 * 描述：报名表模板服务实现
 *
 * @author xhsf
 * @create 2020/12/28 21:55
 */
@Service
public class ApplicationFormTemplateServiceImpl implements ApplicationFormTemplateService {
    /**
     * 给一个招新添加报名表模板
     *
     * @param addApplicationFormTemplatePO 添加的参数对象
     * @return ApplicationFormTemplateDTO 报名表模板
     * @permission 需要是该招新所属组织所属用户主体本身
     */
    @Override
    public Result<ApplicationFormTemplateDTO> addApplicationFormTemplate(@NotNull(message = "The addApplicationFormTemplatePO can't be null.") AddApplicationFormTemplatePO addApplicationFormTemplatePO) {
        return null;
    }

    /**
     * 获取报名表模板，通过招新编号
     *
     * @param recruitmentId 招新编号
     * @return 报名表模板
     */
    @Override
    public Result<ApplicationFormTemplateDTO> getApplicationFormTemplateByRecruitmentId(@NotNull(message = "The recruitmentId can't be null.") @Positive(message = "The recruitmentId must be greater than 0.") Long recruitmentId) {
        return null;
    }

    /**
     * 更新报名表模板
     *
     * @param updateApplicationFormTemplatePO 更新的参数对象
     * @return 更新后的报名表模板
     * @permission 需要是该报名表模板所属招新所属组织所属用户主体本身
     */
    @Override
    public Result<ApplicationFormTemplateDTO> updateApplicationFormTemplate(@NotNull(message = "The updateApplicationFormTemplatePO can't be null.") UpdateApplicationFormTemplatePO updateApplicationFormTemplatePO) {
        return null;
    }

    /**
     * 更新报名提示
     *
     * @param id     报名表模板编号
     * @param prompt 报名提示
     * @return 更新后的报名表模板
     * @permission 需要是该报名表模板所属招新所属组织所属用户主体本身
     */
    @Override
    public Result<ApplicationFormTemplateDTO> updatePrompt(@NotNull(message = "The id can't be null.") @Positive(message = "The id must be greater than 0.") Long id, @NotBlank(message = "The prompt can't be blank.") String prompt) {
        return null;
    }

    /**
     * 停用报名表模板
     *
     * @param id 报名表模板编号
     * @return 停用后的报名表模板
     * @permission 需要是该报名表模板所属招新所属组织所属用户主体本身
     */
    @Override
    public Result<ApplicationFormTemplateDTO> deactivateApplicationFormTemplate(@NotNull(message = "The id can't be null.") @Positive(message = "The id must be greater than 0.") Long id) {
        return null;
    }

    /**
     * 启用报名表模板
     *
     * @param id 报名表模板编号
     * @return 启用后的报名表模板
     * @permission 需要是该报名表模板所属招新所属组织所属用户主体本身
     */
    @Override
    public Result<ApplicationFormTemplateDTO> enableApplicationFormTemplate(@NotNull(message = "The id can't be null.") @Positive(message = "The id must be greater than 0.") Long id) {
        return null;
    }

    /**
     * 获取招新编号
     *
     * @param id 报名表模板编号
     * @return 招新编号，如果该报名表不存在则返回 null
     * @private 内部方法
     */
    @Override
    public Long getRecruitmentId(Long id) {
        return null;
    }

    /**
     * 检查报名表模板的状态，检查报名表模板是否存在，报名表模板是否被停用
     *
     * @param recruitmentId 招新编号
     * @return 检查结果
     * @private 内部方法
     * @errorCode InvalidParameter.NotExist: 报名表模板不存在
     * Forbidden.Deactivated: 报名表模板被停用
     */
    @Override
    public <T> Result<T> checkApplicationFormTemplateStatusByRecruitmentId(Long recruitmentId) {
        return Result.success();
    }
}
