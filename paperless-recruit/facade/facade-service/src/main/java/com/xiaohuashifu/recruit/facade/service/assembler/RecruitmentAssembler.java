package com.xiaohuashifu.recruit.facade.service.assembler;

import com.xiaohuashifu.recruit.facade.service.request.CreateRecruitmentRequest;
import com.xiaohuashifu.recruit.facade.service.request.UpdateRecruitmentRequest;
import com.xiaohuashifu.recruit.facade.service.vo.RecruitmentVO;
import com.xiaohuashifu.recruit.registration.api.dto.RecruitmentDTO;
import org.mapstruct.Mapper;

/**
 * 描述：Recruitment 的装配器
 *
 * @author xhsf
 * @create 2021/1/9 13:02
 */
@Mapper(componentModel = "spring")
public interface RecruitmentAssembler {

    RecruitmentVO recruitmentDTOToRecruitmentVO(RecruitmentDTO recruitmentDTO);

    com.xiaohuashifu.recruit.registration.api.request.CreateRecruitmentRequest
            createRecruitmentRequestToCreateRecruitmentRequest(CreateRecruitmentRequest createRecruitmentRequest);

    com.xiaohuashifu.recruit.registration.api.request.UpdateRecruitmentRequest
    updateRecruitmentRequestToUpdateRecruitmentRequest(UpdateRecruitmentRequest updateRecruitmentRequest);

}
