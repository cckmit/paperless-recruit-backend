package com.xiaohuashifu.recruit.authentication.service.service;

import com.xiaohuashifu.recruit.authentication.api.query.PermittedUrlQuery;
import com.xiaohuashifu.recruit.authentication.api.service.WhiteListService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.junit.Before;
import org.junit.Test;

/**
 * 描述：
 *
 * @author xhsf
 * @create 2020/11/27 20:32
 */
public class WhiteListServiceImplTest {

    private WhiteListService whiteListService;

    @Before
    public void before() {
        ApplicationConfig application = new ApplicationConfig();
        application.setName("whiteListServiceTest");
        ReferenceConfig<WhiteListService> reference = new ReferenceConfig<>();
        reference.setUrl("dubbo://127.0.0.1:20882/com.xiaohuashifu.recruit.authentication.api.service.WhiteListService");
        reference.setApplication(application);
        reference.setInterface(WhiteListService.class);
        reference.setTimeout(10000000);
        whiteListService = reference.get();
    }

    @Test
    public void savePermittedUrl() {
        System.out.println(whiteListService.savePermittedUrl("/oauth/*"));
    }

    @Test
    public void deletePermittedUrl() {
        System.out.println(whiteListService.removePermittedUrl(3L));
    }

    @Test
    public void getPermittedUrl() {
        System.out.println(whiteListService.getPermittedUrl(2L));
    }

    @Test
    public void testGetPermittedUrl() {
        System.out.println(whiteListService.listPermittedUrls(new PermittedUrlQuery.Builder().url("/oaut2h").build()));
    }

    @Test
    public void getWhiteList() {
        System.out.println(whiteListService.getWhiteList());
    }

    @Test
    public void updateUrl() {
        System.out.println(whiteListService.updateUrl(3L, "/aaa/**"));
    }
}