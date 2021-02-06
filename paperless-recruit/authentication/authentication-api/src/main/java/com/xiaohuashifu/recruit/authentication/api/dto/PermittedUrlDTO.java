package com.xiaohuashifu.recruit.authentication.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 描述：被允许路径传输对象
 *
 * @author xhsf
 * @create 2020/11/27 17:27
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PermittedUrlDTO implements Serializable {

    /**
     * 被允许路径编号
     */
    private Long id;

    /**
     * 被允许路径
     */
    private String url;

}
