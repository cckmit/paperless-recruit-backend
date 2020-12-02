package com.xiaohuashifu.recruit.common.result;

/**
 * 描述：错误码的工具类
 *
 * @author xhsf
 * @create 2020/12/2 17:17
 */
public class ErrorCodeUtils {

    /**
     * 判断一个字符串的 errorCode 是否和一个枚举类型的 errorCode 相同
     *
     * @param errorCodeString 字符串错误码
     * @param errorCodeEnum 枚举错误码
     * @return 是否相同
     */
    public static boolean equals(String errorCodeString, ErrorCodeEnum errorCodeEnum) {
        return errorCodeString.equals(errorCodeEnum.getCode());
    }

}
