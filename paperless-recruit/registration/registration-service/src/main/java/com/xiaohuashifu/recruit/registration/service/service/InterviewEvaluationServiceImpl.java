package com.xiaohuashifu.recruit.registration.service.service;

import com.xiaohuashifu.recruit.common.aspect.annotation.DistributedLock;
import com.xiaohuashifu.recruit.common.result.ErrorCodeEnum;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.organization.api.service.OrganizationMemberService;
import com.xiaohuashifu.recruit.registration.api.constant.InterviewStatusEnum;
import com.xiaohuashifu.recruit.registration.api.dto.InterviewEvaluationDTO;
import com.xiaohuashifu.recruit.registration.api.po.SaveInterviewEvaluationPO;
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

    private final InterviewEvaluationAssembler interviewEvaluationAssembler = InterviewEvaluationAssembler.INSTANCES;

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
    private static final String SAVE_INTERVIEW_EVALUATION_LOCK_KEY_PATTERN =
            "interview-evaluation:save-interview-evaluation:interview-form-id:{0}:interviewer-id:{1}";

    public InterviewEvaluationServiceImpl(InterviewEvaluationMapper interviewEvaluationMapper) {
        this.interviewEvaluationMapper = interviewEvaluationMapper;
    }

    /**
     * 保存面试评价
     *
     * @permission 必须是面试官->组织成员->用户的主体
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 面试表不存在 | 面试官不存在
     *              Forbidden: 面试官和面试表不是同一个组织的
     *              OperationConflict.Status: 面试表状态已经在 PENDING 之后，不可评价
     *              OperationConflict.Duplicate: 面试评价已经存在
     *              OperationConflict.Lock: 获取保存面试评价的锁失败
     *
     * @param saveInterviewEvaluationPO 保存面试评价的参数对象
     * @return 新创建的面试评价
     */
    @DistributedLock(value = SAVE_INTERVIEW_EVALUATION_LOCK_KEY_PATTERN,
            parameters = {"#{#saveInterviewEvaluationPO.interviewFormId}",
                    "#{#saveInterviewEvaluationPO.interviewerId}"},
            errorMessage = "Failed to acquire save interview evaluation lock.")
    @Override
    public Result<InterviewEvaluationDTO> saveInterviewEvaluation(SaveInterviewEvaluationPO saveInterviewEvaluationPO) {
        // 判断该面试评价是否已经存在
        Long interviewFormId = saveInterviewEvaluationPO.getInterviewFormId();
        Long interviewerId = saveInterviewEvaluationPO.getInterviewerId();
        int count = interviewEvaluationMapper.countByInterviewFormIdAndInterviewerId(interviewFormId, interviewerId);
        if (count > 0) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT_DUPLICATE,
                    "The interviewEvaluation already exist.");
        }

        // 判断面试表是否存在
        Long interviewId = interviewFormService.getInterviewId(interviewFormId);
        if (interviewId == null) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_EXIST,
                    "The interviewForm does not exist.");
        }

        // 判断面试官是否存在
        Long organizationIdFromInterviewer = interviewerService.getOrganizationId(interviewerId);
        if (organizationIdFromInterviewer == null) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_EXIST,
                    "The interviewer does not exist.");
        }

        // 判断面试官和面试表是否是同个组织的
        Long recruitmentId = interviewService.getRecruitmentId(interviewId);
        Long organizationIdFromInterviewForm = recruitmentService.getOrganizationId(recruitmentId);
        if (!Objects.equals(organizationIdFromInterviewForm, organizationIdFromInterviewer)) {
            return Result.fail(ErrorCodeEnum.FORBIDDEN);
        }

        // 判断面试表是否可以评价
        Result<InterviewEvaluationDTO> canEvaluation = canEvaluation(interviewFormId);
        if (canEvaluation.isFailure()) {
            return canEvaluation;
        }

        // 插入面试表
        InterviewEvaluationDO interviewEvaluationDO = InterviewEvaluationDO.builder()
                .interviewerId(interviewerId)
                .interviewFormId(interviewFormId)
                .evaluation(saveInterviewEvaluationPO.getEvaluation())
                .build();
        interviewEvaluationMapper.insertInterviewEvaluation(interviewEvaluationDO);

        // 获取创建的面试表
        return getInterviewEvaluation(interviewEvaluationDO.getId());
    }

    /**
     * 更新面试评价
     *
     * @permission 必须是面试评价的主体
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 面试评价不存在
     *              OperationConflict.Status: 面试表状态已经在 PENDING 之后，不可评价
     *
     * @param id 面试评价编号
     * @param evaluation 评价
     * @return 更新后的评价
     */
    @Override
    public Result<InterviewEvaluationDTO> updateEvaluation(Long id, String evaluation) {
        // 判断面试评价是否存在
        Long interviewFormId = interviewEvaluationMapper.getInterviewFormId(id);
        if (interviewFormId == null) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_EXIST,
                    "The interview evaluation does not exist.");
        }

        // 判断面试表是否可以评价
        Result<InterviewEvaluationDTO> canEvaluation = canEvaluation(interviewFormId);
        if (canEvaluation.isFailure()) {
            return canEvaluation;
        }

        // 更新评价
        interviewEvaluationMapper.updateEvaluation(id, evaluation);

        // 获取更新后的评价
        return getInterviewEvaluation(id);
    }

    /**
     * 验证面试评价的主体，也就是面试评价->面试官->组织成员->用户的主体
     *
     * @private 内部方法
     *
     * @param id 面试评价编号
     * @param userId 主体编号
     * @return 若是返回 true，不是返回 false
     */
    @Override
    public Boolean authenticatePrincipal(Long id, Long userId) {
        Long interviewerId = interviewEvaluationMapper.getInterviewerId(id);
        if (interviewerId == null) {
            return false;
        }
        Long organizationMemberId = interviewerService.getOrganizationMemberId(interviewerId);
        Long userId0 = organizationMemberService.getUserId(organizationMemberId);
        return Objects.equals(userId, userId0);
    }

    /**
     * 获取面试评价
     *
     * @param id 面试评价编号
     * @return 面试评价
     */
    private Result<InterviewEvaluationDTO> getInterviewEvaluation(Long id) {
        InterviewEvaluationDO interviewEvaluationDO = interviewEvaluationMapper.getInterviewEvaluation(id);
        return Result.success(interviewEvaluationAssembler.toDTO(interviewEvaluationDO));
    }

    /**
     * 判断是否可以评价面试表
     *
     * @errorCode OperationConflict.Status: 面试表状态已经在 PENDING 之后，不可评价
     *
     * @param interviewFormId 面试表编号
     * @return 是否可以评价
     */
    private <T> Result<T> canEvaluation(Long interviewFormId) {
        // 判断当前报名表的状态是否适合更新
        String interviewStatus = interviewFormService.getInterviewStatus(interviewFormId);
        if (InterviewStatusEnum.valueOf(interviewStatus).getCode() > InterviewStatusEnum.PENDING.getCode()) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT_STATUS,
                    "The status of interview form can't be greater than "
                            + InterviewStatusEnum.PENDING.name() + ".");
        }

        // 可以评价
        return Result.success();
    }

}
