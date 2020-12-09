package com.xiaohuashifu.recruit.external.api.po;

import java.io.Serializable;

/**
 * 描述：封装订阅消息 Data
 *
 * @author xhsf
 * @create 2020/12/9 19:15
 */
public class SendWeChatMpSubscribeMessageDataPO implements Serializable {
    private String value;

    public SendWeChatMpSubscribeMessageDataPO() {}

    public SendWeChatMpSubscribeMessageDataPO(String value) {
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
        return "SendWeChatMpSubscribeMessageDataPO{" +
                "value='" + value + '\'' +
                '}';
    }
}
