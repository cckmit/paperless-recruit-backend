package com.xiaohuashifu.recruit.facade.service.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 描述：专业
 *
 * @author xhsf
 * @create 2021/1/9 00:41
 */
@ApiModel
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MajorVO implements Serializable {

    /**
     * 专业编号
     */
    @ApiModelProperty(value = "专业编号", example = "123")
    private Long id;

    /**
     * 专业名
     */
    @ApiModelProperty(value = "专业名", example = "软件工程")
    private String majorName;

    /**
     * 专业所属学院编号
     */
    @ApiModelProperty(value = "专业所属学院编号", example = "12")
    private CollegeVO college;

}
