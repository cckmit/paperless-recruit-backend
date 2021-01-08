package com.xiaohuashifu.recruit.pay.api.constant;

import lombok.Getter;

import java.util.Objects;

/**
 * 描述：撤销订单时的行为
 *
 * @author xhsf
 * @create 2021/1/7 20:40
 */
@Getter
public enum CancelActionEnum {

    /**
     * 无动作
     */
    NONE(null),

    /**
     * 交易未支付，触发关闭交易动作，无退款
     */
    CLOSE("close"),

    /**
     * 交易已支付，触发交易退款动作
     */
    REFUND("refund");

    private final String action;

    CancelActionEnum(String action) {
        this.action = action;
    }

    public static String getActionName(String action) {
        for (CancelActionEnum value : values()) {
            if (Objects.equals(value.action, action)) {
                return value.name();
            }
        }
        return NONE.name();
    }

}
