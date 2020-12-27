package com.xiaohuashifu.recruit.user.service.dao;

import com.xiaohuashifu.recruit.user.api.query.MajorQuery;
import com.xiaohuashifu.recruit.user.service.do0.MajorDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 描述：专业表数据库映射层
 *
 * @author: xhsf
 * @create: 2020/10/30 15:05
 */
public interface MajorMapper {

    int insertMajor(MajorDO majorDO);

    MajorDO getMajor(Long id);

    Boolean getDeactivated(Long id);

    List<MajorDO> listMajorsByCollegeId(Long collegeId);

    List<MajorDO> listMajors(MajorQuery query);

    int countByMajorName(String majorName);

    int updateMajorName(@Param("id") Long id, @Param("majorName") String majorName);

    int updateDeactivated(@Param("id") Long id, @Param("deactivated") Boolean deactivated);

    int updateDeactivatedByCollegeId(@Param("collegeId") Long collegeId, @Param("deactivated") Boolean deactivated);
}
