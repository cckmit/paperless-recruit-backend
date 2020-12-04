package com.xiaohuashifu.recruit.external.service.service;

import com.github.dozermapper.core.Mapper;
import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.recruit.common.result.ErrorCodeEnum;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.common.util.ObjectUtils;
import com.xiaohuashifu.recruit.external.api.dto.WeChatMpSubscribeMessageTemplateDTO;
import com.xiaohuashifu.recruit.external.api.query.WeChatMpSubscribeMessageTemplateQuery;
import com.xiaohuashifu.recruit.external.api.service.WeChatMpSubscribeMessageTemplateService;
import com.xiaohuashifu.recruit.external.service.dao.WeChatMpSubscribeMessageTemplateMapper;
import com.xiaohuashifu.recruit.external.service.pojo.do0.WeChatMpSubscribeMessageTemplateDO;
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
public class WeChatMpSubscribeMessageTemplateServiceImpl implements WeChatMpSubscribeMessageTemplateService {

    private final WeChatMpSubscribeMessageTemplateMapper weChatMpSubscribeMessageTemplateMapper;

    private final Mapper mapper;

    public WeChatMpSubscribeMessageTemplateServiceImpl(
            WeChatMpSubscribeMessageTemplateMapper weChatMpSubscribeMessageTemplateMapper, Mapper mapper) {
        this.weChatMpSubscribeMessageTemplateMapper = weChatMpSubscribeMessageTemplateMapper;
        this.mapper = mapper;
    }

    /**
     * 添加模板
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *              OperationConflict: 存在相同的模板编号
     *
     * @param weChatMpSubscribeMessageTemplateDTO WeChatMpSubscribeMessageTemplateDTO
     * @return WeChatMpSubscribeMessageTemplateDTO
     */
    @Override
    public Result<WeChatMpSubscribeMessageTemplateDTO> saveWeChatMpSubscribeMessageTemplate(
            WeChatMpSubscribeMessageTemplateDTO weChatMpSubscribeMessageTemplateDTO) {
        // 查看是否存在相同的模板 id
        int count = weChatMpSubscribeMessageTemplateMapper.countByTemplateId(
                weChatMpSubscribeMessageTemplateDTO.getTemplateId());
        if (count > 0) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT, "This template id already exists.");
        }

        // 添加到数据库
        WeChatMpSubscribeMessageTemplateDO weChatMpSubscribeMessageTemplateDO =
                new WeChatMpSubscribeMessageTemplateDO.Builder()
                        .appName(weChatMpSubscribeMessageTemplateDTO.getApp())
                        .templateId(weChatMpSubscribeMessageTemplateDTO.getTemplateId())
                        .title(weChatMpSubscribeMessageTemplateDTO.getTitle())
                        .type(weChatMpSubscribeMessageTemplateDTO.getType())
                        .description(weChatMpSubscribeMessageTemplateDTO.getDescription())
                        .status(weChatMpSubscribeMessageTemplateDTO.getStatus())
                        .build();
        weChatMpSubscribeMessageTemplateMapper.insertWeChatMpSubscribeMessageTemplate(weChatMpSubscribeMessageTemplateDO);
        return getWeChatMpSubscribeMessageTemplate(weChatMpSubscribeMessageTemplateDO.getId());
    }

    /**
     * 获取模板
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *              InvalidParameter.NotFound: 该编号的模板不存在
     *
     * @param id 模板编号
     * @return WeChatMpSubscribeMessageTemplateDTO
     */
    @Override
    public Result<WeChatMpSubscribeMessageTemplateDTO> getWeChatMpSubscribeMessageTemplate(Long id) {
        WeChatMpSubscribeMessageTemplateDO weChatMpSubscribeMessageTemplateDO =
                weChatMpSubscribeMessageTemplateMapper.getWeChatMpSubscribeMessageTemplate(id);
        if (weChatMpSubscribeMessageTemplateDO == null) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_FOUND);
        }
        WeChatMpSubscribeMessageTemplateDTO weChatMpSubscribeMessageTemplateDTO =
                mapper.map(weChatMpSubscribeMessageTemplateDO, WeChatMpSubscribeMessageTemplateDTO.class);
        weChatMpSubscribeMessageTemplateDTO.setApp(weChatMpSubscribeMessageTemplateDO.getAppName());
        return Result.success(weChatMpSubscribeMessageTemplateDTO);
    }

    /**
     * 获取模板通过 query 参数
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *
     * @param query 查询参数
     * @return WeChatMpSubscribeMessageTemplateDTO 可能返回空列表
     */
    @Override
    public Result<PageInfo<WeChatMpSubscribeMessageTemplateDTO>> listWeChatMpSubscribeMessageTemplates(
            WeChatMpSubscribeMessageTemplateQuery query) {
        // 获取 WeChatMpSubscribeMessageTemplateDO
        List<WeChatMpSubscribeMessageTemplateDO> weChatMpSubscribeMessageTemplateDOList =
                weChatMpSubscribeMessageTemplateMapper.listWeChatMpSubscribeMessageTemplates(query);

        // 转换成 WeChatMpSubscribeMessageTemplateDTO
        List<WeChatMpSubscribeMessageTemplateDTO> weChatMpSubscribeMessageTemplateDTOList =
                weChatMpSubscribeMessageTemplateDOList
                .stream()
                .map(weChatMpSubscribeMessageTemplateDO -> {
                            WeChatMpSubscribeMessageTemplateDTO weChatMpSubscribeMessageTemplateDTO =
                                    mapper.map(weChatMpSubscribeMessageTemplateDO, WeChatMpSubscribeMessageTemplateDTO.class);
                            weChatMpSubscribeMessageTemplateDTO.setApp(weChatMpSubscribeMessageTemplateDO.getAppName());
                            return weChatMpSubscribeMessageTemplateDTO;
                })
                .collect(Collectors.toList());

        // 包装 PageInfo
        PageInfo<WeChatMpSubscribeMessageTemplateDTO> pageInfo =
                new PageInfo<>(weChatMpSubscribeMessageTemplateDTOList);
        return Result.success(pageInfo);
    }

    /**
     * 更新模板，这是一个较广的更新接口，请小心使用
     *
     * @errorCode InvalidParameter: 请求参数格式错误 | 模板不存在 | 不能每个域都为 null
     *
     * @param weChatMpSubscribeMessageTemplateDTO WeChatMpSubscribeMessageTemplateDTO
     * @return WeChatMpSubscribeMessageTemplateDTO
     */
    @Override
    public Result<WeChatMpSubscribeMessageTemplateDTO> updateWeChatMpSubscribeMessageTemplate(
            WeChatMpSubscribeMessageTemplateDTO weChatMpSubscribeMessageTemplateDTO) {
        // 判断该编号的模板存不存在
        int count = weChatMpSubscribeMessageTemplateMapper.count(weChatMpSubscribeMessageTemplateDTO.getId());
        if (count < 1) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER, "This template does not exist.");
        }

        // 配置更新参数
        WeChatMpSubscribeMessageTemplateDO weChatMpSubscribeMessageTemplateDO =
                new WeChatMpSubscribeMessageTemplateDO.Builder()
                        .appName(weChatMpSubscribeMessageTemplateDTO.getApp())
                        .templateId(weChatMpSubscribeMessageTemplateDTO.getTemplateId())
                        .title(weChatMpSubscribeMessageTemplateDTO.getTitle())
                        .type(weChatMpSubscribeMessageTemplateDTO.getType())
                        .description(weChatMpSubscribeMessageTemplateDTO.getDescription())
                        .status(weChatMpSubscribeMessageTemplateDTO.getStatus())
                        .build();

        // 检查是否每个域都为 null
        if (ObjectUtils.allFieldIsNull(weChatMpSubscribeMessageTemplateDO)) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER, "At least one non null parameter.");
        }

        // 添加 id 属性
        weChatMpSubscribeMessageTemplateDO.setId(weChatMpSubscribeMessageTemplateDTO.getId());

        // 更新到数据库
        weChatMpSubscribeMessageTemplateMapper.updateWeChatMpSubscribeMessageTemplate(
                weChatMpSubscribeMessageTemplateDO);
        return getWeChatMpSubscribeMessageTemplate(weChatMpSubscribeMessageTemplateDTO.getId());
    }
}
