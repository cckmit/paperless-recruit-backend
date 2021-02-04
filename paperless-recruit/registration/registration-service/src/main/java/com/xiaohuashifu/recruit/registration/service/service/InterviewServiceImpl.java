package com.xiaohuashifu.recruit.registration.service.service;

import com.xiaohuashifu.recruit.common.aspect.annotation.DistributedLock;
import com.xiaohuashifu.recruit.common.result.ErrorCodeEnum;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.registration.api.constant.InterviewConstants;
import com.xiaohuashifu.recruit.registration.api.dto.InterviewDTO;
import com.xiaohuashifu.recruit.registration.api.service.InterviewService;
import com.xiaohuashifu.recruit.registration.api.service.RecruitmentService;
import com.xiaohuashifu.recruit.registration.service.assembler.InterviewAssembler;
import com.xiaohuashifu.recruit.registration.service.dao.InterviewMapper;
import com.xiaohuashifu.recruit.registration.service.do0.InterviewDO;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;

/**
 * 描述：面试服务
 *
 * @author xhsf
 * @create 2021/1/5 00:35
 */
@Service
public class InterviewServiceImpl implements InterviewService {

    private final InterviewAssembler interviewAssembler = InterviewAssembler.INSTANCES;

    private final InterviewMapper interviewMapper;

    @Reference
    private RecruitmentService recruitmentService;

    /**
     * 创建面试的锁定键模式，{0}是招新编号
     */
    private static final String CREATE_INTERVIEW_LOCK_KEY_PATTERN = "interview:create-interview:recruitment-id:{0}";

    public InterviewServiceImpl(InterviewMapper interviewMapper) {
        this.interviewMapper = interviewMapper;
    }

    /**
     * 创建一个面试
     *
     * @permission 必须是招新所属组织所属用户主体
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 招新不存在
     *              Forbidden.Unavailable: 招新不可用 | 组织不可用
     *              OperationConflict.Lock: 获取创建 interview 的锁失败
     *
     * @param recruitmentId 招新编号
     * @param title 面试标题
     * @return 面试对象
     */
    @DistributedLock(value = CREATE_INTERVIEW_LOCK_KEY_PATTERN, parameters = "#{#recruitmentId}",
            errorMessage = "Failed to acquire create interview lock.")
    @Override
    public Result<InterviewDTO> createInterview(Long recruitmentId, String title) {
        // 检查招新状态
        Result<InterviewDTO> checkResult = recruitmentService.checkRecruitmentStatus(recruitmentId);
        if (checkResult.isFailure()) {
            return checkResult;
        }

        // 获取下一个轮次
        Result<Integer> getNextRoundResult = getNextRound(recruitmentId);
        if (getNextRoundResult.isFailure()) {
            return Result.fail(getNextRoundResult);
        }
        Integer nextRound = getNextRoundResult.getData();

        // 插入数据库
        InterviewDO interviewDO = InterviewDO.builder()
                .recruitmentId(recruitmentId)
                .title(title)
                .round(nextRound)
                .build();
        interviewMapper.insertInterview(interviewDO);

        // 获取创建的面试
        return getInterview(interviewDO.getId());
    }

    /**
     * 获取面试
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotFound: 不存在该面试
     *
     * @param id 面试编号
     * @return 面试
     */
    @Override
    public Result<InterviewDTO> getInterview(Long id) {
        InterviewDO interviewDO = interviewMapper.getInterview(id);
        if (interviewDO == null) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_FOUND, "The interview does not exist.");
        }
        return Result.success(interviewAssembler.toDTO(interviewDO));
    }

    /**
     * 更新面试标题
     *
     * @permission 必须是面试所属的主体
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 面试不存在
     *              Forbidden.Unavailable: 招新不可用 | 组织不可用
     *
     * @param id 面试编号
     * @param title 面试标题
     * @return 更新后的面试对象
     */
    @Override
    public Result<InterviewDTO> updateTitle(Long id, String title) {
        // 检查面试状态
        Result<InterviewDTO> checkResult = checkInterviewStatus(id);
        if (checkResult.isFailure()) {
            return checkResult;
        }

        // 更新标题
        interviewMapper.updateTitle(id, title);

        // 获取更新后的面试
        return getInterview(id);
    }

    /**
     * 获取下一个轮次
     *
     * @permission 必须是招新所属的主体
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              OperationConflict.OverLimit: 超过轮次限制
     *
     * @param recruitmentId 招新编号
     * @return 下一个轮次
     */
    @Override
    public Result<Integer> getNextRound(Long recruitmentId) {
        Integer maxRound = interviewMapper.getMaxRoundByRecruitmentId(recruitmentId);
        int nextRound = maxRound == null ? 1 : maxRound + 1;
        if (nextRound > InterviewConstants.MAX_ROUND) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT_OVER_LIMIT,
                    "The round can't be greater than " + InterviewConstants.MAX_ROUND + ".");
        }
        return Result.success(nextRound);
    }

    /**
     * 检查面试状态
     *
     * @private 内部方法
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 面试不存在
     *              Forbidden.Unavailable: 招新不可用 | 组织不可用
     *
     * @param id 面试编号
     * @return 检查结果
     */
    @Override
    public <T> Result<T> checkInterviewStatus(Long id) {
        // 判断面试是否存在
        Long recruitmentId = interviewMapper.getRecruitmentId(id);
        if (recruitmentId == null) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_EXIST,
                    "The interview does not exist.");
        }

        // 检查招新状态
        Result<T> checkResult = recruitmentService.checkRecruitmentStatus(recruitmentId);
        if (checkResult.isFailure()) {
            return checkResult;
        }

        // 通过检查
        return Result.success();
    }

    /**
     * 获取面试所属的招新
     *
     * @private 内部方法
     *
     * @param id 面试编号
     * @return 面试所属招新的编号，若找不到返回 null
     */
    @Override
    public Long getRecruitmentId(Long id) {
        return interviewMapper.getRecruitmentId(id);
    }

}
