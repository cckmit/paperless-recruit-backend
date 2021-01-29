package com.xiaohuashifu.recruit.oss.api.request;

import com.xiaohuashifu.recruit.common.validator.annotation.ObjectName;
import com.xiaohuashifu.recruit.oss.api.constant.ObjectStorageServiceConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 描述：预上传对象请求
 *
 * @author xhsf
 * @create 2021/1/28 21:12
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PreUploadObjectRequest implements Serializable {

    /**
     * 上传者用户编号
     */
    @NotNull
    @Positive
    private Long userId;

    /**
     * 原始对象名，如 简历.doc
     */
    @Size(max = ObjectStorageServiceConstants.MAX_ORIGINAL_NAME_LENGTH)
    private String originalName;

    /**
     * 对象名（对象完整路径），如 users/avatars/1321.jpg
     * 必须唯一
     */
    @NotBlank
    @ObjectName
    private String objectName;

    /**
     * 对象
     */
    @NotNull
    @Size(max = ObjectStorageServiceConstants.MAX_OBJECT_SIZE)
    private byte[] object;

}
