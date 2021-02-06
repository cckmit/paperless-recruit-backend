package com.xiaohuashifu.recruit.user.service.service;

import com.xiaohuashifu.recruit.user.api.query.UserProfileQuery;
import com.xiaohuashifu.recruit.user.api.request.UpdateUserProfileRequest;
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
        System.out.println(userProfileService.createUserProfile(1L));
    }

    @Test
    public void getUserProfile() {
        System.out.println(userProfileService.getUserProfile(1L));
    }

    @Test
    public void listUserProfiles() {
        System.out.println(userProfileService.listUserProfiles(
                UserProfileQuery.builder()
                        .pageNum(1L)
                        .pageSize(50L)
                        .build()));
    }

    @Test
    public void updateUserProfile() {
        System.out.println(userProfileService.updateUserProfile(
                UpdateUserProfileRequest.builder().id(2L).fullName("黄海欣").introduction("hdx")
                        .studentNumber("201820182018").majorId(2L).build()));
    }

}