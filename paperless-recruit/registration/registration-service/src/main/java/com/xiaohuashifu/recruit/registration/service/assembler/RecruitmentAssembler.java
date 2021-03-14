package com.xiaohuashifu.recruit.registration.service.assembler;

import com.xiaohuashifu.recruit.registration.api.dto.RecruitmentDTO;
import com.xiaohuashifu.recruit.registration.api.request.CreateRecruitmentRequest;
import com.xiaohuashifu.recruit.registration.api.request.UpdateRecruitmentRequest;
import com.xiaohuashifu.recruit.registration.service.do0.RecruitmentDO;
import org.mapstruct.Mapper;

/**
 * 描述：RecruitmentAssembler
 *
 * @author xhsf
 * @create 2021/1/1 22:11
 */
@Mapper(componentModel = "spring")
public interface RecruitmentAssembler {

    RecruitmentDTO recruitmentDOToRecruitmentDTO(RecruitmentDO recruitmentDO);

    RecruitmentDO createRecruitmentRequestToRecruitmentDO(CreateRecruitmentRequest createRecruitmentRequest);

    RecruitmentDO updateRecruitmentRequestToRecruitmentDO(UpdateRecruitmentRequest updateRecruitmentRequest);

}
