package com.xiaohuashifu.recruit.external.api.request;

import com.xiaohuashifu.recruit.common.constant.AppEnum;
import com.xiaohuashifu.recruit.external.api.constant.WeChatMpSubscribeMessageTemplateServiceConstants;
import com.xiaohuashifu.recruit.external.api.constant.WeChatMpSubscribeMessageTemplateStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 描述：更新 WeChatMpSubscribeMessageTemplate 的请求
 *
 * @author xhsf
 * @create 2020/12/9 17:34
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateWeChatMpSubscribeMessageTemplateRequest implements Serializable {

    /**
     * 模板编号，（项目数据库生成的）
     */
    @NotNull
    @Positive
    private Long id;

    /**
     * 微信小程序类型
     */
    private AppEnum app;

    /**
     * 模板编号
     */
    @Size(max = WeChatMpSubscribeMessageTemplateServiceConstants.MAX_SUBSCRIBE_MESSAGE_TEMPLATE_ID_LENGTH)
    private String templateId;

    /**
     * 模板标题，（微信小程序平台生成的）
     */
    @Size(max = WeChatMpSubscribeMessageTemplateServiceConstants.MAX_SUBSCRIBE_MESSAGE_TITLE_LENGTH)
    private String title;

    /**
     * 类目
     */
    @Size(max = WeChatMpSubscribeMessageTemplateServiceConstants.MAX_SUBSCRIBE_MESSAGE_TYPE_LENGTH)
    private String type;

    /**
     * 描述
     */
    @Size(max = WeChatMpSubscribeMessageTemplateServiceConstants.MAX_SUBSCRIBE_MESSAGE_DESCRIPTION_LENGTH)
    private String description;

    /**
     * 模板的状态
     */
    private WeChatMpSubscribeMessageTemplateStatusEnum status;

}
