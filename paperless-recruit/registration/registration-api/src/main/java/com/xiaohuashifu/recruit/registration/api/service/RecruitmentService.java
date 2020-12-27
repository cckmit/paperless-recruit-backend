package com.xiaohuashifu.recruit.registration.api.service;

import com.xiaohuashifu.recruit.common.constant.GradeEnum;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.registration.api.constant.RecruitmentStatusEnum;
import com.xiaohuashifu.recruit.registration.api.dto.RecruitmentDTO;
import com.xiaohuashifu.recruit.registration.api.po.CreateRecruitmentPO;

import javax.validation.constraints.NotNull;
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
     * 添加招新学院，报名开始后无法添加
     *
     * @permission 必须是招新所属组织所属用户主体本身
     *
     * @param id 招新的编号
     * @param collegeId 招新学院编号，若0表示将学院设置为不限，即清空招新学院
     * @return 添加结果
     */
    Result<RecruitmentDTO> addRecruitmentCollege(Long id, Long collegeId);

    /**
     * 添加招新专业，报名开始后无法添加
     *
     * @permission 必须是招新所属组织所属用户主体本身
     *
     * @param id 招新的编号
     * @param majorId 招新专业编号，若0表示将专业设置为不限，即清空招新专业
     * @return 添加结果
     */
    Result<RecruitmentDTO> addRecruitmentMajor(Long id, String majorId);

    /**
     * 添加招新年级，报名开始后无法添加
     *
     * @permission 必须是招新所属组织所属用户主体本身
     *
     * @param id 招新的编号
     * @param recruitmentGrade 招新年级，若null表示将年招设置为不限，即清空招新年级
     * @return 添加结果
     */
    Result<RecruitmentDTO> addRecruitmentGrade(Long id, GradeEnum recruitmentGrade);

    /**
     * 添加招新的部门，报名开始后无法添加
     *
     * @permission 必须是招新所属组织所属用户主体本身
     *
     * @param id 招新的编号
     * @param departmentId 招新部门的编号，若0表示将部门设置为不限，即清空招新部门
     * @return 添加结果
     */
    Result<RecruitmentDTO> addRecruitmentDepartment(Long id, Long departmentId);

    /**
     * 移除招新学院，报名开始后无法移除
     *
     * @permission 必须是招新所属组织所属用户主体本身
     *
     * @param id 招新的编号
     * @param collegeId 招新学院编号
     * @return 移除结果
     */
    Result<RecruitmentDTO> removeRecruitmentCollege(Long id, Long collegeId);

    /**
     * 移除招新专业，报名开始后无法移除
     *
     * @permission 必须是招新所属组织所属用户主体本身
     *
     * @param id 招新的编号
     * @param majorId 招新专业编号
     * @return 移除结果
     */
    Result<RecruitmentDTO> removeRecruitmentMajor(Long id, Long majorId);

    /**
     * 移除招新年级，报名开始后无法移除
     *
     * @permission 必须是招新所属组织所属用户主体本身
     *
     * @param id 招新的编号
     * @param recruitmentGrade 招新年级
     * @return 移除结果
     */
    Result<RecruitmentDTO> removeRecruitmentGrade(Long id, GradeEnum recruitmentGrade);

    /**
     * 移除招新的部门，报名开始后无法移除
     *
     * @permission 必须是招新所属组织所属用户主体本身
     *
     * @param id 招新的编号
     * @param departmentId 招新部门的编号
     * @return 移除结果
     */
    Result<RecruitmentDTO> removeRecruitmentDepartment(Long id, Long departmentId);

    /**
     * 获取招新
     *
     * @param id 招新编号
     * @return RecruitmentDTO
     */
    Result<RecruitmentDTO> getRecruitment(Long id);

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
     * 更新招新职位名，报名开始后无法更新
     *
     * @permission 必须是招新所属组织所属用户主体本身
     *
     * @param id 招新的编号
     * @param newPositionName 新招新职位名
     * @return 更新结果
     */
    Result<RecruitmentDTO> updatePositionName(Long id, String newPositionName);

    /**
     * 更新招新人数，报名开始后无法更新
     *
     * @permission 必须是招新所属组织所属用户主体本身
     *
     * @param id 招新的编号
     * @param newRecruitmentNumbers 新招新人数
     * @return 更新结果
     */
    Result<RecruitmentDTO> updateRecruitmentNumbers(Long id, String newRecruitmentNumbers);

    /**
     * 更新职位职责，报名开始后无法更新
     *
     * @permission 必须是招新所属组织所属用户主体本身
     *
     * @param id 招新的编号
     * @param newPositionDuty 新职位职责
     * @return 更新结果
     */
    Result<RecruitmentDTO> updatePositionDuty(Long id, String newPositionDuty);

    /**
     * 更新职位要求，报名开始后无法更新
     *
     * @permission 必须是招新所属组织所属用户主体本身
     *
     * @param id 招新的编号
     * @param newPositionRequirement 新职位要求
     * @return 更新结果
     */
    Result<RecruitmentDTO> updatePositionRequirement(Long id, String newPositionRequirement);

    /**
     * 更新发布时间，招新发布后无法更新
     *
     * @permission 必须是招新所属组织所属用户主体本身
     *
     * @param id 招新的编号
     * @param newReleaseTime 新发布时间
     * @return 更新结果
     */
    Result<RecruitmentDTO> updateReleaseTime(Long id, LocalDateTime newReleaseTime);

    /**
     * 更新报名的开始时间，报名开始后无法更新
     *
     * @permission 必须是招新所属组织所属用户主体本身
     *
     * @param id 招新的编号
     * @param newRegistrationTimeFrom 新报名开始时间
     * @return 更新结果
     */
    Result<RecruitmentDTO> updateRegistrationTimeFrom(Long id, LocalDateTime newRegistrationTimeFrom);

    /**
     * 更新报名截止时间，报名结束后无法更新
     *
     * @permission 必须是招新所属组织所属用户主体本身
     *
     * @param id 招新的编号
     * @param newRegistrationTimeTo 新报名截止时间
     * @return 更新结果
     */
    Result<RecruitmentDTO> updateRegistrationTimeTo(Long id, LocalDateTime newRegistrationTimeTo);

    /**
     * 更新招新的状态，用于状态的转换
     *
     * @private 内部方法
     *
     * @param id 招新的编号
     * @param oldRecruitmentStatus 原招新状态
     * @param newRecruitmentStatus 新招新状态
     * @return 更新结果
     */
    Result<Void> updateRecruitmentStatus(Long id, RecruitmentStatusEnum oldRecruitmentStatus,
                                         RecruitmentStatusEnum newRecruitmentStatus);

    /**
     * 禁用一个招新
     *
     * @permission 必须是 admin 权限
     *
     * @param id 招新的编号
     * @return 禁用结果
     */
    Result<RecruitmentDTO> disableRecruitment(Long id);

    /**
     * 解禁一个招新
     *
     * @permission 必须是 admin 权限
     *
     * @param id 招新的编号
     * @return 解禁结果
     */
    Result<RecruitmentDTO> enableRecruitment(Long id);

}
