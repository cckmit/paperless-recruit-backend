package com.xiaohuashifu.recruit.registration.service.service;

import com.xiaohuashifu.recruit.common.aspect.annotation.DistributedLock;
import com.xiaohuashifu.recruit.common.exception.NotFoundServiceException;
import com.xiaohuashifu.recruit.common.query.QueryResult;
import com.xiaohuashifu.recruit.common.util.ObjectUtils;
import com.xiaohuashifu.recruit.organization.api.service.OrganizationService;
import com.xiaohuashifu.recruit.registration.api.dto.RecruitmentDTO;
import com.xiaohuashifu.recruit.registration.api.query.RecruitmentQuery;
import com.xiaohuashifu.recruit.registration.api.request.CreateRecruitmentRequest;
import com.xiaohuashifu.recruit.registration.api.request.UpdateRecruitmentRequest;
import com.xiaohuashifu.recruit.registration.api.service.RecruitmentService;
import com.xiaohuashifu.recruit.registration.service.assembler.RecruitmentAssembler;
import com.xiaohuashifu.recruit.registration.service.dao.RecruitmentMapper;
import com.xiaohuashifu.recruit.registration.service.do0.RecruitmentDO;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;

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

    private final RecruitmentMapper recruitmentMapper;

    private final RecruitmentAssembler recruitmentAssembler;

    /**
     * 招新增加报名表数量的锁定键模式，{0}招新编号
     */
    private static final String INCREASE_NUMBER_OF_APPLICATION_FORMS_LOCK_KEY_PATTERN =
            "recruitment:{0}:increase-number-of-application-forms";

    public RecruitmentServiceImpl(RecruitmentMapper recruitmentMapper, RecruitmentAssembler recruitmentAssembler) {
        this.recruitmentMapper = recruitmentMapper;
        this.recruitmentAssembler = recruitmentAssembler;
    }

    @Override
    public RecruitmentDTO createRecruitment(CreateRecruitmentRequest request) {
        // 检查组织是否存在
        organizationService.getOrganization(request.getOrganizationId());
        ObjectUtils.trimAllStringFields(request);

        // 插入招新
        RecruitmentDO recruitmentDOForInsert = recruitmentAssembler.createRecruitmentRequestToRecruitmentDO(request);
        recruitmentMapper.insert(recruitmentDOForInsert);
        return getRecruitment(recruitmentDOForInsert.getId());
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
    public QueryResult<RecruitmentDTO> listRecruitments(RecruitmentQuery query) {
        return null;
    }

    @Override
    public RecruitmentDTO updateRecruitment(UpdateRecruitmentRequest request) {
        // 更新招新
        RecruitmentDO recruitmentDOForUpdate = recruitmentAssembler.updateRecruitmentRequestToRecruitmentDO(request);
        recruitmentMapper.updateById(recruitmentDOForUpdate);
        return getRecruitment(request.getId());
    }

    @Override
    @DistributedLock(value = INCREASE_NUMBER_OF_APPLICATION_FORMS_LOCK_KEY_PATTERN, parameters = "#{#recruitmentId}")
    public void increaseNumberOfApplicationForms(Long recruitmentId) {
        recruitmentMapper.increaseNumberOfApplicationForms(recruitmentId);
    }

}
