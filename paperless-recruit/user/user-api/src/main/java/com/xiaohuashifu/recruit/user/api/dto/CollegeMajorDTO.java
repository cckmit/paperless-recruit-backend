package com.xiaohuashifu.recruit.user.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 描述：学院专业传输对象
 *
 * @author: xhsf
 * @create: 2020/10/30 15:05
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CollegeMajorDTO implements Serializable {

    /**
     * 学院编号
     */
    private Long id;

    /**
     * 学院名
     */
    private String collegeName;

    /**
     * 专业列表
     */
    private List<MajorDTO> majorList;

}
