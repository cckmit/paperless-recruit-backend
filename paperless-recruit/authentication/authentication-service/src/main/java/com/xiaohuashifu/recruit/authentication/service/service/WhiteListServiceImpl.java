package com.xiaohuashifu.recruit.authentication.service.service;

import com.github.dozermapper.core.Mapper;
import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.recruit.authentication.api.dto.PermittedUrlDTO;
import com.xiaohuashifu.recruit.authentication.api.query.PermittedUrlQuery;
import com.xiaohuashifu.recruit.authentication.api.service.WhiteListService;
import com.xiaohuashifu.recruit.authentication.service.dao.PermittedUrlMapper;
import com.xiaohuashifu.recruit.authentication.service.do0.PermittedUrlDO;
import com.xiaohuashifu.recruit.common.result.ErrorCodeEnum;
import com.xiaohuashifu.recruit.common.result.Result;
import org.apache.dubbo.config.annotation.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 描述：白名单服务，这是基于 URL 的白名单
 *
 * @author xhsf
 * @create 2020/11/27 20:23
 */
@Service
public class WhiteListServiceImpl implements WhiteListService {

    private final PermittedUrlMapper permittedUrlMapper;
    private final Mapper mapper;

    public WhiteListServiceImpl(PermittedUrlMapper permittedUrlMapper, Mapper mapper) {
        this.permittedUrlMapper = permittedUrlMapper;
        this.mapper = mapper;
    }

    /**
     * 添加被允许的 Url
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *              OperationConflict: 该 url 已经存在
     *
     * @param url 被允许的 Url
     * @return PermittedUrlDTO
     */
    @Override
    public Result<PermittedUrlDTO> savePermittedUrl(String url) {
        // 判断该 url 是否已经存在
        int count = permittedUrlMapper.countByUrl(url);
        if (count > 0) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT, "The url already exist.");
        }

        // 保存
        PermittedUrlDO permittedUrlDO = new PermittedUrlDO.Builder().url(url).build();
        permittedUrlMapper.savePermittedUrl(permittedUrlDO);
        return getPermittedUrl(permittedUrlDO.getId());
    }

    /**
     * 删除被允许的 Url
     *
     * @errorCode InvalidParameter: 请求参数格式错误 | 要删除的编号不存在
     *
     * @param id 被允许的 Url 的编号
     * @return PermittedUrlDTO
     */
    @Override
    public Result<Void> deletePermittedUrl(Long id) {
        // 判断要删除的 permittedUrl 存不存在
        int count = permittedUrlMapper.count(id);
        if (count < 1) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER, "The url does not exist.");
        }

        // 删除
        permittedUrlMapper.deletePermittedUrl(id);
        return Result.success();
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
    @Override
    public Result<PermittedUrlDTO> getPermittedUrl(Long id) {
        PermittedUrlDO permittedUrlDO = permittedUrlMapper.getPermittedUrl(id);
        if (permittedUrlDO == null) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_FOUND);
        }
        return Result.success(mapper.map(permittedUrlDO, PermittedUrlDTO.class));
    }

    /**
     * 查询被允许的路径
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *
     * @param query 查询参数
     * @return PageInfo<PermittedUrlDTO> 这里可能返回空列表
     */
    @Override
    public Result<PageInfo<PermittedUrlDTO>> getPermittedUrl(PermittedUrlQuery query) {
        List<PermittedUrlDO> permittedUrlDOList = permittedUrlMapper.getPermittedUrlByQuery(query);
        List<PermittedUrlDTO> permittedUrlDTOList = permittedUrlDOList.stream()
                .map(permittedUrlDO -> mapper.map(permittedUrlDO, PermittedUrlDTO.class))
                .collect(Collectors.toList());
        PageInfo<PermittedUrlDTO> pageInfo = new PageInfo<>(permittedUrlDTOList);
        return Result.success(pageInfo);
    }

    /**
     * 获取白名单
     *
     * @return 白名单列表
     */
    @Override
    public Result<List<String>> getWhiteList() {
        List<String> whiteList = permittedUrlMapper.getAllUrl();
        return Result.success(whiteList);
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
    @Override
    public Result<PermittedUrlDTO> updateUrl(Long id, String newUrl) {
        // 判断该url是否存在
        PermittedUrlDO permittedUrlDO = permittedUrlMapper.getPermittedUrl(id);
        if (permittedUrlDO == null) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER, "The url does not exist.");
        }

        // 判断该url是否与原url相同
        if (permittedUrlDO.getUrl().equals(newUrl)) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER, "The new url must not same as old url.");
        }

        // 判断该url是否已经存在
        int count = permittedUrlMapper.countByUrl(newUrl);
        if (count > 0) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT, "The url already exist.");
        }

        // 更新url
        permittedUrlMapper.updateUrl(id, newUrl);
        return getPermittedUrl(id);
    }

}
