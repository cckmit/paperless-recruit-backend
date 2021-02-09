package com.xiaohuashifu.recruit.registration.service.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaohuashifu.recruit.registration.service.do0.InterviewDO;

/**
 * 描述：面试数据层映射
 *
 * @author xhsf
 * @create 2021/1/4 20:47
 */
public interface InterviewMapper extends BaseMapper<InterviewDO> {

    Integer getMaxRoundByRecruitmentId(Long recruitmentId);

}
