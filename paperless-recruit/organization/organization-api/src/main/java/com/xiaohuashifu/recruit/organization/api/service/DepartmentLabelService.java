package com.xiaohuashifu.recruit.organization.api.service;

import com.xiaohuashifu.recruit.common.query.QueryResult;
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
     * 创建部门标签，初始引用数0
     *
     * @permission admin
     *
     * @errorCode UnprocessableEntity.Exist 标签名已经存在
     *
     * @param labelName 标签名
     * @return DepartmentLabelDTO
     */
    Result<DepartmentLabelDTO> createDepartmentLabel(
            @NotBlank @Size(max = DepartmentLabelConstants.MAX_LABEL_NAME_LENGTH) String labelName);

    /**
     * 获取部门标签
     *
     * @errorCode NotFound: 部门标签不存在
     *
     * @param id 部门标签编号
     * @return DepartmentLabelDTO
     */
    Result<DepartmentLabelDTO> getDepartmentLabel(@NotNull @Positive Long id);

    /**
     * 获取部门标签
     *
     * @errorCode NotFound: 部门标签不存在
     *
     * @param labelName 部门标签名
     * @return DepartmentLabelDTO
     */
    Result<DepartmentLabelDTO> getDepartmentLabelByLabelName(
            @NotNull @Size(max = DepartmentLabelConstants.MAX_LABEL_NAME_LENGTH) String labelName);

    /**
     * 查询部门标签
     *
     * @param query 查询参数
     * @return QueryResult<DepartmentLabelDTO> 若查询不到返回空列表
     */
    Result<QueryResult<DepartmentLabelDTO>> listDepartmentLabels(@NotNull DepartmentLabelQuery query);

    /**
     * 禁用一个部门标签，会把所有拥有这个标签的部门的这个标签给删了
     *
     * @permission admin
     *
     * @errorCode UnprocessableEntity.NotExist 部门标签不存在
     *              OperationConflict 部门标签已经被禁用
     *
     * @param id 部门标签编号
     * @return DisableDepartmentLabelDTO 禁用后的部门标签对象和被删除标签的部门数量；
     */
    Result<DisableDepartmentLabelDTO> disableDepartmentLabel(@NotNull @Positive Long id);

    /**
     * 解禁标签
     *
     * @permission admin
     *
     * @errorCode UnprocessableEntity.NotExist 部门标签不存在
     *              OperationConflict 部门标签已经可用
     *
     * @param id 部门标签编号
     * @return 解禁后的部门标签对象
     */
    Result<DepartmentLabelDTO> enableDepartmentLabel(@NotNull @Positive Long id);

    /**
     * 增加标签引用数量，若标签不存在则保存标签，初始引用数1
     *
     * @private 内部方法
     *
     * @errorCode UnprocessableEntity.Unavailable 无法处理，不可用
     *
     * @param labelName 标签名
     * @return 操作是否成功
     */
    Result<DepartmentLabelDTO> increaseReferenceNumberOrSaveDepartmentLabel(
            @NotBlank @Size(max = DepartmentLabelConstants.MAX_LABEL_NAME_LENGTH) String labelName);

}
