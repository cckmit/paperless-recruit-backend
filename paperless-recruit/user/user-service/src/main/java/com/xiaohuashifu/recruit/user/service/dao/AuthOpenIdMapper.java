package com.xiaohuashifu.recruit.user.service.dao;

import com.xiaohuashifu.recruit.common.constant.AppEnum;
import com.xiaohuashifu.recruit.user.service.do0.AuthOpenIdDO;
import org.apache.ibatis.annotations.Param;

/**
 * 描述：AuthOpenId 数据库映射层
 *
 * @author: xhsf
 * @create: 2020/10/30 15:05
 */
public interface AuthOpenIdMapper {

    int insertAuthOpenId(AuthOpenIdDO authOpenIdDO);

    AuthOpenIdDO getAuthOpenId(Long id);

    String getOpenIdByAppNameAndUserId(@Param("appName") AppEnum appName, @Param("userId") Long userId);

    Long getIdByAppNameAndOpenId(@Param("appName") AppEnum appName, @Param("openId") String openId);

    int countByUserIdAndAppName(@Param("userId") Long userId, @Param("appName") AppEnum appName);

    int countByAppNameAndOpenId(@Param("appName") AppEnum appName, @Param("openId") String openId);

}
