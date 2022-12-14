package com.xiaohuashifu.recruit.facade.service.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.xiaohuashifu.recruit.facade.service.authorize.UserContext;
import com.xiaohuashifu.recruit.facade.service.manager.ObjectManager;
import com.xiaohuashifu.recruit.facade.service.vo.ObjectInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 描述：对象控制器
 *
 * @author xhsf
 * @create 2021/1/30 01:09
 */
@ApiSupport(author = "XHSF")
@Api(tags = "对象")
@RestController
public class ObjectController {

    private final ObjectManager objectManager;

    private final UserContext userContext;

    public ObjectController(ObjectManager objectManager, UserContext userContext) {
        this.objectManager = objectManager;
        this.userContext = userContext;
    }

    @ApiOperation(value = "预上传对象")
    @PostMapping("/objects")
    @ResponseStatus(HttpStatus.CREATED)
    public ObjectInfoVO preUploadObject(
            @ApiParam(value = "对象", type = "object") MultipartFile object,
            @ApiParam(value = "基础对象名", example = "users/avatars/") String baseObjectName) throws IOException {
        return objectManager.preUploadObject(userContext.getUserId(), object, baseObjectName);
    }

}
