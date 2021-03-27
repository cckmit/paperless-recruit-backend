package com.xiaohuashifu.recruit.oss.service.service;

import com.xiaohuashifu.recruit.oss.api.request.ListObjectInfosRequest;
import com.xiaohuashifu.recruit.oss.api.request.PreUploadObjectRequest;
import com.xiaohuashifu.recruit.oss.api.service.ObjectStorageService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * 描述：
 *
 * @author xhsf
 * @create 2021/1/29 16:51
 */
public class ObjectStorageServiceImplTest {

    private ObjectStorageService objectStorageService;

    @Before
    public void before() {
        ApplicationConfig application = new ApplicationConfig();
        application.setName("objectStorageServiceTest");
        ReferenceConfig<ObjectStorageService> reference = new ReferenceConfig<>();
        reference.setUrl("dubbo://127.0.0.1:20890/com.xiaohuashifu.recruit.oss.api.service.ObjectStorageService");
        reference.setApplication(application);
        reference.setInterface(ObjectStorageService.class);
        reference.setTimeout(10000000);
        objectStorageService = reference.get();
    }

    @Test
    public void preUploadObject() throws IOException {
        File file = new File("D:\\Github\\SpecializedCourseCode\\文档\\毕设相关文档\\图片\\毕设原型图片\\社科部科科.jpg");
        FileInputStream fis = new FileInputStream(file);
        byte[] bytes = fis.readAllBytes();
        PreUploadObjectRequest request = new PreUploadObjectRequest(
                1L, "简历.txt", "organizations/core-members/avartars/u=336497710,1373556175&fm=26&gp=10.jpg", bytes);
        System.out.println(objectStorageService.preUploadObject(request));
    }

    @Test
    public void deleteObject() {
        objectStorageService.deleteObject(1L);
    }

    @Test
    public void testDeleteObject() {
    }

    @Test
    public void getObjectInfo() {
    }

    @Test
    public void testGetObjectInfo() {
    }

    @Test
    public void listObjectInfos() {
        System.out.println(objectStorageService.listObjectInfos(ListObjectInfosRequest.builder()
                .pageNum(2).pageSize(4).baseObjectName("pro").build()));
    }

    @Test
    public void linkObject() {
        System.out.println(objectStorageService.linkObject(3L));
    }

    @Test
    public void testLinkObject() {
        System.out.println(objectStorageService.linkObject("application-forms/avatars/050930d838b2454e96ab759caadffebc9d82d158ccbf6c81c23a7956b43eb13533fa407a.jpg"));
    }
}