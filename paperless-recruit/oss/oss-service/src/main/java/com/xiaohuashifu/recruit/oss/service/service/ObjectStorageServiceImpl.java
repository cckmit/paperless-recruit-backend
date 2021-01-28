package com.xiaohuashifu.recruit.oss.service.service;

import com.xiaohuashifu.recruit.common.query.QueryResult;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.oss.api.request.ListObjectInfosRequest;
import com.xiaohuashifu.recruit.oss.api.request.PreUploadObjectRequest;
import com.xiaohuashifu.recruit.oss.api.response.ObjectInfoResponse;
import com.xiaohuashifu.recruit.oss.api.service.ObjectStorageService;
import org.apache.dubbo.config.annotation.Service;

import javax.validation.constraints.NotNull;

/**
 * 描述：对象存储服务
 *
 * @author xhsf
 * @create 2021/1/29 00:17
 */
@Service
public class ObjectStorageServiceImpl implements ObjectStorageService {
    /**
     * 预上传对象
     *
     * @param request 请求
     * @return 预上传结果
     * @errorCode InvalidParameter: 对象名 | 对象格式错误
     * InternalError: 上传文件失败
     */
    @Override
    public Result<ObjectInfoResponse> preUploadObject(@NotNull PreUploadObjectRequest request) {
        return null;
    }

    /**
     * 删除对象
     *
     * @param objectName 对象名，需要完整路径，如 users/avatars/1321.jpg
     * @return 删除结果
     * @errorCode UnprocessableEntity.NotExist 所要删除的对象不存在
     * InternalError 由于网络原因，删除对象失败
     */
    @Override
    public Result<Void> deleteObject(String objectName) {
        return null;
    }

    /**
     * 删除对象
     *
     * @param id 对象编号
     * @return 删除结果
     * @errorCode UnprocessableEntity.NotExist 所要删除的对象不存在
     * InternalError 由于网络原因，删除对象失败
     */
    @Override
    public Result<Void> deleteObject(Long id) {
        return null;
    }

    /**
     * 获取对象信息
     *
     * @param objectName 对象名，需要完整路径，如 users/avatars/1321.jpg
     * @return 响应
     * @errorCode NotFound: 对象信息不存在
     */
    @Override
    public Result<ObjectInfoResponse> getObjectInfo(String objectName) {
        return null;
    }

    /**
     * 获取对象信息
     *
     * @param id 对象编号
     * @return 响应
     * @errorCode NotFound: 对象信息不存在
     */
    @Override
    public Result<ObjectInfoResponse> getObjectInfo(Long id) {
        return null;
    }

    /**
     * 列出对象信息
     *
     * @param request 请求
     * @return 列出对象信息结果，可能返回空列表
     * @errorCode UnprocessableEntity.InvalidParameter: 无法处理，非法参数
     */
    @Override
    public Result<QueryResult<ObjectInfoResponse>> listObjectInfos(@NotNull ListObjectInfosRequest request) {
        return null;
    }

    /**
     * 链接对象，对象若不链接会被自动删除
     *
     * @param objectName 对象名
     * @return 链接后的对象信息
     * @errorCode UnprocessableEntity.NotExist 所要链接的对象不存在
     */
    @Override
    public Result<ObjectInfoResponse> linkObject(String objectName) {
        return null;
    }

    /**
     * 链接对象，对象若不链接会被自动删除
     *
     * @param id 对象编号
     * @return 链接后的对象信息
     * @errorCode UnprocessableEntity.NotExist 所要链接的对象不存在
     */
    @Override
    public Result<ObjectInfoResponse> linkObject(Long id) {
        return null;
    }
}
