package com.xiaohuashifu.recruit.pay.api.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 描述：金额，单位分
 *
 * @author xhsf
 * @create 2021/1/7 12:43
 */
@Data
public class Money implements Serializable {

    int value;

    public Money(int value) {
//        if (money < 0) {
//            throw new ValidationException();
//        }
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(getValue());
    }

}
