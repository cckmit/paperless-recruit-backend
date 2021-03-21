package com.xiaohuashifu.recruit.facade.service.assembler;

import com.xiaohuashifu.recruit.facade.service.assembler.translator.PathToUrl;
import com.xiaohuashifu.recruit.facade.service.assembler.translator.UrlTranslator;
import com.xiaohuashifu.recruit.facade.service.assembler.translator.impl.UrlTranslatorImpl;
import com.xiaohuashifu.recruit.facade.service.vo.ApplicationFormVO;
import com.xiaohuashifu.recruit.registration.api.dto.ApplicationFormDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * 描述：ApplicationForm 的装配器
 *
 * @author xhsf
 * @create 2021/1/9 13:02
 */
@Mapper(uses = UrlTranslatorImpl.class, componentModel = "spring")
public interface ApplicationFormAssembler {

    @Mapping(target = "avatarUrl", qualifiedBy = {UrlTranslator.class, PathToUrl.class})
    @Mapping(target = "attachmentUrl", qualifiedBy = {UrlTranslator.class, PathToUrl.class})
    ApplicationFormVO applicationFormDTOToApplicationFormVO(ApplicationFormDTO applicationFormDTO);

}
