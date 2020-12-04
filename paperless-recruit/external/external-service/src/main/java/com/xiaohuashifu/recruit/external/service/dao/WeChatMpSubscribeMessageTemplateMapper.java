package com.xiaohuashifu.recruit.external.service.dao;

import com.xiaohuashifu.recruit.external.api.query.WeChatMpSubscribeMessageTemplateQuery;
import com.xiaohuashifu.recruit.external.service.pojo.do0.WeChatMpSubscribeMessageTemplateDO;

import java.util.List;

/**
 * 描述：微信订阅消息模板数据库映射层
 *
 * @author: xhsf
 * @create: 2020/11/11 15:12
 */
public interface WeChatMpSubscribeMessageTemplateMapper {

    int insertWeChatMpSubscribeMessageTemplate(WeChatMpSubscribeMessageTemplateDO weChatMpSubscribeMessageTemplateDO);

    WeChatMpSubscribeMessageTemplateDO getWeChatMpSubscribeMessageTemplate(Long id);

    List<WeChatMpSubscribeMessageTemplateDO> listWeChatMpSubscribeMessageTemplates(
            WeChatMpSubscribeMessageTemplateQuery query);

    int count(Long id);

    int countByTemplateId(String templateId);

    int updateWeChatMpSubscribeMessageTemplate(WeChatMpSubscribeMessageTemplateDO weChatMpSubscribeMessageTemplateDO);

}
