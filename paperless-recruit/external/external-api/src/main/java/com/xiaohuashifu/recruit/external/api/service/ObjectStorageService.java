package com.xiaohuashifu.recruit.external.api.service;

import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.common.validator.annotation.ObjectName;
import com.xiaohuashifu.recruit.external.api.constant.EmailServiceConstants;
import com.xiaohuashifu.recruit.external.api.constant.ObjectStorageServiceConstants;
import com.xiaohuashifu.recruit.external.api.dto.ObjectInfoListDTO;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * 描述：对象存储服务
 *
 * @author xhsf
 * @create 2020/12/7 17:36
 */
public interface ObjectStorageService {
    
    /**
     * 上传对象
     *
     * @param objectName 对象名，需要完整路径，如 users/avatars/1321.jpg
     * @param object 对象
     * @return 上传结果
     */
    Result<Void> putObject(String objectName, byte[] object);

    /**
     * 删除对象
     *
     * @param objectName 对象名，需要完整路径，如 users/avatars/1321.jpg
     * @return 删除结果
     */
    Result<Void> deleteObject(@NotBlank(message = "The objectName can't be blank.")
                              @ObjectName String objectName);

    /**
     * 下载对象
     *
     * @param objectName 对象名，需要完整路径，如 users/avatars/1321.jpg
     * @return 对象
     */
    Result<byte[]> getObject(@NotBlank(message = "The objectName can't be blank.")
                             @ObjectName String objectName);

    /**
     * 获取对象信息，一次最大 50 条
     *
     * @param prefix 对象名前缀
     * @param startMarker 起始对象名，若为空默认
     * @param size 获取条数
     * @return 列举对象结果，可能包含空列表
     */
    Result<ObjectInfoListDTO> listObjectInfos(
            @ObjectName String prefix, String startMarker,
            @NotNull(message = "The size can't be null.")
            @Positive(message = "The size must be greater than 0.")
            @Max(value = ObjectStorageServiceConstants.MAX_LIST_OBJECT_INFOS_SIZE,
                    message = "The size must not be greater than "
                            + ObjectStorageServiceConstants.MAX_LIST_OBJECT_INFOS_SIZE + ".") Integer size);

}
