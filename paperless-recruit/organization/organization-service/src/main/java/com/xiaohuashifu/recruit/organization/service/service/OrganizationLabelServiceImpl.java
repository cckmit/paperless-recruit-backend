package com.xiaohuashifu.recruit.organization.service.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaohuashifu.recruit.common.query.QueryResult;
import com.xiaohuashifu.recruit.common.result.ErrorCodeEnum;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.organization.api.dto.DisableOrganizationLabelDTO;
import com.xiaohuashifu.recruit.organization.api.dto.OrganizationLabelDTO;
import com.xiaohuashifu.recruit.organization.api.query.OrganizationLabelQuery;
import com.xiaohuashifu.recruit.organization.api.service.OrganizationLabelService;
import com.xiaohuashifu.recruit.organization.api.service.OrganizationService;
import com.xiaohuashifu.recruit.organization.service.assembler.OrganizationLabelAssembler;
import com.xiaohuashifu.recruit.organization.service.dao.OrganizationLabelMapper;
import com.xiaohuashifu.recruit.organization.service.do0.OrganizationLabelDO;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;

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

    private final OrganizationLabelMapper organizationLabelMapper;

    private final OrganizationService organizationService;

    private final OrganizationLabelAssembler organizationLabelAssembler;

    private final PlatformTransactionManager transactionManager;

    private final TransactionDefinition transactionDefinition;

    public OrganizationLabelServiceImpl(OrganizationLabelMapper organizationLabelMapper,
                                        OrganizationService organizationService,
                                        OrganizationLabelAssembler organizationLabelAssembler,
                                        PlatformTransactionManager transactionManager,
                                        TransactionDefinition transactionDefinition) {
        this.organizationLabelMapper = organizationLabelMapper;
        this.organizationService = organizationService;
        this.organizationLabelAssembler = organizationLabelAssembler;
        this.transactionManager = transactionManager;
        this.transactionDefinition = transactionDefinition;
    }

    @Override
    public Result<OrganizationLabelDTO> createOrganizationLabel(String labelName) {
        // 判断标签名是否已经存在
        OrganizationLabelDO organizationLabelDO = organizationLabelMapper.selectByLabelName(labelName);
        if (organizationLabelDO != null) {
            return Result.fail(ErrorCodeEnum.UNPROCESSABLE_ENTITY_EXIST, "This label name already exist.");
        }

        // 保存标签
        OrganizationLabelDO organizationLabelDOForInsert = OrganizationLabelDO.builder()
                .labelName(labelName)
                .build();
        organizationLabelMapper.insert(organizationLabelDOForInsert);
        return getOrganizationLabel(organizationLabelDOForInsert.getId());
    }

    @Override
    public Result<OrganizationLabelDTO> getOrganizationLabel(Long id) {
        OrganizationLabelDO organizationLabelDO = organizationLabelMapper.selectById(id);
        if (organizationLabelDO == null) {
            return Result.fail(ErrorCodeEnum.NOT_FOUND);
        }
        return Result.success(organizationLabelAssembler.organizationLabelDOToOrganizationLabelDTO(organizationLabelDO));
    }

    @Override
    public Result<OrganizationLabelDTO> getOrganizationLabelByLabelName(String labelName) {
        OrganizationLabelDO organizationLabelDO = organizationLabelMapper.selectByLabelName(labelName);
        if (organizationLabelDO == null) {
            return Result.fail(ErrorCodeEnum.NOT_FOUND);
        }
        return Result.success(organizationLabelAssembler.organizationLabelDOToOrganizationLabelDTO(organizationLabelDO));
    }

    @Override
    public Result<QueryResult<OrganizationLabelDTO>> listOrganizationLabels(OrganizationLabelQuery query) {
        LambdaQueryWrapper<OrganizationLabelDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(query.getLabelName() != null, OrganizationLabelDO::getLabelName, query.getLabelName())
                .eq(query.getAvailable() != null, OrganizationLabelDO::getAvailable, query.getAvailable());
        Page<OrganizationLabelDO> page = new Page<>(query.getPageNum(), query.getPageSize(), true);
        organizationLabelMapper.selectPage(page, wrapper);
        List<OrganizationLabelDO> organizationLabelDOS = page.getRecords();
        List<OrganizationLabelDTO> organizationLabelDTOS = organizationLabelDOS
                .stream()
                .map(organizationLabelAssembler::organizationLabelDOToOrganizationLabelDTO)
                .collect(Collectors.toList());
        QueryResult<OrganizationLabelDTO> queryResult = new QueryResult<>(page.getTotal(), organizationLabelDTOS);
        return Result.success(queryResult);
    }

    @Override
    public Result<DisableOrganizationLabelDTO> disableOrganizationLabel(Long id) {
        TransactionStatus transactionStatus = transactionManager.getTransaction(transactionDefinition);
        try {
            // 判断标签是否存在
            OrganizationLabelDO organizationLabelDO = organizationLabelMapper.selectById(id);
            if (organizationLabelDO == null) {
                return Result.fail(ErrorCodeEnum.UNPROCESSABLE_ENTITY_NOT_EXIST, "The label does not exist.");
            }

            // 判断标签是否已经被禁用
            if (!organizationLabelDO.getAvailable()) {
                return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT, "The label already unavailable.");
            }

            // 禁用标签
            OrganizationLabelDO organizationLabelDOForUpdate = OrganizationLabelDO.builder()
                    .id(id)
                    .available(false)
                    .build();
            organizationLabelMapper.updateById(organizationLabelDOForUpdate);

            // 删除组织的这个标签
            int deletedNumber = organizationService.removeLabels(organizationLabelDO.getLabelName());

            // 封装删除数量和禁用后的组织标签对象
            OrganizationLabelDTO organizationLabelDTO = getOrganizationLabel(id).getData();
            DisableOrganizationLabelDTO disableOrganizationLabelDTO =
                    new DisableOrganizationLabelDTO(organizationLabelDTO, deletedNumber);
            transactionManager.commit(transactionStatus);
            return Result.success(disableOrganizationLabelDTO);
        } catch (RuntimeException e) {
            transactionManager.rollback(transactionStatus);
            log.error("Disable organization label error. id=" + id, e);
            return Result.fail(ErrorCodeEnum.INTERNAL_ERROR);
        }
    }

    @Override
    public Result<OrganizationLabelDTO> enableOrganizationLabel(Long id) {
        TransactionStatus transactionStatus = transactionManager.getTransaction(transactionDefinition);
        try {
            // 判断标签是否存在
            OrganizationLabelDO organizationLabelDO = organizationLabelMapper.selectById(id);
            if (organizationLabelDO == null) {
                return Result.fail(ErrorCodeEnum.UNPROCESSABLE_ENTITY_NOT_EXIST,
                        "The label does not exist.");
            }

            // 判断标签是否已经可用
            if (organizationLabelDO.getAvailable()) {
                return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT, "The label already available.");
            }

            // 解禁标签
            OrganizationLabelDO organizationLabelDOForUpdate = OrganizationLabelDO.builder()
                    .id(id)
                    .available(true)
                    .build();
            organizationLabelMapper.updateById(organizationLabelDOForUpdate);
            transactionManager.commit(transactionStatus);
            return getOrganizationLabel(id);
        } catch (RuntimeException e) {
            transactionManager.rollback(transactionStatus);
            log.error("Disable organization label error. id=" + id, e);
            return Result.fail(ErrorCodeEnum.INTERNAL_ERROR);
        }
    }

    @Override
    public Result<OrganizationLabelDTO> increaseReferenceNumberOrSaveOrganizationLabel(String labelName) {
        // 判断标签是否已经存在
        OrganizationLabelDO organizationLabelDO = organizationLabelMapper.selectByLabelName(labelName);

        // 若存在且被禁用则不可用增加引用数量
        if (organizationLabelDO != null && !organizationLabelDO.getAvailable()) {
            return Result.fail(ErrorCodeEnum.UNPROCESSABLE_ENTITY_UNAVAILABLE,
                    "The organization label unavailable.");
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
