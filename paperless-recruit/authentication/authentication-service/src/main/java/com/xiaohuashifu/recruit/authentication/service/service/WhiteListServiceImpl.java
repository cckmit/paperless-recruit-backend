package com.xiaohuashifu.recruit.authentication.service.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaohuashifu.recruit.authentication.api.dto.PermittedUrlDTO;
import com.xiaohuashifu.recruit.authentication.api.query.PermittedUrlQuery;
import com.xiaohuashifu.recruit.authentication.api.service.WhiteListService;
import com.xiaohuashifu.recruit.authentication.service.assembler.PermittedUrlAssembler;
import com.xiaohuashifu.recruit.authentication.service.dao.PermittedUrlMapper;
import com.xiaohuashifu.recruit.authentication.service.do0.PermittedUrlDO;
import com.xiaohuashifu.recruit.common.exception.NotFoundServiceException;
import com.xiaohuashifu.recruit.common.exception.unprocessable.DuplicateServiceException;
import com.xiaohuashifu.recruit.common.query.QueryResult;
import org.apache.dubbo.config.annotation.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 描述：白名单服务，这是基于 URL 的白名单
 *
 * @private 内部服务
 *
 * @author xhsf
 * @create 2020/11/27 20:23
 */
@Service
public class WhiteListServiceImpl implements WhiteListService {

    private final PermittedUrlMapper permittedUrlMapper;

    private final PermittedUrlAssembler permittedUrlAssembler;

    public WhiteListServiceImpl(PermittedUrlMapper permittedUrlMapper, PermittedUrlAssembler permittedUrlAssembler) {
        this.permittedUrlMapper = permittedUrlMapper;
        this.permittedUrlAssembler = permittedUrlAssembler;
    }

    @Override
    public PermittedUrlDTO createPermittedUrl(String url) {
        // 判断该 url 是否已经存在
        PermittedUrlDO permittedUrlDO = permittedUrlMapper.selectByUrl(url);
        if (permittedUrlDO != null) {
            throw new DuplicateServiceException("The url already exist.");
        }

        // 保存
        PermittedUrlDO permittedUrlDOForInsert = PermittedUrlDO.builder().url(url).build();
        permittedUrlMapper.insert(permittedUrlDOForInsert);
        return getPermittedUrl(permittedUrlDOForInsert.getId());
    }

    @Override
    public void removePermittedUrl(Long id) {
        // 删除
        permittedUrlMapper.deleteById(id);
    }

    @Override
    public PermittedUrlDTO getPermittedUrl(Long id) {
        PermittedUrlDO permittedUrlDO = permittedUrlMapper.selectById(id);
        if (permittedUrlDO == null) {
            throw new NotFoundServiceException("permittedUrl", "id", id);
        }
        return permittedUrlAssembler.permittedUrlDOToPermittedUrlDTO(permittedUrlDO);
    }

    @Override
    public QueryResult<PermittedUrlDTO> listPermittedUrls(PermittedUrlQuery query) {
        LambdaQueryWrapper<PermittedUrlDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.likeRight(query.getUrl() != null, PermittedUrlDO::getUrl, query.getUrl());

        Page<PermittedUrlDO> page = new Page<>(query.getPageNum(), query.getPageSize(), true);
        permittedUrlMapper.selectPage(page, wrapper);
        List<PermittedUrlDTO> permittedUrlDTOS = page.getRecords()
                .stream().map(permittedUrlAssembler::permittedUrlDOToPermittedUrlDTO).collect(Collectors.toList());
        return new QueryResult<>(page.getTotal(), permittedUrlDTOS);
    }

    @Override
    public List<String> getWhiteList() {
        LambdaQueryWrapper<PermittedUrlDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(PermittedUrlDO::getUrl);
        List<PermittedUrlDO> whiteList = permittedUrlMapper.selectList(wrapper);
        return whiteList.stream().map(PermittedUrlDO::getUrl).collect(Collectors.toList());
    }

    @Override
    public PermittedUrlDTO updateUrl(Long id, String url) {
        // 更新 url
        PermittedUrlDO permittedUrlDOForUpdate = PermittedUrlDO.builder().id(id).url(url).build();
        permittedUrlMapper.updateById(permittedUrlDOForUpdate);
        return getPermittedUrl(id);
    }

}
