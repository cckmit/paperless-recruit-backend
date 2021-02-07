package com.xiaohuashifu.recruit.registration.api.service;

import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.common.validator.annotation.FullName;
import com.xiaohuashifu.recruit.common.validator.annotation.Phone;
import com.xiaohuashifu.recruit.common.validator.annotation.StudentNumber;
import com.xiaohuashifu.recruit.registration.api.constant.ApplicationFormConstants;
import com.xiaohuashifu.recruit.registration.api.dto.ApplicationFormDTO;
import com.xiaohuashifu.recruit.registration.api.request.CreateApplicationFormPO;

import javax.validation.constraints.*;

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
     *              InvalidParameter.NotExist: 学院不存在 | 专业不存在 | 招新不存在 | 报名表模板不存在 | 用户不存在
     *              InvalidParameter.NotContain: 学院不被包含 | 专业不被包含 | 部门不被包含
     *              InvalidParameter.Mismatch: 组织不包含该部门
     *              Forbidden.User: 用户不可用
     *              Forbidden.Unavailable: 招新不可用 | 组织不可用
     *              Forbidden.Deactivated: 学院被停用 | 专业被停用 | 部门被停用 | 报名表模板被停用
     *              OperationConflict.Status: 招新的状态必须是 STARTED
     *              OperationConflict.Duplicate: 报名表已经存在
     *              OperationConflict.Lock: 获取报名表的锁失败
     *              InternalError: 上传文件失败
     *
     * @return 创建的报名表
     */
    Result<ApplicationFormDTO> createApplicationForm(@NotNull CreateApplicationFormPO createApplicationFormPO);

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
    Result<ApplicationFormDTO> getApplicationForm(@NotNull @Positive Long id);

    /**
     * 更新头像
     *
     * @permission 必须是该报名表所属用户
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 报名表不存在
     *              InvalidParameter.NotRequired: 不需要该参数
     *              Forbidden.Unavailable: 招新不可用 | 组织不可用
     *              Forbidden.Deactivated: 报名表模板被停用
     *              OperationConflict.Status: 招新的状态必须是 STARTED
     *              OperationConflict.Lock: 获取报名表头像的锁失败
     *              UnprocessableEntity.NotExist 所要链接的对象不存在
     *              OperationConflict.Linked 对象已经链接
     *              OperationConflict.Deleted 对象已经删除
     *              InternalError 链接对象失败
     *
     * @param id 报名表编号
     * @param avatarUrl 报名表 url
     * @return 更新后的报名表
     */
    Result<ApplicationFormDTO> updateAvatar(
            @NotNull @Positive Long id,
            @NotNull @Pattern(regexp = ApplicationFormConstants.AVATAR_URL_PATTERN) String avatarUrl);

    /**
     * 更新姓名
     *
     * @permission 必须是该报名表所属用户
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 报名表不存在
     *              InvalidParameter.NotRequired: 不需要该参数
     *              Forbidden.Unavailable: 招新不可用 | 组织不可用
     *              Forbidden.Deactivated: 报名表模板被停用
     *              OperationConflict.Status: 招新的状态必须是 STARTED
     *
     * @param id 报名表编号
     * @param fullName 姓名
     * @return 更新后的报名表
     */
    Result<ApplicationFormDTO> updateFullName(
            @NotNull(message = "The id can't be null.") @Positive(message = "The id must be greater than 0.") Long id,
            @NotBlank(message = "The fullName can't be blank.") @FullName String fullName);

    /**
     * 更新手机号码
     *
     * @permission 必须是该报名表所属用户
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 报名表不存在
     *              InvalidParameter.NotRequired: 不需要该参数
     *              Forbidden.Unavailable: 招新不可用 | 组织不可用
     *              Forbidden.Deactivated: 报名表模板被停用
     *              OperationConflict.Status: 招新的状态必须是 STARTED
     *
     * @param id 报名表编号
     * @param phone 手机号码
     * @return 更新后的报名表
     */
    Result<ApplicationFormDTO> updatePhone(
            @NotNull(message = "The id can't be null.") @Positive(message = "The id must be greater than 0.") Long id,
            @NotBlank(message = "The phone can't be blank.") @Phone String phone);

    /**
     * 更新第一部门
     *
     * @permission 必须是该报名表所属用户
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 报名表不存在 | 部门不存在
     *              InvalidParameter.NotRequired: 不需要该参数
     *              InvalidParameter.NotContain: 该招新不包含该部门
     *              Forbidden.Unavailable: 招新不可用 | 组织不可用
     *              Forbidden.Deactivated: 报名表模板被停用 | 部门被停用
     *              OperationConflict.Status: 招新的状态必须是 STARTED
     *
     * @param id 报名表编号
     * @param firstDepartmentId 第一部门
     * @return 更新后的报名表
     */
    Result<ApplicationFormDTO> updateFirstDepartment(
            @NotNull(message = "The id can't be null.") @Positive(message = "The id must be greater than 0.") Long id,
            @NotNull(message = "The firstDepartmentId can't be null.")
            @Positive(message = "The firstDepartmentId must be greater than 0.") Long firstDepartmentId);

    /**
     * 更新第二部门
     *
     * @permission 必须是该报名表所属用户
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 报名表不存在 | 部门不存在
     *              InvalidParameter.NotRequired: 不需要该参数
     *              InvalidParameter.NotContain: 该招新不包含该部门
     *              Forbidden.Unavailable: 招新不可用 | 组织不可用
     *              Forbidden.Deactivated: 报名表模板被停用 | 部门被停用
     *              OperationConflict.Status: 招新的状态必须是 STARTED
     *
     * @param id 报名表编号
     * @param secondDepartmentId 第二部门
     * @return 更新后的报名表
     */
    Result<ApplicationFormDTO> updateSecondDepartment(
            @NotNull(message = "The id can't be null.") @Positive(message = "The id must be greater than 0.") Long id,
            @NotNull(message = "The secondDepartmentId can't be null.")
            @Positive(message = "The secondDepartmentId must be greater than 0.") Long secondDepartmentId);

    /**
     * 更新邮箱
     *
     * @permission 必须是该报名表所属用户
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 报名表不存在
     *              InvalidParameter.NotRequired: 不需要该参数
     *              Forbidden.Unavailable: 招新不可用 | 组织不可用
     *              Forbidden.Deactivated: 报名表模板被停用
     *              OperationConflict.Status: 招新的状态必须是 STARTED
     *
     * @param id 报名表编号
     * @param email 邮箱
     * @return 更新后的报名表
     */
    Result<ApplicationFormDTO> updateEmail(
            @NotNull(message = "The id can't be null.") @Positive(message = "The id must be greater than 0.") Long id,
            @NotBlank(message = "The email can't be blank.") @Email String email);

    /**
     * 更新个人简介
     *
     * @permission 必须是该报名表所属用户
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 报名表不存在
     *              InvalidParameter.NotRequired: 不需要该参数
     *              Forbidden.Unavailable: 招新不可用 | 组织不可用
     *              Forbidden.Deactivated: 报名表模板被停用
     *              OperationConflict.Status: 招新的状态必须是 STARTED
     *
     * @param id 报名表编号
     * @param introduction 个人简介
     * @return 更新后的报名表
     */
    Result<ApplicationFormDTO> updateIntroduction(
            @NotNull(message = "The id can't be null.") @Positive(message = "The id must be greater than 0.") Long id,
            @NotBlank(message = "The introduction can't be blank.")
            @Size(max = ApplicationFormConstants.MAX_INTRODUCTION_LENGTH,
                    message = "The length of introduction must not be greater than "
                            + ApplicationFormConstants.MAX_INTRODUCTION_LENGTH + ".") String introduction);

    /**
     * 更新附件
     *
     * @permission 必须是该报名表所属用户
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 报名表不存在
     *              InvalidParameter.NotRequired: 不需要该参数
     *              Forbidden.Unavailable: 招新不可用 | 组织不可用
     *              Forbidden.Deactivated: 报名表模板被停用
     *              OperationConflict.Status: 招新的状态必须是 STARTED
     *              OperationConflict.Lock: 获取报名表附件的锁失败
     *              UnprocessableEntity.NotExist 所要链接的对象不存在
     *              OperationConflict.Linked 对象已经链接
     *              OperationConflict.Deleted 对象已经删除
     *              InternalError 链接对象失败
     *
     * @param id 报名表编号
     * @param attachmentUrl 附件 url
     * @return 更新后的报名表
     */
    Result<ApplicationFormDTO> updateAttachment(
            @NotNull @Positive Long id,
            @NotBlank @Pattern(regexp = ApplicationFormConstants.ATTACHMENT_URL_PATTERN) String attachmentUrl);

    /**
     * 更新学号
     *
     * @permission 必须是该报名表所属用户
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 报名表不存在
     *              InvalidParameter.NotRequired: 不需要该参数
     *              Forbidden.Unavailable: 招新不可用 | 组织不可用
     *              Forbidden.Deactivated: 报名表模板被停用
     *              OperationConflict.Status: 招新的状态必须是 STARTED
     *
     * @param id 报名表编号
     * @param studentNumber 学号
     * @return 更新后的报名表
     */
    Result<ApplicationFormDTO> updateStudentNumber(
            @NotNull(message = "The id can't be null.") @Positive(message = "The id must be greater than 0.") Long id,
            @NotBlank(message = "The studentNumber can't be blank.") @StudentNumber String studentNumber);

    /**
     * 更新学院
     *
     * @permission 必须是该报名表所属用户
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 报名表不存在 | 学院不存在
     *              InvalidParameter.NotRequired: 不需要该参数
     *              InvalidParameter.NotContain: 该招新不包含该学院
     *              Forbidden.Unavailable: 招新不可用 | 组织不可用
     *              Forbidden.Deactivated: 报名表模板被停用 | 学院被停用
     *              OperationConflict.Status: 招新的状态必须是 STARTED
     *
     * @param id 报名表编号
     * @param collegeId 学院
     * @return 更新后的报名表
     */
    Result<ApplicationFormDTO> updateCollege(
            @NotNull(message = "The id can't be null.") @Positive(message = "The id must be greater than 0.") Long id,
            @NotNull(message = "The collegeId can't be null.")
            @Positive(message = "The collegeId must be greater than 0.") Long collegeId);

    /**
     * 更新专业
     *
     * @permission 必须是该报名表所属用户
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 报名表不存在 | 专业不存在
     *              InvalidParameter.NotRequired: 不需要该参数
     *              InvalidParameter.NotContain: 该招新不包含该专业
     *              Forbidden.Unavailable: 招新不可用 | 组织不可用
     *              Forbidden.Deactivated: 报名表模板被停用 | 专业被停用
     *              OperationConflict.Status: 招新的状态必须是 STARTED
     *
     * @param id 报名表编号
     * @param majorId 专业
     * @return 更新后的报名表
     */
    Result<ApplicationFormDTO> updateMajor(
            @NotNull(message = "The id can't be null.") @Positive(message = "The id must be greater than 0.") Long id,
            @NotNull(message = "The majorId can't be null.")
            @Positive(message = "The majorId must be greater than 0.") Long majorId);

    /**
     * 更新备注
     *
     * @permission 必须是该报名表所属用户
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 报名表不存在
     *              InvalidParameter.NotRequired: 不需要该参数
     *              Forbidden.Unavailable: 招新不可用 | 组织不可用
     *              Forbidden.Deactivated: 报名表模板被停用
     *              OperationConflict.Status: 招新的状态必须是 STARTED
     *
     * @param id 报名表编号
     * @param note 备注
     * @return 更新后的报名表
     */
    Result<ApplicationFormDTO> updateNote(
            @NotNull(message = "The id can't be null.") @Positive(message = "The id must be greater than 0.") Long id,
            @NotBlank(message = "The note can't be blank.")
            @Size(max = ApplicationFormConstants.MAX_NOTE_LENGTH,
                    message = "The length of note must not be greater than "
                            + ApplicationFormConstants.MAX_NOTE_LENGTH + ".") String note);

    /**
     * 获取报名表所属招新的编号
     *
     * @private 内部方法
     *
     * @param id 报名表编号
     * @return 招新编号，若找不到可能返回 null
     */
    Long getRecruitmentId(Long id);

    /**
     * 获取报名表所属用户的编号
     *
     * @private 内部方法
     *
     * @param id 报名表编号
     * @return 用户编号，若找不到可能返回 null
     */
    Long getUserId(Long id);
}
