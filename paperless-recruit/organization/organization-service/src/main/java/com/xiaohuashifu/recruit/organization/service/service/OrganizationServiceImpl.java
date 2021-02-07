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
import com.xiaohuashifu.recruit.organization.api.constant.OrganizationConstants;
import com.xiaohuashifu.recruit.organization.api.dto.OrganizationDTO;
import com.xiaohuashifu.recruit.organization.api.dto.OrganizationLabelDTO;
import com.xiaohuashifu.recruit.organization.api.query.OrganizationQuery;
import com.xiaohuashifu.recruit.organization.api.request.CreateOrganizationRequest;
import com.xiaohuashifu.recruit.organization.api.service.OrganizationLabelService;
import com.xiaohuashifu.recruit.organization.api.service.OrganizationService;
import com.xiaohuashifu.recruit.organization.service.assembler.OrganizationAssembler;
import com.xiaohuashifu.recruit.organization.service.dao.OrganizationMapper;
import com.xiaohuashifu.recruit.organization.service.do0.OrganizationDO;
import com.xiaohuashifu.recruit.oss.api.service.ObjectStorageService;
import com.xiaohuashifu.recruit.user.api.dto.UserDTO;
import com.xiaohuashifu.recruit.user.api.service.RoleService;
import com.xiaohuashifu.recruit.user.api.service.UserService;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 描述：组织服务
 *
 * @author xhsf
 * @create 2020/12/8 21:56
 */
@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Reference
    private OrganizationLabelService organizationLabelService;

    @Reference
    private UserService userService;

    @Reference
    private RoleService roleService;

    @Reference
    private ObjectStorageService objectStorageService;

    private final OrganizationMapper organizationMapper;

    private final OrganizationAssembler organizationAssembler;

    /**
     * 组织默认角色编号
     */
    private static final Long ORGANIZATION_DEFAULT_ROLE_ID = 14L;

    /**
     * 创建组织邮件验证码标题
     */
    private static final String CREATE_ORGANIZATION_EMAIL_AUTH_CODE_TITLE = "创建组织";

    /**
     * 组织标签锁定键模式，{0}是组织编号
     */
    private static final String ORGANIZATION_LABELS_LOCK_KEY_PATTERN = "organizations:{0}:labels";

    /**
     * 组织邮箱锁定键模式，{0}是邮箱
     */
    private static final String ORGANIZATION_EMAIL_LOCK_KEY_PATTERN = "organizations:email:{0}";

    /**
     * 组织 available 属性锁定键模式，{0}是组织编号
     */
    private static final String ORGANIZATION_AVAILABLE_LOCK_KEY_PATTERN = "organizations:{0}:available";

    public OrganizationServiceImpl(OrganizationMapper organizationMapper, OrganizationAssembler organizationAssembler) {
        this.organizationMapper = organizationMapper;
        this.organizationAssembler = organizationAssembler;
    }

    // TODO: 2021/2/5 这里需要用消息队列使得最终一致性
    @Override
    @Transactional
    @DistributedLock(value = ORGANIZATION_EMAIL_LOCK_KEY_PATTERN, parameters = "#{#request.email}")
    public OrganizationDTO createOrganization(CreateOrganizationRequest request) {
        // 注册主体账号
        UserDTO userDTO = userService.createUserByEmailAuthCode(
                organizationAssembler.createOrganizationRequestToCreateUserByEmailAuthCodeRequest(request));

        // 赋予主体组织的基本权限
        roleService.createUserRole(userDTO.getId(), ORGANIZATION_DEFAULT_ROLE_ID);

        // 创建组织
        OrganizationDO organizationDO = OrganizationDO.builder().userId(userDTO.getId()).build();
        organizationMapper.insert(organizationDO);

        // 获取组织
        return getOrganization(organizationDO.getId());
    }

    // TODO: 2021/2/5 添加消息队列保证最终一致性
    @Override
    @Transactional
    @DistributedLock(value = ORGANIZATION_LABELS_LOCK_KEY_PATTERN, parameters = "#{#id}")
    public OrganizationDTO addLabel(Long id, String label) {
        // 判断标签数量是否大于 MAX_ORGANIZATION_LABEL_NUMBER
        OrganizationDTO organizationDTO = getOrganization(id);
        if (organizationDTO.getLabels().size() >= OrganizationConstants.MAX_ORGANIZATION_LABEL_NUMBER) {
            throw new OverLimitServiceException("The number of labels must not be greater than "
                    + OrganizationConstants.MAX_ORGANIZATION_LABEL_NUMBER + ".");
        }

        // 判断该标签是否可用
        OrganizationLabelDTO organizationLabelDTO = organizationLabelService.getOrganizationLabelByLabelName(label);
        if (!organizationLabelDTO.getAvailable()) {
            throw new UnavailableServiceException("The label unavailable.");
        }

        // 添加组织的标签
        int count = organizationMapper.addLabel(id, label);
        // 如果失败表示该标签已经存在
        if (count < 1) {
            throw new DuplicateServiceException("The organization already owns this label.");
        }

        // 组织标签的引用数增加
        organizationLabelService.increaseReferenceNumberOrSaveOrganizationLabel(label);

        // 获取添加标签后的组织对象
        return getOrganization(id);
    }

    @Override
    public OrganizationDTO removeLabel(Long id, String label) {
        // 删除标签
        organizationMapper.removeLabel(id, label);

        // 获取删除标签后的组织对象
        return getOrganization(id);
    }

    @Override
    public OrganizationDTO getOrganization(Long id) {
        OrganizationDO organizationDO = organizationMapper.selectById(id);
        if (organizationDO == null) {
            throw new NotFoundServiceException("organization", "id", id);
        }
        return organizationAssembler.organizationDOToOrganizationDTO(organizationDO);
    }

    @Override
    public OrganizationDTO getOrganizationByUserId(Long userId) {
        OrganizationDO organizationDO = organizationMapper.selectByUserId(userId);
        if (organizationDO == null) {
            throw new NotFoundServiceException("organization", "userId", userId);
        }
        return organizationAssembler.organizationDOToOrganizationDTO(organizationDO);
    }

    @Override
    public QueryResult<OrganizationDTO> listOrganizations(OrganizationQuery query) {
        LambdaQueryWrapper<OrganizationDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(query.getUserId() != null,
                OrganizationDO::getUserId, query.getUserId())
                .likeRight(query.getOrganizationName() != null,
                        OrganizationDO::getOrganizationName, query.getOrganizationName())
                .likeRight(query.getAbbreviationOrganizationName() != null,
                        OrganizationDO::getAbbreviationOrganizationName, query.getAbbreviationOrganizationName())
                .eq(query.getAvailable() != null, OrganizationDO::getAvailable, query.getAvailable());

        Page<OrganizationDO> page = new Page<>(query.getPageNum(), query.getPageSize(), true);
        organizationMapper.selectPage(page, wrapper);
        System.out.println(page.getRecords());
        List<OrganizationDTO> departmentDTOS = page.getRecords()
                .stream().map(organizationAssembler::organizationDOToOrganizationDTO).collect(Collectors.toList());
        return new QueryResult<>(page.getTotal(), departmentDTOS);
    }

    @Override
    public OrganizationDTO updateOrganizationName(Long id, String organizationName) {
        // 更新组织名
        OrganizationDO organizationDOForUpdate =
                OrganizationDO.builder().id(id).organizationName(organizationName).build();
        organizationMapper.updateById(organizationDOForUpdate);

        // 获取更新后的组织对象
        return getOrganization(id);
    }

    @Override
    public OrganizationDTO updateAbbreviationOrganizationName(Long id, String abbreviationOrganizationName) {
        // 更新组织名缩写
        OrganizationDO organizationDOForUpdate =
                OrganizationDO.builder().id(id).abbreviationOrganizationName(abbreviationOrganizationName).build();
        organizationMapper.updateById(organizationDOForUpdate);

        // 获取更新后的组织对象
        return getOrganization(id);
    }

    @Override
    public OrganizationDTO updateIntroduction(Long id, String introduction) {
        // 更新组织介绍
        OrganizationDO organizationDOForUpdate = OrganizationDO.builder().id(id).introduction(introduction).build();
        organizationMapper.updateById(organizationDOForUpdate);

        // 获取更新后的组织对象
        return getOrganization(id);
    }

    @Override
    public OrganizationDTO updateLogo(Long id, String logoUrl) {
        // 链接 logo
        objectStorageService.linkObject(logoUrl);

        // 更新 logoUrl 到数据库
        OrganizationDO organizationDOForUpdate = OrganizationDO.builder().id(id).logoUrl(logoUrl).build();
        organizationMapper.updateById(organizationDOForUpdate);

        // 更新后的组织信息
        return getOrganization(id);
    }

    @Override
    @DistributedLock(value = ORGANIZATION_AVAILABLE_LOCK_KEY_PATTERN, parameters = "#{#id}")
    public OrganizationDTO disableOrganization(Long id) {
        // 判断组织是否已经被禁用
        OrganizationDTO organizationDTO = getOrganization(id);
        if (!organizationDTO.getAvailable()) {
            throw new UnmodifiedServiceException("The organization already unavailable.");
        }

        // 禁用组织
        OrganizationDO organizationDOForUpdate = OrganizationDO.builder().id(id).available(false).build();
        organizationMapper.updateById(organizationDOForUpdate);

        // 获取禁用后的组织对象
        return getOrganization(id);
    }

    @Override
    @DistributedLock(value = ORGANIZATION_AVAILABLE_LOCK_KEY_PATTERN, parameters = "#{#id}")
    public OrganizationDTO enableOrganization(Long id) {
        // 判断组织是否可用
        OrganizationDTO organizationDTO = getOrganization(id);
        if (organizationDTO.getAvailable()) {
            throw new UnmodifiedServiceException("The organization already available.");
        }

        // 解禁组织
        OrganizationDO organizationDOForUpdate = OrganizationDO.builder().id(id).available(true).build();
        organizationMapper.updateById(organizationDOForUpdate);

        // 获取解禁后的组织对象
        return getOrganization(id);
    }

    @Override
    public void sendEmailAuthCodeForSignUp(String email) {
        userService.sendEmailAuthCodeForSignUp(email, CREATE_ORGANIZATION_EMAIL_AUTH_CODE_TITLE);
    }

    @Override
    public OrganizationDTO increaseNumberOfMembers(Long id) {
        // 增加成员数
        organizationMapper.increaseMemberNumber(id);

        // 添加成员数后的组织对象
        return getOrganization(id);
    }

    @Override
    public OrganizationDTO decreaseNumberOfMembers(Long id) {
        // 减少成员数
        organizationMapper.decreaseMemberNumber(id);

        // 减少成员数后的组织对象
        return getOrganization(id);
    }

    @Override
    public OrganizationDTO increaseNumberOfDepartments(Long id) {
        // 增加部门数量
        organizationMapper.increaseNumberOfDepartments(id);

        // 增加部门数后的组织对象
        return getOrganization(id);
    }

    @Override
    public OrganizationDTO decreaseNumberOfDepartments(Long id) {
        // 增加部门数量
        organizationMapper.decreaseNumberOfDepartments(id);

        // 减少部门数后的组织对象
        return getOrganization(id);
    }

    @Override
    public int removeLabels(String label) {
        return organizationMapper.removeLabels(label);
    }

}
