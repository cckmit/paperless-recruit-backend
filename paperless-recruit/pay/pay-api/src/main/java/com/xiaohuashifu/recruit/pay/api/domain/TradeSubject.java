package com.xiaohuashifu.recruit.pay.api.domain;

import lombok.Data;
import lombok.NonNull;

import java.io.Serializable;

/**
 * 描述：订单主题
 *      长度在1-128之间
 *
 * @author xhsf
 * @create 2021/1/7 15:58
 */
@Data
public class TradeSubject implements Serializable {

    /**
     * 订单主题最大长度
     */
    public static final int MAX_ORDER_SUBJECT_LENGTH = 128;

    private String value;

    public TradeSubject(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return getValue();
    }
}
