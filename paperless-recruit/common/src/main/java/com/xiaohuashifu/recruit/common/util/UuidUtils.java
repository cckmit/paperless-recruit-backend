package com.xiaohuashifu.recruit.common.util;

import java.util.UUID;

/**
 * 描述：UUID 工具类
 *
 * @author xhsf
 * @create 2021/1/30 16:45
 */
public class UuidUtils {

    /**
     * UUID 去掉 -
     *
     * @return UUID
     */
    public static String randomUuidTrim() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 生成一个 UUID
     *
     * @return UUID
     */
    public static String randomUuid() {
        return UUID.randomUUID().toString();
    }

}
