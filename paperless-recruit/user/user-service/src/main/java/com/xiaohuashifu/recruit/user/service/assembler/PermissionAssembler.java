package com.xiaohuashifu.recruit.user.service.assembler;

import com.xiaohuashifu.recruit.user.api.dto.PermissionDTO;
import com.xiaohuashifu.recruit.user.api.request.CreatePermissionRequest;
import com.xiaohuashifu.recruit.user.api.request.UpdatePermissionRequest;
import com.xiaohuashifu.recruit.user.service.do0.PermissionDO;
import org.mapstruct.Mapper;

/**
 * 描述：Permission 的装配器
 *
 * @author xhsf
 * @create 2021/1/26 13:02
 */
@Mapper(componentModel = "spring")
public interface PermissionAssembler {

    PermissionDTO permissionDOToPermissionDTO(PermissionDO permissionDO);

    PermissionDO createPermissionRequestToPermissionDO(CreatePermissionRequest createPermissionRequest);

    PermissionDO updatePermissionRequestToPermissionDO(UpdatePermissionRequest updatePermissionRequest);

}
