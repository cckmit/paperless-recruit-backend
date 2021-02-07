package com.xiaohuashifu.recruit.external.api.service;

import com.xiaohuashifu.recruit.common.exception.NotFoundServiceException;
import com.xiaohuashifu.recruit.common.exception.unprocessable.DuplicateServiceException;
import com.xiaohuashifu.recruit.common.query.QueryResult;
import com.xiaohuashifu.recruit.external.api.dto.WeChatMpSubscribeMessageTemplateDTO;
import com.xiaohuashifu.recruit.external.api.query.WeChatMpSubscribeMessageTemplateQuery;
import com.xiaohuashifu.recruit.external.api.request.CreateWeChatMpSubscribeMessageTemplateRequest;
import com.xiaohuashifu.recruit.external.api.request.UpdateWeChatMpSubscribeMessageTemplateRequest;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * 描述：微信小程序订阅消息模板的服务
 *
 * @private 内部服务
 *
 * @author: xhsf
 * @create: 2020/11/22 00:39
 */
public interface WeChatMpSubscribeMessageTemplateService {

    /**
     * 添加模板
     *
     * @param request CreateWeChatMpSubscribeMessageTemplateRequest
     * @return WeChatMpSubscribeMessageTemplateDTO
     */
    WeChatMpSubscribeMessageTemplateDTO createWeChatMpSubscribeMessageTemplate(
            @NotNull CreateWeChatMpSubscribeMessageTemplateRequest request) throws DuplicateServiceException;

    /**
     * 获取模板
     *
     * @param id 模板编号
     * @return WeChatMpSubscribeMessageTemplateDTO
     */
    WeChatMpSubscribeMessageTemplateDTO getWeChatMpSubscribeMessageTemplate(@NotNull @Positive Long id)
            throws NotFoundServiceException;

    /**
     * 获取模板通过 query 参数
     *
     * @param query 查询参数
     * @return WeChatMpSubscribeMessageTemplateDTO 可能返回空列表
     */
    QueryResult<WeChatMpSubscribeMessageTemplateDTO> listWeChatMpSubscribeMessageTemplates(
            @NotNull WeChatMpSubscribeMessageTemplateQuery query);

    /**
     * 更新模板，这是一个较广的更新接口，请小心使用
     *
     * @param request UpdateWeChatMpSubscribeMessageTemplateRequest
     * @return WeChatMpSubscribeMessageTemplateDTO
     */
    WeChatMpSubscribeMessageTemplateDTO updateWeChatMpSubscribeMessageTemplate(
            @NotNull UpdateWeChatMpSubscribeMessageTemplateRequest request);

}
