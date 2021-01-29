package com.xiaohuashifu.recruit.oss.service.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaohuashifu.recruit.common.query.QueryResult;
import com.xiaohuashifu.recruit.oss.api.request.ListObjectInfosRequest;
import com.xiaohuashifu.recruit.oss.service.do0.ObjectInfoDO;

/**
 * 描述：对象信息映射
 *
 * @author xhsf
 * @create 2021/1/29 01:36
 */
public interface ObjectInfoMapper extends BaseMapper<ObjectInfoDO> {

    default ObjectInfoDO selectByObjectName(String objectName) {
        QueryWrapper<ObjectInfoDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("object_name", objectName);
        return selectOne(queryWrapper);
    }

    default QueryResult<ObjectInfoDO> selectList(ListObjectInfosRequest request) {
        QueryWrapper<ObjectInfoDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.likeRight("object_name", request.getBaseObjectName());
        Page<ObjectInfoDO> page = selectPage(
                new Page<>(request.getPageNum(), request.getPageSize(), true), queryWrapper);
        return new QueryResult<>((int) page.getTotal(), page.getRecords());
    }

}
