package com.xiaohuashifu.recruit.organization.service.service;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.organization.api.dto.OrganizationDTO;
import com.xiaohuashifu.recruit.organization.api.po.CreateOrganizationPO;
import com.xiaohuashifu.recruit.organization.api.query.OrganizationQuery;
import com.xiaohuashifu.recruit.organization.api.service.OrganizationLabelService;
import com.xiaohuashifu.recruit.organization.api.service.OrganizationService;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;

import javax.validation.constraints.*;
import java.util.List;

/**
 * 描述：组织服务
 *
 * @author xhsf
 * @create 2020/12/8 21:56
 */
@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Reference
    private OrganizationLabelService organizationLabelService;

    @Override
    public Result<OrganizationDTO> createOrganization(CreateOrganizationPO createOrganizationPO) {
        List<String> labels = createOrganizationPO.getLabels();
        if (labels != null) {
            for (String label : labels) {
                organizationLabelService.isValidOrganizationLabel(label);
            }
        }

        return null;
    }

    @Override
    public Result<Void> addLabel(@NotNull @Positive Long id, @NotBlank @Size(min = 1, max = 4) String labelName) {
        return null;
    }

    @Override
    public Result<Void> removeLabel(@NotNull @Positive Long id, @NotBlank @Size(min = 1, max = 4) String labelName) {
        return null;
    }

    @Override
    public Result<OrganizationDTO> getOrganization(@NotNull @Positive Long id) {
        return null;
    }

    @Override
    public Result<PageInfo<OrganizationDTO>> listOrganizations(@NotNull OrganizationQuery query) {
        return null;
    }

    @Override
    public Result<OrganizationDTO> updateOrganizationName(@NotNull @Positive Long id, @NotBlank @Size(min = 2, max = 20) String newOrganizationName) {
        return null;
    }

    @Override
    public Result<OrganizationDTO> updateAbbreviationOrganizationName(@NotNull @Positive Long id, @NotBlank @Size(min = 2, max = 5) String newAbbreviationOrganizationName) {
        return null;
    }

    @Override
    public Result<OrganizationDTO> updateIntroduction(@NotNull @Positive Long id, @NotBlank @Size(min = 1, max = 400) String newIntroduction) {
        return null;
    }

    @Override
    public Result<OrganizationDTO> updateLogo(@NotNull @Positive Long id, @NotEmpty @Size(min = 1, max = 10240) byte[] newLogo) {
        return null;
    }

    @Override
    public Result<Void> increaseMemberNumber(@NotNull @Positive Long id) {
        return null;
    }

    @Override
    public Result<Void> decreaseMemberNumber(@NotNull @Positive Long id) {
        return null;
    }

    @Override
    public Result<OrganizationDTO> disableOrganization(@NotNull @Positive Long id) {
        return null;
    }

    @Override
    public Result<OrganizationDTO> enableOrganization(@NotNull @Positive Long id) {
        return null;
    }
}
