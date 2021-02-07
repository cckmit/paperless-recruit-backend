package com.xiaohuashifu.recruit.registration.service.service;

import com.xiaohuashifu.recruit.common.aspect.annotation.DistributedLock;
import com.xiaohuashifu.recruit.common.exception.unprocessable.UnavailableServiceException;
import com.xiaohuashifu.recruit.common.exception.unprocessable.UnprocessableServiceException;
import com.xiaohuashifu.recruit.common.result.ErrorCodeEnum;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.organization.api.dto.DepartmentDTO;
import com.xiaohuashifu.recruit.organization.api.service.DepartmentService;
import com.xiaohuashifu.recruit.oss.api.response.ObjectInfoResponse;
import com.xiaohuashifu.recruit.oss.api.service.ObjectStorageService;
import com.xiaohuashifu.recruit.registration.api.dto.ApplicationFormDTO;
import com.xiaohuashifu.recruit.registration.api.dto.ApplicationFormTemplateDTO;
import com.xiaohuashifu.recruit.registration.api.dto.RecruitmentDTO;
import com.xiaohuashifu.recruit.registration.api.request.CreateApplicationFormPO;
import com.xiaohuashifu.recruit.registration.api.service.ApplicationFormService;
import com.xiaohuashifu.recruit.registration.api.service.ApplicationFormTemplateService;
import com.xiaohuashifu.recruit.registration.api.service.RecruitmentService;
import com.xiaohuashifu.recruit.registration.service.dao.ApplicationFormMapper;
import com.xiaohuashifu.recruit.registration.service.do0.ApplicationFormDO;
import com.xiaohuashifu.recruit.user.api.dto.CollegeDTO;
import com.xiaohuashifu.recruit.user.api.dto.MajorDTO;
import com.xiaohuashifu.recruit.user.api.service.CollegeService;
import com.xiaohuashifu.recruit.user.api.service.MajorService;
import com.xiaohuashifu.recruit.user.api.service.UserService;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.Set;

/**
 * 描述：报名表服务实现
 *
 * @author xhsf
 * @create 2020/12/29 21:03
 */
@Service
public class ApplicationFormServiceImpl implements ApplicationFormService {

    @Reference
    private ApplicationFormTemplateService applicationFormTemplateService;

    @Reference
    private CollegeService collegeService;

    @Reference
    private MajorService majorService;

    @Reference
    private DepartmentService departmentService;

    @Reference
    private ObjectStorageService objectStorageService;

    @Reference
    private RecruitmentService recruitmentService;

    @Reference
    private UserService userService;

    private final ApplicationFormMapper applicationFormMapper;

    /**
     * 创建报名表的锁定键模式，{0}是用户编号，{1}是招新编号
     */
    private static final String CREATE_APPLICATION_FORM_LOCK_KEY_PATTERN =
            "application-form:create-application-form:user-id:{0}:recruitment-id:{1}";

    public ApplicationFormServiceImpl(ApplicationFormMapper applicationFormMapper) {
        this.applicationFormMapper = applicationFormMapper;
    }

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
    @DistributedLock(value = CREATE_APPLICATION_FORM_LOCK_KEY_PATTERN,
            parameters = {"#{#createApplicationFormPO.userId}", "#{#createApplicationFormPO.recruitmentId}"},
            errorMessage = "Failed to acquire applicationForm lock.")
    @Override
    public Result<ApplicationFormDTO> createApplicationForm(CreateApplicationFormPO createApplicationFormPO) {
        // 判断是否已经报名了
        Long recruitmentId = createApplicationFormPO.getRecruitmentId();
        Long userId = createApplicationFormPO.getUserId();
        int count = applicationFormMapper.countByUserIdAndRecruitmentId(userId, recruitmentId);
        if (count > 0) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT_DUPLICATE,
                    "The applicationForm already exist.");
        }

        // 判断用户状态
        userService.getUser(userId);

        // 判断该招新是否可以报名
        Result<ApplicationFormDTO> canRegistration = applicationFormTemplateService.canRegistration(recruitmentId);
        if (canRegistration.isFailure()) {
            return canRegistration;
        }

        // 获取该招新的报名表模板
        Result<ApplicationFormTemplateDTO> getApplicationFormTemplateResult =
                applicationFormTemplateService.getApplicationFormTemplateByRecruitmentId(recruitmentId);
        ApplicationFormTemplateDTO applicationFormTemplateDTO = getApplicationFormTemplateResult.getData();

        // 构造插入数据库的数据对象
        Result<ApplicationFormDO> createApplicationFormPO2ApplicationFormDOResult =
                createApplicationFormPO2ApplicationFormDO(createApplicationFormPO, applicationFormTemplateDTO);
        if (createApplicationFormPO2ApplicationFormDOResult.isFailure()) {
            return Result.fail(createApplicationFormPO2ApplicationFormDOResult);
        }

        // 插入数据库
        ApplicationFormDO applicationFormDO = createApplicationFormPO2ApplicationFormDOResult.getData();
        applicationFormMapper.insertApplicationForm(applicationFormDO);
        return getApplicationForm(applicationFormDO.getId());
    }

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
    @Override
    public Result<ApplicationFormDTO> getApplicationForm(Long id) {
        // 判断报名表存不存在
        ApplicationFormDO applicationFormDO = applicationFormMapper.getApplicationForm(id);
        if (applicationFormDO == null) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_FOUND,
                    "The applicationForm not found.");
        }

        // 封装成 DTO
        ApplicationFormDTO applicationFormDTO = applicationFormDO2ApplicationFormDTO(applicationFormDO);
        return Result.success(applicationFormDTO);
    }

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
    @Override
    public Result<ApplicationFormDTO> updateAvatar(Long id, String avatarUrl) {
        // 检查是否可以更新
        Result<Long> checkResult = checkForUpdate(id, "avatar");
        if (checkResult.isFailure()) {
            return Result.fail(checkResult);
        }

        // 链接 avatar
        Result<ObjectInfoResponse> linkResult = objectStorageService.linkObject(avatarUrl);
        if (linkResult.isFailure()) {
            return Result.fail(linkResult);
        }

        // 更新 avatarUrl 到数据
        applicationFormMapper.updateAvatarUrl(id, avatarUrl);

        // 获取更新后的报名表
        return getApplicationForm(id);
    }

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
    @Override
    public Result<ApplicationFormDTO> updateFullName(Long id, String fullName) {
        // 检查是否可以更新
        Result<Long> checkResult = checkForUpdate(id, "fullName");
        if (checkResult.isFailure()) {
            return Result.fail(checkResult);
        }

        // 更新姓名
        applicationFormMapper.updateFullName(id, fullName);

        // 获取更新后的报名表
        return getApplicationForm(id);
    }

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
    @Override
    public Result<ApplicationFormDTO> updatePhone(Long id, String phone) {
        // 检查是否可以更新
        Result<Long> checkResult = checkForUpdate(id, "phone");
        if (checkResult.isFailure()) {
            return Result.fail(checkResult);
        }

        // 更新手机
        applicationFormMapper.updatePhone(id, phone);

        // 获取更新后的报名表
        return getApplicationForm(id);
    }

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
    @Override
    public Result<ApplicationFormDTO> updateFirstDepartment(Long id, Long firstDepartmentId) {
        // 检查是否可以更新
        Result<Long> checkResult = checkForUpdate(id, "firstDepartment");
        if (checkResult.isFailure()) {
            return Result.fail(checkResult);
        }

        // 判断招新部门列表是否包含该部门
        Long recruitmentId = checkResult.getData();
        Result<RecruitmentDTO> getRecruitmentResult = recruitmentService.getRecruitment(recruitmentId);
        RecruitmentDTO recruitmentDTO = getRecruitmentResult.getData();
        Result<ApplicationFormDTO> checkDepartmentResult = checkDepartmentStatus(firstDepartmentId, recruitmentId,
                recruitmentDTO.getRecruitmentDepartmentIds(), "firstDepartment");
        if (checkDepartmentResult.isFailure()) {
            return checkDepartmentResult;
        }

        // 更新第一部门
        applicationFormMapper.updateFirstDepartmentId(id, firstDepartmentId);

        // 获取更新后的报名表
        return getApplicationForm(id);
    }

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
    @Override
    public Result<ApplicationFormDTO> updateSecondDepartment(Long id, Long secondDepartmentId) {
        // 检查是否可以更新
        Result<Long> checkResult = checkForUpdate(id, "secondDepartment");
        if (checkResult.isFailure()) {
            return Result.fail(checkResult);
        }

        // 判断招新部门列表是否包含该部门
        Long recruitmentId = checkResult.getData();
        Result<RecruitmentDTO> getRecruitmentResult = recruitmentService.getRecruitment(recruitmentId);
        RecruitmentDTO recruitmentDTO = getRecruitmentResult.getData();
        Result<ApplicationFormDTO> checkDepartmentResult = checkDepartmentStatus(secondDepartmentId, recruitmentId,
                recruitmentDTO.getRecruitmentDepartmentIds(), "secondDepartment");
        if (checkDepartmentResult.isFailure()) {
            return checkDepartmentResult;
        }

        // 更新第二部门
        applicationFormMapper.updateSecondDepartmentId(id, secondDepartmentId);

        // 获取更新后的报名表
        return getApplicationForm(id);
    }

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
    @Override
    public Result<ApplicationFormDTO> updateEmail(Long id, String email) {
        // 检查是否可以更新
        Result<Long> checkResult = checkForUpdate(id, "email");
        if (checkResult.isFailure()) {
            return Result.fail(checkResult);
        }

        // 更新邮箱
        applicationFormMapper.updateEmail(id, email);

        // 获取更新后的报名表
        return getApplicationForm(id);
    }

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
    @Override
    public Result<ApplicationFormDTO> updateIntroduction(Long id, String introduction) {
        // 检查是否可以更新
        Result<Long> checkResult = checkForUpdate(id, "introduction");
        if (checkResult.isFailure()) {
            return Result.fail(checkResult);
        }

        // 更新个人简介
        applicationFormMapper.updateIntroduction(id, introduction);

        // 获取更新后的报名表
        return getApplicationForm(id);
    }

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
    @Override
    public Result<ApplicationFormDTO> updateAttachment(Long id, String attachmentUrl) {
        // 检查是否可以更新
        Result<Long> checkResult = checkForUpdate(id, "attachment");
        if (checkResult.isFailure()) {
            return Result.fail(checkResult);
        }

        // 链接 attachment
        Result<ObjectInfoResponse> linkResult = objectStorageService.linkObject(attachmentUrl);
        if (linkResult.isFailure()) {
            return Result.fail(linkResult);
        }

        // 更新附件 url 到数据库
        applicationFormMapper.updateAttachmentUrl(id, attachmentUrl);

        // 获取更新后的报名表
        return getApplicationForm(id);
    }

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
    @Override
    public Result<ApplicationFormDTO> updateStudentNumber(Long id, String studentNumber) {
        // 检查是否可以更新
        Result<Long> checkResult = checkForUpdate(id, "studentNumber");
        if (checkResult.isFailure()) {
            return Result.fail(checkResult);
        }

        // 更新学号
        applicationFormMapper.updateStudentNumber(id, studentNumber);

        // 获取更新后的报名表
        return getApplicationForm(id);
    }

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
    @Override
    public Result<ApplicationFormDTO> updateCollege(Long id, Long collegeId) {
        // 检查是否可以更新
        Result<Long> checkResult = checkForUpdate(id, "college");
        if (checkResult.isFailure()) {
            return Result.fail(checkResult);
        }

        // 检查学院状态
        Long recruitmentId = checkResult.getData();
        Result<RecruitmentDTO> getRecruitmentResult = recruitmentService.getRecruitment(recruitmentId);
        RecruitmentDTO recruitmentDTO = getRecruitmentResult.getData();
        Result<ApplicationFormDTO> checkCollegeStatusResult =
                checkCollegeStatus(collegeId, recruitmentDTO.getRecruitmentCollegeIds());
        if (checkCollegeStatusResult.isFailure()) {
            return checkCollegeStatusResult;
        }

        // 更新学院
        applicationFormMapper.updateCollegeId(id, collegeId);

        // 获取更新后的报名表
        return getApplicationForm(id);
    }

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
    @Override
    public Result<ApplicationFormDTO> updateMajor(Long id, Long majorId) {
        // 检查是否可以更新
        Result<Long> checkResult = checkForUpdate(id, "major");
        if (checkResult.isFailure()) {
            return Result.fail(checkResult);
        }

        // 判断状态状态
        Long recruitmentId = checkResult.getData();
        Result<RecruitmentDTO> getRecruitmentResult = recruitmentService.getRecruitment(recruitmentId);
        RecruitmentDTO recruitmentDTO = getRecruitmentResult.getData();
        Result<ApplicationFormDTO> checkMajorStatusResult =
                checkMajorStatus(majorId, recruitmentDTO.getRecruitmentMajorIds());
        if (checkMajorStatusResult.isFailure()) {
            return checkMajorStatusResult;
        }

        // 更新专业
        applicationFormMapper.updateMajor(id, majorId);

        // 获取更新后的报名表
        return getApplicationForm(id);
    }

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
    @Override
    public Result<ApplicationFormDTO> updateNote(Long id, String note) {
        // 检查是否可以更新
        Result<Long> checkResult = checkForUpdate(id, "note");
        if (checkResult.isFailure()) {
            return Result.fail(checkResult);
        }

        // 更新备注
        applicationFormMapper.updateNote(id, note);

        // 获取更新后的报名表
        return getApplicationForm(id);
    }

    /**
     * 获取报名表所属招新的编号
     *
     * @private 内部方法
     *
     * @param id 报名表编号
     * @return 招新编号，若找不到可能返回 null
     */
    @Override
    public Long getRecruitmentId(Long id) {
        return applicationFormMapper.getRecruitmentId(id);
    }

    /**
     * 获取报名表所属用户的编号
     *
     * @private 内部方法
     *
     * @param id 报名表编号
     * @return 用户编号，若找不到可能返回 null
     */
    @Override
    public Long getUserId(Long id) {
        return applicationFormMapper.getUserId(id);
    }

    /**
     * 检查是否可以更新
     *
     * @errorCode InvalidParameter.NotExist: 报名表不存在
     *              InvalidParameter.NotRequired: 不需要该字段
     *              Forbidden.Unavailable: 招新不可用 | 组织不可用
     *              Forbidden.Deactivated: 报名表模板被停用
     *              OperationConflict.Status: 招新的状态必须是 STARTED
     *
     * @param id 报名表编号
     * @return 检查结果，通过检查返回招新编号
     */
    private Result<Long> checkForUpdate(Long id, String fieldName) {
        // 判断报名表是否存在
        Long recruitmentId = applicationFormMapper.getRecruitmentId(id);
        if (recruitmentId == null) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_EXIST,
                    "The applicationForm does not exist.");
        }

        // 判断是否可以报名
        Result<Long> canRegistration = applicationFormTemplateService.canRegistration(recruitmentId);
        if (canRegistration.isFailure()) {
            return canRegistration;
        }

        // 检查该字段是否是需要的
        Result<ApplicationFormTemplateDTO> getApplicationFormTemplateResult =
                applicationFormTemplateService.getApplicationFormTemplateByRecruitmentId(recruitmentId);
        ApplicationFormTemplateDTO applicationFormTemplateDTO = getApplicationFormTemplateResult.getData();
        try {
            Field field = ApplicationFormTemplateDTO.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            Boolean required = (Boolean) field.get(applicationFormTemplateDTO);
            if (!required) {
                return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_REQUIRED,
                        "The " + fieldName + " is not required.");
            }
        } catch (NoSuchFieldException | IllegalAccessException ignored) {}

        // 通过检查
        return Result.success(recruitmentId);
    }

    /**
     * ApplicationFormDO to ApplicationFormDTO
     *
     * @param applicationFormDO ApplicationFormDO
     * @return ApplicationFormDTO
     */
    private ApplicationFormDTO applicationFormDO2ApplicationFormDTO(ApplicationFormDO applicationFormDO) {
        return ApplicationFormDTO.builder()
                .id(applicationFormDO.getId())
                .userId(applicationFormDO.getUserId())
                .recruitmentId(applicationFormDO.getRecruitmentId())
                .avatarUrl(applicationFormDO.getAvatarUrl())
                .fullName(applicationFormDO.getFullName())
                .phone(applicationFormDO.getPhone())
                .firstDepartmentId(applicationFormDO.getFirstDepartmentId())
                .secondDepartmentId(applicationFormDO.getSecondDepartmentId())
                .email(applicationFormDO.getEmail())
                .introduction(applicationFormDO.getIntroduction())
                .attachmentUrl(applicationFormDO.getAttachmentUrl())
                .studentNumber(applicationFormDO.getStudentNumber())
                .collegeId(applicationFormDO.getCollegeId())
                .majorId(applicationFormDO.getMajorId())
                .note(applicationFormDO.getNote())
                .build();
    }

    /**
     * 这段代码比较长，但是也不知道这么改
     * CreateApplicationFormPO to ApplicationFormDO
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 学院不存在 | 专业不存在
     *              InvalidParameter.NotContain: 学院不被包含 | 专业不被包含 | 部门不被包含
     *              InvalidParameter.Mismatch: 组织不包含该部门
     *              Forbidden.Deactivated: 学院被停用 | 专业被停用 | 部门被停用
     *              InternalError: 上传文件失败
     *
     * @param createApplicationFormPO CreateApplicationFormPO
     * @param applicationFormTemplateDTO ApplicationFormTemplateDTO
     * @return ApplicationFormDO
     */
    private Result<ApplicationFormDO> createApplicationFormPO2ApplicationFormDO(
            CreateApplicationFormPO createApplicationFormPO, ApplicationFormTemplateDTO applicationFormTemplateDTO) {
        // 判断是否需要 note
        ApplicationFormDO.ApplicationFormDOBuilder<?, ?> applicationFormDOBuilder = ApplicationFormDO.builder();
        if (applicationFormTemplateDTO.getNote()) {
            if (createApplicationFormPO.getNote() == null) {
                return Result.fail(ErrorCodeEnum.INVALID_PARAMETER, "The note can't be null.");
            }
            applicationFormDOBuilder.note(createApplicationFormPO.getNote());
        }

        // 判断是否需要 studentNumber
        if (applicationFormTemplateDTO.getStudentNumber()) {
            if (createApplicationFormPO.getStudentNumber() == null) {
                return Result.fail(ErrorCodeEnum.INVALID_PARAMETER, "The studentNumber can't be null.");
            }
            applicationFormDOBuilder.studentNumber(createApplicationFormPO.getStudentNumber());
        }

        // 判断是否需要 introduction
        if (applicationFormTemplateDTO.getIntroduction()) {
            if (createApplicationFormPO.getIntroduction() == null) {
                return Result.fail(ErrorCodeEnum.INVALID_PARAMETER, "The introduction can't be null.");
            }
            applicationFormDOBuilder.introduction(createApplicationFormPO.getIntroduction());
        }

        // 判断是否需要 email
        if (applicationFormTemplateDTO.getEmail()) {
            if (createApplicationFormPO.getEmail() == null) {
                return Result.fail(ErrorCodeEnum.INVALID_PARAMETER, "The email can't be null.");
            }
            applicationFormDOBuilder.email(createApplicationFormPO.getEmail());
        }

        // 判断是否需要 phone
        if (applicationFormTemplateDTO.getPhone()) {
            if (createApplicationFormPO.getPhone() == null) {
                return Result.fail(ErrorCodeEnum.INVALID_PARAMETER, "The phone can't be null.");
            }
            applicationFormDOBuilder.phone(createApplicationFormPO.getPhone());
        }

        // 判断是否需要 fullName
        if (applicationFormTemplateDTO.getFullName()) {
            if (createApplicationFormPO.getFullName() == null) {
                return Result.fail(ErrorCodeEnum.INVALID_PARAMETER, "The fullName can't be null.");
            }
            applicationFormDOBuilder.fullName(createApplicationFormPO.getFullName());
        }

        // 判断是否需要 college
        Long recruitmentId = createApplicationFormPO.getRecruitmentId();
        Result<RecruitmentDTO> recruitment = recruitmentService.getRecruitment(recruitmentId);
        RecruitmentDTO recruitmentDTO = recruitment.getData();
        if (applicationFormTemplateDTO.getCollege()) {
            // 判断学院状态
            Long collegeId = createApplicationFormPO.getCollegeId();
            Result<ApplicationFormDO> checkCollegeStatusResult =
                    checkCollegeStatus(collegeId, recruitmentDTO.getRecruitmentCollegeIds());
            if (checkCollegeStatusResult.isFailure()) {
                return checkCollegeStatusResult;
            }

            applicationFormDOBuilder.collegeId(collegeId);
        }

        // 判断是否需要 major
        if (applicationFormTemplateDTO.getMajor()) {
            // 判断专业状态
            Long majorId = createApplicationFormPO.getMajorId();
            Result<ApplicationFormDO> checkMajorStatusResult =
                    checkMajorStatus(majorId, recruitmentDTO.getRecruitmentMajorIds());
            if (checkMajorStatusResult.isFailure()) {
                return checkMajorStatusResult;
            }

            applicationFormDOBuilder.majorId(majorId);
        }

        // 判断是否需要 firstDepartment
        if (applicationFormTemplateDTO.getFirstDepartment()) {
            // 检查部门状态
            Long firstDepartmentId = createApplicationFormPO.getFirstDepartmentId();
            Result<ApplicationFormDO> checkResult = checkDepartmentStatus(firstDepartmentId, recruitmentId,
                    recruitmentDTO.getRecruitmentDepartmentIds(), "firstDepartment");
            if (checkResult.isFailure()) {
                return checkResult;
            }

            applicationFormDOBuilder.firstDepartmentId(firstDepartmentId);
        }

        // 判断是否需要 secondDepartment
        if (applicationFormTemplateDTO.getSecondDepartment()) {
            // 检查部门状态
            Long secondDepartmentId = createApplicationFormPO.getSecondDepartmentId();
            Result<ApplicationFormDO> checkResult = checkDepartmentStatus(secondDepartmentId, recruitmentId,
                    recruitmentDTO.getRecruitmentDepartmentIds(), "secondDepartment");
            if (checkResult.isFailure()) {
                return checkResult;
            }

            applicationFormDOBuilder.secondDepartmentId(secondDepartmentId);
        }

        // 判断是否需要 avatar，若需要则链接 avatar
        if (applicationFormTemplateDTO.getAvatar()) {
            if (createApplicationFormPO.getAvatarUrl() == null) {
                return Result.fail(ErrorCodeEnum.INVALID_PARAMETER, "The avatar can't be null.");
            }

            // 链接 avatar
            Result<ObjectInfoResponse> linkResult =
                    objectStorageService.linkObject(createApplicationFormPO.getAvatarUrl());
            if (linkResult.isFailure()) {
                return Result.fail(linkResult);
            }

            applicationFormDOBuilder.avatarUrl(createApplicationFormPO.getAvatarUrl());
        }

        // 判断是否需要 attachment，若需要则链接 attachment
        if (applicationFormTemplateDTO.getAttachment()) {
            if (createApplicationFormPO.getAttachmentUrl() == null) {
                return Result.fail(ErrorCodeEnum.INVALID_PARAMETER, "The attachment can't be null.");
            }

            // 链接 attachment
            Result<ObjectInfoResponse> linkResult =
                    objectStorageService.linkObject(createApplicationFormPO.getAttachmentUrl());
            if (linkResult.isFailure()) {
                return Result.fail(linkResult);
            }

            applicationFormDOBuilder.attachmentUrl(createApplicationFormPO.getAttachmentUrl());
        }

        // 构造成功
        return Result.success(applicationFormDOBuilder
                .userId(createApplicationFormPO.getUserId())
                .recruitmentId(createApplicationFormPO.getRecruitmentId())
                .build());
    }

    /**
     * 检查部门状态
     *
     * @errorCode InvalidParameter: 部门编号为 null
     *              InvalidParameter.NotContain: 部门不被包含在此招新
     *              InvalidParameter.Mismatch: 组织不包含该部门
     *              Forbidden.Deactivated: 部门被停用
     *
     * @param departmentId 部门编号
     * @param recruitmentId 招新编号
     * @param recruitmentDepartmentIds 招新部门编号列表
     * @param firstOrSecondDepartment 是第一部门还是第二部门，作为错误提示的一部分
     * @return 检查结果
     */
    private <T> Result<T> checkDepartmentStatus(Long departmentId, Long recruitmentId,
                                                Set<Long> recruitmentDepartmentIds, String firstOrSecondDepartment) {
        // 判断部门编号是否为 null
        if (departmentId == null) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER,
                    "The " + firstOrSecondDepartment + " can't be null.");
        }

        // 判断招新部门列表是否包含该部门
        if (recruitmentDepartmentIds.size() != 0 && !recruitmentDepartmentIds.contains(departmentId)) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_CONTAIN,
                    "The " + firstOrSecondDepartment + " does not contain in this recruitment.");
        }

        // 判断该部门是否是该组织的
        Long organizationIdByRecruitmentId = recruitmentService.getOrganizationId(recruitmentId);
        DepartmentDTO departmentDTO = departmentService.getDepartment(departmentId);
        if (!Objects.equals(organizationIdByRecruitmentId, departmentDTO.getOrganizationId())) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_MISMATCH,
                    "The " + firstOrSecondDepartment + " does not belong to this organization.");
        }

        // 判断部门状态
        DepartmentDTO departmentDTO1 = departmentService.getDepartment(departmentId);
        if (departmentDTO1.getDeactivated()) {
            throw new UnprocessableServiceException("Department already deactivated.");
        }

        // 通过判断
        return Result.success();
    }

    /**
     * 判断学院状态
     *
     * @param collegeId 学院编号
     * @param recruitmentCollegeIds 招新学院编号列表
     * @return 检查结果
     */
    private <T> Result<T> checkCollegeStatus(Long collegeId, Set<Long> recruitmentCollegeIds) {
        // 判断学院编号是否为 null
        if (collegeId == null) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER, "The collegeId can't be null.");
        }

        // 判断招新学院列表是否包含该学院
        if ((recruitmentCollegeIds.size() != 0) && (!recruitmentCollegeIds.contains(collegeId))) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_CONTAIN,
                    "The college does not contain in this recruitment.");
        }

        // 判断学院状态
        CollegeDTO collegeDTO = collegeService.getCollege(collegeId);
        if (collegeDTO.getDeactivated()) {
            throw new UnavailableServiceException("The college is deactivated.");
        }

        // 通过检查
        return Result.success();
    }

    /**
     * 检查专业状态
     *
     * @param majorId 专业编号
     * @param recruitmentMajorIds 招新专业编号列表
     * @return 检查结果
     */
    private <T> Result<T> checkMajorStatus(Long majorId, Set<Long> recruitmentMajorIds) {
        // 判断专业编号是否为 null
        if (majorId == null) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER, "The majorId can't be null.");
        }

        // 判断招新专业列表是否包含该专业
        if (recruitmentMajorIds.size() != 0 && !recruitmentMajorIds.contains(majorId)) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_CONTAIN,
                    "The major does not contain in this recruitment.");
        }

        // 判断专业状态
        MajorDTO majorDTO = majorService.getMajor(majorId);
        if (majorDTO.getDeactivated()) {
            throw new UnavailableServiceException("The major is deactivated");
        }

        // 通过检查
        return Result.success();
    }
}
