package com.xiaohuashifu.recruit.organization.service.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaohuashifu.recruit.common.aspect.annotation.DistributedLock;
import com.xiaohuashifu.recruit.common.exception.NotFoundServiceException;
import com.xiaohuashifu.recruit.common.exception.unprocessable.DuplicateServiceException;
import com.xiaohuashifu.recruit.common.exception.unprocessable.OverLimitServiceException;
import com.xiaohuashifu.recruit.common.exception.unprocessable.UnavailableServiceException;
import com.xiaohuashifu.recruit.common.exception.unprocessable.UnmodifiedServiceException;
import com.xiaohuashifu.recruit.common.query.QueryResult;
import com.xiaohuashifu.recruit.organization.api.constant.DepartmentConstants;
import com.xiaohuashifu.recruit.organization.api.dto.DepartmentDTO;
import com.xiaohuashifu.recruit.organization.api.dto.DepartmentLabelDTO;
import com.xiaohuashifu.recruit.organization.api.query.DepartmentQuery;
import com.xiaohuashifu.recruit.organization.api.request.CreateDepartmentRequest;
import com.xiaohuashifu.recruit.organization.api.service.DepartmentLabelService;
import com.xiaohuashifu.recruit.organization.api.service.DepartmentService;
import com.xiaohuashifu.recruit.organization.api.service.OrganizationService;
import com.xiaohuashifu.recruit.organization.service.assembler.DepartmentAssembler;
import com.xiaohuashifu.recruit.organization.service.dao.DepartmentMapper;
import com.xiaohuashifu.recruit.organization.service.do0.DepartmentDO;
import com.xiaohuashifu.recruit.oss.api.service.ObjectStorageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
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

    // TODO: 2021/2/5 这里需要消息队列保证最终一致性
    @Override
    @Transactional
    public DepartmentDTO createDepartment(CreateDepartmentRequest request) {
        // 判断组织是否存在
        organizationService.getOrganization(request.getOrganizationId());

        // 判断组织是否已经有这个部门名或部门名缩写
        LambdaQueryWrapper<DepartmentDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DepartmentDO::getOrganizationId, request.getOrganizationId())
                .and(c -> c.eq(DepartmentDO::getDepartmentName, request.getDepartmentName())
                        .or()
                        .eq(DepartmentDO::getAbbreviationDepartmentName, request.getAbbreviationDepartmentName()));
        DepartmentDO departmentDO = departmentMapper.selectOne(wrapper);
        if (departmentDO != null) {
            throw new DuplicateServiceException("The departmentName or abbreviationDepartmentName already exist.");
        }

        // 创建部门
        DepartmentDO departmentDOForInsert = departmentAssembler.createDepartmentRequestToDepartmentDO(request);
        departmentDOForInsert.setLabels(new ArrayList<>());
        departmentMapper.insert(departmentDOForInsert);

        return getDepartment(departmentDOForInsert.getId());
    }

    // TODO: 2021/2/5 这里需要消息队列保证最终一致性
    @Override
    @Transactional
    @DistributedLock(value = DEPARTMENT_LABELS_LOCK_KEY_PATTERN, parameters = "#{#id}")
    public DepartmentDTO addLabel(Long id, String label) {
        // 判断标签数量是否大于 MAX_DEPARTMENT_LABEL_NUMBER
        DepartmentDTO departmentDTO = getDepartment(id);
        if (departmentDTO.getLabels().size() >= DepartmentConstants.MAX_DEPARTMENT_LABEL_NUMBER) {
            throw new OverLimitServiceException("The number of label must not be greater than "
                    + DepartmentConstants.MAX_DEPARTMENT_LABEL_NUMBER + ".");
        }

        // 判断该标签是否可用
        DepartmentLabelDTO departmentLabelDTO = departmentLabelService.getDepartmentLabelByLabelName(label);
        if (!departmentLabelDTO.getAvailable()) {
            throw new UnavailableServiceException("The label unavailable.");
        }

        // 添加部门的标签
        departmentMapper.addLabel(id, label);

        // 部门标签的引用数增加
        departmentLabelService.increaseReferenceNumberOrSaveDepartmentLabel(label);

        // 获取添加标签后的部门对象
        return getDepartment(id);
    }

    @Override
    public DepartmentDTO removeLabel(Long id, String label) {
        // 删除标签
        departmentMapper.removeLabel(id, label);

        // 获取删除标签后的部门对象
        return getDepartment(id);
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
                        DepartmentDO::getDepartmentName, query.getDepartmentName())
                .likeRight(query.getAbbreviationDepartmentName() != null,
                        DepartmentDO::getAbbreviationDepartmentName, query.getAbbreviationDepartmentName())
                .eq(query.getDeactivated() != null, DepartmentDO::getDeactivated, query.getDeactivated());

        Page<DepartmentDO> page = new Page<>(query.getPageNum(), query.getPageSize(), true);
        departmentMapper.selectPage(page, wrapper);
        List<DepartmentDTO> departmentDTOS = page.getRecords()
                .stream().map(departmentAssembler::departmentDOToDepartmentDTO).collect(Collectors.toList());
        return new QueryResult<>(page.getTotal(), departmentDTOS);
    }

    @Override
    public DepartmentDTO updateDepartmentName(Long id, String departmentName) {
        // 判断该组织是否已经存在该部门名
        DepartmentDTO departmentDTO = getDepartment(id);
        LambdaQueryWrapper<DepartmentDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DepartmentDO::getOrganizationId, departmentDTO.getOrganizationId())
                .eq(DepartmentDO::getDepartmentName, departmentName);
        int count = departmentMapper.selectCount(wrapper);
        if (count > 0) {
            throw new DuplicateServiceException("The departmentName already exist.");
        }

        // 更新部门名
        DepartmentDO departmentDOForUpdate = DepartmentDO.builder().id(id).departmentName(departmentName).build();
        departmentMapper.updateById(departmentDOForUpdate);

        // 获取更新后的部门对象
        return getDepartment(id);
    }

    @Override
    public DepartmentDTO updateAbbreviationDepartmentName(Long id, String abbreviationDepartmentName) {
        // 判断该组织是否已经存在该部门名缩写
        DepartmentDTO departmentDTO = getDepartment(id);
        LambdaQueryWrapper<DepartmentDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DepartmentDO::getOrganizationId, departmentDTO.getOrganizationId())
                .eq(DepartmentDO::getAbbreviationDepartmentName, abbreviationDepartmentName);
        int count = departmentMapper.selectCount(wrapper);
        if (count > 0) {
            throw new DuplicateServiceException("The abbreviationDepartmentName already exist.");
        }

        // 更新部门名缩写
        DepartmentDO departmentDOForUpdate = DepartmentDO.builder().id(id)
                .abbreviationDepartmentName(abbreviationDepartmentName).build();
        departmentMapper.updateById(departmentDOForUpdate);

        // 获取更新后的部门对象
        return getDepartment(id);
    }

    @Override
    public DepartmentDTO updateIntroduction(Long id, String introduction) {
        // 更新部门介绍
        DepartmentDO departmentDOForUpdate = DepartmentDO.builder().id(id)
                .introduction(introduction).build();
        departmentMapper.updateById(departmentDOForUpdate);

        // 获取更新后的部门对象
        return getDepartment(id);
    }

    @Override
    public DepartmentDTO updateLogo(Long id, String logoUrl) {
        // 链接 logo
        objectStorageService.linkObject(logoUrl);

        // 更新 logoUrl 到数据库
        DepartmentDO departmentDOForUpdate = DepartmentDO.builder().id(id).logoUrl(logoUrl).build();
        departmentMapper.updateById(departmentDOForUpdate);

        // 更新后的部门信息
        return getDepartment(id);
    }

    @Override
    @Transactional
    @DistributedLock(value = DEPARTMENT_DEACTIVATED_LOCK_KEY_PATTERN, parameters = "#{#id}")
    public DepartmentDTO deactivateDepartment(Long id) {
        // 判断是否已经被停用
        DepartmentDTO departmentDTO = getDepartment(id);
        if (departmentDTO.getDeactivated()) {
            throw new UnmodifiedServiceException("The department already deactivated.");
        }

        // 更新为停用
        DepartmentDO departmentDOForUpdate = DepartmentDO.builder().id(id).deactivated(true).build();
        departmentMapper.updateById(departmentDOForUpdate);

        // 停用后的部门对象
        return getDepartment(id);
    }

    @Override
    public int removeLabels(String label) {
        return departmentMapper.removeLabels(label);
    }

    @Override
    public DepartmentDTO increaseNumberOfMembers(Long id) {
        // 增加成员数
        departmentMapper.increaseNumberOfMembers(id);

        // 添加成员数后的部门对象
        return getDepartment(id);
    }

    @Override
    public DepartmentDTO decreaseNumberOfMembers(Long id) {
        // 减少成员数
        departmentMapper.decreaseNumberOfMembers(id);

        // 减少成员数后的部门对象
        return getDepartment(id);
    }

}
