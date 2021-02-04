package com.xiaohuashifu.recruit.organization.api.service;

import com.xiaohuashifu.recruit.common.exception.NotFoundServiceException;
import com.xiaohuashifu.recruit.common.exception.ServiceException;
import com.xiaohuashifu.recruit.common.query.QueryResult;
import com.xiaohuashifu.recruit.organization.api.constant.DepartmentConstants;
import com.xiaohuashifu.recruit.organization.api.constant.DepartmentLabelConstants;
import com.xiaohuashifu.recruit.organization.api.dto.DepartmentDTO;
import com.xiaohuashifu.recruit.organization.api.query.DepartmentQuery;
import com.xiaohuashifu.recruit.organization.api.request.CreateDepartmentRequest;

import javax.validation.constraints.*;

/**
 * 描述：部门服务
 *
 * @author xhsf
 * @create 2020/12/13 21:57
 */
public interface DepartmentService {

    /**
     * 创建部门
     *
     * @param request CreateDepartmentRequest
     * @return DepartmentDTO 部门对象
     */
    DepartmentDTO createDepartment(CreateDepartmentRequest request) throws ServiceException;

    /**
     * 添加部门的标签
     *
     * @param id 部门编号
     * @param label 标签名
     * @return 添加后的部门对象
     */
    DepartmentDTO addLabel(
            @NotNull @Positive Long id,
            @NotBlank @Size(max = DepartmentLabelConstants.MAX_LABEL_NAME_LENGTH) String label)
            throws ServiceException;

    /**
     * 删除部门的标签
     *
     * @param id 部门编号
     * @param label 标签名
     * @return 删除标签后的部门
     */
    DepartmentDTO removeLabel(
            @NotNull @Positive Long id,
            @NotBlank @Size(max = DepartmentLabelConstants.MAX_LABEL_NAME_LENGTH) String label);

    /**
     * 获取部门
     *
     * @param id 部门编号
     * @return DepartmentDTO
     */
    DepartmentDTO getDepartment(@NotNull @Positive Long id) throws NotFoundServiceException;

    /**
     * 查询部门
     *
     * @param query 查询参数
     * @return QueryResult<DepartmentDTO> 查询结果，可能返回空列表
     */
    QueryResult<DepartmentDTO> listDepartments(@NotNull DepartmentQuery query);

    /**
     * 更新部门名
     *
     * @param id 部门编号
     * @param departmentName 部门名
     * @return 更新后的部门
     */
    DepartmentDTO updateDepartmentName(
            @NotNull @Positive Long id,
            @NotBlank @Size(min = DepartmentConstants.MIN_DEPARTMENT_NAME_LENGTH,
                    max = DepartmentConstants.MAX_DEPARTMENT_NAME_LENGTH) String departmentName) throws ServiceException;

    /**
     * 更新部门名缩写
     *
     * @param id 部门编号
     * @param abbreviationDepartmentName 部门名缩写
     * @return 更新后的部门
     */
    DepartmentDTO updateAbbreviationDepartmentName(
            @NotNull @Positive Long id,
            @NotBlank @Size(min = DepartmentConstants.MIN_ABBREVIATION_DEPARTMENT_NAME_LENGTH,
                    max = DepartmentConstants.MAX_ABBREVIATION_DEPARTMENT_NAME_LENGTH)
                    String abbreviationDepartmentName) throws ServiceException;

    /**
     * 更新部门介绍
     *
     * @param id 部门编号
     * @param introduction 部门介绍
     * @return 更新后的部门
     */
    DepartmentDTO updateIntroduction(
            @NotNull @Positive Long id,
            @NotBlank @Size(max = DepartmentConstants.MAX_DEPARTMENT_INTRODUCTION_LENGTH) String introduction);

    /**
     * 更新部门 Logo
     *
     * @param id 部门编号
     * @param logoUrl logoUrl
     * @return 更新后的部门
     */
    DepartmentDTO updateLogo(
            @NotNull @Positive Long id,
            @NotBlank @Pattern(regexp = "(departments/logos/)(.+)(\\.jpg|\\.jpeg|\\.png|\\.gif)") String logoUrl);

    /**
     * 停用部门，只是标识为停用
     * 无法再添加成员到该部门，无法创建招新报名
     *
     * @param id 部门编号
     * @return 停用后的部门对象
     */
    DepartmentDTO deactivateDepartment(@NotNull @Positive Long id) throws ServiceException;

    /**
     * 删除部门的标签，通过标签名
     * 小心使用，一次性会删除所有的拥有该标签的部门的这个标签
     *
     * @private 内部方法
     *
     * @param label 标签名
     * @return 被删除标签的部门数量
     */
    int removeLabels(String label);

    /**
     * 增加成员数，+1
     *
     * @private 内部方法
     *
     * @param id 部门编号
     * @return 增加成员数后的部门对象
     */
    DepartmentDTO increaseNumberOfMembers(@NotNull @Positive Long id);

    /**
     * 减少成员数，-1
     *
     * @private 内部方法
     *
     * @param id 部门编号
     * @return 减少成员数后的部门对象
     */
    DepartmentDTO decreaseNumberOfMembers(@NotNull @Positive Long id);

}
