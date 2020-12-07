package com.xiaohuashifu.recruit.organization.api.service;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.organization.api.dto.OrganizationLabelDTO;
import com.xiaohuashifu.recruit.organization.api.query.OrganizationLabelQuery;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * 描述：组织标签服务
 *
 * @author xhsf
 * @create 2020/12/7 13:22
 */
public interface OrganizationLabelService {
    /**
     * 保存组织标签
     *
     * @param labelName 标签名
     * @return OrganizationLabelDTO
     */
    default Result<OrganizationLabelDTO> saveOrganizationLabel(@NotBlank @Size(min = 1, max = 4) String labelName) {
        throw new UnsupportedOperationException();
    }

    /**
     * 增加标签引用数量，若标签不存在则保存标签
     *
     * @param labelName 标签名
     * @return 操作是否成功
     */
    default Result<Void> increaseReferenceNumberOrSaveOrganizationLabel(
            @NotBlank @Size(min = 1, max = 4) String labelName) {
        throw new UnsupportedOperationException();
    }

    /**
     * 查询组织标签
     *
     * @param query 查询参数
     * @return PageInfo<OrganizationLabelDTO> 若查询不到返回空列表
     */
    default Result<PageInfo<OrganizationLabelDTO>> listOrganizationLabels(@NotNull OrganizationLabelQuery query) {
        throw new UnsupportedOperationException();
    }

}
