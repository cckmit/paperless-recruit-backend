package com.xiaohuashifu.recruit.facade.service.assembler;

import com.xiaohuashifu.recruit.facade.service.vo.ObjectInfoVO;
import com.xiaohuashifu.recruit.oss.api.response.ObjectInfoResponse;
import org.mapstruct.Mapper;

/**
 * 描述：ObjectInfo 的装配器
 *
 * @author xhsf
 * @create 2021/1/9 13:02
 */
@Mapper(componentModel = "spring")
public interface ObjectInfoAssembler {

    ObjectInfoVO objectInfoResponseToObjectInfoVO(ObjectInfoResponse objectInfoResponse);

}
