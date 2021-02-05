package com.xiaohuashifu.recruit.user.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 描述：停用学院的传输对象
 *
 * @author xhsf
 * @create 2020/12/14 16:48
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeactivateCollegeDTO implements Serializable {

    /**
     * 学院
     */
    private CollegeDTO collegeDTO;

    /**
     * 被停用的专业数量
     */
    private Integer deactivatedCount;

}
