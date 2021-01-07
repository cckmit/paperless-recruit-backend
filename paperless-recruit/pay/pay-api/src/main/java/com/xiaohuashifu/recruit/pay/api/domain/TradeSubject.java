package com.xiaohuashifu.recruit.pay.api.domain;

import com.xiaohuashifu.recruit.common.domain.Domain;
import lombok.NonNull;
import lombok.Value;

import javax.validation.ValidationException;

/**
 * 描述：订单主题
 *      长度在1-128之间
 *
 * @author xhsf
 * @create 2021/1/7 15:58
 */
@Value
public class TradeSubject implements Domain {

    /**
     * 订单主题最小长度
     */
    public static final int MIN_ORDER_SUBJECT_LENGTH = 1;

    /**
     * 订单主题最大长度
     */
    public static final int MAX_ORDER_SUBJECT_LENGTH = 128;

    String value;

    public TradeSubject(@NonNull String subject) {
        if (subject.length() < MIN_ORDER_SUBJECT_LENGTH || subject.length() > MAX_ORDER_SUBJECT_LENGTH) {
            throw new ValidationException();
        }
        this.value = subject;
    }

    @Override
    public String toString() {
        return getValue();
    }
}
