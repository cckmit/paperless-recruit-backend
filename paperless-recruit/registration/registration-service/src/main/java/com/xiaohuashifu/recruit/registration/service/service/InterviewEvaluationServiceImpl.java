package com.xiaohuashifu.recruit.registration.service.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaohuashifu.recruit.common.aspect.annotation.DistributedLock;
import com.xiaohuashifu.recruit.common.exception.NotFoundServiceException;
import com.xiaohuashifu.recruit.common.exception.unprocessable.DuplicateServiceException;
import com.xiaohuashifu.recruit.common.exception.unprocessable.InvalidStatusServiceException;
import com.xiaohuashifu.recruit.common.exception.unprocessable.MisMatchServiceException;
import com.xiaohuashifu.recruit.organization.api.service.OrganizationMemberService;
import com.xiaohuashifu.recruit.registration.api.constant.InterviewStatusEnum;
import com.xiaohuashifu.recruit.registration.api.dto.*;
import com.xiaohuashifu.recruit.registration.api.request.CreateInterviewEvaluationRequest;
import com.xiaohuashifu.recruit.registration.api.service.*;
import com.xiaohuashifu.recruit.registration.service.assembler.InterviewEvaluationAssembler;
import com.xiaohuashifu.recruit.registration.service.dao.InterviewEvaluationMapper;
import com.xiaohuashifu.recruit.registration.service.do0.InterviewEvaluationDO;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;

import java.util.Objects;

/**
 * 描述：面试评价服务
 *
 * @author xhsf
 * @create 2021/1/5 16:48
 */
@Service
public class InterviewEvaluationServiceImpl implements InterviewEvaluationService {

    private final InterviewEvaluationAssembler interviewEvaluationAssembler;

    private final InterviewEvaluationMapper interviewEvaluationMapper;

    @Reference
    private InterviewFormService interviewFormService;

    @Reference
    private InterviewerService interviewerService;

    @Reference
    private InterviewService interviewService;

    @Reference
    private RecruitmentService recruitmentService;

    @Reference
    private OrganizationMemberService organizationMemberService;

    /**
     * 保存面试评价的锁定键模式，{0}面试表编号，{1}是面试官编号
     */
    private static final String CREATE_INTERVIEW_EVALUATION_LOCK_KEY_PATTERN =
            "interview-evaluation:create-interview-evaluation:interview-form-id:{0}:interviewer-id:{1}";

    public InterviewEvaluationServiceImpl(InterviewEvaluationAssembler interviewEvaluationAssembler,
                                          InterviewEvaluationMapper interviewEvaluationMapper) {
        this.interviewEvaluationAssembler = interviewEvaluationAssembler;
        this.interviewEvaluationMapper = interviewEvaluationMapper;
    }

    @DistributedLock(value = CREATE_INTERVIEW_EVALUATION_LOCK_KEY_PATTERN,
            parameters = {"#{#request.interviewFormId}", "#{#request.interviewerId}"})
    @Override
    public InterviewEvaluationDTO createInterviewEvaluation(CreateInterviewEvaluationRequest request) {
        // 判断该面试评价是否已经存在
        LambdaQueryWrapper<InterviewEvaluationDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(InterviewEvaluationDO::getInterviewFormId, request.getInterviewFormId())
                .eq(InterviewEvaluationDO::getInterviewerId, request.getInterviewerId());
        int count = interviewEvaluationMapper.selectCount(wrapper);
        if (count > 0) {
            throw new DuplicateServiceException("The interviewEvaluation already exist.");
        }

        // 判断面试表是否存在
        InterviewFormDTO interviewFormDTO = interviewFormService.getInterviewForm(request.getInterviewFormId());

        // 判断面试官是否存在
        InterviewerDTO interviewerDTO = interviewerService.getInterviewer(request.getInterviewerId());

        // 判断面试官和面试表是否是同个组织的
        InterviewDTO interviewDTO = interviewService.getInterview(interviewFormDTO.getInterviewId());
        RecruitmentDTO recruitmentDTO = recruitmentService.getRecruitment(interviewDTO.getRecruitmentId());
        if (!Objects.equals(recruitmentDTO.getOrganizationId(), interviewerDTO.getOrganizationId())) {
            throw new MisMatchServiceException("面试官和面试表不是同个组织");
        }

        // 判断面试表是否可以评价
        canEvaluation(interviewFormDTO);

        // 插入面试表
        InterviewEvaluationDO interviewEvaluationDOForInsert = InterviewEvaluationDO.builder()
                .interviewerId(request.getInterviewerId()).interviewFormId(request.getInterviewFormId())
                .evaluation(request.getEvaluation()).build();
        interviewEvaluationMapper.insert(interviewEvaluationDOForInsert);

        // 获取创建的面试表
        return getInterviewEvaluation(interviewEvaluationDOForInsert.getId());
    }

    @Override
    public InterviewEvaluationDTO getInterviewEvaluation(Long id) {
        InterviewEvaluationDO interviewEvaluationDO = interviewEvaluationMapper.selectById(id);
        if (interviewEvaluationDO == null) {
            throw new NotFoundServiceException("interviewEvaluation", "id", id);
        }
        return interviewEvaluationAssembler.interviewEvaluationDOToInterviewEvaluationDTO(interviewEvaluationDO);
    }

    @Override
    public InterviewEvaluationDTO updateEvaluation(Long id, String evaluation) {
        // 判断面试评价是否存在
        InterviewEvaluationDTO interviewEvaluationDTO = getInterviewEvaluation(id);
        InterviewFormDTO interviewFormDTO =
                interviewFormService.getInterviewForm(interviewEvaluationDTO.getInterviewFormId());

        // 判断面试表是否可以评价
        canEvaluation(interviewFormDTO);

        // 更新评价
        InterviewEvaluationDO interviewEvaluationDOForUpdate =
                InterviewEvaluationDO.builder().id(id).evaluation(evaluation).build();
        interviewEvaluationMapper.updateById(interviewEvaluationDOForUpdate);

        // 获取更新后的评价
        return getInterviewEvaluation(id);
    }

    /**
     * 判断是否可以评价面试表
     *
     * @param interviewFormDTO InterviewFormDTO
     */
    private void canEvaluation(InterviewFormDTO interviewFormDTO) {
        // 判断当前报名表的状态是否适合更新
        if (InterviewStatusEnum.valueOf(interviewFormDTO.getInterviewStatus()).getCode()
                > InterviewStatusEnum.PENDING.getCode()) {
            throw new InvalidStatusServiceException("The status of interview form can't be greater than "
                    + InterviewStatusEnum.PENDING.name() + ".");
        }
    }

}
