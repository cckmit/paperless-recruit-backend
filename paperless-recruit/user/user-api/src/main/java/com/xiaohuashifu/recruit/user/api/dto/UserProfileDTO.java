package com.xiaohuashifu.recruit.user.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 描述：用户个人信息传输对象
 *
 * @author: xhsf
 * @create: 2020/10/30 15:05
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDTO implements Serializable {

    /**
     * 用户个人信息编号
     */
    private Long id;

    /**
     * 该用户信息所属用户的编号
     */
    private Long userId;

    /**
     * 姓名
     */
    private String fullName;

    /**
     * 学号
     */
    private String studentNumber;

    /**
     * 学院编号
     */
    private Long collegeId;

    /**
     * 专业编号
     */
    private Long majorId;

    /**
     * 自我介绍
     */
    private String introduction;

}
