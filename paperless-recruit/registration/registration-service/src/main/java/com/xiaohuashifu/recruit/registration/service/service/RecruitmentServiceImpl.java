package com.xiaohuashifu.recruit.registration.service.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.xiaohuashifu.recruit.common.aspect.annotation.DistributedLock;
import com.xiaohuashifu.recruit.common.constant.GradeEnum;
import com.xiaohuashifu.recruit.common.constant.MySqlConstants;
import com.xiaohuashifu.recruit.common.exception.NotFoundServiceException;
import com.xiaohuashifu.recruit.common.exception.ServiceException;
import com.xiaohuashifu.recruit.common.exception.unprocessable.*;
import com.xiaohuashifu.recruit.organization.api.dto.DepartmentDTO;
import com.xiaohuashifu.recruit.organization.api.service.DepartmentService;
import com.xiaohuashifu.recruit.organization.api.service.OrganizationService;
import com.xiaohuashifu.recruit.registration.api.constant.RecruitmentConstants;
import com.xiaohuashifu.recruit.registration.api.constant.RecruitmentStatusEnum;
import com.xiaohuashifu.recruit.registration.api.dto.RecruitmentDTO;
import com.xiaohuashifu.recruit.registration.api.request.CreateRecruitmentRequest;
import com.xiaohuashifu.recruit.registration.api.request.UpdateRecruitmentRequest;
import com.xiaohuashifu.recruit.registration.api.service.ApplicationFormTemplateService;
import com.xiaohuashifu.recruit.registration.api.service.RecruitmentService;
import com.xiaohuashifu.recruit.registration.service.assembler.RecruitmentAssembler;
import com.xiaohuashifu.recruit.registration.service.dao.RecruitmentMapper;
import com.xiaohuashifu.recruit.registration.service.do0.RecruitmentDO;
import com.xiaohuashifu.recruit.user.api.dto.CollegeDTO;
import com.xiaohuashifu.recruit.user.api.dto.MajorDTO;
import com.xiaohuashifu.recruit.user.api.service.CollegeService;
import com.xiaohuashifu.recruit.user.api.service.MajorService;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.HashSet;
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

    @Reference
    private ApplicationFormTemplateService applicationFormTemplateService;

    private final RecruitmentMapper recruitmentMapper;

    private final RecruitmentAssembler recruitmentAssembler;

    /**
     * 招新学院编号列表锁定键模式，{0}是招新编号
     */
    private static final String RECRUITMENT_COLLEGE_IDS_LOCK_KEY_PATTERN = "recruitment:{0}:recruitment-college-ids";

    /**
     * 招新专业编号列表锁定键模式，{0}是招新编号
     */
    private static final String RECRUITMENT_MAJOR_IDS_LOCK_KEY_PATTERN = "recruitment:{0}:recruitment-major-ids";

    /**
     * 招新锁定键模式，{0}是招新编号
     */
    private static final String RECRUITMENT_LOCK_KEY_PATTERN = "recruitment:{0}";

    /**
     * 更新招新状态的初始延迟
     */
    private static final long UPDATE_RECRUITMENT_STATUS_INITIAL_DELAY = 10000;

    /**
     * 更新招新状态的固定延迟
     */
    private static final long UPDATE_RECRUITMENT_STATUS_FIXED_DELAY = 30000;

    public RecruitmentServiceImpl(RecruitmentMapper recruitmentMapper, RecruitmentAssembler recruitmentAssembler) {
        this.recruitmentMapper = recruitmentMapper;
        this.recruitmentAssembler = recruitmentAssembler;
    }

    @Override
    public RecruitmentDTO createRecruitment(CreateRecruitmentRequest request) {
        // 检查参数
        RecruitmentDO recruitmentDOForInsert = checkForCreateRecruitment(request);

        // 插入招新
        recruitmentMapper.insert(recruitmentDOForInsert);
        return getRecruitment(recruitmentDOForInsert.getId());
    }

    @DistributedLock(value = RECRUITMENT_COLLEGE_IDS_LOCK_KEY_PATTERN, parameters = "#{#id}",
            errorMessage = "Failed to acquire recruitmentCollegeIds lock.")
    @Override
    public RecruitmentDTO addRecruitmentCollege(Long id, Long collegeId) {
        // 检查招新状态
        RecruitmentDTO recruitmentDTO = checkRecruitmentStatus(id, RecruitmentStatusEnum.ENDED);

        // 如果学院编号为0，则直接清空招新学院
        if (Objects.equals(0L, collegeId)) {
            RecruitmentDO recruitmentDOForUpdate = RecruitmentDO.builder().id(id)
                    .recruitmentCollegeIds(new HashSet<>()).build();
            recruitmentMapper.updateById(recruitmentDOForUpdate);
        }
        // 否则走正常的添加招新学院流程
        else {
            // 判断学院状态
            CollegeDTO collegeDTO = collegeService.getCollege(collegeId);
            if (collegeDTO.getDeactivated()) {
                throw new UnavailableServiceException("The college is deactivated.");
            }

            // 判断招新学院数量是否超过限制
            if (recruitmentDTO.getRecruitmentCollegeIds().size()
                    >= RecruitmentConstants.MAX_RECRUITMENT_COLLEGE_NUMBERS) {
                throw new OverLimitServiceException("The number of recruitment colleges can't greater than "
                        + RecruitmentConstants.MAX_RECRUITMENT_COLLEGE_NUMBERS + ".");
            }

            // 添加招新学院
            int count = recruitmentMapper.addRecruitmentCollege(id, collegeId);

            // 添加失败表示该学院已经存在
            if (count < 1) {
                throw new DuplicateServiceException("The college already exist.");
            }
        }

        // 添加成功
        return getRecruitment(id);
    }

    @DistributedLock(value = RECRUITMENT_MAJOR_IDS_LOCK_KEY_PATTERN, parameters = "#{#id}",
            errorMessage = "Failed to acquire recruitmentMajorIds lock.")
    @Override
    public RecruitmentDTO addRecruitmentMajor(Long id, Long majorId) {
        // 检查招新状态
        RecruitmentDTO recruitmentDTO = checkRecruitmentStatus(id, RecruitmentStatusEnum.ENDED);

        // 如果专业编号为0，则直接清空招新专业
        if (Objects.equals(0L, majorId)) {
            RecruitmentDO recruitmentDOForUpdate = RecruitmentDO.builder().id(id)
                    .recruitmentMajorIds(new HashSet<>()).build();
            recruitmentMapper.updateById(recruitmentDOForUpdate);
        }
        // 否则走正常的添加招新专业流程
        else {
            // 检查专业状态
            MajorDTO majorDTO = majorService.getMajor(majorId);
            if (majorDTO.getDeactivated()) {
                throw new UnavailableServiceException("The major is deactivated");
            }

            // 判断招新专业数量是否超过限制
            if (recruitmentDTO.getRecruitmentMajorIds().size() >= RecruitmentConstants.MAX_RECRUITMENT_MAJOR_NUMBERS) {
                throw new OverLimitServiceException("The number of recruitment majors can't greater than "
                        + RecruitmentConstants.MAX_RECRUITMENT_MAJOR_NUMBERS + ".");
            }

            // 添加招新专业
            int count = recruitmentMapper.addRecruitmentMajor(id, majorId);

            // 添加失败表示该专业已经存在
            if (count < 1) {
                throw new DuplicateServiceException("The major already exist.");
            }
        }

        // 添加成功
        return getRecruitment(id);
    }

    @Override
    public RecruitmentDTO addRecruitmentGrade(Long id, GradeEnum recruitmentGrade) {
        // 检查招新状态
        checkRecruitmentStatus(id, RecruitmentStatusEnum.ENDED);

        // 如果招新年级为 null，则直接清空招新年级
        if (recruitmentGrade == null) {
            RecruitmentDO recruitmentDOForUpdate = RecruitmentDO.builder().id(id)
                    .recruitmentGrades(new HashSet<>()).build();
            recruitmentMapper.updateById(recruitmentDOForUpdate);
        }
        // 否则走正常的添加招新年级流程
        else {
            // 添加招新年级
            int count = recruitmentMapper.addRecruitmentGrade(id, recruitmentGrade.name());

            // 添加失败表示该年级已经存在
            if (count < 1) {
                throw new DuplicateServiceException("The grade already exist.");
            }
        }

        // 添加成功
        return getRecruitment(id);
    }

    @Override
    public RecruitmentDTO addRecruitmentDepartment(Long id, Long departmentId) {
        // 检查招新状态
        RecruitmentDTO recruitmentDTO = checkRecruitmentStatus(id, RecruitmentStatusEnum.ENDED);

        // 如果部门编号为0，则直接清空招新部门
        if (Objects.equals(0L, departmentId)) {
            RecruitmentDO recruitmentDOForUpdate = RecruitmentDO.builder().id(id)
                    .recruitmentDepartmentIds(new HashSet<>()).build();
            recruitmentMapper.updateById(recruitmentDOForUpdate);
        }
        // 否则走正常的添加招新部门流程
        else {
            // 判断部门是否属于该组织的
            DepartmentDTO departmentDTO = departmentService.getDepartment(departmentId);
            if (!Objects.equals(departmentDTO.getOrganizationId(), recruitmentDTO.getOrganizationId())) {
                throw new MisMatchServiceException("该部门不属于该组织的");
            }

            // 检查部门状态
            DepartmentDTO departmentDTO1 = departmentService.getDepartment(departmentId);
            if (departmentDTO1.getDeactivated()) {
                throw new UnprocessableServiceException("Department already deactivated.");
            }

            // 添加招新部门
            int count = recruitmentMapper.addRecruitmentDepartment(id, departmentId);

            // 添加失败表示该部门已经存在
            if (count < 1) {
                throw new DuplicateServiceException("The department already exist.");
            }
        }

        // 添加成功
        return getRecruitment(id);
    }

    @Override
    public RecruitmentDTO removeRecruitmentCollege(Long id, Long collegeId) {
        // 检查招新状态
        checkRecruitmentStatus(id, RecruitmentStatusEnum.ENDED);

        // 移除招新学院
        recruitmentMapper.removeRecruitmentCollege(id, collegeId);

        // 更新后的招新
        return getRecruitment(id);
    }

    @Override
    public RecruitmentDTO removeRecruitmentMajor(Long id, Long majorId) {
        // 检查招新状态
        checkRecruitmentStatus(id, RecruitmentStatusEnum.ENDED);

        // 移除招新专业
        recruitmentMapper.removeRecruitmentMajor(id, majorId);

        // 更新后的招新
        return getRecruitment(id);
    }

    @Override
    public RecruitmentDTO removeRecruitmentGrade(Long id, GradeEnum recruitmentGrade) {
        // 检查招新状态
        checkRecruitmentStatus(id, RecruitmentStatusEnum.ENDED);

        // 移除招新年级
        recruitmentMapper.removeRecruitmentGrade(id, recruitmentGrade.name());

        // 更新后的招新
        return getRecruitment(id);
    }

    @Override
    public RecruitmentDTO removeRecruitmentDepartment(Long id, Long departmentId) {
        // 检查招新状态
        checkRecruitmentStatus(id, RecruitmentStatusEnum.ENDED);


        // 移除招新部门
        recruitmentMapper.removeRecruitmentDepartment(id, departmentId);

        // 更新后的招新
        return getRecruitment(id);
    }

    @Override
    public RecruitmentDTO getRecruitment(Long id) {
        RecruitmentDO recruitmentDO = recruitmentMapper.selectById(id);
        if (recruitmentDO == null) {
            throw new NotFoundServiceException("recruitment", "id", id);
        }
        return recruitmentAssembler.recruitmentDOToRecruitmentDTO(recruitmentDO);
    }

    @Override
    @DistributedLock(value = RECRUITMENT_LOCK_KEY_PATTERN, parameters = "#{#request.id}")
    public RecruitmentDTO updateRecruitment(UpdateRecruitmentRequest request) {
        // 检查招新状态
        checkRecruitmentStatus(request.getId(), RecruitmentStatusEnum.ENDED);

        // 更新招新
        RecruitmentDO recruitmentDOForUpdate = recruitmentAssembler.updateRecruitmentRequestToRecruitmentDO(request);
        recruitmentMapper.updateById(recruitmentDOForUpdate);

        return getRecruitment(request.getId());
    }

    @Override
    @DistributedLock(value = RECRUITMENT_LOCK_KEY_PATTERN, parameters = "#{#request.id}")
    public RecruitmentDTO updateReleaseTime(Long id, LocalDateTime releaseTime) {
        // 检查招新状态
        checkRecruitmentStatus(id, RecruitmentStatusEnum.WAITING_START);

        // 如果新发布时间为 null 则设置为当前时间
        if (releaseTime == null) {
            releaseTime = LocalDateTime.now();
        }

        // 更新发布时间
        RecruitmentDO recruitmentDOForUpdate = RecruitmentDO.builder().id(id).releaseTime(releaseTime).build();
        recruitmentMapper.updateById(recruitmentDOForUpdate);

        // 更新后的招新
        return getRecruitment(id);
    }

    @Override
    @DistributedLock(value = RECRUITMENT_LOCK_KEY_PATTERN, parameters = "#{#request.id}")
    public RecruitmentDTO updateRegistrationTimeFrom(Long id, LocalDateTime registrationTimeFrom) {
        // 检查招新状态
        RecruitmentDTO recruitmentDTO = checkRecruitmentStatus(id, RecruitmentStatusEnum.STARTED);

        // 更新报名开始时间
        // 如果 registrationTimeFrom == null 则更新为 releaseTime
        if (registrationTimeFrom == null) {
            RecruitmentDO recruitmentDOForUpdate = RecruitmentDO.builder().id(id)
                    .registrationTimeFrom(recruitmentDTO.getReleaseTime()).build();
            recruitmentMapper.updateById(recruitmentDOForUpdate);
        }
        // 否则正常更新
        else {
            // 报名开始时间不能小于招新发布时间
            if (registrationTimeFrom.isBefore(recruitmentDTO.getReleaseTime())) {
                throw new InvalidValueServiceException("The registrationTimeFrom must not be less than releaseTime.");
            }

            // 更新报名开始时间
            RecruitmentDO recruitmentDOForUpdate = RecruitmentDO.builder().id(id)
                    .registrationTimeFrom(registrationTimeFrom).build();
            recruitmentMapper.updateById(recruitmentDOForUpdate);
        }

        // 更新后的招新
        return getRecruitment(id);
    }

    @Override
    @DistributedLock(value = RECRUITMENT_LOCK_KEY_PATTERN, parameters = "#{#request.id}")
    public RecruitmentDTO updateRegistrationTimeTo(Long id, LocalDateTime registrationTimeTo) {
        // 检查招新状态
        RecruitmentDTO recruitmentDTO = checkRecruitmentStatus(id, RecruitmentStatusEnum.ENDED);

        // 报名截止时间不能小于报名开始时间
        if (registrationTimeTo.isBefore(recruitmentDTO.getRegistrationTimeFrom())) {
            throw new InvalidValueServiceException("The registrationTimeTo must not be less than registrationTimeFrom");
        }

        // 更新报名截止时间
        RecruitmentDO recruitmentDOForUpdate = RecruitmentDO.builder().id(id)
                .registrationTimeTo(registrationTimeTo).build();
        recruitmentMapper.updateById(recruitmentDOForUpdate);

        // 更新后的招新
        return getRecruitment(id);
    }

    @Override
    @DistributedLock(value = RECRUITMENT_LOCK_KEY_PATTERN, parameters = "#{#request.id}")
    public RecruitmentDTO endRegistration(Long id) {
        // 检查招新状态
        RecruitmentDTO recruitmentDTO = checkRecruitmentStatus(id, RecruitmentStatusEnum.ENDED);

        // 当前的状态必须是 STARTED
        if (!Objects.equals(RecruitmentStatusEnum.STARTED.name(), recruitmentDTO.getRecruitmentStatus())) {
            throw new InvalidStatusServiceException("The current status must be STARTED.");
        }

        // 更新当前招新状态为 ENDED并更新 registrationTimeTo 为当前时间
        RecruitmentDO recruitmentDOForUpdate = RecruitmentDO.builder().id(id)
                .recruitmentStatus(RecruitmentStatusEnum.ENDED.name()).registrationTimeTo(LocalDateTime.now()).build();
        recruitmentMapper.updateById(recruitmentDOForUpdate);

        // 获取更新后的招新
        return getRecruitment(id);
    }

    @Override
    @DistributedLock(value = RECRUITMENT_LOCK_KEY_PATTERN, parameters = "#{#request.id}")
    public RecruitmentDTO closeRecruitment(Long id) {
        // 检查招新状态
        RecruitmentDTO recruitmentDTO = checkRecruitmentStatus(id);

        // 当前的状态必须是 CLOSED
        if (!Objects.equals(RecruitmentStatusEnum.STARTED.name(), recruitmentDTO.getRecruitmentStatus())) {
            throw new InvalidStatusServiceException("The current status must be CLOSED.");
        }

        // 更新当前招新状态为 CLOSED
        RecruitmentDO recruitmentDOForUpdate = RecruitmentDO.builder().id(id)
                .recruitmentStatus(RecruitmentStatusEnum.CLOSED.name()).build();
        recruitmentMapper.updateById(recruitmentDOForUpdate);

        // 获取更新后的招新
        return getRecruitment(id);
    }

    @Override
    @DistributedLock(value = RECRUITMENT_LOCK_KEY_PATTERN, parameters = "#{#request.id}")
    public RecruitmentDTO disableRecruitment(Long id) {
        // 更新为不可用
        RecruitmentDO recruitmentDOForUpdate = RecruitmentDO.builder().id(id).available(false).build();
        recruitmentMapper.updateById(recruitmentDOForUpdate);

        // 获取更新后的招新
        return getRecruitment(id);
    }

    @Override
    @DistributedLock(value = RECRUITMENT_LOCK_KEY_PATTERN, parameters = "#{#request.id}")
    public RecruitmentDTO enableRecruitment(Long id) {
        // 更新为可用
        RecruitmentDO recruitmentDOForUpdate = RecruitmentDO.builder().id(id).available(true).build();
        recruitmentMapper.updateById(recruitmentDOForUpdate);

        // 获取更新后的招新
        return getRecruitment(id);
    }

    @Override
    @DistributedLock(value = RECRUITMENT_LOCK_KEY_PATTERN, parameters = "#{#request.id}")
    public RecruitmentDTO updateRecruitmentStatus(Long id, RecruitmentStatusEnum oldRecruitmentStatus,
                                                RecruitmentStatusEnum newRecruitmentStatus) {
        LambdaUpdateWrapper<RecruitmentDO> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(RecruitmentDO::getId, id).eq(RecruitmentDO::getRecruitmentStatus, oldRecruitmentStatus.name());
        RecruitmentDO recruitmentDOForUpdate =
                RecruitmentDO.builder().recruitmentStatus(newRecruitmentStatus.name()).build();

        int count = recruitmentMapper.update(recruitmentDOForUpdate, wrapper);
        if (count < 1) {
            throw new InvalidStatusServiceException("The old recruitment status does not equal "
                    + oldRecruitmentStatus + ".");
        }

        return getRecruitment(id);
    }

    @Override
    public RecruitmentDTO checkRecruitmentStatus(Long id, RecruitmentStatusEnum followRecruitmentStatus) {
        // 检查招新状态
        RecruitmentDTO recruitmentDTO = checkRecruitmentStatus(id);

        // 检查招新状态是否在 followRecruitmentStatus 之前
        RecruitmentStatusEnum recruitmentStatus = RecruitmentStatusEnum.valueOf(recruitmentDTO.getRecruitmentStatus());
        if (recruitmentStatus.getCode() >= followRecruitmentStatus.getCode()) {
            throw new InvalidStatusServiceException(
                    "The recruitment status must be precede " + followRecruitmentStatus + ".");
        }

        // 通过检查
        return recruitmentDTO;
    }

    @Override
    public RecruitmentDTO checkRecruitmentStatus(Long id) throws ServiceException {
        // 检查招新存不存在
        RecruitmentDTO recruitmentDTO = getRecruitment(id);

        // 检查招新是否可用
        if (!recruitmentDTO.getAvailable()) {
            throw new UnavailableServiceException("The recruitment unavailable.");
        }

        // 通过检查
        return recruitmentDTO;
    }

    /**
     * 更新招新的状态
     */
    @Scheduled(initialDelay = UPDATE_RECRUITMENT_STATUS_INITIAL_DELAY,
            fixedDelay = UPDATE_RECRUITMENT_STATUS_FIXED_DELAY)
    protected void updateRecruitmentStatus() {
        // 把已经可以发布的招新发布了
        recruitmentMapper.updateRecruitmentStatusWhenReleaseTimeLessThan(LocalDateTime.now(),
                RecruitmentStatusEnum.WAITING_FOR_RELEASE.name(), RecruitmentStatusEnum.WAITING_START.name());
        // 把已经可以开始报名的招新开始了
        recruitmentMapper.updateRecruitmentStatusWhenRegistrationTimeFromLessThan(LocalDateTime.now(),
                RecruitmentStatusEnum.WAITING_START.name(), RecruitmentStatusEnum.STARTED.name());
        // 把已经结束报名的招新结束了
        recruitmentMapper.updateRecruitmentStatusWhenRegistrationTimeToLessThan(LocalDateTime.now(),
                RecruitmentStatusEnum.STARTED.name(), RecruitmentStatusEnum.ENDED.name());
    }

    /**
     * 检查创建招新的参数
     *
     * @param request CreateRecruitmentRequest
     * @return 检查结果，检查成功返回招新的数据对象，可以直接用于插入数据库
     */
    private RecruitmentDO checkForCreateRecruitment(CreateRecruitmentRequest request) {
        // 检查组织是否存在
        organizationService.getOrganization(request.getOrganizationId());

        // 检查部门状态
        for (Long recruitmentDepartmentId : request.getRecruitmentDepartmentIds()) {
            // 判断部门是否属于该组织的
            DepartmentDTO departmentDTO = departmentService.getDepartment(recruitmentDepartmentId);
            if (!Objects.equals(departmentDTO.getOrganizationId(), request.getOrganizationId())) {
                throw new MisMatchServiceException("部门不属于该组织的");
            }

            // 检查部门状态
            DepartmentDTO departmentDTO1 = departmentService.getDepartment(recruitmentDepartmentId);
            if (departmentDTO1.getDeactivated()) {
                throw new UnprocessableServiceException("Department already deactivated.");
            }
        }

        // 检查学院状态
        for (Long recruitmentCollegeId : request.getRecruitmentCollegeIds()) {
            CollegeDTO collegeDTO = collegeService.getCollege(recruitmentCollegeId);
            if (collegeDTO.getDeactivated()) {
                throw new UnavailableServiceException("The college is deactivated.");
            }
        }

        // 检查专业状态
        for (Long recruitmentMajorId : request.getRecruitmentMajorIds()) {
            MajorDTO majorDTO = majorService.getMajor(recruitmentMajorId);
            if (majorDTO.getDeactivated()) {
                throw new UnavailableServiceException("The major is deactivated");
            }
        }

        // 检查招新发布时间
        RecruitmentStatusEnum recruitmentStatus = RecruitmentStatusEnum.WAITING_FOR_RELEASE;
        LocalDateTime currentTime = LocalDateTime.now();
        if (request.getReleaseTime() == null) {
            request.setReleaseTime(currentTime);
        }

        // 如果招新发布时间小于等于当前时间则直接发布
        LocalDateTime releaseTime = request.getReleaseTime();
        if (releaseTime.isBefore(currentTime) || releaseTime.isEqual(currentTime)) {
            recruitmentStatus = RecruitmentStatusEnum.WAITING_START;
        }

        // 检查报名开始时间
        if (request.getRegistrationTimeFrom() == null) {
            request.setRegistrationTimeFrom(request.getReleaseTime());
        }

        // 报名开始时间必须大于等于招新发布时间
        LocalDateTime registrationTimeFrom = request.getRegistrationTimeFrom();
        if (registrationTimeFrom.isBefore(releaseTime)){
            throw new InvalidValueServiceException(
                    "The registrationTimeFrom must be greater than or equal to releaseTime.");
        }

        // 报名开始时间小于等于当前时间，则直接开始报名
        if (registrationTimeFrom.isBefore(currentTime) || registrationTimeFrom.isEqual(currentTime)) {
            recruitmentStatus = RecruitmentStatusEnum.STARTED;
        }

        // 检查报名结束时间
        if (request.getRegistrationTimeTo() == null) {
            request.setRegistrationTimeTo(MySqlConstants.DATE_TIME_MAX_VALUE);
        }

        // 报名结束时间必须大于报名开始时间
        LocalDateTime registrationTimeTo = request.getRegistrationTimeTo();
        if (registrationTimeTo.isBefore(registrationTimeFrom) || registrationTimeTo.isEqual(registrationTimeFrom)){
            throw new InvalidValueServiceException("The registrationTimeTo must be greater than registrationTimeFrom.");
        }

        // 通过检查
        return RecruitmentDO.builder()
                .organizationId(request.getOrganizationId())
                .positionName(request.getPositionName())
                .recruitmentNumbers(request.getRecruitmentNumbers())
                .positionDuty(request.getPositionDuty())
                .positionRequirement(request.getPositionRequirement())
                .recruitmentGrades(request.getRecruitmentGrades()
                        .stream()
                        .map(Enum::name)
                        .collect(Collectors.toSet()))
                .recruitmentDepartmentIds(request.getRecruitmentDepartmentIds())
                .recruitmentCollegeIds(request.getRecruitmentCollegeIds())
                .recruitmentMajorIds(request.getRecruitmentMajorIds())
                .releaseTime(request.getReleaseTime())
                .registrationTimeFrom(request.getRegistrationTimeFrom())
                .registrationTimeTo(request.getRegistrationTimeTo())
                .recruitmentStatus(recruitmentStatus.name())
                .build();
    }

}
