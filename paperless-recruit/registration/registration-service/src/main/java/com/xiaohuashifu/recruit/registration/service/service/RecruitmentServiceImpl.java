package com.xiaohuashifu.recruit.registration.service.service;

import com.xiaohuashifu.recruit.common.constant.GradeEnum;
import com.xiaohuashifu.recruit.common.constant.MySqlConstants;
import com.xiaohuashifu.recruit.common.result.ErrorCodeEnum;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.organization.api.service.DepartmentService;
import com.xiaohuashifu.recruit.organization.api.service.OrganizationService;
import com.xiaohuashifu.recruit.registration.api.constant.RecruitmentStatusEnum;
import com.xiaohuashifu.recruit.registration.api.dto.RecruitmentDTO;
import com.xiaohuashifu.recruit.registration.api.po.CreateRecruitmentPO;
import com.xiaohuashifu.recruit.registration.api.service.RecruitmentService;
import com.xiaohuashifu.recruit.registration.service.dao.RecruitmentMapper;
import com.xiaohuashifu.recruit.registration.service.do0.RecruitmentDO;
import com.xiaohuashifu.recruit.user.api.service.CollegeService;
import com.xiaohuashifu.recruit.user.api.service.MajorService;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 描述：招新服务实现
 *
 * @author xhsf
 * @create 2020/12/26 20:34
 */
@Service
public class RecruitmentServiceImpl implements RecruitmentService {

    @Reference
    private OrganizationService organizationService;

    @Reference
    private DepartmentService departmentService;

    @Reference
    private CollegeService collegeService;

    @Reference
    private MajorService majorService;

    private final RecruitmentMapper recruitmentMapper;

    public RecruitmentServiceImpl(RecruitmentMapper recruitmentMapper) {
        this.recruitmentMapper = recruitmentMapper;
    }

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
    @Override
    public Result<RecruitmentDTO> createRecruitment(CreateRecruitmentPO createRecruitmentPO) {
        // 检查参数
        Result<RecruitmentDO> checkResult = checkForCreateRecruitment(createRecruitmentPO);
        if (checkResult.isFailure()) {
            return Result.fail(checkResult);
        }

        // 插入招新
        RecruitmentDO recruitmentDO = checkResult.getData();
        recruitmentMapper.insertRecruitment(recruitmentDO);
        return getRecruitment(recruitmentDO.getId());
    }

    /**
     * 添加招新学院，报名开始后无法添加
     *
     * @param id        招新的编号
     * @param collegeId 招新学院编号，若0表示将学院设置为不限，即清空招新学院
     * @return 添加结果
     * @permission 必须是招新所属组织所属用户主体本身
     */
    @Override
    public Result<RecruitmentDTO> addRecruitmentCollege(Long id, Long collegeId) {
        return null;
    }

    /**
     * 添加招新专业，报名开始后无法添加
     *
     * @param id      招新的编号
     * @param majorId 招新专业编号，若0表示将专业设置为不限，即清空招新专业
     * @return 添加结果
     * @permission 必须是招新所属组织所属用户主体本身
     */
    @Override
    public Result<RecruitmentDTO> addRecruitmentMajor(Long id, String majorId) {
        return null;
    }

    /**
     * 添加招新年级，报名开始后无法添加
     *
     * @param id               招新的编号
     * @param recruitmentGrade 招新年级，若null表示将年招设置为不限，即清空招新年级
     * @return 添加结果
     * @permission 必须是招新所属组织所属用户主体本身
     */
    @Override
    public Result<RecruitmentDTO> addRecruitmentGrade(Long id, GradeEnum recruitmentGrade) {
        return null;
    }

    /**
     * 添加招新的部门，报名开始后无法添加
     *
     * @param id           招新的编号
     * @param departmentId 招新部门的编号，若0表示将部门设置为不限，即清空招新部门
     * @return 添加结果
     * @permission 必须是招新所属组织所属用户主体本身
     */
    @Override
    public Result<RecruitmentDTO> addRecruitmentDepartment(Long id, Long departmentId) {
        return null;
    }

    /**
     * 移除招新学院，报名开始后无法移除
     *
     * @param id        招新的编号
     * @param collegeId 招新学院编号
     * @return 移除结果
     * @permission 必须是招新所属组织所属用户主体本身
     */
    @Override
    public Result<RecruitmentDTO> removeRecruitmentCollege(Long id, Long collegeId) {
        return null;
    }

    /**
     * 移除招新专业，报名开始后无法移除
     *
     * @param id      招新的编号
     * @param majorId 招新专业编号
     * @return 移除结果
     * @permission 必须是招新所属组织所属用户主体本身
     */
    @Override
    public Result<RecruitmentDTO> removeRecruitmentMajor(Long id, Long majorId) {
        return null;
    }

    /**
     * 移除招新年级，报名开始后无法移除
     *
     * @param id               招新的编号
     * @param recruitmentGrade 招新年级
     * @return 移除结果
     * @permission 必须是招新所属组织所属用户主体本身
     */
    @Override
    public Result<RecruitmentDTO> removeRecruitmentGrade(Long id, GradeEnum recruitmentGrade) {
        return null;
    }

    /**
     * 移除招新的部门，报名开始后无法移除
     *
     * @param id           招新的编号
     * @param departmentId 招新部门的编号
     * @return 移除结果
     * @permission 必须是招新所属组织所属用户主体本身
     */
    @Override
    public Result<RecruitmentDTO> removeRecruitmentDepartment(Long id, Long departmentId) {
        return null;
    }

    /**
     * 获取招新
     *
     * @param id 招新编号
     * @return RecruitmentDTO
     */
    @Override
    public Result<RecruitmentDTO> getRecruitment(Long id) {
        RecruitmentDO recruitmentDO = recruitmentMapper.getRecruitment(id);
        if (recruitmentDO == null) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_FOUND,
                    "The recruitment does not exist.");
        }
        return Result.success(recruitmentDO2RecruitmentDTO(recruitmentDO));
    }

    /**
     * 获取组织编号
     *
     * @param id 招新编号
     * @return 组织编号，若招新不存在则返回 null
     * @private 内部方法
     */
    @Override
    public Long getOrganizationId(Long id) {
        return null;
    }

    /**
     * 更新招新职位名，报名开始后无法更新
     *
     * @param id              招新的编号
     * @param newPositionName 新招新职位名
     * @return 更新结果
     * @permission 必须是招新所属组织所属用户主体本身
     */
    @Override
    public Result<RecruitmentDTO> updatePositionName(Long id, String newPositionName) {
        return null;
    }

    /**
     * 更新招新人数，报名开始后无法更新
     *
     * @param id                    招新的编号
     * @param newRecruitmentNumbers 新招新人数
     * @return 更新结果
     * @permission 必须是招新所属组织所属用户主体本身
     */
    @Override
    public Result<RecruitmentDTO> updateRecruitmentNumbers(Long id, String newRecruitmentNumbers) {
        return null;
    }

    /**
     * 更新职位职责，报名开始后无法更新
     *
     * @param id              招新的编号
     * @param newPositionDuty 新职位职责
     * @return 更新结果
     * @permission 必须是招新所属组织所属用户主体本身
     */
    @Override
    public Result<RecruitmentDTO> updatePositionDuty(Long id, String newPositionDuty) {
        return null;
    }

    /**
     * 更新职位要求，报名开始后无法更新
     *
     * @param id                     招新的编号
     * @param newPositionRequirement 新职位要求
     * @return 更新结果
     * @permission 必须是招新所属组织所属用户主体本身
     */
    @Override
    public Result<RecruitmentDTO> updatePositionRequirement(Long id, String newPositionRequirement) {
        return null;
    }

    /**
     * 更新发布时间，招新发布后无法更新
     *
     * @param id             招新的编号
     * @param newReleaseTime 新发布时间
     * @return 更新结果
     * @permission 必须是招新所属组织所属用户主体本身
     */
    @Override
    public Result<RecruitmentDTO> updateReleaseTime(Long id, LocalDateTime newReleaseTime) {
        return null;
    }

    /**
     * 更新报名的开始时间，报名开始后无法更新
     *
     * @param id                      招新的编号
     * @param newRegistrationTimeFrom 新报名开始时间
     * @return 更新结果
     * @permission 必须是招新所属组织所属用户主体本身
     */
    @Override
    public Result<RecruitmentDTO> updateRegistrationTimeFrom(Long id, LocalDateTime newRegistrationTimeFrom) {
        return null;
    }

    /**
     * 更新报名截止时间，报名结束后无法更新
     *
     * @param id                    招新的编号
     * @param newRegistrationTimeTo 新报名截止时间
     * @return 更新结果
     * @permission 必须是招新所属组织所属用户主体本身
     */
    @Override
    public Result<RecruitmentDTO> updateRegistrationTimeTo(Long id, LocalDateTime newRegistrationTimeTo) {
        return null;
    }

    /**
     * 更新招新的状态，用于状态的转换
     *
     * @param id                   招新的编号
     * @param oldRecruitmentStatus 原招新状态
     * @param newRecruitmentStatus 新招新状态
     * @return 更新结果
     * @private 内部方法
     */
    @Override
    public Result<Void> updateRecruitmentStatus(Long id, RecruitmentStatusEnum oldRecruitmentStatus, RecruitmentStatusEnum newRecruitmentStatus) {
        return null;
    }

    /**
     * 禁用一个招新
     *
     * @param id 招新的编号
     * @return 禁用结果
     * @permission 必须是 admin 权限
     */
    @Override
    public Result<RecruitmentDTO> disableRecruitment(Long id) {
        return null;
    }

    /**
     * 解禁一个招新
     *
     * @param id 招新的编号
     * @return 解禁结果
     * @permission 必须是 admin 权限
     */
    @Override
    public Result<RecruitmentDTO> enableRecruitment(Long id) {
        return null;
    }

    /**
     * 检查创建招新的参数
     *
     * @errorCode InvalidParameter: 招新发布时间小于招新开始时间 | 招新结束时间小于等于招新开始时间
     *              InvalidParameter.NotExist: 组织不存在 | 部门不存在 | 学院不存在 | 专业不存在
     *              Forbidden.Unavailable: 组织不可用
     *              Forbidden.Deactivated: 部门被停用 | 学院被停用 | 专业被停用
     *              Forbidden: 部门不属于该组织
     *
     * @param createRecruitmentPO CreateRecruitmentPO
     * @return 检查结果，检查成功返回招新的数据对象，可以直接用于插入数据库
     */
    private Result<RecruitmentDO> checkForCreateRecruitment(CreateRecruitmentPO createRecruitmentPO) {
        // 检查组织状态
        Result<RecruitmentDO> checkOrganizationStatusResult =
                organizationService.checkOrganizationStatus(createRecruitmentPO.getOrganizationId());
        if (checkOrganizationStatusResult.isFailure()) {
            return checkOrganizationStatusResult;
        }

        // 检查部门状态
        for (Long recruitmentDepartmentId : createRecruitmentPO.getRecruitmentDepartmentIds()) {
            // 判断部门是否属于该组织的
            Long organizationId = departmentService.getOrganizationId(recruitmentDepartmentId);
            if (!Objects.equals(organizationId, createRecruitmentPO.getOrganizationId())) {
                return Result.fail(ErrorCodeEnum.FORBIDDEN);
            }

            // 检查部门状态
            Result<RecruitmentDO> checkDepartmentStatusResult =
                    departmentService.checkDepartmentStatus(recruitmentDepartmentId);
            if (checkDepartmentStatusResult.isFailure()) {
                return checkDepartmentStatusResult;
            }
        }

        // 检查学院状态
        for (Long recruitmentCollegeId : createRecruitmentPO.getRecruitmentCollegeIds()) {
            Result<RecruitmentDO> checkCollegeStatusResult = collegeService.checkCollegeStatus(recruitmentCollegeId);
            if (checkCollegeStatusResult.isFailure()) {
                return checkCollegeStatusResult;
            }
        }

        // 检查专业状态
        for (Long recruitmentMajorId : createRecruitmentPO.getRecruitmentMajorIds()) {
            Result<RecruitmentDO> checkMajorStatusResult = majorService.checkMajorStatus(recruitmentMajorId);
            if (checkMajorStatusResult.isFailure()) {
                return checkMajorStatusResult;
            }
        }

        // 检查招新发布时间
        RecruitmentStatusEnum recruitmentStatus = RecruitmentStatusEnum.WAITING_FOR_RELEASE;
        if (createRecruitmentPO.getReleaseTime() == null) {
            createRecruitmentPO.setReleaseTime(LocalDateTime.now());
            recruitmentStatus = RecruitmentStatusEnum.WAITING_START;
        }

        // 招新发布时间不能大于数据库的上限
        LocalDateTime releaseTime = createRecruitmentPO.getReleaseTime();
        if (releaseTime.isAfter(MySqlConstants.DATE_TIME_MAX_VALUE)) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER,
                    "The releaseTime must not be greater than 9999-12-31 23:59:59");
        }

        // 检查报名开始时间
        if (createRecruitmentPO.getRegistrationTimeFrom() == null) {
            createRecruitmentPO.setRegistrationTimeFrom(createRecruitmentPO.getReleaseTime());
        }

        // 报名开始时间不能大于数据库的上限
        LocalDateTime registrationTimeFrom = createRecruitmentPO.getRegistrationTimeFrom();
        if (registrationTimeFrom.isAfter(MySqlConstants.DATE_TIME_MAX_VALUE)) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER,
                    "The registrationTimeFrom must not be greater than 9999-12-31 23:59:59");
        }

        // 报名开始时间必须大于等于招新发布时间
        if (registrationTimeFrom.isBefore(releaseTime)){
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER,
                    "The registrationTimeFrom must be greater than or equal to releaseTime.");
        }

        // 报名开始时间等于招新发布时间，则直接开始报名
        if (registrationTimeFrom.isEqual(releaseTime)) {
            recruitmentStatus = RecruitmentStatusEnum.STARTED;
        }

        // 检查报名结束时间
        if (createRecruitmentPO.getRegistrationTimeTo() == null) {
            createRecruitmentPO.setRegistrationTimeTo(MySqlConstants.DATE_TIME_MAX_VALUE);
        }

        // 报名结束时间不能大于数据库的上限
        LocalDateTime registrationTimeTo = createRecruitmentPO.getRegistrationTimeTo();
        if (registrationTimeTo.isAfter(MySqlConstants.DATE_TIME_MAX_VALUE)) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER,
                    "The registrationTimeTo must not be greater than 9999-12-31 23:59:59");
        }

        // 报名结束时间必须大于报名开始时间
        if (registrationTimeTo.isBefore(registrationTimeFrom) || registrationTimeTo.isEqual(registrationTimeFrom)){
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER,
                    "The registrationTimeTo must be greater than registrationTimeFrom.");
        }

        // 通过检查
        RecruitmentDO recruitmentDO = new RecruitmentDO.Builder()
                .organizationId(createRecruitmentPO.getOrganizationId())
                .positionName(createRecruitmentPO.getPositionName())
                .recruitmentNumbers(createRecruitmentPO.getRecruitmentNumbers())
                .positionDuty(createRecruitmentPO.getPositionDuty())
                .positionRequirement(createRecruitmentPO.getPositionRequirement())
                .recruitmentGrades(createRecruitmentPO.getRecruitmentGrades()
                        .stream()
                        .map(Enum::name)
                        .collect(Collectors.toSet()))
                .recruitmentDepartmentIds(createRecruitmentPO.getRecruitmentDepartmentIds())
                .recruitmentCollegeIds(createRecruitmentPO.getRecruitmentCollegeIds())
                .recruitmentMajorIds(createRecruitmentPO.getRecruitmentMajorIds())
                .releaseTime(createRecruitmentPO.getReleaseTime())
                .registrationTimeFrom(createRecruitmentPO.getRegistrationTimeFrom())
                .registrationTimeTo(createRecruitmentPO.getRegistrationTimeTo())
                .recruitmentStatus(recruitmentStatus.name())
                .build();
        return Result.success(recruitmentDO);
    }

    /**
     * RecruitmentDO to RecruitmentDTO
     *
     * @param recruitmentDO RecruitmentDO
     * @return RecruitmentDTO
     */
    private RecruitmentDTO recruitmentDO2RecruitmentDTO(RecruitmentDO recruitmentDO) {
        return new RecruitmentDTO.Builder()
                .id(recruitmentDO.getId())
                .organizationId(recruitmentDO.getOrganizationId())
                .recruitmentCollegeIds(recruitmentDO.getRecruitmentCollegeIds())
                .positionName(recruitmentDO.getPositionName())
                .recruitmentNumbers(recruitmentDO.getRecruitmentNumbers())
                .positionDuty(recruitmentDO.getPositionDuty())
                .positionRequirement(recruitmentDO.getPositionRequirement())
                .recruitmentGrades(recruitmentDO.getRecruitmentGrades())
                .recruitmentCollegeIds(recruitmentDO.getRecruitmentCollegeIds())
                .recruitmentMajorIds(recruitmentDO.getRecruitmentMajorIds())
                .releaseTime(recruitmentDO.getReleaseTime())
                .registrationTimeFrom(recruitmentDO.getRegistrationTimeFrom())
                .registrationTimeTo(recruitmentDO.getRegistrationTimeTo())
                .recruitmentStatus(recruitmentDO.getRecruitmentStatus())
                .available(recruitmentDO.getAvailable())
                .build();
    }
}
