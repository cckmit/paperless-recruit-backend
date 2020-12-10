package com.xiaohuashifu.recruit.external.api.po;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 描述：订阅消息 TemplateData 的值
 *
 * @author xhsf
 * @create 2020/12/9 19:15
 */
public class SendWeChatMpSubscribeMessageDataPO implements Serializable {

    /**
     * 订阅消息模板数据的值
     */
    @NotBlank(message = "The value can't be blank.")
    private String value;

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
