package com.xiaohuashifu.recruit.facade.service.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 描述：用户
 *
 * @author xhsf
 * @create 2020/11/29 17:56
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class UserVO implements Serializable {

    /**
     * 用户编号
     */
    @ApiModelProperty(value = "用户编号", example = "321")
    private Long id;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名", example = "username")
    private String username;

    /**
     * 手机号码
     */
    @ApiModelProperty(value = "手机号码", example = "13333333333")
    private String phone;

    /**
     * 电子邮箱
     */
    @ApiModelProperty(value = "电子邮箱", example = "email@163.com")
    private String email;

    /**
     * 用户是否可用
     */
    @ApiModelProperty(value = "用户是否可用", example = "true")
    private Boolean available;

}
