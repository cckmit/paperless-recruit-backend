package com.xiaohuashifu.recruit.user.service.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaohuashifu.recruit.common.exception.NotFoundServiceException;
import com.xiaohuashifu.recruit.common.exception.unprocessable.DuplicateServiceException;
import com.xiaohuashifu.recruit.common.exception.unprocessable.UnmodifiedServiceException;
import com.xiaohuashifu.recruit.common.exception.unprocessable.UnprocessableServiceException;
import com.xiaohuashifu.recruit.common.query.QueryResult;
import com.xiaohuashifu.recruit.user.api.dto.DisablePermissionDTO;
import com.xiaohuashifu.recruit.user.api.dto.EnablePermissionDTO;
import com.xiaohuashifu.recruit.user.api.dto.PermissionDTO;
import com.xiaohuashifu.recruit.user.api.query.PermissionQuery;
import com.xiaohuashifu.recruit.user.api.request.CreatePermissionRequest;
import com.xiaohuashifu.recruit.user.api.request.UpdatePermissionRequest;
import com.xiaohuashifu.recruit.user.api.service.PermissionService;
import com.xiaohuashifu.recruit.user.service.assembler.PermissionAssembler;
import com.xiaohuashifu.recruit.user.service.dao.PermissionMapper;
import com.xiaohuashifu.recruit.user.service.dao.RolePermissionMapper;
import com.xiaohuashifu.recruit.user.service.do0.PermissionDO;
import com.xiaohuashifu.recruit.user.service.do0.RolePermissionDO;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.aop.framework.AopContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 描述：权限服务 RPC 接口实现
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/12 20:46
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    private final PermissionMapper permissionMapper;

    private final RolePermissionMapper rolePermissionMapper;

    private final PermissionAssembler permissionAssembler;

    /**
     * 当权限没有父亲时的 parentPermissionId
     */
    private static final Long NO_PARENT_PERMISSION_ID = 0L;

    public PermissionServiceImpl(PermissionMapper permissionMapper, RolePermissionMapper rolePermissionMapper,
                                 PermissionAssembler permissionAssembler) {
        this.permissionMapper = permissionMapper;
        this.rolePermissionMapper = rolePermissionMapper;
        this.permissionAssembler = permissionAssembler;
    }

    @Override
    public PermissionDTO createPermission(CreatePermissionRequest request) {
        // 如果父权限编号不为0，则父权限必须存在
        if (!Objects.equals(request.getParentPermissionId(), NO_PARENT_PERMISSION_ID)) {
            PermissionDTO parentPermissionDTO = getPermission(request.getParentPermissionId());
            // 如果父权限被禁用了，则该权限也应该被禁用
            if (!parentPermissionDTO.getAvailable()) {
                request.setAvailable(false);
            }
        }

        // 去掉权限名两边的空白符
        request.setPermissionName(request.getPermissionName().trim());

        // 判断权限名存不存在，权限名必须不存在
        PermissionDO permissionDO = permissionMapper.selectByPermissionName(request.getPermissionName());
        if (permissionDO != null) {
            throw new DuplicateServiceException("The permission name already exist.");
        }

        // 去掉权限描述和授权路径两边的空白符
        request.setDescription(request.getDescription().trim());
        request.setAuthorizationUrl(request.getAuthorizationUrl().trim());

        // 保存权限
        PermissionDO permissionDOForInsert = permissionAssembler.createPermissionRequestToPermissionDO(request);
        permissionMapper.insert(permissionDOForInsert);
        return getPermission(permissionDOForInsert.getId());
    }

    @Override
    @Transactional
    public void removePermission(Long id) {
        // 判断该权限存不存在
        getPermission(id);

        // 判断该权限是否还拥有子权限，必须没有子权限才可以删除
        LambdaQueryWrapper<PermissionDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PermissionDO::getParentPermissionId, id);
        int count = permissionMapper.selectCount(wrapper);
        if (count > 0) {
            throw new UnprocessableServiceException("The permission exist children.");
        }

        // 删除该权限所关联的角色 Role 的关联关系
        LambdaQueryWrapper<RolePermissionDO> wrapperForDelete = new LambdaQueryWrapper<>();
        wrapperForDelete.eq(RolePermissionDO::getPermissionId, id);
        rolePermissionMapper.delete(wrapperForDelete);

        // 删除该权限
        permissionMapper.deleteById(id);
    }

    @Override
    public PermissionDTO getPermission(Long id) {
        PermissionDO permissionDO = permissionMapper.selectById(id);
        if (permissionDO == null) {
            throw new NotFoundServiceException("permission", "id", id);
        }
        return permissionAssembler.permissionDOToPermissionDTO(permissionDO);
    }

    @Override
    public QueryResult<PermissionDTO> listPermissions(PermissionQuery query) {
        LambdaQueryWrapper<PermissionDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(query.getParentPermissionId() != null, PermissionDO::getParentPermissionId,
                query.getParentPermissionId())
                .likeRight(query.getPermissionName() != null, PermissionDO::getPermissionName,
                        query.getPermissionName())
                .likeRight(query.getAuthorizationUrl() != null, PermissionDO::getAuthorizationUrl,
                        query.getAuthorizationUrl())
                .eq(query.getAvailable() != null, PermissionDO::getAvailable, query.getAvailable());

        Page<PermissionDO> page = new Page<>(query.getPageNum(), query.getPageSize(), true);
        permissionMapper.selectPage(page, wrapper);
        List<PermissionDTO> permissionDTOS = page.getRecords()
                .stream().map(permissionAssembler::permissionDOToPermissionDTO).collect(Collectors.toList());
        return new QueryResult<>(page.getTotal(), permissionDTOS);
    }

    @Override
    public PermissionDTO updatePermission(UpdatePermissionRequest request) {
        if (request.getPermissionName() != null) {
            // 去除权限名两边空白符
            request.setPermissionName(request.getPermissionName().trim());

            // 判断新权限名存不存在，新权限名必须不存在
            PermissionDO permissionDO = permissionMapper.selectByPermissionName(request.getPermissionName());
            if (permissionDO != null) {
                throw new DuplicateServiceException("The new permission name already exist.");
            }
        }

        if (request.getAuthorizationUrl() != null) {
            // 去除授权路径两边空白符
            request.setAuthorizationUrl(request.getAuthorizationUrl().trim());
        }

        if (request.getDescription() != null) {
            // 去除权限描述两边空白符
            request.setDescription(request.getDescription().trim());
        }

        PermissionDO permissionDOForUpdate = permissionAssembler.updatePermissionRequestToPermissionDO(request);
        permissionMapper.updateById(permissionDOForUpdate);
        return getPermission(request.getId());
    }

    @Override
    @Transactional
    public DisablePermissionDTO disablePermission(Long id) {
        // 判断该权限存不存在
        PermissionDTO permissionDTO = getPermission(id);

        // 判断该权限是否已经被禁用
        if (!permissionDTO.getAvailable()) {
            throw new UnmodifiedServiceException("The permission already disable.");
        }

        // 递归的禁用权限
        int disabledCount = ((PermissionServiceImpl)AopContext.currentProxy()).recursiveDisablePermission(id);
        return new DisablePermissionDTO(getPermission(id), disabledCount);
    }

    @Override
    public EnablePermissionDTO enablePermission(Long id) {
        // 判断该权限存不存在
        PermissionDTO permissionDTO = getPermission(id);

        // 不能解禁已经有效的权限
        if (permissionDTO.getAvailable()) {
            throw new UnmodifiedServiceException("The permission is available.");
        }

        // 如果该权限的父权限编号不为0
        // 判断该权限的父权限是否已经被禁用，如果父权限已经被禁用，则无法解禁该权限
        if (!Objects.equals(permissionDTO.getParentPermissionId(), NO_PARENT_PERMISSION_ID)) {
            PermissionDTO parentPermissionDTO = getPermission(permissionDTO.getParentPermissionId());
            if (!parentPermissionDTO.getAvailable()) {
                throw new UnmodifiedServiceException(
                        "Can't enable this permission, because the parent permission is disable.");
            }
        }

        // 递归的解禁权限
        int enabledCount = ((PermissionServiceImpl)AopContext.currentProxy()).recursiveEnablePermission(id);
        return new EnablePermissionDTO(getPermission(id), enabledCount);
    }

    @Override
    @Transactional
    public DisablePermissionDTO setParentPermission(Long id, Long parentPermissionId) {
        // 判断该权限存不存在
        PermissionDTO permissionDTO = getPermission(id);

        // 如果原来的父权限编号和要设置的父权限编号相同，则直接返回
        if (Objects.equals(permissionDTO.getParentPermissionId(), parentPermissionId)) {
            throw new UnmodifiedServiceException("The new permission same as the old permission.");
        }

        // 若父权限编号不为0，则判断要设置的父权限是否存在
        if (!Objects.equals(parentPermissionId, NO_PARENT_PERMISSION_ID)) {
            getPermission(parentPermissionId);
        }

        // 设置父权限
        PermissionDO permissionDOForUpdate =
                PermissionDO.builder().id(id).parentPermissionId(parentPermissionId).build();
        permissionMapper.updateById(permissionDOForUpdate);

        // 如果要设置的父权限编号为0（取消父权限）
        // 或者要设置的父权限的状态为可用
        // 或者要设置的父权限的状态为禁用且当前权限的状态也为禁用，则直接返回
        boolean canReturn = Objects.equals(parentPermissionId, NO_PARENT_PERMISSION_ID)
                || permissionMapper.selectById(parentPermissionId).getAvailable()
                || !permissionDTO.getAvailable();
        if (canReturn) {
            return new DisablePermissionDTO(getPermission(id), 0);
        }

        // 如果父权限状态为禁用，而该权限的状态为可用，则递归更新该权限状态为禁用
        return ((PermissionServiceImpl)AopContext.currentProxy()).disablePermission(id);
    }

    /**
     * 递归的禁用权限
     *
     * @param id 权限编号
     * @return 总共禁用的权限数量
     */
    @Transactional
    protected int recursiveDisablePermission(Long id) {
        PermissionDO permissionDOForUpdate = PermissionDO.builder().id(id).available(false).build();
        int count = permissionMapper.updateById(permissionDOForUpdate);

        LambdaQueryWrapper<PermissionDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PermissionDO::getParentPermissionId, id).eq(PermissionDO::getAvailable, true);
        List<PermissionDO> permissionDOS = permissionMapper.selectList(wrapper);
        for (PermissionDO permissionDO : permissionDOS) {
            count += recursiveDisablePermission(permissionDO.getId());
        }
        return count;
    }

    /**
     * 递归的解禁权限
     *
     * @param id 权限编号
     * @return 总共解禁的权限数量
     */
    @Transactional
    protected int recursiveEnablePermission(Long id) {
        LambdaUpdateWrapper<PermissionDO> wrapperForUpdate = new LambdaUpdateWrapper<>();
        wrapperForUpdate.eq(PermissionDO::getAvailable, false).eq(PermissionDO::getId, id);
        PermissionDO permissionDOForUpdate = PermissionDO.builder().available(true).build();
        int count = permissionMapper.update(permissionDOForUpdate, wrapperForUpdate);

        LambdaQueryWrapper<PermissionDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PermissionDO::getParentPermissionId, id);
        List<PermissionDO> permissionDOS = permissionMapper.selectList(wrapper);
        for (PermissionDO permissionDO : permissionDOS) {
            count += recursiveEnablePermission(permissionDO.getId());
        }
        return count;
    }

}
