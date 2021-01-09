package com.xiaohuashifu.recruit.facade.service.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 描述：学院
 *
 * @author xhsf
 * @create 2021/1/9 00:40
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CollegeVO implements Serializable {
    /**
     * 学院编号
     */
    private Long id;

    /**
     * 学院名
     */
    private String collegeName;
}
