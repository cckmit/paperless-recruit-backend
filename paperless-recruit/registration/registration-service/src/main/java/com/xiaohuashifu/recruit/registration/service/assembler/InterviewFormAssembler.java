package com.xiaohuashifu.recruit.registration.service.assembler;

import com.xiaohuashifu.recruit.registration.api.dto.InterviewFormDTO;
import com.xiaohuashifu.recruit.registration.service.do0.InterviewFormDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 描述：面试表装配器
 *
 * @author xhsf
 * @create 2021/1/4 22:14
 */
@Mapper
public interface InterviewFormAssembler {

    InterviewFormAssembler INSTANCES = Mappers.getMapper(InterviewFormAssembler.class);

    InterviewFormDTO toDTO(InterviewFormDO interviewFormDO);

}
