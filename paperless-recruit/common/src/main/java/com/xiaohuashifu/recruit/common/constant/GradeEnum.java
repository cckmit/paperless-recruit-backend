package com.xiaohuashifu.recruit.common.constant;

/**
 * 描述：年级
 *
 * @author xhsf
 * @create 2020/12/23 17:18
 */
public enum  GradeEnum {

    /**
     * 大一
     */
    FRESHMEN(1, "大一"),

    /**
     * 大二
     */
    SOPHOMORE(2, "大二"),

    /**
     * 大三
     */
    JUNIOR(3, "大三"),

    /**
     * 大四
     */
    SENIOR(4, "大四");

    private final int code;

    private final String name;

    GradeEnum(int code, String name) {
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
