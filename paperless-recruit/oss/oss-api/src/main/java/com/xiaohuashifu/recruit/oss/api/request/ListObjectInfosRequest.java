package com.xiaohuashifu.recruit.oss.api.request;

import com.xiaohuashifu.recruit.common.validator.annotation.ObjectName;
import com.xiaohuashifu.recruit.oss.api.constant.ObjectStorageServiceConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 描述：列出对象信息请求
 *
 * @author xhsf
 * @create 2021/1/28 23:27
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListObjectInfosRequest implements Serializable {

    /**
     * 基础对象名，如 users/avatars/
     */
    @NotNull
    @ObjectName
    private String baseObjectName;

    /**
     * 页码
     */
    @NotNull
    private Integer pageNum;

    /**
     * 页条数，最大 50
     */
    @NotNull
    @Max(ObjectStorageServiceConstants.LIST_OBJECT_INFOS_REQUEST_MAX_PAGE_SIZE)
    private Integer pageSize;
}
