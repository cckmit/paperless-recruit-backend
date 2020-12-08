package com.xiaohuashifu.recruit.organization.api.service;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.organization.api.dto.OrganizationLabelDTO;
import com.xiaohuashifu.recruit.organization.api.query.OrganizationLabelQuery;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.Map;

/**
 * 描述：组织标签服务
 *
 * @author xhsf
 * @create 2020/12/7 13:22
 */
public interface OrganizationLabelService {

    /**
     * 保存组织标签，初始引用数0
     *
     * @errorCode InvalidParameter: 标签名格式错误
     *              OperationConflict: 标签名已经存在
     *
     * @param labelName 标签名
     * @return OrganizationLabelDTO
     */
    Result<OrganizationLabelDTO> saveOrganizationLabel(@NotBlank @Size(min = 1, max = 4) String labelName);

    /**
     * 增加标签引用数量，若标签不存在则保存标签，初始引用数1
     *
     * @errorCode InvalidParameter: 标签名格式错误
     *              InvalidParameter.NotAvailable: 该标签已经被禁用，不可用增加引用
     *
     * @param labelName 标签名
     * @return 增加引用数量后的组织标签对象
     */
    Result<OrganizationLabelDTO> increaseReferenceNumberOrSaveOrganizationLabel(@NotBlank @Size(min = 1, max = 4) String labelName);

    /**
     * 查询组织标签
     *
     * @param query 查询参数
     * @return PageInfo<OrganizationLabelDTO> 若查询不到返回空列表
     */
    Result<PageInfo<OrganizationLabelDTO>> listOrganizationLabels(@NotNull OrganizationLabelQuery query);

    /**
     * 禁用一个组织标签，会把所有拥有这个标签的社团的这个标签给删了
     *
     * @param id 社团标签编号
     * @return 禁用后的组织标签对象和被删除标签的社团数量；
     *          Map 的 key 分别为 organizationLabel 和 deletedNumber，类型分别为 OrganizationLabelDTO 和 Integer
     */
    Result<Map<String, Object>> disableOrganizationLabel(@NotNull @Positive Long id);

    /**
     * 解禁标签
     *
     * @param id 社团标签编号
     * @return 解禁后的组织标签对象
     */
    Result<OrganizationLabelDTO> enableOrganizationLabel(@NotNull @Positive Long id);
}
