package com.xiaohuashifu.recruit.external.api.dto;

import com.xiaohuashifu.recruit.external.api.service.ImageAuthCodeService;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * 描述：图形验证码传输类型
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/22 15:44
 */
public class ImageAuthCodeDTO implements Serializable {
    /**
     * 唯一标识一个图形验证码
     * 格式为：随机字符串+序列编号
     */
    private String id;

    /**
     * 图形验证码的Base64编码字符串
     */
    private String authCode;

    /**
     * 图形验证码宽度
     */
    @NotNull(groups = ImageAuthCodeService.CreateImageAuthCode.class)
    @Positive
    private Integer width;

    /**
     * 图形验证码高度
     */
    @NotNull(groups = ImageAuthCodeService.CreateImageAuthCode.class)
    @Positive
    private Integer height;

    /**
     * 验证码长度（位数）
     */
    @NotNull(groups = ImageAuthCodeService.CreateImageAuthCode.class)
    @Positive
    @Max(10)
    private Integer length;

    /**
     * 图形验证码有效时间，单位分钟
     */
    @NotNull(groups = ImageAuthCodeService.CreateImageAuthCode.class)
    @Positive
    @Max(10)
    private Integer expiredTime;


}
