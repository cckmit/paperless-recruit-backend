package com.xiaohuashifu.recruit.facade.service.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 描述：对象信息
 *
 * @author xhsf
 * @create 2021/1/30 01:10
 */
@ApiModel
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ObjectInfoVO {

    /**
     * 对象编号
     */
    @ApiModelProperty(value = "对象编号", example = "123")
    private Long id;

    /**
     * 对象名（对象完整路径）
     */
    @ApiModelProperty(value = "对象名（对象完整路径）", example = "users/avatars/1321.jpg")
    private String objectName;

    /**
     * 上传者用户编号
     */
    @ApiModelProperty(value = "上传者用户编号", example = "123")
    private Long userId;

    /**
     * 原始对象名
     */
    @ApiModelProperty(value = "原始对象名", example = "简历.doc")
    private String originalName;

    /**
     * 对象大小，单位字节
     */
    @ApiModelProperty(value = "对象大小，单位字节", example = "31321")
    private Integer size;

    /**
     * 对象是否已经被关联
     */
    @ApiModelProperty(value = "对象是否已经被关联", example = "false")
    private Boolean linked;

    /**
     * 对象是否已经被删除
     */
    @ApiModelProperty(value = "对象是否已经被删除", example = "false")
    private Boolean deleted;

}
