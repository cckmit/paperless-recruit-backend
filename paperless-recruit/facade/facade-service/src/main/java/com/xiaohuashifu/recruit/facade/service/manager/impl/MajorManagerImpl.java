package com.xiaohuashifu.recruit.facade.service.manager.impl;

import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.facade.service.assembler.MajorAssembler;
import com.xiaohuashifu.recruit.facade.service.manager.MajorManager;
import com.xiaohuashifu.recruit.facade.service.vo.MajorVO;
import com.xiaohuashifu.recruit.user.api.dto.MajorDTO;
import com.xiaohuashifu.recruit.user.api.service.MajorService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

/**
 * 描述：专业管理器
 *
 * @author xhsf
 * @create 2021/1/9 13:05
 */
@Component
public class MajorManagerImpl implements MajorManager {

    private final MajorAssembler majorAssembler = MajorAssembler.INSTANCE;

    @Reference
    private MajorService majorService;

    /**
     * 获取专业
     *
     * @param majorId 专业编号
     * @return 专业
     */
    public MajorVO getMajor(Long majorId) {
        Result<MajorDTO> getMajorResult = majorService.getMajor(majorId);
        MajorDTO majorDTO = getMajorResult.getData();
        if (majorDTO == null) {
            return new MajorVO();
        }
        return majorAssembler.majorDTO2MajorVO(majorDTO);
    }
}
