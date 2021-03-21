package com.xiaohuashifu.recruit.facade.service.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 描述：报名表
 *
 * @author xhsf
 * @create 2020/12/23 16:08
 */
@ApiModel
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationFormVO implements Serializable {

    /**
     * 报名表模板编号
     */
    @ApiModelProperty(value = "报名表模板编号", example = "123")
    private Long id;

    /**
     * 报名者用户编号
     */
    @ApiModelProperty(value = "报名者用户编号", example = "123")
    private Long userId;

    /**
     * 招新
     */
    @ApiModelProperty(value = "招新")
    private RecruitmentVO recruitment;

    /**
     * 头像地址
     */
    @ApiModelProperty(value = "头像地址",
            example = "https://oss.xiaohuashifu.top/application-forms/avatars/dc098266-814f-450e-aa98-837810833d7b1.jpg")
    private String avatarUrl;

    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名", example = "吴阿花")
    private String fullName;

    /**
     * 手机号码
     */
    @ApiModelProperty(value = "手机号码", example = "13333333333")
    private String phone;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱", example = "123@qq.com")
    private String email;

    /**
     * 个人简介
     */
    @ApiModelProperty(value = "个人简介", example = "xx")
    private String introduction;

    /**
     * 附件地址
     */
    @ApiModelProperty(value = "附件地址",
            example = "https://oss.xiaohuashifu.top/application-forms/attachments/dc098266-814f-450e-aa98-837810833d7b1.jpg")
    private String attachmentUrl;

    /**
     * 学号
     */
    @ApiModelProperty(value = "学号", example = "202020202020")
    private String studentNumber;

    /**
     * 学院
     */
    @ApiModelProperty(value = "学院", example = "数信")
    private String college;

    /**
     * 专业
     */
    @ApiModelProperty(value = "专业", example = "软工")
    private String major;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", example = "xx")
    private String note;

    /**
     * 报名时间
     */
    @ApiModelProperty(value = "报名时间", example = "2021-03-17 23:35:00")
    private LocalDateTime applicationTime;

}
