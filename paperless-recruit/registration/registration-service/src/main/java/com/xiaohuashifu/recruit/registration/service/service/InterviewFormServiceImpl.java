package com.xiaohuashifu.recruit.registration.service.service;

import com.xiaohuashifu.recruit.common.aspect.annotation.DistributedLock;
import com.xiaohuashifu.recruit.common.result.ErrorCodeEnum;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.registration.api.constant.InterviewStatusEnum;
import com.xiaohuashifu.recruit.registration.api.dto.InterviewFormDTO;
import com.xiaohuashifu.recruit.registration.api.po.SaveInterviewFormPO;
import com.xiaohuashifu.recruit.registration.api.service.ApplicationFormService;
import com.xiaohuashifu.recruit.registration.api.service.InterviewFormService;
import com.xiaohuashifu.recruit.registration.api.service.InterviewService;
import com.xiaohuashifu.recruit.registration.service.assembler.InterviewFormAssembler;
import com.xiaohuashifu.recruit.registration.service.dao.InterviewFormMapper;
import com.xiaohuashifu.recruit.registration.service.do0.InterviewFormDO;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;

import java.util.Objects;

/**
 * 描述：面试表服务实现
 *
 * @author xhsf
 * @create 2021/1/5 14:29
 */
@Service
public class InterviewFormServiceImpl implements InterviewFormService {

    private final InterviewFormAssembler interviewFormAssembler = InterviewFormAssembler.INSTANCES;

    private final InterviewFormMapper interviewFormMapper;

    @Reference
    private InterviewService interviewService;

    @Reference
    private ApplicationFormService applicationFormService;

    /**
     * 保存面试表锁定键模式，{0}是面试编号，{1}是报名表编号
     */
    private final static String SAVE_INTERVIEW_FORM_LOCK_KEY_PATTERN =
            "interview-form:save-interview-form:interview-id:{0}:application-form-id:{1}";

    public InterviewFormServiceImpl(InterviewFormMapper interviewFormMapper) {
        this.interviewFormMapper = interviewFormMapper;
    }

    /**
     * 保存面试表
     *
     * @permission 必须是所属面试的主体
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 面试不存在
     *              Forbidden: 面试和报名表不是同一个招新的
     *              Forbidden.Unavailable: 招新不可用 | 组织不可用
     *              OperationConflict.Lock: 获取保存面试表的锁失败
     *              OperationConflict.Duplicate: 面试表已经存在
     *
     * @param saveInterviewFormPO 保存面试表的参数对象
     * @return 创建的面试表
     */
    // TODO: 2021/1/5 发送系统通知，通知面试者组织已经发送面试通知了
    @DistributedLock(value = SAVE_INTERVIEW_FORM_LOCK_KEY_PATTERN,
            parameters = {"#{#saveInterviewFormPO.interviewId}", "#{#saveInterviewFormPO.applicationFormId}"},
            errorMessage = "Failed to acquire save interview form lock.")
    @Override
    public Result<InterviewFormDTO> saveInterviewForm(SaveInterviewFormPO saveInterviewFormPO) {
        // 检查面试状态
        Long interviewId = saveInterviewFormPO.getInterviewId();
        Result<InterviewFormDTO> checkInterviewStatusResult = interviewService.checkInterviewStatus(interviewId);
        if (checkInterviewStatusResult.isFailure()) {
            return checkInterviewStatusResult;
        }

        // 判断是否已经存在该面试表
        Long applicationFormId = saveInterviewFormPO.getApplicationFormId();
        int count = interviewFormMapper.countByInterviewIdAndApplicationFormId(interviewId, applicationFormId);
        if (count > 0) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT_DUPLICATE,
                    "The interviewForm already exist.");
        }

        // 判断面试和报名表是否是同一个招新的
        Long recruitmentIdFromInterview = interviewService.getRecruitmentId(interviewId);
        Long recruitmentIdFromApplicationForm = applicationFormService.getRecruitmentId(applicationFormId);
        if (!Objects.equals(recruitmentIdFromInterview, recruitmentIdFromApplicationForm)) {
            return Result.fail(ErrorCodeEnum.FORBIDDEN);
        }

        // 保存面试表
        InterviewFormDO interviewFormDO = InterviewFormDO.builder()
                .interviewId(interviewId)
                .applicationFormId(applicationFormId)
                .interviewTime(saveInterviewFormPO.getInterviewTime())
                .interviewLocation(saveInterviewFormPO.getInterviewLocation())
                .note(saveInterviewFormPO.getNote())
                .interviewStatus(InterviewStatusEnum.WAITING_INTERVIEW.name())
                .build();
        interviewFormMapper.insertInterviewForm(interviewFormDO);

        // 获取创建的面试表
        return getInterviewForm(interviewFormDO.getId());
    }

    /**
     * 更新面试时间
     *
     * @permission 必须是面试表的主体
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 面试表不存在
     *              OperationConflict.Status: 面试表的状态不是 WAITING_INTERVIEW
     *
     * @param id 面试表编号
     * @param interviewTime 面试时间
     * @return 更新后的面试表
     */
    // TODO: 2021/1/5 给面试者发送面试时间已经修改的信息
    @Override
    public Result<InterviewFormDTO> updateInterviewTime(Long id, String interviewTime) {
        // 检查面试表状态
        Result<InterviewFormDTO> checkResult = checkForUpdate(id);
        if (checkResult.isFailure()) {
            return checkResult;
        }

        // 更新面试时间
        interviewFormMapper.updateInterviewTime(id, interviewTime);

        // 获取更新后的面试表
        return getInterviewForm(id);
    }

    /**
     * 更新面试地点
     *
     * @permission 必须是面试表的主体
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 面试表不存在
     *              OperationConflict.Status: 面试表的状态不是 WAITING_INTERVIEW
     *
     * @param id 面试表编号
     * @param interviewLocation 面试地点
     * @return 更新后的面试表
     */
    // TODO: 2021/1/5 给面试者发送面试地点已经修改的信息
    @Override
    public Result<InterviewFormDTO> updateInterviewLocation(Long id, String interviewLocation) {
        // 检查面试表状态
        Result<InterviewFormDTO> checkResult = checkForUpdate(id);
        if (checkResult.isFailure()) {
            return checkResult;
        }

        // 更新面试地点
        interviewFormMapper.updateInterviewLocation(id, interviewLocation);

        // 获取更新后的面试表
        return getInterviewForm(id);
    }

    /**
     * 更新备注
     *
     * @permission 必须是面试表的主体
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 面试表不存在
     *              OperationConflict.Status: 面试表的状态不是 WAITING_INTERVIEW
     *
     * @param id 面试表编号
     * @param note 备注
     * @return 更新后的面试表
     */
    // TODO: 2021/1/5 给面试者发送备注已经修改的信息
    @Override
    public Result<InterviewFormDTO> updateNote(Long id, String note) {
        // 检查面试表状态
        Result<InterviewFormDTO> checkResult = checkForUpdate(id);
        if (checkResult.isFailure()) {
            return checkResult;
        }

        // 更新备注
        interviewFormMapper.updateNote(id, note);

        // 获取更新后的面试表
        return getInterviewForm(id);
    }

    /**
     * 更新面试状态
     *
     * @permission 必须是面试表的主体
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 面试表不存在
     *              InvalidParameter.Mismatch: 旧面试状态已经改变了
     *              OperationConflict.Status: 旧面试状态必须小于新模式状态
     *
     * @param id 面试表编号
     * @param oldInterviewStatus 旧面试状态
     * @param newInterviewStatus 新面试状态
     * @return 更新后的面试表
     */
    // TODO: 2021/1/5 给面试者发送面试状态已经修改的信息
    @Override
    public Result<InterviewFormDTO> updateInterviewStatus(Long id, InterviewStatusEnum oldInterviewStatus,
                                                          InterviewStatusEnum newInterviewStatus) {
        // 判断面试表是否存在
        String interviewStatus = interviewFormMapper.getInterviewStatus(id);
        if (interviewStatus == null) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_EXIST,
                    "The interviewForm does not exist.");
        }

        // 旧面试状态必须小于新模式状态
        if (oldInterviewStatus.getCode() >= newInterviewStatus.getCode()) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT_STATUS,
                    "The newInterviewStatus must be greater than oldInterviewStatus.");
        }

        // 更新面试状态
        int count = interviewFormMapper.updateInterviewStatus(id, oldInterviewStatus, newInterviewStatus);

        // 更新失败表示旧面试状态已经改变了
        if (count < 1) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_MISMATCH,
                    "The oldInterviewStatus does not is " + oldInterviewStatus.name() + ".");
        }

        // 获取更新后的面试表
        return getInterviewForm(id);
    }

    /**
     * 获取面试编号
     *
     * @private 内部方法
     *
     * @param id 面试表编号
     * @return 面试编号，若不存在返回 null
     */
    @Override
    public Long getInterviewId(Long id) {
        return interviewFormMapper.getInterviewId(id);
    }

    /**
     * 获取面试状态
     *
     * @private 内部方法
     *
     * @param id 面试表编号
     * @return 面试状态，若不存在返回 null
     */
    @Override
    public String getInterviewStatus(Long id) {
        return interviewFormMapper.getInterviewStatus(id);
    }

    /**
     * 验证面试表的主体
     *
     * @private 内部方法
     *
     * @param id 面试表编号
     * @param userId 主体编号
     * @return 若是返回 true，不是返回 false
     */
    @Override
    public Boolean authenticatePrincipal(Long id, Long userId) {
        Long interviewId = interviewFormMapper.getInterviewId(id);
        if (interviewId == null) {
            return false;
        }
        return interviewService.authenticatePrincipal(interviewId, userId);
    }

    /**
     * 获取面试表
     *
     * @param id 面试表编号
     * @return 面试表
     */
    private Result<InterviewFormDTO> getInterviewForm(Long id) {
        InterviewFormDO interviewFormDO = interviewFormMapper.getInterviewForm(id);
        return Result.success(interviewFormAssembler.toDTO(interviewFormDO));
    }

    /**
     * 检查面试表的状态是否适合招新
     *
     * @errorCode InvalidParameter.NotExist: 面试表不存在
     *              OperationConflict.Status: 面试表的状态不是 WAITING_INTERVIEW
     *
     * @param id 面试表编号
     * @return 检查结果
     */
    private <T> Result<T> checkForUpdate(Long id) {
        // 判断面试表是否存在
        String interviewStatus = interviewFormMapper.getInterviewStatus(id);
        if (interviewStatus == null) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_EXIST,
                    "The interviewForm does not exist.");
        }

        // 判断面试状态是否是 WAITING_INTERVIEW
        if (!Objects.equals(interviewStatus, InterviewStatusEnum.WAITING_INTERVIEW.name())) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT_STATUS,
                    "The status of interviewForm must be "
                            + InterviewStatusEnum.WAITING_INTERVIEW.name() + ".");
        }

        // 通过检查
        return Result.success();
    }

}
