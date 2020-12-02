package com.xiaohuashifu.recruit.external.api.service;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.external.api.dto.WechatMpSubscribeMessageTemplateDTO;
import com.xiaohuashifu.recruit.external.api.query.WechatMpSubscribeMessageTemplateQuery;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * 描述：微信小程序订阅消息模板的服务
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/22 00:39
 */
public interface WechatMpSubscribeMessageTemplateService {

    @interface SaveWechatMpSubscribeMessageTemplate{}
    /**
     * 添加模板
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *              OperationConflict: 存在相同的模板编号
     *
     * @param wechatMpSubscribeMessageTemplateDTO WechatMpSubscribeMessageTemplateDTO
     * @return WechatMpSubscribeMessageTemplateDTO
     */
    default Result<WechatMpSubscribeMessageTemplateDTO> saveWechatMpSubscribeMessageTemplate(
            @NotNull WechatMpSubscribeMessageTemplateDTO wechatMpSubscribeMessageTemplateDTO) {
        throw new UnsupportedOperationException();
    }

    /**
     * 获取模板
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *              InvalidParameter.NotFound: 该编号的模板不存在
     *
     * @param id 模板编号
     * @return WechatMpSubscribeMessageTemplateDTO
     */
    default Result<WechatMpSubscribeMessageTemplateDTO> getWechatMpSubscribeMessageTemplate(
            @NotNull @Positive Long id) {
        throw new UnsupportedOperationException();
    }

    /**
     * 获取模板通过query参数
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *
     * @param query 查询参数
     * @return WechatMpSubscribeMessageTemplateDTO 可能返回空列表
     */
    default Result<PageInfo<WechatMpSubscribeMessageTemplateDTO>> listWechatMpSubscribeMessageTemplates(
            @NotNull WechatMpSubscribeMessageTemplateQuery query) {
        throw new UnsupportedOperationException();
    }

    @interface UpdateWechatMpSubscribeMessageTemplate{}
    /**
     * 更新模板，这是一个较广的更新接口，请小心使用
     *
     * @errorCode InvalidParameter: 请求参数格式错误 | 模板不存在 | 不能每个域都为null
     *
     * @param wechatMpSubscribeMessageTemplateDTO WechatMpSubscribeMessageTemplateDTO
     * @return WechatMpSubscribeMessageTemplateDTO
     */
    default Result<WechatMpSubscribeMessageTemplateDTO> updateWechatMpSubscribeMessageTemplate(
            @NotNull WechatMpSubscribeMessageTemplateDTO wechatMpSubscribeMessageTemplateDTO) {
        throw new UnsupportedOperationException();
    }

}
