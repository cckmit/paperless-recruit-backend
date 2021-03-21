package com.xiaohuashifu.recruit.facade.service.manager.impl;

import com.xiaohuashifu.recruit.common.query.QueryResult;
import com.xiaohuashifu.recruit.facade.service.assembler.ApplicationFormAssembler;
import com.xiaohuashifu.recruit.facade.service.manager.ApplicationFormManager;
import com.xiaohuashifu.recruit.facade.service.manager.RecruitmentManager;
import com.xiaohuashifu.recruit.facade.service.vo.ApplicationFormVO;
import com.xiaohuashifu.recruit.registration.api.dto.ApplicationFormDTO;
import com.xiaohuashifu.recruit.registration.api.query.ApplicationFormQuery;
import com.xiaohuashifu.recruit.registration.api.request.CreateApplicationFormRequest;
import com.xiaohuashifu.recruit.registration.api.request.UpdateApplicationFormRequest;
import com.xiaohuashifu.recruit.registration.api.service.ApplicationFormService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 描述：报名表管理器
 *
 * @author xhsf
 * @create 2021/1/9 14:57
 */
@Component
@CacheConfig(cacheNames = "default")
public class ApplicationFormManagerImpl implements ApplicationFormManager {

    private final ApplicationFormAssembler applicationFormAssembler;

    private final RecruitmentManager recruitmentManager;

    @Reference
    private ApplicationFormService applicationFormService;

    public ApplicationFormManagerImpl(ApplicationFormAssembler applicationFormAssembler,
                                      RecruitmentManager recruitmentManager) {
        this.applicationFormAssembler = applicationFormAssembler;
        this.recruitmentManager = recruitmentManager;
    }

    @Override
    public ApplicationFormVO createApplicationForm(CreateApplicationFormRequest request) {
        ApplicationFormDTO applicationFormDTO = applicationFormService.createApplicationForm(request);
        return deepAssembler(applicationFormDTO);
    }

    @Cacheable(key = "'application-forms:' + #id")
    @Override
    public ApplicationFormVO getApplicationForm(Long id) {
        return deepAssembler(applicationFormService.getApplicationForm(id));
    }

    @Cacheable(key = "'application-forms:' + #query")
    @Override
    public QueryResult<ApplicationFormVO> listApplicationForms(ApplicationFormQuery query) {
        QueryResult<ApplicationFormDTO> result = applicationFormService.listApplicationForms(query);
        List<ApplicationFormVO> applicationFormVOS = result.getResult().stream()
                .map(this::deepAssembler)
                .collect(Collectors.toList());
        return new QueryResult<>(result.getTotal(), applicationFormVOS);
    }

//    @Caching(evict = {
//            @CacheEvict(key = "'departments:' + #departmentId", beforeInvocation = true)
//    })
    @Override
    public ApplicationFormVO updateApplicationForm(UpdateApplicationFormRequest request) {
        ApplicationFormDTO applicationFormDTO = applicationFormService.updateApplicationForm(request);
        return deepAssembler(applicationFormDTO);
    }

    /**
     * 深装配
     *
     * @param applicationFormDTO ApplicationFormDTO
     * @return ApplicationFormVO
     */
    private ApplicationFormVO deepAssembler(ApplicationFormDTO applicationFormDTO) {
        ApplicationFormVO applicationFormVO =
                applicationFormAssembler.applicationFormDTOToApplicationFormVO(applicationFormDTO);
        applicationFormVO.setRecruitment(recruitmentManager.getRecruitment(applicationFormDTO.getRecruitmentId()));
        return applicationFormVO;
    }

}
