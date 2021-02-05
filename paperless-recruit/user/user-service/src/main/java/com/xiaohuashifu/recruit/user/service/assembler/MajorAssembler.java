package com.xiaohuashifu.recruit.user.service.assembler;

import com.xiaohuashifu.recruit.user.api.dto.MajorDTO;
import com.xiaohuashifu.recruit.user.api.request.UpdateMajorRequest;
import com.xiaohuashifu.recruit.user.service.do0.MajorDO;
import org.mapstruct.Mapper;

/**
 * 描述：Major 的装配器
 *
 * @author xhsf
 * @create 2021/1/26 13:02
 */
@Mapper(componentModel = "spring")
public interface MajorAssembler {

    MajorDTO majorDOToMajorDTO(MajorDO majorDO);

    MajorDO updateMajorRequestToMajorDO(UpdateMajorRequest updateMajorRequest);

}
