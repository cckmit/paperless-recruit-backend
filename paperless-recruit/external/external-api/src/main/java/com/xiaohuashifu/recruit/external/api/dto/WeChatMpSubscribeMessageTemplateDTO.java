package com.xiaohuashifu.recruit.external.api.dto;

import com.xiaohuashifu.recruit.common.constant.AppEnum;
import com.xiaohuashifu.recruit.external.api.constant.WeChatMpSubscribeMessageTemplateStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 描述：微信订阅消息模板的传输对象
 *
 * @author: xhsf
 * @create: 2020/11/22 00:33
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WeChatMpSubscribeMessageTemplateDTO implements Serializable {

    /**
     * 模板编号，项目产生的
     */
    private Long id;

    /**
     * 模板所属微信小程序
     */
    private AppEnum appName;

    /**
     * 模板编号，微信小程序平台的
     */
    private String templateId;

    /**
     * 模板标题
     */
    private String title;

    /**
     * 类目
     */
    private String type;

    /**
     * 描述
     */
    private String description;

    /**
     * 模板的状态
     */
    private WeChatMpSubscribeMessageTemplateStatusEnum status;

}
