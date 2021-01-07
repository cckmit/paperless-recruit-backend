package com.xiaohuashifu.recruit.pay.api.domain;

import com.xiaohuashifu.recruit.common.domain.Domain;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

/**
 * 描述：序号抽象类
 *
 * @author xhsf
 * @create 2021/1/7 00:51
 */
@Getter
@EqualsAndHashCode
public abstract class SerialNumber implements Domain {

    String value;

    public SerialNumber(@NonNull String serialNumber) {
        validate(serialNumber);
        this.value = serialNumber;
    }

    protected abstract void validate(String serialNumber);

    @Override
    public String toString() {
        return getValue();
    }

}
