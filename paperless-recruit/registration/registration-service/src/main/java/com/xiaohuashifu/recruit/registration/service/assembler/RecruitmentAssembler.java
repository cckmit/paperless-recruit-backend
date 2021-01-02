package com.xiaohuashifu.recruit.registration.service.assembler;

import com.xiaohuashifu.recruit.registration.api.dto.RecruitmentDTO;
import com.xiaohuashifu.recruit.registration.service.do0.RecruitmentDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 描述：
 *
 * @author xhsf
 * @create 2021/1/1 22:11
 */
@Mapper
public interface RecruitmentAssembler {

    RecruitmentAssembler INSTANCE = Mappers.getMapper(RecruitmentAssembler.class);

    RecruitmentDTO toDTO(RecruitmentDO recruitmentDO);

}
