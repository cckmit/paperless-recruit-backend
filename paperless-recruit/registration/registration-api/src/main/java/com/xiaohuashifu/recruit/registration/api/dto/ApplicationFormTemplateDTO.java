package com.xiaohuashifu.recruit.registration.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 描述：报名表模板的数据传输对象
 *
 * @author xhsf
 * @create 2020/12/23 21:23
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ApplicationFormTemplateDTO implements Serializable {

    /**
     * 报名表模板编号
     */
    private Long id;

    /**
     * 报名者用户编号
     */
    private Long userId;

    /**
     * 头像地址
     */
    private String avatarUrl;

    /**
     * 姓名
     */
    private String fullName;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 个人简介
     */
    private String introduction;

    /**
     * 附件地址
     */
    private String attachmentUrl;

    /**
     * 学号
     */
    private String studentNumber;

    /**
     * 学院
     */
    private String college;

    /**
     * 专业
     */
    private String major;

}
