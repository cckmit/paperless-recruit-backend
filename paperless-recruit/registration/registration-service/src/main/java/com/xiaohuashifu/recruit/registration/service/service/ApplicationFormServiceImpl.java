package com.xiaohuashifu.recruit.registration.service.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaohuashifu.recruit.common.aspect.annotation.DistributedLock;
import com.xiaohuashifu.recruit.common.exception.NotFoundServiceException;
import com.xiaohuashifu.recruit.common.exception.unprocessable.DuplicateServiceException;
import com.xiaohuashifu.recruit.common.exception.unprocessable.InvalidStatusServiceException;
import com.xiaohuashifu.recruit.common.query.QueryResult;
import com.xiaohuashifu.recruit.common.util.ObjectUtils;
import com.xiaohuashifu.recruit.oss.api.service.ObjectStorageService;
import com.xiaohuashifu.recruit.registration.api.constant.RecruitmentStatusEnum;
import com.xiaohuashifu.recruit.registration.api.dto.ApplicationFormDTO;
import com.xiaohuashifu.recruit.registration.api.dto.RecruitmentDTO;
import com.xiaohuashifu.recruit.registration.api.query.ApplicationFormQuery;
import com.xiaohuashifu.recruit.registration.api.request.CreateApplicationFormRequest;
import com.xiaohuashifu.recruit.registration.api.request.UpdateApplicationFormRequest;
import com.xiaohuashifu.recruit.registration.api.service.ApplicationFormService;
import com.xiaohuashifu.recruit.registration.api.service.RecruitmentService;
import com.xiaohuashifu.recruit.registration.service.assembler.ApplicationFormAssembler;
import com.xiaohuashifu.recruit.registration.service.dao.ApplicationFormMapper;
import com.xiaohuashifu.recruit.registration.service.do0.ApplicationFormDO;
import com.xiaohuashifu.recruit.user.api.service.UserService;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 描述：报名表服务实现
 *
 * @author xhsf
 * @create 2020/12/29 21:03
 */
@Service
public class ApplicationFormServiceImpl implements ApplicationFormService {

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

        // 判断用户是否存在
        userService.getUser(request.getUserId());

        // 判断该招新是否已经结束
        RecruitmentDTO recruitmentDTO = recruitmentService.getRecruitment(request.getRecruitmentId());
        if (RecruitmentStatusEnum.ENDED.name().equals(recruitmentDTO.getRecruitmentStatus())) {
            throw new InvalidStatusServiceException("招新已经结束");
        }

        // 构造插入数据库的数据对象
        ApplicationFormDO applicationFormDOForInsert = createApplicationFormRequestToApplicationFormDO(request);

        // 插入数据库
        applicationFormMapper.insert(applicationFormDOForInsert);

        // 增加招新的报名表数量
        recruitmentService.increaseNumberOfApplicationForms(request.getRecruitmentId());
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

    @Override
    public QueryResult<ApplicationFormDTO> listApplicationForms(ApplicationFormQuery query) {
        LambdaQueryWrapper<ApplicationFormDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(query.getRecruitmentId() != null,
                ApplicationFormDO::getRecruitmentId, query.getRecruitmentId())
                .likeRight(query.getCollege() != null, ApplicationFormDO::getCollege, query.getCollege())
                .likeRight(query.getMajor() != null, ApplicationFormDO::getMajor, query.getMajor())
                .orderByAsc(BooleanUtils.isTrue(query.getOrderByApplicationTime()),
                        ApplicationFormDO::getApplicationTime)
                .orderByDesc(BooleanUtils.isTrue(query.getOrderByApplicationTimeDesc()),
                        ApplicationFormDO::getApplicationTime);

        Page<ApplicationFormDO> page = new Page<>(query.getPageNum(), query.getPageSize(), true);
        applicationFormMapper.selectPage(page, wrapper);
        List<ApplicationFormDTO> applicationFormDTOS = page.getRecords().stream()
                .map(applicationFormAssembler::applicationFormDOToApplicationFormDTO)
                .collect(Collectors.toList());
        return new QueryResult<>(page.getTotal(), applicationFormDTOS);
    }

    @DistributedLock(value = APPLICATION_FORM_LOCK_KEY_PATTERN, parameters = "#{#request.id}")
    @Override
    public ApplicationFormDTO updateApplicationForm(UpdateApplicationFormRequest request) {
        // 判断报名表是否存在
        ApplicationFormDTO applicationFormDTO = getApplicationForm(request.getId());

        // 判断该招新是否已经结束
        RecruitmentDTO recruitmentDTO = recruitmentService.getRecruitment(applicationFormDTO.getRecruitmentId());
        if (RecruitmentStatusEnum.ENDED.name().equals(recruitmentDTO.getRecruitmentStatus())) {
            throw new InvalidStatusServiceException("招新已经结束");
        }

        // 构造更新数据库的数据对象
        ApplicationFormDO applicationFormDOForUpdate = updateApplicationFormRequestToApplicationFormDO(request);

        // 更新到数据库
        applicationFormMapper.updateById(applicationFormDOForUpdate);
        return getApplicationForm(request.getId());
    }

    /**
     * CreateApplicationFormRequest to ApplicationFormDO
     *
     * @param request CreateApplicationFormRequest
     * @return ApplicationFormDO
     */
    private ApplicationFormDO createApplicationFormRequestToApplicationFormDO(CreateApplicationFormRequest request) {
        // 预处理
        ObjectUtils.trimAllStringFields(request);
        ApplicationFormDO applicationFormDO =
                applicationFormAssembler.createApplicationFormRequestToApplicationFormDO(request);

        // 链接 avatar
        objectStorageService.linkObject(applicationFormDO.getAvatarUrl());

        // 链接 attachment
        if (applicationFormDO.getAttachmentUrl() != null) {
            objectStorageService.linkObject(applicationFormDO.getAttachmentUrl());
        }

        // 构造成功
        return applicationFormDO;
    }

    /**
     * UpdateApplicationFormRequest to ApplicationFormDO
     *
     * @param request UpdateApplicationFormRequest
     * @return ApplicationFormDO
     */
    private ApplicationFormDO updateApplicationFormRequestToApplicationFormDO(UpdateApplicationFormRequest request) {
        // 预处理
        ObjectUtils.trimAllStringFields(request);
        ApplicationFormDO applicationFormDO =
                applicationFormAssembler.updateApplicationFormRequestToApplicationFormDO(request);

        // 链接 avatar
        if (applicationFormDO.getAvatarUrl() != null) {
            objectStorageService.linkObject(applicationFormDO.getAvatarUrl());
        }

        // 链接 attachment
        if (applicationFormDO.getAttachmentUrl() != null) {
            objectStorageService.linkObject(applicationFormDO.getAttachmentUrl());
        }

        // 构造成功
        return applicationFormDO;
    }

}
