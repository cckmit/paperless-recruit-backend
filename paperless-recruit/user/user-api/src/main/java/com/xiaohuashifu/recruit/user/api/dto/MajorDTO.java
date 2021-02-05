package com.xiaohuashifu.recruit.user.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 描述：专业传输对象
 *
 * @author: xhsf
 * @create: 2020/10/30 15:05
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MajorDTO implements Serializable {

    /**
     * 专业编号
     */
    private Long id;

    /**
     * 专业所属学院编号
     */
    private Long collegeId;

    /**
     * 专业名
     */
    private String majorName;

    /**
     * 专业是否被停用
     */
    private Boolean deactivated;

}
