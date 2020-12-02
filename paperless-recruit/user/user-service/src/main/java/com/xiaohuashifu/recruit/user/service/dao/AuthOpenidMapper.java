package com.xiaohuashifu.recruit.user.service.dao;

import com.xiaohuashifu.recruit.common.constant.AppEnum;
import com.xiaohuashifu.recruit.user.service.do0.AuthOpenidDO;
import org.apache.ibatis.annotations.Param;

/**
 * 描述：AuthOpenid 数据库映射层
 *
 * @author: xhsf
 * @create: 2020/10/30 15:05
 */
public interface AuthOpenidMapper {

    int insertAuthOpenid(AuthOpenidDO authOpenidDO);

    AuthOpenidDO getAuthOpenid(Long id);

    String getOpenidByAppNameAndUserId(@Param("appName") AppEnum appName, @Param("userId") Long userId);

    Long getIdByAppNameAndOpenid(@Param("appName") AppEnum appName, @Param("openid") String openid);

    int countByUserIdAndAppName(@Param("userId") Long userId, @Param("appName") AppEnum appName);

    int countByAppNameAndOpenid(@Param("appName") AppEnum appName, @Param("openid") String openid);

}
