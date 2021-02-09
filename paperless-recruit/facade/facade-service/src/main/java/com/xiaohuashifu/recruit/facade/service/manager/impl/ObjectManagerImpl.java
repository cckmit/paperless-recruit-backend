package com.xiaohuashifu.recruit.facade.service.manager.impl;

import com.xiaohuashifu.recruit.common.util.UuidUtils;
import com.xiaohuashifu.recruit.facade.service.assembler.ObjectInfoAssembler;
import com.xiaohuashifu.recruit.facade.service.manager.ObjectManager;
import com.xiaohuashifu.recruit.facade.service.vo.ObjectInfoVO;
import com.xiaohuashifu.recruit.oss.api.request.PreUploadObjectRequest;
import com.xiaohuashifu.recruit.oss.api.service.ObjectStorageService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 描述：Oss 管理器实现
 *
 * @author xhsf
 * @create 2021/1/30 16:00
 */
@Component
public class ObjectManagerImpl implements ObjectManager {

    @Reference
    private ObjectStorageService objectStorageService;

    private final ObjectInfoAssembler objectInfoAssembler;

    public ObjectManagerImpl(ObjectInfoAssembler objectInfoAssembler) {
        this.objectInfoAssembler = objectInfoAssembler;
    }

    @Override
    public ObjectInfoVO preUploadObject(Long userId, MultipartFile object, String baseObjectName) throws IOException {
        PreUploadObjectRequest request = PreUploadObjectRequest.builder()
                .object(object.getBytes())
                .originalName(object.getOriginalFilename())
                .objectName(generateObjectName(baseObjectName, object.getOriginalFilename()))
                .userId(userId)
                .build();
        return objectInfoAssembler.objectInfoResponseToObjectInfoVO(objectStorageService.preUploadObject(request));
    }

    /**
     * 生成对象名
     *
     * @param baseObjectName 基础对象名
     * @param originalObjectName 原始对象名
     * @return 对象名
     */
    private String generateObjectName(String baseObjectName, String originalObjectName) {
        return baseObjectName + UuidUtils.randomUuidTrim() + originalObjectName;
    }

}
