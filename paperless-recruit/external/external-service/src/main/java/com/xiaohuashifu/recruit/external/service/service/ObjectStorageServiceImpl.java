package com.xiaohuashifu.recruit.external.service.service;

import com.xiaohuashifu.recruit.common.result.ErrorCodeEnum;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.common.validator.ObjectNameValidator;
import com.xiaohuashifu.recruit.external.api.dto.ObjectInfoListDTO;
import com.xiaohuashifu.recruit.external.api.service.ObjectStorageService;
import com.xiaohuashifu.recruit.external.service.manager.ObjectStorageManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;

/**
 * 描述：对象存储服务
 *
 * @author xhsf
 * @create 2020/12/7 17:09
 */
@Service
public class ObjectStorageServiceImpl implements ObjectStorageService {

    private final ObjectStorageManager objectStorageManager;

    private static final ObjectNameValidator objectNameValidator = new ObjectNameValidator();

    public ObjectStorageServiceImpl(ObjectStorageManager objectStorageManager) {
        this.objectStorageManager = objectStorageManager;
    }

    /**
     * 上传对象
     *
     * @param objectName 对象名，需要完整路径，如 users/avatars/1321.jpg
     * @param object 对象
     * @return 上传结果
     */
    @Override
    public Result<Void> putObject(String objectName, byte[] object) {
        // 对象名不能为空
        if (StringUtils.isBlank(objectName)) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER, "The object name can't be blank.");
        }

        // 必须符合格式
        if (objectNameValidator.isValid(objectName, null)) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER,
                    "The length of object name must be between 1 and 1023, and can't start with /.");
        }

        // 对象长度必须小于 10MB
        if (object != null && object.length > MAX_OBJECT_SIZE) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER,
                    "The object length must be less then 10240.");
        }

        // 上传对象
        if (!objectStorageManager.putObject(objectName, object)) {
            return Result.fail(ErrorCodeEnum.INTERNAL_ERROR, "Put object failed.");
        }
        return Result.success();
    }

    /**
     * 删除对象
     *
     * @param objectName 对象名，需要完整路径，如 users/avatars/1321.jpg
     * @return 删除结果
     */
    @Override
    public Result<Void> deleteObject(String objectName) {
        if (!objectStorageManager.deleteObject(objectName)) {
            return Result.fail(ErrorCodeEnum.INTERNAL_ERROR, "Delete object failed.");
        }
        return Result.success();
    }

    /**
     * 下载对象
     *
     * @param objectName 对象名，需要完整路径，如 users/avatars/1321.jpg
     * @return 对象
     */
    @Override
    public Result<byte[]> getObject(String objectName) {
        byte[] object = objectStorageManager.getObject(objectName);
        if (object == null) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_FOUND, "The object does not exist.");
        }
        return Result.success(object);
    }

    /**
     * 获取对象信息，一次最大 50 条
     *
     * @param prefix 对象名前缀
     * @param startMarker 起始对象名，若为空默认
     * @param size 获取条数
     * @return 列举对象结果，可能包含空列表
     */
    @Override
    public Result<ObjectInfoListDTO> listObjectInfos(String prefix, String startMarker, Integer size) {
        ObjectInfoListDTO objectInfoListDTO = objectStorageManager.listObjectInfos(prefix, startMarker, size);
        return Result.success(objectInfoListDTO);
    }

}
