package com.xiaohuashifu.recruit.oss.service.manager;

import com.xiaohuashifu.recruit.common.exception.InternalServiceException;

/**
 * 描述：对象存储相关服务封装
 *
 * @author xhsf
 * @create 2020/12/7 20:42
 */
public interface ObjectStorageManager {

    /**
     * 上传对象
     *
     * @param objectName 对象名，需要完整路径，如 users/avatars/1321.jpg
     * @param object 对象
     */
    void putObject(String objectName, byte[] object) throws InternalServiceException;

    /**
     * 删除对象
     *
     * @param objectName 对象名，需要完整路径，如 users/avatars/1321.jpg
     */
    void deleteObject(String objectName) throws InternalServiceException;

}
