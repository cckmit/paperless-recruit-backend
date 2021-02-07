package com.xiaohuashifu.recruit.external.service.pojo.do0;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xiaohuashifu.recruit.common.constant.AppEnum;
import com.xiaohuashifu.recruit.external.api.constant.WeChatMpSubscribeMessageTemplateStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 描述：WeChatMpSubscribeMessageTemplateDO
 *
 * @author: xhsf
 * @create: 2020/11/22 00:33
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("wechat_mp_subscribe_message_template")
public class WeChatMpSubscribeMessageTemplateDO {
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 模板编号
     */
    private String templateId;

    /**
     * 具体的微信小程序
     */
    private AppEnum appName;

    /**
     * 模板标题
     */
    private String title;

    /**
     * 类目
     */
    private String templateType;

    /**
     * 描述
     */
    private String description;

    /**
     * 状态
     */
    private WeChatMpSubscribeMessageTemplateStatusEnum templateStatus;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
