package com.xiaohuashifu.recruit.facade.service.manager;

import com.xiaohuashifu.recruit.facade.service.vo.MyVO;

/**
 * 描述：我的页面管理器
 *
 * @author xhsf
 * @create 2021/1/9 12:46
 */
public interface MyManager {

    /**
     * 获取我的页面
     *
     * @param userId 用户编号
     * @return MyVO
     */
    MyVO getPage(Long userId);

}
