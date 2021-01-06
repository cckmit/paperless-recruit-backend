package com.xiaohuashifu.recruit.pay.api.constant;

/**
 * 描述：支付相关常量
 *
 * @author xhsf
 * @create 2021/1/6 21:51
 */
public class PayConstants {

    /**
     * 订单描述最大长度
     */
    public static final int MAX_DESCRIPTION_LENGTH = 128;

    /**
     * 最小过期时间，1分钟
     */
    public static final int MIN_EXPIRE_TIME = 1;

    /**
     * 最大过期时间，15天
     */
    public static final int MAX_EXPIRE_TIME = 21600;

    /**
     * 最小业务号长度
     */
    public static final int MIN_BUSINESS_NUMBER_LENGTH = 6;

    /**
     * 最大业务号长度
     */
    public static final int MAX_BUSINESS_NUMBER_LENGTH = 32;

}
