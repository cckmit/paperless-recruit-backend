package com.xiaohuashifu.recruit.external.api.dto;

import java.io.Serializable;

/**
 * 描述: 封装订阅消息 Data
 *
 * @author xhsf
 * @create 2019-09-01 1:42
 */
public class SubscribeTemplateDataDTO implements Serializable {

    private String value;

    public SubscribeTemplateDataDTO(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "SubscribeTemplateDataDTO{" +
                "value='" + value + '\'' +
                '}';
    }

}
