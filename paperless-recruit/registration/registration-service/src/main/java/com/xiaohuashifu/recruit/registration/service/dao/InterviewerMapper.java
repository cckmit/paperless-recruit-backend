package com.xiaohuashifu.recruit.registration.service.dao;

import com.xiaohuashifu.recruit.registration.service.do0.InterviewerDO;
import org.apache.ibatis.annotations.Param;

/**
 * 描述：面试官数据层映射
 *
 * @author xhsf
 * @create 2021/1/4 20:47
 */
public interface InterviewerMapper {

    int insertInterviewer(InterviewerDO interviewerDO);

    InterviewerDO getInterviewer(Long id);

    Long getOrganizationId(Long id);

    Long getOrganizationMemberId(Long id);

    int count(Long id);

    int countByOrganizationMemberId(Long organizationMemberId);

    int updateAvailable(@Param("id") Long id, @Param("available") Boolean available);

}
