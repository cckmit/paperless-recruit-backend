package com.xiaohuashifu.recruit.common.constant;

/**
 * 描述：性别
 *
 * @author xhsf
 * @create 2020/12/4 20:54
 */
public enum GenderEnum {
    UNKNOWN(0, "未知"),
    MAN(1, "男"),
    WOMAN(2, "女");

    private final int id;
    private final String name;

    GenderEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static GenderEnum getGenderById(int id) {
        for (GenderEnum gender : GenderEnum.values()) {
            if (gender.getId() == id) {
                return gender;
            }
        }
        return null;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
