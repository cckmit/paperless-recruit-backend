package com.xiaohuashifu.recruit.user.api.service;

import com.xiaohuashifu.recruit.common.exception.NotFoundServiceException;
import com.xiaohuashifu.recruit.common.exception.ServiceException;
import com.xiaohuashifu.recruit.common.query.QueryResult;
import com.xiaohuashifu.recruit.user.api.dto.DisablePermissionDTO;
import com.xiaohuashifu.recruit.user.api.dto.EnablePermissionDTO;
import com.xiaohuashifu.recruit.user.api.dto.PermissionDTO;
import com.xiaohuashifu.recruit.user.api.query.PermissionQuery;
import com.xiaohuashifu.recruit.user.api.request.CreatePermissionRequest;
import com.xiaohuashifu.recruit.user.api.request.UpdatePermissionRequest;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

/**
 * 描述：权限服务 RPC 接口
 *
 * @author: xhsf
 * @create: 2020/11/12 19:42
 */
public interface PermissionService {

    /**
     * 创建权限
     * 权限名必须不存在
     * 如果父权限被禁用了，则该权限也会被禁用
     *
     * @param request CreatePermissionRequest
     * @return PermissionDTO
     */
    PermissionDTO createPermission(@NotNull CreatePermissionRequest request) throws ServiceException;

    /**
     * 删除权限，只允许没有子权限的权限删除
     * 同时会删除与此权限关联的所有角色 Role 的关联关系
     *
     * @param id 权限编号
     */
    void removePermission(@NotNull @Positive Long id) throws ServiceException;

    /**
     * 获取权限
     *
     * @param id 权限编号
     * @return PermissionDTO
     */
    PermissionDTO getPermission(@NotNull @Positive Long id) throws NotFoundServiceException;

    /**
     * 获取权限
     *
     * @param query 查询参数
     * @return QueryResult<PermissionDTO> 带分页信息的权限列表，可能返回空列表
     */
    QueryResult<PermissionDTO> listPermissions(@NotNull PermissionQuery query);

    /**
     * 更新权限请求
     *
     * @param request UpdatePermissionRequest
     * @return PermissionDTO
     */
    PermissionDTO updatePermission(@NotNull UpdatePermissionRequest request) throws ServiceException;

    /**
     * 禁用权限（且子权限可用状态也被禁用，递归禁用）
     *
     * @param id 权限编号
     * @return DisablePermissionDTO 禁用的数量和禁用后的权限对象
     */
    DisablePermissionDTO disablePermission(@NotNull @Positive Long id) throws ServiceException;

    /**
     * 解禁权限（且子权限可用状态也被解禁，递归解禁）
     *
     * @param id 权限编号
     * @return EnablePermissionDTO 解禁的数量和解禁后的权限对象
     */
    EnablePermissionDTO enablePermission(@NotNull @Positive Long id) throws ServiceException;

    /**
     * 设置父权限
     * 设置 parentPermissionId 为0表示取消父权限设置
     * 如果父权限状态为禁用，而该权限的状态为可用，则递归更新该权限状态为禁用
     *
     * @param id 权限编号
     * @param parentPermissionId 父权限编号
     * @return DisablePermissionDTO 禁用的数量和设置父权限后的权限对象
     *          这里的禁用是因为如果父权限为禁用，则该权限必须也递归的禁用
     */
    DisablePermissionDTO setParentPermission(
            @NotNull @Positive Long id,
            @NotNull @PositiveOrZero Long parentPermissionId) throws ServiceException;
}
