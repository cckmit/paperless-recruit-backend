package com.xiaohuashifu.recruit.organization.api.service;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.organization.api.constant.DepartmentLabelConstants;
import com.xiaohuashifu.recruit.organization.api.dto.DepartmentLabelDTO;
import com.xiaohuashifu.recruit.organization.api.dto.DisableDepartmentLabelDTO;
import com.xiaohuashifu.recruit.organization.api.query.DepartmentLabelQuery;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

/**
 * 描述：部门标签服务
 *
 * @author xhsf
 * @create 2020/12/7 13:22
 */
public interface DepartmentLabelService {

    /**
     * 保存部门标签，初始引用数0
     *
     * @permission 需要管理员权限
     *
     * @errorCode InvalidParameter: 标签名格式错误
     *              OperationConflict: 标签名已经存在
     *
     * @param labelName 标签名
     * @return DepartmentLabelDTO
     */
    Result<DepartmentLabelDTO> saveDepartmentLabel(
            @NotBlank(message = "The labelName can't be blank.")
            @Size(max = DepartmentLabelConstants.MAX_LABEL_NAME_LENGTH,
                    message = "The length of labelName must not be greater than "
                            + DepartmentLabelConstants.MAX_LABEL_NAME_LENGTH + ".") String labelName);

    /**
     * 增加标签引用数量，若标签不存在则保存标签，初始引用数1
     *
     * @private 内部方法
     *
     * @errorCode InvalidParameter: 标签名格式错误
     *              Forbidden: 该标签已经被禁用，不可用增加引用
     *
     * @param labelName 标签名
     * @return 操作是否成功
     */
    Result<DepartmentLabelDTO> increaseReferenceNumberOrSaveDepartmentLabel(
            @NotBlank(message = "The labelName can't be blank.")
            @Size(max = DepartmentLabelConstants.MAX_LABEL_NAME_LENGTH,
                    message = "The length of labelName must not be greater than "
                            + DepartmentLabelConstants.MAX_LABEL_NAME_LENGTH + ".") String labelName);

    /**
     * 查询部门标签
     *
     * @errorCode InvalidParameter: 查询参数格式错误
     *
     * @param query 查询参数
     * @return PageInfo<DepartmentLabelDTO> 若查询不到返回空列表
     */
    Result<PageInfo<DepartmentLabelDTO>> listDepartmentLabels(
            @NotNull(message = "The query can't be null.") DepartmentLabelQuery query);

    /**
     * 禁用一个部门标签，会把所有拥有这个标签的部门的这个标签给删了
     *
     * @permission 需要管理员权限
     *
     * @errorCode InvalidParameter: 部门标签编号格式错误
     *              InvalidParameter.NotExist: 部门标签不存在
     *              OperationConflict: 部门标签已经被禁用
     *
     * @param id 部门标签编号
     * @return DisableDepartmentLabelDTO 禁用后的部门标签对象和被删除标签的部门数量；
     */
    Result<DisableDepartmentLabelDTO> disableDepartmentLabel(
            @NotNull(message = "The id can't be null.")
            @Positive(message = "The id must be greater than 0.") Long id);

    /**
     * 解禁标签
     *
     * @permission 需要管理员权限
     *
     * @errorCode InvalidParameter: 部门标签编号格式错误
     *              InvalidParameter.NotExist: 部门标签不存在
     *              OperationConflict: 部门标签已经可用
     *
     * @param id 部门标签编号
     * @return 解禁后的部门标签对象
     */
    Result<DepartmentLabelDTO> enableDepartmentLabel(@NotNull(message = "The id can't be null.")
                                                     @Positive(message = "The id must be greater than 0.") Long id);

    /**
     * 判断一个标签是否合法
     *
     * @errorCode InvalidParameter: 标签名格式错误
     *              InvalidParameter.NotAvailable: 该标签名已经被禁用
     *
     * @param labelName 标签名
     * @return 标签是否合法
     */
    Result<Void> isValidDepartmentLabel(
            @NotBlank(message = "The labelName can't be blank.")
            @Size(max = DepartmentLabelConstants.MAX_LABEL_NAME_LENGTH,
                    message = "The length of labelName must not be greater than "
                            + DepartmentLabelConstants.MAX_LABEL_NAME_LENGTH + ".") String labelName);

}
