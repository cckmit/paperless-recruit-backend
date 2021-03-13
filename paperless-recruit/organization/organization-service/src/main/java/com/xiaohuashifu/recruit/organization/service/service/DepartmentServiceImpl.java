package com.xiaohuashifu.recruit.organization.service.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaohuashifu.recruit.common.exception.NotFoundServiceException;
import com.xiaohuashifu.recruit.common.exception.unprocessable.OverLimitServiceException;
import com.xiaohuashifu.recruit.common.query.QueryResult;
import com.xiaohuashifu.recruit.common.util.ObjectUtils;
import com.xiaohuashifu.recruit.organization.api.constant.DepartmentConstants;
import com.xiaohuashifu.recruit.organization.api.dto.DepartmentDTO;
import com.xiaohuashifu.recruit.organization.api.query.DepartmentQuery;
import com.xiaohuashifu.recruit.organization.api.request.CreateDepartmentRequest;
import com.xiaohuashifu.recruit.organization.api.request.UpdateDepartmentRequest;
import com.xiaohuashifu.recruit.organization.api.service.DepartmentLabelService;
import com.xiaohuashifu.recruit.organization.api.service.DepartmentService;
import com.xiaohuashifu.recruit.organization.api.service.OrganizationService;
import com.xiaohuashifu.recruit.organization.service.assembler.DepartmentAssembler;
import com.xiaohuashifu.recruit.organization.service.dao.DepartmentMapper;
import com.xiaohuashifu.recruit.organization.service.do0.DepartmentDO;
import com.xiaohuashifu.recruit.oss.api.service.ObjectStorageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 描述：部门服务
 *
 * @author xhsf
 * @create 2020/12/8 21:56
 */
@Service
@Slf4j
public class DepartmentServiceImpl implements DepartmentService {

    @Reference
    private DepartmentLabelService departmentLabelService;

    @Reference
    private OrganizationService organizationService;

    @Reference
    private ObjectStorageService objectStorageService;

    private final DepartmentMapper departmentMapper;

    private final DepartmentAssembler departmentAssembler;

    /**
     * 部门标签锁定键模式，{0}是部门编号
     */
    private static final String DEPARTMENT_LABELS_LOCK_KEY_PATTERN = "departments:{0}:labels";

    /**
     * 部门 deactivated 的锁定键模式，{0}是部门编号
     */
    private static final String DEPARTMENT_DEACTIVATED_LOCK_KEY_PATTERN = "departments:{0}:deactivated";

    public DepartmentServiceImpl(DepartmentMapper departmentMapper, DepartmentAssembler departmentAssembler) {
        this.departmentMapper = departmentMapper;
        this.departmentAssembler = departmentAssembler;
    }

    @Override
    public DepartmentDTO createDepartment(CreateDepartmentRequest request) {
        // 预处理数据
        ObjectUtils.trimAllStringFields(request);
        checkLabels(request.getLabels());

        // 判断组织是否存在
        organizationService.getOrganization(request.getOrganizationId());

        // 创建部门
        DepartmentDO departmentDOForInsert = departmentAssembler.createDepartmentRequestToDepartmentDO(request);
        departmentMapper.insert(departmentDOForInsert);

        return getDepartment(departmentDOForInsert.getId());
    }

    @Override
    public void removeDepartment(Long id) {
        departmentMapper.deleteById(id);
    }

    @Override
    public DepartmentDTO getDepartment(Long id) {
        DepartmentDO departmentDO = departmentMapper.selectById(id);
        if (departmentDO == null) {
            throw new NotFoundServiceException("department", "id", id);
        }
        return departmentAssembler.departmentDOToDepartmentDTO(departmentDO);
    }

    @Override
    public QueryResult<DepartmentDTO> listDepartments(DepartmentQuery query) {
        LambdaQueryWrapper<DepartmentDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(query.getOrganizationId() != null,
                DepartmentDO::getOrganizationId, query.getOrganizationId())
                .likeRight(query.getDepartmentName() != null,
                        DepartmentDO::getDepartmentName, query.getDepartmentName());

        Page<DepartmentDO> page = new Page<>(query.getPageNum(), query.getPageSize(), true);
        departmentMapper.selectPage(page, wrapper);
        List<DepartmentDTO> departmentDTOS = page.getRecords()
                .stream().map(departmentAssembler::departmentDOToDepartmentDTO).collect(Collectors.toList());
        return new QueryResult<>(page.getTotal(), departmentDTOS);
    }

    @Override
    public DepartmentDTO updateDepartment(UpdateDepartmentRequest request) {
        // 判断部门是否存在
        getDepartment(request.getId());

        // 更新组织
        DepartmentDO departmentDOForUpdate = checkForUpdate(request);
        departmentMapper.updateById(departmentDOForUpdate);
        return getDepartment(request.getId());
    }

    /**
     * 检查更新参数
     *
     * @param request UpdateDepartmentRequest
     * @return DepartmentDO
     */
    private DepartmentDO checkForUpdate(UpdateDepartmentRequest request) {
        // 转换成 DO 对象
        DepartmentDO departmentDO = departmentAssembler.updateDepartmentRequestToDepartmentDO(request);
        ObjectUtils.trimAllStringFields(departmentDO);

        // 链接 logo
        if (departmentDO.getLogoUrl() != null) {
            objectStorageService.linkObject(departmentDO.getLogoUrl());
        }

        // 判断组织标签列表是否合法
        if (departmentDO.getLabels() != null) {
            checkLabels(departmentDO.getLabels());
        }

        return departmentDO;
    }

    /**
     * 检查标签是否合法
     *
     * @param labels 标签列表
     */
    public void checkLabels(Set<String> labels) {
        labels.forEach((label)-> {
            if (StringUtils.isBlank(label) || label.trim().length() >
                    DepartmentConstants.MAX_DEPARTMENT_LABEL_LENGTH) {
                throw new OverLimitServiceException("部门标签长度必须小于"
                        + DepartmentConstants.MAX_DEPARTMENT_LABEL_LENGTH);
            }
        });
    }

}
