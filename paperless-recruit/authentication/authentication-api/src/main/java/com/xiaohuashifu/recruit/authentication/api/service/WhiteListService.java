package com.xiaohuashifu.recruit.authentication.api.service;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.recruit.authentication.api.dto.PermittedUrlDTO;
import com.xiaohuashifu.recruit.authentication.api.query.PermittedUrlQuery;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.common.validator.annotation.AntPath;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * 描述：白名单服务，这是基于 URL 的白名单
 *
 * @author xhsf
 * @create 2020/11/27 17:22
 */
public interface WhiteListService {

    /**
     * 添加被允许的 url
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *              OperationConflict: 该 url 已经存在
     *
     * @param url 被允许的 url
     * @return PermittedUrlDTO
     */
    default Result<PermittedUrlDTO> savePermittedUrl(
            @NotBlank @Size(min = 1, max = 255) @AntPath String url) {
        throw new UnsupportedOperationException();
    }

    /**
     * 删除被允许的 url
     *
     * @errorCode InvalidParameter: 请求参数格式错误 | 要删除的编号不存在
     *
     * @param id 被允许的 url 的编号
     * @return PermittedUrlDTO
     */
    default Result<Void> removePermittedUrl(@NotNull @Positive Long id) {
        throw new UnsupportedOperationException();
    }

    /**
     * 通过编号获得 PermittedUrlDTO
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *              InvalidParameter.NotFound: 找不到该编号的允许路径
     *
     * @param id 被允许路径的编号
     * @return PermittedUrlDTO
     */
    default Result<PermittedUrlDTO> getPermittedUrl(@NotNull @Positive Long id) {
        throw new UnsupportedOperationException();
    }

    /**
     * 查询被允许的路径
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *
     * @param query 查询参数
     * @return PageInfo<PermittedUrlDTO> 这里可能返回空列表
     */
    default Result<PageInfo<PermittedUrlDTO>> listPermittedUrls(@NotNull PermittedUrlQuery query) {
        throw new UnsupportedOperationException();
    }

    /**
     * 获取白名单
     *
     * @return 白名单列表
     */
    default Result<List<String>> getWhiteList() {
        throw new UnsupportedOperationException();
    }

    /**
     * 更新被允许的路径
     *
     * @errorCode InvalidParameter: 请求参数格式错误 | 该编号的允许路径不存在 | 新 url 与原 url 相同
     *              OperationConflict: 新 url 已经存在
     *
     * @param id 编号
     * @param newUrl 新的被允许路径
     * @return PermittedUrlDTO 更新后的 PermittedUrlDTO
     */
    default Result<PermittedUrlDTO> updateUrl(
            @NotNull @Positive Long id,
            @NotBlank @Size(min = 1, max = 255) @AntPath String newUrl) {
        throw new UnsupportedOperationException();
    }
}
