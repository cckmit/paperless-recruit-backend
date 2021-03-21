package com.xiaohuashifu.recruit.registration.service.do0;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 描述：报名表的数据对象
 *
 * @author xhsf
 * @create 2020/12/23 21:23
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@TableName("application_form")
public class ApplicationFormDO {

    /**
     * 报名表模板编号
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 报名者用户编号
     */
    private Long userId;

    /**
     * 招新编号
     */
    private Long recruitmentId;

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

    /**
     * 备注
     */
    private String note;

    /**
     * 报名时间
     */
    private LocalDateTime applicationTime;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
