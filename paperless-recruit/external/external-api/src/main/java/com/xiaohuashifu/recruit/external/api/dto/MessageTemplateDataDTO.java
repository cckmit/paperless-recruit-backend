package com.xiaohuashifu.recruit.external.api.dto;

/**
 * 描述: 封装模板信息Data
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-09-01 1:42
 */
public class MessageTemplateDataDTO {

    private String value;

    public MessageTemplateDataDTO() {
    }

    public MessageTemplateDataDTO(String value) {
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
        return "MessageTemplateDataDTO{" +
                "value='" + value + '\'' +
                '}';
    }

}
