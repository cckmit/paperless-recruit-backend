package com.xiaohuashifu.recruit.registration.service.service;

import com.xiaohuashifu.recruit.common.aspect.annotation.DistributedLock;
import com.xiaohuashifu.recruit.common.exception.NotFoundServiceException;
import com.xiaohuashifu.recruit.common.exception.unprocessable.OverLimitServiceException;
import com.xiaohuashifu.recruit.registration.api.constant.InterviewConstants;
import com.xiaohuashifu.recruit.registration.api.dto.InterviewDTO;
import com.xiaohuashifu.recruit.registration.api.request.CreateInterviewRequest;
import com.xiaohuashifu.recruit.registration.api.request.UpdateInterviewRequest;
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

    private final InterviewAssembler interviewAssembler;

    private final InterviewMapper interviewMapper;

    @Reference
    private RecruitmentService recruitmentService;

    /**
     * 创建面试的锁定键模式，{0}是招新编号
     */
    private static final String CREATE_INTERVIEW_LOCK_KEY_PATTERN = "interview:create-interview:recruitment-id:{0}";

    public InterviewServiceImpl(InterviewAssembler interviewAssembler, InterviewMapper interviewMapper) {
        this.interviewAssembler = interviewAssembler;
        this.interviewMapper = interviewMapper;
    }

    @DistributedLock(value = CREATE_INTERVIEW_LOCK_KEY_PATTERN, parameters = "#{#recruitmentId}",
            errorMessage = "Failed to acquire create interview lock.")
    @Override
    public InterviewDTO createInterview(CreateInterviewRequest request) {
        // 检查招新状态
        recruitmentService.checkRecruitmentStatus(request.getRecruitmentId());

        // 获取下一个轮次
        Integer nextRound = getNextRound(request.getRecruitmentId());

        // 插入数据库
        InterviewDO interviewDOForInsert = InterviewDO.builder().recruitmentId(request.getRecruitmentId())
                .title(request.getTitle()).round(nextRound).build();
        interviewMapper.insert(interviewDOForInsert);

        // 获取创建的面试
        return getInterview(interviewDOForInsert.getId());
    }

    @Override
    public InterviewDTO getInterview(Long id) {
        InterviewDO interviewDO = interviewMapper.selectById(id);
        if (interviewDO == null) {
            throw new NotFoundServiceException("interview", "id", id);
        }
        return interviewAssembler.interviewDOToInterviewDTO(interviewDO);
    }

    @Override
    public InterviewDTO updateInterview(UpdateInterviewRequest request) {
        // 检查面试状态
        checkInterviewStatus(request.getId());

        // 更新标题
        InterviewDO interviewDOForUpdate = interviewAssembler.updateInterviewRequestToInterviewDO(request);
        interviewMapper.updateById(interviewDOForUpdate);

        // 获取更新后的面试
        return getInterview(request.getId());
    }

    @Override
    public Integer getNextRound(Long recruitmentId) {
        Integer maxRound = interviewMapper.getMaxRoundByRecruitmentId(recruitmentId);
        int nextRound = maxRound == null ? 1 : maxRound + 1;
        if (nextRound > InterviewConstants.MAX_ROUND) {
            throw new OverLimitServiceException("The round can't be greater than " + InterviewConstants.MAX_ROUND + ".");
        }
        return nextRound;
    }

    @Override
    public InterviewDTO checkInterviewStatus(Long id) {
        // 判断面试是否存在
        InterviewDTO interviewDTO = getInterview(id);

        recruitmentService.checkRecruitmentStatus(interviewDTO.getRecruitmentId());

        // 通过检查
        return interviewDTO;
    }

}
