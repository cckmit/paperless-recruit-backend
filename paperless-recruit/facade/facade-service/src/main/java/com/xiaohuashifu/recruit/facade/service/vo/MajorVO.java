package com.xiaohuashifu.recruit.facade.service.vo;

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
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MajorVO implements Serializable {

    /**
     * 专业编号
     */
    private Long id;

    /**
     * 专业名
     */
    private String majorName;

    /**
     * 专业所属学院编号
     */
    private CollegeVO college;

}
