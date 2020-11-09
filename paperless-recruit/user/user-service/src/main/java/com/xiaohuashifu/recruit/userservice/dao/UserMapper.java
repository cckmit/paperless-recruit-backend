package com.xiaohuashifu.recruit.userservice.dao;

import com.xiaohuashifu.recruit.userservice.pojo.do0.UserDO;

/**
 * 描述：数据库映射层
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/10/30 15:05
 */
public interface UserMapper {
    UserDO getUser(Long id);

    UserDO saveUser(UserDO user);

}
