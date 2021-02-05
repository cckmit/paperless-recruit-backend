package com.xiaohuashifu.recruit.external.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 描述：图形验证码传输对象
 *
 * @author: xhsf
 * @create: 2020/11/22 15:44
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageAuthCodeDTO implements Serializable {
    /**
     * 唯一标识一个图形验证码
     * 格式为：随机字符串+序列编号
     */
    private String id;

    /**
     * 图形验证码的 Base64 编码字符串
     */
    private String authCode;

}
