package com.xiaohuashifu.recruit.user.service.dao;

import com.xiaohuashifu.recruit.common.constant.App;
import com.xiaohuashifu.recruit.user.service.pojo.do0.AuthOpenidDO;
import org.apache.ibatis.annotations.Param;

/**
 * 描述：数据库映射层
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/10/30 15:05
 */
public interface AuthOpenidMapper {
    int saveAuthOpenid(AuthOpenidDO authOpenidDO);

    AuthOpenidDO getAuthOpenid(Long id);

    String getOpenidByAppNameAndUserId(@Param("appName") App appName, @Param("userId") Long userId);

    Long getIdByAppNameAndOpenid(@Param("appName") App appName, @Param("openid") String openid);

    int countByUserIdAndAppName(@Param("userId") Long userId, @Param("appName") App appName);

    int countByAppNameAndOpenid(@Param("appName") App appName, @Param("openid") String openid);

}
