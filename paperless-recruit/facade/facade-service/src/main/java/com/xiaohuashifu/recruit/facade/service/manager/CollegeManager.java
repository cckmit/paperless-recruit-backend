package com.xiaohuashifu.recruit.facade.service.manager;

import com.xiaohuashifu.recruit.facade.service.vo.CollegeVO;

/**
 * 描述：学院管理器
 *
 * @author xhsf
 * @create 2021/1/9 13:05
 */
public interface CollegeManager {

    /**
     * 获取学院
     *
     * @param collegeId 学院编号
     * @return 学院
     */
    CollegeVO getCollege(Long collegeId);

}
