package com.xiaohuashifu.recruit.external.service.manager.impl;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.xiaohuashifu.recruit.external.api.dto.ObjectInfoDTO;
import com.xiaohuashifu.recruit.external.api.dto.ObjectInfoListDTO;
import com.xiaohuashifu.recruit.external.service.manager.ObjectStorageManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述：对象存储相关服务封装
 *
 * @author xhsf
 * @create 2020/12/7 21:26
 */
@Component
public class ObjectStorageManagerImpl implements ObjectStorageManager {

    private static final Logger logger = LoggerFactory.getLogger(ObjectStorageManagerImpl.class);

    private OSS oss;

    /**
     * 存储空间名
     */
    private static final String BUCKET_NAME = "scau-recruit";

//    public ObjectStorageManagerImpl(OSS oss) {
//        this.oss = oss;
//    }

    /**
     * 上传对象
     *
     * @param objectName 对象名，需要完整路径，如 users/avatars/1321.jpg
     * @param object 对象
     * @return 上传结果
     */
    @Override
    public boolean putObject(String objectName, byte[] object) {
        try {
            OSS oss = new OSSClientBuilder().build("https://oss-cn-guangzhou.aliyuncs.com",
                    "LTAI4GHw6M37rPWbiJWRJtcU", "Oi9UNj1M6OrYSVmSGezu0MJeD8x7Ze");
            oss.putObject(BUCKET_NAME, objectName, new ByteArrayInputStream(object));
            oss.shutdown();
            return true;
        } catch (OSSException | ClientException e) {
            logger.error("Put object failed. objectName={}, objectSize={}, exception={}", objectName, object.length, e);
            return false;
        }
    }

    /**
     * 删除对象
     *
     * @param objectName 对象名，需要完整路径，如 users/avatars/1321.jpg
     * @return 删除结果
     */
    @Override
    public boolean deleteObject(String objectName) {
        try {
            oss.deleteObject(BUCKET_NAME, objectName);
            return true;
        } catch (OSSException | ClientException e) {
            logger.error("Delete object failed. objectName={}, exception={}", objectName, e);
            return false;
        }
    }

    /**
     * 下载对象
     *
     * @param objectName 对象名，需要完整路径，如 users/avatars/1321.jpg
     * @return 对象，若对象不存在，则返回 null
     */
    @Override
    public byte[] getObject(String objectName) {
        try {
            OSSObject object = oss.getObject(BUCKET_NAME, objectName);
            return object.getObjectContent().readAllBytes();
        } catch (OSSException | ClientException | IOException e) {
            logger.error("Get object failed. objectName={}, exception={}", objectName, e);
            return null;
        }
    }

    /**
     * 获取对象信息
     *
     * @param prefix 对象名前缀
     * @param startMarker 起始对象名，若为空默认
     * @param size 获取条数
     * @return 列举对象结果，可能包含空列表
     */
    @Override
    public ObjectInfoListDTO listObjectInfos(String prefix, String startMarker, int size) {
        // 获取对象信息
        ObjectListing objectListing = oss.listObjects(
                new ListObjectsRequest(BUCKET_NAME).withPrefix(prefix).withMarker(startMarker).withMaxKeys(size));

        // 封装成 ObjectInfoListDTO
        List<ObjectInfoDTO> objectInfoDTOList = new ArrayList<>(objectListing.getObjectSummaries().size());
        for (OSSObjectSummary objectSummary : objectListing.getObjectSummaries()) {
            LocalDateTime updateTime =
                    objectSummary.getLastModified().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            String objectName = objectSummary.getKey();
            Long objectSize = objectSummary.getSize();
            ObjectInfoDTO objectInfoDTO = new ObjectInfoDTO(objectName, objectSize, updateTime);
            objectInfoDTOList.add(objectInfoDTO);
        }
        String nextMarker = objectListing.getNextMarker();
        return new ObjectInfoListDTO(nextMarker, objectInfoDTOList);
    }
}
