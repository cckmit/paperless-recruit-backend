package com.xiaohuashifu.recruit.common.constant;

/**
 * 描述：该产品的子产品名
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/9 20:03
 */
public enum AppEnum {
    /**
     * 华农招新面试者端微信小程序
     */
    SCAU_RECRUIT_INTERVIEWEE_MP(PlatformEnum.WECHAT_MINI_PROGRAM),

    /**
     * 华农招新面试官端微信小程序
     */
    SCAU_RECRUIT_INTERVIEWER_MP(PlatformEnum.WECHAT_MINI_PROGRAM);

    private final PlatformEnum platform;

    AppEnum(PlatformEnum platform) {
        this.platform = platform;
    }

    public PlatformEnum getPlatform() {
        return platform;
    }
}
