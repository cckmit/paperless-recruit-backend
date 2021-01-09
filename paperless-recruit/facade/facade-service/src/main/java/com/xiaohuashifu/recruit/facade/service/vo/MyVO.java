package com.xiaohuashifu.recruit.facade.service.vo;

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
public class MyVO implements Serializable {

    /**
     * 用户个人信息编号
     */
    private Long id;

    /**
     * 姓名
     */
    private String fullName;

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

    /**
     * 自我介绍
     */
    private String introduction;
}
