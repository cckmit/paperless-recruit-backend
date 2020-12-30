package com.xiaohuashifu.recruit.registration.api.service;

import com.github.pagehelper.PageInfo;
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
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 学院不存在 | 专业不存在 | 部门不存在
     *              Forbidden.Deactivated: 学院被停用 | 专业被停用 | 部门被停用
     *              OperationConflict.Duplicate: 报名表已经存在
     *              OperationConflict.Lock: 获取报名表的锁失败
     *              InternalError: 上传文件失败
     *
     * @return 创建的报名表
     */
    Result<ApplicationFormDTO> createApplicationForm(@NotNull(message = "The createApplicationFormPO can't be null.")
                                                             CreateApplicationFormPO createApplicationFormPO);

    /**
     * 获取报名表
     *
     * @permission 必须是用户本身，或者该报名表所属招新所属组织所属用户主体是用户本身
     *              或者是该报名表所属招新所属组织的面试官的用户主体本身
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotFound: 找不到该报名表
     *
     * @param id 报名表编号
     * @return 报名表
     */
    Result<ApplicationFormDTO> getApplicationForm(Long id);

    /**
     * 获取一个招新的报名表
     * 
     * @return
     */
    Result<PageInfo<ApplicationFormDTO>> listApplicationFormsByRecruitment();

}
