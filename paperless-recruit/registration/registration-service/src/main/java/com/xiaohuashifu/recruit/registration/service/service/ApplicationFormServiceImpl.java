package com.xiaohuashifu.recruit.registration.service.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaohuashifu.recruit.common.aspect.annotation.DistributedLock;
import com.xiaohuashifu.recruit.common.exception.NotFoundServiceException;
import com.xiaohuashifu.recruit.common.exception.unprocessable.DuplicateServiceException;
import com.xiaohuashifu.recruit.common.exception.unprocessable.MisMatchServiceException;
import com.xiaohuashifu.recruit.common.exception.unprocessable.UnavailableServiceException;
import com.xiaohuashifu.recruit.common.exception.unprocessable.UnprocessableServiceException;
import com.xiaohuashifu.recruit.organization.api.dto.DepartmentDTO;
import com.xiaohuashifu.recruit.organization.api.service.DepartmentService;
import com.xiaohuashifu.recruit.oss.api.service.ObjectStorageService;
import com.xiaohuashifu.recruit.registration.api.dto.ApplicationFormDTO;
import com.xiaohuashifu.recruit.registration.api.dto.ApplicationFormTemplateDTO;
import com.xiaohuashifu.recruit.registration.api.dto.RecruitmentDTO;
import com.xiaohuashifu.recruit.registration.api.request.ApplicationFormRequest;
import com.xiaohuashifu.recruit.registration.api.request.CreateApplicationFormRequest;
import com.xiaohuashifu.recruit.registration.api.request.UpdateApplicationFormRequest;
import com.xiaohuashifu.recruit.registration.api.service.ApplicationFormService;
import com.xiaohuashifu.recruit.registration.api.service.ApplicationFormTemplateService;
import com.xiaohuashifu.recruit.registration.api.service.RecruitmentService;
import com.xiaohuashifu.recruit.registration.service.assembler.ApplicationFormAssembler;
import com.xiaohuashifu.recruit.registration.service.dao.ApplicationFormMapper;
import com.xiaohuashifu.recruit.registration.service.do0.ApplicationFormDO;
import com.xiaohuashifu.recruit.user.api.dto.CollegeDTO;
import com.xiaohuashifu.recruit.user.api.dto.MajorDTO;
import com.xiaohuashifu.recruit.user.api.service.CollegeService;
import com.xiaohuashifu.recruit.user.api.service.MajorService;
import com.xiaohuashifu.recruit.user.api.service.UserService;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;

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

    private final ApplicationFormAssembler applicationFormAssembler;

    /**
     * 创建报名表的锁定键模式，{0}是用户编号，{1}是招新编号
     */
    private static final String CREATE_APPLICATION_FORM_LOCK_KEY_PATTERN =
            "application-form:create-application-form:user-id:{0}:recruitment-id:{1}";

    /**
     * 报名表的锁定键模式，{0}是报名表编号
     */
    private static final String APPLICATION_FORM_LOCK_KEY_PATTERN = "application-form:{0}";

    public ApplicationFormServiceImpl(ApplicationFormMapper applicationFormMapper,
                                      ApplicationFormAssembler applicationFormAssembler) {
        this.applicationFormMapper = applicationFormMapper;
        this.applicationFormAssembler = applicationFormAssembler;
    }

    @DistributedLock(value = CREATE_APPLICATION_FORM_LOCK_KEY_PATTERN,
            parameters = {"#{#request.userId}", "#{#request.recruitmentId}"})
    @Override
    public ApplicationFormDTO createApplicationForm(CreateApplicationFormRequest request) {
        // 判断是否已经报名了
        LambdaQueryWrapper<ApplicationFormDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ApplicationFormDO::getRecruitmentId, request.getRecruitmentId())
                .eq(ApplicationFormDO::getUserId, request.getUserId());
        int count = applicationFormMapper.selectCount(wrapper);
        if (count > 0) {
            throw new DuplicateServiceException("The applicationForm already exist.");
        }

        // 判断用户状态
        userService.getUser(request.getUserId());

        // 判断该招新是否可以报名
        ApplicationFormTemplateDTO applicationFormTemplateDTO =
                applicationFormTemplateService.canRegistration(request.getRecruitmentId());

        // 构造插入数据库的数据对象
        ApplicationFormDO.ApplicationFormDOBuilder applicationFormDOBuilder =
                applicationFormRequestToApplicationFormDO(request, applicationFormTemplateDTO);
        ApplicationFormDO applicationFormDOForInsert = applicationFormDOBuilder.userId(request.getUserId())
                .recruitmentId(request.getRecruitmentId()).build();

        // 插入数据库
        applicationFormMapper.insert(applicationFormDOForInsert);
        return getApplicationForm(applicationFormDOForInsert.getId());
    }

    @Override
    public ApplicationFormDTO getApplicationForm(Long id) {
        ApplicationFormDO applicationFormDO = applicationFormMapper.selectById(id);
        if (applicationFormDO == null) {
            throw new NotFoundServiceException("applicationForm", "id", id);
        }
        return applicationFormAssembler.applicationFormDOToApplicationFormDTO(applicationFormDO);
    }

    @DistributedLock(value = APPLICATION_FORM_LOCK_KEY_PATTERN, parameters = "#{#request.id}")
    @Override
    public ApplicationFormDTO updateApplicationForm(UpdateApplicationFormRequest request) {
        // 判断报名表是否存在
        ApplicationFormDTO applicationFormDTO = getApplicationForm(request.getId());

        // 判断该招新是否可以报名
        ApplicationFormTemplateDTO applicationFormTemplateDTO =
                applicationFormTemplateService.canRegistration(applicationFormDTO.getRecruitmentId());

        // 构造更新数据库的数据对象
        ApplicationFormDO.ApplicationFormDOBuilder applicationFormDOBuilder =
                applicationFormRequestToApplicationFormDO(request, applicationFormTemplateDTO);
        ApplicationFormDO applicationFormDOForUpdate = applicationFormDOBuilder.id(request.getId()).build();

        // 更新到数据库
        applicationFormMapper.updateById(applicationFormDOForUpdate);

        return getApplicationForm(request.getId());
    }

    /**
     * 这段代码比较长，但是也不知道这么改
     * ApplicationFormRequest to ApplicationFormDO
     *
     * @param request ApplicationFormRequest
     * @param applicationFormTemplateDTO ApplicationFormTemplateDTO
     * @return ApplicationFormDOBuilder
     */
    private ApplicationFormDO.ApplicationFormDOBuilder applicationFormRequestToApplicationFormDO(
            ApplicationFormRequest request, ApplicationFormTemplateDTO applicationFormTemplateDTO) {
        // 判断是否需要 note
        ApplicationFormDO.ApplicationFormDOBuilder applicationFormDOBuilder = ApplicationFormDO.builder();
        if (applicationFormTemplateDTO.getNote()) {
            if (request.getNote() == null) {
                throw new MisMatchServiceException("The note can't be null.");
            }
            applicationFormDOBuilder.note(request.getNote());
        }

        // 判断是否需要 studentNumber
        if (applicationFormTemplateDTO.getStudentNumber()) {
            if (request.getStudentNumber() == null) {
                throw new MisMatchServiceException("The studentNumber can't be null.");
            }
            applicationFormDOBuilder.studentNumber(request.getStudentNumber());
        }

        // 判断是否需要 introduction
        if (applicationFormTemplateDTO.getIntroduction()) {
            if (request.getIntroduction() == null) {
                throw new MisMatchServiceException("The introduction can't be null.");
            }
            applicationFormDOBuilder.introduction(request.getIntroduction());
        }

        // 判断是否需要 email
        if (applicationFormTemplateDTO.getEmail()) {
            if (request.getEmail() == null) {
                throw new MisMatchServiceException("The email can't be null.");
            }
            applicationFormDOBuilder.email(request.getEmail());
        }

        // 判断是否需要 phone
        if (applicationFormTemplateDTO.getPhone()) {
            if (request.getPhone() == null) {
                throw new MisMatchServiceException("The phone can't be null.");
            }
            applicationFormDOBuilder.phone(request.getPhone());
        }

        // 判断是否需要 fullName
        if (applicationFormTemplateDTO.getFullName()) {
            if (request.getFullName() == null) {
                throw new MisMatchServiceException("The fullName can't be null.");
            }
            applicationFormDOBuilder.fullName(request.getFullName());
        }

        // 判断是否需要 college
        RecruitmentDTO recruitmentDTO = recruitmentService.getRecruitment(applicationFormTemplateDTO.getRecruitmentId());
        if (applicationFormTemplateDTO.getCollege()) {
            // 判断学院状态
            checkCollegeStatus(request.getCollegeId(), recruitmentDTO.getRecruitmentCollegeIds());

            applicationFormDOBuilder.collegeId(request.getCollegeId());
        }

        // 判断是否需要 major
        if (applicationFormTemplateDTO.getMajor()) {
            // 判断专业状态
            checkMajorStatus(request.getMajorId(), recruitmentDTO.getRecruitmentMajorIds());

            applicationFormDOBuilder.majorId(request.getMajorId());
        }

        // 判断是否需要 firstDepartment
        if (applicationFormTemplateDTO.getFirstDepartment()) {
            // 检查部门状态
            checkDepartmentStatus(request.getFirstDepartmentId(), recruitmentDTO.getId(),
                    recruitmentDTO.getRecruitmentDepartmentIds(), "firstDepartment");

            applicationFormDOBuilder.firstDepartmentId(request.getFirstDepartmentId());
        }

        // 判断是否需要 secondDepartment
        if (applicationFormTemplateDTO.getSecondDepartment()) {
            // 检查部门状态
            checkDepartmentStatus(request.getSecondDepartmentId(), recruitmentDTO.getId(),
                    recruitmentDTO.getRecruitmentDepartmentIds(), "secondDepartment");

            applicationFormDOBuilder.secondDepartmentId(request.getSecondDepartmentId());
        }

        // 判断是否需要 avatar，若需要则链接 avatar
        if (applicationFormTemplateDTO.getAvatar()) {
            if (request.getAvatarUrl() == null) {
                throw new MisMatchServiceException("The avatar can't be null.");
            }

            // 链接 avatar
            objectStorageService.linkObject(request.getAvatarUrl());

            applicationFormDOBuilder.avatarUrl(request.getAvatarUrl());
        }

        // 判断是否需要 attachment，若需要则链接 attachment
        if (applicationFormTemplateDTO.getAttachment()) {
            if (request.getAttachmentUrl() == null) {
                throw new MisMatchServiceException("The attachment can't be null.");
            }

            // 链接 attachment
            objectStorageService.linkObject(request.getAttachmentUrl());

            applicationFormDOBuilder.attachmentUrl(request.getAttachmentUrl());
        }

        // 构造成功
        return applicationFormDOBuilder;
    }

    /**
     * 检查部门状态
     *
     * @param departmentId 部门编号
     * @param recruitmentId 招新编号
     * @param recruitmentDepartmentIds 招新部门编号列表
     * @param firstOrSecondDepartment 是第一部门还是第二部门，作为错误提示的一部分
     */
    private void checkDepartmentStatus(Long departmentId, Long recruitmentId, Set<Long> recruitmentDepartmentIds,
                                       String firstOrSecondDepartment) {
        // 判断部门编号是否为 null
        if (departmentId == null) {
            throw new MisMatchServiceException("The " + firstOrSecondDepartment + " can't be null.");
        }

        // 判断招新部门列表是否包含该部门
        if (recruitmentDepartmentIds.size() != 0 && !recruitmentDepartmentIds.contains(departmentId)) {
            throw new MisMatchServiceException(
                    "The " + firstOrSecondDepartment + " does not contain in this recruitment.");
        }

        // 判断该部门是否是该组织的
        RecruitmentDTO recruitmentDTO = recruitmentService.getRecruitment(recruitmentId);
        DepartmentDTO departmentDTO = departmentService.getDepartment(departmentId);
        if (!Objects.equals(recruitmentDTO.getOrganizationId(), departmentDTO.getOrganizationId())) {
            throw new MisMatchServiceException(
                    "The " + firstOrSecondDepartment + " does not belong to this organization.");
        }

        // 判断部门状态
        DepartmentDTO departmentDTO1 = departmentService.getDepartment(departmentId);
        if (departmentDTO1.getDeactivated()) {
            throw new UnprocessableServiceException("Department already deactivated.");
        }
    }

    /**
     * 判断学院状态
     *
     * @param collegeId 学院编号
     * @param recruitmentCollegeIds 招新学院编号列表
     */
    private void checkCollegeStatus(Long collegeId, Set<Long> recruitmentCollegeIds) {
        // 判断学院编号是否为 null
        if (collegeId == null) {
            throw new MisMatchServiceException("The collegeId can't be null.");
        }

        // 判断招新学院列表是否包含该学院
        if ((recruitmentCollegeIds.size() != 0) && (!recruitmentCollegeIds.contains(collegeId))) {
            throw new MisMatchServiceException("The college does not contain in this recruitment.");
        }

        // 判断学院状态
        CollegeDTO collegeDTO = collegeService.getCollege(collegeId);
        if (collegeDTO.getDeactivated()) {
            throw new UnavailableServiceException("The college is deactivated.");
        }
    }

    /**
     * 检查专业状态
     *
     * @param majorId 专业编号
     * @param recruitmentMajorIds 招新专业编号列表
     */
    private void checkMajorStatus(Long majorId, Set<Long> recruitmentMajorIds) {
        // 判断专业编号是否为 null
        if (majorId == null) {
            throw new MisMatchServiceException("The majorId can't be null.");
        }

        // 判断招新专业列表是否包含该专业
        if (recruitmentMajorIds.size() != 0 && !recruitmentMajorIds.contains(majorId)) {
            throw new MisMatchServiceException("The major does not contain in this recruitment.");
        }

        // 判断专业状态
        MajorDTO majorDTO = majorService.getMajor(majorId);
        if (majorDTO.getDeactivated()) {
            throw new UnavailableServiceException("The major is deactivated");
        }
    }

}
