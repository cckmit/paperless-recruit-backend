package com.xiaohuashifu.recruit.organization.api.service;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.organization.api.constant.OrganizationLabelConstants;
import com.xiaohuashifu.recruit.organization.api.dto.DisableOrganizationLabelDTO;
import com.xiaohuashifu.recruit.organization.api.dto.OrganizationLabelDTO;
import com.xiaohuashifu.recruit.organization.api.query.OrganizationLabelQuery;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

/**
 * 描述：组织标签服务
 *
 * @author xhsf
 * @create 2020/12/7 13:22
 */
public interface OrganizationLabelService {

    /**
     * 创建组织标签，初始引用数0
     *
     * @permission 需要管理员权限
     *
     * @errorCode InvalidParameter: 标签名格式错误
     *              OperationConflict: 标签名已经存在
     *
     * @param labelName 标签名
     * @return OrganizationLabelDTO
     */
    Result<OrganizationLabelDTO> createOrganizationLabel(
            @NotBlank @Size(max = OrganizationLabelConstants.MAX_LABEL_NAME_LENGTH) String labelName);

    /**
     * 获取组织标签
     *
     * @errorCode InvalidParameter: 标签名格式错误
     *              NotFound: 组织标签不存在
     *
     * @param id 组织标签编号
     * @return OrganizationLabelDTO
     */
    Result<OrganizationLabelDTO> getOrganizationLabel(@NotNull @Positive Long id);

    /**
     * 查询组织标签
     *
     * @errorCode InvalidParameter: 查询参数格式错误
     *
     * @param query 查询参数
     * @return PageInfo<OrganizationLabelDTO> 若查询不到返回空列表
     */
    Result<PageInfo<OrganizationLabelDTO>> listOrganizationLabels(@NotNull OrganizationLabelQuery query);

    /**
     * 禁用一个组织标签，会把所有拥有这个标签的社团的这个标签给删了
     *
     * @permission 需要管理员权限
     *
     * @errorCode InvalidParameter: 组织标签编号格式错误
     *              InvalidParameter.NotExist: 组织标签不存在
     *              OperationConflict: 组织标签已经被禁用
     *
     * @param id 组织标签编号
     * @return DisableOrganizationLabelDTO 禁用后的组织标签对象和被删除标签的社团数量
     */
    Result<DisableOrganizationLabelDTO> disableOrganizationLabel(@NotNull @Positive Long id);

    /**
     * 解禁标签
     *
     * @permission 需要管理员权限
     *
     * @errorCode InvalidParameter: 组织标签编号格式错误
     *              InvalidParameter.NotExist: 组织标签不存在
     *              OperationConflict: 组织标签已经可用
     *
     * @param id 组织标签编号
     * @return 解禁后的组织标签对象
     */
    Result<OrganizationLabelDTO> enableOrganizationLabel(@NotNull @Positive Long id);

    /**
     * 判断一个标签是否合法
     *
     * @private 内部方法
     *
     * @errorCode InvalidParameter: 标签名格式错误
     *              InvalidParameter.NotAvailable: 该标签名已经被禁用
     *
     * @param labelName 标签名
     * @return 标签是否合法
     */
    Result<Void> isValidOrganizationLabel(
            @NotBlank @Size(max = OrganizationLabelConstants.MAX_LABEL_NAME_LENGTH) String labelName);

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
    Result<OrganizationLabelDTO> increaseReferenceNumberOrSaveOrganizationLabel(
            @NotBlank @Size(max = OrganizationLabelConstants.MAX_LABEL_NAME_LENGTH) String labelName);
}
