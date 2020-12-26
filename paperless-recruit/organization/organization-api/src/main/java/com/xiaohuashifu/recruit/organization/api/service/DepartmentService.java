package com.xiaohuashifu.recruit.organization.api.service;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.organization.api.constant.DepartmentConstants;
import com.xiaohuashifu.recruit.organization.api.constant.DepartmentLabelConstants;
import com.xiaohuashifu.recruit.organization.api.dto.DepartmentDTO;
import com.xiaohuashifu.recruit.organization.api.po.UpdateDepartmentLogoPO;
import com.xiaohuashifu.recruit.organization.api.query.DepartmentQuery;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

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
     * @permission 必须是组织所属用户主体本身
     *
     * @errorCode InvalidParameter: 组织编号或部门猛或部门名缩写格式错误
     *              InvalidParameter.NotExist: 组织不存在
     *              Forbidden.Unavailable: 组织不可用
     *              OperationConflict: 该组织已经存在该部门名或部门名缩写
     *
     * @param organizationId 部门所属组织的编号
     * @param departmentName 部门名
     * @param abbreviationDepartmentName 部门名缩写
     * @return DepartmentDTO 部门对象
     */
    Result<DepartmentDTO> createDepartment(
            @NotNull(message = "The organizationId can't be null.")
            @Positive(message = "The organizationId must be greater than 0.") Long organizationId,
            @NotBlank(message = "The departmentName can't be blank.")
            @Size(min = DepartmentConstants.MIN_DEPARTMENT_NAME_LENGTH,
                    max = DepartmentConstants.MAX_DEPARTMENT_NAME_LENGTH,
                    message = "The length of departmentName must be between "
                            + DepartmentConstants.MIN_DEPARTMENT_NAME_LENGTH + " and "
                            + DepartmentConstants.MAX_DEPARTMENT_NAME_LENGTH + ".") String departmentName,
            @NotBlank(message = "The abbreviationDepartmentName can't be blank.")
            @Size(min = DepartmentConstants.MIN_ABBREVIATION_DEPARTMENT_NAME_LENGTH,
                    max = DepartmentConstants.MAX_ABBREVIATION_DEPARTMENT_NAME_LENGTH,
                    message = "The length of abbreviationDepartmentName must be between "
                            + DepartmentConstants.MIN_ABBREVIATION_DEPARTMENT_NAME_LENGTH + " and "
                            + DepartmentConstants.MAX_ABBREVIATION_DEPARTMENT_NAME_LENGTH + ".")
                    String abbreviationDepartmentName);

    /**
     * 添加部门的标签
     *
     * @permission 该部门所属组织的所属用户必须是用户主体本身
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotAvailable: 标签不可用
     *              InvalidParameter.NotExist: 部门不存在
     *              Forbidden.Unavailable: 组织不可用
     *              OperationConflict: 该标签已经存在
     *              OperationConflict.OverLimit: 部门标签数量超过规定数量
     *              OperationConflict.Lock: 获取部门标签的锁失败
     *
     * @param id 部门编号
     * @param label 标签名
     * @return 添加后的部门对象
     */
    Result<DepartmentDTO> addLabel(
            @NotNull(message = "The id can't be null.") @Positive(message = "The id must be greater than 0.") Long id,
            @NotBlank(message = "The label can't be blank.")
            @Size(max = DepartmentLabelConstants.MAX_LABEL_NAME_LENGTH,
                    message = "The length of label must not be greater than "
                            + DepartmentLabelConstants.MAX_LABEL_NAME_LENGTH + ".") String label);

    /**
     * 删除部门的标签
     *
     * @permission 该部门所属组织的所属用户必须是用户主体本身
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 部门不存在
     *              Forbidden.Unavailable: 组织不可用
     *              OperationConflict: 该标签不存在
     *
     * @param id 部门编号
     * @param label 标签名
     * @return 删除标签后的部门
     */
    Result<DepartmentDTO> removeLabel(
            @NotNull(message = "The id can't be null.") @Positive(message = "The id must be greater than 0.") Long id,
            @NotBlank(message = "The label can't be blank.")
            @Size(max = DepartmentLabelConstants.MAX_LABEL_NAME_LENGTH,
                    message = "The length of label must not be greater than "
                            + DepartmentLabelConstants.MAX_LABEL_NAME_LENGTH + ".") String label);

    /**
     * 获取部门
     *
     * @errorCode InvalidParameter: 部门编号格式错误
     *              InvalidParameter.NotFound: 该编号的部门不存在
     *
     * @param id 部门编号
     * @return DepartmentDTO
     */
    Result<DepartmentDTO> getDepartment(@NotNull(message = "The id can't be null.")
                                        @Positive(message = "The id must be greater than 0.") Long id);

    /**
     * 查询部门
     *
     * @errorCode InvalidParameter: 查询参数格式错误
     *
     * @param query 查询参数
     * @return PageInfo<DepartmentDTO> 查询结果，可能返回空列表
     */
    Result<PageInfo<DepartmentDTO>> listDepartments(
            @NotNull(message = "The query can't be null.") DepartmentQuery query);

    /**
     * 更新部门名
     *
     * @permission 该部门所属组织的所属用户必须是用户主体本身
     *
     * @errorCode InvalidParameter: 部门编号或部门名格式错误
     *              InvalidParameter.NotExist: 部门不存在
     *              Forbidden.Unavailable: 组织不可用
     *              OperationConflict: 该组织已经存在相同的部门名
     *              OperationConflict.Lock: 获取组织的部门名锁失败
     *
     * @param id 部门编号
     * @param newDepartmentName 新部门名
     * @return 更新后的部门
     */
    Result<DepartmentDTO> updateDepartmentName(
            @NotNull(message = "The id can't be null.") @Positive(message = "The id must be greater than 0.") Long id,
            @NotBlank(message = "The newDepartmentName can't be blank.")
            @Size(min = DepartmentConstants.MIN_DEPARTMENT_NAME_LENGTH,
                    max = DepartmentConstants.MAX_DEPARTMENT_NAME_LENGTH,
                    message = "The length of newDepartmentName must be between "
                            + DepartmentConstants.MIN_DEPARTMENT_NAME_LENGTH + " and "
                            + DepartmentConstants.MAX_DEPARTMENT_NAME_LENGTH + ".") String newDepartmentName);

    /**
     * 更新部门名缩写
     *
     * @permission 该部门所属组织的所属用户必须是用户主体本身
     *
     * @errorCode InvalidParameter: 部门编号或部门名缩写格式错误
     *              InvalidParameter.NotExist: 部门不存在
     *              Forbidden.Unavailable: 组织不可用
     *              OperationConflict: 该组织已经存在相同的部门名缩写
     *              OperationConflict.Lock: 获取组织的部门名缩写锁失败
     *
     * @param id 部门编号
     * @param newAbbreviationDepartmentName 新部门名缩写
     * @return 更新后的部门
     */
    Result<DepartmentDTO> updateAbbreviationDepartmentName(
            @NotNull(message = "The id can't be null.") @Positive(message = "The id must be greater than 0.") Long id,
            @NotBlank(message = "The newAbbreviationDepartmentName can't be blank.")
            @Size(min = DepartmentConstants.MIN_ABBREVIATION_DEPARTMENT_NAME_LENGTH,
                    max = DepartmentConstants.MAX_ABBREVIATION_DEPARTMENT_NAME_LENGTH,
                    message = "The length of newAbbreviationDepartmentName must be between "
                            + DepartmentConstants.MIN_ABBREVIATION_DEPARTMENT_NAME_LENGTH + " and "
                            + DepartmentConstants.MAX_ABBREVIATION_DEPARTMENT_NAME_LENGTH + ".")
                    String newAbbreviationDepartmentName);

    /**
     * 更新部门介绍
     *
     * @permission 该部门所属组织的所属用户必须是用户主体本身
     *
     * @errorCode InvalidParameter: 部门编号或部门介绍格式错误
     *              InvalidParameter.NotExist: 部门不存在
     *              Forbidden.Unavailable: 组织不可用
     *
     * @param id 部门编号
     * @param newIntroduction 新部门介绍
     * @return 更新后的部门
     */
    Result<DepartmentDTO> updateIntroduction(
            @NotNull(message = "The id can't be null.")
            @Positive(message = "The id must be greater than 0.") Long id,
            @NotBlank(message = "The newIntroduction can't be blank.")
            @Size(max = DepartmentConstants.MAX_DEPARTMENT_INTRODUCTION_LENGTH,
                    message = "The length of newIntroduction must not be greater than "
                            + DepartmentConstants.MAX_DEPARTMENT_INTRODUCTION_LENGTH + ".") String newIntroduction);

    /**
     * 更新部门 Logo
     *
     * @permission 该部门所属组织的所属用户必须是用户主体本身
     *
     * @errorCode InvalidParameter: 更新参数格式错误
     *              InvalidParameter.NotExist: 部门不存在
     *              Forbidden.Unavailable: 组织不可用
     *              InternalError: 上传文件失败
     *              OperationConflict.Lock: 获取部门 logo 的锁失败
     *
     * @param updateDepartmentLogoPO 更新 logo 的参数对象
     * @return 更新后的部门
     */
    Result<DepartmentDTO> updateLogo(@NotNull(message = "The updateDepartmentLogoPO can't be null.")
                                             UpdateDepartmentLogoPO updateDepartmentLogoPO);

    /**
     * 停用部门，只是标识为停用
     * 无法再添加成员到该部门，无法创建招新报名
     *
     * @permission 该部门所属组织的所属用户必须是用户主体本身
     *
     * @errorCode InvalidParameter: 部门编号错误
     *              InvalidParameter.NotExist: 部门不存在
     *              Forbidden.Unavailable: 组织不可用
     *              OperationConflict: 部门已经被停用
     *
     * @param id 部门编号
     * @return 停用后的部门对象
     */
    Result<DepartmentDTO> deactivateDepartment(@NotNull(message = "The id can't be null.")
                                               @Positive(message = "The id must be greater than 0.") Long id);

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
     * 获取该部门所属组织的编号
     *
     * @private 内部方法
     *
     * @param id 部门编号
     * @return 组织编号，若该部门不存在则返回 null
     */
    Long getOrganizationId(Long id);

    /**
     * 增加成员数，+1
     *
     * @private 内部方法
     *
     * @errorCode InvalidParameter: 部门编号格式错误
     *
     * @param id 部门编号
     * @return 增加成员数后的部门对象
     */
    Result<DepartmentDTO> increaseMemberNumber(@NotNull(message = "The id can't be null.")
                                               @Positive(message = "The id must be greater than 0.") Long id);

    /**
     * 减少成员数，-1
     *
     * @private 内部方法
     *
     * @errorCode InvalidParameter: 部门编号格式错误
     *
     * @param id 部门编号
     * @return 减少成员数后的部门对象
     */
    Result<DepartmentDTO> decreaseMemberNumber(@NotNull(message = "The id can't be null.")
                                               @Positive(message = "The id must be greater than 0.") Long id);

}
