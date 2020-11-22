package com.xiaohuashifu.recruit.external.service.dao;

import com.xiaohuashifu.recruit.external.api.query.WechatMpSubscribeMessageTemplateQuery;
import com.xiaohuashifu.recruit.external.service.pojo.do0.WechatMpSubscribeMessageTemplateDO;

import java.util.List;

/**
 * 描述：
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/11 15:12
 */
public interface WechatMpSubscribeMessageTemplateMapper {

    int saveWechatMpSubscribeMessageTemplate(WechatMpSubscribeMessageTemplateDO wechatMpSubscribeMessageTemplateDO);

    WechatMpSubscribeMessageTemplateDO getWechatMpSubscribeMessageTemplate(Long id);

    List<WechatMpSubscribeMessageTemplateDO> getWechatMpSubscribeMessageTemplateByQuery(
            WechatMpSubscribeMessageTemplateQuery query);

    int countByTemplateId(String templateId);

    int updateWechatMpSubscribeMessageTemplate(WechatMpSubscribeMessageTemplateDO wechatMpSubscribeMessageTemplateDO);
}
