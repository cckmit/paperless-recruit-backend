package com.xiaohuashifu.recruit.registration.service.service;

import com.xiaohuashifu.recruit.common.aspect.annotation.DistributedLock;
import com.xiaohuashifu.recruit.common.result.ErrorCodeEnum;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.external.api.service.ObjectStorageService;
import com.xiaohuashifu.recruit.organization.api.service.DepartmentService;
import com.xiaohuashifu.recruit.registration.api.dto.ApplicationFormDTO;
import com.xiaohuashifu.recruit.registration.api.dto.ApplicationFormTemplateDTO;
import com.xiaohuashifu.recruit.registration.api.po.ApplicationFormAttachmentPO;
import com.xiaohuashifu.recruit.registration.api.po.ApplicationFormAvatarPO;
import com.xiaohuashifu.recruit.registration.api.po.CreateApplicationFormPO;
import com.xiaohuashifu.recruit.registration.api.service.ApplicationFormService;
import com.xiaohuashifu.recruit.registration.api.service.ApplicationFormTemplateService;
import com.xiaohuashifu.recruit.registration.service.dao.ApplicationFormMapper;
import com.xiaohuashifu.recruit.registration.service.do0.ApplicationFormDO;
import com.xiaohuashifu.recruit.user.api.service.CollegeService;
import com.xiaohuashifu.recruit.user.api.service.MajorService;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;

import java.text.MessageFormat;
import java.util.UUID;

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

    private final ApplicationFormMapper applicationFormMapper;

    /**
     * 报名表的 avatar 的路径模式，{0}是招新编号，{1}是文件名
     */
    private static final String APPLICATION_FORM_AVATAR_PATH_PATTERN =
            "recruitment/{0}/application/form/avatar/{1}";

    /**
     * 报名表的 attachment 的路径模式，{0}是招新编号，{1}是用户编号，{2}是文件名
     */
    private static final String APPLICATION_FORM_ATTACHMENT_PATH_PATTERN =
            "recruitment/{0}/application/form/attachment/user/{1}/{2}";

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
     *              InvalidParameter.NotExist: 学院不存在 | 专业不存在 | 部门不存在
     *              Forbidden.Deactivated: 学院被停用 | 专业被停用 | 部门被停用
     *              OperationConflict.Duplicate: 报名表已经存在
     *              OperationConflict.Lock: 获取报名表的锁失败
     *              InternalError: 上传文件失败
     *
     * @return 创建的报名表
     */
    @DistributedLock(value = CREATE_APPLICATION_FORM_LOCK_KEY_PATTERN,
            parameters = {"createApplicationFormPO.userId", "createApplicationFormPO.recruitmentId"},
            errorMessage = "Failed to acquire applicationForm lock.")
    @Override
    public Result<ApplicationFormDTO> createApplicationForm(CreateApplicationFormPO createApplicationFormPO) {
        // 判断是否已经报名了
        int count = applicationFormMapper.countByUserIdAndRecruitmentId(
                createApplicationFormPO.getUserId(), createApplicationFormPO.getRecruitmentId());
        if (count > 0) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT_DUPLICATE,
                    "The applicationForm already exist.");
        }

        // 判断该招新是否可以报名
        Long recruitmentId = createApplicationFormPO.getRecruitmentId();
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
     * CreateApplicationFormPO to ApplicationFormDO
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 学院不存在 | 专业不存在 | 部门不存在
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
        ApplicationFormDO.Builder applicationFormDOBuilder = ApplicationFormDO.builder();
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
        if (applicationFormTemplateDTO.getCollege()) {
            if (createApplicationFormPO.getCollegeId() == null) {
                return Result.fail(ErrorCodeEnum.INVALID_PARAMETER, "The collegeId can't be null.");
            }

            // 判断学院状态
            Result<ApplicationFormDO> checkCollegeStatusResult =
                    collegeService.checkCollegeStatus(createApplicationFormPO.getCollegeId());
            if (checkCollegeStatusResult.isFailure()) {
                return checkCollegeStatusResult;
            }

            applicationFormDOBuilder.collegeId(createApplicationFormPO.getCollegeId());
        }

        // 判断是否需要 major
        if (applicationFormTemplateDTO.getMajor()) {
            if (createApplicationFormPO.getMajorId() == null) {
                return Result.fail(ErrorCodeEnum.INVALID_PARAMETER, "The majorId can't be null.");
            }

            // 判断专业状态
            Result<ApplicationFormDO> checkMajorStatusResult =
                    majorService.checkMajorStatus(createApplicationFormPO.getMajorId());
            if (checkMajorStatusResult.isFailure()) {
                return checkMajorStatusResult;
            }

            applicationFormDOBuilder.majorId(createApplicationFormPO.getMajorId());
        }

        // 判断是否需要 firstDepartment
        if (applicationFormTemplateDTO.getFirstDepartment()) {
            if (createApplicationFormPO.getFirstDepartmentId() == null) {
                return Result.fail(ErrorCodeEnum.INVALID_PARAMETER,
                        "The firstDepartmentId can't be null.");
            }

            // 判断第一部门状态
            Result<ApplicationFormDO> checkDepartmentStatusResult =
                    departmentService.checkDepartmentStatus(createApplicationFormPO.getFirstDepartmentId());
            if (checkDepartmentStatusResult.isFailure()) {
                return checkDepartmentStatusResult;
            }

            applicationFormDOBuilder.firstDepartmentId(createApplicationFormPO.getFirstDepartmentId());
        }

        // 判断是否需要 secondDepartment
        if (applicationFormTemplateDTO.getSecondDepartment()) {
            if (createApplicationFormPO.getSecondDepartmentId() == null) {
                return Result.fail(ErrorCodeEnum.INVALID_PARAMETER,
                        "The secondDepartmentId can't be null.");
            }

            // 判断第二部门状态
            Result<ApplicationFormDO> checkDepartmentStatusResult =
                    departmentService.checkDepartmentStatus(createApplicationFormPO.getSecondDepartmentId());
            if (checkDepartmentStatusResult.isFailure()) {
                return checkDepartmentStatusResult;
            }

            applicationFormDOBuilder.secondDepartmentId(createApplicationFormPO.getSecondDepartmentId());
        }

        // 判断是否需要 avatar
        if (applicationFormTemplateDTO.getAvatar()) {
            if (createApplicationFormPO.getAvatar() == null) {
                return Result.fail(ErrorCodeEnum.INVALID_PARAMETER, "The avatar can't be null.");
            }
        }

        // 判断是否需要 attachment
        if (applicationFormTemplateDTO.getAttachment()) {
            if (createApplicationFormPO.getAttachment() == null) {
                return Result.fail(ErrorCodeEnum.INVALID_PARAMETER, "The attachment can't be null.");
            }
        }

        // 上传头像
        if (applicationFormTemplateDTO.getAvatar()) {
            ApplicationFormAvatarPO applicationFormAvatarPO = createApplicationFormPO.getAvatar();
            String fileName = UUID.randomUUID().toString() + applicationFormAvatarPO.getExtensionName();
            String path = MessageFormat.format(APPLICATION_FORM_AVATAR_PATH_PATTERN,
                    createApplicationFormPO.getRecruitmentId(), fileName);
            Result<ApplicationFormDO> putObjectResult =
                    objectStorageService.putObject(path, applicationFormAvatarPO.getAvatar());
            if (putObjectResult.isFailure()) {
                return putObjectResult;
            }
            applicationFormDOBuilder.avatarUrl(path);
        }

        // 上传附件
        if (applicationFormTemplateDTO.getAttachment()) {
            ApplicationFormAttachmentPO applicationFormAttachmentPO = createApplicationFormPO.getAttachment();
            String path = MessageFormat.format(APPLICATION_FORM_ATTACHMENT_PATH_PATTERN,
                    createApplicationFormPO.getRecruitmentId(), createApplicationFormPO.getUserId(),
                    applicationFormAttachmentPO.getAttachmentName());
            Result<ApplicationFormDO> putObjectResult =
                    objectStorageService.putObject(path, applicationFormAttachmentPO.getAttachment());
            if (putObjectResult.isFailure()) {
                return putObjectResult;
            }
            applicationFormDOBuilder.attachmentUrl(path);
        }

        // 构造成功
        return Result.success(applicationFormDOBuilder
                .userId(createApplicationFormPO.getUserId())
                .recruitmentId(createApplicationFormPO.getRecruitmentId())
                .build());
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
}
