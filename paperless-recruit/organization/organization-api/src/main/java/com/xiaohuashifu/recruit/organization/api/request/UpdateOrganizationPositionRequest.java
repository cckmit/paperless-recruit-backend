package com.xiaohuashifu.recruit.organization.api.request;

import com.xiaohuashifu.recruit.common.validator.annotation.NotAllCharactersBlank;
import com.xiaohuashifu.recruit.organization.api.constant.OrganizationPositionConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * 描述：更新组织职位请求
 *
 * @author xhsf
 * @create 2021/2/7 20:18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrganizationPositionRequest implements Serializable {

    /**
     * 组织职位编号
     */
    @NotNull
    @Positive
    private Long id;

    /**
     * 职位名
     */
    @NotAllCharactersBlank
    @Size(max = OrganizationPositionConstants.MAX_ORGANIZATION_POSITION_NAME_LENGTH)
    private String positionName;

    /**
     * 职位优先级
     */
    @Min(value = OrganizationPositionConstants.MIN_ORGANIZATION_POSITION_PRIORITY)
    @Max(value = OrganizationPositionConstants.MAX_ORGANIZATION_POSITION_PRIORITY)
    private Integer priority;

}
