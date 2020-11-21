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
     * @param query 查询参数
     * @return WechatMpSubscribeMessageTemplateDTO
     */
    default Result<PageInfo<WechatMpSubscribeMessageTemplateDTO>> getWechatMpSubscribeMessageTemplate(
            @NotNull WechatMpSubscribeMessageTemplateQuery query) {
        throw new UnsupportedOperationException();
    }

    @interface UpdateWechatMpSubscribeMessageTemplate{}
    /**
     * 更新模板
     *
     * @param wechatMpSubscribeMessageTemplateDTO WechatMpSubscribeMessageTemplateDTO
     * @return WechatMpSubscribeMessageTemplateDTO
     */
    default Result<WechatMpSubscribeMessageTemplateDTO> updateWechatMpSubscribeMessageTemplate(
            @NotNull WechatMpSubscribeMessageTemplateDTO wechatMpSubscribeMessageTemplateDTO) {
        throw new UnsupportedOperationException();
    }

}
