package com.xiaohuashifu.recruit.facade.service.assembler;

import com.xiaohuashifu.recruit.facade.service.vo.ApplicationFormVO;
import com.xiaohuashifu.recruit.registration.api.dto.ApplicationFormDTO;
import org.mapstruct.Mapper;

/**
 * 描述：ApplicationForm 的装配器
 *
 * @author xhsf
 * @create 2021/1/9 13:02
 */
@Mapper(componentModel = "spring")
public interface ApplicationFormAssembler {

    ApplicationFormVO applicationFormDTOToApplicationFormVO(ApplicationFormDTO applicationFormDTO);

}
