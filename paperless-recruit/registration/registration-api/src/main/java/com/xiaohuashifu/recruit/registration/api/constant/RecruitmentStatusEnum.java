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
     * 等待开始
     */
    WAITING_START(1, "等待开始"),

    /**
     * 已开始
     */
    STARTED(2, "已开始"),

    /**
     * 已结束
     */
    ENDED(3, "已结束");

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

    /**
     * 获取当前状态的前一个状态
     *
     * @param recruitmentStatus 当前状态，不能是 WAITING_FOR_RELEASE
     * @return 前一个状态
     */
    public static RecruitmentStatusEnum getPreviousStatus(RecruitmentStatusEnum recruitmentStatus) {
        if (recruitmentStatus == WAITING_FOR_RELEASE) {
            throw new IllegalStateException("This status does not exist previous status.");
        }

        RecruitmentStatusEnum lastStatus = WAITING_FOR_RELEASE;
        for (RecruitmentStatusEnum currentStatus : RecruitmentStatusEnum.values()) {
            if (currentStatus == recruitmentStatus) {
                return lastStatus;
            }
            lastStatus = currentStatus;
        }

        throw new IllegalStateException("This status does not exist previous status.");
    }

}
