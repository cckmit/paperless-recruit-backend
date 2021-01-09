package com.xiaohuashifu.recruit.facade.service.manager;

import com.xiaohuashifu.recruit.facade.service.vo.MajorVO;

/**
 * 描述：专业管理器
 *
 * @author xhsf
 * @create 2021/1/9 13:05
 */
public interface MajorManager {

    /**
     * 获取专业
     *
     * @param majorId 专业编号
     * @return 专业
     */
    MajorVO getMajor(Long majorId);

}
