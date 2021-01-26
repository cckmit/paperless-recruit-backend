package com.xiaohuashifu.recruit.facade.service.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 描述：学院
 *
 * @author xhsf
 * @create 2021/1/9 00:40
 */
@ApiModel
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CollegeVO implements Serializable {

    /**
     * 学院编号
     */
    @ApiModelProperty(value = "学院编号", example = "123")
    private Long id;

    /**
     * 学院名
     */
    @ApiModelProperty(value = "学院名", example = "软件学院")
    private String collegeName;

}
