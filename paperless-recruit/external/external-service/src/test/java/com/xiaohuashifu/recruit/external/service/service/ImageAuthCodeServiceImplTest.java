package com.xiaohuashifu.recruit.external.service.service;

import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.external.api.dto.ImageAuthCodeDTO;
import com.xiaohuashifu.recruit.external.api.service.ImageAuthCodeService;
import com.xiaohuashifu.recruit.external.api.service.WechatMpSubscribeMessageTemplateService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

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
        Result<ImageAuthCodeDTO> createImageAuthCodeResult = imageAuthCodeService.createImageAuthCode(
                new ImageAuthCodeDTO.Builder()
                        .width(100)
                        .height(40)
                        .length(5)
                        .expiredTime(5)
                        .build());
        System.out.println(createImageAuthCodeResult.getData().getAuthCode());
        System.out.println(createImageAuthCodeResult.getData().getId());
    }

    @Test
    public void checkImageAuthCode() {
        System.out.println(imageAuthCodeService.checkImageAuthCode(
                "09661b30-b93b-4ad2-b02c-214f9eb14917sl6GMDMaxd","90573"));
    }
}