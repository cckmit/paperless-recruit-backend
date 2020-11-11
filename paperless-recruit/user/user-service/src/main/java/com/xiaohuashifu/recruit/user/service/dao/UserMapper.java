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

    UserDO getUserByPhone(String phone);

    UserDO getUserByEmail(String email);

    int countUserByUsername(String username);

    int countUserByPhone(String phone);

    int countUserByEmail(String email);

    int updateUsername(@Param("id") Long id, @Param("username") String username);

    int updatePhone(@Param("id") Long id, @Param("phone") String phone);

    int updateEmail(@Param("id") Long id, @Param("email") String email);

    int updatePassword(@Param("id") Long id, @Param("password") String password);

    int updateAvailable(@Param("id") Long id, @Param("available") Boolean available);
}
