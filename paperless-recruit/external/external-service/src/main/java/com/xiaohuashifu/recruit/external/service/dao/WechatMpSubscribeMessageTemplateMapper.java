package com.xiaohuashifu.recruit.external.service.dao;

import com.xiaohuashifu.recruit.external.api.query.WechatMpSubscribeMessageTemplateQuery;
import com.xiaohuashifu.recruit.external.service.pojo.do0.WechatMpSubscribeMessageTemplateDO;

import java.util.List;

/**
 * 描述：微信订阅消息模板数据库映射层
 *
 * @author: xhsf
 * @create: 2020/11/11 15:12
 */
public interface WechatMpSubscribeMessageTemplateMapper {

    int insertWechatMpSubscribeMessageTemplate(WechatMpSubscribeMessageTemplateDO wechatMpSubscribeMessageTemplateDO);

    WechatMpSubscribeMessageTemplateDO getWechatMpSubscribeMessageTemplate(Long id);

    List<WechatMpSubscribeMessageTemplateDO> listWechatMpSubscribeMessageTemplates(
            WechatMpSubscribeMessageTemplateQuery query);

    int count(Long id);

    int countByTemplateId(String templateId);

    int updateWechatMpSubscribeMessageTemplate(WechatMpSubscribeMessageTemplateDO wechatMpSubscribeMessageTemplateDO);

}
