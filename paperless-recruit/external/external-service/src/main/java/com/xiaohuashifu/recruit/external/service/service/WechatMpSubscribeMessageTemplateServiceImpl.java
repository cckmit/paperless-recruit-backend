package com.xiaohuashifu.recruit.external.service.service;

import com.github.dozermapper.core.Mapper;
import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.recruit.common.result.ErrorCodeEnum;
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
     * @errorCode InvalidParameter: 请求参数格式错误
     *              OperationConflict: 存在相同的模板编号
     *
     * @param wechatMpSubscribeMessageTemplateDTO WechatMpSubscribeMessageTemplateDTO
     * @return WechatMpSubscribeMessageTemplateDTO
     */
    @Override
    public Result<WechatMpSubscribeMessageTemplateDTO> saveWechatMpSubscribeMessageTemplate(
            WechatMpSubscribeMessageTemplateDTO wechatMpSubscribeMessageTemplateDTO) {
        // 查看是否存在相同的模板 id
        int count = wechatMpSubscribeMessageTemplateMapper.countByTemplateId(
                wechatMpSubscribeMessageTemplateDTO.getTemplateId());
        if (count > 0) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT, "This template id already exists.");
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
        wechatMpSubscribeMessageTemplateMapper.insertWechatMpSubscribeMessageTemplate(wechatMpSubscribeMessageTemplateDO);
        return getWechatMpSubscribeMessageTemplate(wechatMpSubscribeMessageTemplateDO.getId());
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
    @Override
    public Result<WechatMpSubscribeMessageTemplateDTO> getWechatMpSubscribeMessageTemplate(Long id) {
        WechatMpSubscribeMessageTemplateDO wechatMpSubscribeMessageTemplateDO =
                wechatMpSubscribeMessageTemplateMapper.getWechatMpSubscribeMessageTemplate(id);
        if (wechatMpSubscribeMessageTemplateDO == null) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_FOUND);
        }
        return Result.success(
                mapper.map(wechatMpSubscribeMessageTemplateDO, WechatMpSubscribeMessageTemplateDTO.class));
    }

    /**
     * 获取模板通过 query 参数
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *
     * @param query 查询参数
     * @return WechatMpSubscribeMessageTemplateDTO 可能返回空列表
     */
    @Override
    public Result<PageInfo<WechatMpSubscribeMessageTemplateDTO>> listWechatMpSubscribeMessageTemplates(
            WechatMpSubscribeMessageTemplateQuery query) {
        // 获取 WechatMpSubscribeMessageTemplateDO
        List<WechatMpSubscribeMessageTemplateDO> wechatMpSubscribeMessageTemplateDOList =
                wechatMpSubscribeMessageTemplateMapper.listWechatMpSubscribeMessageTemplates(query);

        // 转换成 WechatMpSubscribeMessageTemplateDTO
        List<WechatMpSubscribeMessageTemplateDTO> wechatMpSubscribeMessageTemplateDTOList =
                wechatMpSubscribeMessageTemplateDOList
                .stream()
                .map(wechatMpSubscribeMessageTemplateDO ->
                        mapper.map(wechatMpSubscribeMessageTemplateDO, WechatMpSubscribeMessageTemplateDTO.class))
                .collect(Collectors.toList());

        // 包装 PageInfo
        PageInfo<WechatMpSubscribeMessageTemplateDTO> pageInfo =
                new PageInfo<>(wechatMpSubscribeMessageTemplateDTOList);
        return Result.success(pageInfo);
    }

    /**
     * 更新模板，这是一个较广的更新接口，请小心使用
     *
     * @errorCode InvalidParameter: 请求参数格式错误 | 模板不存在 | 不能每个域都为 null
     *
     * @param wechatMpSubscribeMessageTemplateDTO WechatMpSubscribeMessageTemplateDTO
     * @return WechatMpSubscribeMessageTemplateDTO
     */
    @Override
    public Result<WechatMpSubscribeMessageTemplateDTO> updateWechatMpSubscribeMessageTemplate(
            WechatMpSubscribeMessageTemplateDTO wechatMpSubscribeMessageTemplateDTO) {
        // 判断该编号的模板存不存在
        int count = wechatMpSubscribeMessageTemplateMapper.count(wechatMpSubscribeMessageTemplateDTO.getId());
        if (count < 1) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER, "This template does not exist.");
        }

        // 配置更新参数
        WechatMpSubscribeMessageTemplateDO wechatMpSubscribeMessageTemplateDO =
                new WechatMpSubscribeMessageTemplateDO.Builder()
                        .appName(wechatMpSubscribeMessageTemplateDTO.getApp())
                        .templateId(wechatMpSubscribeMessageTemplateDTO.getTemplateId())
                        .title(wechatMpSubscribeMessageTemplateDTO.getTitle())
                        .type(wechatMpSubscribeMessageTemplateDTO.getType())
                        .description(wechatMpSubscribeMessageTemplateDTO.getDescription())
                        .status(wechatMpSubscribeMessageTemplateDTO.getStatus())
                        .build();

        // 检查是否每个域都为 null
        if (ObjectUtils.allFieldIsNull(wechatMpSubscribeMessageTemplateDO)) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER, "At least one non null parameter.");
        }

        // 添加 id 属性
        wechatMpSubscribeMessageTemplateDO.setId(wechatMpSubscribeMessageTemplateDTO.getId());

        // 更新到数据库
        wechatMpSubscribeMessageTemplateMapper.updateWechatMpSubscribeMessageTemplate(
                wechatMpSubscribeMessageTemplateDO);
        return getWechatMpSubscribeMessageTemplate(wechatMpSubscribeMessageTemplateDTO.getId());
    }
}
