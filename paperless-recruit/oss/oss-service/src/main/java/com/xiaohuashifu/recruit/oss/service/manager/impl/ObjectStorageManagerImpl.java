package com.xiaohuashifu.recruit.oss.service.manager.impl;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSException;
import com.xiaohuashifu.recruit.common.exception.InternalServiceException;
import com.xiaohuashifu.recruit.oss.service.manager.ObjectStorageManager;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;

/**
 * 描述：对象存储相关服务封装
 *
 * @author xhsf
 * @create 2020/12/7 21:26
 */
@Component
public class ObjectStorageManagerImpl implements ObjectStorageManager {

    private final OSS oss;

    /**
     * 存储空间名
     */
    private static final String BUCKET_NAME = "scau-recruit";

    public ObjectStorageManagerImpl(OSS oss) {
        this.oss = oss;
    }

    /**
     * 上传对象
     *
     * @param objectName 对象名，需要完整路径，如 users/avatars/1321.jpg
     * @param object 对象
     */
    @Override
    public void putObject(String objectName, byte[] object) {
        try {
            oss.putObject(BUCKET_NAME, objectName, new ByteArrayInputStream(object));
        } catch (OSSException | ClientException e) {
            throw new InternalServiceException("Put object error.");
        }
    }

    /**
     * 删除对象
     *
     * @param objectName 对象名，需要完整路径，如 users/avatars/1321.jpg
     */
    @Override
    public void deleteObject(String objectName) {
        try {
            oss.deleteObject(BUCKET_NAME, objectName);
        } catch (OSSException | ClientException e) {
            throw new InternalServiceException("Delete object error.");
        }
    }

}
