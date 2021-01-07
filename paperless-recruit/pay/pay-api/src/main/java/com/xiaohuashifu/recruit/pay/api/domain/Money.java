package com.xiaohuashifu.recruit.pay.api.domain;

import com.xiaohuashifu.recruit.common.domain.Domain;
import lombok.Value;

import javax.validation.ValidationException;

/**
 * 描述：金额，单位分
 *
 * @author xhsf
 * @create 2021/1/7 12:43
 */
@Value
public class Money implements Domain {

    int value;

    public Money(int money) {
        if (money < 0) {
            throw new ValidationException();
        }
        this.value = money;
    }

    @Override
    public String toString() {
        return String.valueOf(getValue());
    }

}
