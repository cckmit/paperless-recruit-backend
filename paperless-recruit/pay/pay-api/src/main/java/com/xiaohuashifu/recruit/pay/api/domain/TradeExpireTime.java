package com.xiaohuashifu.recruit.pay.api.domain;

import lombok.Value;

import javax.validation.ValidationException;

/**
 * 描述：订单过期时间，单位分
 *
 * @author xhsf
 * @create 2021/1/7 16:06
 */
@Value
public class TradeExpireTime {

    /**
     * 最小过期时间，1分钟
     */
    public static final int MIN_EXPIRE_TIME = 1;

    /**
     * 最大过期时间，15天
     */
    public static final int MAX_EXPIRE_TIME = 21600;

    int value;

    public TradeExpireTime(int expireTime) {
        if (expireTime < MIN_EXPIRE_TIME || expireTime > MAX_EXPIRE_TIME) {
            throw new ValidationException();
        }
        this.value = expireTime;
    }

    @Override
    public String toString() {
        return String.valueOf(getValue());
    }

}
