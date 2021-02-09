package com.xiaohuashifu.recruit.registration.service.assembler;

import com.xiaohuashifu.recruit.registration.api.dto.ApplicationFormDTO;
import com.xiaohuashifu.recruit.registration.service.do0.ApplicationFormDO;
import org.mapstruct.Mapper;

/**
 * 描述：报名表装配器
 *
 * @author xhsf
 * @create 2021/1/4 22:14
 */
@Mapper(componentModel = "spring")
public interface ApplicationFormAssembler {

    ApplicationFormDTO applicationFormDOToApplicationFormDTO(
            ApplicationFormDO applicationFormDO);

}
