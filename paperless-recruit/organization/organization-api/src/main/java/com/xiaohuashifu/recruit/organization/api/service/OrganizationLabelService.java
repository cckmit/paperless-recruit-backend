package com.xiaohuashifu.recruit.organization.api.service;

import com.xiaohuashifu.recruit.common.exception.NotFoundServiceException;
import com.xiaohuashifu.recruit.common.exception.ServiceException;
import com.xiaohuashifu.recruit.common.exception.unprocessable.DuplicateServiceException;
import com.xiaohuashifu.recruit.common.exception.unprocessable.UnavailableServiceException;
import com.xiaohuashifu.recruit.common.query.QueryResult;
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
     * @permission admin
     *
     * @param labelName 标签名
     * @return OrganizationLabelDTO
     */
    OrganizationLabelDTO createOrganizationLabel(
            @NotBlank @Size(max = OrganizationLabelConstants.MAX_LABEL_NAME_LENGTH) String labelName)
            throws DuplicateServiceException;

    /**
     * 获取组织标签
     *
     * @param id 组织标签编号
     * @return OrganizationLabelDTO
     */
    OrganizationLabelDTO getOrganizationLabel(@NotNull @Positive Long id) throws NotFoundServiceException;

    /**
     * 获取组织标签
     *
     * @param labelName 组织标签名
     * @return OrganizationLabelDTO
     */
    OrganizationLabelDTO getOrganizationLabelByLabelName(
            @NotNull @Size(max = OrganizationLabelConstants.MAX_LABEL_NAME_LENGTH) String labelName)
            throws NotFoundServiceException;

    /**
     * 查询组织标签
     *
     * @param query 查询参数
     * @return QueryResult<OrganizationLabelDTO> 若查询不到返回空列表
     */
    QueryResult<OrganizationLabelDTO> listOrganizationLabels(@NotNull OrganizationLabelQuery query);

    /**
     * 禁用一个组织标签，会把所有拥有这个标签的社团的这个标签给删了
     *
     * @permission admin
     *
     * @param id 组织标签编号
     * @return DisableOrganizationLabelDTO 禁用后的组织标签对象和被删除标签的社团数量
     */
    DisableOrganizationLabelDTO disableOrganizationLabel(@NotNull @Positive Long id) throws ServiceException;

    /**
     * 解禁标签
     *
     * @permission admin
     *
     * @param id 组织标签编号
     * @return 解禁后的组织标签对象
     */
    OrganizationLabelDTO enableOrganizationLabel(@NotNull @Positive Long id) throws ServiceException;

    /**
     * 增加标签引用数量，若标签不存在则保存标签，初始引用数1
     *
     * @private 内部方法
     *
     * @param labelName 标签名
     * @return 操作是否成功
     */
    OrganizationLabelDTO increaseReferenceNumberOrSaveOrganizationLabel(
            @NotBlank @Size(max = OrganizationLabelConstants.MAX_LABEL_NAME_LENGTH) String labelName)
            throws UnavailableServiceException;

}
