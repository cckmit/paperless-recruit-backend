package com.xiaohuashifu.recruit.facade.service.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 描述：组织类型 VO
 *
 * @author xhsf
 * @create 2021/1/9 14:55
 */
@ApiModel
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationTypeVO {

    /**
     * 组织类型编号
     */
    @ApiModelProperty(value = "组织类型编号", example = "123")
    private Long id;

    /**
     * 组织类型名
     */
    @ApiModelProperty(value = "类型名", example = "综合")
    private String typeName;

}
