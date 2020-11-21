package com.xiaohuashifu.recruit.external.service.service;

import com.xiaohuashifu.recruit.external.api.service.WechatMpSubscribeMessageTemplateService;
import com.xiaohuashifu.recruit.external.service.dao.WechatMpSubscribeMessageTemplateMapper;
import org.apache.dubbo.config.annotation.Service;

/**
 * 描述：微信小程序订阅消息模板的服务
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/22 01:32
 */
@Service
public class WechatMpSubscribeMessageTemplateServiceImpl implements WechatMpSubscribeMessageTemplateService {

    private final WechatMpSubscribeMessageTemplateMapper wechatMpSubscribeMessageTemplateMapper;

    public WechatMpSubscribeMessageTemplateServiceImpl(
            WechatMpSubscribeMessageTemplateMapper wechatMpSubscribeMessageTemplateMapper) {
        this.wechatMpSubscribeMessageTemplateMapper = wechatMpSubscribeMessageTemplateMapper;
    }
    
}
