package com.xiaohuashifu.recruit.registration.api.constant;

/**
 * 描述：招新的状态
 *
 * @author xhsf
 * @create 2020/12/23 16:45
 */
public enum RecruitmentStatusEnum {

    /**
     * 等待发布
     */
    WAITING_FOR_RELEASE(0, "等待发布"),

    /**
     * 已发布
     */
    RELEASED(1, "已发布"),

    /**
     * 等待开始
     */
    WAITING_START(2, "等待开始"),

    /**
     * 已开始
     */
    STARTED(3, "已开始"),

    /**
     * 已结束
     */
    ENDED(4, "已结束");

    private final int code;
    private final String name;

    RecruitmentStatusEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
