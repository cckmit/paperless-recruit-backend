package com.xiaohuashifu.recruit.facade.service.assembler;

import com.xiaohuashifu.recruit.facade.service.vo.MajorVO;
import com.xiaohuashifu.recruit.user.api.dto.MajorDTO;
import org.mapstruct.Mapper;

/**
 * 描述：Major 的装配器
 *
 * @author xhsf
 * @create 2021/1/9 13:02
 */
@Mapper(componentModel = "spring")
public interface MajorAssembler {

    MajorVO majorDTO2MajorVO(MajorDTO majorDTO);

}
