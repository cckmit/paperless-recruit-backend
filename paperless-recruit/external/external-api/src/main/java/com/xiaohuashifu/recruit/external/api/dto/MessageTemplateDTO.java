package com.xiaohuashifu.recruit.external.api.dto;

import java.io.Serializable;
import java.util.Map;

/**
 * 描述: 封装微信小程序消息模板的各种数据
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-31 18:58
 */
public class MessageTemplateDTO implements Serializable {

    /**
     * 接收者（用户）的 openid
     */
    private String touser;

    /**
     * 所需下发的模板消息的id
     */
    private String template_id;

    /**
     * 点击模板卡片后的跳转页面，仅限本小程序内的页面。支持带参数,（示例index?foo=bar）。该字段不填则模板无跳转。
     */
    private String page;

    /**
     * 表单提交场景下，为 submit 事件带上的 formId；支付场景下，为本次支付的 prepay_id
     */
    private String form_id;

    /**
     * 模板内容，不填则下发空模板。
     */
    private Map<String, MessageTemplateDataDTO> data;

    /**
     *模板需要放大的关键词，不填则默认无放大
     */
    private String emphasis_keyword;

    public MessageTemplateDTO() {
    }

    public MessageTemplateDTO(String touser, String template_id, String page, String form_id,
                              Map<String, MessageTemplateDataDTO> data, String emphasis_keyword) {
        this.touser = touser;
        this.template_id = template_id;
        this.page = page;
        this.form_id = form_id;
        this.data = data;
        this.emphasis_keyword = emphasis_keyword;
    }

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getForm_id() {
        return form_id;
    }

    public void setForm_id(String form_id) {
        this.form_id = form_id;
    }

    public Map<String, MessageTemplateDataDTO> getData() {
        return data;
    }

    public void setData(Map<String, MessageTemplateDataDTO> data) {
        this.data = data;
    }

    public String getEmphasis_keyword() {
        return emphasis_keyword;
    }

    public void setEmphasis_keyword(String emphasis_keyword) {
        this.emphasis_keyword = emphasis_keyword;
    }

    @Override
    public String toString() {
        return "MessageTemplateDTO{" +
                "touser='" + touser + '\'' +
                ", template_id='" + template_id + '\'' +
                ", page='" + page + '\'' +
                ", form_id='" + form_id + '\'' +
                ", data=" + data +
                ", emphasis_keyword='" + emphasis_keyword + '\'' +
                '}';
    }


    public static final class Builder {
        private String touser;
        private String template_id;
        private String page;
        private String form_id;
        private Map<String, MessageTemplateDataDTO> data;
        private String emphasis_keyword;

        public Builder() {
        }

        public Builder touser(String touser) {
            this.touser = touser;
            return this;
        }

        public Builder template_id(String template_id) {
            this.template_id = template_id;
            return this;
        }

        public Builder page(String page) {
            this.page = page;
            return this;
        }

        public Builder form_id(String form_id) {
            this.form_id = form_id;
            return this;
        }

        public Builder data(Map<String, MessageTemplateDataDTO> data) {
            this.data = data;
            return this;
        }

        public Builder emphasis_keyword(String emphasis_keyword) {
            this.emphasis_keyword = emphasis_keyword;
            return this;
        }

        public MessageTemplateDTO build() {
            MessageTemplateDTO messageTemplateDTO = new MessageTemplateDTO();
            messageTemplateDTO.setTouser(touser);
            messageTemplateDTO.setTemplate_id(template_id);
            messageTemplateDTO.setPage(page);
            messageTemplateDTO.setForm_id(form_id);
            messageTemplateDTO.setData(data);
            messageTemplateDTO.setEmphasis_keyword(emphasis_keyword);
            return messageTemplateDTO;
        }
    }
}
