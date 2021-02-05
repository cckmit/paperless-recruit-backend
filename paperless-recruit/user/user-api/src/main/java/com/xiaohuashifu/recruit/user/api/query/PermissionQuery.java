package com.xiaohuashifu.recruit.user.api.query;

import com.xiaohuashifu.recruit.common.constant.QueryConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;

/**
 * 描述：权限查询参数
 *
 * @author: xhsf
 * @create: 2020/10/29 23:48
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PermissionQuery implements Serializable {

    /**
     * 页码
     */
    @NotNull
    @Positive
    private Long pageNum;

    /**
     * 页条数
     */
    @NotNull
    @Positive
    @Max(value = QueryConstants.MAX_PAGE_SIZE)
    private Long pageSize;

    /**
     * 父权限编号
     */
    private Long parentPermissionId;

    /**
     * 权限名，可模糊
     */
    private String permissionName;

    /**
     * 授权路径，可模糊
     */
    private String authorizationUrl;

    /**
     * 权限是否可用
     */
    private Boolean available;

}
