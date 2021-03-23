package com.xiaohuashifu.recruit.registration.service.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaohuashifu.recruit.common.exception.NotFoundServiceException;
import com.xiaohuashifu.recruit.common.exception.unprocessable.DuplicateServiceException;
import com.xiaohuashifu.recruit.common.util.ObjectUtils;
import com.xiaohuashifu.recruit.oss.api.service.ObjectStorageService;
import com.xiaohuashifu.recruit.registration.api.dto.ApplicationFormTemplateDTO;
import com.xiaohuashifu.recruit.registration.api.request.UpdateApplicationFormTemplateRequest;
import com.xiaohuashifu.recruit.registration.api.service.ApplicationFormTemplateService;
import com.xiaohuashifu.recruit.registration.service.assembler.ApplicationFormTemplateAssembler;
import com.xiaohuashifu.recruit.registration.service.dao.ApplicationFormTemplateMapper;
import com.xiaohuashifu.recruit.registration.service.do0.ApplicationFormTemplateDO;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;

/**
 * 描述：报名表模板服务实现
 *
 * @author xhsf
 * @create 2020/12/28 21:55
 */
@Service
public class ApplicationFormTemplateServiceImpl implements ApplicationFormTemplateService {

    @Reference
    private ObjectStorageService objectStorageService;

    private final ApplicationFormTemplateMapper applicationFormTemplateMapper;

    private final ApplicationFormTemplateAssembler applicationFormTemplateAssembler;

    public ApplicationFormTemplateServiceImpl(ApplicationFormTemplateMapper applicationFormTemplateMapper,
                                              ApplicationFormTemplateAssembler applicationFormTemplateAssembler) {
        this.applicationFormTemplateMapper = applicationFormTemplateMapper;
        this.applicationFormTemplateAssembler = applicationFormTemplateAssembler;
    }

    @Override
    public ApplicationFormTemplateDTO createApplicationFormTemplate(Long userId) {
        // 判断报名表模板是否存在
        LambdaQueryWrapper<ApplicationFormTemplateDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ApplicationFormTemplateDO::getUserId, userId);
        int count = applicationFormTemplateMapper.selectCount(wrapper);
        if (count > 0) {
            throw new DuplicateServiceException( "The applicationFormTemplate already exist.");
        }

        // 添加报名表模板
        ApplicationFormTemplateDO applicationFormTemplateDOForInsert =
                ApplicationFormTemplateDO.builder().userId(userId).build();
        applicationFormTemplateMapper.insert(applicationFormTemplateDOForInsert);
        return getApplicationFormTemplate(applicationFormTemplateDOForInsert.getId());
    }

    @Override
    public ApplicationFormTemplateDTO getApplicationFormTemplate(Long id) {
        ApplicationFormTemplateDO applicationFormTemplateDO = applicationFormTemplateMapper.selectById(id);
        if (applicationFormTemplateDO == null) {
            throw new NotFoundServiceException("applicationFormTemplate", "id", id);
        }
        return applicationFormTemplateAssembler.applicationFormTemplateDOToApplicationFormTemplateDTO(
                applicationFormTemplateDO);
    }

    @Override
    public ApplicationFormTemplateDTO getApplicationFormTemplateByUserId(Long userId) {
        LambdaQueryWrapper<ApplicationFormTemplateDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ApplicationFormTemplateDO::getUserId, userId);
        ApplicationFormTemplateDO applicationFormTemplateDO = applicationFormTemplateMapper.selectOne(wrapper);
        if (applicationFormTemplateDO == null) {
            throw new NotFoundServiceException("applicationFormTemplate", "userId", userId);
        }
        return applicationFormTemplateAssembler.applicationFormTemplateDOToApplicationFormTemplateDTO(
                applicationFormTemplateDO);
    }

    @Override
    public ApplicationFormTemplateDTO updateApplicationFormTemplate(UpdateApplicationFormTemplateRequest request) {
        // 判断报名表模板是否存在
        LambdaQueryWrapper<ApplicationFormTemplateDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ApplicationFormTemplateDO::getId, request.getId()).last("for update");
        ApplicationFormTemplateDO applicationFormTemplateDO = applicationFormTemplateMapper.selectOne(wrapper);
        if (applicationFormTemplateDO == null) {
            throw new NotFoundServiceException("applicationFormTemplate", "id", request.getId());
        }

        // 更新报名表模板
        ApplicationFormTemplateDO applicationFormTemplateDOForUpdate =
                applicationFormTemplateAssembler.updateApplicationFormTemplateRequestToApplicationFormTemplateDO(request);
        applicationFormTemplateMapper.updateById(applicationFormTemplateDOForUpdate);
        return getApplicationFormTemplate(request.getId());
    }

    /**
     * UpdateApplicationFormTemplateRequest to ApplicationFormTemplateDO
     *
     * @param request UpdateApplicationFormTemplateRequest
     * @return ApplicationFormTemplateDO
     */
    private ApplicationFormTemplateDO updateApplicationFormTemplateRequestToApplicationFormTemplateDO(
            UpdateApplicationFormTemplateRequest request) {
        // 预处理
        ObjectUtils.trimAllStringFields(request);
        ApplicationFormTemplateDO applicationFormTemplateDO = applicationFormTemplateAssembler
                .updateApplicationFormTemplateRequestToApplicationFormTemplateDO(request);

        // 链接 avatar
        if (applicationFormTemplateDO.getAvatarUrl() != null) {
            objectStorageService.linkObject(applicationFormTemplateDO.getAvatarUrl());
        }

        // 链接 attachment
        if (applicationFormTemplateDO.getAttachmentUrl() != null) {
            objectStorageService.linkObject(applicationFormTemplateDO.getAttachmentUrl());
        }

        // 构造成功
        return applicationFormTemplateDO;
    }

}
