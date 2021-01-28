package com.xiaohuashifu.recruit.oss.api.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Long userId;

    /**
     * 原始对象名，如 简历.doc
     */
    private String originalName;

    /**
     * 基础对象名，如 users/avatars/
     */
    private String baseObjectName;

    /**
     * 对象类型，如 .jpg .txt
     */
    private String type;

    /**
     * 对象
     */
    private byte[] object;

}
