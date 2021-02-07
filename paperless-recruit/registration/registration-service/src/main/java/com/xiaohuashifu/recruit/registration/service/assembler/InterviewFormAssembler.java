package com.xiaohuashifu.recruit.registration.service.assembler;

import com.xiaohuashifu.recruit.registration.api.dto.InterviewFormDTO;
import com.xiaohuashifu.recruit.registration.service.do0.InterviewFormDO;
import org.mapstruct.Mapper;

/**
 * 描述：面试表装配器
 *
 * @author xhsf
 * @create 2021/1/4 22:14
 */
@Mapper(componentModel = "spring")
public interface InterviewFormAssembler {

    InterviewFormDTO toDTO(InterviewFormDO interviewFormDO);

}
