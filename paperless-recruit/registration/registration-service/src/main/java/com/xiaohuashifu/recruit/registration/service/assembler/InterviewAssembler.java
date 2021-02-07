package com.xiaohuashifu.recruit.registration.service.assembler;

import com.xiaohuashifu.recruit.registration.api.dto.InterviewDTO;
import com.xiaohuashifu.recruit.registration.service.do0.InterviewDO;
import org.mapstruct.Mapper;

/**
 * 描述：面试装配器
 *
 * @author xhsf
 * @create 2021/1/4 22:14
 */
@Mapper(componentModel = "spring")
public interface InterviewAssembler {

    InterviewDTO toDTO(InterviewDO interviewDO);

}
