package com.xiaohuashifu.recruit.facade.service.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 描述：我的页面VO
 *
 * @author xhsf
 * @create 2020/12/2 21:07
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class MyVO implements Serializable {

    /**
     * 用户个人信息编号
     */
    @ApiModelProperty(value = "用户个人信息编号", example = "321")
    private Long id;

    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名", example = "张三")
    private String fullName;

    /**
     * 学号
     */
    @ApiModelProperty(value = "学号", example = "207734420199")
    private String studentNumber;

    /**
     * 学院
     */
    @ApiModelProperty(value = "学院", example = "软件学院")
    private String college;

    /**
     * 专业
     */
    @ApiModelProperty(value = "专业", example = "软件工程")
    private String major;

    /**
     * 自我介绍
     */
    @ApiModelProperty(value = "自我介绍", example = "我是法外狂徒张三")
    private String introduction;
}
