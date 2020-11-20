package com.xiaohuashifu.recruit.authentication.service.dao;

import com.xiaohuashifu.recruit.authentication.service.pojo.do0.AuthOpenidDO;
import com.xiaohuashifu.recruit.common.constant.App;
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

    int countByUserIdAndAppName(@Param("userId") Long userId, @Param("appName") App appName);


}
