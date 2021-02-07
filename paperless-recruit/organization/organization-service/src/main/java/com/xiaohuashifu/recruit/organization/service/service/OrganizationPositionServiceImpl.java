package com.xiaohuashifu.recruit.organization.service.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaohuashifu.recruit.common.exception.NotFoundServiceException;
import com.xiaohuashifu.recruit.common.exception.unprocessable.DuplicateServiceException;
import com.xiaohuashifu.recruit.common.query.QueryResult;
import com.xiaohuashifu.recruit.organization.api.dto.OrganizationPositionDTO;
import com.xiaohuashifu.recruit.organization.api.query.OrganizationPositionQuery;
import com.xiaohuashifu.recruit.organization.api.request.CreateOrganizationPositionRequest;
import com.xiaohuashifu.recruit.organization.api.request.UpdateOrganizationPositionRequest;
import com.xiaohuashifu.recruit.organization.api.service.OrganizationMemberService;
import com.xiaohuashifu.recruit.organization.api.service.OrganizationPositionService;
import com.xiaohuashifu.recruit.organization.api.service.OrganizationService;
import com.xiaohuashifu.recruit.organization.service.assembler.OrganizationPositionAssembler;
import com.xiaohuashifu.recruit.organization.service.dao.OrganizationPositionMapper;
import com.xiaohuashifu.recruit.organization.service.do0.OrganizationPositionDO;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 描述：组织职位服务
 *
 * @author xhsf
 * @create 2020/12/15 14:35
 */
@Service
public class OrganizationPositionServiceImpl implements OrganizationPositionService {

    @Reference
    private OrganizationService organizationService;

    @Reference
    private OrganizationMemberService organizationMemberService;

    private final OrganizationPositionMapper organizationPositionMapper;

    private final OrganizationPositionAssembler organizationPositionAssembler;

    public OrganizationPositionServiceImpl(OrganizationPositionMapper organizationPositionMapper,
                                           OrganizationPositionAssembler organizationPositionAssembler) {
        this.organizationPositionMapper = organizationPositionMapper;
        this.organizationPositionAssembler = organizationPositionAssembler;
    }

    @Override
    public OrganizationPositionDTO createOrganizationPosition(CreateOrganizationPositionRequest request) {
        // 检查组织是否存在
         organizationService.getOrganization(request.getOrganizationId());

        // 判断该组织是否已经存在该职位名
        LambdaQueryWrapper<OrganizationPositionDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrganizationPositionDO::getOrganizationId, request.getOrganizationId())
                .eq(OrganizationPositionDO::getPositionName, request.getPositionName());
        int count = organizationPositionMapper.selectCount(wrapper);
        if (count > 0) {
            throw new DuplicateServiceException("The positionName already exist.");
        }

        // 插入职位
        OrganizationPositionDO organizationPositionDOForInsert =
                organizationPositionAssembler.createOrganizationPositionRequestToOrganizationPositionDO(request);
        organizationPositionMapper.insert(organizationPositionDOForInsert);
        return getOrganizationPosition(organizationPositionDOForInsert.getId());
    }

    @Override
    @Transactional
    public Integer removeOrganizationPosition(Long id) {
        // 检查组织和组织职位状态
        getOrganizationPosition(id);

        // 删除组织职位
        organizationPositionMapper.deleteById(id);

        // 把该职位的组织成员的职位都清除（设置为0）
        return organizationMemberService.clearOrganizationPositions(id);
    }

    @Override
    public OrganizationPositionDTO getOrganizationPosition(Long id) {
        OrganizationPositionDO organizationPositionDO = organizationPositionMapper.selectById(id);
        if (organizationPositionDO == null) {
            throw new NotFoundServiceException("organization position", "id", id);
        }
        return organizationPositionAssembler.organizationPositionDOToOrganizationPositionDTO(organizationPositionDO);
    }

    @Override
    public QueryResult<OrganizationPositionDTO> listOrganizationPositions(OrganizationPositionQuery query) {
        LambdaQueryWrapper<OrganizationPositionDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(query.getOrganizationId() != null, OrganizationPositionDO::getOrganizationId,
                query.getOrganizationId())
                .likeRight(query.getPositionName() != null, OrganizationPositionDO::getPositionName,
                        query.getPositionName())
                .eq(query.getPriority() != null, OrganizationPositionDO::getPriority, query.getPriority());

        Page<OrganizationPositionDO> page = new Page<>(query.getPageNum(), query.getPageSize(), true);
        organizationPositionMapper.selectPage(page, wrapper);
        List<OrganizationPositionDTO> organizationPositionDTOS = page.getRecords()
                .stream().map(organizationPositionAssembler::organizationPositionDOToOrganizationPositionDTO)
                .collect(Collectors.toList());
        return new QueryResult<>(page.getTotal(), organizationPositionDTOS);
    }

    @Override
    public OrganizationPositionDTO updateOrganizationPosition(UpdateOrganizationPositionRequest request) {
        OrganizationPositionDTO organizationPositionDTO = getOrganizationPosition(request.getId());
        // 判断组织是否存在相同的职位名
        if (request.getPositionName() != null) {
            LambdaQueryWrapper<OrganizationPositionDO> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(OrganizationPositionDO::getOrganizationId, organizationPositionDTO.getOrganizationId())
                    .eq(OrganizationPositionDO::getPositionName, request.getPositionName());
            int count = organizationPositionMapper.selectCount(wrapper);
            if (count > 0) {
                throw new DuplicateServiceException("The positionName already exist.");
            }
        }

        // 更新组织职位名
        OrganizationPositionDO organizationPositionDOForUpdate =
                organizationPositionAssembler.updateOrganizationPositionRequestToOrganizationPositionDO(request);
        organizationPositionMapper.updateById(organizationPositionDOForUpdate);
        return getOrganizationPosition(request.getId());
    }

}
