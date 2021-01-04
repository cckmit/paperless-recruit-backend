package com.xiaohuashifu.recruit.registration.service.service;

import com.xiaohuashifu.recruit.common.aspect.annotation.DistributedLock;
import com.xiaohuashifu.recruit.common.result.Result;
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

    private Result<InterviewDTO> getInterview(Long id) {
        return null;
    }

    private Result<Integer> getNextRound(Long recruitmentId) {
        return null;
    }

    /**
     * 更新面试标题
     *
     * @param id    面试编号
     * @param title 面试标题
     * @return 更新后的面试对象
     * @permission 必须是面试所属的主体
     */
    @Override
    public Result<InterviewDTO> updateTitle(Long id, String title) {
        return null;
    }

    /**
     * 获取面试所属的招新
     *
     * @param id 面试编号
     * @return 面试所属招新的编号，若找不到返回 null
     * @private 内部方法
     */
    @Override
    public Long getRecruitmentId(Long id) {
        return null;
    }

    /**
     * 验证面试的主体
     *
     * @param id     面试编号
     * @param userId 主体编号
     * @return 若是返回 true，不是返回 false
     * @private 内部方法
     */
    @Override
    public Boolean authenticatePrincipal(Long id, Long userId) {
        return null;
    }
}
