package com.xiaohuashifu.recruit.external.service.service;

import com.github.dozermapper.core.Mapper;
import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.recruit.common.result.ErrorCode;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.common.util.ObjectUtils;
import com.xiaohuashifu.recruit.external.api.dto.WechatMpSubscribeMessageTemplateDTO;
import com.xiaohuashifu.recruit.external.api.query.WechatMpSubscribeMessageTemplateQuery;
import com.xiaohuashifu.recruit.external.api.service.WechatMpSubscribeMessageTemplateService;
import com.xiaohuashifu.recruit.external.service.dao.WechatMpSubscribeMessageTemplateMapper;
import com.xiaohuashifu.recruit.external.service.pojo.do0.WechatMpSubscribeMessageTemplateDO;
import org.apache.dubbo.config.annotation.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    private final Mapper mapper;

    public WechatMpSubscribeMessageTemplateServiceImpl(
            WechatMpSubscribeMessageTemplateMapper wechatMpSubscribeMessageTemplateMapper, Mapper mapper) {
        this.wechatMpSubscribeMessageTemplateMapper = wechatMpSubscribeMessageTemplateMapper;
        this.mapper = mapper;
    }

    /**
     * 添加模板
     *
     * @param wechatMpSubscribeMessageTemplateDTO WechatMpSubscribeMessageTemplateDTO
     * @return WechatMpSubscribeMessageTemplateDTO
     */
    @Override
    public Result<WechatMpSubscribeMessageTemplateDTO> saveWechatMpSubscribeMessageTemplate(
            WechatMpSubscribeMessageTemplateDTO wechatMpSubscribeMessageTemplateDTO) {
        // 查看是否存在相同的模板id
        int count = wechatMpSubscribeMessageTemplateMapper.countByTemplateId(
                wechatMpSubscribeMessageTemplateDTO.getTemplateId());
        if (count > 0) {
            return Result.fail(ErrorCode.INVALID_PARAMETER, "This template id already exists.");
        }

        // 添加到数据库
        WechatMpSubscribeMessageTemplateDO wechatMpSubscribeMessageTemplateDO =
                new WechatMpSubscribeMessageTemplateDO.Builder()
                        .appName(wechatMpSubscribeMessageTemplateDTO.getApp())
                        .templateId(wechatMpSubscribeMessageTemplateDTO.getTemplateId())
                        .title(wechatMpSubscribeMessageTemplateDTO.getTitle())
                        .type(wechatMpSubscribeMessageTemplateDTO.getType())
                        .description(wechatMpSubscribeMessageTemplateDTO.getDescription())
                        .status(wechatMpSubscribeMessageTemplateDTO.getStatus())
                        .build();
        wechatMpSubscribeMessageTemplateMapper.saveWechatMpSubscribeMessageTemplate(wechatMpSubscribeMessageTemplateDO);
        return getWechatMpSubscribeMessageTemplate(wechatMpSubscribeMessageTemplateDO.getId());
    }

    /**
     * 获取模板
     *
     * @param id 模板编号
     * @return WechatMpSubscribeMessageTemplateDTO
     */
    @Override
    public Result<WechatMpSubscribeMessageTemplateDTO> getWechatMpSubscribeMessageTemplate(Long id) {
        WechatMpSubscribeMessageTemplateDO wechatMpSubscribeMessageTemplateDO =
                wechatMpSubscribeMessageTemplateMapper.getWechatMpSubscribeMessageTemplate(id);
        if (wechatMpSubscribeMessageTemplateDO == null) {
            return Result.fail(ErrorCode.INVALID_PARAMETER_NOT_FOUND);
        }
        return Result.success(
                mapper.map(wechatMpSubscribeMessageTemplateDO, WechatMpSubscribeMessageTemplateDTO.class));
    }

    /**
     * 获取模板通过query参数
     *
     * @param query 查询参数
     * @return WechatMpSubscribeMessageTemplateDTO
     */
    @Override
    public Result<PageInfo<WechatMpSubscribeMessageTemplateDTO>> getWechatMpSubscribeMessageTemplate(
            WechatMpSubscribeMessageTemplateQuery query) {
        // 获取WechatMpSubscribeMessageTemplateDO
        List<WechatMpSubscribeMessageTemplateDO> wechatMpSubscribeMessageTemplateDOList =
                wechatMpSubscribeMessageTemplateMapper.getWechatMpSubscribeMessageTemplateByQuery(query);

        // 转换成WechatMpSubscribeMessageTemplateDTO
        List<WechatMpSubscribeMessageTemplateDTO> wechatMpSubscribeMessageTemplateDTOList =
                wechatMpSubscribeMessageTemplateDOList
                .stream()
                .map(wechatMpSubscribeMessageTemplateDO ->
                        mapper.map(wechatMpSubscribeMessageTemplateDO, WechatMpSubscribeMessageTemplateDTO.class))
                .collect(Collectors.toList());

        // 包装PageInfo
        PageInfo<WechatMpSubscribeMessageTemplateDTO> pageInfo =
                new PageInfo<>(wechatMpSubscribeMessageTemplateDTOList);
        return Result.success(pageInfo);
    }

    /**
     * 更新模板
     *
     * @param wechatMpSubscribeMessageTemplateDTO WechatMpSubscribeMessageTemplateDTO
     * @return WechatMpSubscribeMessageTemplateDTO
     */
    @Override
    public Result<WechatMpSubscribeMessageTemplateDTO> updateWechatMpSubscribeMessageTemplate(
            WechatMpSubscribeMessageTemplateDTO wechatMpSubscribeMessageTemplateDTO) {
        WechatMpSubscribeMessageTemplateDO wechatMpSubscribeMessageTemplateDO =
                new WechatMpSubscribeMessageTemplateDO.Builder()
                        .appName(wechatMpSubscribeMessageTemplateDTO.getApp())
                        .templateId(wechatMpSubscribeMessageTemplateDTO.getTemplateId())
                        .title(wechatMpSubscribeMessageTemplateDTO.getTitle())
                        .type(wechatMpSubscribeMessageTemplateDTO.getType())
                        .description(wechatMpSubscribeMessageTemplateDTO.getDescription())
                        .status(wechatMpSubscribeMessageTemplateDTO.getStatus())
                        .build();

        // 检查是否每个域都为null
        if (ObjectUtils.allFieldIsNull(wechatMpSubscribeMessageTemplateDO)) {
            return Result.fail(ErrorCode.INVALID_PARAMETER, "At least one non null parameter.");
        }

        // 添加id属性
        wechatMpSubscribeMessageTemplateDO.setId(wechatMpSubscribeMessageTemplateDTO.getId());

        // 更新到数据库
        wechatMpSubscribeMessageTemplateMapper.updateWechatMpSubscribeMessageTemplate(
                wechatMpSubscribeMessageTemplateDO);
        return getWechatMpSubscribeMessageTemplate(wechatMpSubscribeMessageTemplateDTO.getId());
    }
}
