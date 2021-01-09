package com.xiaohuashifu.recruit.facade.service.assembler;

import com.xiaohuashifu.recruit.facade.service.vo.MajorVO;
import com.xiaohuashifu.recruit.user.api.dto.MajorDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 描述：Major 的装配器
 *
 * @author xhsf
 * @create 2021/1/9 13:02
 */
@Mapper
public interface MajorAssembler {

    MajorAssembler INSTANCE = Mappers.getMapper(MajorAssembler.class);

    MajorVO majorDTO2MajorVO(MajorDTO majorDTO);

}
