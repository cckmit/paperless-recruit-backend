package com.xiaohuashifu.recruit.external.service.assembler;

import com.xiaohuashifu.recruit.external.api.dto.WeChatMpSubscribeMessageTemplateDTO;
import com.xiaohuashifu.recruit.external.api.request.CreateWeChatMpSubscribeMessageTemplateRequest;
import com.xiaohuashifu.recruit.external.api.request.UpdateWeChatMpSubscribeMessageTemplateRequest;
import com.xiaohuashifu.recruit.external.service.pojo.do0.WeChatMpSubscribeMessageTemplateDO;
import org.mapstruct.Mapper;

/**
 * 描述：WeChatMpSubscribeMessageTemplate 的装配器
 *
 * @author xhsf
 * @create 2021/2/7 16:21
 */
@Mapper(componentModel = "spring")
public interface WeChatMpSubscribeMessageTemplateAssembler {

    WeChatMpSubscribeMessageTemplateDTO weChatMpSubscribeMessageTemplateDOToWeChatMpSubscribeMessageTemplateDTO(
            WeChatMpSubscribeMessageTemplateDO weChatMpSubscribeMessageTemplateDO);

    WeChatMpSubscribeMessageTemplateDO createWeChatMpSubscribeMessageTemplateRequestToWeChatMpSubscribeMessageTemplateDO(
            CreateWeChatMpSubscribeMessageTemplateRequest createWeChatMpSubscribeMessageTemplateRequest);

    WeChatMpSubscribeMessageTemplateDO updateWeChatMpSubscribeMessageTemplateRequestToWeChatMpSubscribeMessageTemplateDO(
            UpdateWeChatMpSubscribeMessageTemplateRequest updateWeChatMpSubscribeMessageTemplateRequest);
}
