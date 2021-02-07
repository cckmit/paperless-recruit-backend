package com.xiaohuashifu.recruit.authentication.service.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaohuashifu.recruit.authentication.service.do0.PermittedUrlDO;

/**
 * 描述：PermittedUrlMapper
 *
 * @author xhsf
 * @create 2020/11/27 20:22
 */
public interface PermittedUrlMapper extends BaseMapper<PermittedUrlDO> {

    default PermittedUrlDO selectByUrl(String url) {
        LambdaQueryWrapper<PermittedUrlDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PermittedUrlDO::getUrl, url);
        return selectOne(wrapper);
    }

}
