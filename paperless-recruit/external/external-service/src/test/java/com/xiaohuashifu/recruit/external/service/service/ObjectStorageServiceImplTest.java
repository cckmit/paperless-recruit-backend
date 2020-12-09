package com.xiaohuashifu.recruit.external.service.service;

import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.external.api.service.ObjectStorageService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.junit.Before;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述：
 *
 * @author xhsf
 * @create 2020/12/8 0:22
 */
public class ObjectStorageServiceImplTest {

    private ObjectStorageService objectStorageService;

    @Before
    public void setUp() {
        ApplicationConfig application = new ApplicationConfig();
        application.setName("objectStorageServiceTest");
        ReferenceConfig<ObjectStorageService> reference = new ReferenceConfig<>();
        reference.setUrl("dubbo://127.0.0.1:20883/com.xiaohuashifu.recruit.external.api.service.ObjectStorageService");
        reference.setApplication(application);
        reference.setInterface(ObjectStorageService.class);
        reference.setTimeout(10000000);
        objectStorageService = reference.get();
    }

    @Test
    public void putObject() {
        System.out.println(objectStorageService.putObject("test/test4", "新华社发新华社发顺丰".getBytes()));
    }

    @Test
    public void deleteObject() {
        System.out.println(objectStorageService.deleteObject("31231"));
    }

    @Test
    public void getObject() {
        Result<byte[]> object = objectStorageService.getObject("xxxx");
        byte[] data = object.getData();
        System.out.println(new String(data, StandardCharsets.UTF_8));
    }

    @Test
    public void listObjectInfos() {
        System.out.println(objectStorageService.listObjectInfos("test/", "", 1));
    }

}