package com.xiaohuashifu.recruit.user.service.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaohuashifu.recruit.common.exception.NotFoundServiceException;
import com.xiaohuashifu.recruit.common.exception.unprocessable.DuplicateServiceException;
import com.xiaohuashifu.recruit.common.exception.unprocessable.UnmodifiedServiceException;
import com.xiaohuashifu.recruit.common.exception.unprocessable.UnprocessableServiceException;
import com.xiaohuashifu.recruit.common.query.QueryResult;
import com.xiaohuashifu.recruit.user.api.dto.*;
import com.xiaohuashifu.recruit.user.api.query.RoleQuery;
import com.xiaohuashifu.recruit.user.api.request.CreateRoleRequest;
import com.xiaohuashifu.recruit.user.api.service.PermissionService;
import com.xiaohuashifu.recruit.user.api.service.RoleService;
import com.xiaohuashifu.recruit.user.api.service.UserService;
import com.xiaohuashifu.recruit.user.service.assembler.RoleAssembler;
import com.xiaohuashifu.recruit.user.service.assembler.RolePermissionAssembler;
import com.xiaohuashifu.recruit.user.service.assembler.UserRoleAssembler;
import com.xiaohuashifu.recruit.user.service.dao.RoleMapper;
import com.xiaohuashifu.recruit.user.service.dao.RolePermissionMapper;
import com.xiaohuashifu.recruit.user.service.dao.UserRoleMapper;
import com.xiaohuashifu.recruit.user.service.do0.RoleDO;
import com.xiaohuashifu.recruit.user.service.do0.RolePermissionDO;
import com.xiaohuashifu.recruit.user.service.do0.UserRoleDO;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.aop.framework.AopContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 描述：角色服务 RPC 接口
 *
 * @author: xhsf
 * @create: 2020/11/12 19:42
 */
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleMapper roleMapper;

    private final UserRoleMapper userRoleMapper;

    private final RolePermissionMapper rolePermissionMapper;

    private final RoleAssembler roleAssembler;

    private final UserRoleAssembler userRoleAssembler;

    private final RolePermissionAssembler rolePermissionAssembler;

    @Reference
    private UserService userService;

    @Reference
    private PermissionService permissionService;

    /**
     * 当角色没有父亲时的 parentRoleId
     */
    private static final Long NO_PARENT_ROLE_ID = 0L;

    public RoleServiceImpl(RoleMapper roleMapper, UserRoleMapper userRoleMapper, RoleAssembler roleAssembler,
                           UserRoleAssembler userRoleAssembler, RolePermissionMapper rolePermissionMapper,
                           RolePermissionAssembler rolePermissionAssembler) {
        this.roleMapper = roleMapper;
        this.userRoleMapper = userRoleMapper;
        this.roleAssembler = roleAssembler;
        this.userRoleAssembler = userRoleAssembler;
        this.rolePermissionMapper = rolePermissionMapper;
        this.rolePermissionAssembler = rolePermissionAssembler;
    }

    @Override
    public RoleDTO createRole(CreateRoleRequest request) {
        // 如果父角色编号不为0，则父角色必须存在
        RoleDTO parentRoleDTO = null;
        if (!Objects.equals(request.getParentRoleId(), NO_PARENT_ROLE_ID)) {
            parentRoleDTO = getRole(request.getParentRoleId());
        }

        // 去掉角色名两边的空白符
        request.setRoleName(request.getRoleName().trim());

        // 判断角色名存不存在，角色名必须不存在
        RoleDO roleDO = roleMapper.selectByRoleName(request.getRoleName());
        if (roleDO != null) {
            throw new DuplicateServiceException("The role name already exist.");
        }

        // 如果父角色编号不为0，且被禁用了，则该角色也应该被禁用
        if (!Objects.equals(request.getParentRoleId(), NO_PARENT_ROLE_ID)) {
            if (!parentRoleDTO.getAvailable()) {
                request.setAvailable(false);
            }
        }

        // 去掉角色描述两边的空白符
        request.setDescription(request.getDescription().trim());

        // 保存角色
        RoleDO roleDOForInsert = roleAssembler.createRoleRequestToRoleDO(request);
        roleMapper.insert(roleDOForInsert);
        return getRole(roleDOForInsert.getId());
    }

    @Override
    public UserRoleDTO createUserRole(Long userId, Long roleId) {
        // 判断该用户存不存在
        userService.getUser(userId);

        // 判断该角色存不存在
        getRole(roleId);

        // 判断该用户角色存不存在
        LambdaQueryWrapper<UserRoleDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRoleDO::getUserId, userId).eq(UserRoleDO::getRoleId, roleId);
        int count = userRoleMapper.selectCount(wrapper);
        if (count > 0) {
            throw new DuplicateServiceException("The user already has this role.");
        }

        // 绑定用户与角色
        UserRoleDO userRoleDOForInsert = UserRoleDO.builder().userId(userId).roleId(roleId).build();
        userRoleMapper.insert(userRoleDOForInsert);
        return getUserRole(userRoleDOForInsert.getId());
    }

    @Override
    public RolePermissionDTO createRolePermission(Long roleId, Long permissionId) {
        // 判断该角色存不存在
        getRole(roleId);

        // 判断该权限存不存在
        permissionService.getPermission(permissionId);

        // 判断该角色权限存不存在
        LambdaQueryWrapper<RolePermissionDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RolePermissionDO::getRoleId, roleId)
                .eq(RolePermissionDO::getPermissionId, permissionId);
        int count = rolePermissionMapper.selectCount(wrapper);
        if (count > 0) {
            throw new DuplicateServiceException("The role already has this permission.");
        }

        // 绑定角色权限
        RolePermissionDO rolePermissionDOForInsert =
                RolePermissionDO.builder().roleId(roleId).permissionId(permissionId).build();
        rolePermissionMapper.insert(rolePermissionDOForInsert);
        return getRolePermission(rolePermissionDOForInsert.getId());
    }

    @Override
    @Transactional
    public void removeRole(Long id) {
        // 判断该角色存不存在
        getRole(id);

        // 判断该角色是否还拥有子角色，必须没有子角色才可以删除
        LambdaQueryWrapper<RoleDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleDO::getParentRoleId, id);
        int count = roleMapper.selectCount(wrapper);
        if (count > 0) {
            throw new UnprocessableServiceException("The role exists children.");
        }

        // 删除该角色所关联的权限（Permission）的关联关系
        LambdaQueryWrapper<RolePermissionDO> wrapperForDeleteRolePermission = new LambdaQueryWrapper<>();
        wrapperForDeleteRolePermission.eq(RolePermissionDO::getRoleId, id);
        rolePermissionMapper.delete(wrapperForDeleteRolePermission);

        // 删除该角色关联的用户的关联关系
        LambdaQueryWrapper<UserRoleDO> wrapperForDeleteUserRole = new LambdaQueryWrapper<>();
        wrapperForDeleteUserRole.eq(UserRoleDO::getRoleId, id);
        userRoleMapper.delete(wrapperForDeleteUserRole);

        // 删除该角色
        roleMapper.deleteById(id);
    }

    @Override
    public void removeUserRole(Long userId, Long roleId) {
        // 删除用户角色
        LambdaQueryWrapper<UserRoleDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRoleDO::getUserId, userId)
                .eq(UserRoleDO::getRoleId, roleId);
        userRoleMapper.delete(wrapper);
    }

    @Override
    public void removeRolePermission(Long roleId, Long permissionId) {
        // 删除角色权限
        LambdaQueryWrapper<RolePermissionDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RolePermissionDO::getRoleId, roleId)
                .eq(RolePermissionDO::getPermissionId, permissionId);
        rolePermissionMapper.delete(wrapper);
    }

    @Override
    public RoleDTO getRole(Long id) {
        RoleDO roleDO = roleMapper.selectById(id);
        if (roleDO == null) {
            throw new NotFoundServiceException("role", "id", id);
        }
        return roleAssembler.roleDOToRoleDTO(roleDO);
    }

    @Override
    public UserRoleDTO getUserRole(Long id) {
        UserRoleDO userRoleDO = userRoleMapper.selectById(id);
        if (userRoleDO == null) {
            throw new NotFoundServiceException("userRole", "id", id);
        }
        return userRoleAssembler.userRoleDOToUserRoleDTO(userRoleDO);
    }

    @Override
    public RolePermissionDTO getRolePermission(Long id) {
        RolePermissionDO rolePermissionDO = rolePermissionMapper.selectById(id);
        if (rolePermissionDO == null) {
            throw new NotFoundServiceException("rolePermission", "id", id);
        }
        return rolePermissionAssembler.rolePermissionDOToRolePermissionDTO(rolePermissionDO);
    }

    @Override
    public QueryResult<RoleDTO> listRoles(RoleQuery query) {
        LambdaQueryWrapper<RoleDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(query.getParentRoleId() != null, RoleDO::getParentRoleId, query.getParentRoleId())
                .likeRight(query.getRoleName() != null, RoleDO::getRoleName, query.getRoleName())
                .eq(query.getAvailable() != null, RoleDO::getAvailable, query.getAvailable());

        Page<RoleDO> page = new Page<>(query.getPageNum(), query.getPageSize(), true);
        roleMapper.selectPage(page, wrapper);
        List<RoleDTO> roleDTOS = page.getRecords()
                .stream().map(roleAssembler::roleDOToRoleDTO).collect(Collectors.toList());
        return new QueryResult<>(page.getTotal(), roleDTOS);
    }

    @Override
    public RoleDTO updateRoleName(Long id, String roleName) {
        // 判断该角色存不存在，该角色必须存在
        getRole(id);

        // 去除角色名两边空白符
        roleName = roleName.trim();

        // 判断新角色名存不存在，新角色名必须不存在
        RoleDO roleDO = roleMapper.selectByRoleName(roleName);
        if (roleDO != null) {
            throw new DuplicateServiceException("The role name already exist.");
        }

        // 更新角色名
        RoleDO roleDOForUpdate = RoleDO.builder().id(id).roleName(roleName).build();
        roleMapper.updateById(roleDOForUpdate);
        return getRole(id);
    }

    @Override
    public RoleDTO updateDescription(Long id, String description) {
        // 去除角色描述两边空白符
        description = description.trim();

        // 更新角色描述
        RoleDO roleDOForUpdate = RoleDO.builder().id(id).description(description).build();
        roleMapper.updateById(roleDOForUpdate);
        return getRole(id);
    }

    @Override
    @Transactional
    public DisableRoleDTO disableRole(Long id) {
        // 判断该角色存不存在
        RoleDTO roleDTO = getRole(id);

        // 判断该角色是否已经被禁用
        if (!roleDTO.getAvailable()) {
            throw new UnmodifiedServiceException("The role already unavailable.");
        }

        // 递归的禁用角色
        int disabledCount = ((RoleServiceImpl)AopContext.currentProxy()).recursiveDisableRole(id);
        return new DisableRoleDTO(getRole(id), disabledCount);
    }

    @Override
    @Transactional
    public EnableRoleDTO enableRole(Long id) {
        // 判断该角色存不存在
        RoleDTO roleDTO = getRole(id);

        // 不能解禁已经有效的角色
        if (roleDTO.getAvailable()) {
            throw new UnmodifiedServiceException("The role is available.");
        }

        // 如果该角色的父角色编号不为0
        // 判断该角色的父角色是否已经被禁用，如果父角色已经被禁用，则无法解禁该角色
        if (!Objects.equals(roleDTO.getParentRoleId(), NO_PARENT_ROLE_ID)) {
            RoleDTO parentRoleDTO = getRole(roleDTO.getParentRoleId());
            if (!parentRoleDTO.getAvailable()) {
                throw new UnmodifiedServiceException("Can't enable this role, because the parent role is disable.");
            }
        }

        // 递归的解禁角色
        int enabledCount = ((RoleServiceImpl)AopContext.currentProxy()).recursiveEnableRole(id);
        return new EnableRoleDTO(getRole(id), enabledCount);
    }

    @Override
    @Transactional
    public DisableRoleDTO setParentRole(Long id, Long parentRoleId) {
        // 判断该角色存不存在
        RoleDTO roleDTO = getRole(id);

        // 如果原来的父角色编号和要设置的父角色编号相同，则直接返回
        if (Objects.equals(roleDTO.getParentRoleId(), parentRoleId)) {
            throw new UnmodifiedServiceException("The new parent can't same as old parent.");
        }

        // 若父角色编号不为0，则判断要设置的父角色是否存在
        if (!Objects.equals(parentRoleId, NO_PARENT_ROLE_ID)) {
            getRole(parentRoleId);
        }

        // 设置父角色
        RoleDO roleDOForUpdate = RoleDO.builder().id(id).parentRoleId(parentRoleId).build();
        roleMapper.updateById(roleDOForUpdate);

        // 如果要设置的父角色编号为0（取消父角色）
        // 或者要设置的父角色的状态为可用
        // 或者要设置的父角色的状态为禁用且当前角色的状态也为禁用，则直接返回
        boolean canReturn = Objects.equals(parentRoleId, NO_PARENT_ROLE_ID)
                || roleMapper.selectById(parentRoleId).getAvailable()
                || !roleDTO.getAvailable();
        if (canReturn) {
            return new DisableRoleDTO(getRole(id), 0);
        }

        // 如果父角色状态为禁用，而该角色的状态为可用，则递归更新该角色状态为禁用
        return ((RoleServiceImpl)AopContext.currentProxy()).disableRole(id);
    }

    /**
     * 递归的禁用角色
     *
     * @param id 角色编号
     * @return 总共禁用的角色数量
     */
    @Transactional
    protected int recursiveDisableRole(Long id) {
        RoleDO roleDOForUpdate = RoleDO.builder().id(id).available(false).build();
        int count = roleMapper.updateById(roleDOForUpdate);

        LambdaQueryWrapper<RoleDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleDO::getParentRoleId, id).eq(RoleDO::getAvailable, true);
        List<RoleDO> roleDOS = roleMapper.selectList(wrapper);
        for (RoleDO roleDO : roleDOS) {
            count += recursiveDisableRole(roleDO.getId());
        }
        return count;
    }

    /**
     * 递归的解禁角色
     *
     * @param id 角色编号
     * @return 总共解禁的角色数量
     */
    @Transactional
    protected int recursiveEnableRole(Long id) {
        RoleDO roleDOForUpdate = RoleDO.builder().available(true).build();
        LambdaUpdateWrapper<RoleDO> wrapperForUpdate = new LambdaUpdateWrapper<>();
        wrapperForUpdate.eq(RoleDO::getAvailable, false).eq(RoleDO::getId, id);
        int count = roleMapper.update(roleDOForUpdate, wrapperForUpdate);

        LambdaQueryWrapper<RoleDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleDO::getParentRoleId, id);
        List<RoleDO> roleDOS = roleMapper.selectList(wrapper);
        for (RoleDO roleDO : roleDOS) {
            count += recursiveEnableRole(roleDO.getId());
        }
        return count;
    }

}
