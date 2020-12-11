package com.xiaohuashifu.recruit.authentication.service.dao;

import com.xiaohuashifu.recruit.authentication.api.query.PermittedUrlQuery;
import com.xiaohuashifu.recruit.authentication.service.do0.PermittedUrlDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 描述：
 *
 * @author xhsf
 * @create 2020/11/27 20:22
 */
public interface PermittedUrlMapper {

    int insertPermittedUrl(PermittedUrlDO permittedUrlDO);

    int deletePermittedUrl(Long id);

    PermittedUrlDO getPermittedUrl(Long id);

    List<PermittedUrlDO> listPermittedUrls(PermittedUrlQuery query);

    /**
     * 获取所有的 url，用于白名单
     *
     * @return List<String> urlList
     */
    List<String> listAllUrls();

    int count(Long id);

    int countByUrl(String url);

    int updateUrl(@Param("id") Long id, @Param("url") String url);

}
