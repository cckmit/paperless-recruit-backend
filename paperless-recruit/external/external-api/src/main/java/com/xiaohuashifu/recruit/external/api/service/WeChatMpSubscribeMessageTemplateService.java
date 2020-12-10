package com.xiaohuashifu.recruit.external.api.service;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.external.api.dto.WeChatMpSubscribeMessageTemplateDTO;
import com.xiaohuashifu.recruit.external.api.po.SaveWeChatMpSubscribeMessageTemplatePO;
import com.xiaohuashifu.recruit.external.api.po.UpdateWeChatMpSubscribeMessageTemplatePO;
import com.xiaohuashifu.recruit.external.api.query.WeChatMpSubscribeMessageTemplateQuery;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * 描述：微信小程序订阅消息模板的服务
 *
 * @author: xhsf
 * @create: 2020/11/22 00:39
 */
public interface WeChatMpSubscribeMessageTemplateService {

    /**
     * 添加模板
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *              OperationConflict: 存在相同的模板编号
     *
     * @param saveWeChatMpSubscribeMessageTemplatePO 保存的参数对象
     * @return WeChatMpSubscribeMessageTemplateDTO
     */
    Result<WeChatMpSubscribeMessageTemplateDTO> saveWeChatMpSubscribeMessageTemplate(
            @NotNull(message = "The saveWeChatMpSubscribeMessageTemplatePO can't be null.")
                    SaveWeChatMpSubscribeMessageTemplatePO saveWeChatMpSubscribeMessageTemplatePO);

    /**
     * 获取模板
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *              InvalidParameter.NotFound: 该编号的模板不存在
     *
     * @param id 模板编号
     * @return WeChatMpSubscribeMessageTemplateDTO
     */
    Result<WeChatMpSubscribeMessageTemplateDTO> getWeChatMpSubscribeMessageTemplate(
            @NotNull(message = "The id can't be null.")
            @Positive(message = "The id must be greater than 0.") Long id);

    /**
     * 获取模板通过 query 参数
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *
     * @param query 查询参数
     * @return WeChatMpSubscribeMessageTemplateDTO 可能返回空列表
     */
    Result<PageInfo<WeChatMpSubscribeMessageTemplateDTO>> listWeChatMpSubscribeMessageTemplates(
            @NotNull(message = "The query can't be null.") WeChatMpSubscribeMessageTemplateQuery query);

    /**
     * 更新模板，这是一个较广的更新接口，请小心使用
     *
     * @errorCode InvalidParameter: 请求参数格式错误 | 模板不存在 | 不能每个域都为 null
     *
     * @param updateWeChatMpSubscribeMessageTemplatePO 更新的参数对象
     * @return WeChatMpSubscribeMessageTemplateDTO
     */
    Result<WeChatMpSubscribeMessageTemplateDTO> updateWeChatMpSubscribeMessageTemplate(
            @NotNull(message = "The updateWeChatMpSubscribeMessageTemplatePO can't be null.")
                    UpdateWeChatMpSubscribeMessageTemplatePO updateWeChatMpSubscribeMessageTemplatePO);

}
