package com.xiaohuashifu.recruit.common.util;

import java.lang.reflect.Field;

/**
 * 描述: Object 检查器
 *
 * @author xhsf
 * @create 2019-08-23 1:26
 */
public class ObjectUtils {

    private ObjectUtils() {}

    /**
     * 判断类中每个属性是否都为空
     *
     * @param o 检查的对象
     * @return 是否全为空
     */
    public static boolean allFieldIsNull(Object o) {
        for (Field field : o.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object value = null;
            try {
                value = field.get(o);
            } catch (IllegalAccessException ignored) {
                // 该异常不可能抛出，忽略
            }
            if (null != value) {
                return false;
            }
        }
        return true;
    }

}
