package com.xiaohuashifu.recruit.registration.service.dao;

import com.xiaohuashifu.recruit.registration.service.do0.ApplicationFormDO;
import org.apache.ibatis.annotations.Param;

/**
 * 描述：报名表数据库映射层
 *
 * @author xhsf
 * @create 2020/12/26 20:33
 */
public interface ApplicationFormMapper {

    int insertApplicationForm(ApplicationFormDO applicationFormDO);

    ApplicationFormDO getApplicationForm(Long id);

    Long getRecruitmentId(Long id);

    Long getUserId(Long id);

    int countByUserIdAndRecruitmentId(@Param("userId") Long userId, @Param("recruitmentId") Long recruitmentId);

}

