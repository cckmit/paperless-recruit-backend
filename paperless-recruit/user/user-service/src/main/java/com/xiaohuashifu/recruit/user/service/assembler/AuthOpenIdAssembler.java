package com.xiaohuashifu.recruit.user.service.assembler;

import com.xiaohuashifu.recruit.user.api.dto.AuthOpenIdDTO;
import com.xiaohuashifu.recruit.user.service.do0.AuthOpenIdDO;
import org.mapstruct.Mapper;

/**
 * 描述：AuthOpenId 的装配器
 *
 * @author xhsf
 * @create 2021/1/26 13:02
 */
@Mapper(componentModel = "spring")
public interface AuthOpenIdAssembler {

    AuthOpenIdDTO authOpenIdDOToAuthOpenIdDTO(AuthOpenIdDO authOpenIdDO);

}
