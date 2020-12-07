package com.xiaohuashifu.recruit.external.service.manager;

import com.xiaohuashifu.recruit.external.api.dto.ObjectInfoListDTO;

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
     * @return 上传结果
     */
    boolean putObject(String objectName, byte[] object);

    /**
     * 删除对象
     *
     * @param objectName 对象名，需要完整路径，如 users/avatars/1321.jpg
     * @return 删除结果
     */
    boolean deleteObject(String objectName);

    /**
     * 下载对象
     *
     * @param objectName 对象名，需要完整路径，如 users/avatars/1321.jpg
     * @return 对象，若对象不存在，则返回 null
     */
    byte[] getObject(String objectName);

    /**
     * 获取对象信息
     *
     * @param prefix 对象名前缀
     * @param startMarker 起始对象名，若为空默认
     * @param size 获取条数
     * @return 列举对象结果，可能包含空列表
     */
    ObjectInfoListDTO listObjectInfos(String prefix, String startMarker, int size);
}
