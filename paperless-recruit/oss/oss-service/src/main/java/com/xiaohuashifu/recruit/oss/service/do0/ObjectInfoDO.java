package com.xiaohuashifu.recruit.oss.service.do0;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 描述：对象信息数据对象
 *
 * @author xhsf
 * @create 2021/1/29 01:35
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("object_info")
public class ObjectInfoDO {
    /**
     * 对象编号
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 对象名（对象完整路径），如 users/avatars/1321.jpg
     */
    private String objectName;

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
    private Integer size;

    /**
     * 对象是否已经被关联
     */
    @TableField(value = "is_linked")
    private Boolean linked;

    /**
     * 对象是否已经被删除
     */
    @TableField(value = "is_deleted")
    private Boolean deleted;

    /**
     * 对象创建时间
     */
    private LocalDateTime createTime;

    /**
     * 对象保存时间
     */
    private LocalDateTime updateTime;

}
