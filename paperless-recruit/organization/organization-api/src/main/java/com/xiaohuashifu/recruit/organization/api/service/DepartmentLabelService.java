package com.xiaohuashifu.recruit.organization.api.service;

import com.xiaohuashifu.recruit.common.exception.NotFoundServiceException;
import com.xiaohuashifu.recruit.common.exception.ServiceException;
import com.xiaohuashifu.recruit.common.exception.unprocessable.DuplicateServiceException;
import com.xiaohuashifu.recruit.common.exception.unprocessable.UnavailableServiceException;
import com.xiaohuashifu.recruit.common.query.QueryResult;
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
@Deprecated
public interface DepartmentLabelService {

    /**
     * 创建部门标签，初始引用数0
     *
     * @param labelName 标签名
     * @return DepartmentLabelDTO
     */
    DepartmentLabelDTO createDepartmentLabel(
            @NotBlank @Size(max = DepartmentLabelConstants.MAX_LABEL_NAME_LENGTH)String labelName)
            throws DuplicateServiceException;

    /**
     * 获取部门标签
     *
     * @param id 部门标签编号
     * @return DepartmentLabelDTO
     */
    DepartmentLabelDTO getDepartmentLabel(@NotNull @Positive Long id) throws NotFoundServiceException;

    /**
     * 获取部门标签
     *
     * @param labelName 部门标签名
     * @return DepartmentLabelDTO
     */
    DepartmentLabelDTO getDepartmentLabelByLabelName(
            @NotNull @Size(max = DepartmentLabelConstants.MAX_LABEL_NAME_LENGTH) String labelName)
            throws NotFoundServiceException;

    /**
     * 查询部门标签
     *
     * @param query 查询参数
     * @return QueryResult<DepartmentLabelDTO> 若查询不到返回空列表
     */
    QueryResult<DepartmentLabelDTO> listDepartmentLabels(@NotNull DepartmentLabelQuery query);

    /**
     * 禁用一个部门标签，会把所有拥有这个标签的部门的这个标签给删了
     *
     * @param id 部门标签编号
     * @return DisableDepartmentLabelDTO 禁用后的部门标签对象和被删除标签的部门数量；
     */
    DisableDepartmentLabelDTO disableDepartmentLabel(@NotNull @Positive Long id) throws ServiceException;

    /**
     * 解禁标签
     *
     * @param id 部门标签编号
     * @return 解禁后的部门标签对象
     */
    DepartmentLabelDTO enableDepartmentLabel(@NotNull @Positive Long id) throws ServiceException;

    /**
     * 增加标签引用数量，若标签不存在则保存标签，初始引用数1
     *
     * @param labelName 标签名
     * @return 操作是否成功
     */
    DepartmentLabelDTO increaseReferenceNumberOrSaveDepartmentLabel(
            @NotBlank @Size(max = DepartmentLabelConstants.MAX_LABEL_NAME_LENGTH) String labelName)
            throws UnavailableServiceException;

}
