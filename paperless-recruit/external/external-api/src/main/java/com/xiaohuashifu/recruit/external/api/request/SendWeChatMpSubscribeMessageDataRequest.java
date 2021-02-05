package com.xiaohuashifu.recruit.external.api.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 描述：订阅消息 TemplateData 的值
 *
 * @author xhsf
 * @create 2020/12/9 19:15
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SendWeChatMpSubscribeMessageDataRequest implements Serializable {

    /**
     * 订阅消息模板数据的值
     */
    @NotBlank
    private String value;

}
