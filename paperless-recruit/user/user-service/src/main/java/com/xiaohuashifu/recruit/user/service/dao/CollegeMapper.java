package com.xiaohuashifu.recruit.user.service.dao;

import com.xiaohuashifu.recruit.user.api.query.CollegeQuery;
import com.xiaohuashifu.recruit.user.service.pojo.do0.CollegeDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 描述：数据库映射层
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/10/30 15:05
 */
public interface CollegeMapper {

    int saveCollege(CollegeDO collegeDO);

    int delete(Long id);

    CollegeDO getCollege(Long id);

    List<CollegeDO> getCollegeByQuery(CollegeQuery query);

    int count(Long id);

    int countByCollegeName(String collegeName);

    int updateCollegeName(@Param("id") Long id, @Param("collegeName") String collegeName);

}
