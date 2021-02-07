package com.xiaohuashifu.recruit.external.service.service;

import com.xiaohuashifu.recruit.external.api.dto.ImageAuthCodeDTO;
import com.xiaohuashifu.recruit.external.api.request.CreateImageAuthCodeRequest;
import com.xiaohuashifu.recruit.external.api.service.ImageAuthCodeService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.junit.Before;
import org.junit.Test;

/**
 * 描述：
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/22 18:34
 */
public class ImageAuthCodeServiceImplTest {

    private ImageAuthCodeService imageAuthCodeService;

    @Before
    public void setUp() throws Exception {
        ApplicationConfig application = new ApplicationConfig();
        application.setName("imageAuthCodeServiceTest");
        ReferenceConfig<ImageAuthCodeService> reference = new ReferenceConfig<>();
        reference.setUrl("dubbo://127.0.0.1:20883/com.xiaohuashifu.recruit.external.api.service." +
                "ImageAuthCodeService");
        reference.setApplication(application);
        reference.setInterface(ImageAuthCodeService.class);
        reference.setTimeout(10000000);
        imageAuthCodeService = reference.get();
    }


    @Test
    public void createImageAuthCode() {
        ImageAuthCodeDTO imageAuthCodeDTO = imageAuthCodeService.createImageAuthCode(
                CreateImageAuthCodeRequest.builder()
                        .width(100)
                        .height(40)
                        .length(5)
                        .expirationTime(5)
                        .build());
        System.out.println(imageAuthCodeDTO);
    }

    @Test
    public void checkImageAuthCode() {
        imageAuthCodeService.checkImageAuthCode("e4a7df78-c5b3-4fb2-b143-aad07524b9594","90573");
    }
}