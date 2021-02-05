package com.xiaohuashifu.recruit.user.service.service;

import com.xiaohuashifu.recruit.user.api.query.UserProfileQuery;
import com.xiaohuashifu.recruit.user.api.service.UserProfileService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.junit.Before;
import org.junit.Test;

/**
 * 描述：
 *
 * @author: xhsf
 * @create: 2020/11/24 19:55
 */
public class UserProfileServiceImplTest {

    private UserProfileService userProfileService;

    @Before
    public void before() {
        ApplicationConfig application = new ApplicationConfig();
        application.setName("userProfileServiceTest");
        ReferenceConfig<UserProfileService> reference = new ReferenceConfig<>();
        reference.setUrl("dubbo://127.0.0.1:20881/com.xiaohuashifu.recruit.user.api.service.UserProfileService");
        reference.setApplication(application);
        reference.setInterface(UserProfileService.class);
        reference.setTimeout(10000000);
        userProfileService = reference.get();
    }

    @Test
    public void createUserProfile() {
        for (long i = 2; i <= 11; i++ ) {
            System.out.println(userProfileService.createUserProfile(i));
        }
    }

    @Test
    public void getUserProfile() {
        System.out.println(userProfileService.getUserProfile(1L));
    }

    @Test
    public void testGetUserProfile() {
        System.out.println(userProfileService.listUserProfiles(
                UserProfileQuery.builder()
                        .pageNum(1L)
                        .pageSize(50L)
                        .build()));
    }


    @Test
    public void updateCollegeAndMajor() {
//        System.out.println(userProfileService.updateCollegeAndMajor(1L, 2L));
    }

}