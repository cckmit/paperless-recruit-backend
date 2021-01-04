package com.xiaohuashifu.recruit.registration.api.constant;

import lombok.Getter;

/**
 * 描述：面试状态
 *
 * @author xhsf
 * @create 2021/1/4 17:35
 */
@Getter
public enum InterviewStatusEnum {

    /**
     * 等待面试
     */
    WAITING_INTERVIEW(0, "等待面试"),

    /**
     * 待定
     */
    PENDING(1, "待定"),

    /**
     * 未通过
     */
    NOT_PASS(2, "未通过"),

    /**
     * 通过
     */
    PASS(2, "通过");

    private final int code;
    private final String name;

    InterviewStatusEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

}
