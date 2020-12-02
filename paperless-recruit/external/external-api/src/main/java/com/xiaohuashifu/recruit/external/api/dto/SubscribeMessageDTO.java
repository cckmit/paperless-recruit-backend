package com.xiaohuashifu.recruit.external.api.dto;

import com.xiaohuashifu.recruit.external.api.service.WechatMpService;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Map;

/**
 * 描述: 封装微信小程序订阅消息的各种数据
 *
 * @author xhsf
 * @create 2020-11-21 18:58
 */
public class SubscribeMessageDTO implements Serializable {

    /**
     * 接收者（用户）的 openid
     */
    private String touser;

    /**
     * 所需下发的模板消息的 id
     */
    @NotBlank(groups = WechatMpService.SendSubscribeMessage.class)
    private String template_id;

    /**
     * 点击模板卡片后的跳转页面，仅限本小程序内的页面。支持带参数,（示例 index?foo=bar）。该字段不填则模板无跳转。
     */
    private String page;

    /**
     * 模板内容，格式形如 { "key1": { "value": any }, "key2": { "value": any } }
     */
    @NotEmpty(groups = WechatMpService.SendSubscribeMessage.class)
    private Map<String, SubscribeTemplateDataDTO> data;

    /**
     * 	跳转小程序类型：developer 为开发版；trial 为体验版；formal 为正式版；默认为正式版
     */
    private String miniprogram_state;

    /**
     * 进入小程序查看的语言类型，支持 zh_CN (简体中文)、en_US (英文)、zh_HK (繁体中文)、zh_TW (繁体中文)，默认为 zh_CN
     */
    private String lang;

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

    public Map<String, SubscribeTemplateDataDTO> getData() {
        return data;
    }

    public void setData(Map<String, SubscribeTemplateDataDTO> data) {
        this.data = data;
    }

    public String getMiniprogram_state() {
        return miniprogram_state;
    }

    public void setMiniprogram_state(String miniprogram_state) {
        this.miniprogram_state = miniprogram_state;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    @Override
    public String toString() {
        return "SubscribeMessageDTO{" +
                "touser='" + touser + '\'' +
                ", template_id='" + template_id + '\'' +
                ", page='" + page + '\'' +
                ", data=" + data +
                ", miniprogram_state='" + miniprogram_state + '\'' +
                ", lang='" + lang + '\'' +
                '}';
    }

    public static final class Builder {
        private String touser;
        private String template_id;
        private String page;
        private Map<String, SubscribeTemplateDataDTO> data;
        private String miniprogram_state;
        private String lang;

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

        public Builder data(Map<String, SubscribeTemplateDataDTO> data) {
            this.data = data;
            return this;
        }

        public Builder miniprogram_state(String miniprogram_state) {
            this.miniprogram_state = miniprogram_state;
            return this;
        }

        public Builder lang(String lang) {
            this.lang = lang;
            return this;
        }

        public SubscribeMessageDTO build() {
            SubscribeMessageDTO messageTemplateDTO = new SubscribeMessageDTO();
            messageTemplateDTO.setTouser(touser);
            messageTemplateDTO.setTemplate_id(template_id);
            messageTemplateDTO.setPage(page);
            messageTemplateDTO.setData(data);
            messageTemplateDTO.setMiniprogram_state(miniprogram_state);
            messageTemplateDTO.setLang(lang);
            return messageTemplateDTO;
        }
    }
}
