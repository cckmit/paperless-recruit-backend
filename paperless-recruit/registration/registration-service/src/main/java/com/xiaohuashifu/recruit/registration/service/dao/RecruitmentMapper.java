package com.xiaohuashifu.recruit.registration.service.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaohuashifu.recruit.registration.service.do0.RecruitmentDO;

/**
 * 描述：招新数据库映射层
 *
 * @author xhsf
 * @create 2020/12/26 20:33
 */
public interface RecruitmentMapper extends BaseMapper<RecruitmentDO> {

    int increaseNumberOfApplicationForms(Long id);

}

