package com.xiaohuashifu.recruit.user.service.assembler;

import com.xiaohuashifu.recruit.user.api.dto.CollegeDTO;
import com.xiaohuashifu.recruit.user.service.do0.CollegeDO;
import org.mapstruct.Mapper;

/**
 * 描述：College 的装配器
 *
 * @author xhsf
 * @create 2021/1/26 13:02
 */
@Mapper(componentModel = "spring")
public interface CollegeAssembler {

    CollegeDTO collegeDOToCollegeDTO(CollegeDO collegeDO);

}
