package com.xiaohuashifu.recruit.registration.service.service;

import com.xiaohuashifu.recruit.common.aspect.annotation.DistributedLock;
import com.xiaohuashifu.recruit.common.constant.GradeEnum;
import com.xiaohuashifu.recruit.common.constant.MySqlConstants;
import com.xiaohuashifu.recruit.common.result.ErrorCodeEnum;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.organization.api.service.DepartmentService;
import com.xiaohuashifu.recruit.organization.api.service.OrganizationService;
import com.xiaohuashifu.recruit.registration.api.constant.RecruitmentConstants;
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

    /**
     * 招新学院编号列表锁定键模式，{0}是招新编号
     */
    private static final String RECRUITMENT_COLLEGE_IDS_LOCK_KEY_PATTERN = "recruitment:{0}:recruitment-college-ids";

    /**
     * 招新专业编号列表锁定键模式，{0}是招新编号
     */
    private static final String RECRUITMENT_MAJOR_IDS_LOCK_KEY_PATTERN = "recruitment:{0}:recruitment-major-ids";


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
     * 添加招新学院，报名结束后无法添加
     *
     * @permission 必须是招新所属组织所属用户主体本身
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 招新不存在 | 学院不存在
     *              Forbidden.Unavailable: 招新不可用
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
    @DistributedLock(value = RECRUITMENT_COLLEGE_IDS_LOCK_KEY_PATTERN, parameters = "#{#id}",
            errorMessage = "Failed to acquire recruitmentCollegeIds lock.")
    @Override
    public Result<RecruitmentDTO> addRecruitmentCollege(Long id, Long collegeId) {
        // 检查招新状态
        Result<RecruitmentStatusEnum> checkResult = checkRecruitmentStatus(id, RecruitmentStatusEnum.ENDED);
        if (checkResult.isFailure()) {
            return Result.fail(checkResult);
        }

        // 如果学院编号为0，则直接清空招新学院
        if (Objects.equals(0L, collegeId)) {
            recruitmentMapper.clearRecruitmentCollegeIds(id);
        }
        // 否则走正常的添加招新学院流程
        else {
            // 检查学院状态
            Result<RecruitmentDTO> checkCollegeStatusResult = collegeService.checkCollegeStatus(collegeId);
            if (checkCollegeStatusResult.isFailure()) {
                return checkCollegeStatusResult;
            }

            // 判断招新学院数量是否超过限制
            int count = recruitmentMapper.countRecruitmentCollegeIds(id);
            if (count >= RecruitmentConstants.MAX_RECRUITMENT_COLLEGE_NUMBERS) {
                return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT_OVER_LIMIT,
                        "The number of recruitment colleges can't greater than "
                                + RecruitmentConstants.MAX_RECRUITMENT_COLLEGE_NUMBERS + ".");
            }

            // 添加招新学院
            count = recruitmentMapper.addRecruitmentCollege(id, collegeId);

            // 添加失败表示该学院已经存在
            if (count < 1) {
                return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT_DUPLICATE,
                        "The college already exist.");
            }
        }

        // 添加成功
        return getRecruitment(id);
    }

    /**
     * 添加招新专业，报名结束后无法添加
     *
     * @permission 必须是招新所属组织所属用户主体本身
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 招新不存在 | 专业不存在
     *              Forbidden.Unavailable: 招新不可用
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
    @DistributedLock(value = RECRUITMENT_MAJOR_IDS_LOCK_KEY_PATTERN, parameters = "#{#id}",
            errorMessage = "Failed to acquire recruitmentMajorIds lock.")
    @Override
    public Result<RecruitmentDTO> addRecruitmentMajor(Long id, Long majorId) {
        // 检查招新状态
        Result<RecruitmentStatusEnum> checkResult = checkRecruitmentStatus(id, RecruitmentStatusEnum.ENDED);
        if (checkResult.isFailure()) {
            return Result.fail(checkResult);
        }

        // 如果专业编号为0，则直接清空招新专业
        if (Objects.equals(0L, majorId)) {
            recruitmentMapper.clearRecruitmentMajorIds(id);
        }
        // 否则走正常的添加招新专业流程
        else {
            // 检查专业状态
            Result<RecruitmentDTO> checkMajorStatusResult = majorService.checkMajorStatus(majorId);
            if (checkMajorStatusResult.isFailure()) {
                return checkMajorStatusResult;
            }

            // 判断招新专业数量是否超过限制
            int count = recruitmentMapper.countRecruitmentMajorIds(id);
            if (count >= RecruitmentConstants.MAX_RECRUITMENT_MAJOR_NUMBERS) {
                return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT_OVER_LIMIT,
                        "The number of recruitment majors can't greater than "
                                + RecruitmentConstants.MAX_RECRUITMENT_MAJOR_NUMBERS + ".");
            }

            // 添加招新专业
            count = recruitmentMapper.addRecruitmentMajor(id, majorId);

            // 添加失败表示该专业已经存在
            if (count < 1) {
                return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT_DUPLICATE,
                        "The major already exist.");
            }
        }

        // 添加成功
        return getRecruitment(id);
    }

    /**
     * 添加招新年级，报名结束后无法添加
     *
     * @permission 必须是招新所属组织所属用户主体本身
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 招新不存在
     *              Forbidden.Unavailable: 招新不可用
     *              OperationConflict.Status: 招新状态不允许
     *              OperationConflict.Duplicate: 招新专业已经存在
     *
     * @param id 招新的编号
     * @param recruitmentGrade 招新年级，若 null 表示将年招设置为不限，即清空招新年级
     * @return 添加结果
     */
    @Override
    public Result<RecruitmentDTO> addRecruitmentGrade(Long id, GradeEnum recruitmentGrade) {
        // 检查招新状态
        Result<RecruitmentStatusEnum> checkResult = checkRecruitmentStatus(id, RecruitmentStatusEnum.ENDED);
        if (checkResult.isFailure()) {
            return Result.fail(checkResult);
        }

        // 如果招新年级为 null，则直接清空招新年级
        if (recruitmentGrade == null) {
            recruitmentMapper.clearRecruitmentGrades(id);
        }
        // 否则走正常的添加招新年级流程
        else {
            // 添加招新年级
            int count = recruitmentMapper.addRecruitmentGrade(id, recruitmentGrade.name());

            // 添加失败表示该年级已经存在
            if (count < 1) {
                return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT_DUPLICATE,
                        "The grade already exist.");
            }
        }

        // 添加成功
        return getRecruitment(id);
    }

    /**
     * 添加招新的部门，报名结束后无法添加
     *
     * @permission 必须是招新所属组织所属用户主体本身
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 招新不存在 | 部门不存在
     *              Forbidden: 部门不属于该组织的
     *              Forbidden.Unavailable: 招新不可用
     *              Forbidden.Deactivated: 部门被停用
     *              OperationConflict.Status: 招新状态不允许
     *              OperationConflict.Duplicate: 招新部门已经存在
     *
     * @param id 招新的编号
     * @param departmentId 招新部门的编号，若0表示将部门设置为不限，即清空招新部门
     * @return 添加结果
     */
    @Override
    public Result<RecruitmentDTO> addRecruitmentDepartment(Long id, Long departmentId) {
        // 检查招新状态
        Result<RecruitmentStatusEnum> checkResult = checkRecruitmentStatus(id, RecruitmentStatusEnum.ENDED);
        if (checkResult.isFailure()) {
            return Result.fail(checkResult);
        }

        // 如果部门编号为0，则直接清空招新部门
        if (Objects.equals(0L, departmentId)) {
            recruitmentMapper.clearRecruitmentDepartmentIds(id);
        }
        // 否则走正常的添加招新部门流程
        else {
            // 判断部门是否属于该组织的
            Long organizationId = departmentService.getOrganizationId(departmentId);
            if (!Objects.equals(organizationId, recruitmentMapper.getOrganizationId(id))) {
                return Result.fail(ErrorCodeEnum.FORBIDDEN);
            }

            // 检查部门状态
            Result<RecruitmentDTO> checkDepartmentStatusResult = departmentService.checkDepartmentStatus(departmentId);
            if (checkDepartmentStatusResult.isFailure()) {
                return checkDepartmentStatusResult;
            }

            // 添加招新部门
            int count = recruitmentMapper.addRecruitmentDepartment(id, departmentId);

            // 添加失败表示该部门已经存在
            if (count < 1) {
                return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT_DUPLICATE,
                        "The department already exist.");
            }
        }

        // 添加成功
        return getRecruitment(id);
    }

    /**
     * 移除招新学院，报名结束后无法移除
     *
     * @permission 必须是招新所属组织所属用户主体本身
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 招新不存在
     *              Forbidden.Unavailable: 招新不可用
     *              OperationConflict.Status: 招新状态不允许
     *              OperationConflict.Unmodified: 招新学院不存在
     *
     * @param id 招新的编号
     * @param collegeId 招新学院编号
     * @return 移除结果
     */
    @Override
    public Result<RecruitmentDTO> removeRecruitmentCollege(Long id, Long collegeId) {
        // 检查招新状态
        Result<RecruitmentStatusEnum> checkResult = checkRecruitmentStatus(id, RecruitmentStatusEnum.ENDED);
        if (checkResult.isFailure()) {
            return Result.fail(checkResult);
        }

        // 移除招新学院
        int count = recruitmentMapper.removeRecruitmentCollege(id, collegeId);
        if (count < 1) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT_UNMODIFIED,
                    "The college does not exist.");
        }

        // 更新后的招新
        return getRecruitment(id);
    }

    /**
     * 移除招新专业，报名结束后无法移除
     *
     * @permission 必须是招新所属组织所属用户主体本身
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 招新不存在
     *              Forbidden.Unavailable: 招新不可用
     *              OperationConflict.Status: 招新状态不允许
     *              OperationConflict.Unmodified: 招新专业不存在
     *
     * @param id 招新的编号
     * @param majorId 招新专业编号
     * @return 移除结果
     */
    @Override
    public Result<RecruitmentDTO> removeRecruitmentMajor(Long id, Long majorId) {
        // 检查招新状态
        Result<RecruitmentStatusEnum> checkResult = checkRecruitmentStatus(id, RecruitmentStatusEnum.ENDED);
        if (checkResult.isFailure()) {
            return Result.fail(checkResult);
        }

        // 移除招新专业
        int count = recruitmentMapper.removeRecruitmentMajor(id, majorId);
        if (count < 1) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT_UNMODIFIED, "The major does not exist.");
        }

        // 更新后的招新
        return getRecruitment(id);
    }

    /**
     * 移除招新年级，报名结束后无法移除
     *
     * @permission 必须是招新所属组织所属用户主体本身
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 招新不存在
     *              Forbidden.Unavailable: 招新不可用
     *              OperationConflict.Status: 招新状态不允许
     *              OperationConflict.Unmodified: 招新年级不存在
     *
     * @param id 招新的编号
     * @param recruitmentGrade 招新年级
     * @return 移除结果
     */
    @Override
    public Result<RecruitmentDTO> removeRecruitmentGrade(Long id, GradeEnum recruitmentGrade) {
        // 检查招新状态
        Result<RecruitmentStatusEnum> checkResult = checkRecruitmentStatus(id, RecruitmentStatusEnum.ENDED);
        if (checkResult.isFailure()) {
            return Result.fail(checkResult);
        }

        // 移除招新年级
        int count = recruitmentMapper.removeRecruitmentGrade(id, recruitmentGrade.name());
        if (count < 1) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT_UNMODIFIED, "The grade does not exist.");
        }

        // 更新后的招新
        return getRecruitment(id);
    }

    /**
     * 移除招新的部门，报名结束后无法移除
     *
     * @permission 必须是招新所属组织所属用户主体本身
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 招新不存在
     *              Forbidden.Unavailable: 招新不可用
     *              OperationConflict.Status: 招新状态不允许
     *              OperationConflict.Unmodified: 招新部门不存在
     *
     * @param id 招新的编号
     * @param departmentId 招新部门的编号
     * @return 移除结果
     */
    @Override
    public Result<RecruitmentDTO> removeRecruitmentDepartment(Long id, Long departmentId) {
        // 检查招新状态
        Result<RecruitmentStatusEnum> checkResult = checkRecruitmentStatus(id, RecruitmentStatusEnum.ENDED);
        if (checkResult.isFailure()) {
            return Result.fail(checkResult);
        }

        // 移除招新部门
        int count = recruitmentMapper.removeRecruitmentDepartment(id, departmentId);
        if (count < 1) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT_UNMODIFIED,
                    "The department does not exist.");
        }

        // 更新后的招新
        return getRecruitment(id);
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
     * 更新招新职位名，报名结束后无法更新
     *
     * @permission 必须是招新所属组织所属用户主体本身
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 招新不存在
     *              Forbidden.Unavailable: 招新不可用
     *              OperationConflict.Status: 招新状态不允许
     *              OperationConflict.Unmodified: 新旧职位名相同
     *
     * @param id 招新的编号
     * @param newPositionName 新招新职位名
     * @return 更新结果
     */
    @Override
    public Result<RecruitmentDTO> updatePositionName(Long id, String newPositionName) {
        // 检查招新状态
        Result<RecruitmentStatusEnum> checkResult = checkRecruitmentStatus(id, RecruitmentStatusEnum.ENDED);
        if (checkResult.isFailure()) {
            return Result.fail(checkResult);
        }

        // 更新职位名
        int count = recruitmentMapper.updatePositionName(id, newPositionName);
        if (count < 1) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT_UNMODIFIED,
                    "The newPositionName can't be same as the oldPositionName.");
        }

        // 更新后的招新
        return getRecruitment(id);
    }

    /**
     * 更新招新人数，报名结束后无法更新
     *
     * @permission 必须是招新所属组织所属用户主体本身
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 招新不存在
     *              Forbidden.Unavailable: 招新不可用
     *              OperationConflict.Status: 招新状态不允许
     *              OperationConflict.Unmodified: 新旧招新人数相同
     *
     * @param id 招新的编号
     * @param newRecruitmentNumbers 新招新人数
     * @return 更新结果
     */
    @Override
    public Result<RecruitmentDTO> updateRecruitmentNumbers(Long id, String newRecruitmentNumbers) {
        // 检查招新状态
        Result<RecruitmentStatusEnum> checkResult = checkRecruitmentStatus(id, RecruitmentStatusEnum.ENDED);
        if (checkResult.isFailure()) {
            return Result.fail(checkResult);
        }

        // 更新招新人数
        int count = recruitmentMapper.updateRecruitmentNumbers(id, newRecruitmentNumbers);
        if (count < 1) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT_UNMODIFIED,
                    "The newRecruitmentNumbers can't be same as the oldRecruitmentNumbers.");
        }

        // 更新后的招新
        return getRecruitment(id);
    }

    /**
     * 更新职位职责，报名结束后无法更新
     *
     * @permission 必须是招新所属组织所属用户主体本身
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 招新不存在
     *              Forbidden.Unavailable: 招新不可用
     *              OperationConflict.Status: 招新状态不允许
     *              OperationConflict.Unmodified: 新旧职位职责相同
     *
     * @param id 招新的编号
     * @param newPositionDuty 新职位职责
     * @return 更新结果
     */
    @Override
    public Result<RecruitmentDTO> updatePositionDuty(Long id, String newPositionDuty) {
        // 检查招新状态
        Result<RecruitmentStatusEnum> checkResult = checkRecruitmentStatus(id, RecruitmentStatusEnum.ENDED);
        if (checkResult.isFailure()) {
            return Result.fail(checkResult);
        }

        // 更新职位职责
        int count = recruitmentMapper.updatePositionDuty(id, newPositionDuty);
        if (count < 1) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT_UNMODIFIED,
                    "The newPositionDuty can't be same as the oldPositionDuty.");
        }

        // 更新后的招新
        return getRecruitment(id);
    }

    /**
     * 更新职位要求，报名结束后无法更新
     *
     * @permission 必须是招新所属组织所属用户主体本身
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 招新不存在
     *              Forbidden.Unavailable: 招新不可用
     *              OperationConflict.Status: 招新状态不允许
     *              OperationConflict.Unmodified: 新旧职位要求相同
     *
     * @param id 招新的编号
     * @param newPositionRequirement 新职位要求
     * @return 更新结果
     */
    @Override
    public Result<RecruitmentDTO> updatePositionRequirement(Long id, String newPositionRequirement) {
        // 检查招新状态
        Result<RecruitmentStatusEnum> checkResult = checkRecruitmentStatus(id, RecruitmentStatusEnum.ENDED);
        if (checkResult.isFailure()) {
            return Result.fail(checkResult);
        }

        // 更新职位要求
        int count = recruitmentMapper.updatePositionRequirement(id, newPositionRequirement);
        if (count < 1) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT_UNMODIFIED,
                    "The newPositionRequirement can't be same as the oldPositionRequirement.");
        }

        // 更新后的招新
        return getRecruitment(id);
    }

    /**
     * 更新发布时间，招新发布后无法更新
     *
     * @permission 必须是招新所属组织所属用户主体本身
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 招新不存在
     *              Forbidden.Unavailable: 招新不可用
     *              OperationConflict.Status: 招新状态不允许
     *              OperationConflict.Unmodified: 新旧发布时间相同
     *
     * @param id 招新的编号
     * @param newReleaseTime 新发布时间，null 表示立即发布
     * @return 更新结果
     */
    @Override
    public Result<RecruitmentDTO> updateReleaseTime(Long id, LocalDateTime newReleaseTime) {
        // 检查招新状态
        Result<RecruitmentStatusEnum> checkResult = checkRecruitmentStatus(id, RecruitmentStatusEnum.WAITING_START);
        if (checkResult.isFailure()) {
            return Result.fail(checkResult);
        }

        // 如果新发布时间为 null 则设置为当前时间
        if (newReleaseTime == null) {
            newReleaseTime = LocalDateTime.now();
        }

        // 招新发布时间不能大于数据库的上限
        if (newReleaseTime.isAfter(MySqlConstants.DATE_TIME_MAX_VALUE)) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER,
                    "The newReleaseTime must not be greater than 9999-12-31 23:59:59");
        }

        // 更新发布时间
        int count = recruitmentMapper.updateReleaseTime(id, newReleaseTime);
        if (count < 1) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT_UNMODIFIED,
                    "The newReleaseTime can't be same as the oldReleaseTime.");
        }

        // 更新后的招新
        return getRecruitment(id);
    }

    /**
     * 更新报名的开始时间，报名开始后无法更新
     *
     * @permission 必须是招新所属组织所属用户主体本身
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 招新不存在
     *              Forbidden.Unavailable: 招新不可用
     *              OperationConflict.Status: 招新状态不允许
     *              OperationConflict.Unmodified: 新旧报名开始时间相同，或者是新报名开始时间小于发布时间
     *
     * @param id 招新的编号
     * @param newRegistrationTimeFrom 新报名开始时间，null 表示招新发布后立刻开始报名
     * @return 更新结果
     */
    @Override
    public Result<RecruitmentDTO> updateRegistrationTimeFrom(Long id, LocalDateTime newRegistrationTimeFrom) {
        // 检查招新状态
        Result<RecruitmentStatusEnum> checkResult = checkRecruitmentStatus(id, RecruitmentStatusEnum.STARTED);
        if (checkResult.isFailure()) {
            return Result.fail(checkResult);
        }

        // 更新报名开始时间
        // 如果 newRegistrationTimeFrom == null 则更新为 releaseTime
        if (newRegistrationTimeFrom == null) {
            recruitmentMapper.updateRegistrationTimeFromToReleaseTime(id);
        }
        // 否则正常更新
        else {
            // 报名开始时间不能大于数据库的上限
            if (newRegistrationTimeFrom.isAfter(MySqlConstants.DATE_TIME_MAX_VALUE)) {
                return Result.fail(ErrorCodeEnum.INVALID_PARAMETER,
                        "The newRegistrationTimeFrom must not be greater than 9999-12-31 23:59:59");
            }

            // 更新报名开始时间
            int count = recruitmentMapper.updateRegistrationTimeFrom(id, newRegistrationTimeFrom);
            if (count < 1) {
                return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT_UNMODIFIED,
                        "The newRegistrationTimeFrom can't be same as the oldRegistrationTimeFrom, " +
                                "or the newRegistrationTimeFrom is less than the releaseTime.");
            }
        }

        // 更新后的招新
        return getRecruitment(id);
    }

    /**
     * 更新报名截止时间，报名结束后无法更新
     *
     * @permission 必须是招新所属组织所属用户主体本身
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 招新不存在
     *              Forbidden.Unavailable: 招新不可用
     *              OperationConflict.Status: 招新状态不允许
     *              OperationConflict.Unmodified: 新旧报名截止时间相同，或者是新报名截止时间小于或等于报名开始
     *
     * @param id 招新的编号
     * @param newRegistrationTimeTo 新报名截止时间
     * @return 更新结果
     */
    @Override
    public Result<RecruitmentDTO> updateRegistrationTimeTo(Long id, LocalDateTime newRegistrationTimeTo) {
        // 检查招新状态
        Result<RecruitmentStatusEnum> checkResult = checkRecruitmentStatus(id, RecruitmentStatusEnum.ENDED);
        if (checkResult.isFailure()) {
            return Result.fail(checkResult);
        }

        // 报名截止时间不能大于数据库的上限
        if (newRegistrationTimeTo.isAfter(MySqlConstants.DATE_TIME_MAX_VALUE)) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER,
                    "The newRegistrationTimeTo must not be greater than 9999-12-31 23:59:59");
        }

        // 更新报名截止时间
        int count = recruitmentMapper.updateRegistrationTimeTo(id, newRegistrationTimeTo);
        if (count < 1) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT_UNMODIFIED,
                    "The newRegistrationTimeTo can't be same as the oldRegistrationTimeTo, " +
                            "or the newRegistrationTimeTo is less than or equal to the registrationTimeFrom.");
        }

        // 更新后的招新
        return getRecruitment(id);
    }

    /**
     * 结束一个招新的报名，必须是招新当前状态为 STARTED
     *
     * @permission 必须是招新所属组织所属用户主体本身
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 招新不存在
     *              Forbidden.Unavailable: 招新不可用
     *              OperationConflict.Status: 招新状态不允许
     *              OperationConflict.Unmodified: 当前状态不是 STARTED
     *
     * @param id 招新的编号
     * @return 更新结果
     */
    @Override
    public Result<RecruitmentDTO> endRegistration(Long id) {
        // 检查招新状态
        Result<RecruitmentStatusEnum> checkResult = checkRecruitmentStatus(id, RecruitmentStatusEnum.ENDED);
        if (checkResult.isFailure()) {
            return Result.fail(checkResult);
        }

        // 更新当前招新状态为 ENDED
        int count = recruitmentMapper.updateRecruitmentStatus(
                id, RecruitmentStatusEnum.STARTED.name(), RecruitmentStatusEnum.ENDED.name());
        if (count < 1) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT_UNMODIFIED,
                    "The current status must be STARTED.");
        }

        // 更新 registrationTimeTo 为当前时间
        recruitmentMapper.updateRegistrationTimeTo(id, LocalDateTime.now());

        // 获取更新后的招新
        return getRecruitment(id);
    }

    /**
     * 关闭一个招新
     *
     * @permission 必须是招新所属组织所属用户主体本身
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 招新不存在
     *              Forbidden.Unavailable: 招新不可用
     *              OperationConflict.Status: 招新状态不允许
     *              OperationConflict.Unmodified: 当前状态必须不是 CLOSED
     *
     * @param id 招新的编号
     * @return 更新结果
     */
    @Override
    public Result<RecruitmentDTO> closeRecruitment(Long id) {
        // 检查招新状态
        Result<RecruitmentStatusEnum> checkResult = checkRecruitmentStatus(id, RecruitmentStatusEnum.CLOSED);
        if (checkResult.isFailure()) {
            return Result.fail(checkResult);
        }

        // 更新当前招新状态为 CLOSED
        int count = recruitmentMapper.updateRecruitmentStatusWhenNotEqual(id, RecruitmentStatusEnum.CLOSED.name());
        if (count < 1) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT_UNMODIFIED,
                    "The current status already is CLOSED.");
        }

        // 获取更新后的招新
        return getRecruitment(id);
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
     * 获取组织编号
     *
     * @private 内部方法
     *
     * @param id 招新编号
     * @return 组织编号，若招新不存在则返回 null
     */
    @Override
    public Long getOrganizationId(Long id) {
        return recruitmentMapper.getOrganizationId(id);
    }

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
    @Override
    public Result<Void> updateRecruitmentStatus(Long id, RecruitmentStatusEnum oldRecruitmentStatus,
                                                RecruitmentStatusEnum newRecruitmentStatus) {
        int count = recruitmentMapper.updateRecruitmentStatus(
                id, oldRecruitmentStatus.name(), newRecruitmentStatus.name());
        if (count < 1) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT_UNMODIFIED);
        }
        return Result.success();
    }

    /**
     * 检查招新状态
     *
     * @errorCode InvalidParameter.NotExist: 招新不存在
     *              Forbidden.Unavailable: 招新不可用
     *              OperationConflict.Status: 招新状态不允许
     *
     * @param id 招新编号
     * @param followRecruitmentStatus 后续招新状态，当前状态必须小于该状态
     * @return 检查结果
     */
    private Result<RecruitmentStatusEnum> checkRecruitmentStatus(
            Long id, RecruitmentStatusEnum followRecruitmentStatus) {
        // 检查招新存不存在
        RecruitmentDO recruitmentDO = recruitmentMapper.getRecruitment(id);
        if (recruitmentDO == null) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_EXIST,
                    "The recruitment does not exist.");
        }

        // 检查招新是否可用
        if (!recruitmentDO.getAvailable()) {
            return Result.fail(ErrorCodeEnum.FORBIDDEN_UNAVAILABLE, "The recruitment unavailable.");
        }

        // 检查招新状态是否在 followRecruitmentStatus 之前
        RecruitmentStatusEnum recruitmentStatus = RecruitmentStatusEnum.valueOf(recruitmentDO.getRecruitmentStatus());
        if (recruitmentStatus.getCode() >= followRecruitmentStatus.getCode()) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT_STATUS,
                    "The recruitment status must be precede " + followRecruitmentStatus + ".");
        }

        // 通过检查
        return Result.success(recruitmentStatus);
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
