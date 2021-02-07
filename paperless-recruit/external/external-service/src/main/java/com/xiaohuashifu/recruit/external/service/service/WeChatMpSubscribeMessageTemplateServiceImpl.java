package com.xiaohuashifu.recruit.external.service.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaohuashifu.recruit.common.exception.NotFoundServiceException;
import com.xiaohuashifu.recruit.common.exception.unprocessable.DuplicateServiceException;
import com.xiaohuashifu.recruit.common.query.QueryResult;
import com.xiaohuashifu.recruit.external.api.dto.WeChatMpSubscribeMessageTemplateDTO;
import com.xiaohuashifu.recruit.external.api.query.WeChatMpSubscribeMessageTemplateQuery;
import com.xiaohuashifu.recruit.external.api.request.CreateWeChatMpSubscribeMessageTemplateRequest;
import com.xiaohuashifu.recruit.external.api.request.UpdateWeChatMpSubscribeMessageTemplateRequest;
import com.xiaohuashifu.recruit.external.api.service.WeChatMpSubscribeMessageTemplateService;
import com.xiaohuashifu.recruit.external.service.assembler.WeChatMpSubscribeMessageTemplateAssembler;
import com.xiaohuashifu.recruit.external.service.dao.WeChatMpSubscribeMessageTemplateMapper;
import com.xiaohuashifu.recruit.external.service.pojo.do0.WeChatMpSubscribeMessageTemplateDO;
import org.apache.dubbo.config.annotation.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 描述：微信小程序订阅消息模板的服务
 *
 * @private 内部服务
 *
 * @author: xhsf
 * @create: 2020/11/22 01:32
 */
@Service
public class WeChatMpSubscribeMessageTemplateServiceImpl implements WeChatMpSubscribeMessageTemplateService {

    private final WeChatMpSubscribeMessageTemplateMapper weChatMpSubscribeMessageTemplateMapper;

    private final WeChatMpSubscribeMessageTemplateAssembler weChatMpSubscribeMessageTemplateAssembler;

    public WeChatMpSubscribeMessageTemplateServiceImpl(
            WeChatMpSubscribeMessageTemplateMapper weChatMpSubscribeMessageTemplateMapper,
            WeChatMpSubscribeMessageTemplateAssembler weChatMpSubscribeMessageTemplateAssembler) {
        this.weChatMpSubscribeMessageTemplateMapper = weChatMpSubscribeMessageTemplateMapper;
        this.weChatMpSubscribeMessageTemplateAssembler = weChatMpSubscribeMessageTemplateAssembler;
    }

    @Override
    public WeChatMpSubscribeMessageTemplateDTO createWeChatMpSubscribeMessageTemplate(
            CreateWeChatMpSubscribeMessageTemplateRequest request) {
        // 查看是否存在相同的模板 id
        LambdaQueryWrapper<WeChatMpSubscribeMessageTemplateDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WeChatMpSubscribeMessageTemplateDO::getTemplateId, request.getTemplateId());
        int count = weChatMpSubscribeMessageTemplateMapper.selectCount(wrapper);
        if (count > 0) {
            throw new DuplicateServiceException("This template id already exists.");
        }

        // 添加到数据库
        WeChatMpSubscribeMessageTemplateDO weChatMpSubscribeMessageTemplateDOForInsert =
                weChatMpSubscribeMessageTemplateAssembler
                        .createWeChatMpSubscribeMessageTemplateRequestToWeChatMpSubscribeMessageTemplateDO(request);
        weChatMpSubscribeMessageTemplateMapper.insert(weChatMpSubscribeMessageTemplateDOForInsert);
        return getWeChatMpSubscribeMessageTemplate(weChatMpSubscribeMessageTemplateDOForInsert.getId());
    }

    @Override
    public WeChatMpSubscribeMessageTemplateDTO getWeChatMpSubscribeMessageTemplate(Long id) {
        WeChatMpSubscribeMessageTemplateDO weChatMpSubscribeMessageTemplateDO =
                weChatMpSubscribeMessageTemplateMapper.selectById(id);
        if (weChatMpSubscribeMessageTemplateDO == null) {
            throw new NotFoundServiceException("weChatMpSubscribeMessageTemplate", "id", id);
        }
        return weChatMpSubscribeMessageTemplateAssembler
                .weChatMpSubscribeMessageTemplateDOToWeChatMpSubscribeMessageTemplateDTO(
                        weChatMpSubscribeMessageTemplateDO);
    }

    @Override
    public QueryResult<WeChatMpSubscribeMessageTemplateDTO> listWeChatMpSubscribeMessageTemplates(
            WeChatMpSubscribeMessageTemplateQuery query) {
        LambdaQueryWrapper<WeChatMpSubscribeMessageTemplateDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(query.getAppName() != null, WeChatMpSubscribeMessageTemplateDO::getAppName,
                query.getAppName())
                .eq(query.getTemplateId() != null, WeChatMpSubscribeMessageTemplateDO::getTemplateId,
                        query.getTemplateId())
                .likeRight(query.getTitle() != null, WeChatMpSubscribeMessageTemplateDO::getTitle,
                        query.getTitle())
                .likeRight(query.getTemplateType() != null,
                        WeChatMpSubscribeMessageTemplateDO::getTemplateType, query.getTemplateType())
                .eq(query.getTemplateStatus() != null, WeChatMpSubscribeMessageTemplateDO::getTemplateStatus,
                        query.getTemplateStatus());

        Page<WeChatMpSubscribeMessageTemplateDO> page =
                new Page<>(query.getPageNum(), query.getPageSize(), true);
        weChatMpSubscribeMessageTemplateMapper.selectPage(page, wrapper);
        List<WeChatMpSubscribeMessageTemplateDTO> weChatMpSubscribeMessageTemplateDTOS = page.getRecords().stream()
                .map(weChatMpSubscribeMessageTemplateAssembler::
                        weChatMpSubscribeMessageTemplateDOToWeChatMpSubscribeMessageTemplateDTO)
                .collect(Collectors.toList());
        return new QueryResult<>(page.getTotal(), weChatMpSubscribeMessageTemplateDTOS);
    }

    @Override
    public WeChatMpSubscribeMessageTemplateDTO updateWeChatMpSubscribeMessageTemplate(
            UpdateWeChatMpSubscribeMessageTemplateRequest request) {
        // 配置更新参数
        WeChatMpSubscribeMessageTemplateDO weChatMpSubscribeMessageTemplateDOForInsert =
                weChatMpSubscribeMessageTemplateAssembler
                        .updateWeChatMpSubscribeMessageTemplateRequestToWeChatMpSubscribeMessageTemplateDO(request);

        // 更新到数据库
        weChatMpSubscribeMessageTemplateMapper.updateById(weChatMpSubscribeMessageTemplateDOForInsert);
        return getWeChatMpSubscribeMessageTemplate(request.getId());
    }

}
