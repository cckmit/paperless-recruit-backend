package com.xiaohuashifu.recruit.registration.service.dao;

import com.xiaohuashifu.recruit.registration.api.constant.InterviewStatusEnum;
import com.xiaohuashifu.recruit.registration.service.do0.InterviewFormDO;
import org.apache.ibatis.annotations.Param;

/**
 * 描述：面试表数据层映射
 *
 * @author xhsf
 * @create 2021/1/4 20:47
 */
public interface InterviewFormMapper {

    int insertInterviewForm(InterviewFormDO interviewFormDO);

    InterviewFormDO getInterviewForm(Long id);

    Long getInterviewId(Long id);

    String getInterviewStatus(Long id);

    int countByInterviewIdAndApplicationFormId(@Param("interviewId") Long interviewId,
                                               @Param("applicationFormId") Long applicationFormId);

    int updateInterviewTime(@Param("id") Long id, @Param("interviewTime") String interviewTime);

    int updateInterviewLocation(@Param("id") Long id, @Param("interviewLocation") String interviewLocation);

    int updateNote(@Param("id") Long id, @Param("note") String note);

    int updateInterviewStatus(@Param("id") Long id, @Param("oldInterviewStatus") InterviewStatusEnum oldInterviewStatus,
                              @Param("newInterviewStatus") InterviewStatusEnum newInterviewStatus);

}
