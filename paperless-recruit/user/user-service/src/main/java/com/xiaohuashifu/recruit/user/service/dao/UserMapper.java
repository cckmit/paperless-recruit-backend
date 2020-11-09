package com.xiaohuashifu.recruit.user.service.dao;

import com.xiaohuashifu.recruit.user.service.pojo.do0.UserDO;
import org.apache.ibatis.annotations.Param;

/**
 * 描述：数据库映射层
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/10/30 15:05
 */
public interface UserMapper {
    int saveUser(UserDO user);

    UserDO getUser(Long id);

    UserDO getUserByUsername(String username);

    int countUserByUsername(String username);

    int updateUsername(@Param("id") Long id, @Param("username") String username);

    int updatePassword(@Param("id") Long id, @Param("password") String password);

    int updateAvailable(@Param("id") Long id, @Param("available") Boolean available);
}
