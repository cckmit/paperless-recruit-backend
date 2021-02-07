package com.xiaohuashifu.recruit.authentication.service.assembler;

import com.xiaohuashifu.recruit.authentication.api.dto.PermittedUrlDTO;
import com.xiaohuashifu.recruit.authentication.service.do0.PermittedUrlDO;
import org.mapstruct.Mapper;

/**
 * 描述：PermittedUrl 的装配器
 *
 * @author xhsf
 * @create 2021/1/26 13:02
 */
@Mapper(componentModel = "spring")
public interface PermittedUrlAssembler {

    PermittedUrlDTO permittedUrlDOToPermittedUrlDTO(PermittedUrlDO permittedUrlDO);

}
