package com.xiaohuashifu.recruit.registration.api.service;

import com.xiaohuashifu.recruit.common.constant.GradeEnum;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.registration.api.constant.RecruitmentConstants;
import com.xiaohuashifu.recruit.registration.api.constant.RecruitmentStatusEnum;
import com.xiaohuashifu.recruit.registration.api.dto.RecruitmentDTO;
import com.xiaohuashifu.recruit.registration.api.po.CreateRecruitmentPO;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

/**
 * 描述：招新服务
 *
 * @author xhsf
 * @create 2020/12/23 19:30
 */
public interface RecruitmentService {

    /**
     * 创建一个招新
     *
     * @permission 必须是组织所属主体用户本身
     *
     * @errorCode InvalidParameter: 参数格式错误 | 招新发布时间小于招新开始时间 | 招新结束时间小于等于招新开始时间
     *              InvalidParameter.NotExist: 组织不存在 | 部门不存在 | 学院不存在 | 专业不存在
     *              Forbidden.Unavailable: 组织不可用
     *              Forbidden.Deactivated: 部门被停用 | 学院被停用 | 专业被停用
     *              Forbidden: 部门不属于该组织
     *
     * @param createRecruitmentPO 创建招新参数对象
     * @return 创建结果
     */
    Result<RecruitmentDTO> createRecruitment(@NotNull(message = "The createRecruitmentPO can't be null.")
                                                     CreateRecruitmentPO createRecruitmentPO);

    /**
     * 添加招新学院，报名结束后无法添加
     *
     * @permission 必须是招新所属组织所属用户主体本身
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 招新不存在 | 学院不存在
     *              Forbidden.Unavailable: 招新不可用 | 组织不可用
     *              Forbidden.Deactivated: 学院被停用
     *              OperationConflict.Status: 招新状态不允许
     *              OperationConflict.OverLimit: 招新学院数量超过限制数量
     *              OperationConflict.Duplicate: 招新学院已经存在
     *              OperationConflict.Lock: 获取招新学院编号的锁失败
     *
     * @param id 招新的编号
     * @param collegeId 招新学院编号，若0表示将学院设置为不限，即清空招新学院
     * @return 添加结果
     */
    Result<RecruitmentDTO> addRecruitmentCollege(
            @NotNull(message = "The id can't be null.") @Positive(message = "The id must be greater than 0.") Long id,
            @NotNull(message = "The collegeId can't be null.")
            @PositiveOrZero(message = "The collegeId must be greater than or equal to 0.") Long collegeId);

    /**
     * 添加招新专业，报名结束后无法添加
     *
     * @permission 必须是招新所属组织所属用户主体本身
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 招新不存在 | 专业不存在
     *              Forbidden.Unavailable: 招新不可用 | 组织不可用
     *              Forbidden.Deactivated: 专业被停用
     *              OperationConflict.Status: 招新状态不允许
     *              OperationConflict.OverLimit: 招新专业数量超过限制数量
     *              OperationConflict.Duplicate: 招新专业已经存在
     *              OperationConflict.Lock: 获取招新专业编号的锁失败
     *
     * @param id 招新的编号
     * @param majorId 招新专业编号，若0表示将专业设置为不限，即清空招新专业
     * @return 添加结果
     */
    Result<RecruitmentDTO> addRecruitmentMajor(
            @NotNull(message = "The id can't be null.") @Positive(message = "The id must be greater than 0.") Long id,
            @NotNull(message = "The majorId can't be null.")
            @PositiveOrZero(message = "The majorId must be greater than or equal to 0.") Long majorId);

    /**
     * 添加招新年级，报名结束后无法添加
     *
     * @permission 必须是招新所属组织所属用户主体本身
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 招新不存在
     *              Forbidden.Unavailable: 招新不可用 | 组织不可用
     *              OperationConflict.Status: 招新状态不允许
     *              OperationConflict.Duplicate: 招新专业已经存在
     *
     * @param id 招新的编号
     * @param recruitmentGrade 招新年级，若 null 表示将年招设置为不限，即清空招新年级
     * @return 添加结果
     */
    Result<RecruitmentDTO> addRecruitmentGrade(
            @NotNull(message = "The id can't be null.") @Positive(message = "The id must be greater than 0.") Long id,
            GradeEnum recruitmentGrade);

    /**
     * 添加招新的部门，报名结束后无法添加
     *
     * @permission 必须是招新所属组织所属用户主体本身
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 招新不存在 | 部门不存在
     *              Forbidden: 部门不属于该组织的
     *              Forbidden.Unavailable: 招新不可用 | 组织不可用
     *              Forbidden.Deactivated: 部门被停用
     *              OperationConflict.Status: 招新状态不允许
     *              OperationConflict.Duplicate: 招新部门已经存在
     *
     * @param id 招新的编号
     * @param departmentId 招新部门的编号，若0表示将部门设置为不限，即清空招新部门
     * @return 添加结果
     */
    Result<RecruitmentDTO> addRecruitmentDepartment(
            @NotNull(message = "The id can't be null.") @Positive(message = "The id must be greater than 0.") Long id,
            @NotNull(message = "The departmentId can't be null.")
            @PositiveOrZero(message = "The departmentId must be greater than or equal to 0.") Long departmentId);

    /**
     * 移除招新学院，报名结束后无法移除
     *
     * @permission 必须是招新所属组织所属用户主体本身
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 招新不存在
     *              Forbidden.Unavailable: 招新不可用 | 组织不可用
     *              OperationConflict.Status: 招新状态不允许
     *              OperationConflict.Unmodified: 招新学院不存在
     *
     * @param id 招新的编号
     * @param collegeId 招新学院编号
     * @return 移除结果
     */
    Result<RecruitmentDTO> removeRecruitmentCollege(
            @NotNull(message = "The id can't be null.") @Positive(message = "The id must be greater than 0.") Long id,
            @NotNull(message = "The collegeId can't be null.")
            @Positive(message = "The collegeId must be greater than 0.") Long collegeId);

    /**
     * 移除招新专业，报名结束后无法移除
     *
     * @permission 必须是招新所属组织所属用户主体本身
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 招新不存在
     *              Forbidden.Unavailable: 招新不可用 | 组织不可用
     *              OperationConflict.Status: 招新状态不允许
     *              OperationConflict.Unmodified: 招新专业不存在
     *
     * @param id 招新的编号
     * @param majorId 招新专业编号
     * @return 移除结果
     */
    Result<RecruitmentDTO> removeRecruitmentMajor(
            @NotNull(message = "The id can't be null.") @Positive(message = "The id must be greater than 0.") Long id,
            @NotNull(message = "The majorId can't be null.")
            @Positive(message = "The majorId must be greater than 0.") Long majorId);

    /**
     * 移除招新年级，报名结束后无法移除
     *
     * @permission 必须是招新所属组织所属用户主体本身
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 招新不存在
     *              Forbidden.Unavailable: 招新不可用 | 组织不可用
     *              OperationConflict.Status: 招新状态不允许
     *              OperationConflict.Unmodified: 招新年级不存在
     *
     * @param id 招新的编号
     * @param recruitmentGrade 招新年级
     * @return 移除结果
     */
    Result<RecruitmentDTO> removeRecruitmentGrade(
            @NotNull(message = "The id can't be null.") @Positive(message = "The id must be greater than 0.") Long id,
            @NotNull(message = "The recruitmentGrade can't be null.") GradeEnum recruitmentGrade);

    /**
     * 移除招新的部门，报名结束后无法移除
     *
     * @permission 必须是招新所属组织所属用户主体本身
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 招新不存在
     *              Forbidden.Unavailable: 招新不可用 | 组织不可用
     *              OperationConflict.Status: 招新状态不允许
     *              OperationConflict.Unmodified: 招新部门不存在
     *
     * @param id 招新的编号
     * @param departmentId 招新部门的编号
     * @return 移除结果
     */
    Result<RecruitmentDTO> removeRecruitmentDepartment(
            @NotNull(message = "The id can't be null.") @Positive(message = "The id must be greater than 0.") Long id,
            @NotNull(message = "The departmentId can't be null.")
            @Positive(message = "The departmentId must be greater than 0.") Long departmentId);

    /**
     * 获取招新
     *
     * @param id 招新编号
     * @return RecruitmentDTO
     */
    Result<RecruitmentDTO> getRecruitment(@NotNull(message = "The id can't be null.")
                                          @Positive(message = "The id must be greater than 0.") Long id);

    /**
     * 更新招新职位名，报名结束后无法更新
     *
     * @permission 必须是招新所属组织所属用户主体本身
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 招新不存在
     *              Forbidden.Unavailable: 招新不可用 | 组织不可用
     *              OperationConflict.Status: 招新状态不允许
     *              OperationConflict.Unmodified: 新旧职位名相同
     *
     * @param id 招新的编号
     * @param newPositionName 新招新职位名
     * @return 更新结果
     */
    Result<RecruitmentDTO> updatePositionName(
            @NotNull(message = "The id can't be null.") @Positive(message = "The id must be greater than 0.") Long id,
            @NotBlank(message = "The newPositionName can't be blank.")
            @Size(max = RecruitmentConstants.MAX_POSITION_NAME_LENGTH,
                    message = "The length of newPositionName must not be greater than "
                            + RecruitmentConstants.MAX_POSITION_NAME_LENGTH + ".") String newPositionName);

    /**
     * 更新招新人数，报名结束后无法更新
     *
     * @permission 必须是招新所属组织所属用户主体本身
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 招新不存在
     *              Forbidden.Unavailable: 招新不可用 | 组织不可用
     *              OperationConflict.Status: 招新状态不允许
     *              OperationConflict.Unmodified: 新旧招新人数相同
     *
     * @param id 招新的编号
     * @param newRecruitmentNumbers 新招新人数
     * @return 更新结果
     */
    Result<RecruitmentDTO> updateRecruitmentNumbers(
            @NotNull(message = "The id can't be null.") @Positive(message = "The id must be greater than 0.") Long id,
            @NotBlank(message = "The newRecruitmentNumbers can't be blank.")
            @Size(max = RecruitmentConstants.MAX_RECRUITMENT_NUMBERS_LENGTH,
                    message = "The length of newRecruitmentNumbers must not be greater than "
                            + RecruitmentConstants.MAX_RECRUITMENT_NUMBERS_LENGTH + ".") String newRecruitmentNumbers);

    /**
     * 更新职位职责，报名结束后无法更新
     *
     * @permission 必须是招新所属组织所属用户主体本身
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 招新不存在
     *              Forbidden.Unavailable: 招新不可用 | 组织不可用
     *              OperationConflict.Status: 招新状态不允许
     *              OperationConflict.Unmodified: 新旧职位职责相同
     *
     * @param id 招新的编号
     * @param newPositionDuty 新职位职责
     * @return 更新结果
     */
    Result<RecruitmentDTO> updatePositionDuty(
            @NotNull(message = "The id can't be null.") @Positive(message = "The id must be greater than 0.") Long id,
            @NotBlank(message = "The newPositionDuty can't be blank.")
            @Size(max = RecruitmentConstants.MAX_POSITION_DUTY_LENGTH,
                    message = "The length of newPositionDuty must not be greater than "
                            + RecruitmentConstants.MAX_POSITION_DUTY_LENGTH + ".") String newPositionDuty);

    /**
     * 更新职位要求，报名结束后无法更新
     *
     * @permission 必须是招新所属组织所属用户主体本身
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 招新不存在
     *              Forbidden.Unavailable: 招新不可用 | 组织不可用
     *              OperationConflict.Status: 招新状态不允许
     *              OperationConflict.Unmodified: 新旧职位要求相同
     *
     * @param id 招新的编号
     * @param newPositionRequirement 新职位要求
     * @return 更新结果
     */
    Result<RecruitmentDTO> updatePositionRequirement(
            @NotNull(message = "The id can't be null.") @Positive(message = "The id must be greater than 0.") Long id,
            @NotBlank(message = "The newPositionRequirement can't be blank.")
            @Size(max = RecruitmentConstants.MAX_POSITION_REQUIREMENT_LENGTH,
                    message = "The length of newPositionRequirement must not be greater than "
                            + RecruitmentConstants.MAX_POSITION_REQUIREMENT_LENGTH + ".")
                    String newPositionRequirement);

    /**
     * 更新发布时间，招新发布后无法更新
     *
     * @permission 必须是招新所属组织所属用户主体本身
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 招新不存在
     *              Forbidden.Unavailable: 招新不可用 | 组织不可用
     *              OperationConflict.Status: 招新状态不允许
     *              OperationConflict.Unmodified: 新旧发布时间相同
     *
     * @param id 招新的编号
     * @param newReleaseTime 新发布时间，null 表示立即发布
     * @return 更新结果
     */
    Result<RecruitmentDTO> updateReleaseTime(
            @NotNull(message = "The id can't be null.") @Positive(message = "The id must be greater than 0.") Long id,
            @FutureOrPresent(message = "The newReleaseTime must be greater than the current time.")
                    LocalDateTime newReleaseTime);

    /**
     * 更新报名的开始时间，报名开始后无法更新
     *
     * @permission 必须是招新所属组织所属用户主体本身
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 招新不存在
     *              Forbidden.Unavailable: 招新不可用 | 组织不可用
     *              OperationConflict.Status: 招新状态不允许
     *              OperationConflict.Unmodified: 新旧报名开始时间相同，或者是新报名开始时间小于发布时间
     *
     * @param id 招新的编号
     * @param newRegistrationTimeFrom 新报名开始时间，null 表示招新发布后立刻开始报名
     * @return 更新结果
     */
    Result<RecruitmentDTO> updateRegistrationTimeFrom(
            @NotNull(message = "The id can't be null.") @Positive(message = "The id must be greater than 0.") Long id,
            @FutureOrPresent(message = "The newRegistrationTimeFrom must be greater than the current time.")
                    LocalDateTime newRegistrationTimeFrom);

    /**
     * 更新报名截止时间，报名结束后无法更新
     *
     * @permission 必须是招新所属组织所属用户主体本身
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 招新不存在
     *              Forbidden.Unavailable: 招新不可用 | 组织不可用
     *              OperationConflict.Status: 招新状态不允许
     *              OperationConflict.Unmodified: 新旧报名截止时间相同，或者是新报名截止时间小于或等于报名开始
     *
     * @param id 招新的编号
     * @param newRegistrationTimeTo 新报名截止时间
     * @return 更新结果
     */
    Result<RecruitmentDTO> updateRegistrationTimeTo(
            @NotNull(message = "The id can't be null.") @Positive(message = "The id must be greater than 0.") Long id,
            @NotNull(message = "The newRegistrationTimeTo can't be null.")
            @FutureOrPresent(message = "The newRegistrationTimeTo must be greater than the current time.")
                    LocalDateTime newRegistrationTimeTo);

    /**
     * 结束一个招新的报名，必须是招新当前状态为 STARTED
     *
     * @permission 必须是招新所属组织所属用户主体本身
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 招新不存在
     *              Forbidden.Unavailable: 招新不可用 | 组织不可用
     *              OperationConflict.Status: 招新状态不允许
     *              OperationConflict.Unmodified: 当前状态不是 STARTED
     *
     * @param id 招新的编号
     * @return 更新结果
     */
    Result<RecruitmentDTO> endRegistration(@NotNull(message = "The id can't be null.")
                                           @Positive(message = "The id must be greater than 0.") Long id);

    /**
     * 关闭一个招新
     *
     * @permission 必须是招新所属组织所属用户主体本身
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 招新不存在
     *              Forbidden.Unavailable: 招新不可用 | 组织不可用
     *              OperationConflict.Unmodified: 当前状态必须不是 CLOSED
     *
     * @param id 招新的编号
     * @return 更新结果
     */
    Result<RecruitmentDTO> closeRecruitment(@NotNull(message = "The id can't be null.")
                                            @Positive(message = "The id must be greater than 0.") Long id);


    /**
     * 禁用一个招新
     *
     * @permission 必须是 admin 权限
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 招新不存在
     *              OperationConflict.Unmodified: 招新已经不可用
     *
     * @param id 招新的编号
     * @return 禁用结果
     */
    Result<RecruitmentDTO> disableRecruitment(@NotNull(message = "The id can't be null.")
                                              @Positive(message = "The id must be greater than 0.") Long id);

    /**
     * 解禁一个招新
     *
     * @permission 必须是 admin 权限
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 招新不存在
     *              OperationConflict.Unmodified: 招新已经可用
     *
     * @param id 招新的编号
     * @return 解禁结果
     */
    Result<RecruitmentDTO> enableRecruitment(@NotNull(message = "The id can't be null.")
                                             @Positive(message = "The id must be greater than 0.") Long id);

    /**
     * 获取组织编号
     *
     * @private 内部方法
     *
     * @param id 招新编号
     * @return 组织编号，若招新不存在则返回 null
     */
    Long getOrganizationId(Long id);

    /**
     * 更新招新的状态，用于状态的转换
     *
     * @private 内部方法
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              OperationConflict.Unmodified: 修改失败，一般是由于招新编号不存在或者旧状态错误
     *
     * @param id 招新的编号
     * @param oldRecruitmentStatus 旧招新状态
     * @param newRecruitmentStatus 新招新状态
     * @return 更新结果
     */
    Result<Void> updateRecruitmentStatus(
            @NotNull(message = "The id can't be null.") @Positive(message = "The id must be greater than 0.") Long id,
            @NotNull(message = "The oldRecruitmentStatus can't be null.") RecruitmentStatusEnum oldRecruitmentStatus,
            @NotNull(message = "The newRecruitmentStatus can't be null.") RecruitmentStatusEnum newRecruitmentStatus);

    /**
     * 判断一个招新是否可以报名
     *
     * @private 内部方法
     *
     * @errorCode InvalidParameter.NotExist: 招新不存在 | 报名表模板不存在
     *              Forbidden.Unavailable: 招新不可用 | 组织不可用
     *               Forbidden.Deactivated: 报名表模板被停用
     *              OperationConflict.Status: 招新的状态必须是 STARTED
     *
     * @param id 招新编号
     * @return 是否可以报名
     */
    <T> Result<T> canRegistration(@NotNull(message = "The id can't be null.")
                                  @Positive(message = "The id must be greater than 0.") Long id);

}
