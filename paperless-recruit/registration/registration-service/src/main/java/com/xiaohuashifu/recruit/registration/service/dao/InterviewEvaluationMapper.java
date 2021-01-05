package com.xiaohuashifu.recruit.registration.service.dao;

import com.xiaohuashifu.recruit.registration.service.do0.InterviewEvaluationDO;
import org.apache.ibatis.annotations.Param;

/**
 * 描述：面试评价数据层映射
 *
 * @author xhsf
 * @create 2021/1/4 20:47
 */
public interface InterviewEvaluationMapper {

    int insertInterviewEvaluation(InterviewEvaluationDO interviewEvaluationDO);

    InterviewEvaluationDO getInterviewEvaluation(Long id);

    Long getInterviewerId(Long id);

    Long getInterviewFormId(Long id);

    int countByInterviewFormIdAndInterviewerId(@Param("interviewFormId") Long interviewFormId,
                                               @Param("interviewerId") Long interviewerId);

    int updateEvaluation(@Param("id") Long id, @Param("evaluation") String evaluation);

}
