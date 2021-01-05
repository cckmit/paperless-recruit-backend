package com.xiaohuashifu.recruit.registration.service.assembler;

import com.xiaohuashifu.recruit.registration.api.dto.InterviewEvaluationDTO;
import com.xiaohuashifu.recruit.registration.service.do0.InterviewEvaluationDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 描述：面试评价装配器
 *
 * @author xhsf
 * @create 2021/1/4 22:14
 */
@Mapper
public interface InterviewEvaluationAssembler {

    InterviewEvaluationAssembler INSTANCES = Mappers.getMapper(InterviewEvaluationAssembler.class);

    InterviewEvaluationDTO toDTO(InterviewEvaluationDO interviewEvaluationDO);

}
