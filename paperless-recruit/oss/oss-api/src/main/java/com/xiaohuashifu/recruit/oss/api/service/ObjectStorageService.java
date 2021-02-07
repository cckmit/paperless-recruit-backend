package com.xiaohuashifu.recruit.oss.api.service;

import com.xiaohuashifu.recruit.common.exception.NotFoundServiceException;
import com.xiaohuashifu.recruit.common.exception.ServiceException;
import com.xiaohuashifu.recruit.common.query.QueryResult;
import com.xiaohuashifu.recruit.oss.api.request.ListObjectInfosRequest;
import com.xiaohuashifu.recruit.oss.api.request.PreUploadObjectRequest;
import com.xiaohuashifu.recruit.oss.api.response.ObjectInfoResponse;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * 描述：对象存储服务
 *
 * @author xhsf
 * @create 2021/1/28 21:09
 */
public interface ObjectStorageService {

    /**
     * 预上传对象
     *
     * @param request 请求
     * @return 预上传结果
     */
    ObjectInfoResponse preUploadObject(@NotNull PreUploadObjectRequest request) throws ServiceException;

    /**
     * 删除对象
     *
     * @param objectName 对象名，需要完整路径，如 users/avatars/1321.jpg
     */
    void deleteObject(@NotNull String objectName) throws ServiceException;

    /**
     * 删除对象
     *
     * @param id 对象编号
     */
    void deleteObject(@NotNull @Positive Long id) throws ServiceException;

    /**
     * 获取对象信息
     *
     * @param objectName 对象名，需要完整路径，如 users/avatars/1321.jpg
     * @return 响应
     */
    ObjectInfoResponse getObjectInfo(@NotNull String objectName) throws NotFoundServiceException;

    /**
     * 获取对象信息
     *
     * @param id 对象编号
     * @return 响应
     */
    ObjectInfoResponse getObjectInfo(@NotNull @Positive Long id) throws NotFoundServiceException;

    /**
     * 列出对象信息
     *
     * @param request 请求
     * @return 列出对象信息结果，可能返回空列表
     */
    QueryResult<ObjectInfoResponse> listObjectInfos(@NotNull ListObjectInfosRequest request);

    /**
     * 链接对象，对象若不链接会被自动删除
     *
     * @param objectName 对象名
     * @return 链接后的对象信息
     */
    ObjectInfoResponse linkObject(@NotNull String objectName) throws ServiceException;

    /**
     * 链接对象，对象若不链接会被自动删除
     *
     * @param id 对象编号
     * @return 链接后的对象信息
     */
    ObjectInfoResponse linkObject(@NotNull @Positive Long id) throws ServiceException;

}
