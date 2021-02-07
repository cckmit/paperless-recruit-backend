package com.xiaohuashifu.recruit.organization.service.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaohuashifu.recruit.common.exception.NotFoundServiceException;
import com.xiaohuashifu.recruit.common.exception.unprocessable.DuplicateServiceException;
import com.xiaohuashifu.recruit.common.exception.unprocessable.UnavailableServiceException;
import com.xiaohuashifu.recruit.common.exception.unprocessable.UnmodifiedServiceException;
import com.xiaohuashifu.recruit.common.query.QueryResult;
import com.xiaohuashifu.recruit.organization.api.dto.DisableOrganizationLabelDTO;
import com.xiaohuashifu.recruit.organization.api.dto.OrganizationLabelDTO;
import com.xiaohuashifu.recruit.organization.api.query.OrganizationLabelQuery;
import com.xiaohuashifu.recruit.organization.api.service.OrganizationLabelService;
import com.xiaohuashifu.recruit.organization.api.service.OrganizationService;
import com.xiaohuashifu.recruit.organization.service.assembler.OrganizationLabelAssembler;
import com.xiaohuashifu.recruit.organization.service.dao.OrganizationLabelMapper;
import com.xiaohuashifu.recruit.organization.service.do0.OrganizationLabelDO;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 描述：组织标签服务
 *
 * @author xhsf
 * @create 2020/12/8 18:41
 */
@Service
@Slf4j
public class OrganizationLabelServiceImpl implements OrganizationLabelService {

    @Reference
    private OrganizationService organizationService;

    private final OrganizationLabelMapper organizationLabelMapper;

    private final OrganizationLabelAssembler organizationLabelAssembler;

    public OrganizationLabelServiceImpl(OrganizationLabelMapper organizationLabelMapper,
                                        OrganizationLabelAssembler organizationLabelAssembler) {
        this.organizationLabelMapper = organizationLabelMapper;
        this.organizationLabelAssembler = organizationLabelAssembler;
    }

    @Override
    public OrganizationLabelDTO createOrganizationLabel(String labelName) {
        // 判断标签名是否已经存在
        OrganizationLabelDO organizationLabelDO = organizationLabelMapper.selectByLabelName(labelName);
        if (organizationLabelDO != null) {
            throw new DuplicateServiceException("This label name already exist.");
        }

        // 保存标签
        OrganizationLabelDO organizationLabelDOForInsert = OrganizationLabelDO.builder().labelName(labelName).build();
        organizationLabelMapper.insert(organizationLabelDOForInsert);
        return getOrganizationLabel(organizationLabelDOForInsert.getId());
    }

    @Override
    public OrganizationLabelDTO getOrganizationLabel(Long id) {
        OrganizationLabelDO organizationLabelDO = organizationLabelMapper.selectById(id);
        if (organizationLabelDO == null) {
            throw new NotFoundServiceException("organization label", "id", id);
        }
        return organizationLabelAssembler.organizationLabelDOToOrganizationLabelDTO(organizationLabelDO);
    }

    @Override
    public OrganizationLabelDTO getOrganizationLabelByLabelName(String labelName) {
        OrganizationLabelDO organizationLabelDO = organizationLabelMapper.selectByLabelName(labelName);
        if (organizationLabelDO == null) {
            throw new NotFoundServiceException("organization label", "labelName", labelName);
        }
        return organizationLabelAssembler.organizationLabelDOToOrganizationLabelDTO(organizationLabelDO);
    }

    @Override
    public QueryResult<OrganizationLabelDTO> listOrganizationLabels(OrganizationLabelQuery query) {
        LambdaQueryWrapper<OrganizationLabelDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.likeRight(query.getLabelName() != null, OrganizationLabelDO::getLabelName,
                query.getLabelName())
                .eq(query.getAvailable() != null, OrganizationLabelDO::getAvailable, query.getAvailable());
        Page<OrganizationLabelDO> page = new Page<>(query.getPageNum(), query.getPageSize(), true);
        organizationLabelMapper.selectPage(page, wrapper);
        List<OrganizationLabelDO> organizationLabelDOS = page.getRecords();
        List<OrganizationLabelDTO> organizationLabelDTOS = organizationLabelDOS
                .stream()
                .map(organizationLabelAssembler::organizationLabelDOToOrganizationLabelDTO)
                .collect(Collectors.toList());
        return new QueryResult<>(page.getTotal(), organizationLabelDTOS);
    }

    @Override
    @Transactional
    public DisableOrganizationLabelDTO disableOrganizationLabel(Long id) {
        // 判断标签是否存在
        OrganizationLabelDTO organizationLabelDTO = getOrganizationLabel(id);

        // 判断标签是否已经被禁用
        if (!organizationLabelDTO.getAvailable()) {
            throw new UnmodifiedServiceException("The label already unavailable.");
        }

        // 禁用标签
        OrganizationLabelDO organizationLabelDOForUpdate = OrganizationLabelDO.builder().id(id).available(false).build();
        organizationLabelMapper.updateById(organizationLabelDOForUpdate);

        // 删除组织的这个标签
        int deletedNumber = organizationService.removeLabels(organizationLabelDTO.getLabelName());

        // 封装删除数量和禁用后的组织标签对象
        return new DisableOrganizationLabelDTO(getOrganizationLabel(id), deletedNumber);
    }

    @Override
    @Transactional
    public OrganizationLabelDTO enableOrganizationLabel(Long id) {
        // 判断标签是否存在
        OrganizationLabelDTO organizationLabelDTO = getOrganizationLabel(id);

        // 判断标签是否已经可用
        if (organizationLabelDTO.getAvailable()) {
            throw new UnmodifiedServiceException("The label already available.");
        }

        // 解禁标签
        OrganizationLabelDO organizationLabelDOForUpdate = OrganizationLabelDO.builder().id(id).available(true).build();
        organizationLabelMapper.updateById(organizationLabelDOForUpdate);
        return getOrganizationLabel(id);
    }

    @Override
    public OrganizationLabelDTO increaseReferenceNumberOrSaveOrganizationLabel(String labelName) {
        // 判断标签是否已经存在
        OrganizationLabelDO organizationLabelDO = organizationLabelMapper.selectByLabelName(labelName);

        // 若存在且被禁用则不可用增加引用数量
        if (organizationLabelDO != null && !organizationLabelDO.getAvailable()) {
            throw new UnavailableServiceException("The organization label unavailable.");
        }

        // 若不存在先添加标签
        if (organizationLabelDO == null) {
            organizationLabelDO = OrganizationLabelDO.builder().labelName(labelName).build();
            organizationLabelMapper.insert(organizationLabelDO);
        }

        // 添加标签引用数量
        organizationLabelMapper.increaseReferenceNumber(organizationLabelDO.getId());
        return getOrganizationLabel(organizationLabelDO.getId());
    }

}
