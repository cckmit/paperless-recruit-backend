package com.xiaohuashifu.recruit.user.dao;

import com.xiaohuashifu.recruit.user.pojo.do0.UserDO;

/**
 * 描述：数据库映射层
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/10/30 15:05
 */
public interface UserMapper {
    UserDO getUser(Long id);

}
