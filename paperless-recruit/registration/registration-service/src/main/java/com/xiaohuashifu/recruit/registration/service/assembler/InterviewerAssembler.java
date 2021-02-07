package com.xiaohuashifu.recruit.registration.service.assembler;

import com.xiaohuashifu.recruit.registration.api.dto.InterviewerDTO;
import com.xiaohuashifu.recruit.registration.service.do0.InterviewerDO;
import org.mapstruct.Mapper;

/**
 * 描述：面试官装配器
 *
 * @author xhsf
 * @create 2021/1/4 22:14
 */
@Mapper(componentModel = "spring")
public interface InterviewerAssembler {

    InterviewerDTO toDTO(InterviewerDO interviewerDO);

}
