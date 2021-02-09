package com.xiaohuashifu.recruit.facade.service.manager.impl;

import com.xiaohuashifu.recruit.facade.service.assembler.CollegeAssembler;
import com.xiaohuashifu.recruit.facade.service.manager.CollegeManager;
import com.xiaohuashifu.recruit.facade.service.vo.CollegeVO;
import com.xiaohuashifu.recruit.user.api.service.CollegeService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

/**
 * 描述：学院管理器
 *
 * @author xhsf
 * @create 2021/1/9 13:05
 */
@Component
public class CollegeManagerImpl implements CollegeManager {

    private final CollegeAssembler collegeAssembler;

    @Reference
    private CollegeService collegeService;

    public CollegeManagerImpl(CollegeAssembler collegeAssembler) {
        this.collegeAssembler = collegeAssembler;
    }

    /**
     * 获取学院
     *
     * @param collegeId 学院编号
     * @return 学院
     */
    @Override
    public CollegeVO getCollege(Long collegeId) {
        return collegeAssembler.collegeDTO2CollegeVO(collegeService.getCollege(collegeId));
    }
    
}
