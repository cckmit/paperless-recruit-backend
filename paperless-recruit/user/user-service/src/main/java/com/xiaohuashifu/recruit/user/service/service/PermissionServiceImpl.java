package com.xiaohuashifu.recruit.user.service.service;

import com.github.dozermapper.core.Mapper;
import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.recruit.common.result.ErrorCodeEnum;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.user.api.dto.DisablePermissionDTO;
import com.xiaohuashifu.recruit.user.api.dto.EnablePermissionDTO;
import com.xiaohuashifu.recruit.user.api.dto.PermissionDTO;
import com.xiaohuashifu.recruit.user.api.po.SavePermissionPO;
import com.xiaohuashifu.recruit.user.api.query.PermissionQuery;
import com.xiaohuashifu.recruit.user.api.service.PermissionService;
import com.xiaohuashifu.recruit.user.service.dao.PermissionMapper;
import com.xiaohuashifu.recruit.user.service.do0.PermissionDO;
import org.apache.dubbo.config.annotation.Service;

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
    private final Mapper mapper;

    /**
     * 当权限没有父亲时的 parentPermissionId
     */
    private static final Long NO_PARENT_PERMISSION_ID = 0L;

    public PermissionServiceImpl(PermissionMapper permissionMapper, Mapper mapper) {
        this.permissionMapper = permissionMapper;
        this.mapper = mapper;
    }

    /**
     * 创建权限
     * 权限名必须不存在
     * 如果父权限被禁用了，则该权限也会被禁用
     *
     * @errorCode InvalidParameter: 请求参数格式错误 | 父权限不存在
     *              OperationConflict: 权限名已经存在
     *
     * @param savePermissionPO 保存 Permission 需要的参数
     * @return Result<PermissionDTO>
     */
    @Override
    public Result<PermissionDTO> savePermission(SavePermissionPO savePermissionPO) {
        // 如果父权限编号不为0，则父权限必须存在
        if (!Objects.equals(savePermissionPO.getParentPermissionId(), NO_PARENT_PERMISSION_ID)) {
            int count = permissionMapper.count(savePermissionPO.getParentPermissionId());
            if (count < 1) {
                return Result.fail(ErrorCodeEnum.INVALID_PARAMETER,
                        "The parent permission does not exist.");
            }
        }

        // 去掉权限名两边的空白符
        savePermissionPO.setPermissionName(savePermissionPO.getPermissionName().trim());

        // 判断权限名存不存在，权限名必须不存在
        int count = permissionMapper.countByPermissionName(savePermissionPO.getPermissionName());
        if (count > 0) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT, "The permission name already exist.");
        }

        // 如果父权限编号不为0，且被禁用了，则该权限也应该被禁用
        if (!Objects.equals(savePermissionPO.getParentPermissionId(), NO_PARENT_PERMISSION_ID)) {
            count = permissionMapper.countByIdAndAvailable(savePermissionPO.getParentPermissionId(), false);
            if (count > 0) {
                savePermissionPO.setAvailable(false);
            }
        }

        // 去掉权限描述和授权路径两边的空白符
        savePermissionPO.setDescription(savePermissionPO.getDescription().trim());
        savePermissionPO.setAuthorizationUrl(savePermissionPO.getAuthorizationUrl().trim());

        // 保存权限
        PermissionDO permissionDO = new PermissionDO.Builder()
                .parentPermissionId(savePermissionPO.getParentPermissionId())
                .permissionName(savePermissionPO.getPermissionName())
                .authorizationUrl(savePermissionPO.getAuthorizationUrl())
                .description(savePermissionPO.getDescription())
                .available(savePermissionPO.getAvailable())
                .build();
        permissionMapper.insertPermission(permissionDO);
        return getPermission(permissionDO.getId());
    }

    /**
     * 删除权限，只允许没有子权限的权限删除
     * 同时会删除与此权限关联的所有角色 Role 的关联关系
     *
     * @errorCode InvalidParameter: 请求参数格式错误 | 该权限不存在 | 存在子权限 |
     *
     * @param id 权限编号
     * @return Result<Void>
     */
    @Override
    public Result<Void> removePermission(Long id) {
        // 判断该权限存不存在
        int count = permissionMapper.count(id);
        if (count < 1) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER, "This permission not exists.");
        }

        // 判断该权限是否还拥有子权限，必须没有子权限才可以删除
        count = permissionMapper.countByParentPermissionId(id);
        if (count > 0) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER, "The permission exist children.");
        }

        // 删除该权限所关联的角色 Role 的关联关系
        permissionMapper.deleteRolePermissionByPermissionId(id);

        // 删除该权限
        permissionMapper.deletePermission(id);
        return Result.success();
    }

    /**
     * 获取权限
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *              InvalidParameter.NotFound: 找不到该编号对应的权限
     *
     * @param id 权限编号
     * @return Result<PermissionDTO>
     */
    @Override
    public Result<PermissionDTO> getPermission(Long id) {
        PermissionDO permissionDO = permissionMapper.getPermission(id);
        if (permissionDO == null) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_FOUND);
        }
        return Result.success(mapper.map(permissionDO, PermissionDTO.class));
    }

    /**
     * 获取权限
     *
     * @errorCode InvalidParameter: 请求参数格式错误
     *
     * @param query 查询参数
     * @return Result<PageInfo<PermissionDTO>> 带分页信息的权限列表，可能返回空列表
     */
    @Override
    public Result<PageInfo<PermissionDTO>> listPermissions(PermissionQuery query) {
        List<PermissionDO> permissionDOList = permissionMapper.listPermissions(query);
        List<PermissionDTO> permissionDTOList = permissionDOList
                .stream()
                .map(permissionDO -> mapper.map(permissionDO, PermissionDTO.class))
                .collect(Collectors.toList());
        PageInfo<PermissionDTO> pageInfo = new PageInfo<>(permissionDTOList);
        return Result.success(pageInfo);
    }

    /**
     * 更新权限名，新权限名必须不存在
     *
     * @errorCode InvalidParameter: 请求参数格式错误 | 该权限不存在
     *              OperationConflict: 新权限名已经存在
     *
     * @param id 权限编号
     * @param newPermissionName 新权限名
     * @return Result<PermissionDTO> 更新后的权限对象
     */
    @Override
    public Result<PermissionDTO> updatePermissionName(Long id, String newPermissionName) {
        // 判断该权限存不存在，该权限必须存在
        int count = permissionMapper.count(id);
        if (count < 1) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER, "The permission does not exist.");
        }

        // 去除权限名两边空白符
        newPermissionName = newPermissionName.trim();

        // 判断新权限名存不存在，新权限名必须不存在
        count = permissionMapper.countByPermissionName(newPermissionName);
        if (count > 0) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT, "The new permission name already exist.");
        }

        // 更新权限名
        permissionMapper.updatePermissionName(id, newPermissionName);
        return getPermission(id);
    }

    /**
     * 更新授权路径
     *
     * @errorCode InvalidParameter: 请求参数格式错误 | 该权限不存在
     *
     * @param id 权限编号
     * @param newAuthorizationUrl 新授权路径
     * @return Result<PermissionDTO> 更新后的权限对象
     */
    @Override
    public Result<PermissionDTO> updateAuthorizationUrl(Long id, String newAuthorizationUrl) {
        // 判断该权限存不存在，该权限必须存在
        int count = permissionMapper.count(id);
        if (count < 1) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER, "The permission does not exist.");
        }

        // 去除授权路径两边空白符
        // 更新授权路径
        permissionMapper.updateAuthorizationUrl(id, newAuthorizationUrl.trim());
        return getPermission(id);
    }

    /**
     * 更新权限描述
     *
     * @errorCode InvalidParameter: 请求参数格式错误 | 该权限不存在
     *
     * @param id 权限编号
     * @param newDescription 新权限描述
     * @return Result<PermissionDTO> 更新后的权限对象
     */
    @Override
    public Result<PermissionDTO> updateDescription(Long id, String newDescription) {
        // 判断该权限存不存在，该权限必须存在
        int count = permissionMapper.count(id);
        if (count < 1) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER, "The permission does not exist.");
        }

        // 去除权限描述两边空白符
        // 更新权限描述
        permissionMapper.updateDescription(id, newDescription.trim());
        return getPermission(id);
    }

    /**
     * 禁用权限（且子权限可用状态也被禁用，递归禁用）
     *
     * @errorCode InvalidParameter: 请求参数格式错误 | 该权限不存在
     *              OperationConflict: 该权限已经被禁用
     *
     * @param id 权限编号
     * @return DisablePermissionDTO 禁用的数量和禁用后的权限对象
     */
    @Override
    public Result<DisablePermissionDTO> disablePermission(Long id) {
        // 判断该权限存不存在
        int count = permissionMapper.count(id);
        if (count < 1) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER, "The permission does not exist.");
        }

        // 判断该权限是否已经被禁用
        count = permissionMapper.countByIdAndAvailable(id, false);
        if (count > 0) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT, "The permission already disable.");
        }

        // 递归的禁用权限
        int disabledCount = recursiveDisablePermission(id);
        DisablePermissionDTO disablePermissionDTO =
                new DisablePermissionDTO(getPermission(id).getData(), disabledCount);
        return Result.success(disablePermissionDTO);
    }

    /**
     * 解禁权限（且子权限可用状态也被解禁，递归解禁）
     *
     * @errorCode InvalidParameter: 请求参数格式错误 | 该权限不存在
     *              OperationConflict: 该权限已经可用 | 父权限被禁用，无法解禁该权限
     *
     * @param id 权限编号
     * @return Result<Map<String, Object>> 解禁的数量和解禁后的权限对象
     */
    @Override
    public Result<EnablePermissionDTO> enablePermission(Long id) {
        // 判断该权限存不存在
        PermissionDO permissionDO = permissionMapper.getPermission(id);
        if (permissionDO == null) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER, "The permission does not exist.");
        }

        // 不能解禁已经有效的权限
        if (permissionDO.getAvailable()) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT, "The permission is available.");
        }

        // 如果该权限的父权限编号不为0
        // 判断该权限的父权限是否已经被禁用，如果父权限已经被禁用，则无法解禁该权限
        if (!Objects.equals(permissionDO.getParentPermissionId(), NO_PARENT_PERMISSION_ID)) {
            int count = permissionMapper.countByIdAndAvailable(permissionDO.getParentPermissionId(), false);
            if (count > 0) {
                return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT,
                        "Can't enable this permission, because the parent permission is disable.");
            }
        }

        // 递归的解禁权限
        int enabledCount = recursiveEnablePermission(id);
        EnablePermissionDTO enablePermissionDTO =
                new EnablePermissionDTO(getPermission(id).getData(), enabledCount);
        return Result.success(enablePermissionDTO);
    }

    /**
     * 设置父权限
     * 设置 parentPermissionId 为0表示取消父权限设置
     * 如果父权限状态为禁用，而该权限的状态为可用，则递归更新该权限状态为禁用
     *
     * @errorCode InvalidParameter: 请求参数格式错误 | 权限不存在 | 父权限不存在
     *              OperationConflict: 新旧父权限相同
     *
     * @param id 权限编号
     * @param parentPermissionId 父权限编号
     * @return Result<DisablePermissionDTO>，禁用的数量和设置父权限后的权限对象
     *          这里的禁用是因为如果父权限为禁用，则该权限必须也递归的禁用
     */
    @Override
    public Result<DisablePermissionDTO> setParentPermission(Long id, Long parentPermissionId) {
        // 判断该权限存不存在
        PermissionDO permissionDO = permissionMapper.getPermission(id);
        if (permissionDO == null) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER, "The permission does not exist.");
        }

        // 如果原来的父权限编号和要设置的父权限编号相同，则直接返回
        if (Objects.equals(permissionDO.getParentPermissionId(), parentPermissionId)) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT,
                    "The new permission same as the old permission.");
        }

        // 若父权限编号不为0，则判断要设置的父权限是否存在
        if (!Objects.equals(parentPermissionId, NO_PARENT_PERMISSION_ID)) {
            int count = permissionMapper.count(parentPermissionId);
            if (count < 1) {
                return Result.fail(ErrorCodeEnum.INVALID_PARAMETER, "The parent permission does not exist.");
            }
        }

        // 设置父权限
        permissionMapper.updateParentPermissionId(id, parentPermissionId);

        // 如果要设置的父权限编号为0（取消父权限）
        // 或者要设置的父权限的状态为可用
        // 或者要设置的父权限的状态为禁用且当前权限的状态也为禁用，则直接返回
        boolean canReturn = Objects.equals(parentPermissionId, NO_PARENT_PERMISSION_ID)
                || permissionMapper.countByIdAndAvailable(parentPermissionId, true) == 1
                || !permissionDO.getAvailable();
        if (canReturn) {
            DisablePermissionDTO disablePermissionDTO =
                    new DisablePermissionDTO(getPermission(id).getData(), 0);
            return Result.success(disablePermissionDTO);
        }

        // 如果父权限状态为禁用，而该权限的状态为可用，则递归更新该权限状态为禁用
        return disablePermission(id);
    }

    /**
     * 递归的禁用权限
     *
     * @param id 权限编号
     * @return 总共禁用的权限数量
     */
    private int recursiveDisablePermission(Long id) {
        int count = permissionMapper.updateAvailable(id, false);
        List<Long> permissionIdList = permissionMapper.listIdsByParentPermissionIdAndAvailable(id, true);
        for (Long permissionId : permissionIdList) {
            count += recursiveDisablePermission(permissionId);
        }
        return count;
    }

    /**
     * 递归的解禁权限
     *
     * @param id 权限编号
     * @return 总共解禁的权限数量
     */
    private int recursiveEnablePermission(Long id) {
        int count = permissionMapper.updateAvailableIfUnavailable(id);
        List<Long> permissionIdList = permissionMapper.listIdsByParentPermissionId(id);
        for (Long permissionId : permissionIdList) {
            count += recursiveEnablePermission(permissionId);
        }
        return count;
    }

}
