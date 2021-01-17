package com.xiaohuashifu.recruit.facade.service.assembler;

import com.xiaohuashifu.recruit.facade.service.vo.CollegeVO;
import com.xiaohuashifu.recruit.user.api.dto.CollegeDTO;
import org.mapstruct.Mapper;

/**
 * 描述：College 的装配器
 *
 * @author xhsf
 * @create 2021/1/9 13:02
 */
@Mapper(componentModel = "spring")
public interface CollegeAssembler {

    CollegeVO collegeDTO2CollegeVO(CollegeDTO collegeDTO);

}
