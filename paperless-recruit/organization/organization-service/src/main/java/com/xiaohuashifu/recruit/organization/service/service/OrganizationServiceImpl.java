package com.xiaohuashifu.recruit.organization.service.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaohuashifu.recruit.common.aspect.annotation.DistributedLock;
import com.xiaohuashifu.recruit.common.exception.NotFoundServiceException;
import com.xiaohuashifu.recruit.common.exception.unprocessable.InvalidValueServiceException;
import com.xiaohuashifu.recruit.common.exception.unprocessable.OverLimitServiceException;
import com.xiaohuashifu.recruit.common.query.QueryResult;
import com.xiaohuashifu.recruit.common.util.ObjectUtils;
import com.xiaohuashifu.recruit.organization.api.constant.OrganizationConstants;
import com.xiaohuashifu.recruit.organization.api.dto.OrganizationDTO;
import com.xiaohuashifu.recruit.organization.api.query.OrganizationQuery;
import com.xiaohuashifu.recruit.organization.api.request.CreateOrganizationRequest;
import com.xiaohuashifu.recruit.organization.api.request.UpdateOrganizationRequest;
import com.xiaohuashifu.recruit.organization.api.service.OrganizationLabelService;
import com.xiaohuashifu.recruit.organization.api.service.OrganizationService;
import com.xiaohuashifu.recruit.organization.service.assembler.OrganizationAssembler;
import com.xiaohuashifu.recruit.organization.service.dao.OrganizationMapper;
import com.xiaohuashifu.recruit.organization.service.do0.OrganizationDO;
import com.xiaohuashifu.recruit.oss.api.service.ObjectStorageService;
import com.xiaohuashifu.recruit.user.api.dto.UserDTO;
import com.xiaohuashifu.recruit.user.api.service.RoleService;
import com.xiaohuashifu.recruit.user.api.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
     * 组织邮箱锁定键模式，{0}是邮箱
     */
    private static final String ORGANIZATION_EMAIL_LOCK_KEY_PATTERN = "organizations:email:{0}";

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
        UserDTO userDTO = userService.registerByEmailAuthCode(
                organizationAssembler.createOrganizationRequestToCreateUserByEmailAuthCodeRequest(request));

        // 赋予主体组织的基本权限
        roleService.createUserRole(userDTO.getId(), ORGANIZATION_DEFAULT_ROLE_ID);

        // 创建组织
        OrganizationDO organizationDO = OrganizationDO.builder().userId(userDTO.getId()).build();
        organizationMapper.insert(organizationDO);

        // 获取组织
        return getOrganization(organizationDO.getId());
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
                        OrganizationDO::getOrganizationName, query.getOrganizationName());

        Page<OrganizationDO> page = new Page<>(query.getPageNum(), query.getPageSize(), true);
        organizationMapper.selectPage(page, wrapper);
        List<OrganizationDTO> departmentDTOS = page.getRecords()
                .stream().map(organizationAssembler::organizationDOToOrganizationDTO).collect(Collectors.toList());
        return new QueryResult<>(page.getTotal(), departmentDTOS);
    }

    @Override
    @Transactional
    public OrganizationDTO updateOrganization(UpdateOrganizationRequest request) {
        // 判断组织是否存在并锁定
        OrganizationDO organizationDO = organizationMapper.selectByIdForUpdate(request.getId());
        if (organizationDO == null) {
            throw new NotFoundServiceException("organization", "id", request.getId());
        }

        // 更新组织
        OrganizationDO organizationDOForUpdate = checkForUpdate(request);
        organizationMapper.updateById(organizationDOForUpdate);
        return getOrganization(request.getId());
    }

    @Override
    public void sendEmailAuthCodeForCreateOrganization(String email) {
        userService.sendEmailAuthCodeForSignUp(email, CREATE_ORGANIZATION_EMAIL_AUTH_CODE_TITLE);
    }

    /**
     * 检查更新参数
     *
     * @param request UpdateOrganizationRequest
     * @return OrganizationDO
     */
    private OrganizationDO checkForUpdate(UpdateOrganizationRequest request) {
        // 链接 logo
        if (request.getLogoUrl() != null) {
            objectStorageService.linkObject(request.getLogoUrl());
        }

        // 判断组织标签列表是否合法
        if (request.getLabels() != null) {
            Set<String> labelSet = new HashSet<>(request.getLabels());
            labelSet.forEach((label)-> {
                if (StringUtils.isBlank(label) || label.trim().length() >
                        OrganizationConstants.MAX_ORGANIZATION_LABEL_LENGTH) {
                    throw new OverLimitServiceException("组织标签长度必须小于"
                            + OrganizationConstants.MAX_ORGANIZATION_LABEL_LENGTH);
                }
            });
            request.setLabels(new ArrayList<>(labelSet));
        }

        // TODO: 2021/3/9 这里需要判断组织类型是否存在
        // 判断组织类型是否合法


        // 判断组织规模是否合法
        if (!OrganizationConstants.ORGANIZATION_SIZE_SET.contains(request.getSize().trim())) {
            throw new InvalidValueServiceException("非法组织规模");
        }

        // 转换成 DO 对象
        OrganizationDO organizationDO = organizationAssembler.updateOrganizationRequestToOrganizationDO(request);
        ObjectUtils.trimAllStringFields(organizationDO);
        return organizationDO;
    }

}
