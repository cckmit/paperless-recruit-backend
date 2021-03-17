package com.xiaohuashifu.recruit.registration.service.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaohuashifu.recruit.common.aspect.annotation.DistributedLock;
import com.xiaohuashifu.recruit.common.exception.NotFoundServiceException;
import com.xiaohuashifu.recruit.common.exception.unprocessable.InvalidStatusServiceException;
import com.xiaohuashifu.recruit.common.query.QueryResult;
import com.xiaohuashifu.recruit.common.util.ObjectUtils;
import com.xiaohuashifu.recruit.organization.api.service.OrganizationService;
import com.xiaohuashifu.recruit.registration.api.constant.RecruitmentStatusEnum;
import com.xiaohuashifu.recruit.registration.api.dto.RecruitmentDTO;
import com.xiaohuashifu.recruit.registration.api.query.RecruitmentQuery;
import com.xiaohuashifu.recruit.registration.api.request.CreateRecruitmentRequest;
import com.xiaohuashifu.recruit.registration.api.request.UpdateRecruitmentRequest;
import com.xiaohuashifu.recruit.registration.api.service.RecruitmentService;
import com.xiaohuashifu.recruit.registration.service.assembler.RecruitmentAssembler;
import com.xiaohuashifu.recruit.registration.service.dao.RecruitmentMapper;
import com.xiaohuashifu.recruit.registration.service.do0.RecruitmentDO;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
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

    private final RecruitmentMapper recruitmentMapper;

    private final RecruitmentAssembler recruitmentAssembler;

    /**
     * 招新增加报名表数量的锁定键模式，{0}招新编号
     */
    private static final String INCREASE_NUMBER_OF_APPLICATION_FORMS_LOCK_KEY_PATTERN =
            "recruitment:{0}:increase-number-of-application-forms";

    /**
     * 招新状态集合
     */
    private static final Set<String> RECRUITMENT_STATUS_SET = Arrays.stream(RecruitmentStatusEnum.values())
            .map(Enum::name)
            .collect(Collectors.toUnmodifiableSet());

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
        recruitmentDOForInsert.setRecruitmentStatus(RecruitmentStatusEnum.STARTED.name());
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
        LambdaQueryWrapper<RecruitmentDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(query.getOrganizationId() != null,
                RecruitmentDO::getOrganizationId, query.getOrganizationId())
                .eq(query.getRecruitmentStatus() != null,
                        RecruitmentDO::getRecruitmentStatus, query.getRecruitmentStatus())
                .likeRight(query.getRecruitmentName() != null,
                        RecruitmentDO::getRecruitmentName, query.getRecruitmentName())
                .orderByAsc(BooleanUtils.isTrue(query.getOrderByRecruitmentStatus()), RecruitmentDO::getRecruitmentStatus)
                .orderByDesc(BooleanUtils.isTrue(query.getOrderByRecruitmentStatusDesc()), RecruitmentDO::getRecruitmentStatus)
                .orderByAsc(BooleanUtils.isTrue(query.getOrderByUpdateTime()), RecruitmentDO::getUpdateTime)
                .orderByDesc(BooleanUtils.isTrue(query.getOrderByUpdateTimeDesc()), RecruitmentDO::getUpdateTime);

        Page<RecruitmentDO> page = new Page<>(query.getPageNum(), query.getPageSize(), true);
        recruitmentMapper.selectPage(page, wrapper);
        List<RecruitmentDTO> recruitmentDTOS = page.getRecords()
                .stream().map(recruitmentAssembler::recruitmentDOToRecruitmentDTO).collect(Collectors.toList());
        return new QueryResult<>(page.getTotal(), recruitmentDTOS);
    }

    @Override
    public RecruitmentDTO updateRecruitment(UpdateRecruitmentRequest request) {
        // 预处理请求
        ObjectUtils.trimAllStringFields(request);
        RecruitmentDO recruitmentDOForUpdate = recruitmentAssembler.updateRecruitmentRequestToRecruitmentDO(request);

        // 招新状态检查
        if (request.getRecruitmentStatus() != null) {
            // 招新状态是否正确
            if (!RECRUITMENT_STATUS_SET.contains(request.getRecruitmentStatus())) {
                throw new InvalidStatusServiceException("招新状态不正确，正确的招新状态必须是：" + RECRUITMENT_STATUS_SET + "之一");
            }

            // 若是 ENDED 则添加结束时间
            if (RecruitmentStatusEnum.ENDED.name().equals(request.getRecruitmentStatus())) {
                recruitmentDOForUpdate.setEndTime(LocalDateTime.now());
            }
        }

        // 更新招新
        recruitmentMapper.updateById(recruitmentDOForUpdate);
        return getRecruitment(request.getId());
    }

    @Override
    @DistributedLock(value = INCREASE_NUMBER_OF_APPLICATION_FORMS_LOCK_KEY_PATTERN, parameters = "#{#recruitmentId}")
    public void increaseNumberOfApplicationForms(Long recruitmentId) {
        recruitmentMapper.increaseNumberOfApplicationForms(recruitmentId);
    }

}
