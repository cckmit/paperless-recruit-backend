package com.xiaohuashifu.recruit.registration.api.service;

import com.xiaohuashifu.recruit.common.constant.GradeEnum;
import com.xiaohuashifu.recruit.common.exception.NotFoundServiceException;
import com.xiaohuashifu.recruit.common.exception.ServiceException;
import com.xiaohuashifu.recruit.common.validator.annotation.DateTime;
import com.xiaohuashifu.recruit.registration.api.constant.RecruitmentStatusEnum;
import com.xiaohuashifu.recruit.registration.api.dto.RecruitmentDTO;
import com.xiaohuashifu.recruit.registration.api.request.CreateRecruitmentRequest;
import com.xiaohuashifu.recruit.registration.api.request.UpdateRecruitmentRequest;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
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
     * @permission Role: organization
     *
     * @param request CreateRecruitmentRequest
     * @return 创建结果
     */
    RecruitmentDTO createRecruitment(@NotNull CreateRecruitmentRequest request) throws ServiceException;

    /**
     * 添加招新学院，报名结束后无法添加
     *
     * @permission Role: organization
     *
     * @param id 招新的编号
     * @param collegeId 招新学院编号，若0表示将学院设置为不限，即清空招新学院
     * @return 添加结果
     */
    RecruitmentDTO addRecruitmentCollege(@NotNull @Positive Long id, @NotNull @PositiveOrZero Long collegeId)
            throws ServiceException;

    /**
     * 添加招新专业，报名结束后无法添加
     *
     * @permission Role: organization
     *
     * @param id 招新的编号
     * @param majorId 招新专业编号，若0表示将专业设置为不限，即清空招新专业
     * @return 添加结果
     */
    RecruitmentDTO addRecruitmentMajor(@NotNull @Positive Long id, @NotNull @PositiveOrZero Long majorId)
            throws ServiceException;

    /**
     * 添加招新年级，报名结束后无法添加
     *
     * @permission Role: organization
     *
     * @param id 招新的编号
     * @param recruitmentGrade 招新年级，若 null 表示将年招设置为不限，即清空招新年级
     * @return 添加结果
     */
    RecruitmentDTO addRecruitmentGrade(@NotNull @Positive Long id, GradeEnum recruitmentGrade) throws ServiceException;

    /**
     * 添加招新的部门，报名结束后无法添加
     *
     * @permission Role: organization
     *
     * @param id 招新的编号
     * @param departmentId 招新部门的编号，若0表示将部门设置为不限，即清空招新部门
     * @return 添加结果
     */
    RecruitmentDTO addRecruitmentDepartment(@NotNull @Positive Long id, @NotNull @PositiveOrZero Long departmentId)
            throws ServiceException;

    /**
     * 移除招新学院，报名结束后无法移除
     *
     * @permission Role: organization
     *
     * @param id 招新的编号
     * @param collegeId 招新学院编号
     * @return 移除结果
     */
    RecruitmentDTO removeRecruitmentCollege(@NotNull @Positive Long id, @NotNull @Positive Long collegeId)
            throws ServiceException;

    /**
     * 移除招新专业，报名结束后无法移除
     *
     * @permission Role: organization
     *
     * @param id 招新的编号
     * @param majorId 招新专业编号
     * @return 移除结果
     */
    RecruitmentDTO removeRecruitmentMajor(@NotNull @Positive Long id, @NotNull @Positive Long majorId)
            throws ServiceException;

    /**
     * 移除招新年级，报名结束后无法移除
     *
     * @permission Role: organization
     *
     * @param id 招新的编号
     * @param recruitmentGrade 招新年级
     * @return 移除结果
     */
    RecruitmentDTO removeRecruitmentGrade(@NotNull @Positive Long id, @NotNull GradeEnum recruitmentGrade)
            throws ServiceException;

    /**
     * 移除招新的部门，报名结束后无法移除
     *
     * @permission Role: organization
     *
     * @param id 招新的编号
     * @param departmentId 招新部门的编号
     * @return 移除结果
     */
    RecruitmentDTO removeRecruitmentDepartment(@NotNull @Positive Long id, @NotNull @Positive Long departmentId)
            throws ServiceException;

    /**
     * 获取招新
     *
     * @param id 招新编号
     * @return RecruitmentDTO
     */
    RecruitmentDTO getRecruitment(@NotNull @Positive Long id) throws NotFoundServiceException;

    /**
     * 更新招新职位名，报名结束后无法更新
     *
     * @permission Role: organization
     *
     * @param request UpdateRecruitmentRequest
     * @return 更新结果
     */
    RecruitmentDTO updateRecruitment(@NotNull UpdateRecruitmentRequest request) throws ServiceException;

    /**
     * 更新发布时间，招新发布后无法更新
     *
     * @permission Role: organization
     *
     * @param id 招新的编号
     * @param releaseTime 发布时间，null 表示立即发布
     * @return 更新结果
     */
    RecruitmentDTO updateReleaseTime(@NotNull @Positive Long id, @FutureOrPresent @DateTime LocalDateTime releaseTime)
            throws ServiceException;

    /**
     * 更新报名的开始时间，报名开始后无法更新
     *
     * @permission Role: organization
     *
     * @param id 招新的编号
     * @param registrationTimeFrom 报名开始时间，null 表示招新发布后立刻开始报名
     * @return 更新结果
     */
    RecruitmentDTO updateRegistrationTimeFrom(
            @NotNull @Positive Long id, @FutureOrPresent @DateTime LocalDateTime registrationTimeFrom)
            throws ServiceException;

    /**
     * 更新报名截止时间，报名结束后无法更新
     *
     * @permission Role: organization
     *
     * @param id 招新的编号
     * @param registrationTimeTo 报名截止时间
     * @return 更新结果
     */
    RecruitmentDTO updateRegistrationTimeTo(
            @NotNull @Positive Long id, @NotNull @FutureOrPresent @DateTime LocalDateTime registrationTimeTo)
            throws ServiceException;

    /**
     * 结束一个招新的报名，必须是招新当前状态为 STARTED
     *
     * @permission Role: organization
     *
     * @param id 招新的编号
     * @return 更新结果
     */
    RecruitmentDTO endRegistration(@NotNull @Positive Long id) throws ServiceException;

    /**
     * 关闭一个招新
     *
     * @permission Role: organization
     *
     * @param id 招新的编号
     * @return 更新结果
     */
    RecruitmentDTO closeRecruitment(@NotNull @Positive Long id) throws ServiceException;

    /**
     * 禁用一个招新
     *
     * @permission Role: admin
     *
     * @param id 招新的编号
     * @return 禁用结果
     */
    RecruitmentDTO disableRecruitment(@NotNull @Positive Long id) throws ServiceException;

    /**
     * 解禁一个招新
     *
     * @permission Role: admin
     *
     * @param id 招新的编号
     * @return 解禁结果
     */
    RecruitmentDTO enableRecruitment(@NotNull @Positive Long id) throws ServiceException;

    /**
     * 更新招新的状态，用于状态的转换
     *
     * @private 内部方法
     *
     * @param id 招新的编号
     * @param oldRecruitmentStatus 旧招新状态
     * @param newRecruitmentStatus 新招新状态
     * @return RecruitmentDTO
     */
    RecruitmentDTO updateRecruitmentStatus(
            @NotNull @Positive Long id, @NotNull RecruitmentStatusEnum oldRecruitmentStatus,
            @NotNull RecruitmentStatusEnum newRecruitmentStatus)  throws ServiceException;

    /**
     * 检查招新状态
     *
     * @private 内部方法
     *
     * @param id 招新编号
     * @param followRecruitmentStatus 后续招新状态，当前状态必须小于该状态
     * @return 检查结果，检查成功返回当前招新的状态
     */
    RecruitmentDTO checkRecruitmentStatus(Long id, RecruitmentStatusEnum followRecruitmentStatus)
            throws ServiceException;

    /**
     * 检查招新状态
     *
     * @private 内部方法
     *
     * @param id 招新编号
     * @return 检查结果
     */
    RecruitmentDTO checkRecruitmentStatus(Long id) throws ServiceException;

}
