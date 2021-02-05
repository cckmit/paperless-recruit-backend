package com.xiaohuashifu.recruit.external.api.request;

import com.xiaohuashifu.recruit.common.constant.AppEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.Map;

/**
 * 描述：发送订阅消息的请求
 *
 * @author xhsf
 * @create 2020/12/9 17:46
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SendWeChatMpSubscribeMessageRequest implements Serializable {
    /**
     * 微信小程序类型
     */
    @NotNull
    private AppEnum app;

    /**
     * 接收者（用户）的用户编号
     */
    @NotNull
    @Positive
    private Long userId;

    /**
     * 所需下发的模板消息的 id
     */
    @NotBlank
    private String templateId;

    /**
     * 点击模板卡片后的跳转页面，仅限本小程序内的页面。支持带参数,（示例 index?foo=bar）。该字段不填则模板无跳转。
     */
    private String page;

    /**
     * 模板内容，格式形如 { "key1": { "value": any }, "key2": { "value": any } }
     */
    @NotEmpty
    private Map<String, SendWeChatMpSubscribeMessageDataRequest> templateData;

    /**
     * 	跳转小程序类型：developer 为开发版；trial 为体验版；formal 为正式版；默认为正式版
     */
    private String mpType;

    /**
     * 进入小程序查看的语言类型，支持 zh_CN (简体中文)、en_US (英文)、zh_HK (繁体中文)、zh_TW (繁体中文)，默认为 zh_CN
     */
    private String language;

}
