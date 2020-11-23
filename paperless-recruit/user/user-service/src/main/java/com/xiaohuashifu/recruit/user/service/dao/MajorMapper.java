package com.xiaohuashifu.recruit.user.service.dao;

import com.xiaohuashifu.recruit.user.api.query.MajorQuery;
import com.xiaohuashifu.recruit.user.service.pojo.do0.MajorDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 描述：数据库映射层
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/10/30 15:05
 */
public interface MajorMapper {

    int saveMajor(MajorDO majorDO);

    int delete(Long id);

    MajorDO getMajor(Long id);

    List<MajorDO> getMajorByCollegeId(Long collegeId);

    List<MajorDO> getMajorByQuery(MajorQuery query);

    int count(Long id);

    int countByCollegeId(Long collegeId);

    int countByCollegeIdAndMajorName(
            @Param("collegeId") Long collegeId, @Param("majorName") String majorName);

    int updateMajorName(@Param("id") Long id, @Param("majorName") String majorName);

}
