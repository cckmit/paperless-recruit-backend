package com.xiaohuashifu.recruit.user.service.dao;

import com.xiaohuashifu.recruit.user.api.query.UserQuery;
import com.xiaohuashifu.recruit.user.service.do0.UserDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 描述：用户表数据库映射层
 *
 * @author: xhsf
 * @create: 2020/10/30 15:05
 */
public interface UserMapper {

    int insertUser(UserDO user);

    UserDO getUser(Long id);

    List<UserDO> listUsers(UserQuery query);

    UserDO getUserByUsername(String username);

    UserDO getUserByUsernameOrPhoneOrEmail(String usernameOrPhoneOrEmail);

    UserDO getUserByPhone(String phone);

    UserDO getUserByEmail(String email);

    int count(Long id);

    int countByUsername(String username);

    int countByPhone(String phone);

    int countByEmail(String email);

    int updateUsername(@Param("id") Long id, @Param("username") String username);

    int updatePhone(@Param("id") Long id, @Param("phone") String phone);

    int updateEmail(@Param("id") Long id, @Param("email") String email);

    int updatePassword(@Param("id") Long id, @Param("password") String password);

    int updatePasswordByEmail(@Param("email") String email, @Param("password") String password);

    int updatePasswordByPhone(@Param("phone") String phone, @Param("password") String password);

    int updateAvailable(@Param("id") Long id, @Param("available") Boolean available);

}
