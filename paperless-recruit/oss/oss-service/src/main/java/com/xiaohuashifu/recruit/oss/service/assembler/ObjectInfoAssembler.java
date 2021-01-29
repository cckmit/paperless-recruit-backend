package com.xiaohuashifu.recruit.oss.service.assembler;

import com.xiaohuashifu.recruit.oss.api.request.PreUploadObjectRequest;
import com.xiaohuashifu.recruit.oss.api.response.ObjectInfoResponse;
import com.xiaohuashifu.recruit.oss.service.do0.ObjectInfoDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * 描述：ObjectInfo 的装配器
 *
 * @author xhsf
 * @create 2021/1/26 13:02
 */
@Mapper(componentModel = "spring")
public interface ObjectInfoAssembler {

    ObjectInfoResponse objectInfoDOToObjectInfoResponse(ObjectInfoDO objectInfoDO);

    @Mapping(target = "size", expression = "java(request.getObject().length)")
    ObjectInfoDO preUploadObjectRequestToObjectInfoDO(PreUploadObjectRequest request);
}
