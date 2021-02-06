package com.xiaohuashifu.recruit.authentication.api.service;

import com.xiaohuashifu.recruit.authentication.api.constant.WhiteListServiceConstants;
import com.xiaohuashifu.recruit.authentication.api.dto.PermittedUrlDTO;
import com.xiaohuashifu.recruit.authentication.api.query.PermittedUrlQuery;
import com.xiaohuashifu.recruit.common.exception.NotFoundServiceException;
import com.xiaohuashifu.recruit.common.query.QueryResult;
import com.xiaohuashifu.recruit.common.validator.annotation.AntPath;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * 描述：白名单服务，这是基于 URL 的白名单
 *
 * @private 内部服务
 *
 * @author xhsf
 * @create 2020/11/27 17:22
 */
public interface WhiteListService {

    /**
     * 添加被允许的 url
     *
     * @param url 被允许的 url
     * @return PermittedUrlDTO
     */
    PermittedUrlDTO createPermittedUrl(
            @NotBlank @Size(max = WhiteListServiceConstants.MAX_PERMITTED_URL_LENGTH) @AntPath String url);

    /**
     * 删除被允许的 url
     *
     * @param id 被允许的 url 的编号
     */
    void removePermittedUrl(@NotNull @Positive Long id);

    /**
     * 通过编号获得 PermittedUrlDTO
     *
     * @param id 被允许路径的编号
     * @return PermittedUrlDTO
     */
    PermittedUrlDTO getPermittedUrl(@NotNull @Positive Long id) throws NotFoundServiceException;

    /**
     * 查询被允许的路径
     *
     * @param query 查询参数
     * @return QueryResult<PermittedUrlDTO> 这里可能返回空列表
     */
    QueryResult<PermittedUrlDTO> listPermittedUrls(@NotNull PermittedUrlQuery query);

    /**
     * 获取白名单
     *
     * @return 白名单列表
     */
    List<String> getWhiteList();

    /**
     * 更新被允许的路径
     *
     * @param id 编号
     * @param url 被允许路径
     * @return PermittedUrlDTO 更新后的 PermittedUrlDTO
     */
    PermittedUrlDTO updateUrl(@NotNull @Positive Long id,
            @NotBlank @Size(max = WhiteListServiceConstants.MAX_PERMITTED_URL_LENGTH) @AntPath String url);
}
