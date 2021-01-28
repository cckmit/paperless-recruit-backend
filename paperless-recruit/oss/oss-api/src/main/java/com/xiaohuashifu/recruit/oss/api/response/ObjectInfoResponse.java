package com.xiaohuashifu.recruit.oss.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 描述：对象响应
 *
 * @author xhsf
 * @create 2021/1/28 21:15
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ObjectInfoResponse implements Serializable {

    /**
     * 对象编号
     */
    private Long id;

    /**
     * 对象名（对象完整路径），如 users/avatars/1321.jpg
     */
    private String objectName;

    /**
     * 基础对象名，如 users/avatars/
     */
    private String baseObjectName;

    /**
     * 上传者用户编号
     */
    private Long userId;

    /**
     * 原始对象名，如 简历.doc
     */
    private String originalName;

    /**
     * 对象大小，B
     */
    private Long size;

    /**
     * 对象类型，如 .jpg .txt
     */
    private String type;

    /**
     * 对象是否已经被关联
     */
    private Boolean isLinked;

    /**
     * 对象是否已经被删除
     */
    private Boolean isDeleted;

    /**
     * 对象创建时间
     */
    private LocalDateTime createTime;

    /**
     * 对象保存时间
     */
    private LocalDateTime updateTime;

}
