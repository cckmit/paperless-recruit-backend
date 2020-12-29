package com.xiaohuashifu.recruit.registration.api.service;

import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.registration.api.constant.ApplicationFormTemplateConstants;
import com.xiaohuashifu.recruit.registration.api.dto.ApplicationFormTemplateDTO;
import com.xiaohuashifu.recruit.registration.api.po.AddApplicationFormTemplatePO;
import com.xiaohuashifu.recruit.registration.api.po.UpdateApplicationFormTemplatePO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

/**
 * 描述：报名表模板服务
 *
 * @author xhsf
 * @create 2020/12/28 20:42
 */
public interface ApplicationFormTemplateService {

    /**
     * 给一个招新添加报名表模板，必须是招新状态是 ENDED 之前才可以添加
     *
     * @permission 需要是该招新所属组织所属用户主体本身
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.Number: 报名表模板的域个数小于最少限制
     *              InvalidParameter.NotExist: 招新不存在
     *              Forbidden.Unavailable: 招新不可用 | 组织不可用
     *              OperationConflict.Duplicate: 报名表模板已经存在
     *              OperationConflict.Status: 招新状态不允许
     *              OperationConflict.Lock: 获取报名表模板的锁失败
     *
     * @param addApplicationFormTemplatePO 添加的参数对象
     * @return ApplicationFormTemplateDTO 报名表模板
     */
    Result<ApplicationFormTemplateDTO> addApplicationFormTemplate(
            @NotNull(message = "The addApplicationFormTemplatePO can't be null.")
                    AddApplicationFormTemplatePO addApplicationFormTemplatePO);

    /**
     * 获取报名表模板，通过招新编号
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotFound: 找不到该招新编号的报名表模板
     *
     * @param recruitmentId 招新编号
     * @return 报名表模板
     */
    Result<ApplicationFormTemplateDTO> getApplicationFormTemplateByRecruitmentId(
            @NotNull(message = "The recruitmentId can't be null.")
            @Positive(message = "The recruitmentId must be greater than 0.") Long recruitmentId);

    /**
     * 更新报名表模板
     *
     * @permission 需要是该报名表模板所属招新所属组织所属用户主体本身
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.Number: 报名表模板的域个数小于最少限制
     *              InvalidParameter.NotExist: 报名表模板不存在
     *              Forbidden.Unavailable: 招新不可用 | 组织不可用
     *              OperationConflict.Status: 招新状态不是 STARTED 之前
     *              OperationConflict.Lock: 获取报名表模板的锁失败
     *
     * @param updateApplicationFormTemplatePO 更新的参数对象
     * @return 更新后的报名表模板
     */
    Result<ApplicationFormTemplateDTO> updateApplicationFormTemplate(
            @NotNull(message = "The updateApplicationFormTemplatePO can't be null.")
                    UpdateApplicationFormTemplatePO updateApplicationFormTemplatePO);

    /**
     * 更新报名提示
     *
     * @permission 需要是该报名表模板所属招新所属组织所属用户主体本身
     *
     * @param id 报名表模板编号
     * @param prompt 报名提示
     * @return 更新后的报名表模板
     */
    Result<ApplicationFormTemplateDTO> updatePrompt(
            @NotNull(message = "The id can't be null.") @Positive(message = "The id must be greater than 0.") Long id,
            @NotBlank(message = "The prompt can't be blank.")
            @Size(max = ApplicationFormTemplateConstants.MAX_PROMPT_LENGTH,
                    message = "The length of prompt must not be greater than "
                            + ApplicationFormTemplateConstants.MAX_PROMPT_LENGTH + ".") String prompt);

    /**
     * 停用报名表模板，停用之后无法报名，可以再次启用
     *
     * @permission 需要是该报名表模板所属招新所属组织所属用户主体本身
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 报名表模板不存在
     *              Forbidden.Unavailable: 招新不可用 | 组织不可用
     *              OperationConflict.Status: 招新状态不是 ENDED 之前
     *              OperationConflict.Unmodified: 报名表模板已经被停用
     *
     * @param id 报名表模板编号
     * @return 停用后的报名表模板
     */
    Result<ApplicationFormTemplateDTO> deactivateApplicationFormTemplate(
            @NotNull(message = "The id can't be null.") @Positive(message = "The id must be greater than 0.") Long id);

    /**
     * 启用报名表模板
     *
     * @permission 需要是该报名表模板所属招新所属组织所属用户主体本身
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 报名表模板不存在
     *              Forbidden.Unavailable: 招新不可用 | 组织不可用
     *              OperationConflict.Status: 招新状态不是 ENDED 之前
     *              OperationConflict.Unmodified: 报名表模板已经被启用
     *
     * @param id 报名表模板编号
     * @return 启用后的报名表模板
     */
    Result<ApplicationFormTemplateDTO> enableApplicationFormTemplate(
            @NotNull(message = "The id can't be null.") @Positive(message = "The id must be greater than 0.") Long id);

    /**
     * 判断一个招新是否可以报名
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 招新不存在 | 报名表模板不存在
     *              Forbidden.Unavailable: 招新不可用 | 组织不可用
     *              Forbidden.Deactivated: 报名表模板被停用
     *              OperationConflict.Status: 招新的状态必须是 STARTED
     *
     * @param recruitmentId 招新编号
     * @return 是否可以报名
     */
    <T> Result<T> canRegistration(@NotNull(message = "The recruitmentId can't be null.")
                                  @Positive(message = "The recruitmentId must be greater than 0.") Long recruitmentId);

    /**
     * 获取招新编号
     *
     * @private 内部方法
     *
     * @param id 报名表模板编号
     * @return 招新编号，如果该报名表不存在则返回 null
     */
    Long getRecruitmentId(Long id);

}
