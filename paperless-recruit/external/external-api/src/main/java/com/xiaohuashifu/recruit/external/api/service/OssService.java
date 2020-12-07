package com.xiaohuashifu.recruit.external.api.service;

import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.common.validator.annotation.ObjectName;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 描述：对象存储服务
 *
 * @author xhsf
 * @create 2020/12/7 17:36
 */
public interface OssService {
    
    /**
     * 上传文件
     *
     * @param objectName 对象名，需要完整路径，如 user/avatar/1321.jpg
     * @param object 对象
     * @return 上传结果
     */
    default Result<Void> putObject(@NotBlank @ObjectName String objectName, @Size(max = 10240) byte[] object) {
        throw new UnsupportedOperationException();
    }

    /**
     * 下载文件
     *
     * @param objectName 对象名，需要完整路径，如 user/avatar/1321.jpg
     * @return 文件对象
     */
    default Result<byte[]> getObject(@NotBlank @ObjectName String objectName) {
        throw new UnsupportedOperationException();
    }

    /**
     * 删除文件
     *
     * @param objectName 对象名，需要完整路径，如 user/avatar/1321.jpg
     * @return 删除结果
     */
    default Result<Void> deleteObject(@NotBlank @ObjectName String objectName) {
        throw new UnsupportedOperationException();
    }

}
