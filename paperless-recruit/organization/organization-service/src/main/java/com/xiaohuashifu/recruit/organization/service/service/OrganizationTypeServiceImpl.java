package com.xiaohuashifu.recruit.organization.service.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaohuashifu.recruit.common.exception.NotFoundServiceException;
import com.xiaohuashifu.recruit.common.query.QueryResult;
import com.xiaohuashifu.recruit.organization.api.dto.OrganizationTypeDTO;
import com.xiaohuashifu.recruit.organization.api.query.OrganizationTypeQuery;
import com.xiaohuashifu.recruit.organization.api.service.OrganizationTypeService;
import com.xiaohuashifu.recruit.organization.service.assembler.OrganizationTypeAssembler;
import com.xiaohuashifu.recruit.organization.service.dao.OrganizationTypeMapper;
import com.xiaohuashifu.recruit.organization.service.do0.OrganizationTypeDO;
import org.apache.dubbo.config.annotation.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 描述：组织类型服务
 *
 * @author xhsf
 * @create 2021/3/9 14:15
 */
@Service
public class OrganizationTypeServiceImpl implements OrganizationTypeService {

    private final OrganizationTypeMapper organizationTypeMapper;

    private final OrganizationTypeAssembler organizationTypeAssembler;

    public OrganizationTypeServiceImpl(OrganizationTypeMapper organizationTypeMapper,
                                       OrganizationTypeAssembler organizationTypeAssembler) {
        this.organizationTypeMapper = organizationTypeMapper;
        this.organizationTypeAssembler = organizationTypeAssembler;
    }

    @Override
    public OrganizationTypeDTO getOrganizationTypeByTypeName(String typeName) {
        OrganizationTypeDO organizationTypeDO = organizationTypeMapper.selectOrganizationTypeByTypeName(typeName);
        if (organizationTypeDO == null) {
            throw new NotFoundServiceException("organizationType", "typeName", typeName);
        }
        return organizationTypeAssembler.organizationTypeDODOToOrganizationTypeDTO(organizationTypeDO);
    }

    @Override
    public QueryResult<OrganizationTypeDTO> listOrganizationTypes(OrganizationTypeQuery query) {
        Page<OrganizationTypeDO> page = new Page<>(query.getPageNum(), query.getPageSize(), true);
        organizationTypeMapper.selectPage(page, null);
        List<OrganizationTypeDTO> organizationTypeDTOS = page.getRecords()
                .stream()
                .map(organizationTypeAssembler::organizationTypeDODOToOrganizationTypeDTO)
                .collect(Collectors.toList());
        return new QueryResult<>(page.getTotal(), organizationTypeDTOS);
    }

}
