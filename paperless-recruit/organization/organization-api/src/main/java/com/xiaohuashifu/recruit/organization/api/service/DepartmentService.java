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
     * @errorCode InvalidParameter: 邮箱或验证码或密码格式错误
     *              OperationConflict: 邮箱已经存在
     *              OperationConflict.Lock: 无法获取关于该邮箱的锁
     *              InvalidParameter.AuthCode.Incorrect: 邮箱验证码错误
     *              UnknownError: 注册主体失败
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
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 组织不存在
     *              InvalidParameter.NotAvailable: 标签不可用
     *              Forbidden: 组织不可用
     *              OperationConflict: 该标签已经存在
     *              OperationConflict.OverLimit: 组织标签数量超过规定数量
     *              OperationConflict.Lock: 获取组织标签的锁失败
     *
     * @param departmentId 部门编号
     * @param labelName 标签名
     * @return 添加后的部门对象
     */
    Result<DepartmentDTO> addLabel(
            @NotNull(message = "The departmentId can't be null.")
            @Positive(message = "The departmentId must be greater than 0.") Long departmentId,
            @NotBlank(message = "The labelName can't be blank.")
            @Size(max = DepartmentLabelConstants.MAX_LABEL_NAME_LENGTH,
                    message = "The length of labelName must not be greater than "
                            + DepartmentLabelConstants.MAX_LABEL_NAME_LENGTH + ".") String labelName);

    /**
     * 删除部门的标签
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 组织不存在
     *              Forbidden: 组织不可用
     *              OperationConflict: 该标签不存在
     *
     * @param departmentId 部门编号
     * @param labelName 标签名
     * @return 删除标签后的部门
     */
    Result<DepartmentDTO> removeLabel(
            @NotNull(message = "The departmentId can't be null.")
            @Positive(message = "The departmentId must be greater than 0.") Long departmentId,
            @NotBlank(message = "The labelName can't be blank.")
            @Size(max = DepartmentLabelConstants.MAX_LABEL_NAME_LENGTH,
                    message = "The length of labelName must not be greater than "
                            + DepartmentLabelConstants.MAX_LABEL_NAME_LENGTH + ".") String labelName);

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
     * @errorCode InvalidParameter: 组织编号或组织名格式错误
     *              InvalidParameter.NotExist: 组织不存在
     *              Forbidden: 组织不可用
     *              OperationConflict: 新组织名已经存在
     *              OperationConflict.Lock: 获取组织名的锁失败
     *
     * @param id 部门编号
     * @param newDepartmentName 新部门名
     * @return 更新后的部门
     */
    Result<DepartmentDTO> updateDepartmentName(
            @NotNull(message = "The id can't be null.")
            @Positive(message = "The id must be greater than 0.") Long id,
            @NotBlank(message = "The newDepartmentName can't be blank.")
            @Size(min = DepartmentConstants.MIN_DEPARTMENT_NAME_LENGTH,
                    max = DepartmentConstants.MAX_DEPARTMENT_NAME_LENGTH,
                    message = "The length of newDepartmentName must be between "
                            + DepartmentConstants.MIN_DEPARTMENT_NAME_LENGTH + " and "
                            + DepartmentConstants.MAX_DEPARTMENT_NAME_LENGTH + ".") String newDepartmentName);

    /**
     * 更新部门名缩写
     *
     * @errorCode InvalidParameter: 组织编号或组织名缩写格式错误
     *              InvalidParameter.NotExist: 组织不存在
     *              Forbidden: 组织不可用
     *
     * @param id 部门编号
     * @param newAbbreviationDepartmentName 新部门名缩写
     * @return 更新后的部门
     */
    Result<DepartmentDTO> updateAbbreviationDepartmentName(
            @NotNull(message = "The id can't be null.")
            @Positive(message = "The id must be greater than 0.") Long id,
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
     * @errorCode InvalidParameter: 组织编号或组织介绍格式错误
     *              InvalidParameter.NotExist: 组织不存在
     *              Forbidden: 组织不可用
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
     * @errorCode InvalidParameter: 组织编号或组织 logo 格式错误
     *              InvalidParameter.NotExist: 组织不存在
     *              Forbidden: 组织不可用
     *              InternalError: 上传文件失败
     *              OperationConflict.Lock: 获取组织 logo 的锁失败
     *
     * @param updateDepartmentLogoPO 更新 logo 的参数对象
     * @return 更新后的部门
     */
    Result<DepartmentDTO> updateLogo(@NotNull(message = "The updateDepartmentLogoPO can't be null.")
                                             UpdateDepartmentLogoPO updateDepartmentLogoPO);

    /**
     * 增加成员数，+1
     *
     * @errorCode InvalidParameter: 组织编号格式错误
     *              InvalidParameter.NotExist: 组织不存在
     *              Forbidden: 组织不可用
     *
     * @param id 部门编号
     * @return 增加成员数后的部门对象
     */
    Result<DepartmentDTO> increaseMemberNumber(@NotNull(message = "The id can't be null.")
                                               @Positive(message = "The id must be greater than 0.") Long id);

    /**
     * 减少成员数，-1
     *
     * @param id 部门编号
     * @return 减少成员数后的部门对象
     */
    Result<DepartmentDTO> decreaseMemberNumber(@NotNull(message = "The id can't be null.")
                                               @Positive(message = "The id must be greater than 0.") Long id);

}
