package com.xiaohuashifu.recruit.facade.service.manager.impl;

import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.facade.service.assembler.CollegeAssembler;
import com.xiaohuashifu.recruit.facade.service.manager.CollegeManager;
import com.xiaohuashifu.recruit.facade.service.vo.CollegeVO;
import com.xiaohuashifu.recruit.user.api.dto.CollegeDTO;
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

    private final CollegeAssembler collegeAssembler = CollegeAssembler.INSTANCE;

    @Reference
    private CollegeService collegeService;

    /**
     * 获取学院
     *
     * @param collegeId 学院编号
     * @return 学院
     */
    @Override
    public CollegeVO getCollege(Long collegeId) {
        Result<CollegeDTO> getCollegeResult = collegeService.getCollege(collegeId);
        CollegeDTO collegeDTO = getCollegeResult.getData();
        if (collegeDTO == null) {
            return new CollegeVO();
        }
        return collegeAssembler.collegeDTO2CollegeVO(collegeDTO);
    }
}
