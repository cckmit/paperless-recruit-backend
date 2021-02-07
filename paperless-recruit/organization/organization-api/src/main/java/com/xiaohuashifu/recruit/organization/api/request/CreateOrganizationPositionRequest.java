package com.xiaohuashifu.recruit.organization.api.request;

import com.xiaohuashifu.recruit.organization.api.constant.OrganizationPositionConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * 描述：创建组织职位请求
 *
 * @author xhsf
 * @create 2021/2/7 20:12
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrganizationPositionRequest implements Serializable {

    /**
     * 组织编号
     */
    @NotNull
    @Positive
    private Long organizationId;

    /**
     * 职位名
     */
    @NotBlank
    @Size(max = OrganizationPositionConstants.MAX_ORGANIZATION_POSITION_NAME_LENGTH)
    private String positionName;

    /**
     * 优先级
     */
    @NotNull
    @Min(value = OrganizationPositionConstants.MIN_ORGANIZATION_POSITION_PRIORITY)
    @Max(value = OrganizationPositionConstants.MAX_ORGANIZATION_POSITION_PRIORITY)
    private Integer priority;

}
