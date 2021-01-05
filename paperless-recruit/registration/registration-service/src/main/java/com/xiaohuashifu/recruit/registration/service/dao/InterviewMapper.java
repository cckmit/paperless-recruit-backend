package com.xiaohuashifu.recruit.registration.service.dao;

import com.xiaohuashifu.recruit.registration.service.do0.InterviewDO;
import org.apache.ibatis.annotations.Param;

/**
 * 描述：面试数据层映射
 *
 * @author xhsf
 * @create 2021/1/4 20:47
 */
public interface InterviewMapper {

    int insertInterview(InterviewDO interviewDO);

    InterviewDO getInterview(Long id);

    Long getRecruitmentId(Long id);

    Integer getMaxRoundByRecruitmentId(Long recruitmentId);

    int updateTitle(@Param("id") Long id, @Param("title") String title);

}
